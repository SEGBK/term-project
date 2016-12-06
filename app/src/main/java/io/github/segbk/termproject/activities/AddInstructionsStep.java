package io.github.segbk.termproject.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import io.github.segbk.termproject.MainActivity;
import io.github.segbk.termproject.R;
import librecipe.CookBook;
import librecipe.Step;

public class AddInstructionsStep extends AppCompatActivity {

    final Context context = this;
    private Intent intent;
    librecipe.Recipe submitRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructions_step);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();

        getSupportActionBar().setTitle("Steps");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.instruction_add_dialog);
                dialog.setTitle("Add Ingredient");
                Button dialogButton = (Button) dialog.findViewById(R.id.btn_submit);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        submitRecipe = new librecipe.Recipe();
        submitRecipe.setName(intent.getStringExtra("name"));
        submitRecipe.setCategory(intent.getStringExtra("category"));
        submitRecipe.setType(intent.getStringExtra("type"));
        submitRecipe.setPSteps(new ArrayList<Step>());
        submitRecipe.setRecipeClass("");
        submitRecipe.setServings(12);
        submitRecipe.setSteps(new ArrayList<Step>());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_finish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        intent.setClass(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try {
            final CookBook cookBook = new CookBook();
            cookBook.onReady(new Runnable() {
                @Override
                public void run() {
                    try {
                        cookBook.save(submitRecipe);
                    } catch (Exception ex){
                        Log.d("","");
                    }
                }
            });
        } catch (Exception ex) {

        }
        startActivity(intent);
        return true;
    }
}
