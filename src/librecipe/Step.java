package librecipe;

import java.util.LinkedHashMap;

/**
 * Represents a single step in the Recipe.
 */
public class Step {
    /**
     * Creates a new step object.
     */
    public Step() {
        this.dblIngredients = new LinkedHashMap<>();
    }

    /**
     * The time it takes to execute this step (in minutes).
     */
    private double time;

    /**
     * Retrieves the amount of time to execute this step.
     * @return the amount of time required for this step
     */
    public double getTime() { return this.time; }

    /**
     * Sets the time required to execute this step.
     * @param time the number of minutes required to execute this step
     * @return the Step object for chaining
     */
    public Step setTime(double time) { this.time = time; return this; }

    /**
     * Map of ingredients to quantity required for one
     * serving.
     */
    LinkedHashMap<String,Ingredient> dblIngredients;

    /**
     * Adds a new ingredient to this step.
     * @param name the text name of the ingredient
     * @param quantity the amount of this ingredient required in this serving
     * @param units the measure of units to use for this ingredient
     * @return the Step object for chaining
     */
    public Step addIngredient(String name, double quantity, String units) {
        this.dblIngredients.put(name, new Ingredient(name, quantity, units));
        return this;
    }

    /**
     * Creates an iterable over the list of ingredients.
     * @return an iterable object to iterate ingredient names
     */
    public Iterable<String> iterateIngredients() {
        return (Iterable<String>)this.dblIngredients.keySet();
    }

    /**
     * Get the quantity of 'ingredient' required for this step.
     * @param ingredient the name of the ingredient
     * @return the total quantity required for this step
     */
    public double getQuantityOf(String ingredient) {
        if (!this.dblIngredients.containsKey(ingredient)) return 0;
        return this.dblIngredients.get(ingredient).getQuantity();
    }

    /**
     * Get the units of 'ingredient' required for this step.
     * @param ingredient the name of the ingredient
     * @return the units of measure being used
     */
    public String getUnitsOf(String ingredient) {
        if (!this.dblIngredients.containsKey(ingredient)) return null;
        return this.dblIngredients.get(ingredient).getUnits();
    }

    /**
     * Get full map of ingredients.
     * @return the underlying map of ingredients
     */
    public LinkedHashMap<String,Ingredient> getIngredients() {
        return this.dblIngredients;
    }

    /**
     * Set full map of ingredients.
     * @param the Ingredients map to replace the current one with
     * @return the Step object for chaining
     */
    public Step setIngredients(LinkedHashMap<String,Ingredient> ingredients) {
        this.dblIngredients = ingredients;
        return this;
    }

    /**
     * The description of this step.
     */
    private String text;

    /**
     * Retrieves the instructions of how to execute this step.
     * @return the instructions of how to execute this step
     */
    public String getText() { return this.text; }

    /**
     * Sets the instructions of how to execute this step.
     * @param text the instructions of how to execute this step
     * @return the Step object for chaining
     */
    public Step setText(String text) { this.text = text; return this; }
}
