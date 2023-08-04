package com.kiran.apnaapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.kiran.apnaapp.activities.SplashScreenActivity;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class CommonUtilities {

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");
        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }

    public static boolean isValidNumber(String mobileNumber) {
        Pattern PASSWORD_PATTERN = Pattern.compile("^[+]{1}(?:[0-9\\\\-\\\\(\\\\)\\\\/\\\\.]\\\\s?){6,15}[0-9]{1}$");
        return !TextUtils.isEmpty(mobileNumber) && PASSWORD_PATTERN.matcher(mobileNumber).matches();
    }

    public static String changeDateFormat(String inputDate) {
        // Create a DateTimeFormatter object for the input date format.
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("E MMM d HH:mm:ss z yyyy");

        // Create a DateTimeFormatter object for the output date format.
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Parse the input date string and get the LocalDate object.
        LocalDate date = LocalDate.parse(inputDate, inputFormatter);

        // Format the LocalDate object to the output date format.
        String outputDate = date.format(outputFormatter);

        // Return the output date string.
        return outputDate;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

 /*   public static void dialogLogout(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View customLayout = getLayoutInflater().inflate(R.layout.dialog_logout, null);
        builder.setView(customLayout);
        AppCompatTextView tvYes = customLayout.findViewById(R.id.tvYes);
        AppCompatTextView tvNo = customLayout.findViewById(R.id.tvNo);
        AlertDialog dialog = builder.create();
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }*/
}
