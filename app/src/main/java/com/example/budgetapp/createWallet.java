package com.example.budgetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class createWallet extends AppCompatActivity implements  View.OnClickListener{

    //Creating instance of Wallet class
    Wallet wallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);
        
        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText walletName = findViewById(R.id.walletName);
        EditText walletBalance = findViewById(R.id.walletBalance);

        int balance = Integer.parseInt(walletBalance.getText().toString());

        //Creating new Wallet with inputted values
        wallet = new Wallet(walletName.getText().toString(), balance);
    }
}
