package com.example.budgetapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WalletHome extends AppCompatActivity implements View.OnClickListener, transactionDialog.DialogListener
{
    private static final String TAG = "WalletHome";
    
    WalletClass wallet;

    List<WalletClass.Transactions> transactionsList = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_home);

        Bundle data = getIntent().getExtras();
        wallet = (WalletClass) data.getSerializable("wallet");

        recyclerView = findViewById(R.id.transactionRecyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        TextView balance = findViewById(R.id.balance);
        balance.setText("£" + wallet.getBalance());

        TextView name = findViewById(R.id.name);
        name.setText(wallet.getWalletName());
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        mAdapter = new recyclerViewAdapterWallet(transactionsList);
        recyclerView.setAdapter(mAdapter);
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
        new transactionDialog(wallet, "Withdraw").show(getSupportFragmentManager(), "Withdraw");
    }

    private void deposit()
    {
        new transactionDialog(wallet, "Deposit").show(getSupportFragmentManager(), "Deposit");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment, Bundle bundle)
    {
        WalletClass.Transactions transaction = new WalletClass.Transactions(
                bundle.getString("transactionType"),
                bundle.getDouble("amount"),
                bundle.getBoolean("recurring"),
                bundle.getString("reference")
        );

        //TODO: Error: Attempt to invoke interface method. "Problems with adding a transaction to the transaction list in the wallet class". Might need help in next weeks lab
        wallet.addTransaction(transaction);

        transactionsList.add(transaction);

        double newBalance = wallet.getBalance() - bundle.getDouble("amount");

        wallet.setBalance(newBalance);
        TextView balance = findViewById(R.id.balance);
        balance.setText("£" + newBalance);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment)
    {
        Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
    }
}
