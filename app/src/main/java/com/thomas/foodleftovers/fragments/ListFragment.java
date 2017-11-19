package com.thomas.foodleftovers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.thomas.foodleftovers.R;
import com.thomas.foodleftovers.ui.IngredientsListView;

import java.util.List;

public class ListFragment extends Fragment
{
    private DecoratedBarcodeView mBarcodeView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        /* Démarrage du scan */
        mBarcodeView = view.findViewById(R.id.barcode);
        mBarcodeView.decodeContinuous(callback);

        /* Suppression du texte en dessous */
        mBarcodeView.getStatusView().setVisibility(View.GONE);

        return view;
    }

    private BarcodeCallback callback = new BarcodeCallback()
    {
        @Override
        public void barcodeResult(BarcodeResult result)
        {
            /* Ajout dans l'adapter */
            IngredientsListView list = getActivity().findViewById(R.id.ingredients_list);
            list.addIngredientFromBarcode(Long.parseLong(result.getText()));
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
}