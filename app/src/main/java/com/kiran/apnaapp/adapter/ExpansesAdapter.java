package com.kiran.apnaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.kiran.apnaapp.R;
import com.kiran.apnaapp.interfaces.InterfaceClickCallback;
import com.kiran.apnaapp.modals.ExpensesModal;

import java.util.List;

public class ExpansesAdapter extends RecyclerView.Adapter<ExpansesAdapter.DataViewHolder> {

    private final Context context;
    private final List<ExpensesModal> list;

    private final InterfaceClickCallback interfaceClickCallback;

    public ExpansesAdapter(Context context, List<ExpensesModal> list, InterfaceClickCallback interfaceClickCallback) {
        this.context = context;
        this.list = list;
        this.interfaceClickCallback = interfaceClickCallback;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false);
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
        private AppCompatTextView tvExpenseName, tvAmount;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            tvExpenseName = itemView.findViewById(R.id.tvExpenseName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }

        private void bindView(ExpensesModal tripModal, int position) {

            tvExpenseName.setText(tripModal.note);
            tvAmount.setText("" + Float.parseFloat(tripModal.amount));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interfaceClickCallback.clickCallBack(tripModal, position, "item");
                }
            });
        }
    }
}