
package test;
import java.util.*;
import librecipe.*;
import test.util.*;

public class unitTest extends Test {
    private recipe_Test_cases1 test;
    public unitTest() {
        super("Steps test");
    }

    public void test(Runnable end) throws Throwable {
      test = new recipe_Test_cases1();
      Recipe recipe = test.testCase;
      equal(recipe.getSteps().get(0).getText(),"heat the pan with 5 mins","Get step description test");


      equal(recipe.getSteps().get(0).getTime(), 5.0,"Get time consume test");
      recipe.getSteps().get(0).setTime(10.0);

      equal(recipe.getSteps().get(0).getTime (),10.0,"Set time consume of the step");

      equal(recipe.getSteps().get(0).setText("overwrite into setText testing").getText(),"overwrite into setText testing","Description testing");

      recipe.removeStep(0);
      equal(recipe.getSteps().get(0).getText(),"flip with each 2 minutes 10 times","remove references check");
      end.run();
    }
    public class recipe_Test_cases1{
        Recipe testCase;
        public String[] unit;
        public String[] recipe_Name;
        public String[] ingredient_Name;
        public String[] unitofServe;
        public String[] unitofIngredient;

          public recipe_Test_cases1(){
            this.testCase=this.generatetest();
          };

        public Recipe generatetest(){
          Recipe test1 =  new Recipe ();
           test1.setName("Mother deep fried Chicken burger");
           String [] ingredients = new String [] {"Chicken breast", "any kind of burn",
                                                  "thousand island sources", "Lettuces",
                                                  "sliced tamato","sliced Pickles"," flour","eggs",
                                                  "pre-sliced chill", "pre-sliced herbs"};
          String [] unitsForIngredient = new String[]{"g","pieces", "tea spoon","pieces","pieces","pieces","tea spoon","pieces","g","g"};
          Integer[] amountOfIngredients= new Integer[]{500,4,2,4,4,2,2,1,5,5};
          String [] Steps = new String[]{ "cover the chicken with egg white and flour ","Toaste the bun with 2 minutes","heat the pan with 5 mins",
                                          "place olive oil into the hot pan and place the chicken into the pan","flip with each 2 minutes 10 times",
                                          "Let the chicken breast to rest for 5 minutes", "mix the thousand with pre-sliced chill and pre-sliced herbs",
                                          "place the sauces on the bun","place the lettuces, tamato and pickles on top of the bun","place the chicken on the bun"};
          Double[] timeConsume= new Double[]{2.0,0.0,5.0,0.0,20.0,5.0,0.0,0.0,0.0,0.0};

          for (int i=0;i<Steps.length;i++){
            if ( timeConsume[i]<=2.0){
              Step astep =new Step();
              astep.setText(Steps[i]);astep.setTime(timeConsume[i]);
              astep.addIngredient(ingredients[i],amountOfIngredients[i],unitsForIngredient[i]);
              test1.addPrepStep(astep);
            }else{
              Step astep =new Step();
              astep.setText(Steps[i]);astep.setTime(timeConsume[i]);
              astep.addIngredient(ingredients[i],amountOfIngredients[i],unitsForIngredient[i]);
              test1.addStep(astep);
            }
          }



        return test1;
      }

      public String getIngredientFromrecipe(String ingre){
        for ( String i: testCase.iterateIngredients()){
          if(i == ingre) return i;
        }
        return null;
      }

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
