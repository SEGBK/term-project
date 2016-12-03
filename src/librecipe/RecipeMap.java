package librecipe;

import java.util.LinkedHashMap;
import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.JsonMappingException;

public class RecipeMap extends Saveable {
    private LinkedHashMap<String,String> map;
    public LinkedHashMap<String,String> getMap() { return map; }
    public void setMap(LinkedHashMap<String,String> map) { this.map = map; }

    /**
     * Creates a new empty RecipeMap object.
     */
    public RecipeMap() {
        this.map = new LinkedHashMap<>();
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
    public String get(String key) { return this.map.get(key); }

    /**
     * Map a recipe name to recipe ID.
     * @param key the name of a recipe
     * @param value the ID of the recipe
     */
    public void put(String key, String value) { this.map.put(key, value); }

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
        this.setMap(unwrapped.getMap());

        // return for chaining
        return this;
    }
}