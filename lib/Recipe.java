/**
 * lib/Recipe.java - term-project
 * Class to represent a recipe.
 */

package lib;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Recipe {
    /**
     * Creates a new empty recipe object.
     */
    public Recipe() {
        this.steps = new ArrayList<>();
        this.pSteps = new ArrayList<>();
        this.servings = 1;
    }

    /**
     * The common name of the recipe.
     */
    private String name;
    public String getName() { return this.name; }
    public Recipe setName(String name) { this.name = name; return this; }

    /**
     * A list of preparation steps to follow when cooking the recipe.
     */
    private ArrayList<Step> pSteps;
    public int getNumberOfPSteps() { return this.pSteps.size(); }
    public Step getPStep(int index) { return this.pSteps.get(index); }
    
    /**
     * @returns a new iterable to iterate over all preparation steps.
     */
    public Iterable<Step> iteratePrep() {
        final ArrayList<Step> stepSet = this.pSteps;
        return new Iterable<Step>() {
            public Iterator<Step> iterator() {
                return new StepsIterator(stepSet);
            }
        };
    }

    /**
     * A list of steps to follow when cooking the recipe.
     */
    private ArrayList<Step> steps;
    public int getNumberOfSteps() { return this.steps.size(); }
    public Step getStep(int index) { return this.steps.get(index); }
    
    /**
     * @returns a new iterable to iterate over all cook steps.
     */
    public Iterable<Step> iterateCook() {
        final ArrayList<Step> stepSet = this.steps;
        return new Iterable<Step>() {
            public Iterator<Step> iterator() {
                return new StepsIterator(stepSet);
            }
        };
    }

    /**
     * Generalized iterator class to iterate over
     */
    private class StepsIterator implements Iterator<Step> {
        private Iterator<Step> i;
        public StepsIterator(ArrayList<Step> stepSet) { this.i = stepSet.iterator(); }
        public boolean hasNext() { return this.i.hasNext(); }
        public Step next() { return this.i.next(); }
        public void remove() { this.i.remove(); }
    }

    /**
     * Create a new preparation step.
     * @param step the next step in the preparation process
     * @returns the recipe object for chaining
     */
    public Recipe addPrepStep(Step step) {
        this.pSteps.add(step);
        return this;
    }

    /**
     * Create a new cooking step.
     * @param step the next step in the cooking process
     * @returns the recipe object for chaining
     */
    public Recipe addStep(Step step) {
        this.steps.add(step);
        return this;
    }

    /**
     * The number of servings for which to return quantities.
     */
    private float servings;
    public float getServings() { return this.servings; }
    public Recipe setServings(float servings) { this.servings = servings; return this; }

    /**
     * @returns the time required for preparation of this recipe
     */
    public float getPrepTime() {
        float time = 0;

        // sum up the time lengths of all preparation steps 
        for (Step step : this.pSteps) {
            time += step.getTime();
        }

        return time;
    }

    /**
     * @returns the time required to cook for this recipe
     */
    public float getCookTime() {
        float time = 0;

        // sum up the time lengths of all cooking steps 
        for (Step step : this.steps) {
            time += step.getTime();
        }

        return time;
    }

    /**
     * @returns the total time required for this recipe
     */
    public float getTotalTime() {
        return this.getPrepTime() + this.getCookTime();
    }

    /**
     * Create an iterator to go through all the ingredients.
     * @returns an iterable to iterate all ingredients
     */
    public Iterable<String> iterateIngredients() {
        Iterator<Step> itPSteps = this.iteratePrep().iterator(), itSteps = this.iterateCook().iterator();
        PriorityQueue<String> ingredients = new PriorityQueue<String>() {
            @Override
            public boolean offer(String i) {
                if (this.contains(i)) return false;
                return super.offer(i);
            }
        };

        while (itPSteps.hasNext()) for (String i : itPSteps.next().iterateIngredients()) ingredients.offer(i);
        while (itSteps.hasNext()) for (String i : itSteps.next().iterateIngredients()) ingredients.offer(i);

        return ingredients;
    }

    /**
     * Calculate the quantity required of a particular ingredient.
     * @param ingredient the name of the ingredient
     * @returns the quantity of ingredient required by full recipe
     */
    public double getQuantityOf(String ingredient) {
        double quantity = 0.0;

        // sum up the quantity required by each step
        for (Step step : this.steps) {
            quantity += step.getQuantityOf(ingredient);
        }

        // multiply quantity for one serving by the number
        // of servings
        return this.servings * quantity;
    }

    /**
     * Get the units of 'ingredient' required for this step.
     * @param ingredient the name of the ingredient
     * @returns the units of measure being used
     */
    public String getUnitsOf(String ingredient) {
        for (Step step : this.steps) {
            if (step.getQuantityOf(ingredient) > 0) {
                return step.getUnitsOf(ingredient);
            }
        }

        return null;
    }
}