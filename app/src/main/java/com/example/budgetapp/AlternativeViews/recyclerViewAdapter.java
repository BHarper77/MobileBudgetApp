package com.example.budgetapp.AlternativeViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetapp.DataClasses.walletClass;
import com.example.budgetapp.R;

import java.util.List;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.MyViewHolder>
{
    private List<walletClass> adapterDataset;
    private OnWalletListener mOnWalletListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {
        public TextView walletNameView;
        public TextView walletBalanceView;
        OnWalletListener onWalletListener;

        public MyViewHolder (View view, OnWalletListener onWalletListener)
        {
            super(view);

            walletNameView = view.findViewById(R.id.walletName);
            walletBalanceView = view.findViewById(R.id.walletBalance);

            //Setting listener to each ViewHolder that is created
            this.onWalletListener = onWalletListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            onWalletListener.onWalletClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view)
        {
            onWalletListener.onWalletLongClick(getAdapterPosition());
            return true;
        }
    }

    //Listener for recyclerView, when wallet is selected
    public interface OnWalletListener
    {
        void onWalletClick(int position);
        void onWalletLongClick(int position);
    }

    public recyclerViewAdapter (List<walletClass> myDataset, OnWalletListener onWalletListener)
    {
        adapterDataset = myDataset;
        this.mOnWalletListener = onWalletListener;
    }

    @NonNull
    @Override
    public recyclerViewAdapter.MyViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType)
    {
        //Create new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout, parent, false);

        return new MyViewHolder(view, mOnWalletListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        //Casting payloads to walletClass list
        holder.walletNameView.setText(adapterDataset.get(position).getWalletName());

        holder.walletBalanceView.setText("£" + adapterDataset.get(position).getBalance());
    }

    @Override
    public int getItemCount()
    {
        return adapterDataset.size();
    }
}
