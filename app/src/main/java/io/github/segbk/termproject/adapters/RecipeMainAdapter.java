package io.github.segbk.termproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.github.segbk.termproject.R;
import librecipe.*;

/**
 * Created by cssa on 2016-11-15.
 */

public class RecipeMainAdapter extends ArrayAdapter<librecipe.Recipe> {
    public RecipeMainAdapter(Context context, int resource, librecipe.Recipe[] objects) {
        super(context, resource, objects);
    }

    public RecipeMainAdapter(Context context, int resource, ArrayList<librecipe.Recipe> objects) {
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

        librecipe.Recipe item = getItem(position);

        ((TextView)v.findViewById(R.id.item_title)).setText(item.getName());
        ((TextView)v.findViewById(R.id.item_cook_time)).setText(item.getCategory());
        ((TextView)v.findViewById(R.id.item_description)).setText("Description of recipe.");
        ImageView img_background = (ImageView)v.findViewById(R.id.img_background);
        // Picasso.with(getContext()).load(R.drawable.food).into(img_background);
        return v;

    }
}
