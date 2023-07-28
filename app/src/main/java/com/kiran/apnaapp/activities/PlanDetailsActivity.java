package com.kiran.apnaapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.kiran.apnaapp.R;
import com.kiran.apnaapp.Session;
import com.kiran.apnaapp.adapter.ExpansesAdapter;
import com.kiran.apnaapp.interfaces.InterfaceClickCallback;
import com.kiran.apnaapp.modals.ExpensesModal;
import com.kiran.apnaapp.modals.TripModal;

import java.util.ArrayList;
import java.util.List;

public class PlanDetailsActivity extends AppCompatActivity implements InterfaceClickCallback, View.OnClickListener {

    private TripModal tripModal;
    private TextView tvTitle;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private List<ExpensesModal> expensesModalList = new ArrayList<>();
    private ExpansesAdapter expansesAdapter;
    private Session session;
    private String tripDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_details);

        if (getIntent().hasExtra("tripModal")) {
            tripDetails = getIntent().getStringExtra("tripModal");
            tripModal = new Gson().fromJson(tripDetails, TripModal.class);
        }
        session = new Session(this);
        initView();
        setAdapterView();
        tvTitle.setText(tripModal.name);
    }

    private void initView() {
        tvTitle = findViewById(R.id.tvTitle);
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(this);
    }

    private void setAdapterView() {
        expensesModalList.clear();
        expensesModalList = session.getExpensesList(tripModal.name);
        try {
            if (expensesModalList != null) {
                expansesAdapter = new ExpansesAdapter(this, expensesModalList, this);
                recyclerView.setAdapter(expansesAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void clickCallBack(Object object, int position, String from) {

    }

    private final ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.hasExtra("tripModal")) {
                        tripDetails = data.getStringExtra("tripModal");
                        tripModal = new Gson().fromJson(tripDetails, TripModal.class);
                        setAdapterView();
                    }
                }
            });

    @Override
    public void onClick(View view) {
        if (view == floatingActionButton) {
            Intent intent = new Intent(this, ExpenseActivity.class);
            intent.putExtra("tripModal", tripDetails);
            someActivityResultLauncher.launch(intent);
        }
    }
}
