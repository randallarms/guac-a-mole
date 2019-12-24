package com.randallarms.dionysium.guac_a_mole;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    int recipeId;
    Recipe recipe;
    int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recipe);

        recipeId = getIntent().getIntExtra("recipe_id", 0);
        recipe = new Recipe();
        recipe.setId(recipeId);

        //Set the recipe name
        TextView recipeNameTxt = (TextView) findViewById(R.id.recipeSubTitleText);
        recipeNameTxt.setText(recipe.name);

        //Set the ingredients
        setIngredients(recipe);

        //Set the instructions to first text
        setInstructions(currentPage);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void backToMenu(View v) {
        Intent intent = new Intent(this, RecipesActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void pageLeft(View v) {

        //Change the page
        if (currentPage > 0) {
            currentPage--;
            setInstructions(currentPage);
        }

        //Conceal the right arrow button
        if (currentPage < 1) {
            findViewById(R.id.pageBack).setVisibility(View.GONE);
        }

        //Reveal the right arrow button
        if (currentPage == 4) {
            findViewById(R.id.pageNext).setVisibility(View.VISIBLE);
        }

    }

    public void pageRight(View v) {

        //Change the page
        if (currentPage < 5) {
            currentPage++;
            setInstructions(currentPage);
        }

        //Conceal the left arrow button
        if (currentPage > 4) {
            findViewById(R.id.pageNext).setVisibility(View.GONE);
        }

        //Reveal the left arrow button
        if (currentPage == 1) {
            findViewById(R.id.pageBack).setVisibility(View.VISIBLE);
        }

    }

    public void setIngredients(Recipe recipe) {

        int count = 0;

        for (String[] ingredient : recipe.ingredients) {
            setIngredient(ingredient, count);
            count++;
        }

    }

    public void setIngredient(String[] ingredient, int order) {

        RelativeLayout ingredientsView = (RelativeLayout) findViewById(R.id.scrollLayoutIngredients);
        RelativeLayout itemWrapper = new RelativeLayout(this);

        //For the item image
        ImageView img = new ImageView(this);

        img.setImageDrawable(null);
        img.setImageDrawable(getResources().getDrawable(recipe.getIngredientImgId(ingredient[0])));
        img.setBackgroundColor(Color.TRANSPARENT);
        img.setTag(ingredient[0]);

        itemWrapper.addView(img);

        img.setLayoutParams(layoutParamsImg());

        //For the item amount text
        TextView amount = new TextView(this);

        amount.setText(ingredient[1]);
        amount.setTextColor(getResources().getColor(R.color.guacamoleYellow));
        amount.setTextSize(16);
        amount.setBackgroundResource(R.drawable.rounded_corner);

        itemWrapper.addView(amount);

        amount.setLayoutParams(layoutParamsNum());

        //Add the image and text combo to the scroll view
        ingredientsView.addView(itemWrapper);
        itemWrapper.setLayoutParams(layoutParams(order));

    }

    public RelativeLayout.LayoutParams layoutParams(int order) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);


        params.addRule(RelativeLayout.CENTER_VERTICAL, 1);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1);
        params.leftMargin = 300 * order;
        params.height = 350;
        params.width = 350;

        return params;

    }

    public RelativeLayout.LayoutParams layoutParamsImg() {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        params.addRule(RelativeLayout.CENTER_IN_PARENT, 1);
        params.height = 300;
        params.width = 300;

        return params;

    }

    public RelativeLayout.LayoutParams layoutParamsNum() {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, 1);

        return params;

    }

    public void setInstructions(int page) {
        TextView instructionsTxt = (TextView) findViewById(R.id.instructionsText);
        instructionsTxt.setText(recipe.instructions.get(page));
    }

    public class Recipe {

        int id = 0;
        String name = "Guacamole";

        ArrayList<String[]> ingredients = new ArrayList<>();
        ArrayList<String> instructions = new ArrayList<>();

        public void setId(int id) {
            this.id = id;
            setDetails();
        }

        public void setDetails() {

            ingredients.add(new String[]{"avocado", "x3 whole"});
            ingredients.add(new String[]{"salt", "x1/2 tsp"});

            switch (id) {

                //Picante (Spicy)
                case 1:

                    //Name
                    name = "Guacamole Picante";

                    //Ingredients
                    ingredients.add(new String[]{"lime", "x1, juice"});
                    ingredients.add(new String[]{"tomato", "x1/2, dice"});
                    ingredients.add(new String[]{"onion", "x1/2, dice"});
                    ingredients.add(new String[]{"cilantro", "x1 tbsp, chop"});
                    ingredients.add(new String[]{"jalapeno", "x1, dice"});

                    //Instructions
                    instructions.add("Guacamole picante is spicy guac. " +
                            "\n\nMakes about one large bowl for 4-6 people.");
                    instructions.add("Carefully cut the avocado in half from top to bottom, " +
                            "cutting around the avocado pit (seed).");
                    instructions.add("Remove the pit and scoop out the avocado pulp into " +
                            "a large bowl.");
                    instructions.add("Using a masher, pestle, or blunt utensil like a spoon, " +
                            "mash the avocado pulp until no large chunks remain.");
                    instructions.add("Now, add the salt and lime, constantly mixing as you do. " +
                            "\n\nThen, add the cilantro, tomato, jalapeno, and onion, mixing lightly.");
                    instructions.add("Your guacamole is ready! Leave out at room temperature " +
                            "to serve as a dip or topping. \n\nBe sure to refrigerate extras.");

                    break;

                //Americano (American)
                case 2:

                    //Name
                    name = "Guacamole Americano";

                    //Ingredients
                    ingredients.add(new String[]{"onion powder", "x1 dash"});
                    ingredients.add(new String[]{"sour cream", "x1 tbsp"});
                    ingredients.add(new String[]{"salsa", "x1 tbsp, chop"});

                    //Instructions
                    instructions.add("Guacamole Americano is American-style guac. " +
                            "\n\nMakes about one large bowl for 4-6 people.");
                    instructions.add("Carefully cut the avocado in half from top to bottom, " +
                            "cutting around the avocado pit (seed).");
                    instructions.add("Remove the pit and scoop out the avocado pulp into " +
                            "a large bowl.");
                    instructions.add("Using a masher, pestle, or blunt utensil like a spoon, " +
                            "mash the avocado pulp until no large chunks remain.");
                    instructions.add("Now, add the salt and onion powder, constantly mixing as you do. " +
                            "\n\nThen, add the sour cream and salsa, mixing lightly.");
                    instructions.add("Your guacamole is ready! Leave out at room temperature " +
                            "to serve as a dip or topping. \n\nBe sure to refrigerate extras.");

                    break;

                //Tradicional (Traditional)
                case 0:
                default:

                    //Name
                    name = "Guacamole Tradicional";

                    //Ingredients
                    ingredients.add(new String[]{"lime", "x1, juice"});
                    ingredients.add(new String[]{"tomato", "x1/2, dice"});
                    ingredients.add(new String[]{"onion", "x1/2, dice"});
                    ingredients.add(new String[]{"cilantro", "x1 tbsp, chop"});

                    //Instructions
                    instructions.add("Guacamole tradicional is traditional guac. " +
                            "\n\nMakes about one large bowl for 4-6 people.");
                    instructions.add("Carefully cut the avocado in half from top to bottom, " +
                            "cutting around the avocado pit (seed).");
                    instructions.add("Remove the pit and scoop out the avocado pulp into " +
                            "a large bowl.");
                    instructions.add("Using a masher, pestle, or blunt utensil like a spoon, " +
                            "mash the avocado pulp until no large chunks remain.");
                    instructions.add("Now, add the salt and lime, constantly mixing as you do. " +
                            "\n\nThen, add the cilantro, tomato, and onion, mixing lightly.");
                    instructions.add("Your guacamole is ready! Leave out at room temperature " +
                            "to serve as a dip or topping. \n\nBe sure to refrigerate extras.");

                    break;

            }

        }

        public int getIngredientImgId(String ingredient) {

            switch (ingredient) {

                case "salt":
                case "onion powder":
                    return R.drawable.salt_shaker_button;
                case "lime":
                    return R.drawable.limon_button;
                case "onion":
                    return R.drawable.onion_button;
                case "cilantro":
                    return R.drawable.cilantro_button;
                case "tomato":
                    return R.drawable.tomato_button;
                case "jalapeno":
                    return R.drawable.chili_button;
                case "sour cream":
                    return R.drawable.sour_cream_button;
                case "salsa":
                    return R.drawable.salsa_button;
                case "avocado":
                default:
                    return R.drawable.avocado_button;

            }

        }

    }

}
