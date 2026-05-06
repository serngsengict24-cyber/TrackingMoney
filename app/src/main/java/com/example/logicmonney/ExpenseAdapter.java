package com.example.logicmonney;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    private final List<ExpenseModel> list;

    public ExpenseAdapter(List<ExpenseModel> list, DisplayExpense displayExpense) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemexpense, parent, false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExpenseModel model = list.get(position);
        holder.tvId.setText(String.valueOf(model.getId()));
        holder.tvExpense.setText(model.getAmount());
        holder.tvDate.setText(model.getDate());
        holder.tvComment.setText(model.getNote());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemLongClick {
        void onItemLongClicked(int expenseId);
    }

    // ================= VIEW HOLDER =================
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvId, tvExpense, tvDate, tvComment;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvExpense = itemView.findViewById(R.id.tvExpense);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvComment = itemView.findViewById(R.id.tvComment);
        }
    }
}
