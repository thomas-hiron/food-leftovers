package com.thomas.foodleftovers.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.thomas.foodleftovers.R;

/**
 * Réagit au clic sur le bouton pour rechercher les recettes
 */
public class SearchReceipesButton extends AppCompatButton implements View.OnClickListener
{
    public SearchReceipesButton(Context context)
    {
        super(context);
    }

    public SearchReceipesButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SearchReceipesButton(Context context, AttributeSet attrs, int defStyleAttr)
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
        Toast.makeText(getContext(), "Todo: search", Toast.LENGTH_SHORT).show();
    }
}
