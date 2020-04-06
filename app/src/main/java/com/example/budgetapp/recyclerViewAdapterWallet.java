package com.example.budgetapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class recyclerViewAdapterWallet extends RecyclerView.Adapter<recyclerViewAdapterWallet.MyViewHolder>
{
    //TODO: Implement listener to display notes on transactions, if transactions have any notes
    private List<walletClass.transactions> adapterDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView transactionType;
        public TextView amount;
        public TextView dateTime;

        public MyViewHolder (View view)
        {
            super(view);

            transactionType = view.findViewById(R.id.transactionType);
            amount = view.findViewById(R.id.amount);
            dateTime = view.findViewById(R.id.dateTime);
        }
    }

    public recyclerViewAdapterWallet (List<walletClass.transactions> myDataset)
    {
        adapterDataset = myDataset;
    }

    @NonNull
    @Override
    public recyclerViewAdapterWallet.MyViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType)
    {
        //Create new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_recyclerview_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        //Casting payloads to walletClass list
        holder.transactionType.setText(adapterDataset.get(position).getType());

        holder.amount.setText("£" + adapterDataset.get(position).getAmount());

        holder.dateTime.setText(adapterDataset.get(position).getDateTime());
    }

    @Override
    public int getItemCount()
    {
        return adapterDataset.size();
    }
}
