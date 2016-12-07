package io.github.segbk.termproject;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import io.github.segbk.termproject.activities.CategoriesActivity;
import io.github.segbk.termproject.activities.NewRecipe;
import io.github.segbk.termproject.activities.SearchActivity;
import io.github.segbk.termproject.adapters.RecipeMainAdapter;


import io.github.segbk.termproject.listeners.RecipeOnClickListener;
import librecipe.*;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
        private Context context = this;
        private CookBook cookBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        final ImageView popular_food = (ImageView)findViewById(R.id.popular_food);
        final ImageView recent_food = (ImageView)findViewById(R.id.recent_food);
        Animation a = AnimationUtils.loadAnimation(this, R.anim.rotate);
        a.setDuration(1000);
        popular_food.startAnimation(a);
        recent_food.startAnimation(a);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            final CookBook book = new CookBook();

            book.onError(new EventHandler() {
                public void run(String data) {
                    Log.d("COOKBOOKLOG",data);
                }
            });

            book.onReady(new Runnable() {
                public void run() {
                    cookBook = book;
                    book.search("(time > 0) AND (limit by 10)", new ResultsHandler() {
                        public void onResults(final ArrayList<librecipe.Recipe> arrayList) {
                            final LinearLayout recipeList = (LinearLayout)findViewById(R.id.recentScrollLayout);
                            MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    recent_food.setVisibility(View.GONE);
                                    RecipeMainAdapter recipeMainAdapter = new RecipeMainAdapter(context, R.layout.recipe_layout_item, arrayList);
                                    for(int i = 0; i < arrayList.size(); i++){
                                        View v = recipeMainAdapter.getView(i, null, null);
                                        v.setOnClickListener(new RecipeOnClickListener(context, arrayList.get(i)));
                                        recipeList.addView(v);                                    }
                                }
                            });
                        }
                    });
                    book.search("(time > 0) AND (limit by 10)", new ResultsHandler() {
                        public void onResults(final ArrayList<librecipe.Recipe> arrayList) {
                            final LinearLayout recipeList = (LinearLayout)findViewById(R.id.popularScrollLayout);
                            MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    popular_food.setVisibility(View.GONE);
                                    RecipeMainAdapter recipeMainAdapter = new RecipeMainAdapter(context, R.layout.recipe_layout_item, arrayList);
                                    for(int i = 0; i < arrayList.size(); i++){
                                        View v = recipeMainAdapter.getView(i, null, null);
                                        v.setOnClickListener(new RecipeOnClickListener(context, arrayList.get(i)));
                                        recipeList.addView(v);
                                    }
                                }
                            });
                        }
                    });
                }
            });
        } catch (Exception ex){

        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_list) {
            Intent i = new Intent(this, CategoriesActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.equals(null) || query.equals("")) return true;

                Intent i = new Intent(context, SearchActivity.class);
                i.putExtra("query", query);
                startActivity(i);

                /*
                cookBook.search(query, new ResultsHandler() {
                    @Override
                    public void onResults(final ArrayList<librecipe.Recipe> arrayList) {

                        I//ntent i = new Intent(context, SearchActivity.class);

                        //i.putExtra("Results", arrayList);

                        //startActivity(i);


                        pd.dismiss();
                        MainActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                final LinearLayout recipeList = (LinearLayout)findViewById(R.id.popularScrollLayout);
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        RecipeMainAdapter recipeMainAdapter = new RecipeMainAdapter(context, R.layout.recipe_layout_item, arrayList);
                                        for(int i = 0; i < arrayList.size(); i++){
                                            View v = recipeMainAdapter.getView(i, null, null);
                                            v.setOnClickListener(new RecipeOnClickListener(context, arrayList.get(i)));
                                            recipeList.addView(v);
                                        }
                                    }
                                });
                            }
                        });

                    }
                });*/
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            };
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_search:
                return true;
        }
        return false;
    }

    // FAB On Click Listener
    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, NewRecipe.class);
        startActivity(i);

    }
}
