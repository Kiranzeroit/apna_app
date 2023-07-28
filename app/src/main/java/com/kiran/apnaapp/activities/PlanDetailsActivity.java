package com.kiran.apnaapp.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.kiran.apnaapp.R;
import com.kiran.apnaapp.adapter.ExpansesAdapter;
import com.kiran.apnaapp.database.DatabaseHelper;
import com.kiran.apnaapp.interfaces.InterfaceClickCallback;
import com.kiran.apnaapp.modals.TripModal;

import java.util.ArrayList;
import java.util.List;

public class PlanDetailsActivity extends AppCompatActivity implements InterfaceClickCallback {

    private TripModal tripModal;
    private TextView tvTitle;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private List<TripModal> tripList = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private ExpansesAdapter expansesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_details);

        if (getIntent().hasExtra("tripModal")) {
            String tripDetails = getIntent().getStringExtra("tripModal");
            tripModal = new Gson().fromJson(tripDetails, TripModal.class);
        }
        databaseHelper = new DatabaseHelper(this);
        initView();
        setAdapterView();
        tvTitle.setText(tripModal.name);
    }

    private void initView() {
        tvTitle = findViewById(R.id.tvTitle);
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
    }

    private void setAdapterView() {
        tripList.clear();
        tripList = databaseHelper.getUsersTripList();
        expansesAdapter = new ExpansesAdapter(this, tripList, this);
        recyclerView.setAdapter(expansesAdapter);
    }

    @Override
    public void clickCallBack(Object object, int position, String from) {

    }
}
