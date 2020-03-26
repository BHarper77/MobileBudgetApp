package com.example.budgetapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private FirebaseAuth mAuth;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    List<WalletClass> walletList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Check user logged in
        if (currentUser == null)
        {
            Intent intent = new Intent(MainActivity.this, loginActivity.class);
            startActivity(intent);

            finish();
        }

        loadFile();

        mAuth = FirebaseAuth.getInstance();
    }

    public boolean checkWalletsList()
    {
        if (walletList.isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.createWallet:
                Intent intentTwo = new Intent(MainActivity.this, createWalletActivity.class);
                startActivityForResult(intentTwo, 1);
                break;

            case R.id.logOut:
                signOut();
                break;

        }
    }

    public void signOut()
    {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        Log.e("Log out", "user logged out");
                        Intent intent = new Intent(MainActivity.this, loginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    //Retrieving new created wallet from createWallet activity
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            //Retriving bundle and extracting data
            Bundle data = intent.getExtras();
            WalletClass resultWallet = (WalletClass) data.getSerializable("walletClass");

            walletList.add(resultWallet);
        }
    }

    public void loadFile()
    {
        String filename = currentUser.getUid();

        try {
            FileInputStream fis = openFileInput(filename);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            StringBuilder data = new StringBuilder();
            String line = reader.readLine();

            while (line != null)
            {
                data.append(line).append("\n");
                break;
            }

            reader.close();
            fis.close();

            Type walletListType = new TypeToken<ArrayList<WalletClass>>(){}.getType();
            walletList = new Gson().fromJson(data.toString(), walletListType);

            Log.e("load json", "" + data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFile()
    {
        String filename = currentUser.getUid();

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

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        saveFile();
    }
}
