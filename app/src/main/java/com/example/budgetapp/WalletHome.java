package com.example.budgetapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WalletHome extends AppCompatActivity implements View.OnClickListener, withdrawDialog.DialogListener
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
        new withdrawDialog().show(getSupportFragmentManager(), "WithdrawDialog");
    }

    private void deposit()
    {

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment, Bundle bundle)
    {
        //TODO: Refactor dialog and transactions code
        String type = "withdraw";
        int id = 1;

        double amount = bundle.getDouble("amount");
        boolean recurring = bundle.getBoolean("recurring");
        String reference = bundle.getString("reference");

        WalletClass.Transactions transaction = new WalletClass.Transactions(id, type, amount, recurring, reference);

        wallet.transactions.add(transaction);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment)
    {

    }
}
