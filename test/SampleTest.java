
package test;
import java.util.*;
import librecipe.*;
import test.util.*;

public class SampleTest extends Test {
  public static String [] recipe_Name ={"Teaxs Burger ","NewYork Steaks ","Meat Subs ", "Mexcian Burger ","Cheese Cake ","Youger ","Fried Chicken ","Seasoning Salmon "};

  public static String [] ingredient_Name={"tomato ","cutted beef ","steak ","pork ","meat sauces ","pork belly ",
                             "Bacon "," Butter ","Chicken breast ","Salt","Soy Sauces ","Honey ","Powder sugar "
                             ,"Sucrose ", "Garlic "};
  public static String [] step ={"cut the ","putted into oven " ,"put the", "heating the pan ","putting oil on the pan ","seasoning ","roast in the pan ",};

    public SampleTest() {
        super("sample test");
        generatetest();
    }

    public void test(Runnable end) throws Throwable {

        end.run();
    }

    public static void generatetest(){
      ArrayList<Recipe> recipetestcase=  new ArrayList<Recipe>();

      Random randomgenerator = new Random();
      int count=20;
      while(count != 0){
        count--;
        ArrayList<Step> temp_step = new ArrayList<Step>();
        int rand_recipename= randomgenerator.nextInt(3);
        int rand_ingredients=randomgenerator.nextInt(3);
        int rand_step = randomgenerator.nextInt(step.length);
        Recipe  recipe_iterator = new Recipe();
        recipe_iterator.setName(recipe_Name[rand_recipename]);
        Step input_step= new Step();
        int rand_nums_steps = randomgenerator.nextInt(10)+1;
        while(rand_nums_steps != 0){
          input_step.setText(step[rand_step]+ingredient_Name[rand_ingredients]);
          input_step.setTime(randomgenerator.nextInt(10)+1);
          input_step.addIngredient(ingredient_Name[rand_ingredients],randomgenerator.nextInt(20),ingredient_Name[rand_ingredients]);
          rand_nums_steps--;
        }
        recipe_iterator.addStep(input_step);
        recipetestcase.add(recipe_iterator);
        }
      for (Recipe i:recipetestcase){
        printRecipe(i);
        }
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
