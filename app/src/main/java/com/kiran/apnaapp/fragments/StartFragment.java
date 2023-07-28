package com.kiran.apnaapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.kiran.apnaapp.R;
import com.kiran.apnaapp.database.DatabaseHelper;
import com.kiran.apnaapp.modals.TripModal;

import java.util.ArrayList;
import java.util.List;

public class StartFragment extends Fragment {
    private PieChart pieChart;
    private List<TripModal> tripDataList = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private ArrayList<PieEntry> pieEntries = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutInflater().inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseHelper = new DatabaseHelper(requireActivity());
        initView(view);
    }

    private void initView(View view) {
        pieChart = view.findViewById(R.id.pieChart);
        tripDataList = databaseHelper.getUsersTripList();
        showPieChart();
    }

    private void showPieChart() {


        pieEntries.add(new PieEntry(50, "2010"));
        pieEntries.add(new PieEntry(50, "2012"));
        pieEntries.add(new PieEntry(50, "2017"));
        pieEntries.add(new PieEntry(50, "2019"));
        pieEntries.add(new PieEntry(50, "2020"));
        pieEntries.add(new PieEntry(50, "2023"));

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#304567"));
        colors.add(Color.parseColor("#309967"));
        colors.add(Color.parseColor("#476567"));
        colors.add(Color.parseColor("#890567"));
        colors.add(Color.parseColor("#a35567"));
        colors.add(Color.parseColor("#ff5f67"));
        colors.add(Color.parseColor("#3ca567"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "data");
        pieDataSet.setColors(colors);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(18f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Data");
        pieChart.setCenterTextSize(18f);
        pieChart.animate();
    }
}
