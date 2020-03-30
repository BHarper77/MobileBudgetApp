package com.example.budgetapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class withdrawDialog extends DialogFragment
{
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

        builder.setView(getLayoutInflater().inflate(R.layout.dialog_layout, null));

        builder.setTitle("Withdraw")
                .setPositiveButton("Submit", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        if (validate())
                        {
                            Bundle bundle = createBundle();

                            listener.onDialogPositiveClick(withdrawDialog.this, bundle);
                        }
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        listener.onDialogNegativeClick(withdrawDialog.this);
                    }
                });

        // Create the AlertDialog object and return it
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
        EditText amount = getView().findViewById(R.id.amount);
        String amountCheck = amount.getText().toString();

        EditText reference = getView().findViewById(R.id.reference);
        String referenceCheck = reference.getText().toString();

        if (TextUtils.isEmpty(amountCheck) || TextUtils.isEmpty(referenceCheck))
        {
            return false;
        }

        Toast.makeText(getContext(), "Please fill out all required fields", Toast.LENGTH_SHORT).show();

        return false;
    }
}
