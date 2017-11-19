package com.thomas.foodleftovers.async_tasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

/**
 * Charge les résultats
 */
public class LoadResults extends AsyncTaskLoader<ArrayList<String>>
{
    ArrayList<String> mList;

    public LoadResults(Context context)
    {
        super(context);
    }

    @Override
    protected void onStartLoading()
    {
        if(mList != null)
            deliverResult(mList);
        else
            forceLoad();
    }

    @Override
    public ArrayList<String> loadInBackground()
    {
        mList = new ArrayList<>();
        mList.add("Result 1");
        mList.add("Result 2");
        mList.add("Result 3");

        return mList;
    }
}
