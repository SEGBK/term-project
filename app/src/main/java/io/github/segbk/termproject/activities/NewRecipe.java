package io.github.segbk.termproject.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.YoYo;

import io.github.segbk.termproject.R;

import static com.daimajia.androidanimations.library.Techniques.BounceInDown;

public class NewRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        LinearLayout layout = (LinearLayout)findViewById(R.id.animatelayout);
        YoYo.with(BounceInDown).playOn(layout);
    }
}
