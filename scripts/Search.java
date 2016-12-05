import librecipe.Recipe;
import librecipe.CookBook;
import librecipe.ResultsHandler;
import java.util.ArrayList;

/**
 * Runs a search against Firebase.
 */
public class Search {
    public static void main(String[] args) throws Throwable {
        final StringBuilder builder = new StringBuilder();
        for (String s : args) builder.append(s + " ");

        final CookBook book = new CookBook();
        book.onReady(new Runnable() {
            public void run() {
                System.out.format("Searching: '%s' ...\n", builder.toString());
                book.search(builder.toString(), new ResultsHandler() {
                    public void onResults(ArrayList<Recipe> recipes) {
                        System.out.format("Results:\n");
                        for (Recipe r : recipes) System.out.format(" * %s\n", r.getName());
                    }
                });
            }
        });
    }
}