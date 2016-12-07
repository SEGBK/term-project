package io.github.segbk.termproject.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import io.github.segbk.termproject.R;
import io.github.segbk.termproject.adapters.RecipeMainAdapter;
import io.github.segbk.termproject.listeners.RecipeOnClickListener;
import librecipe.*;
import librecipe.Recipe;

public class SearchActivity extends AppCompatActivity {
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final String query = getIntent().getStringExtra("query");

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Searching for");
        pd.setMessage(query);
        pd.show();
        try {
            final CookBook book = new CookBook();
            book.onReady(new Runnable() {
                public void run() {
                    book.search(query, new ResultsHandler() {
                        @Override
                        public void onResults(final ArrayList<librecipe.Recipe> arrayList) {

                            pd.dismiss();
                            SearchActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    final GridView recipeList = (GridView) findViewById(R.id.searchGrid);
                                    SearchActivity.this.runOnUiThread(new Runnable() {
                                        public void run() {
                                            RecipeMainAdapter recipeMainAdapter = new RecipeMainAdapter(context, R.layout.recipe_layout_item, arrayList);
                                            for (int i = 0; i < arrayList.size(); i++) {
                                                recipeList.setAdapter(new RecipeMainAdapter(context, 0, arrayList));
                                            }
                                        }
                                    });
                                }
                            });

                        }
                    });
                }
            });
        } catch (Exception ex){
            pd.dismiss();
            Snackbar.make(findViewById(R.id.activity_search), "Unable to warm up Cookbook", Snackbar.LENGTH_SHORT).show();
        }



        Log.d("","");
    }
}
