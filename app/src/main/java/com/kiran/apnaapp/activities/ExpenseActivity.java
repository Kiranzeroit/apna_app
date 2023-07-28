package com.kiran.apnaapp.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kiran.apnaapp.R;
import com.kiran.apnaapp.adapter.SpinnerAdapter;
import com.kiran.apnaapp.modals.CommonModal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ExpenseActivity extends AppCompatActivity implements View.OnClickListener{
    private SpinnerAdapter spinnerAdapter;
    private AppCompatEditText etNote, etDate, etAmount, etAddPlace;
    private Spinner spinnerPaymentType, spinnerSpentType;
    private FloatingActionButton floatingActionButton;
    private ArrayList<CommonModal> paymentTypeList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        initView();
        setPaymentSpinner();
        setSpentSpinner();
    }

    private void initView() {
        etNote = findViewById(R.id.etNote);
        etDate = findViewById(R.id.etDate);
        etAmount = findViewById(R.id.etAmount);
        etAddPlace = findViewById(R.id.etAddPlace);
        spinnerPaymentType = findViewById(R.id.spinnerPaymentType);
        spinnerSpentType = findViewById(R.id.spinnerSpentType);
        floatingActionButton =findViewById(R.id.floatingActionButton);
        etDate.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
        spinnerPaymentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                CommonModal clickedItem = (CommonModal) adapterView.getItemAtPosition(position);
                String name = clickedItem.getType();
                Toast.makeText(ExpenseActivity.this, name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerSpentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view== etDate) {
            openDatePicker();
            }
    }
        
    private void setPaymentSpinner() {
            spinnerAdapter = new SpinnerAdapter(this,setPaymentList());
            spinnerPaymentType.setAdapter(spinnerAdapter);
    }

    private List<CommonModal> setPaymentList() {
        paymentTypeList= new ArrayList<>();
        paymentTypeList.add(new CommonModal("Cash", ContextCompat.getDrawable(this,R.drawable.ic_cash)));
        paymentTypeList.add(new CommonModal("Credit Card", ContextCompat.getDrawable(this,R.drawable.ic_credit_card)));
        return paymentTypeList;
    }

    private boolean isValidation() {
        return true;
    }
    public void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = day + "-" + (month + 1) + "-" + year;
                etDate.setText(date);
            }
        }, day, month, year);
        datePickerDialog.getDatePicker().init(year, month, day, null);
        datePickerDialog.getDatePicker().setMinDate(Calendar.YEAR);
        datePickerDialog.show();
    }

    private void setSpentSpinner(){
        ArrayAdapter adapter= new ArrayAdapter(ExpenseActivity.this, android.R.layout.simple_spinner_item, setSpentList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpentType.setAdapter(adapter);
    }

    private ArrayList<String> setSpentList() {
        ArrayList<String> spentList= new ArrayList<>();
        spentList.add("Shopping");
        spentList.add("Groceries");
        spentList.add("Fees & Charges");
        spentList.add("Drinks");
        spentList.add("Accommodation");
        spentList.add("Laundry");
        spentList.add("Entertainment");
        spentList.add("Flight");
        spentList.add("Activities");
        spentList.add("Transportation");
        spentList.add("Restaurant");
        spentList.add("General");
        spentList.add("Coffee");
        Collections.sort(spentList);
        return spentList;
    }
}
