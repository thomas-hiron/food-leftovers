package com.thomas.foodleftovers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.thomas.foodleftovers.MainActivity;
import com.thomas.foodleftovers.R;
import com.thomas.foodleftovers.adapters.IngredientsAdapter;
import com.thomas.foodleftovers.interfaces.listeners.OnSearch;
import com.thomas.foodleftovers.popo.Ingredient;
import com.thomas.foodleftovers.ui.IngredientsListView;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment implements View.OnClickListener
{
    private DecoratedBarcodeView mBarcodeView;
    private OnSearch mListener;
    private IngredientsListView mList;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try {
            mListener = (OnSearch) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(MainActivity.class + "must implement" + OnSearch.class.getSimpleName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        /* Démarrage du scan */
        mBarcodeView = view.findViewById(R.id.barcode);
        mBarcodeView.decodeContinuous(callback);

        /* Suppression du texte en dessous */
        mBarcodeView.getStatusView().setVisibility(View.GONE);

        /* Listener sur la recherche */
        View searchButton = view.findViewById(R.id.search_recipes_button);
        searchButton.setOnClickListener(this);

        mList = view.findViewById(R.id.ingredients_list);

        return view;
    }

    private BarcodeCallback callback = new BarcodeCallback()
    {
        @Override
        public void barcodeResult(BarcodeResult result)
        {
            /* Ajout dans l'adapter */
            mList.addIngredientFromBarcode(Long.parseLong(result.getText()));
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints)
        {
        }
    };

    @Override
    public void onResume()
    {
        super.onResume();
        mBarcodeView.resume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mBarcodeView.pause();
    }

    /**
     * Modifie la visibilité
     *
     * @param visibility La visibilité
     */
    public void setBarcodeVisibility(int visibility)
    {
        mBarcodeView.setVisibility(visibility);
    }

    /**
     * Gère le clique sur la vue
     *
     * @param keyCode Le code
     * @param event   L'event
     * @return Si intercepté
     */
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        return mBarcodeView.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.search_recipes_button) {
            /* Lancement de la recherche */
            if (mListener != null) {
                mListener.onSearch();
            }
            /* mListener pas instancié */
            else {
                Toast.makeText(view.getContext(), view.getResources().getString(R.string.search_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * @return La liste des ingrédients
     */
    public ArrayList<Ingredient> getIngredients()
    {
        IngredientsAdapter ingredientsAdapter = (IngredientsAdapter) mList.getAdapter();
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        for (int i = 0, l = ingredientsAdapter.getCount(); i < l; ++i) {
            ingredients.add(ingredientsAdapter.getItem(i));
        }

        return ingredients;
    }
}