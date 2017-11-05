package com.thomas.foodleftovers.popo;

/**
 * Défini un ingrédient
 */
public class Ingredient
{
    private String barcode;
    private String text;

    public String getBarcode()
    {
        return barcode;
    }

    public void setBarcode(String barcode)
    {
        this.barcode = barcode;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
