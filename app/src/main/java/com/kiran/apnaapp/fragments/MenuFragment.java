package com.kiran.apnaapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.kiran.apnaapp.R;
import com.kiran.apnaapp.activities.AddPlanActivity;
import com.kiran.apnaapp.adapter.DetailsAdapter;
import com.kiran.apnaapp.database.DatabaseHelper;
import com.kiran.apnaapp.interfaces.ClickListener;
import com.kiran.apnaapp.modals.TripModal;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment implements View.OnClickListener, ClickListener {
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
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);

    /*   tripList = databaseHelper.getUsersTargetList();

        detailsAdapter = new DetailsAdapter(requireActivity(), tripList, this);
        recyclerView.setAdapter(detailsAdapter);
        detailsAdapter.notifyDataSetChanged();
*/


    }


    @Override
    public void onClick(View view) {
        if (view== floatingActionButton){
            Intent intent = new Intent(requireActivity(), AddPlanActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(TripModal tripModal, int position) {

    }
}
