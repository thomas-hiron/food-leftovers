package com.thomas.foodleftovers.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.thomas.foodleftovers.R;
import com.thomas.foodleftovers.adapters.IngredientsAdapter;
import com.thomas.foodleftovers.async_tasks.LoadOpenFoodFactsIngredient;
import com.thomas.foodleftovers.async_tasks.LoadOpenProductDataIngredient;
import com.thomas.foodleftovers.async_tasks.LoadOutpanIngredient;
import com.thomas.foodleftovers.interfaces.listeners.OnIngredientRequestComplete;
import com.thomas.foodleftovers.popo.Ingredient;

/**
 * Gère la liste des ingrédients
 */
public class IngredientsListView extends ListView implements OnIngredientRequestComplete
{
    private final static int OUTPAN_API = 1;
    private final static int OPEN_FOOD_FACTS_API = 2;
    private final static int OPEN_PRODUCT_DATA_API = 3;

    private IngredientsAdapter mAdapter;

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

        /* Récupération du bouton de recherche */
        View parent = getRootView();
        Button searchReceipesButton = parent.findViewById(R.id.search_receipes_button);

        /* Ajout de l'adapter */
        mAdapter = new IngredientsAdapter(getContext(), searchReceipesButton);
        setAdapter(mAdapter);
    }

    /**
     * Ajoute un ingrédient dans la liste en premier depuis le EditText
     *
     * @param ingredientName Le nom de l'ingrédient
     */
    public void addIngredientFromInput(String ingredientName)
    {
        ingredientName = ingredientName.trim();
        if (!ingredientName.equals("")) {
            /* Pas présent */
            if (!mAdapter.containsName(ingredientName)) {
                /* Instanciation de l'ingrédient */
                Ingredient ingredient = new Ingredient();
                ingredient.setName(ingredientName);
                ingredient.setFetch(true);

                mAdapter.insert(ingredient, 0);
                mAdapter.notifyDataSetChanged();
            }
            /* Affichage toast */
            else {
                toastExistingIngredient();
            }
        }
    }

    /**
     * Ajoute un ingrédient récupéré avec le code barre
     *
     * @param barcode Le code barre
     */
    public void addIngredientFromBarcode(long barcode)
    {
        if (!mAdapter.containsBarcode(barcode)) {
            /* Instanciation de l'ingrédient */
            Ingredient ingredient = new Ingredient();
            ingredient.setName(getResources().getString(R.string.adding_ingredient));
            ingredient.setBarcode(barcode);

            mAdapter.insert(ingredient, 0);
            mAdapter.notifyDataSetChanged();

            /* Chargement en tâche de fond */
            loadIngredient(ingredient, ingredient.getBarcode(), OPEN_FOOD_FACTS_API);
        }
    }

    /**
     * Charge les infos suivant la bonne API
     *
     * @param ingredient L'ingrédient
     * @param barcode    Le code barre
     * @param apiCode    Le code de l'API
     */
    private void loadIngredient(Ingredient ingredient, long barcode, int apiCode)
    {
        switch (apiCode) {
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
        if (ingredient.getName() != null) {
            /* Pas présent dans la liste, ajout */
            if (!mAdapter.containsName(ingredient.getName())) {
                /* Fetched */
                ingredient.setFetch(true);

                /* Update view */
                mAdapter.notifyDataSetChanged();
            }
            else {
                toastExistingIngredient();
                removeFromAdapter(ingredient);
            }
        }
        else {
            toastIngredientNotFound();
            removeFromAdapter(ingredient);
        }
    }

    /**
     * Affiche un toast pour un ingrédient qui existe déjà
     */
    private void toastExistingIngredient()
    {
        String error = getContext().getResources().getString(R.string.existing_ingredient);
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    /**
     * Affiche un toast pour un ingrédient non trouvé
     */
    private void toastIngredientNotFound()
    {
        String error = getContext().getResources().getString(R.string.ingredient_not_found);
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    /**
     * Supprime un ingrédient de l'adapter (Ajout en cours...)
     *
     * @param ingredient L'ingrédient à retirer
     */
    private void removeFromAdapter(Ingredient ingredient)
    {
        /* Suppression de l'ingrédient de l'adapter */
        mAdapter.remove(ingredient);
        mAdapter.notifyDataSetChanged();
    }
}
