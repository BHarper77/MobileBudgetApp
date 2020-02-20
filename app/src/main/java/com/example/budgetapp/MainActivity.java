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
    //Creating walletList
    List<Wallet> walletList = new ArrayList<Wallet>();

    void main()
    {
        if (walletList.size() > 0)
        {
            //Display default wallet if one has been saved, retrieve walletList from local storage

            for (int i = 0; i < (walletList.size() + 1); i++)
            {
                //Display extra menu for every extra wallet
            }
        }
        else
        {
            //Display normal intro page, with Create Wallet button
        }
    }

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

            walletNameText.setText("Current Wallet: " + resultWallet.getWalletName());
            walletBalanceText.setText(Integer.toString(resultWallet.getBalance()));

            //Toast.makeText(this, resultWallet.getWalletName(), Toast.LENGTH_SHORT).show();
        }
    }

    void addToWalletList(Wallet currentWallet) {
        int size = walletList.size();

        walletList.add(size, currentWallet);
    }
}
