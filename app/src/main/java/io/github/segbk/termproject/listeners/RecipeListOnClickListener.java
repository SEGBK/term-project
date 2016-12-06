package io.github.segbk.termproject.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import librecipe.*;
/**
 * Created by cssa on 2016-11-02.
 */

public class RecipeListOnClickListener implements AdapterView.OnItemClickListener {
    private Context context;

    public RecipeListOnClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        librecipe.Recipe selected = ((librecipe.Recipe) adapterView.getAdapter().getItem(i));
        Intent intent = new Intent(context, io.github.segbk.termproject.activities.Recipe.class);
        String s = "";
        for(librecipe.Step step : selected.getPSteps()){
            s += step.getText() + "\n";
        }
        intent.putExtra("TITLE", selected.getName());
        intent.putExtra("INGREDIENTS", s);
        intent.putExtra("STEPS", selected.getRecipeClass());
        intent.putExtra("IMAGE", "");

        context.startActivity(intent);
    }
}
