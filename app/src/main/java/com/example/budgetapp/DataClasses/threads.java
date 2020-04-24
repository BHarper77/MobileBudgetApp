package com.example.budgetapp.DataClasses;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budgetapp.DataClasses.walletClass;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class threads extends AppCompatActivity implements Runnable
{
    //TODO: Clear up how threads are supposed to work, is this right?
    private int time;
    private List<walletClass> walletList;
    private String type;
    private FirebaseUser currentUser;

    public threads(int time, List<walletClass> walletList, String type, FirebaseUser currentUser)
    {
        this.time = time;
        this.walletList = walletList;
        this.type = type;
        this.currentUser = currentUser;
    }

    @Override
    public void run()
    {
        if (type == "Save")
        {
            saveFile();
        }
        else
        {
            loadFile();
        }
    }

    public void saveFile()
    {
        String filename = currentUser.getUid();

        Gson gson = new Gson();
        String data = gson.toJson(walletList);

        Log.d("Save File", data);

        FileOutputStream fos = null;

        try {
            fos = openFileOutput(filename, Context.MODE_PRIVATE);

            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<walletClass> loadFile()
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

            Type walletListType = new TypeToken<ArrayList<walletClass>>(){}.getType();
            walletList = new Gson().fromJson(data.toString(), walletListType);

            Log.d("Load File", "" + data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return walletList;
    }
}
