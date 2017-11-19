package com.thomas.foodleftovers.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.thomas.foodleftovers.R;

public class EnterIngredientEditText extends AppCompatEditText implements TextView.OnEditorActionListener
{
    public EnterIngredientEditText(Context context)
    {
        super(context);
        init();
    }

    public EnterIngredientEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public EnterIngredientEditText(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Pour ajouter le click sur la touche entrée
     */
    private void init()
    {
        setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
    {
        /* Appuie sur entrée, ajoute le texte */
        if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            View parent = (View) getParent();
            ValidateIngredientButton validateButton = parent.findViewById(R.id.validate_ingredient_button);

            validateButton.callOnClick();

            return true;
        }

        return false;
    }
}
