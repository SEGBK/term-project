
package test;
import java.util.*;
import librecipe.*;
import test.util.*;


public class searchTest extends Test{
  ArrayList<Recipe> serachTestCase;

  public searchTest(){
    super("Searching Functionality Test ");
  }

  public void test(Runner end){

  }


public void generatetest(){
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

}
