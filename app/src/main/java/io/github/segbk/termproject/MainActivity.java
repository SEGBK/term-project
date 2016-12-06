package io.github.segbk.termproject;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import io.github.segbk.termproject.activities.NewRecipe;
import io.github.segbk.termproject.adapters.RecipeMainAdapter;
import io.github.segbk.termproject.listeners.RecipeListOnClickListener;


import librecipe.*;
import librecipe.query.*;



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
                            final ListView recipeList = (ListView)findViewById(R.id.recipe_list);

                            MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    recipeList.setAdapter(new RecipeMainAdapter(context, R.layout.recipe_layout_item, arrayList));
                                    recipeList.setOnItemClickListener(new RecipeListOnClickListener(context));
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

        } else if (id == R.id.nav_list) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_share) {

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
                final ProgressDialog pd = new ProgressDialog(context);
                pd.setTitle("Searching...");
                pd.show();
                if (query.equals(null) || query.equals("")) return true;
                cookBook.search(query, new ResultsHandler() {
                    @Override
                    public void onResults(final ArrayList<librecipe.Recipe> arrayList) {
                        pd.dismiss();
                        MainActivity.this.runOnUiThread(new Runnable() {
                            final ListView recipeList = (ListView)findViewById(R.id.recipe_list);
                            public void run() {
                                recipeList.setAdapter(new RecipeMainAdapter(context, R.layout.recipe_layout_item, arrayList));
                                recipeList.setOnItemClickListener(new RecipeListOnClickListener(context));
                            }
                        });
                    }
                });
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
