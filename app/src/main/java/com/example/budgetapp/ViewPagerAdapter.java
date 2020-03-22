package com.example.budgetapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
    List<fragment> fragmentList = new ArrayList<>();
    List<String> fragmentNameList = new ArrayList<>();
    List<Integer> fragmentBalanceList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public fragment getItem(int position)
    {
        return fragmentList.get(position);
    }

    public void addFragment (fragment fragment, WalletClass wallet)
    {
        fragmentList.add(fragment);
        fragmentNameList.add(wallet.getWalletName());
    }

    public void removeFragment (fragment fragment)
    {
        fragmentList.remove(fragment);
    }

    @Override
    public int getCount()
    {
        return fragmentList.size();
    }

    @Nullable
    public CharSequence getPageTitle(int position)
    {
        return fragmentNameList.get(position);
    }

    @Nullable
    public int getFragmentBalance (int position)
    {
        return fragmentBalanceList.get(position);
    }
}
