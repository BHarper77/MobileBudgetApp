package com.example.budgetapp.AlternativeActivities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.budgetapp.DataClasses.walletClass;
import com.example.budgetapp.R;

import java.util.Date;

public class transactionDialog extends DialogFragment
{
    private static final String TAG = "transactionDialog";

    private walletClass wallet;
    private String transactionType;
    private double amount;

    public transactionDialog(walletClass wallet, String transactionType)
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
        //Use the Builder class for dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_layout, null);

        builder.setView(view)
        .setTitle(transactionType)
        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                if (validate(view))
                {
                    Bundle bundle = createBundle(view);

                    listener.onDialogPositiveClick(transactionDialog.this, bundle);
                }
            }
        })

        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
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

    public Bundle createBundle(View view)
    {
        EditText Amount = view.findViewById(R.id.amount);
        double amount = Double.parseDouble(Amount.getText().toString());

        EditText Reference = view.findViewById(R.id.reference);
        String reference = Reference.getText().toString();

        CheckBox checkBox = view.findViewById(R.id.recurringCheck);

        String date = java.text.DateFormat.getDateInstance().format(new Date());
        String time = java.text.DateFormat.getTimeInstance().format(new Date());

        Bundle bundle = new Bundle();
        bundle.putString("transactionType", transactionType);
        bundle.putDouble("amount", amount);
        bundle.putString("reference", reference);
        bundle.putString("date", date);
        bundle.putString("time", time);

        //TODO: Implement recurring functionality
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

    public boolean validate(View view)
    {
        EditText amountT = view.findViewById(R.id.amount);
        String amount = amountT.getText().toString();

        if (TextUtils.isEmpty(amount))
        {
            Toast.makeText(getContext(), "Please fill out all required fields", Toast.LENGTH_SHORT).show();

            return false;
        }

        if (transactionType == "Withdraw")
        {
            double amountD = Double.parseDouble(amount);
            if (amountD > wallet.getBalance())
            {
                Toast.makeText(getContext(), "Withdrawal amount is too large for selected wallet", Toast.LENGTH_SHORT).show();

                return false;
            }
        }

        return true;
    }
}
