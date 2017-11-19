package com.thomas.foodleftovers.view_pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
    private ArrayList<Fragment> mList = null;

    public ViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
        mList = new ArrayList<>();
    }

    public void add(Fragment fragment)
    {
        mList.add(fragment);
    }

    @Override
    public Fragment getItem(int position)
    {
        return mList.get(position);
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    public int indexOfFragment(String fragmentClass)
    {
        for (Fragment fragment : mList) {
            if (fragmentClass.equalsIgnoreCase(fragment.getClass().getName())) {
                return mList.indexOf(fragment);
            }
        }

        return -1;
    }
}
