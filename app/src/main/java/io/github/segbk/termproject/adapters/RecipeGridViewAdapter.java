package io.github.segbk.termproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.github.segbk.termproject.R;
import librecipe.Recipe;

/**
 * Created by cssa on 2016-12-06.
 */

public class RecipeGridViewAdapter extends ArrayAdapter<Recipe> {

    public RecipeGridViewAdapter(Context context, int resource, librecipe.Recipe[] objects) {
        super(context, resource, objects);
    }

    public RecipeGridViewAdapter(Context context, int resource, ArrayList<Recipe> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.layout_recipe_homepage, null);
        }

        librecipe.Recipe item = getItem(position);
        int x = 200 + position;
        int y = 200 + position;

        ((TextView)v.findViewById(R.id.title)).setText(item.getName());
        ((TextView)v.findViewById(R.id.category)).setText(item.getCategory());
        ImageView img_background = (ImageView)v.findViewById(R.id.food_image);
        Picasso.with(getContext()).load("http://lorempixel.com/"+ x +"/"+ y +"/food/").networkPolicy(NetworkPolicy.NO_CACHE).into(img_background);
        // Picasso.with(getContext()).invalidate("http://lorempixel.com/"+ x +"/"+ y +"/food/");
        return v;

    }
}
