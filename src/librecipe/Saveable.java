package librecipe;

/**
 * Similar to Java's serializable interface, but
 * specific to this project.
 */
public abstract class Saveable {
    /**
     * This is the constant by which different datums must
     * be separated in a serialized string.
     */
    public final static String DELIM = "\0";
 
    /**
     * Serializes a Saveable object into a string.
     * @return Saveable in the form of a string (contains unreadable characters)
     */
    public abstract String serialize();

    /**
     * Deserializes a string into a Saveable object.
     * @param serialized the original serialized object as a string
     * @return the Saveable object for chaining
     */
    protected abstract Saveable deserialize(String serialized);
}
