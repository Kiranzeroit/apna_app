package com.kiran.apnaapp;

import android.text.TextUtils;
import android.util.Patterns;

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
}
