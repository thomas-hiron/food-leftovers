package com.thomas.foodleftovers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thomas.foodleftovers.R;
import com.thomas.foodleftovers.adapters.ReceipesAdapter;
import com.thomas.foodleftovers.async_tasks.LoadReceipes;
import com.thomas.foodleftovers.ui.ReceipesListView;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<String>>
{
    private static int LOADER_ID = 1;
    private ArrayList<String> mReceipes;
    private ReceipesListView mReceipesListView;
    private ReceipesAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        mReceipesListView = view.findViewById(R.id.results_list);
        mAdapter = (ReceipesAdapter) mReceipesListView.getAdapter();

        return view;
    }

    /**
     * Lance la recherche
     */
    public void search()
    {
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<ArrayList<String>> onCreateLoader(int id, Bundle args)
    {
        LoadReceipes loadReceipes = null;
        if (mReceipes == null)
        {
            loadReceipes = new LoadReceipes(getContext());
        }

        return loadReceipes;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<String>> loader, ArrayList<String> receipes)
    {
        if (receipes != null && mReceipes == null)
        {
            mReceipes = receipes;

            /* Prevent NullPointerException */
            if (mAdapter == null)
                mAdapter = (ReceipesAdapter) mReceipesListView.getAdapter();

            /* Ajout des résultats dans l'adapter */
            for (String s : receipes)
                mAdapter.add(s);

            /* Notif */
            mAdapter.notifyDataSetChanged();
        }
        /* Sinon affichage des erreurs, et on retourne en arrière */
        else if(mReceipes == null)
        {
            Toast.makeText(getActivity(), getResources().getString(R.string.search_error), Toast.LENGTH_LONG).show();
            getActivity().onBackPressed();
        }

        /* Suppression du loader */
        getLoaderManager().destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<String>> loader)
    {

    }
}