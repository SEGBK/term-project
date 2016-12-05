package io.github.segbk.termproject.models;

import android.graphics.Bitmap;

/**
 * Created by cssa on 2016-11-15.
 */

public class Recipe {
    private String Title;
    private Ingredient[] Ingredients;
    private Step[] Steps;
    private String ImageURL;

    public Recipe(String title, Ingredient[] ingredients, Step[] steps) {
        Title = title;
        Ingredients = ingredients;
        Steps = steps;
    }

    public Recipe(String title, Ingredient[] ingredients, Step[] steps, String imageURL) {
        Title = title;
        Ingredients = ingredients;
        Steps = steps;
        ImageURL = imageURL;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getIngredients() {
        String ingredientList = "";
        for(Ingredient i : Ingredients){
            ingredientList += i.getQuantity()+ " " + i.getIngredientName() + "\n";
        }
        return ingredientList.substring(0,ingredientList.length()-2);
    }

    public void setIngredients(Ingredient[] ingredients) {
        Ingredients = ingredients;
    }

    public String getSteps() {
        String stepString = "";
        for(Step s : Steps){
            stepString += s.getInsruction();
            stepString += "/n";
        }
        return stepString;
    }

    public void setSteps(Step[] steps) {
        Steps = steps;
    }

    public String getImage() {
        return ImageURL;
    }

    public void setImage(String image) {
        ImageURL = image;
    }
}
