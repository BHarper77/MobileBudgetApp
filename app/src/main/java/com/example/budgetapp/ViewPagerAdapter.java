package com.example.budgetapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
    public ViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        DemoFragment fragment = new DemoFragment();
        position = position + 1;

        Bundle bundle = new Bundle();
        bundle.putString("message", "Fragment: " + position);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount()
    {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        position = position + 1;

        return "Fragment: " + position;
    }
}
