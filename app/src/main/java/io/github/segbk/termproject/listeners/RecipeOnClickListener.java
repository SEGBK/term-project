package io.github.segbk.termproject.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import librecipe.Recipe;


/**
 * Created by cssa on 2016-12-06.
 */

public class RecipeOnClickListener implements View.OnClickListener {
    private Recipe recipe;
    private Context context;

    public RecipeOnClickListener(Context context, Recipe recipe) {
        this.context = context;
        this.recipe = recipe;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, io.github.segbk.termproject.activities.Recipe.class);
        String s = "";
        for(librecipe.Step step : recipe.getPSteps()){
            s += step.getText() + "\n";
        }
        intent.putExtra("TITLE", recipe.getName());
        intent.putExtra("INGREDIENTS", s);
        intent.putExtra("STEPS", recipe.getRecipeClass());
        intent.putExtra("IMAGE", "");

        context.startActivity(intent);
    }
}
