package com.thomas.foodleftovers.interfaces.listeners;

import com.thomas.foodleftovers.popo.Ingredient;

/**
 * Requête terminée et parse effectuée
 */
public interface OnIngredientRequestComplete
{
    void onIngredientRequestComplete(Ingredient ingredient);
}
