package com.kiran.apnaapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.kiran.apnaapp.R;
import com.kiran.apnaapp.activities.AddPlanActivity;
import com.kiran.apnaapp.activities.PlanDetailsActivity;
import com.kiran.apnaapp.adapter.DetailsAdapter;
import com.kiran.apnaapp.database.DatabaseHelper;
import com.kiran.apnaapp.interfaces.InterfaceClickCallback;
import com.kiran.apnaapp.modals.TripModal;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment implements View.OnClickListener, InterfaceClickCallback {
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private DetailsAdapter detailsAdapter;
    private DatabaseHelper databaseHelper;
    private List<TripModal> tripList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutInflater().inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseHelper = new DatabaseHelper(requireActivity());
        initView(view);
        setAdapterView();
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);
    }

    private void setAdapterView() {
        tripList.clear();
        tripList = databaseHelper.getUsersTripList();
        detailsAdapter = new DetailsAdapter(requireActivity(), tripList, this);
        recyclerView.setAdapter(detailsAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view == floatingActionButton) {
            Intent intent = new Intent(requireActivity(), AddPlanActivity.class);
            someActivityResultLauncher.launch(intent);
        }
    }

    @Override
    public void clickCallBack(Object object, int position, String from) {
        if (from.equalsIgnoreCase("item")) {
            TripModal tripModal = (TripModal) object;
            Intent intent = new Intent(requireActivity(), PlanDetailsActivity.class);
            String tripDetails = new Gson().toJson(tripModal);
            intent.putExtra("tripModal", tripDetails);
            startActivity(intent);
        } else if (from.equalsIgnoreCase("delete")) {
            TripModal tripModal = (TripModal) object;
            databaseHelper.deletePlanDetails(tripModal.name);
            setAdapterView();
        }
    }

    private final ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.hasExtra("isSave")) {
                        setAdapterView();
                    }
                }
            });
}