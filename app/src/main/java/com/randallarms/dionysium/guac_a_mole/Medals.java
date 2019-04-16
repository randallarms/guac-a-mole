package com.randallarms.dionysium.guac_a_mole;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Medals extends AppCompatActivity {

    FragmentManager manager;

    String nextGoalAvocados = "1000";
    String nextGoalMultiplier = "65";
    String nextGoalPoints = "2500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_medals);

        manager = getFragmentManager();

        //SharedPreferences saved values
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Get the ImageView objects of each medal
        ImageView avocadoMedal = (ImageView) findViewById(R.id.avocadoMedal);
        ImageView stirMedal = (ImageView) findViewById(R.id.stirMedal);
        ImageView pointsMedal = (ImageView) findViewById(R.id.pointsMedal);
        ImageView spiceMedal = (ImageView) findViewById(R.id.spiceMedal);
        ImageView lemonlessMedal = (ImageView) findViewById(R.id.lemonlessMedal);
        ImageView rottenMedal = (ImageView) findViewById(R.id.rottenMedal);

        //Previously saved scores (stats)
        int savedAvocados = prefs.getInt("saved_avocados", 0);
        int savedMultiplier = prefs.getInt("saved_multiplier", 0);
        int savedHiScore = prefs.getInt("saved_hiscore", 0);
        int savedChiliK = prefs.getInt("saved_chili_k", 0);
        int savedLemonless = prefs.getInt("saved_lemonless", 0);
        int savedRotten3K = prefs.getInt("saved_rotten_3k", 0);

        //"Very Guac" medal: total avocados (1k/3k/10k)
        if (savedAvocados >= 10000) {
            avocadoMedal.setImageDrawable(getResources().getDrawable(R.drawable.medal_gold_avocado));
            nextGoalAvocados = "10000";
        } else if (savedAvocados >= 3000) {
            avocadoMedal.setImageDrawable(getResources().getDrawable(R.drawable.medal_silver_avocado));
            nextGoalAvocados = "10000";
        } else if (savedAvocados >= 1000) {
            avocadoMedal.setImageDrawable(getResources().getDrawable(R.drawable.medal_bronze_avocado));
            nextGoalAvocados = "3000";
        }

        TextView avocadoMedalCount = (TextView) findViewById(R.id.avocadoMedalCount);
        avocadoMedalCount.setText(savedAvocados + "/" + nextGoalAvocados + "");

        //"Spun Out" medal: top stir multiplier (x65/x70/x75)
        if (savedMultiplier >= 75) {
            stirMedal.setImageDrawable(getResources().getDrawable(R.drawable.medal_gold_stir));
            nextGoalMultiplier = "75";
        } else if (savedMultiplier >= 70) {
            stirMedal.setImageDrawable(getResources().getDrawable(R.drawable.medal_silver_stir));
            nextGoalMultiplier = "75";
        } else if (savedMultiplier >= 65) {
            stirMedal.setImageDrawable(getResources().getDrawable(R.drawable.medal_bronze_stir));
            nextGoalMultiplier = "70";
        }

        TextView multiplierMedalCount = (TextView) findViewById(R.id.multiplierMedalCount);
        multiplierMedalCount.setText(savedMultiplier + "/" + nextGoalMultiplier + "");

        //"Much Score" medal: hi score (2.5k/3.3k/4.0k)
        if (savedHiScore >= 4000) {
            pointsMedal.setImageDrawable(getResources().getDrawable(R.drawable.medal_gold_points));
            nextGoalPoints = "4000";
        } else if (savedHiScore >= 3300) {
            pointsMedal.setImageDrawable(getResources().getDrawable(R.drawable.medal_silver_points));
            nextGoalPoints = "4000";
        } else if (savedHiScore >= 2500) {
            pointsMedal.setImageDrawable(getResources().getDrawable(R.drawable.medal_bronze_points));
            nextGoalPoints = "3300";
        }

        TextView pointsMedalCount = (TextView) findViewById(R.id.pointsMedalCount);
        pointsMedalCount.setText(savedHiScore + "/" + nextGoalPoints + "");

        //"Control the Spice" medal: smash 6 chilis & score over 1k
        if (savedChiliK >= 6) {
            spiceMedal.setImageDrawable(getResources().getDrawable(R.drawable.medal_gold_spice));
        }

        //"Ol' Fashioned" medal: smash 0 limones & score over 2k
        if (savedLemonless >= 2000) {
            lemonlessMedal.setImageDrawable(getResources().getDrawable(R.drawable.medal_gold_lemonless));
        }

        //"Gutbuster" medal: smash 6 rotten avocados & score over 3k
        if (savedRotten3K >= 6) {
           rottenMedal.setImageDrawable(getResources().getDrawable(R.drawable.medal_gold_gutbuster));
        }

    }

    @Override
    public void onBackPressed() {

        //Set the medals screen intent
        Intent intent = new Intent(this, MainActivity.class);

        //Start the medals activity
        startActivity(intent);
        this.finish();

    }

    public void backToMenu(View v) {
        Intent intent = new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void openTrophy(View v) {
        Intent intent = new Intent(this, TrophyActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void openStats(View v) {
        Intent intent = new Intent(this, StatsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void dialogMedal(View v) {

        String name = "Medal";
        String desc = "Earned by performing certain tasks.";

        switch ( v.getId() ) {

            case R.id.avocadoMedal:
                name = "Very Guac";
                desc = "Smash " + nextGoalAvocados + " total avocados!";
                break;
            case R.id.pointsMedal:
                name = "Much Score";
                desc = "Set a high score over " + nextGoalPoints + "!";
                break;
            case R.id.stirMedal:
                name = "Spun Out";
                desc = "Stir up a x" + nextGoalMultiplier + " multiplier!";
                break;
            case R.id.spiceMedal:
                name = "Control the spice...";
                desc = "Smash 6+ chilis and still score over 1k!";
                break;
            case R.id.lemonlessMedal:
                name = "Ol' Fashioned";
                desc = "Avoid smashing any limones and still score over 2k!";
                break;
            case R.id.rottenMedal:
                name = "Gutbuster";
                desc = "Smash 6+ rotten avocados and still score over 3k!";
                break;
            default:
                break;

        }

        MedalsDialogFragment fragment = new MedalsDialogFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("desc", desc);
        fragment.setArguments(args);
        fragment.show(manager, "dialogMedal");

    }

}