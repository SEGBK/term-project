package io.github.segbk.termproject.models;

/**
 * Created by cssa on 2016-11-15.
 */

public class Ingredient {
    private String IngredientName;
    private String Quantity;

    public Ingredient(String ingredientName, String quantity) {
        IngredientName = ingredientName;
        Quantity = quantity;
    }

    public String getIngredientName() {
        return IngredientName;
    }

    public void setIngredientName(String ingredientName) {
        IngredientName = ingredientName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
