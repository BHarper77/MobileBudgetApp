package com.example.budgetapp;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
    List<fragment> fragmentList = new ArrayList<>();
    List<String> fragmentNameList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public fragment getItem(int position)
    {
        return fragmentList.get(position);
    }

    public void addFragment (fragment fragment, String name)
    {
        fragmentList.add(fragment);
        fragmentNameList.add(name);
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
}