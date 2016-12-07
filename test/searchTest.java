
package test;
import java.util.*;
import librecipe.*;
import librecipe.query.*;
import test.util.*;


public class searchTest extends Test{
  public ArrayList<Recipe> searchTestCase;
  String [] recipe_Name ={"Teaxs Burger ","NewYork Steaks ","Meat Subs ", "Mexcian Burger ","Cheese Cake ","Youger ","Fried Chicken ","Seasoning Salmon "};
  String [] ingredient_Name={"tomato ","cutted beef ","steak ","pork ","meat sauces ","pork belly ",
                             "Bacon "," Butter ","Chicken breast ","Salt","Soy Sauces ","Honey ","Powder sugar "
                             ,"Sucrose ", "Garlic "};
  String [] step ={"cut the ","putted into oven " ,"put the", "heating the pan ","putting oil on the pan ","seasoning ","roast in the pan ",};
  public searchTest(){
    super("Searching Functionality Test ");
  }

  public void test(Runnable end) throws Throwable{
    searchTestCase=this.generatetest();
    Recipe texBurger = new Recipe(); texBurger.setName("Texas Burger");
    equal(new Query("((name has Texas Burger))").matches(texBurger, null),true,"Searching names");
    equal(new Query("((name has Texas Burger))").matches(texBurger, null),true,"Searching ingreidents");
    equal(new Query("((name has Texas Burger))").matches(texBurger, null),true,"Searching and boolean");
    equal(new Query("((name has Texas Burger))").matches(texBurger, null),true,"Searching or boolean");
    equal(new Query("((name has Texas Burger))").matches(texBurger, null),true,"Searching related names");
    equal(new Query("((name has Texas Burger))").matches(texBurger, null),true,"Searching related ingreidents");
    end.run();
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

public ArrayList<Recipe> generatetest(){
  ArrayList<Recipe> recipetestcase=  new ArrayList<Recipe>();

  Random randomgenerator = new Random();
  int count=5;
  while(count != 0){
    count--;
    ArrayList<Step> temp_step = new ArrayList<Step>();
    // randomly generate the recipe name
    int rand_recipename= randomgenerator.nextInt(3);int rand_ingredients=randomgenerator.nextInt(3);
    int rand_step = randomgenerator.nextInt(step.length);
    Recipe  recipe_iterator = new Recipe();
    recipe_iterator.setName(recipe_Name[rand_recipename]);
    Step input_step= new Step();
    int rand_nums_steps = 1;
    while(rand_nums_steps != 0){
      input_step.setText(step[rand_step]+ingredient_Name[rand_ingredients]);
      input_step.setTime(randomgenerator.nextInt(10)+1);
      input_step.addIngredient(ingredient_Name[rand_ingredients],randomgenerator.nextInt(20),ingredient_Name[rand_ingredients]);
      rand_nums_steps--;
    }
    recipe_iterator.addStep(input_step);
    recipetestcase.add(recipe_iterator);
    }

    return recipetestcase;
  }
}
