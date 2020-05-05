package com.example.budgetapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.budgetapp.DataClasses.walletClass;
import com.example.budgetapp.R;
import com.example.budgetapp.AlternativeActivities.deleteWalletDialog;
import com.example.budgetapp.AlternativeActivities.recyclerViewAdapter;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

public class MainActivity extends AppCompatActivity implements recyclerViewAdapter.OnWalletListener, deleteWalletDialog.DialogListener, PopupMenu.OnMenuItemClickListener
{
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    List<walletClass> walletList = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Check user logged in
        if (currentUser == null)
        {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

            finish();
        }

        TextView helloUser = findViewById(R.id.user);
        helloUser.setText(currentUser.getDisplayName());

        loadFile();

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        updateUI();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        updateUI();
    }

    void updateUI()
    {
        mAdapter = new recyclerViewAdapter(walletList, this);
        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        double total = 0;

        //Calculate total balance
        for (int i = 0; i < walletList.size(); i++)
        {
            walletClass object = walletList.get(i);

            total = total + object.getBalance();
        }

        TextView totalBalance = findViewById(R.id.totalBalance);
        totalBalance.setText("£" + total);
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
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);

        if (intent == null)
        {
            Log.d(TAG, "onActivityResult: intent is null");
        }
        
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            //Retriving bundle and extracting data
            Bundle data = intent.getExtras();
            walletClass resultWallet = (walletClass) data.getSerializable("walletClass");

            walletList.add(resultWallet);

            updateUI();
        }

        if (requestCode == 2 && resultCode == RESULT_OK)
        {
            //Retrieving new wallet and transactions from WalletHomeActivity
            Bundle data = intent.getExtras();
            walletClass updatedWallet = (walletClass) data.getSerializable("updatedWallet");
            int index = data.getInt("index");

            try {
                walletList.set(index, updatedWallet);
            }
            catch (Exception e)
            {
                Log.d(TAG, "onActivityResult: " + e);
            }

            updateUI();
        }
    }

    public void showPopup(View view)
    {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.options_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.createWallet:
                Intent intent = new Intent(MainActivity.this, CreateWalletActivity.class);
                startActivityForResult(intent, 1);
                return true;

            case R.id.logOut:
                signOut();
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onWalletClick(int position)
    {
        walletClass wallet = walletList.get(position);
        int index = walletList.indexOf(wallet);

        Intent intent = new Intent(MainActivity.this, WalletHomeActivity.class);
        intent.putExtra("wallet", walletList.get(position));
        intent.putExtra("index",index);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onWalletLongClick(int position)
    {
        //TODO: Look at changing to popup instead of dialog
        new deleteWalletDialog(walletList.get(position)).show(getSupportFragmentManager(), null);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment, walletClass wallet)
    {
        Toast.makeText(this, wallet.getWalletName() + " deleted", Toast.LENGTH_SHORT).show();

        walletList.remove(wallet);
        wallet = null;

        updateUI();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment)
    {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
    }

    public void loadFile()
    {
        final Handler handler = new Handler();

        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
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

                    //Create new type for GSON deserialization
                    Type walletListType = new TypeToken<ArrayList<walletClass>>(){}.getType();
                    walletList = new Gson().fromJson(data.toString(), walletListType);

                    Log.d("Load File", "" + data);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Background thread to update UI after load file thread is finished
                //Handler sends new Runnable thread to parent thread queue
                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        updateUI();
                    }
                });
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void saveFile()
    {
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
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
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        saveFile();
    }
}