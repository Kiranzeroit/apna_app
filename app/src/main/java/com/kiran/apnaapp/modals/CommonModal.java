package com.kiran.apnaapp.modals;

import android.graphics.drawable.Drawable;

public class CommonModal {
    public String type = "";
    public Drawable image;

    public CommonModal(String type, Drawable image) {
        this.type = type;
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public Drawable getImage() {
        return image;
    }
}
