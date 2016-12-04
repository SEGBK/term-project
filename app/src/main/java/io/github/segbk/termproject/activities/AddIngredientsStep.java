package io.github.segbk.termproject.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import io.github.segbk.termproject.R;
import io.github.segbk.termproject.adapters.IngredientAdapter;
import io.github.segbk.termproject.models.Ingredient;

public class AddIngredientsStep extends AppCompatActivity {

    final Context context = this;
    private AlertDialog d;
    private ArrayList<Ingredient> ingredients;
    private ListView ingredient_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients_step);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Ingredients");
        ingredients = new ArrayList<>();
        ingredient_list = (ListView)findViewById(R.id.ingredient_list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(inflater.inflate(R.layout.ingredient_add_dialog, null))
                        // Add action buttons
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                addIngredient();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                d = builder.create();
                String[] units = {"Grams", "Cups", "Tsp", "Tbsp", "Litre", "Ml"};
                d.show();
                ((Spinner)d.findViewById(R.id.unit_spinner)).setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, units));
            }
        });
    }

    public void addIngredient(){
        EditText field_ingredient = (EditText) d.findViewById(R.id.field_ingredient);
        EditText field_quantity = (EditText) d.findViewById(R.id.field_quantity);
        Spinner unit_spinner = (Spinner)d.findViewById(R.id.unit_spinner);
        ingredients.add(new Ingredient(field_ingredient.getText().toString(), field_quantity.getText().toString() + unit_spinner.getSelectedItem().toString()));
        ingredient_list.setAdapter(new IngredientAdapter(context, 0, ingredients));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_next, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(this, AddInstructionsStep.class);
        startActivity(i);
        return true;
    }
}
