package com.thomas.foodleftovers.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.thomas.foodleftovers.R;

/**
 * Réagit au clic sur le bouton d'ajout d'ingrédient
 */
public class ValidateIngredientButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener
{
    public ValidateIngredientButton(Context context)
    {
        super(context);
    }

    public ValidateIngredientButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ValidateIngredientButton(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        /* Récupération de la valeur de l'EditText */
        View parent = (View) getParent();
        EditText input = parent.findViewById(R.id.input_enter_ingredient);
        String ingredient = input.getText().toString();

        /* Suppression du input */
        input.setText(null);
    }
}
