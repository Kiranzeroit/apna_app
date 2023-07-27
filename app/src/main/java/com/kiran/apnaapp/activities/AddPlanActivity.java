package com.kiran.apnaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.archit.calendardaterangepicker.customviews.CalendarListener;
import com.kiran.apnaapp.CommonUtilities;
import com.kiran.apnaapp.bottomsheet.BottomSheetDialogView;
import com.kiran.apnaapp.database.DatabaseHelper;
import com.kiran.apnaapp.databinding.ActivityAddPlanBinding;

import java.util.Calendar;

public class AddPlanActivity extends AppCompatActivity implements View.OnClickListener, CalendarListener, BottomSheetDialogView.ItemClickListener {
    private ActivityAddPlanBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPlanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);
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

                databaseHelper.saveTripDetails(name,budget, startDate, endDate);
                Toast.makeText(this, "Save successfully", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        } else if (view == binding.etStartDate) {
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

        BottomSheetDialogView bottomSheetDialog = BottomSheetDialogView.newInstance();
        bottomSheetDialog.show(getSupportFragmentManager(), BottomSheetDialogView.TAG);
    }

    @Override
    public void onDateRangeSelected(@NonNull Calendar startDate, @NonNull Calendar endDate) {
        binding.etStartDate.setText(CommonUtilities.changeDateFormat(startDate.getTime().toString()));
        binding.etEndDate.setText(CommonUtilities.changeDateFormat(endDate.getTime().toString()));
    }

    @Override
    public void onFirstDateSelected(@NonNull Calendar startDate) {
    }


}