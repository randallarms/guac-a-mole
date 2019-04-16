package com.randallarms.dionysium.guac_a_mole;

import android.app.FragmentManager;
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

import java.util.ArrayList;
import java.util.WeakHashMap;

public class TrophyActivity extends AppCompatActivity {

    //SharedPreferences saved values
    SharedPreferences prefs;
    int savedExperience = 0;
    TrophySpawner trophy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_trophy);

        //SharedPreferences saved values
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Previously saved scores (stats)
        savedExperience = prefs.getInt("saved_experience", 0);

        //Create the trophy image
        ImageView trophyImage = (ImageView) findViewById(R.id.trophy);

        trophy = new TrophySpawner();
        trophy.create(this, savedExperience, trophyImage);

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

    public void openMedals(View v) {
        Intent intent = new Intent(this, Medals.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void openStats(View v) {
        Intent intent = new Intent(this, StatsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void dialogTrophy(View v) {

        //Get dialog info for trophy
        ArrayList<String> info = trophy.getInfo(savedExperience);

        String name = "Possible Trophy";
        String desc = "Earned through experience.";

        //Make the dialog fragment
        FragmentManager manager = getFragmentManager();
        MedalsDialogFragment fragment = new MedalsDialogFragment();
        Bundle args = new Bundle();
        args.putString("name", info.get(0));
        args.putString("desc", info.get(1));
        fragment.setArguments(args);
        fragment.show(manager, "dialogTrophy");

    }

}