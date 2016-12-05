/**
 * An example to demonstrate recipes.
 */

import java.util.ArrayList;

import librecipe.*;
import librecipe.query.*;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.*;

public class Sample {
    public static void main(String[] args) throws Exception {
        Recipe recipe = new Recipe();

        recipe.setName("Cupcakes");
        recipe.setRecipeClass("Chicken"); // My cupcakes have chicken. Deal with it.
        recipe.setType("Dessert");
        recipe.setCategory("English");

        for (String step : new String[] {
            "Prep things.",
            "Do other prep stuff.",
        }) {
            Step sStep = new Step();
            sStep.setText(step);
            sStep.setTime(2.5);

            recipe.addPrepStep(sStep);
        }

        for (String step : new String[] {
            "First step. Lorem ipsum with stuff and not.",
            "Second step. Lorem ipsum with stuff and not.",
            "Third step. Lorem ipsum with stuff and not.",
            "Four step. Lorem ipsum with stuff and not.",
            "Five step. Lorem ipsum with stuff and not."
        }) {
            Step sStep = new Step();
            sStep.setText(step);
            sStep.setTime(5);

            sStep.addIngredient("Fish", 1, "fillet");
            sStep.addIngredient("Paparika", 30, "g");

            recipe.addStep(sStep);
        }

        printRecipe(recipe);

        // test query
        //final String query = "(((name has cake)) AND (prepTime <= 10))";
        //System.out.format("\nquery: %s => %s\n", query, (new Query(query)).matches(recipe));

        System.out.println("---");
        final CookBook book = new CookBook();
        book.onError(new EventHandler() {
            public void run(String data) {
                System.out.println(data);
                System.exit(-1);
            }
        }).onReady(new Runnable() {
            public void run() {
                try {
                    for(int i=0;i<5;i++)book.add(recipe);
                    //printRecipe(book.get(recipe.getName()));

                    /*book.search("(((name has cake)) AND (prepTime <= 10))", new ResultsHandler() {
                        public void onResults(ArrayList<Recipe> results) {
                            System.out.format("Results:\n");
                            for (Recipe r : results) System.out.println("* " + r.getName()); // printRecipe(r);
                        }
                    });/**/
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