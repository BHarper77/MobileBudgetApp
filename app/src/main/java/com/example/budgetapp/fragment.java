package com.example.budgetapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class fragment extends Fragment
{
    public fragment()
    {
        // Required empty public constructor
    }

    public fragment newInstance(WalletClass wallet)
    {
        fragment frag = new fragment();
        Bundle bundle = new Bundle();

        bundle.putString("walletName", wallet.getWalletName());
        bundle.putInt("walletBalance", wallet.getBalance());

        frag.setArguments(bundle);
        return frag;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        Toast.makeText(getContext(), "" + bundle.getString("walletName"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_layout, container, false);

        Bundle bundle = getArguments();

        TextView walletName = view.findViewById(R.id.walletName);
        walletName.setText(bundle.getString("walletName"));

        return view;
    }
}
