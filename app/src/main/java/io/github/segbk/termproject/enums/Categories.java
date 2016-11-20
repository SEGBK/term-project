package io.github.segbk.termproject.enums;

/**
 * Created by cssa on 2016-11-14.
 */

public enum Categories {
    MAIN_DISH, STARTER, DESERT, APPETIZER, DRINK, SAUCE;

    final static private String[] Categories = {"Main Dish", "Starter", "Desert", "Appetizer", "Drink", "Sauce"};

    static public String[] getCategories() {
        return Categories;
    }
}
