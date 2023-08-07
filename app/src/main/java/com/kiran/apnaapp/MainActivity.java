package com.kiran.apnaapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.kiran.apnaapp.activities.SplashScreenActivity;
import com.kiran.apnaapp.database.DatabaseHelper;
import com.kiran.apnaapp.fragments.MenuFragment;
import com.kiran.apnaapp.fragments.ProfileFragment;
import com.kiran.apnaapp.fragments.StartFragment;
import com.kiran.apnaapp.modals.DetailsModal;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private AppCompatTextView tvName, tvCity, tvMobile, tvEmail, tvLogout;
    private DetailsModal detailsModal;
    private Session session;
    private DatabaseHelper databaseHelper;
    private CircleImageView civProfile;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        session = new Session(this);
        databaseHelper = new DatabaseHelper(this);
        detailsModal = new DetailsModal();
        detailsModal = databaseHelper.getUserDetails(session.getStringValue("email"));
        fragmentManager = getSupportFragmentManager();
        initView();
        setData();
        loadFragment(new MenuFragment());
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        civProfile = findViewById(R.id.ivProfile);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvMobile = findViewById(R.id.tvMobile);
        tvCity = findViewById(R.id.tvCity);
        tvLogout = findViewById(R.id.tvLogout);
        navigationView = findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.entries);
        tvLogout.setOnClickListener(this);
        civProfile.setOnClickListener(this);


        // toolbar.setNavigationIcon(R.drawable.ic_menu_24);
        //  drawerLayout.setDrawerIcon(R.drawable.ic_menu_24);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        toolbar.setTitleTextColor(Color.WHITE);
        if (id == R.id.entries) {
            toolbar.setTitle("Record");
            loadFragment(new MenuFragment());
        } else if (id == R.id.start) {
            toolbar.setTitle("Data Chart");
            loadFragment(new StartFragment());
        } else if (id == R.id.profile) {
            toolbar.setTitle("Profile");
            loadFragment(new ProfileFragment());
        }
        return true;
    }

    private void loadFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    private void setData() {
        tvName.setText(detailsModal.name);
        tvCity.setText(detailsModal.city);
        tvEmail.setText(detailsModal.email);
        tvMobile.setText(detailsModal.mobile);
    }

    private void selectImage() {

        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Please Upload!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Camera")) {
                    openCamera();
                } else if (items[item].equals("Gallery")) {
                    openGallery();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        someActivityResultLauncher.launch(cameraIntent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bitmap image = (Bitmap) (data.getExtras().get("data"));
                        Uri uri = CommonUtilities.getImageUri(MainActivity.this, image);
                        Glide.with(MainActivity.this).load(uri).placeholder(R.drawable.ic_person_white).into(civProfile);
                    }
                }
            });

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncherGallery.launch(intent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncherGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri selectedImage = Objects.requireNonNull(data).getData();
                        Glide.with(MainActivity.this).load(selectedImage).placeholder(R.drawable.ic_person_white).into(civProfile);
                    }
                }
            });

    @Override
    public void onClick(View view) {
        if (view == civProfile) {
            selectImage();
        } else if (view == tvLogout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Confirm Logout..!!");
            builder.setMessage("Are you sure you want to logout?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    session.setBooleanValue("Login", false);
                    startActivity(new Intent(MainActivity.this, SplashScreenActivity.class));
                }
            });
            builder.show();
        }
    }

}