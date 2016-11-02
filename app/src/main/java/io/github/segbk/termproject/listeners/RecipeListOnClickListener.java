package io.github.segbk.termproject.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import io.github.segbk.termproject.activities.Recipe;

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
        Intent intent = new Intent(context, Recipe.class);
        context.startActivity(intent);
    }
}
