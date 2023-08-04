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
import com.github.mikephil.charting.utils.ColorTemplate;
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
    private TripModal tripModal = new TripModal();
    private PieDataSet pieDataSet;

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
        tripModal = databaseHelper.getTripDetails();
        showPieChart();
    }

    private void showPieChart() {

        for (int i = 0; i < tripDataList.size(); i++) {
            pieEntries.add(new PieEntry(Float.parseFloat(tripDataList.get(i).budget), tripDataList.get(i).name));
        }
        pieDataSet = new PieDataSet(pieEntries, "Budget");
        // pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(18f);
        pieDataSet.setColors(getColorList());

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Data");
        pieChart.setCenterTextSize(18f);
        pieChart.animate();
    }

    private ArrayList<Integer> getColorList() {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#FF0000"));
        colors.add(Color.parseColor("#00FFFF"));
        colors.add(Color.parseColor("#ADD8E6"));
        colors.add(Color.parseColor("#FFFF00"));
        colors.add(Color.parseColor("#00FF00"));
        colors.add(Color.parseColor("#FF00FF"));
        colors.add(Color.parseColor("#FFC0CB"));
        colors.add(Color.parseColor("#800000"));
        colors.add(Color.parseColor("#7FFFD4"));
        colors.add(Color.parseColor("#FFA500"));
        colors.add(Color.parseColor("#800080"));
        return colors;
    }

}
