
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
        super("Basic Functionality check test");
    }

    public void test(Runnable end) throws Throwable {
      recipe_Test_cases1 test1= new recipe_Test_cases1();
      Recipe recipe1= test1.testCase;
      equal(recipe1.getName(),"Mother deep fried Chicken burger","test setName method");

      recipe1.setName("Just setting");
      equal(recipe1.getName(),"Just setting","Testing setName");


      String pre =recipe1.getSteps().get(recipe1.getSteps().size()-1).getText();
      equal(pre ,"Let the chicken breast to rest for 5 minutes","Testing the geter on the Step");

      String newStep= "Cutting the burger in half.";
      Step testnew = new Step();
      testnew.setTime(0);testnew.setText(newStep);recipe1.addStep(testnew);
      recipe1.addStep(testnew); Step ass = recipe1.getSteps().get(recipe1.getSteps().size()-1);
      String newer = ass.getText();
      equal(newer,"Cutting the burger in half.","Testing adding new steps");

      ArrayList<String> test = new ArrayList<String>();
      for (String i:recipe1.iterateIngredients()){
        test.add(i);
      }
      equal(test.contains("Freals"),false,"Test ingredients iterator");
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

      }

      public static  String toStringStep(String name,ArrayList<Step> steps){
        System.out.print("The name of the recipe is "+ name);
        StringBuilder result = new StringBuilder();
        for(Integer i=0; i<steps.size(); i++){
          result.append((i+1)+". "+steps.get(i).getText()+"\n");
        }
        System.out.println(result.toString());
        return result.toString();
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
