package com.randallarms.dionysium.guac_a_mole;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SaltShake {

    //Shake detection vars
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    public boolean saltAllowed = false;

    //Salt prompt view
    TextView salt;

    //Button scheduling
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void salt() {

        //Salt prompt
        saltPrompt();

        //Finish round
        scheduler.schedule(new Runnable() {
            public void run() {
                //Disallow salting
                saltAllowed = false;
            }
        }, 1500, TimeUnit.MILLISECONDS);

    }

    //Salting prompt
    public void saltPrompt() {

        //Salt prompt
        salt.setVisibility(View.VISIBLE);

        //Allow salting
        saltAllowed = true;

        //Salt prompt removal
        scheduler.schedule(new Runnable() {
            public void run() {
                salt.setVisibility(View.GONE);
            }
        }, 1400, TimeUnit.MILLISECONDS);

    }

    //On salt shake...
    public int onSalt(AppCompatActivity activity, int points, int multiplier) {

        //Disallow salting
        saltAllowed = false;

        //Salt effects
        final Animation animShake = AnimationUtils.loadAnimation(activity, R.anim.animation_shake);
        salt.startAnimation(animShake);

        points += 26 * multiplier;
        return points;

    }

}
