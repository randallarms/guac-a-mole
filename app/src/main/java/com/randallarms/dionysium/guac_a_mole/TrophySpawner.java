package com.randallarms.dionysium.guac_a_mole;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class TrophySpawner extends AppCompatActivity {

    public void create(TrophyActivity activity, int xp, ImageView image) {

        View trophyView = activity.findViewById(R.id.trophyLayout);

        //Default trophy
        int id = R.drawable.trophy_dream;
        int color = R.color.trophyStandard;

        if (xp >= 3000000) {
            id = R.drawable.trophy_ethereal;
            color = R.color.trophyEthereal;
        } else if (xp >= 2500000) {
            id = R.drawable.trophy_radioactive;
            color = R.color.trophyRadioactive;
        } else if (xp >= 2000000) {
            id = R.drawable.trophy_magma;
            color = R.color.trophyMagma;
        } else if (xp >= 1500000) {
            id = R.drawable.trophy_snow;
            color = R.color.trophySnow;
        }else if (xp >= 1200000) {
            id = R.drawable.trophy_midnight;
            color = R.color.trophyMidnight;
        } else if (xp >= 999999) {
            id = R.drawable.trophy_dusk;
            color = R.color.trophyDusk;
        } else if (xp >= 777777) {
            id = R.drawable.trophy_twilight;
            color = R.color.trophyTwilight;
        } else if (xp >= 666666) {
            id = R.drawable.trophy_abyssal;
            color = R.color.trophyAbyssal;
        } else if (xp >= 512000) {
            id = R.drawable.trophy_jpg;
            color = R.color.trophyJpg;
        } else if (xp >= 420000) {
            id = R.drawable.trophy_psychedelic;
            color = R.color.trophyPsychedelic;
        } else if (xp >= 360000) {
            id = R.drawable.trophy_cubist;
            color = R.color.trophyCubist;
        } else if (xp >= 333333) {
            id = R.drawable.trophy_renaissance;
            color = R.color.trophyRenaissance;
        } else if (xp >= 300000) {
            id = R.drawable.trophy_crude;
            color = R.color.trophyCrude;
        } else if (xp >= 250000) {
            id = R.drawable.trophy_black;
            color = R.color.trophyBlack;
        } else if (xp >= 200000) {
            id = R.drawable.trophy_gray;
            color = R.color.trophyGray;
        } else if (xp >= 150000) {
            id = R.drawable.trophy_white;
            color = R.color.trophyWhite;
        } else if (xp >= 100000) {
            id = R.drawable.trophy_prismatic;
            color = R.color.trophyPrismatic;
        } else if (xp >= 75000) {
            id = R.drawable.trophy_blue;
            color = R.color.trophyBlue;
        } else if (xp >= 50000) {
            id = R.drawable.trophy_indigo;
            color = R.color.trophyIndigo;
        } else if (xp >= 25000) {
            id = R.drawable.trophy_violet;
            color = R.color.trophyViolet;
        } else if (xp >= 15000) {
            id = R.drawable.trophy_red;
            color = R.color.trophyRed;
        } else if (xp >= 10000) {
            id = R.drawable.trophy_orange;
            color = R.color.trophyOrange;
        } else if (xp >= 5000) {
            id = R.drawable.trophy_yellow;
            color = R.color.trophyYellow;
        } else if (xp >= 2500) {
            id = R.drawable.trophy_green;
            color = R.color.trophyGreen;
        } else if (xp >= 1) {
            id = R.drawable.avocado_button;
            color = R.color.trophyStandard;
        }

        image.setImageDrawable(activity.getResources().getDrawable(id));
        trophyView.setBackgroundColor(activity.getResources().getColor(color));

    }

    public ArrayList<String> getInfo(int xp) {

        ArrayList<String> info = new ArrayList<>();
        String name = "Possible Trophy";
        String desc = "This could be an actual trophy some day, with enough experience.";

        if (xp >= 1200000) {
            name = "Midnight Avocado";
            desc = "This avocado is revealed only by the strongest moonlight.";
        } else if (xp >= 999999) {
            name = "Dusk Avocado";
            desc = "An avocado that prospers when the sun has set.";
        } else if (xp >= 777777) {
            name = "Twilight Avocado";
            desc = "When the sun's light begins to wane, this avocado blooms.";
        } else if (xp >= 666666) {
            name = "Abyssal Avocado";
            desc = "An avocado from the depths of darkness...";
        } else if (xp >= 512000) {
            name = "JPG Avocado";
            desc = "What it lacks in resolution it makes up in taste.";
        } else if (xp >= 420000) {
            name = "Psychedelic Avocado";
            desc = "Duuuuuuuuuuuuuuuuuuuuuuude.";
        } else if (xp >= 360000) {
            name = "Cubist Avocado";
            desc = "Noticeably more angles than a regular avocado.";
        } else if (xp >= 333333) {
            name = "Renaissance Avocado";
            desc = "Rumor has it that Da Vinci was an avid avocado enthusiast.";
        } else if (xp >= 300000) {
            name = "Crude Avocado";
            desc = "The first known caveman avocado art!";
        } else if (xp >= 250000) {
            name = "Black Avocado";
            desc = "The absence of light...";
        } else if (xp >= 200000) {
            name = "Gray Avocado";
            desc = "A somewhat darker avocado.";
        } else if (xp >= 150000) {
            name = "White Avocado";
            desc = "All of the colors of light, together...";
        } else if (xp >= 100000) {
            name = "Prismatic Avocado";
            desc = "A reward for 100K points!";
        } else if (xp >= 75000) {
            name = "Blue Avocado";
            desc = "Why the long avocado?";
        } else if (xp >= 50000) {
            name = "Indigo Avocado";
            desc = "Yeah, that other blue.";
        } else if (xp >= 25000) {
            name = "Violet Avocado";
            desc = "This avocado is absolutely faaabulous.";
        } else if (xp >= 15000) {
            name = "Red Avocado";
            desc = "That's one angry avocado!";
        } else if (xp >= 10000) {
            name = "Orange Avocado";
            desc = "Orange you glad this is an avocado pun?";
        } else if (xp >= 5000) {
            name = "Yellow Avocado";
            desc = "Hope you aren't too yellow to get the next trophy.";
        } else if (xp >= 2500) {
            name = "Green Avocado";
            desc = "For those still a little green.";
        } else if (xp >= 1) {
            name = "Plain Ol' Avocado";
            desc = "Yup, it's an avocado.";
        }

        info.add(name);
        info.add(desc);
        return info;

    }

}

/*
Trophy ideas: Baby Avocado; Abogado Avocado; Galactic Avocado; Ancient Avocado; Runed Avocado
 */