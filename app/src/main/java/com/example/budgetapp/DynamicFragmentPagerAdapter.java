package com.example.budgetapp;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class DynamicFragmentPagerAdapter extends PagerAdapter
{
    private static final String TAG = "DynamicFragmentPagerAdapter";

    private final FragmentManager fragmentManager;

    public static abstract class FragmentIdentifier implements Parcelable
    {
        private final String fragmentTag;
        private final Bundle args;

        public FragmentIdentifier(@NonNull String fragmentTag, @Nullable Bundle args)
        {
            this.fragmentTag = fragmentTag;
            this.args = args;
        }

        protected FragmentIdentifier(Parcel in)
        {
            fragmentTag = in.readString();
            args = in.readBundle(getClass().getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel dest, int flags)
        {
            dest.writeString(fragmentTag);
            dest.writeBundle(args);
        }

        protected final Fragment newFragment()
        {
            Fragment fragment = createFragment();
            Bundle oldArgs = fragment.getArguments();
            Bundle newArgs = new Bundle();

            if(oldArgs != null)
            {
                newArgs.putAll(oldArgs);
            }

            if(args != null)
            {
                newArgs.putAll(args);
            }

            fragment.setArguments(newArgs);
            return fragment;
        }

        protected abstract Fragment createFragment();
    }

    private ArrayList<FragmentIdentifier> fragmentIdentifiers = new ArrayList<>();

    private FragmentTransaction currentTransaction = null;

    private Fragment currentPrimaryItem = null;

    public DynamicFragmentPagerAdapter(FragmentManager fragmentManager)
    {
        this.fragmentManager = fragmentManager;
    }

    private int findIndexIfAdded(FragmentIdentifier fragmentIdentifier)
    {
        for (int i = 0, size = fragmentIdentifiers.size(); i < size; i++)
        {
            FragmentIdentifier identifier = fragmentIdentifiers.get(i);
            if (identifier.fragmentTag.equals(fragmentIdentifier.fragmentTag))
            {
                return i;
            }
        }
        return -1;
    }

    public void addFragment(FragmentIdentifier fragmentIdentifier)
    {
        if (findIndexIfAdded(fragmentIdentifier) < 0)
        {
            fragmentIdentifiers.add(fragmentIdentifier);
            notifyDataSetChanged();
        }
    }

    public void removeFragment(FragmentIdentifier fragmentIdentifier)
    {
        int index = findIndexIfAdded(fragmentIdentifier);
        if (index >= 0) {
            fragmentIdentifiers.remove(index);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount()
    {
        return fragmentIdentifiers.size();
    }

    @Override
    public void startUpdate(@NonNull ViewGroup container)
    {
        if (container.getId() == View.NO_ID) {
            throw new IllegalStateException("ViewPager with adapter " + this
                    + " requires a view id");
        }
    }

    @SuppressWarnings("ReferenceEquality")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        if (currentTransaction == null)
        {
            currentTransaction = fragmentManager.beginTransaction();
        }
        final FragmentIdentifier fragmentIdentifier = fragmentIdentifiers.get(position);
        // Do we already have this fragment?
        final String name = fragmentIdentifier.fragmentTag;
        Fragment fragment = fragmentManager.findFragmentByTag(name);
        if (fragment != null)
        {
            currentTransaction.attach(fragment);
        } else {
            fragment = fragmentIdentifier.newFragment();
            currentTransaction.add(container.getId(), fragment, fragmentIdentifier.fragmentTag);
        }
        if (fragment != currentPrimaryItem)
        {
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        if (currentTransaction == null)
        {
            currentTransaction = fragmentManager.beginTransaction();
        }
        currentTransaction.detach((Fragment) object);
    }

    @SuppressWarnings("ReferenceEquality")
    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        Fragment fragment = (Fragment) object;
        if (fragment != currentPrimaryItem)
        {
            if (currentPrimaryItem != null)
            {
                currentPrimaryItem.setMenuVisibility(false);
                currentPrimaryItem.setUserVisibleHint(false);
            }
            fragment.setMenuVisibility(true);
            fragment.setUserVisibleHint(true);
            currentPrimaryItem = fragment;
        }
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container)
    {
        if (currentTransaction != null)
        {
            currentTransaction.commitNowAllowingStateLoss();
            currentTransaction = null;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return ((Fragment) object).getView() == view;
    }

    @Override
    public Parcelable saveState()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("fragmentIdentifiers", fragmentIdentifiers);
        return bundle;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader)
    {
        Bundle bundle = ((Bundle)state);
        bundle.setClassLoader(loader);
        fragmentIdentifiers = bundle.getParcelableArrayList("fragmentIdentifiers");
    }
}