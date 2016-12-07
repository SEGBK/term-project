package io.github.segbk.termproject.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import io.github.segbk.termproject.R;

public class CategoriesActivity extends AppCompatActivity {
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ImageView appe = (ImageView)findViewById(R.id.appe);
        ImageView main_dish = (ImageView)findViewById(R.id.main_dish);
        ImageView drink = (ImageView)findViewById(R.id.drink);
        ImageView dessert = (ImageView)findViewById(R.id.dessert);

        Picasso.with(this).load("https://www.wellbeing.com.au/wp-content/uploads/news/Appetiser_main_web.gif").into(appe);
        Picasso.with(this).load("http://www.characters.ca/wp-content/uploads/2015/05/steak.jpg").into(main_dish);
        Picasso.with(this).load("https://assets.entrepreneur.com/content/16x9/822/20150909205144-red-wine-classy-evening-dinner.jpeg").into(drink);
        Picasso.with(this).load("http://www.kraftcanada.com/-/media/kraft%20canada/recipes/620x423/c/chocolate-caramel-brownies-152180.jpg?").into(dessert);

        appe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SearchActivity.class);
                i.putExtra("query", "(category is appetizer)");
                startActivity(i);
            }
        });
        main_dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SearchActivity.class);
                i.putExtra("query", "(category is main dish)");
                startActivity(i);
            }
        });
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SearchActivity.class);
                i.putExtra("query", "(category is drink)");
                startActivity(i);
            }
        });
        dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SearchActivity.class);
                i.putExtra("query", "(category is dessert)");
                startActivity(i);
            }
        });
    }
}
