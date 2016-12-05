import java.io.BufferedReader;
import java.io.FileReader;

import librecipe.Recipe;
import librecipe.Step;
import librecipe.CookBook;

/**
 * Converts a jsonish file into Recipe objects and uploads them
 * to Firebase.
 */
public class JSON2FB {
    public static void main(String[] args) throws Throwable {
        final CookBook book = new CookBook();
        book.onReady(new Runnable() {
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("./recipes.jsonish"));

                    while (reader.ready()) {
                        Recipe recipe = new Recipe(reader.readLine());
                        try {
                            book.add(recipe);
                            System.out.format("* Deserialized: %s\n", recipe.getName());
                        } catch (Exception e) {}
                        //Thread.sleep(500);
                    }
                } catch (Exception ex) { ex.printStackTrace(); }
            }
        });
    }

    private static void printRecipe(Recipe recipe) {
        System.out.format(
            "Recipe: %s\n" +
            "Preparation time: %.2f\n" +
            "Cooking time: %.2f\n" +
            "\n",
            recipe.getName(),
            recipe.prepTime(),
            recipe.cookTime()
        );

        System.out.println("Ingredients:");
        for (String step : recipe.iterateIngredients()) System.out.format(" - (%.1f %s) %s.\n", recipe.quantityOf(step), recipe.unitsOf(step), step);

        System.out.println("\nPreparation:");
        for (Step step : recipe.iteratePrep()) System.out.format(" - %s (%.1f mins).\n", step.getText(), step.getTime());

        System.out.println("\nInstructions:");
        for (Step step : recipe.iterateCook()) System.out.format(" - %s (%.1f mins).\n", step.getText(), step.getTime());
    }
}