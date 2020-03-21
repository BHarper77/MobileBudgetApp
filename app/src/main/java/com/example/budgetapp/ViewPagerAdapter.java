package com.example.budgetapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
    List<fragment> fragmentList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public fragment getItem(int position)
    {
        /*fragment fragment = new fragment();
        position = position + 1;

        Bundle bundle = new Bundle();
        bundle.putString("message", "Fragment: " + position);

        fragment.setArguments(bundle);

        return fragment;*/

        return fragmentList.get(position);
    }

    public void addFragment (fragment fragment, WalletClass wallet)
    {
        fragmentList.add(fragment);
    }

    public void removeFragment (fragment fragment)
    {
        fragmentList.remove(fragment);

        //fragmentList.remove(getItemPosition(fragment));
    }

    @Override
    public int getCount()
    {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        position = position + 1;

        return "Fragment: " + position;
    }
}
