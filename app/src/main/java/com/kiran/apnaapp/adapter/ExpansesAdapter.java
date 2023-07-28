package com.kiran.apnaapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.kiran.apnaapp.R;
import com.kiran.apnaapp.interfaces.InterfaceClickCallback;
import com.kiran.apnaapp.modals.TripModal;

import java.util.List;

public class ExpansesAdapter extends RecyclerView.Adapter<ExpansesAdapter.DataViewHolder> {

    private final Context context;
    private final List<TripModal> list;

    private final InterfaceClickCallback interfaceClickCallback;

    public ExpansesAdapter(Context context, List<TripModal> list, InterfaceClickCallback interfaceClickCallback) {
        this.context = context;
        this.list = list;
        this.interfaceClickCallback = interfaceClickCallback;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_details, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.bindView(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvName, tvBudget, tvStartDate, tvEndDate;
        private AppCompatImageView ivMore;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            tvName = itemView.findViewById(R.id.tvName);
            tvBudget = itemView.findViewById(R.id.tvBudget);
            tvStartDate = itemView.findViewById(R.id.tvStartDate);
            tvEndDate = itemView.findViewById(R.id.tvEndDate);
            ivMore = itemView.findViewById(R.id.ivMore);
        }

        private void bindView(TripModal tripModal, int position) {
            tvName.setText(tripModal.name);
            tvBudget.setText(tripModal.budget);
            tvStartDate.setText(tripModal.startDate);
            tvEndDate.setText(tripModal.endDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interfaceClickCallback.clickCallBack(tripModal, position, "item");
                }
            });

            ivMore.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    interfaceClickCallback.clickCallBack(tripModal, position, "delete");
                    Toast.makeText(context, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                });
                popupMenu.show();
            });
        }
    }
}
