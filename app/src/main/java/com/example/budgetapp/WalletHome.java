package com.example.budgetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class WalletHome extends AppCompatActivity
{
    WalletClass wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_home);

        Bundle data = getIntent().getExtras();
        wallet = (WalletClass) data.getSerializable("wallet");

        TextView walletName = findViewById(R.id.walletHomeName);
        walletName.setText(wallet.getWalletName());

        //TODO: Design walletHome layout
    }
}
