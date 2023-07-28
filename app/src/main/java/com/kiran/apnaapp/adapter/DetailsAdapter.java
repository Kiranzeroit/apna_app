package com.kiran.apnaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.kiran.apnaapp.R;
import com.kiran.apnaapp.interfaces.ClickListener;
import com.kiran.apnaapp.modals.TripModal;

import java.util.ArrayList;
import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DataViewHolder> {
    private AppCompatTextView tvName, tvBudget, tvStartDate, tvEndDate;
    private AppCompatImageView ivMore;
    private Context context;
    private List<TripModal> list;

    private ClickListener clickListener;

    public DetailsAdapter(Context context, List<TripModal> list, ClickListener clickListener) {
        this.context = context;
        this.list = list;
        this.clickListener = clickListener;
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
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            tvName = itemView.findViewById(R.id.tvName);
            tvBudget = itemView.findViewById(R.id.tvBudget);
            tvStartDate = itemView.findViewById(R.id.tvStartDate);
            tvEndDate = itemView.findViewById(R.id.tvEndDate);
            ivMore= itemView.findViewById(R.id.ivMore);

        }

        private void bindView(TripModal tripModal, int position) {
            tvName.setText(tripModal.name);
            tvBudget.setText(tripModal.budget);
            tvStartDate.setText(tripModal.startDate);
            tvEndDate.setText(tripModal.endDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(tripModal, position,"item");
                }
            });

            ivMore.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(context, ivMore);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        clickListener.onClick(tripModal, position,"delete");
                        Toast.makeText(context,menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popupMenu.show();

            });

        }
    }
}
