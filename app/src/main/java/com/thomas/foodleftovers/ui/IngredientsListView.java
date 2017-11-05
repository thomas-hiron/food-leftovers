package com.thomas.foodleftovers.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.Toast;

import com.thomas.foodleftovers.R;
import com.thomas.foodleftovers.adapters.IngredientsAdapter;
import com.thomas.foodleftovers.async_tasks.LoadOpenFoodFactsIngredient;
import com.thomas.foodleftovers.async_tasks.LoadOpenProductDataIngredient;
import com.thomas.foodleftovers.async_tasks.LoadOutpanIngredient;
import com.thomas.foodleftovers.interfaces.listeners.OnIngredientRequestComplete;
import com.thomas.foodleftovers.popo.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Gère la liste des ingrédients
 */
public class IngredientsListView extends ListView implements OnIngredientRequestComplete
{
    private final static int OUTPAN_API = 1;
    private final static int OPEN_FOOD_FACTS_API = 2;
    private final static int OPEN_PRODUCT_DATA_API = 3;

    private IngredientsAdapter mAdapter;
    private List<String> mTextList;

    public IngredientsListView(Context context)
    {
        super(context);
    }

    public IngredientsListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public IngredientsListView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        /* Init list */
        mTextList = new ArrayList<>();

        /* Ajout de l'adapter */
        mAdapter = new IngredientsAdapter(getContext());
        setAdapter(mAdapter);
    }

    /**
     * Ajoute un ingrédient dans la liste en premier depuis le EditText
     *
     * @param text Le nom de l'ingrédient
     */
    public void addIngredientFromInput(String text)
    {
        text = text.trim();
        if (!text.equals("") && !mTextList.contains(text.toLowerCase()))
        {
            /* Instanciation de l'ingrédient */
            Ingredient ingredient = new Ingredient();
            ingredient.setName(text);

            mAdapter.insert(ingredient, 0);
            mAdapter.notifyDataSetChanged();

            /* Ajout à la liste */
            mTextList.add(text.toLowerCase());
        }
    }

    /**
     * Ajoute un ingrédient récupéré avec le code barre
     *
     * @param barcode Le code barre
     */
    public void addIngredientFromBarcode(String barcode)
    {
        if (!mTextList.contains(barcode))
        {
            /* Instanciation de l'ingrédient */
            Ingredient ingredient = new Ingredient();
            ingredient.setName(getResources().getString(R.string.adding_ingredient));

            mAdapter.insert(ingredient, 0);
            mAdapter.notifyDataSetChanged();

            /* Ajout à la liste */
            mTextList.add(barcode);

            /* Chargement en tâche de fond */
            loadIngredient(ingredient, barcode, OPEN_PRODUCT_DATA_API);
        }
    }

    /**
     * Charge les infos suivant la bonne API
     *
     * @param ingredient L'ingrédient
     * @param barcode    Le code barre
     * @param apiCode    Le code de l'API
     */
    private void loadIngredient(Ingredient ingredient, String barcode, int apiCode)
    {
        switch (apiCode)
        {
            case OUTPAN_API:
                new LoadOutpanIngredient(this, ingredient).execute(barcode);
                break;
            case OPEN_FOOD_FACTS_API:
                new LoadOpenFoodFactsIngredient(this, ingredient).execute(barcode);
                break;
            case OPEN_PRODUCT_DATA_API:
                new LoadOpenProductDataIngredient(this, ingredient).execute(barcode);
                break;
            default:
                ingredient.setName(null);
                onIngredientRequestComplete(ingredient);
                break;
        }
    }

    @Override
    public void onIngredientRequestComplete(Ingredient ingredient)
    {
        if (ingredient.getName() != null)
        {
            /* Modification du texte */
            ingredient.setName(ingredient.getName());

            /* Update view */
            mAdapter.notifyDataSetChanged();
        }
        else
        {
            /* Affichage d'un toast */
            String error = getContext().getResources().getString(R.string.ingredient_not_found);
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();

            /* Suppression de l'ingrédient de l'adapter */
            mAdapter.remove(ingredient);
            mAdapter.notifyDataSetChanged();
        }
    }
}
