package com.example.budgetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createWallet  = findViewById(R.id.createWallet);
        createWallet.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, createWallet.class);
        startActivityForResult(intent, 1);
    }

    //Retrieving new created wallet from createWallet activity
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        TextView walletNameText = findViewById(R.id.walletName);
        TextView walletBalanceText = findViewById(R.id.walletBalance);

        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            //Retriving bundle and extracting data
            Bundle data = intent.getExtras();
            Wallet resultWallet = (Wallet)data.getSerializable("walletClass");

            //Adding wallet to walletList
            addToWalletList(resultWallet);

            walletNameText.setText(resultWallet.getWalletName());
            walletBalanceText.setText(Integer.toString(resultWallet.getBalance()));

            //Toast.makeText(this, resultWallet.getWalletName(), Toast.LENGTH_SHORT).show();
        }
    }

    void addToWalletList(Wallet currentWallet) {
        walletList walletList;
        walletList.walletsList.add()
    }
}
