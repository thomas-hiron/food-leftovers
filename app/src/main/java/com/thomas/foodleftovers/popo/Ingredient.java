package com.thomas.foodleftovers.popo;

/**
 * Défini un ingrédient
 */
public class Ingredient
{
    private String barcode;
    private String name;

    public String getBarcode()
    {
        return barcode;
    }

    public void setBarcode(String barcode)
    {
        this.barcode = barcode;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}