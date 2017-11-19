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
import com.thomas.foodleftovers.adapters.RecipesAdapter;
import com.thomas.foodleftovers.async_tasks.LoadRecipes;
import com.thomas.foodleftovers.popo.Ingredient;
import com.thomas.foodleftovers.popo.Recipe;
import com.thomas.foodleftovers.ui.RecipesListView;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>
{
    private static int LOADER_ID = 1;
    private ArrayList<Recipe> mRecipes;
    private RecipesListView mRecipesListView;
    private RecipesAdapter mAdapter;
    private TextView mSearchTitle;
    private TextView mSearchResults;
    private ProgressBar mSearchProgress;
    private ArrayList<Ingredient> mIngredients;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        /* List view et adapter */
        mRecipesListView = view.findViewById(R.id.results_list);
        mAdapter = (RecipesAdapter) mRecipesListView.getAdapter();

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
        mRecipes = null;
        mAdapter.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle args)
    {
        LoadRecipes loadRecipes = null;
        if (mRecipes == null && mIngredients != null) {
            loadRecipes = new LoadRecipes(getContext(), mIngredients);
        }

        return loadRecipes;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> recipes)
    {
        if (recipes != null && mRecipes == null) {
            mRecipes = recipes;

            /* Prevent NullPointerException */
            if (mAdapter == null) {
                mAdapter = (RecipesAdapter) mRecipesListView.getAdapter();
            }

            /* Ajout des résultats dans l'adapter */
            for (Recipe recipe : recipes) {
                mAdapter.add(recipe);
            }

            /* Notif */
            mAdapter.notifyDataSetChanged();

            /* Cache du chargement */
            mSearchResults.setVisibility(View.VISIBLE);
            mSearchTitle.setVisibility(View.GONE);
            mSearchProgress.setVisibility(View.GONE);

            /* Nombre de résultats */
            String resultsText = getResources().getQuantityString(R.plurals.results_number, mRecipes.size(), mRecipes.size());
            mSearchResults.setText(resultsText);
        }
        /* Sinon affichage des erreurs, et on retourne en arrière */
        else if (mRecipes == null) {
            Toast.makeText(getActivity(), getResources().getString(R.string.search_error), Toast.LENGTH_LONG).show();
            getActivity().onBackPressed();
        }

        /* Suppression du loader */
        getLoaderManager().destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipe>> loader)
    {

    }
}