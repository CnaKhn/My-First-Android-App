package com.cnakhn.faradarscompletion.DataModel.Contract;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnakhn.faradarscompletion.R;

import java.util.ArrayList;

public class ContractRecyclerViewAdapter extends RecyclerView.Adapter<ContractRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<ContractIncomingNumber> incomingNumbers;

    public ContractRecyclerViewAdapter(ArrayList<ContractIncomingNumber> incomingNumbers) {
        incomingNumbers = new ArrayList<>();
        this.incomingNumbers = incomingNumbers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_incoming_numbers, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvID.setText(Integer.toString(incomingNumbers.get(position).getId()));
        holder.tvNumber.setText(incomingNumbers.get(position).getNumber());

    }

    @Override
    public int getItemCount() {
        return incomingNumbers.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvID, tvNumber;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tv_incoming_numbers_id);
            tvNumber = itemView.findViewById(R.id.tv_incoming_numbers_number);
        }
    }
}
