/**
 * lib/Step.java - term-project
 * Represents a single step in the Recipe.
 */

package lib;

import java.util.HashMap;

public class Step {
    /**
     * Creates a new step object.
     */
    public Step() {
        this.dblIngredients = new HashMap<>();
    }

    /**
     * The time it takes to execute this step (in minutes).
     */
    private double time;
    public double getTime() { return this.time; }
    public Step setTime(double time) { this.time = time; return this; }

    /**
     * Map of ingredients to quantity required for one
     * serving.
     */
    HashMap<String,Ingredient> dblIngredients;

    /**
     * Adds a new ingredient to this step.
     * @param name the text name of the ingredient
     * @param quantity the amount of this ingredient required in this serving
     * @param units the measure of units to use for this ingredient
     * @returns the step object for chaining
     */
    public Step addIngredient(String name, double quantity, String units) {
        this.dblIngredients.put(name, new Ingredient(name, quantity, units));
        return this;
    }

    /**
     * Creates an iterable over the list of ingredients.
     * @returns an iterable object to iterate ingredient names
     */
    public Iterable<String> iterateIngredients() {
        return (Iterable<String>)this.dblIngredients.keySet();
    }

    /**
     * Get the quantity of 'ingredient' required for this step.
     * @param ingredient the name of the ingredient
     * @returns the total quantity required for this step
     */
    public double getQuantityOf(String ingredient) {
        if (!this.dblIngredients.containsKey(ingredient)) return 0;
        return this.dblIngredients.get(ingredient).getQuantity();
    }

    /**
     * Get the units of 'ingredient' required for this step.
     * @param ingredient the name of the ingredient
     * @returns the units of measure being used
     */
    public String getUnitsOf(String ingredient) {
        if (!this.dblIngredients.containsKey(ingredient)) return null;
        return this.dblIngredients.get(ingredient).getUnits();
    }

    /**
     * The description of this step.
     */
    private String text;
    public String getText() { return this.text; }
    public Step setText(String text) { this.text = text; return this; }
}