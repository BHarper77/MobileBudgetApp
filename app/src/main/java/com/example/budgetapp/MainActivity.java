package com.example.budgetapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

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

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;

    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

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

        //Checking if user has wallets
        if (walletList.isEmpty())
        {
            setContentView(R.layout.default_home);
        }
        else
        {
            setContentView(R.layout.activity_main);
            loadFragments();
        }

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.createWallet).setOnClickListener(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        saveFile();
    }

    public void loadFragments()
    {
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < walletList.size(); i++)
        {
            WalletClass object = walletList.get(i);

            viewPagerAdapter.addFragment(new fragment().newInstance(object), object.getWalletName());
            viewPagerAdapter.notifyDataSetChanged();
            viewPager.setAdapter(viewPagerAdapter);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.createWallet:
                Intent intent = new Intent(MainActivity.this, createWalletActivity.class);
                startActivityForResult(intent, 1);
                break;

            case R.id.currentFragment:
                Toast.makeText(getApplicationContext(), "" + getVisibleFragment(), Toast.LENGTH_LONG).show();
                break;
        }
    }



    public int getVisibleFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();

        //return fragments.size();

        return viewPagerAdapter.fragmentList.size();

        /*for (int i = 0; i < fragments.size(); i++)
        {
            if (fragments.get(i).isVisible() && fragments != null)
            {
                return i;
            }
        }

        return -1;*/
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

    public void saveFile()
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

    public void loadFile()
    {
        String filename = "userSaveFile";

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
}
