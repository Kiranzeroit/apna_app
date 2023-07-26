package com.kiran.apnaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kiran.apnaapp.databinding.ActivityMainBinding;
import com.kiran.apnaapp.fragments.MenuFragment;
import com.kiran.apnaapp.fragments.StartFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private ActivityMainBinding binding;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentManager = getSupportFragmentManager();
        initView();
        loadFragment(new MenuFragment());
    }

    private void initView() {
        binding.bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        binding.bottomNavigationView.setSelectedItemId(R.id.entries);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.entries) {
            loadFragment(new MenuFragment());
        } else if (id==R.id.start) {
            loadFragment(new StartFragment());
        }

        return true;
    }
    private void loadFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }
}