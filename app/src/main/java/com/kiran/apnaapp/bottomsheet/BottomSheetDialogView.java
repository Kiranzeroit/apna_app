package com.kiran.apnaapp.bottomsheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.archit.calendardaterangepicker.customviews.CalendarListener;
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.kiran.apnaapp.R;

import java.util.Calendar;

public class BottomSheetDialog extends BottomSheetDialogFragment implements CalendarListener {
    private ItemClickListener mListener;

    public static BottomSheetDialog newInstance() {
        return new BottomSheetDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_calander_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        DateRangeCalendarView calendar = view.findViewById(R.id.calendar);
        calendar.setCalendarListener(this);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context + " must implement ItemClickListener");
        }
    }

    @Override
    public void onDateRangeSelected(@NonNull Calendar calendar, @NonNull Calendar calendar1) {

    }

    @Override
    public void onFirstDateSelected(@NonNull Calendar calendar) {

    }

    public interface ItemClickListener {
        void onItemClick(String item);
    }
}
