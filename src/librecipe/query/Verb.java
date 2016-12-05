package librecipe.query;

import java.util.ArrayList;
import librecipe.Recipe;

/**
 * Abstraction representing a query verb.
 */
abstract class Verb {
    private String[] verbs;

    /**
     * Creates a new Verb object with the given verb.
     * @param verbs the list of verb aliases
     */
    public Verb(String[] verbs) {
        this.verbs = verbs;
    }

    /**
     * Matches a given verb against this Verb object.
     * @param verb a single String verb
     * @return true if the given string is an alias for this verb
     */
    public boolean is(String verb) {
        for (String v : this.verbs) {
            if (v.equalsIgnoreCase(verb)) return true;
        }

        return false;
    }

    public String toString() {
        return this.verbs[0];
    }

    /**
     * Evaluates verb test on property value.
     * @param propertyValue the value of the property of the Recipe object
     * @param query the String tested against the property
     * @return true if the property value matches the query
     */
    public abstract boolean test(String propertyValue, String query, ArrayList<Recipe> results);
}