package librecipe;

import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.PriorityQueue;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.JsonMappingException;

/**
 * Holds the details related to a recipe.
 */
public class Recipe extends Saveable {
    /**
     * Creates a new empty recipe object.
     */
    public Recipe() {
        this.steps = new ArrayList<>();
        this.pSteps = new ArrayList<>();
        this.servings = 1;
    }

    /**
     * Creates a new Recipe object from JSON.
     * @param json a string of JSON to read from
     * @throws JsonParseException if any errors occur in deserialization
     * @throws IOException if any errors occur in deserialization
     */
    public Recipe(String json) throws JsonParseException, IOException {
        this.deserialize(json);
    }

    /**
     * The common name of the recipe.
     */
    private String name;

    /**
     * Retrieves the common name of the recipe.
     * @return the name as a string
     */
    public String getName() { return this.name; }

    /**
     * Sets the common name of the recipe.
     * @param name the name to set for this recipe
     * @return the Recipe object for chaining
     */
    public Recipe setName(String name) { this.name = name; return this; }

    /**
     * The type of the recipe.
     */
    private String type;

    /**
     * Retrieves the type of the recipe.
     * @return the type as a string
     */
    public String getType() { return this.type; }

    /**
     * Sets the type of the recipe.
     * @param type the type to set for this recipe
     * @return the Recipe object for chaining
     */
    public Recipe setType(String type) { this.type = type; return this; }

    /**
     * A list of preparation steps to follow when cooking the recipe.
     */
    private ArrayList<Step> pSteps;

    /**
     * Retrieves the number of preparation steps required.
     * @return the number of preparation steps
     */
    public int numberOfPSteps() { return this.pSteps.size(); }

    /**
     * Retrieves preparation step at a given index.
     * @param index the index of the step to be fetched (starts at 0)
     * @return the Step object representing the preparation step
     */
    public Step pStep(int index) { return this.pSteps.get(index); }

    /**
     * Creates a new iterable to go over the preparation steps.
     * @return a new iterable to iterate over all preparation steps.
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

    /**
     * Retrieves the number of cooking steps required.
     * @return the number of cooking steps
     */
    public int numberOfSteps() { return this.steps.size(); }

    /**
     * Retrieves a specific cooking step.
     * @param index the index of the step to be fetched (starts at 0)
     * @return the Step object representing the cooking step
     */
    public Step step(int index) { return this.steps.get(index); }
    
    /**
     * Creates a new iterable to go over the cooking steps.
     * @return a new iterable to iterate over all cook steps.
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
     * Generalized iterator class to iterate over a set of steps.
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
     * @return the Recipe object for chaining
     */
    public Recipe addPrepStep(Step step) {
        this.pSteps.add(step);
        return this;
    }

    /**
     * Create a new cooking step.
     * @param step the next step in the cooking process
     * @return the Recipe object for chaining
     */
    public Recipe addStep(Step step) {
        this.steps.add(step);
        return this;
    }

    /**
     * Deletes the preparation step at a given index.
     * @param index the index of the preparation step to delete
     * @return the Recipe object for chaining
     */
    public Recipe removePStep(int index) { this.pSteps.remove(index); return this; }

    /**
     * Deletes a given preparation step.
     * @param step the preparation step to delete
     * @return the Recipe object for chaining
     */
    public Recipe removePStep(Step step) { this.pSteps.remove(step); return this; }

    /**
     * Deletes the step at a given index.
     * @param index the index of the step to delete
     * @return the Recipe object for chaining
     */
    public Recipe removeStep(int index) { this.steps.remove(index); return this; }

    /**
     * Deletes a given step.
     * @param step the step to delete
     * @return the Recipe object for chaining
     */
    public Recipe removeStep(Step step) { this.steps.remove(step); return this; }

    /**
     * The number of servings for which to return quantities.
     */
    private double servings;

    /**
     * Retrieves the number of servings prepared by this recipe.
     * @return the number of servings prepared by this recipe
     */
    public double getServings() { return this.servings; }

    /**
     * Scales the recipe to amounts required for 'servings' servings.
     * @param servings the number of servings to scale to
     * @return the Recipe object for chaining
     */
    public Recipe setServings(double servings) { this.servings = servings; return this; }

    /**
     * Retrieves the amount of time required to prepare this recipe.
     * @return the time required for preparation of this recipe
     */
    public double prepTime() {
        double time = 0;

        // sum up the time lengths of all preparation steps 
        for (Step step : this.pSteps) {
            time += step.getTime();
        }

        return time;
    }

    /**
     * Retrieves the amount of time required to cook this recipe.
     * @return the time required to cook this recipe
     */
    public double cookTime() {
        double time = 0;

        // sum up the time lengths of all cooking steps 
        for (Step step : this.steps) {
            time += step.getTime();
        }

        return time;
    }

    /**
     * Retrieves the total time required to prepare and cook this recipe.
     * @return the total time required for this recipe
     */
    public double totalTime() {
        return this.prepTime() + this.cookTime();
    }

    /**
     * Create an iterator to go through all the ingredients.
     * @return an iterable to iterate all ingredients
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
     * @return the quantity of ingredient required by full recipe
     */
    public double quantityOf(String ingredient) {
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
     * @return the units of measure being used
     */
    public String unitsOf(String ingredient) {
        for (Step step : this.steps) {
            if (step.getQuantityOf(ingredient) > 0) {
                return step.getUnitsOf(ingredient);
            }
        }

        return null;
    }

    /**
     * Get full list of prep steps.
     * @return the list of prep steps
     */
    public ArrayList<Step> getPSteps() { return this.pSteps; }

    /**
     * Set full list of prep steps.
     * @param pSteps the list of steps to set to
     */
    public void setPSteps(ArrayList<Step> pSteps) { this.pSteps = pSteps; }

    /**
     * Get full list of steps.
     * @return the list of prep steps
     */
    public ArrayList<Step> getSteps() { return this.steps; }

    /**
     * Set full list of steps.
     * @param steps the list of steps to set to
     */
    public void setSteps(ArrayList<Step> steps) { this.steps = steps; }

    /**
     * Serializes a Recipe object into a string.
     * @throws JsonMappingException if any errors occur in deserialization
     * @throws IOException if any errors occur in deserialization
     * @return Recipe in the form of a string (contains unreadable characters)
     */
    public String serialize() throws JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    /**
     * Deserializes a string into a Recipe object.
     * @param serialized the original serialized object as a string
     * @throws JsonParseException if any errors occur in deserialization
     * @throws IOException if any errors occur in deserialization
     * @return the Recipe object for chaining
     */
    public Recipe deserialize(String serialized) throws JsonParseException, IOException {
        // deserialize the object
        ObjectMapper mapper = new ObjectMapper();
        Recipe unwrapped = mapper.readValue(serialized, Recipe.class);

        // copy properties over
        this.setName(unwrapped.getName());
        this.setServings(unwrapped.getServings());
        this.setPSteps(unwrapped.getPSteps());
        this.setSteps(unwrapped.getSteps());

        // return for chaining
        return this;
    }

    /**
     * Grabs the value of a property given the name.
     * @param name the property who's value to fetch
     * @return the value of the specified property 
     */
    public String getProperty(String name) {
        switch (name) {
            case "name": return this.getName();
            case "type": return this.getType();
            case "prepTime": return ((Double)this.prepTime()).toString();
            case "cookTime": return ((Double)this.cookTime()).toString();
            case "time": return ((Double)this.totalTime()).toString();
            case "limit": return null;

            default: throw new IllegalArgumentException("No such property: " + name);
        }
    }
}
