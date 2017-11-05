package com.thomas.foodleftovers.popo;

/**
 * Défini un ingrédient
 */
public class Ingredient
{
    private long barcode;
    private String name;
    private boolean fetch = false;

    public long getBarcode()
    {
        return barcode;
    }

    public void setBarcode(long barcode)
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

    public boolean isFetch()
    {
        return fetch;
    }

    public void setFetch(boolean fetch)
    {
        this.fetch = fetch;
    }
}