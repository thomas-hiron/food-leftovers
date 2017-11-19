package com.thomas.foodleftovers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.thomas.foodleftovers.R;
import com.thomas.foodleftovers.adapters.ReceipesAdapter;
import com.thomas.foodleftovers.async_tasks.LoadReceipes;
import com.thomas.foodleftovers.popo.Ingredient;
import com.thomas.foodleftovers.ui.ReceipesListView;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<String>>
{
    private static int LOADER_ID = 1;
    private ArrayList<String> mReceipes;
    private ReceipesListView mReceipesListView;
    private ReceipesAdapter mAdapter;
    private TextView mSearchTitle;
    private TextView mSearchResults;
    private ProgressBar mSearchProgress;
    private ArrayList<Ingredient> mIngredients;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        /* List view et adapter */
        mReceipesListView = view.findViewById(R.id.results_list);
        mAdapter = (ReceipesAdapter) mReceipesListView.getAdapter();

        /* UI */
        mSearchTitle = view.findViewById(R.id.search_title);
        mSearchResults = view.findViewById(R.id.search_results);
        mSearchProgress = view.findViewById(R.id.search_progress);

        return view;
    }

    /**
     * Lance la recherche
     *
     * @param ingredients Les ingrédients
     */
    public void search(ArrayList<Ingredient> ingredients)
    {
        mIngredients = ingredients;

        /* Affichage du chargement */
        mSearchTitle.setVisibility(View.VISIBLE);
        mSearchResults.setVisibility(View.GONE);
        mSearchProgress.setVisibility(View.VISIBLE);

        /* Chargement */
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    /**
     * Réinitialise le fragment
     */
    public void reset()
    {
        mReceipes = null;
        mAdapter.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public Loader<ArrayList<String>> onCreateLoader(int id, Bundle args)
    {
        LoadReceipes loadReceipes = null;
        if (mReceipes == null && mIngredients != null)
        {
            loadReceipes = new LoadReceipes(getContext(), mIngredients);
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

            /* Cache du chargement */
            mSearchResults.setVisibility(View.VISIBLE);
            mSearchTitle.setVisibility(View.GONE);
            mSearchProgress.setVisibility(View.GONE);

            /* Nombre de résultats */
            String resultsText = getResources().getQuantityString(R.plurals.results_number, mReceipes.size(), mReceipes.size());
            mSearchResults.setText(resultsText);
        }
        /* Sinon affichage des erreurs, et on retourne en arrière */
        else if (mReceipes == null)
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