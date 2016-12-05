package io.github.segbk.termproject.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import io.github.segbk.termproject.models.Recipe;
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
        Recipe selected = ((Recipe) adapterView.getAdapter().getItem(i));
        Intent intent = new Intent(context, io.github.segbk.termproject.activities.Recipe.class);

        intent.putExtra("TITLE", selected.getTitle());
        intent.putExtra("INGREDIENTS", selected.getIngredients());
        intent.putExtra("STEPS", selected.getSteps());
        intent.putExtra("IMAGE", selected.getImage());

        context.startActivity(intent);
    }
}
