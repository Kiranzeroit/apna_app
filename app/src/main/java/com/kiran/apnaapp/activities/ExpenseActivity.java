package com.kiran.apnaapp.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.google.gson.Gson;
import com.kiran.apnaapp.R;
import com.kiran.apnaapp.Session;
import com.kiran.apnaapp.adapter.SpinnerAdapter;
import com.kiran.apnaapp.modals.CommonModal;
import com.kiran.apnaapp.modals.ExpensesModal;
import com.kiran.apnaapp.modals.TripModal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ExpenseActivity extends AppCompatActivity implements View.OnClickListener {
    private SpinnerAdapter spinnerAdapter;
    private AppCompatEditText etNote, etDate, etAmount, etAddPlace;
    private Spinner spinnerPaymentType, spinnerSpentType;
    private FloatingActionButton floatingActionButton;
    private String tripDetails;
    private TripModal tripModal;
    private Session session;
    private List<ExpensesModal> expensesModalList = new ArrayList<>();
    private String note = "";
    private String selectDate = "";
    private String amount = "";
    private String paymentType = "";
    private String place = "";
    private String spentType = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        if (getIntent().hasExtra("tripModal")) {
            tripDetails = getIntent().getStringExtra("tripModal");
            tripModal = new Gson().fromJson(tripDetails, TripModal.class);
        }
        session = new Session(this);
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
        floatingActionButton = findViewById(R.id.floatingActionButton);
        etDate.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
        spinnerPaymentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                paymentType = setPaymentList().get(position).type;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSpentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spentType = setSpentList().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == etDate) {
            openDatePicker();
        } else if (view == floatingActionButton) {
            if (isValidation()) {
                ExpensesModal expensesModal = new ExpensesModal();
                expensesModal.note = note;
                expensesModal.selectDate = selectDate;
                expensesModal.amount = amount;
                expensesModal.paymentType = paymentType;
                expensesModal.place = place;
                expensesModal.spentType = spentType;
                expensesModalList.clear();

                try {
                    if (session.getExpensesList(tripModal.name) != null) {
                        expensesModalList = session.getExpensesList(tripModal.name);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                expensesModalList.add(expensesModal);
                String expensesList = new Gson().toJson(expensesModalList);
                session.setExpenses(tripModal.name, expensesList);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("isSave", true);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        }
    }

    private void setPaymentSpinner() {
        spinnerAdapter = new SpinnerAdapter(this, setPaymentList());
        spinnerPaymentType.setAdapter(spinnerAdapter);
    }

    private List<CommonModal> setPaymentList() {
        ArrayList<CommonModal> paymentTypeList = new ArrayList<>();
        paymentTypeList.add(new CommonModal("Cash", ContextCompat.getDrawable(this, R.drawable.ic_cash)));
        paymentTypeList.add(new CommonModal("Credit Card", ContextCompat.getDrawable(this, R.drawable.ic_credit_card)));
        return paymentTypeList;
    }

    private boolean isValidation() {

        note = etNote.getText().toString().trim();
        selectDate = etDate.getText().toString().trim();
        amount = etAmount.getText().toString().trim();
        place = etAddPlace.getText().toString().trim();
        if (note.isEmpty()) {
            Toast.makeText(this, "Please enter note", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selectDate.isEmpty()) {
            Toast.makeText(this, "Please select date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (amount.isEmpty()) {
            Toast.makeText(this, "please enter amount", Toast.LENGTH_SHORT).show();
            return false;
        } else if (paymentType.isEmpty()) {
            Toast.makeText(this, "please select payment type", Toast.LENGTH_SHORT).show();
            return false;
        } else if (place.isEmpty()) {
            Toast.makeText(this, "please enter note", Toast.LENGTH_SHORT).show();
            return false;
        } else if (spentType.isEmpty()) {
            Toast.makeText(this, "please select spent type", Toast.LENGTH_SHORT).show();
            return false;
        }
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

    private void setSpentSpinner() {
        ArrayAdapter adapter = new ArrayAdapter(ExpenseActivity.this, android.R.layout.simple_spinner_item, setSpentList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpentType.setAdapter(adapter);
    }

    private ArrayList<String> setSpentList() {
        ArrayList<String> spentList = new ArrayList<>();
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
