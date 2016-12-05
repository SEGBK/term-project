package librecipe;

import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

/**
 * Similar to Java's serializable interface, but
 * specific to this project and uses Jackson for
 * serialization.
 */
public abstract class Saveable {
    /**
     * Serializes a Saveable object into a string.
     * @throws JsonMappingException if any errors occur in deserialization
     * @throws IOException if any errors occur in deserialization
     * @return Saveable in the form of a string (contains unreadable characters)
     */
    public abstract String serialize() throws JsonMappingException, IOException;

    /**
     * Deserializes a string into a Saveable object.
     * @param serialized the original serialized object as a string
     * @throws JsonParseException if any errors occur in deserialization
     * @throws IOException if any errors occur in deserialization
     * @return the Saveable object for chaining
     */
    public abstract Saveable deserialize(String serialized) throws JsonParseException, IOException;
}