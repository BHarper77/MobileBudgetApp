package com.example.budgetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class createWalletActivity extends AppCompatActivity implements  View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);
        
        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        EditText walletName = findViewById(R.id.walletName);
        EditText walletBalance = findViewById(R.id.walletBalance);

        if (validate() == true)
        {
            int balance = Integer.parseInt(walletBalance.getText().toString());

            //Creating new Wallet with inputted values
            WalletClass wallet = new WalletClass(walletName.getText().toString(), balance);

            //Sending class data back to main activity
            Intent intent = new Intent();
            intent.putExtra("walletClass", wallet);
            this.setResult(RESULT_OK, intent);

            finish();
        }
    }

    //Validating wallet creation fields
    public boolean validate()
    {
        EditText walletName = findViewById(R.id.walletName);
        String walletNameString = walletName.getText().toString();

        EditText walletBalance = findViewById(R.id.walletBalance);
        String walletBalanceString = walletBalance.getText().toString();

        if (TextUtils.isEmpty(walletNameString))
        {
            walletName.setError("This field cannot be empty");

            return false;
        }
        else if (TextUtils.isEmpty(walletBalanceString))
        {
            walletBalance.setError("This field cannot be empty");

            return false;
        }
        else
        {
            return true;
        }
    }
}
