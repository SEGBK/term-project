
package test;
import java.util.*;
import librecipe.*;
import test.util.*;

public class unitTest extends Test {
  public String [] recipe_Name ={"Teaxs Burger ","NewYork Steaks ","Meat Subs ", "Mexcian Burger ","Cheese Cake ","Youger ","Fried Chicken ","Seasoning Salmon "};

  public String [] ingredient_Name={"tomato ","cutted beef ","steak ","pork ","meat sauces ","pork belly ",
                             "Bacon "," Butter ","Chicken breast ","Salt","Soy Sauces ","Honey ","Powder sugar "
                             ,"Sucrose ", "Garlic "};
  public  String [] step ={"cut the ","putted into oven " ,"put the", "heating the pan ","putting oil on the pan ","seasoning ","roast in the pan ",};

    public unitTest() {
        super("Step test");
    }

    public void test(Runnable end) throws Throwable {

      end.run();
    }




      public String toStringStep(String name,ArrayList<Step> steps){
        System.out.print("The name of the recipe is "+ name);
        StringBuilder result = new StringBuilder();
        for(Integer i=0; i<steps.size(); i++){
          result.append((i+1)+". "+steps.get(i).getText()+"\n");
        }
        System.out.println(result.toString());
        return result.toString();
      }

      private void printRecipe(Recipe recipe) {
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
