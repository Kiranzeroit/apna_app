package com.kiran.apnaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.kiran.apnaapp.R;
import com.kiran.apnaapp.modals.CommonModal;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<CommonModal> {
    private AppCompatTextView tvType;
    private AppCompatImageView ivImage;

    public SpinnerAdapter(@NonNull Context context, @NonNull List<CommonModal> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spent_type, parent, false);
        }

        tvType = convertView.findViewById(R.id.tvType);
        ivImage= convertView.findViewById(R.id.ivImage);
        CommonModal commonModal = getItem(position);

        if (commonModal != null) {
            tvType.setText(commonModal.getType());
            ivImage.setImageDrawable(commonModal.getImage());
        }
        return convertView;
    }
}
