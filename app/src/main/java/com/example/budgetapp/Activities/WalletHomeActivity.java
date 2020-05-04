package com.example.budgetapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetapp.DataClasses.walletClass;
import com.example.budgetapp.R;
import com.example.budgetapp.AlternativeActivities.recyclerViewAdapterWallet;
import com.example.budgetapp.AlternativeActivities.transactionDialog;

public class WalletHomeActivity extends AppCompatActivity implements View.OnClickListener, transactionDialog.DialogListener, recyclerViewAdapterWallet.OnTransactionListener
{
    private static final String TAG = "WalletHomeActivity";
    
    walletClass wallet;
    int index;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_home);

        Bundle data = getIntent().getExtras();
        wallet = (walletClass) data.getSerializable("wallet");
        index = data.getInt("index");

        recyclerView = findViewById(R.id.transactionRecyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        TextView balance = findViewById(R.id.totalBalance);
        balance.setText("£" + wallet.getBalance());

        TextView name = findViewById(R.id.name);
        name.setText(wallet.getWalletName());
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        updateUI();
    }

    public void updateUI()
    {
        //Passing wallet transaction list to recyclerView
        mAdapter = new recyclerViewAdapterWallet(wallet.getTransactions(), this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.withdraw:
                new transactionDialog(wallet, "Withdraw").show(getSupportFragmentManager(), "Withdraw");
                break;

            case R.id.deposit:
                new transactionDialog(wallet, "Deposit").show(getSupportFragmentManager(), "Deposit");
                break;
        }
    }

    @Override
    public void onTransactionClick(int position)
    {
        findViewById(R.id.reference).setVisibility(View.VISIBLE);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment, Bundle bundle)
    {
        walletClass.transactions transaction = new walletClass.transactions(
                bundle.getString("transactionType"),
                bundle.getDouble("amount"),
                bundle.getBoolean("recurring"),
                bundle.getString("reference"),
                bundle.getString("date"),
                bundle.getString("time")
        );

        Log.d(TAG, "onDialogPositiveClick: " + transaction.getAmount());

        wallet.addTransaction(transaction);

        double newBalance = 0;

        switch (bundle.getString("transactionType"))
        {
            case "Deposit":
                newBalance = wallet.getBalance() + bundle.getDouble("amount");
                wallet.setBalance(newBalance);
                break;

            case "Withdraw":
                newBalance = wallet.getBalance() - bundle.getDouble("amount");
                wallet.setBalance(newBalance);
                break;
        }


        wallet.setBalance(newBalance);
        TextView balance = findViewById(R.id.totalBalance);
        balance.setText("£" + newBalance);

        updateUI();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment)
    {
        Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed()
    {
        //Sending class data back to main activity
        Intent intent = new Intent();
        intent.putExtra("updatedWallet", wallet);
        intent.putExtra("index", index);
        this.setResult(RESULT_OK, intent);

        finish();
    }
}
