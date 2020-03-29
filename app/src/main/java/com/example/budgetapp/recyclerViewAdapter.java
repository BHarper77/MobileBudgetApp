package com.example.budgetapp;

import android.view.LayoutInflater;
import android.view.View;
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

        public MyViewHolder (View view)
        {
            super(view);

            walletNameView = view.findViewById(R.id.walletName);
            walletBalanceView = view.findViewById(R.id.walletBalance);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        //Casting payloads to WalletClass list
        holder.walletNameView.setText(adapterDataset.get(position).getWalletName());

        holder.walletBalanceView.setText("" + adapterDataset.get(position).getBalance());
    }

    @Override
    public int getItemCount()
    {
        return adapterDataset.size();
    }
}
