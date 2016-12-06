package io.github.segbk.termproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import io.github.segbk.termproject.R;

import librecipe.*;

/**
 * Created by cssa on 2016-11-16.
 */

public class IngredientAdapter extends ArrayAdapter<Ingredient> {

    public IngredientAdapter(Context context, int resource, List<Ingredient> objects) {
        super(context, resource, objects);
    }

    public IngredientAdapter(Context context, int resource, Ingredient[] objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.ingredient_item_layout, null);
        }

        Ingredient item = getItem(position);

        if (item.getName()!= null){
            ((TextView)v.findViewById(R.id.ingredient_name)).setText(item.getName());
        }
        if (String.valueOf(item.getQuantity()) != null){
            ((TextView)v.findViewById(R.id.ingredient_quantity)).setText(String.valueOf(item.getQuantity()));
        }
        return v;
    }
}
