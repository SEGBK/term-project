package io.github.segbk.termproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import io.github.segbk.termproject.R;
import io.github.segbk.termproject.models.Recipe;

/**
 * Created by cssa on 2016-11-15.
 */

public class RecipeMainAdapter extends ArrayAdapter<Recipe> {
    public RecipeMainAdapter(Context context, int resource, Recipe[] objects) {
        super(context, resource, objects);
    }

    public RecipeMainAdapter(Context context, int resource, List<Recipe> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.recipe_layout_item, null);
        }

        Recipe item = getItem(position);

        ((TextView)v.findViewById(R.id.item_title)).setText(item.getTitle());
        ((TextView)v.findViewById(R.id.item_cook_time)).setText(item.getIngredients());
        ((TextView)v.findViewById(R.id.item_description)).setText("Description of recipe.");

        return v;

    }
}
