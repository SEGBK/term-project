package io.github.segbk.termproject.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.daimajia.androidanimations.library.YoYo;

import io.github.segbk.termproject.R;
import io.github.segbk.termproject.adapters.RecipeStepEditAdapter;
import io.github.segbk.termproject.enums.Categories;
import io.github.segbk.termproject.enums.Type;

import static com.daimajia.androidanimations.library.Techniques.BounceInDown;

public class NewRecipe extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        getSupportActionBar().setTitle("New Recipe");

        //LinearLayout layout = (LinearLayout)findViewById(R.id.animatelayout);
        //YoYo.with(BounceInDown).playOn(layout);

        Spinner cat_spinner = (Spinner)findViewById(R.id.category_spinner);
        ArrayAdapter<String> catSpinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Categories.getCategories());
        cat_spinner.setAdapter(catSpinnerArrayAdapter);

        Spinner type_spinner = (Spinner)findViewById(R.id.type_spinner);
        ArrayAdapter<String> typeSpinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Type.getTypes());
        type_spinner.setAdapter(typeSpinnerArrayAdapter);

        Button next_step_button = (Button)findViewById(R.id.btn_next);
        next_step_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, AddIngredientsStep.class);
        startActivity(i);
    }

}
