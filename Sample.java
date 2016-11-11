/**
 * An example to demonstrate recipes.
 */

import librecipe.*;

public class Sample {
    public static void main(String[] args) {
        Recipe recipe = new Recipe();

        recipe.setName("Cupcakes");

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

        // ...
        //System.out.println("---");
        //final String saved = recipe.serialize();
        // ....

        //Recipe newRecipe = new Recipe(saved);
        //printRecipe(newRecipe);
    }

    private static void printRecipe(Recipe recipe) {
        System.out.format(
            "Recipe: %s\n" +
            "Preparation time: %.2f\n" +
            "Cooking time: %.2f\n" +
            "\n",
            recipe.getName(),
            recipe.getPrepTime(),
            recipe.getCookTime()
        );

        System.out.println("Ingredients:");
        for (String step : recipe.iterateIngredients()) System.out.format(" - (%.1f %s) %s.\n", recipe.getQuantityOf(step), recipe.getUnitsOf(step), step);

        System.out.println("\nPreparation:");
        for (Step step : recipe.iteratePrep()) System.out.format(" - %s (%.1f mins).\n", step.getText(), step.getTime());

        System.out.println("\nInstructions:");
        for (Step step : recipe.iterateCook()) System.out.format(" - %s (%.1f mins).\n", step.getText(), step.getTime());
    }
}
