package librecipe;

import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

/**
 * Stores details on the quantity of an ingredient.
 */
public class Ingredient extends Saveable implements Comparable<Ingredient> {
    private double quantity;
    private String name, units;

    /**
     * Creates a new Ingredient object from values.
     * @param name the string name of the ingredient
     * @param quantity the quantity of this ingredient in the step
     * @param units the units in which the quantity is measured
     */
    public Ingredient(String name, double quantity, String units) {
        this.name = name;
        this.quantity = quantity;
        this.units = units;
    }

    /**
     * Creates a new Ingredient object from JSON.
     * @param json the json string to deserialize from
     * @throws JsonParseException if any errors occur in deserialization
     * @throws IOException if any errors occur in deserialization
     */
    public Ingredient(String json) throws JsonParseException, IOException {
        this.deserialize(json);
    }

    /**
     * Creates an empty Ingredient object.
     * WARNING: Only for Jackson use.
     */
    public Ingredient() {}

    /**
     * Compares the name of this Ingredient object with another.
     * @param other a different Ingredient object
     * @return -1, 0, or 1 if the name of this object is less
     *         than, equal to, or greater than the name of ther oher object
     */
    public int compareTo(Ingredient other) { return this.name.compareTo(other.getName()); }

    /**
     * Retrieves the common name for the ingredient.
     * @return the name of the ingredient as a string
     */
    public String getName() { return this.name; }

    /**
     * Retrieves the quantity of this ingredient required.
     * @return a scalar representing quantity
     */
    public double getQuantity() { return this.quantity; }

    /**
     * Retrieves the units in which quantity should be measured
     * for this ingredient.
     * @return the common name for this unit
     */
    public String getUnits() { return this.units; }

    /**
     * Serializes a Saveable object into a string.
     * @throws JsonMappingException if any errors occur in deserialization
     * @throws IOException if any errors occur in deserialization
     * @return Ingredient in the form of a string (contains unreadable characters)
     */
    public String serialize() throws JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    /**
     * Deserializes a string into a Ingredient object.
     * @param serialized the original serialized object as a string
     * @throws JsonParseException if any errors occur in deserialization
     * @throws IOException if any errors occur in deserialization
     * @return the Ingredient object for chaining
     */
    public Ingredient deserialize(String serialized) throws JsonParseException, IOException {
        // deserialize the object
        ObjectMapper mapper = new ObjectMapper();
        Ingredient unwrapped = mapper.readValue(serialized, Ingredient.class);

        // copy properties over
        this.name = unwrapped.getName();
        this.quantity = unwrapped.getQuantity();
        this.units = unwrapped.getUnits();

        // return for chaining
        return this;
    }
}
