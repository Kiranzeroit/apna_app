package com.kiran.apnaapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.archit.calendardaterangepicker.customviews.CalendarListener;
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kiran.apnaapp.R;
import com.kiran.apnaapp.databinding.ActivityAddPlanBinding;

import java.util.Calendar;

public class AddPlanActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityAddPlanBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPlanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.tvSave.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        binding.etStartDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            onBackPressed();
        } else if (view == binding.tvSave) {
            if (isValidation()) {
                String name = binding.etName.getText().toString().trim();
                String budget = binding.etBudget.getText().toString().trim();
                String startDate = binding.etStartDate.getText().toString().trim();
                String endDate = binding.etEndDate.getText().toString().trim();
            }
        } else if (view==binding.etStartDate) {
            showBottomSheetDialog();
        }
    }

    private boolean isValidation() {
        if (binding.etName.getText().toString().trim().isEmpty()) {
            binding.etName.requestFocus();
            binding.etName.setError("Please enter a name for your trip");
            return false;
        } else if (binding.etBudget.getText().toString().trim().isEmpty()) {
            binding.etBudget.requestFocus();
            binding.etBudget.setError("Please enter a name for your trip");
            return false;
        } else if (binding.etStartDate.getText().toString().trim().isEmpty()) {
            binding.etStartDate.requestFocus();
            binding.etStartDate.setError("Select start date");
            return false;
        } else if (binding.etEndDate.getText().toString().trim().isEmpty()) {
            binding.etEndDate.requestFocus();
            binding.etEndDate.setError("Select end date");
            return false;
        }
        return true;
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_calander_dialog);
        DateRangeCalendarView calendar = findViewById(R.id.calendar);
        calendar.setCalendarListener(new CalendarListener() {
            @Override
            public void onFirstDateSelected(Calendar startDate) {
                Toast.makeText(AddPlanActivity.this, "Start Date: " + startDate.getTime().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {

                Toast.makeText(AddPlanActivity.this, "Start Date: " + startDate.getTime().toString() + " End date: " + endDate.getTime().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        bottomSheetDialog.show();
    }

}
