package com.randallarms.dionysium.guac_a_mole;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Date;

public class Scoreboard extends AppCompatActivity {

    int points;
    int avocados;
    int chilis;
    int rotten;
    int limones;
    int multiplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scoreboard);

        //Get intent
        Intent prevIntent = getIntent();
        points = prevIntent.getIntExtra("points", 0);
        avocados = prevIntent.getIntExtra("avocados", 0);
        chilis = prevIntent.getIntExtra("chilis", 0);
        rotten = prevIntent.getIntExtra("rotten", 0);
        limones = prevIntent.getIntExtra("limones", 0);
        multiplier = prevIntent.getIntExtra("multiplier", 0);

        //Experience counter
        int experience = ((int) ( points * 0.1)) + (multiplier * 5);

        //Display individual smash scores
        TextView tvAvocados = (TextView) findViewById(R.id.avocadoScoreText);
        tvAvocados.setText("x" + Integer.toString(avocados));
        TextView tvRotten = (TextView) findViewById(R.id.rottenScoreText);
        tvRotten.setText("x" + Integer.toString(rotten));
        TextView tvChilis = (TextView) findViewById(R.id.chiliScoreText);
        tvChilis.setText("x" + Integer.toString(chilis));
        TextView tvLimones = (TextView) findViewById(R.id.limonScoreText);
        tvLimones.setText("x" + Integer.toString(limones));

        //Display game score
        TextView tv = (TextView) findViewById(R.id.score);
        tv.setText(Integer.toString(points));

        //SharedPreferences file editor
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        //Save general statistics
            //Previously saved scores (stats)
            int savedExperience = prefs.getInt("saved_experience", 0);
            int savedGamesTotal = prefs.getInt("saved_gamesTotal", 0);
            long savedDateMillis = prefs.getLong("saved_lastPlayedDate", 0);

            int savedAvocados = prefs.getInt("saved_avocados", 0);
            int savedRotten = prefs.getInt("saved_rotten", 0);
            int savedChilis = prefs.getInt("saved_chilis", 0);
            int savedLimones = prefs.getInt("saved_limones", 0);
            int savedMultiplier = prefs.getInt("saved_multiplier", 0);
            int savedChiliK = prefs.getInt("saved_chili_k", 0);
            int savedLemonless = prefs.getInt("saved_lemonless", 0);
            int savedRotten3K = prefs.getInt("saved_rotten_3k", 0);

            //Bonus daily experience check
            Date currentDate = new Date();
            Date savedDate = new Date(savedDateMillis);
            long currentMillis = currentDate.getTime();

            if ( savedDateMillis == 0 ) {
                editor.putLong("saved_lastPlayedDate", currentMillis);
            } else if ( !savedDate.toString().equals( currentDate.toString() ) ) {
                editor.putLong("saved_lastPlayedDate", currentMillis);
                experience += 1000;
            }

            //Increment total games played
            editor.putInt("saved_gamesTotal", savedGamesTotal + 1);

            //Increment total button smashes
            editor.putInt("saved_avocados", savedAvocados + avocados);
            editor.putInt("saved_rotten", savedRotten + rotten);
            editor.putInt("saved_chilis", savedChilis + chilis);
            editor.putInt("saved_limones", savedLimones + limones);

            //Save achievement statistics
                //"Control the Spice" (max number of chilis smashed w/ 1k+ score)
                if ( points >= 1000 && (chilis > savedChiliK) ) {
                    editor.putInt("saved_chili_k", chilis);
                }

                if ( points >= 1000 && chilis >= 6 ) {
                    experience += 500;
                }

                //"Ol' Fashioned" (best score without smashing any lemons, 2k+ score)
                if ( limones == 0 && points > savedLemonless ) {
                    editor.putInt("saved_lemonless", points);
                }

                if ( limones == 0 && points >= 2000  ) {
                    experience += 1000;
                }

                //"Gutbuster" (max number of rotten avocados smashed w/ 3k+ score)
                if ( points >= 3000 && rotten > savedRotten3K ) {
                    editor.putInt("saved_rotten_3k", rotten);
                }

                if ( points >= 3000 && rotten >= 6 ) {
                    experience += 2000;
                }

            //Save new hi multiplier
            if (multiplier > savedMultiplier) {
                editor.putInt("saved_multiplier", multiplier);
            }

            //Multiplier medal experience
            if (multiplier >= 73) {
                experience += 300;
            } else if (multiplier >= 68) {
                experience += 200;
            } else if (multiplier >= 65) {
                experience += 100;
            }

            //Save high score
            int hiScore = prefs.getInt("saved_hiscore", 0);

            if ( points > hiScore ) {
                editor.putInt("saved_hiscore", points);
                hiScore = points;
            }

            //High score medal experience
            if (points >= 4000) {
                experience += 2000;
            } else if (points >= 3300) {
                experience += 1000;
            } else if (points >= 2500) {
                experience += 500;
            }

            //Increment experience
            editor.putInt("saved_experience", savedExperience + experience);

            //Commit changes
            editor.apply();

        //Display the hi score
        TextView tvHiScore = (TextView) findViewById(R.id.hiScore);
        tvHiScore.setText(Integer.toString(hiScore));

        //Display the experience total
        TextView tvExperience = (TextView) findViewById(R.id.exp);
        tvExperience.setText("+" + Integer.toString(experience));

    }

    @Override
    public void onBackPressed() {

        //Set the medals screen intent
        Intent intent = new Intent(this, MainActivity.class);

        //Start the medals activity
        startActivity(intent);
        this.finish();

    }

    public void gameRestart(View v) {
        Intent intent = new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
