package com.randallarms.dionysium.guac_a_mole;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class RecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recipes);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void backToMenu(View v) {
        Intent intent = new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }

    public void openRecipe(View v) {
        Intent intent = new Intent(this, RecipeActivity.class);

        int recipeId;
        switch (v.getId()) {

            case R.id.picanteRecipe:
                recipeId = 1;
                break;

            case R.id.americanoRecipe:
                recipeId = 2;
                break;

            case R.id.tradicionalRecipe:
            default:
                recipeId = 0;
                break;

        }
        intent.putExtra("recipe_id", recipeId);


        startActivity(intent);
        this.finish();
    }

}