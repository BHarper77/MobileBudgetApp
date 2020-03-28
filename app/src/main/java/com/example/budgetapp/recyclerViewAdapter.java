package com.example.budgetapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.MyViewHolder>
{
    private List<WalletClass> adapterDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView walletNameView;
        public TextView walletBalanceView;

        public MyViewHolder (TextView name)
        {
            super(name);

            walletNameView = name;
        }
    }

    public recyclerViewAdapter (List<WalletClass> myDataset)
    {
        adapterDataset = myDataset;
    }

    @NonNull
    @Override
    public recyclerViewAdapter.MyViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType)
    {
        //Create new view
        TextView name = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(name);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        //Casting payloads to WalletClass list
        holder.walletNameView.setText(adapterDataset.get(position).getWalletName());
        holder.walletBalanceView.setText(adapterDataset.get(position).getBalance());
    }

    @Override
    public int getItemCount()
    {
        return adapterDataset.size();
    }
}
