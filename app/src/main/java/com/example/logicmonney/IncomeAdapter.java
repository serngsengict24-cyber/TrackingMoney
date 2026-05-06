package com.example.logicmonney;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder> {

    List<IncomeModel> incomeList;

    public IncomeAdapter(List<IncomeModel> incomeList) {
        this.incomeList = incomeList;
    }
    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemincome, parent, false);
        return new IncomeViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull IncomeViewHolder holder, int position) {
        IncomeModel model = incomeList.get(position);
        holder.tvIncome.setText(model.getAmount());
        holder.tvDate.setText(model.getDate());
        holder.tvComment.setText(model.getNote());
    }

    @Override
    public int getItemCount() {
        return incomeList.size();
    }

    static class IncomeViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvIncome, tvDate, tvComment;

        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvIncome = itemView.findViewById(R.id.tvIncome);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvComment = itemView.findViewById(R.id.tvComment);
        }
    }
}
