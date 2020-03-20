package com.example.budgetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    List<WalletClass> walletList = new ArrayList<WalletClass>();

    private FirebaseAuth mAuth;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.createWallet).setOnClickListener(this);
        findViewById(R.id.saveButton).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        if (currentUser == null)
        {
            Intent intent = new Intent(MainActivity.this, loginActivity.class);
            startActivity(intent);

            finish();
        }

        //load();

        loadUI();
    }

    @Override
    protected void onDestroy()
    {
        save();

        super.onDestroy();
    }

    public void loadUI()
    {
        Toast.makeText(getApplicationContext(), "Hello " + currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();

        //Toast.makeText(getApplicationContext(), "Amount of wallets: " + walletList.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.createWallet:
                Intent intent = new Intent(MainActivity.this, createWalletActivity.class);
                startActivityForResult(intent, 1);

            case R.id.saveButton:
                save();
                getDir();
        }
    }

    //Retrieving new created wallet from createWallet activity
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        TextView walletNameText = findViewById(R.id.walletName);
        TextView walletBalanceText = findViewById(R.id.walletBalance);

        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            //Retriving bundle and extracting data
            Bundle data = intent.getExtras();
            WalletClass resultWallet = (WalletClass)data.getSerializable("walletClass");

            walletList.add(resultWallet);

            walletNameText.setText("Current Wallet: " + resultWallet.getWalletName());
            walletBalanceText.setText(Integer.toString(resultWallet.getBalance()));
        }
    }

    public void save()
    {
        String filename = "userSaveFile";

        Gson gson = new Gson();
        String data = gson.toJson(walletList);

        Log.e("walletListJSon", data);

        FileOutputStream fos = null;

        try {
            fos = openFileOutput(filename, Context.MODE_PRIVATE);

            fos.write(data.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getDir()
    {
        String path = getApplicationContext().getFilesDir().getPath();

        File file = new File(path + "fileName");

        Toast.makeText(getApplicationContext(), "path: " + file, Toast.LENGTH_LONG).show();
    }

    public void load()
    {
        String filename = "userSaveFile";

        try {
            FileInputStream fis = openFileInput(filename);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            StringBuilder data = new StringBuilder();
            String line = null;

            line = reader.readLine();

            while (line != null)
            {
                data.append(line).append("\n");
            }

            reader.close();
            fis.close();

            Type walletListType = new TypeToken<ArrayList<WalletClass>>(){}.getType();
            walletList = new Gson().fromJson(data.toString(), walletListType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
