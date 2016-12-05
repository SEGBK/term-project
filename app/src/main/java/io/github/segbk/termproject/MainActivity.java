package io.github.segbk.termproject;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import io.github.segbk.termproject.models.Ingredient;
import io.github.segbk.termproject.models.Recipe;
import io.github.segbk.termproject.models.Step;

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

        Ingredient sugar = new Ingredient("sugar","1 TSP");
        Ingredient flour = new Ingredient("flour","2 Cups");
        Ingredient salt = new Ingredient("salt","1 Pinch");
        Ingredient water = new Ingredient("water","1 Cup");

        Ingredient[] i = {sugar, flour};
        Ingredient[] i2 = {flour, water};
        Ingredient[] i3 = {water, salt};

        Step general = new Step("Dummy Step");
        Step[] stepList = {general,general,general,general};



try {
    final CookBook book = new CookBook();

    book.onError(new EventHandler() {
        public void run(String data) {
            Log.d("COOKBOOKLOG",data);
        }
    });

    book.onReady(new Runnable() {
        public void run() {
            // here, you can hide your activity indicator and start using cookbook
            cookBook.search("(time > 0) AND (limit by 10)", new ResultsHandler() {
                public void onResults(ArrayList<librecipe.Recipe> arrayList) {
                    Log.d("", "");
                }
            });
        }
    });
} catch (Exception ex){

}

        ListView recipeList = (ListView)findViewById(R.id.recipe_list);
        ArrayList<Recipe> l = new ArrayList<Recipe>();
        l.add(new Recipe("Sugar and Flour",i,stepList, "http://weknowyourdreams.com/images/food/food-07.jpg"));
        l.add(new Recipe("Water and Flour",i2,stepList,"http://www.seattleorganicrestaurants.com/vegan-whole-foods/images/Food-Guidelines.jpg"));
        l.add(new Recipe("Saltwater",i3,stepList,"http://dreamatico.com/data_images/food/food-6.jpg"));
        recipeList.setAdapter(new RecipeMainAdapter(this, R.layout.recipe_layout_item, l));
        recipeList.setOnItemClickListener(new RecipeListOnClickListener(this));
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(context, newText, Toast.LENGTH_SHORT).show();
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
