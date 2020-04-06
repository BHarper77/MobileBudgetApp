package com.example.budgetapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class recyclerViewAdapterWallet extends RecyclerView.Adapter<recyclerViewAdapterWallet.MyViewHolder>
{
    //TODO: Implement listener to display notes on transactions, if transactions have any notes
    private List<walletClass.transactions> adapterDataset;
    private OnTransactionListener mOnWalletListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView transactionType;
        public TextView amount;
        public TextView dateTime;
        public TextView reference;
        OnTransactionListener onTransactionListener;

        public MyViewHolder (View view, @Nullable OnTransactionListener onTransactionListener)
        {
            super(view);

            transactionType = view.findViewById(R.id.transactionType);
            amount = view.findViewById(R.id.amount);
            dateTime = view.findViewById(R.id.dateTime);
            reference = view.findViewById(R.id.reference);

            //Setting listener to each ViewHolder that is created
            this.onTransactionListener = onTransactionListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            onTransactionListener.onTransactionClick(getAdapterPosition());
        }
    }

    //Listener for recycler view, when transaction is clicked
    public interface OnTransactionListener
    {
        void onTransactionClick(int position);
    }

    public recyclerViewAdapterWallet (List<walletClass.transactions> myDataset, OnTransactionListener onTransactionListener)
    {
        adapterDataset = myDataset;
        this.mOnWalletListener = onTransactionListener;
    }

    @NonNull
    @Override
    public recyclerViewAdapterWallet.MyViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType)
    {
        //Create new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_recyclerview_layout, parent, false);

        return new MyViewHolder(view, mOnWalletListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        //Casting payloads to walletClass list
        holder.transactionType.setText(adapterDataset.get(position).getType());
        holder.amount.setText("£" + adapterDataset.get(position).getAmount());
        holder.dateTime.setText(adapterDataset.get(position).getDateTime());
        holder.reference.setText(adapterDataset.get(position).getReference());
    }

    @Override
    public int getItemCount()
    {
        return adapterDataset.size();
    }
}
