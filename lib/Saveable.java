/**
 * Similar to Java's serializable interface, but
 * specific to this project.
 */

package lib;

public abstract class Saveable {
    /**
     * This is the constant by which different datums must
     * be separated in a serialized string.
     */
    public final static char DELIM = '\0';

    /**
     * All classes extending Saveable should support
     * this type of constructor.
     */
    public Saveable(String serialized) {
        this.deserialize(serialized);
    }

    /**
     * Serializes a Saveable object into a string.
     * @return Saveable in the form of a string (contains unreadable characters)
     */
    public abstract String serialize();

    /**
     * Deserializes a string into a Saveable object.
     * @return the Saveable object for chaining
     */
    protected abstract Saveable deserialize(String serialized);
}