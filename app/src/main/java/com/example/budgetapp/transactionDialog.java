package com.example.budgetapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class transactionDialog extends DialogFragment
{
    private static final String TAG = "transactionDialog";

    private WalletClass wallet;
    private String transactionType;
    private double amount;

    public transactionDialog(WalletClass wallet,String transactionType)
    {
        this.wallet = wallet;
        this.transactionType = transactionType;
    }

    //Interface for clickListeners
    public interface DialogListener
    {
        public void onDialogPositiveClick(DialogFragment dialogFragment, Bundle bundle);
        public void onDialogNegativeClick(DialogFragment dialogFragment);
    }

    DialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        //Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_layout, null))
        .setTitle(transactionType)
        .setPositiveButton("Submit", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                if (validate())
                {
                    Bundle bundle = createBundle();

                    listener.onDialogPositiveClick(transactionDialog.this, bundle);
                }
            }
        })

        .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                listener.onDialogNegativeClick(transactionDialog.this);
            }
        });

        //Create the AlertDialog object and return it
        return builder.create();
    }

    //Overriding onAttach to instantiate DialogListener
    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);

        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement DialogListener");
        }
    }

    public Bundle createBundle()
    {
        EditText Amount = getView().findViewById(R.id.amount);
        double amount = Double.parseDouble(Amount.getText().toString());

        EditText Reference = getView().findViewById(R.id.reference);
        String reference = Reference.toString();

        CheckBox checkBox = getView().findViewById(R.id.recurringCheck);

        Bundle bundle = new Bundle();
        bundle.putString("transactionType", transactionType);
        bundle.putDouble("amount", amount);
        bundle.putString("reference", reference);

        if (checkBox.isChecked())
        {
            bundle.putBoolean("recurring", true);
        }
        else
        {
            bundle.putBoolean("recurring", false);
        }

        return bundle;
    }

    public boolean validate()
    {
        String amount = getView().findViewById(R.id.amount).toString();

        if (TextUtils.isEmpty(amount))
        {
            Toast.makeText(getContext(), "Please fill out all required fields", Toast.LENGTH_SHORT).show();

            return false;
        }

        if (transactionType == "Withdraw")
        {
            if (Double.parseDouble(amount) > wallet.getBalance())
            {
                Toast.makeText(getContext(), "Withdrawal amount is too large for selected wallet", Toast.LENGTH_SHORT).show();

                return false;
            }
        }

        return false;
    }
}
