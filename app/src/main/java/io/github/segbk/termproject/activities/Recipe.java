package io.github.segbk.termproject.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.codehaus.jackson.smile.Tool;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.github.segbk.termproject.R;

public class Recipe extends AppCompatActivity {

    private String Title;
    private String Category;
    private String[] Ingredients;
    private String ImageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        Title = intent.getStringExtra("TITLE");
        Ingredients = intent.getStringArrayExtra("INGREDIENTS");
        Category = intent.getStringExtra("CATEGORY");
        ImageURL = intent.getStringExtra("IMAGE");

        TextView Content = (TextView)findViewById(R.id.recipe_content);
        CollapsingToolbarLayout Toolbar = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        Toolbar.setTitle(Title);
        String finalString = "";
        for(String s : Ingredients){
            finalString += s + "\n";
        }

        try {
            Content.setText("Category:" + Category + "\n" + finalString);
        } catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}

