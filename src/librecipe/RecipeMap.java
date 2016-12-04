package librecipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.JsonMappingException;

public class RecipeMap extends Saveable implements Iterable<String> {
    private ArrayList<String> keys, values;

    public ArrayList<String> getKeys() { return this.keys; }
    public void setKeys(ArrayList<String> keys) { this.keys = keys; }

    public ArrayList<String> getValues() { return this.values; }
    public void setValues(ArrayList<String> values) { this.values = values; }

    public int size() { return this.keys.size(); }

    /**
     * Creates a new empty RecipeMap object.
     */
    public RecipeMap() {
        this.keys = new ArrayList<String>();
        this.values = new ArrayList<String>();
    }

    /**
     * Creates a new RecipeMap object from JSON.
     * @param json the string JSON object
     * @throws JsonParseException if JSON deserialization fails
     * @throws IOException if JSON deserialization fails
     */
    public RecipeMap(String json) throws JsonParseException, IOException {
        this.deserialize(json);
    }

    /**
     * Gets an ID from a recipe name.
     * @param key the name of a recipe
     * @return the ID of the recipe
     */
    public String get(String key) {
        return this.values.get( this.keys.indexOf(key) );
    }

    /**
     * Map a recipe name to recipe ID.
     * @param key the name of a recipe
     * @param value the ID of the recipe
     */
    public void put(String key, String value) {
        this.keys.add(key);
        this.values.add(value);
    }

    /**
     * Creates a new iterator to go through all keys.
     */
    public Iterator<String> iterator() { return this.keys.iterator(); }

    /**
     * Serializes a RecipeMap object into a string.
     * @throws JsonMappingException if JSON serialization fails
     * @throws IOException if JSON serialization fails
     * @return RecipeMap in the form of a string (contains unreadable characters)
     */
    public String serialize() throws JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    /**
     * Deserializes a string into a RecipeMap object.
     * @param serialized the original serialized object as a string
     * @throws JsonParseException if JSON deserialization fails
     * @throws IOException if JSON deserialization fails
     * @return the RecipeMap object for chaining
     */
    public RecipeMap deserialize(String serialized) throws JsonParseException, IOException {
        // deserialize the object
        ObjectMapper mapper = new ObjectMapper();
        RecipeMap unwrapped = mapper.readValue(serialized, RecipeMap.class);

        // copy over data
        this.setKeys( unwrapped.getKeys() );
        this.setValues( unwrapped.getValues() );

        // return for chaining
        return this;
    }
}