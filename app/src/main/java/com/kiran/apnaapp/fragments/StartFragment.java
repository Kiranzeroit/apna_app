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

import java.util.ArrayList;

public class StartFragment extends Fragment {
    private PieChart pieChart;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutInflater().inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        pieChart = view.findViewById(R.id.pieChart);
        showPieChart();
    }

    private void showPieChart(){

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(50,"2010"));
        pieEntries.add(new PieEntry(50,"2012"));
        pieEntries.add(new PieEntry(50,"2017"));
        pieEntries.add(new PieEntry(50,"2019"));
        pieEntries.add(new PieEntry(50,"2020"));
        pieEntries.add(new PieEntry(50,"2023"));

        PieDataSet pieDataSet= new PieDataSet(pieEntries,"data");
   //     pieDataSet.setColor(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(12f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate();
        pieChart.animate();
    }
}
