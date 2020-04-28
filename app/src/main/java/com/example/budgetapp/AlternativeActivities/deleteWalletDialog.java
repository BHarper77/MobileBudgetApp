package com.example.budgetapp.AlternativeActivities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.budgetapp.DataClasses.walletClass;
import com.example.budgetapp.R;

public class deleteWalletDialog extends DialogFragment
{
    private walletClass wallet;

    public deleteWalletDialog(walletClass wallet)
    {
        this.wallet = wallet;
    }

    //Interface for clickListeners
    public interface DialogListener
    {
        public void onDialogPositiveClick(DialogFragment dialogFragment, walletClass wallet);
        public void onDialogNegativeClick(DialogFragment dialogFragment);
    }

    DialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Delete " + wallet.getWalletName())
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        listener.onDialogPositiveClick(deleteWalletDialog.this, wallet);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        listener.onDialogNegativeClick(deleteWalletDialog.this);
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);

        try {
            listener = (deleteWalletDialog.DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement DialogListener");
        }
    }
}
