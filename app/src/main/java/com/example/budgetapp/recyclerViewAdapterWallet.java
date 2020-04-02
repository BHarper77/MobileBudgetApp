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
    private List<WalletClass.Transactions> adapterDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView transactionType;
        public TextView amount;

        public MyViewHolder (View view)
        {
            super(view);

            transactionType = view.findViewById(R.id.transactionType);
            amount = view.findViewById(R.id.amount);
        }
    }

    public recyclerViewAdapterWallet (List<WalletClass.Transactions> myDataset)
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
        //Casting payloads to WalletClass list
        holder.transactionType.setText(adapterDataset.get(position).getType());

        holder.amount.setText("£" + adapterDataset.get(position).getAmount());
    }

    @Override
    public int getItemCount()
    {
        return adapterDataset.size();
    }
}
