package com.randallarms.dionysium.guac_a_mole;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SmashTutorial
        extends AppCompatActivity
        implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    //RelativeLayout object
    RelativeLayout smashTutorialLayout;

    //Maximum X and Y coordinates on screen (initiate var)
    int maxX;
    int maxY;

    //Swipe direction detection constants
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    //Gesture detector declaration
    private GestureDetectorCompat gestureDetector;

    //Points, multipliers, & other gameplay counters
    public int total = 0;
    public int points = 0;
    public int chilis = 0;
    public int avocados = 0;
    public int limones = 0;
    public int rotten = 0;
    public int totalChilis = 0;
    public int totalRotten = 0;
    public int totalLimon = 0;
    public int multiplier = 1;

    public boolean stirCooldown = false;

    int currentOrder = 0;
    boolean nextCooldown = false;

    ArrayList<Integer> limonesRounds = generateLimones();

    //Splat sound MediaPlayer
    MediaPlayer splatSound = new MediaPlayer();

    //Background image of guacamole bowl
    ImageView guacBowl;

    final Timer promptTimer1 = new Timer();
    final Timer promptTimer2 = new Timer();
    TextView dialog;
    ImageView dialogBox;

    //On create...
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Creation
        super.onCreate(savedInstanceState);

        //Layout
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_smash_tutorial);

        //Get the layout object
        smashTutorialLayout = (RelativeLayout) findViewById(R.id.smashLayout);

        //Background object
        guacBowl = (ImageView) findViewById(R.id.guacBowl);

        //Gesture detection instantiation & listener
        this.gestureDetector = new GestureDetectorCompat(this,this);
        gestureDetector.setOnDoubleTapListener(this);

        //Splat sound settings
        splatSound = MediaPlayer.create(this, R.raw.splat_sound);

        //Display info
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        maxX = mdispSize.x;
        maxY = mdispSize.y;

        //Start the tutorial
        dialog = (TextView) findViewById(R.id.dialogText);
        dialogBox = (ImageView) findViewById(R.id.dialogBox);

        setHelpText(0);

    }

    public void setHelpText(int order) {

        dialog.setVisibility(View.VISIBLE);
        dialogBox.setVisibility(View.VISIBLE);

        switch (order) {

            case 0: //Auto
                dialog.setText("Are you ready to make some guac? Then get ready!");
                break;
            case 1: //Click
                dialog.setText("When a delicious avocado appears on the screen, smash it with your finger.");
                break;
            case 2: //Click
                dialog.setText("Give it a try: ");
                break;
            case 3: //Failure
                dialog.setText("You missed the avocado, but give it another shot...");
                break;
            case 4: //Success
                dialog.setText("Got it! You're well on your way to making some guac.");
                promptTimer1.cancel();
                promptTimer1.purge();
                break;
            case 5: //Click
                dialog.setText("Notice you got points at the top of the screen for smashing the avocado?");
                break;
            case 6: //Click
                dialog.setText("Well, you can increase that score more quickly with a score multiplier.");
                break;
            case 7: //Click
                dialog.setText("See the x1 at the bottom? That right there is the multiplier count.");
                break;
            case 8: //Click
                dialog.setText("The multiplier is increased by stirring your guac often.");
                break;
            case 9: //Click
                dialog.setText("Just swipe your finger across the bowl to stir it. Give it a try: ");
                break;
            case 10: //Failure
                dialog.setText("Looks like you didn't stir the bowl yet. Try it again...");
                break;
            case 11: //Success
                dialog.setText("Awesome. You should notice you get more points now per avocado.");
                break;
            case 12: //Click
                dialog.setText("Now let's move on to the extras. Ever had chilis in your guac?");
                break;
            case 13: //Click
                dialog.setText("Smash this red chili and watch your points go up: ");
                break;
            case 14: //Failure
                dialog.setText("Whoops! That chili slipped by. Try again...");
                break;
            case 15: //Success
                dialog.setText("Ooh, spicy! Chilis are good for points, but watch your heat meter.");
                promptTimer2.cancel();
                promptTimer2.purge();
                break;
            case 16: //Click
                dialog.setText("When the red fills up the heat meter, you will start losing points for chilis!");
                break;
            case 17: //Click
                dialog.setText("And of course, don't forget to add some limon! Good luck...");
                break;

        }

        currentOrder = order;

    }

    public void onNext(View v) {

        if (nextCooldown) {
            return;
        }

        switch (currentOrder) {
            //Continue to the next dialog text
            case 0:
            case 1:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 12:
            case 15:
            case 16:
                setHelpText(currentOrder + 1);
                break;
            //Action & failure prompts
            case 2:
            case 3:
                //Respawn avocado prompt
                dialog.setVisibility(View.GONE);
                dialogBox.setVisibility(View.GONE);
                prompt("avocado");
                break;
            case 9:
            case 10:
                //Respawn stir prompt
                dialog.setVisibility(View.GONE);
                dialogBox.setVisibility(View.GONE);
                prompt("stir");
                break;
            case 13:
            case 14:
                //Respawn chili prompt
                dialog.setVisibility(View.GONE);
                dialogBox.setVisibility(View.GONE);
                prompt("chili");
                break;
            //Finish
            case 17:
                //Buttons
                buttonTimer();
                dialog.setVisibility(View.GONE);
                dialogBox.setVisibility(View.GONE);
                break;

        }

        //Deal with the cooldown
        nextCooldown = true;

        scheduler.schedule(new Runnable() {
            public void run() {
                nextCooldown = false;
            }
        }, 500, TimeUnit.MILLISECONDS);

    }

    public void prompt(final String type) {

        switch (type) {

            case "avocado":
                ImageView avocado = (ImageView) findViewById(R.id.avocado);
                avocado.setVisibility(View.VISIBLE);
                promptTimer1.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                handleButton(R.id.avocado);
                            }
                        });

                    }
                }, 500, 1000);
                break;

            case "chili":
                ImageView chili = (ImageView) findViewById(R.id.chili);
                chili.setVisibility(View.VISIBLE);
                promptTimer2.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                handleButton(R.id.chili);
                            }
                        });

                    }
                }, 500, 1000);
                break;

        }

        scheduler.schedule(new Runnable() {
            public void run() {

                List<Integer> orders = new ArrayList<>();
                orders.addAll( Arrays.asList(2, 3, 9, 10, 13, 14) );

                if (orders.contains(currentOrder)) {
                    switch (type) {
                        case "avocado":
                            setHelpText(3);
                            break;
                        case "stir":
                            setHelpText(10);
                            break;
                        case "chili":
                            setHelpText(14);
                            break;
                    }
                }

            }
        }, 10000, TimeUnit.MILLISECONDS);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        myTimer.cancel();
        scheduler.shutdown();
        splatSound.release();

        //Set the medals screen intent
        Intent intent = new Intent(this, MainActivity.class);

        //Start the medals activity
        startActivity(intent);
        this.finish();
    }

    //Scoreboard handling
    public void scoreboard() {
        Intent intent = new Intent(SmashTutorial.this, Scoreboard.class);
        intent.putExtra("points", points);
        intent.putExtra("avocados", avocados);
        intent.putExtra("chilis", chilis);
        intent.putExtra("rotten", rotten);
        intent.putExtra("limones", limones);
        intent.putExtra("multiplier", multiplier);
        startActivity(intent);
    }

    //Update score
    public void updateScore() {
        TextView currentScoreCounter = (TextView) findViewById(R.id.scoreCounter);
        currentScoreCounter.setText(String.valueOf(points));
    }

    //On gesture...
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) { return true; }

    @Override
    public void onShowPress(MotionEvent e) { }

    @Override
    public void onLongPress(MotionEvent e) { }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return onStir(guacBowl, "right");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH){
                return true;
            }
            // right to left swipe
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                //check if top or bottom of screen
                if (e1.getY() < (maxY * 0.5)) {
                    return onStir(guacBowl, "left");
                } else {
                    return onStir(guacBowl, "right");
                }
            }
            // left to right swipe
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                //check if top or bottom of screen
                if (e1.getY() < (maxY * 0.5)) {
                    return onStir(guacBowl, "right");
                } else {
                    return onStir(guacBowl, "left");
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    //Button scheduling
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    final Timer myTimer = new Timer();

    public void buttonTimer() {

        myTimer.scheduleAtFixedRate(new TimerTask() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    makeButton();
                                                }
                                            });

                                            total++;

                                            //Finish round
                                            if (total == 60) {

                                                //Turn off the timer
                                                myTimer.cancel();

                                                //Release the MediaPlayer
                                                splatSound.release();

                                                //Go to scoreboard activity
                                                scheduler.schedule(new Runnable() {
                                                    public void run() {
                                                        scheduler.shutdown();
                                                        scoreboard();
                                                    }
                                                }, 100, TimeUnit.MILLISECONDS);

                                            }

                                        }
                                    }, 500, 1000 - (total*12));
    }

    //Primary buttons
    public void makeButton() {

        int id;

        //Random chance roll
        int r = new Random().nextInt(1000);

        //Check for limon button spawn...
        if ( limonesRounds.contains(total) ) {
            makeLimon();
        }

        //Choose the primary button
        if (total < 4) { //Guarantee that the first 4 rounds are avocados
            id = R.id.avocado;
        } else if (r<100 && totalRotten < 10) {
            id = R.id.avocadoRotten;
            totalRotten++;
        } else if (r<200 && totalChilis < 7) {
            id = R.id.chili;
            totalChilis++;
        } else {
            id = R.id.avocado;
        }

        handleButton(id);

    }

    //Rotten button
    public void handleButton(int id) {

        //Get the view by ID
        ImageView buttonID = (ImageView) this.findViewById(id);

        //Set the button to visible
        buttonID.setVisibility(View.VISIBLE);

        //Set a random location for the button
        int rndX = (int) (maxX * (new Random().nextInt(75) * 0.01));
        int rndY = (int) (maxY * (new Random().nextInt(75) * 0.01));
        buttonID.setX(rndX);
        buttonID.setY(rndY);

    }

    //On primary button press...
    public void onSmash(View v) {

        //Remove the button
        v.setVisibility(View.GONE);

        //Add points
        switch ( v.getId() ) {

            case R.id.avocado:
                if (currentOrder == 2 || currentOrder == 3) {
                    setHelpText(4);
                }
                avocados++;
                points += multiplier;
                break;
            case R.id.avocadoRotten:
                if (currentOrder == 9 || currentOrder == 10) {
                    setHelpText(11);
                }
                rotten++;
                points -= (5 * multiplier);
                break;
            case R.id.chili:
                if (currentOrder == 13 || currentOrder == 14) {
                    setHelpText(15);
                }
                chilis++;
                onChili();
                if (chilis <= 3) {
                    points += (10 * multiplier);
                } else {
                    points -= (2 * chilis * multiplier);
                }
                break;

        }

        //Avoid negative scores
        if (points < 0) {
            points = 0;
        }

        //Update the score on screen
        updateScore();

        //Splat sound effect for all except last round(s)
        if (total < 59) {
            splatSoundEffect();
        }

    }

    //Figures out when to spawn the 3 limones
    public ArrayList<Integer> generateLimones() {

        ArrayList<Integer> rounds = new ArrayList<>();

        for (int i=0; i<3; i++) {

            Random rnd = new Random();
            Integer rndRound = rnd.nextInt(58);

            if (!rounds.contains(rndRound)) {
                rounds.add(rndRound);
            } else if (!rounds.contains(rndRound + 1)) {
                rounds.add(rndRound + 1);
            } else {
                rounds.add(rndRound + 2);
            }

        }

        return rounds;

    }

    //Limon button
    public void makeLimon() {

        final int limon = R.id.limon;
        findViewById(limon).setVisibility(View.VISIBLE);

        //Set the button to disappear after an interval
        scheduler.schedule(new Runnable() {
            public void run() {
                findViewById(limon).setVisibility(View.GONE);
            }
        }, 900, TimeUnit.MILLISECONDS);

        totalLimon++;

    }

    //On limon button press...
    public void onLimon(View v) {

        //Splat sound effect for all except last round(s)
        if (total < 59) {
            splatSoundEffect();
        }

        limones++;
        multiplier += limones;
        points += 10 * multiplier;

        if (v.getVisibility() != View.GONE) {
            v.setVisibility(View.GONE);
        }

        updateScore();

    }

    //On chili button press (chili meter handling only)...
    public void onChili() {

        ImageView chiliMeter = (ImageView) findViewById(R.id.chiliCounter);

        if (chilis == 1) {
            chiliMeter.setImageResource(R.drawable.chili_meter_1);
        } else if (chilis == 2) {
            chiliMeter.setImageResource(R.drawable.chili_meter_2);
        } else if (chilis == 3) {
            chiliMeter.setImageResource(R.drawable.chili_meter_3);
        } else if (chilis > 3) { //Keeps chili meter updated at max capacity
            chiliMeter.setImageResource(R.drawable.chili_meter_3);
        } else { //Just in case chilis are negative or reset for some reason; resets meter
            chiliMeter.setImageResource(R.drawable.chili_meter_0);
        }

    }

    //On stir...
    public boolean onStir(ImageView guacBowl, String direction) {

        //Check if already stirring
        if (stirCooldown) {
            return true;
        }

        //Variables
        stirCooldown = true;
        multiplier += 1;

        //Multiplier text counter
        String multiText = "x" + String.valueOf(multiplier);
        TextView multiplierCounter = (TextView) findViewById(R.id.multiCounter);
        multiplierCounter.setText( multiText );

        //Animation stuff
        AnimationSet aSet = new AnimationSet(true);
        aSet.setInterpolator(new DecelerateInterpolator());
        aSet.setFillAfter(true);
        aSet.setFillEnabled(true);

        float degrees;

        switch (direction) {
            //Left-swipe handling
            case "left":
                degrees = -360.0f;
                break;
            //Right-swipe handling
            default:
                degrees = 360.0f;
                break;
        }

        final RotateAnimation aRotate = new RotateAnimation(0.0f, degrees,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        aRotate.setFillAfter(true);
        aRotate.setDuration(500);
        aSet.addAnimation(aRotate);

        guacBowl.startAnimation(aSet);

        //Stir cooldown scheduling
        scheduler.schedule(new Runnable() {
            public void run() {
                stirCooldown = false;
            }
        }, 800, TimeUnit.MILLISECONDS);

        if (currentOrder == 9|| currentOrder == 10) {
            setHelpText(11);
        }

        return true;

    }

    //Splat sound effect
    public void splatSoundEffect() {

        if (!splatSound.isPlaying() ) {
            splatSound.start();
        }

    }

    //Splat effects
    public void splatEffect(View v) {

        //Get the splat coords
        float x = v.getX();
        float y = v.getY();

        //Create the splat image
        ImageView splat = (ImageView) findViewById(R.id.splat);
        splat.setVisibility(View.VISIBLE);

        //Add the next splat effect
        splat.setX(x);
        splat.setY(y);

        removeSplat();

    }

    public void removeSplat() {

        //Set the button to disappear after an interval
        scheduler.schedule(new Runnable() {
            public void run() {
                findViewById(R.id.splat).setVisibility(View.GONE);
            }
        }, 250, TimeUnit.MILLISECONDS);

    }

}
