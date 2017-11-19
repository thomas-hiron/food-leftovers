package com.thomas.foodleftovers.view_pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter
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

    /**
     * Supprime le dernier fragment
     */
    public void removeLast()
    {
        /* On supprime le dernier s'il y a plus d'un élément */
        if(mList.size() > 1)
            mList.remove(getCount() - 1);
    }
}
