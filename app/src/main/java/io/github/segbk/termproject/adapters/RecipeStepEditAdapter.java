package io.github.segbk.termproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import io.github.segbk.termproject.R;
import io.github.segbk.termproject.models.Recipe;

/**
 * Created by cssa on 2016-11-14.
 */

public class RecipeStepEditAdapter extends ArrayAdapter<Recipe> {
    public RecipeStepEditAdapter(Context context, int resource, Recipe[] objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.itemlistrow, null);
        }

        Recipe item = getItem(position);

        ((EditText)v.findViewById(R.id.stepText)).setText(item.getTitle());

        return v;

    }
}
