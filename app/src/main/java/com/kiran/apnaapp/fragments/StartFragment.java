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
    private PieDataSet pieDataSetOne;

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
        tripModal= databaseHelper.getTripDetails();
        showPieChart();
    }

    private void showPieChart() {

        for (int i = 0; i < tripDataList.size(); i++) {
            pieEntries.add(new PieEntry(Float.parseFloat(tripDataList.get(i).budget),tripDataList.get(i).name));
        }



        pieDataSetOne = new PieDataSet(pieEntries,"Budget");
        pieDataSetOne.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSetOne.setValueTextColor(Color.BLACK);
        pieDataSetOne.setValueTextSize(18f);

        PieData pieData = new PieData(pieDataSetOne);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Data");
        pieChart.setCenterTextSize(18f);
        pieChart.animate();
    }

   }
