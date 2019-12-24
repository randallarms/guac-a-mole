package com.randallarms.dionysium.guac_a_mole;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    //Round start intent object instantiation
    Intent intent;

    //SharedPreferences
    SharedPreferences prefs;

    //Checks if the tutorial was completed
    boolean tutorial = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        tutorial = prefs.getBoolean("tutorial", false);

    }

    public void onButtonTap(View v) {

        if ( !tutorial ) {

            //Set the round start intent for tutorial
            intent = new Intent(this, SmashTutorial.class);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("tutorial", true);
            editor.apply();

        } else {

            //Set the round start intent
            intent = new Intent(this, SmashActivity.class);

            //Ready toast
            Toast ready = Toast.makeText(getApplicationContext(), "Ready, set, GUAC!", Toast.LENGTH_SHORT);
            ready.show();

        }

        //Start SmashActivity
        startActivity(intent);
        this.finish();

    }

    public void onMedalsButtonTap(View v) {

        //Set the medals screen intent
        intent = new Intent(this, Medals.class);

        //Start the medals activity
        startActivity(intent);
        this.finish();

    }

    public void onTutorial(View v) {

        tutorial = true;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("tutorial", true);
        editor.apply();

        intent = new Intent(this, SmashTutorial.class);

        //Start the medals activity
        startActivity(intent);
        this.finish();

    }

    public void onRecipes(View v) {

        //Start the recipes activity
        intent = new Intent(this, RecipesActivity.class);
        startActivity(intent);
        this.finish();

    }

}
