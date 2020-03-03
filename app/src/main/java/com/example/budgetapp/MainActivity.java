package com.example.budgetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    WalletList walletList = new WalletList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createWallet  = findViewById(R.id.createWallet);
        createWallet.setOnClickListener(this);

        //Read from save file if one exists, check if a walletList exists, if not create an instance, if one exists decode the JSON and keep that list object, display all wallets

        //region ReadingFile
        /*String filename = "myFile";
        FileInputStream fis = null;

        try
        {
            fis = openFileInput(filename);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

        StringBuilder data = new StringBuilder();
        String line = null;

        try
        {
            line = reader.readLine();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        while (line != null)
        {
            //Lines in one string with line break after every row
            data.append(line).append("\n");
        };

        data.toString();

        try
        {
            reader.close();
            fis.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }*/
        //endregion
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Encode wallet list into JSON object and save to file
        //Gson gson = new Gson();

        //region CreatingSaveFile
        /*String filename = "myFile";
        String data = gson.toJson(walletList);

        try
        {
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);

            fos.write(data.getBytes());
            fos.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }*/
        //endregion
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, createWalletActivity.class);
        startActivityForResult(intent, 1);
    }

    //Retrieving new created wallet from createWallet activity
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        TextView walletNameText = findViewById(R.id.walletName);
        TextView walletBalanceText = findViewById(R.id.walletBalance);

        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            //Retriving bundle and extracting data
            Bundle data = intent.getExtras();
            WalletClass resultWallet = (WalletClass)data.getSerializable("walletClass");

            walletList.addToList(resultWallet);

            walletNameText.setText("Current Wallet: " + resultWallet.getWalletName());
            walletBalanceText.setText(Integer.toString(resultWallet.getBalance()));
        }
    }
}
