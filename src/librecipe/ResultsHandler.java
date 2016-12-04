package librecipe;

import java.util.ArrayList;

/**
 * Represents an event handler that should be called when
 * a search is completed.
 */
public interface ResultsHandler {
    /**
     * Called when a search is completed and results have been found.
     * @param results the list of recipe objects that were matched
     */
    public abstract void onResults(ArrayList<Recipe> results);
}