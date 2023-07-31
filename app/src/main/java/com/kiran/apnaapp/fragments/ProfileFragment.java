package com.kiran.apnaapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.kiran.apnaapp.R;
import com.kiran.apnaapp.Session;
import com.kiran.apnaapp.activities.PlanDetailsActivity;
import com.kiran.apnaapp.activities.SplashScreenActivity;
import com.kiran.apnaapp.database.DatabaseHelper;
import com.kiran.apnaapp.modals.DetailsModal;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private AppCompatTextView tvName, tvCity, tvMobile, tvEmail, tvLogout;
    private DetailsModal detailsModal;
    private Session session;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutInflater().inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        session = new Session(requireActivity());
        DatabaseHelper databaseHelper = new DatabaseHelper(requireActivity());
        detailsModal = new DetailsModal();
        detailsModal = databaseHelper.getUserDetails(session.getStringValue("email"));

        initView(view);
        setData();
    }

    private void initView(View view) {
        tvName = view.findViewById(R.id.tvName);
        tvCity = view.findViewById(R.id.tvCity);
        tvMobile = view.findViewById(R.id.tvMobile);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvLogout = view.findViewById(R.id.tvLogout);
        tvLogout.setOnClickListener(this);
    }

    private void setData() {
        tvName.setText(detailsModal.name);
        tvCity.setText(detailsModal.city);
        tvEmail.setText(detailsModal.email);
        tvMobile.setText(detailsModal.mobile);
    }

    @Override
    public void onClick(View view) {
        if (view == tvLogout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want to logout?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    session.setBooleanValue("Login", false);
                    startActivity( new Intent(requireActivity(), SplashScreenActivity.class));
                }
            });
            builder.show();
        }
    }
}
