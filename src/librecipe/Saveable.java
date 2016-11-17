package librecipe;

/**
 * Similar to Java's serializable interface, but
 * specific to this project and uses Jackson for
 * serialization.
 */
public abstract class Saveable {
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