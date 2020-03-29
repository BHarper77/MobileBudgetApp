package com.example.budgetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WalletHome extends AppCompatActivity implements View.OnClickListener
{
    WalletClass wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_home);

        Bundle data = getIntent().getExtras();
        wallet = (WalletClass) data.getSerializable("wallet");

        TextView balance = findViewById(R.id.balance);
        balance.setText("£" + wallet.getBalance());

        TextView name = findViewById(R.id.name);
        name.setText(wallet.getWalletName());

        //TODO: Design walletHome layout
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.withdraw:
                withdraw();
                break;

            case R.id.deposit:
                deposit();
                break;
        }
    }

    private void withdraw()
    {
        //TODO: Design dialogue box for withdrawing and depositing
    }

    private void deposit()
    {

    }
}
