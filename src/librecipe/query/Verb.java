package librecipe.query;

/**
 * Abstraction representing a query verb.
 */
abstract class Verb {
    private String verb;

    /**
     * Creates a new Verb object with the given verb.
     * @param verb the String verb of the Verb
     */
    public Verb(String verb) {
        this.verb = verb;
    }

    /**
     * @return the string verb of this Verb object
     */
    public String toString() { return verb; }

    /**
     * Evaluates verb test on property value.
     * @param propertyValue the value of the property of the Recipe object
     * @param query the String tested against the property
     * @return true if the property value matches the query
     */
    public abstract boolean test(String propertyValue, String query);
}