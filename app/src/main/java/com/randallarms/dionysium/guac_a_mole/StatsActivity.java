package com.randallarms.dionysium.guac_a_mole;

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

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stats);

        //SharedPreferences saved values
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Previously saved scores (stats)
        int savedGamesTotal = prefs.getInt("saved_gamesTotal", 0);
        int savedExperience = prefs.getInt("saved_experience", 0);
        int savedAvocados = prefs.getInt("saved_avocados", 0);
        int savedRotten = prefs.getInt("saved_rotten", 0);
        int savedChilis = prefs.getInt("saved_chilis", 0);
        int savedLimones = prefs.getInt("saved_limones", 0);
        int savedMultiplier = prefs.getInt("saved_multiplier", 0);

        //Display total smash scores
        TextView tvAvocados = (TextView) findViewById(R.id.avocadoTotalText);
        tvAvocados.setText("" + Integer.toString(savedAvocados));
        TextView tvRotten = (TextView) findViewById(R.id.rottenTotalText);
        tvRotten.setText("" + Integer.toString(savedRotten));
        TextView tvChilis = (TextView) findViewById(R.id.chiliTotalText);
        tvChilis.setText("" + Integer.toString(savedChilis));
        TextView tvLimones = (TextView) findViewById(R.id.limonTotalText);
        tvLimones.setText("" + Integer.toString(savedLimones));

        //Display other scores & stats
        TextView tvMultiplier = (TextView) findViewById(R.id.multiplierScoreText);
        tvMultiplier.setText("" + Integer.toString(savedMultiplier));
        TextView tvGames = (TextView) findViewById(R.id.totalGamesScoreText);
        tvGames.setText("" + Integer.toString(savedGamesTotal));
        TextView tvExperience = (TextView) findViewById(R.id.expText);
        tvExperience.setText("" + Integer.toString(savedExperience));

    }

    @Override
    public void onBackPressed() {

        //Set the medals screen intent
        Intent intent = new Intent(this, Medals.class);

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

    public void openMedals(View v) {
        Intent intent = new Intent(this, Medals.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
