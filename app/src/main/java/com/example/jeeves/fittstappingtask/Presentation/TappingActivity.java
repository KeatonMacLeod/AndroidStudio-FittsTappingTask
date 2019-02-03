package com.example.jeeves.fittstappingtask.Presentation;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jeeves.fittstappingtask.Business.DataWriter;
import com.example.jeeves.fittstappingtask.Data.IDCombination;
import com.example.jeeves.fittstappingtask.Data.Position;
import com.example.jeeves.fittstappingtask.R;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/*
 * This is the activity used to display the tapping tasks for the user so data can be collected about
 * the "device" they are using to tap on targets.
 */

public class TappingActivity extends AppCompatActivity {

    private int SCREEN_WIDTH = 650;
    private int SCREEN_HEIGHT = 1800;
    private int feedbackDelay;
    private String device;
    private String last;
    private DataWriter dataWriter;
    private int attemptedTrials;
    private int totalTrials;
    private int[] widths;
    private int[] amplitudes;
    private boolean circleTargetDisplayingFeedback;
    private Resources resources;
    private ArrayList<IDCombination> trialList;
    private CircleImageView startButton;
    private CircleImageView circleTarget;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        feedbackDelay = 375;
        device = intent.getExtras().getString("device");

        if (intent.getExtras().getString("last") != null) {
            last = intent.getExtras().getString("last");
        }

        dataWriter = new DataWriter(this, "experiment-results.txt");
        attemptedTrials = 0;
        widths = new int[]{110, 200, 375};
        amplitudes = new int[]{200, 400, 700};
        circleTargetDisplayingFeedback = false;
        resources = this.getResources();
        trialList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tapping_activity);
        startButton = findViewById(R.id.start_button);
        circleTarget = findViewById(R.id.circle_target);
        relativeLayout = findViewById(R.id.relative_layout);
//        initDisplayParams();
        initializeTrialList();
        beginTrials();
    }

    private void initDisplayParams() {
        SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels - startButton.getWidth();
        SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels - startButton.getHeight();
        Log.d("Tapping", "width:"+SCREEN_WIDTH);
        Log.d("Tapping", "height:"+SCREEN_HEIGHT);
    }

    //Create all of the IDCombinations for the experiment
    private void initializeTrialList() {
        for (int width: widths)
            for (int amplitude: amplitudes)
                trialList.add(new IDCombination(width, amplitude));

        totalTrials = trialList.size() * trialList.get(0).getTotal();
    }

    // This is the function that gathers all of the data for our experiment. It is recursive in that tapping on the
    // startButton causes the square_red to appear, and then tapping on the square_red (successful), or elsewhere on the screen (unsuccessful)
    // causes the beginTrials function to be called. This pattern continues until all the necessary trials have been completed.
    private void beginTrials() {

        if (trialList.size() == 0)
        {
            final Intent intent;

            // If this was the last activity, we can start the ClosingRemarksActivity
            if (last != null)
            {
                intent = new Intent(this, ClosingRemarksActivity.class);
            }

            // If this wasn't the last activity, we need to start the BreakInstructionsActivity
            // and put the nextDevice in so we know which Activity to start next.
            else
            {
                intent = new Intent(this, BreakInstructionsActivity.class);
            }

            // Depending on the tappingTask we just did, put the other tapping task "next" in the queue.
            if (device.equals("thumb"))
            {
                intent.putExtra("device", "index");
            }
            else if (device.equals("index"))
            {
                intent.putExtra("device", "thumb");
            }

            // Used to determine if this is the last tapping task
            intent.putExtra("last", "");

            startActivity(intent);
        }

        TextView counter = findViewById(R.id.counter);
        counter.setText(resources.getString(R.string.trial_counter, attemptedTrials, totalTrials));

        // This ensures that the application doesn't record additional taps after a single successful
        // or unsuccessful tap on the target / screen.
        circleTargetDisplayingFeedback = false;

        // Show the startButton, hide the square_red target, and put the startButton in a random position
        startButton.setVisibility(View.VISIBLE);

        final Position startButtonPosition = randomizeViewPosition(startButton);

        // Set an onClick which causes the square_red of width W and amplitude A away from the startButton to appear given a specified
        // IDCombination
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get a random ID combination that still needs additional trials
                Random random = new Random();
                final IDCombination idCombination = trialList.get(random.nextInt(trialList.size()));

                System.out.println("AMPLITUDE: " + idCombination.getAmplitude() + ", " + "WIDTH: " + idCombination.getWidth());

                startButton.setVisibility(View.GONE);
                circleTarget.setVisibility(View.VISIBLE);

                // Hide the startButton, set the correct target height + width and make the target visible
                setTargetPosition(startButtonPosition, idCombination);

                // Start the timer
                final double startTime = System.currentTimeMillis();

                // Set an onClick for the "happy path" where the user successfully taps on the square_red
                circleTarget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (startButton.getVisibility() == View.VISIBLE) return;

                        if (!circleTargetDisplayingFeedback) {
                            circleTargetDisplayingFeedback = true;
                            circleTarget.setImageResource(R.drawable.square_green);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    circleTarget.setVisibility(View.GONE);
                                    circleTarget.setImageResource(R.drawable.square_blue);
                                    double endTime = System.currentTimeMillis();
                                    double movementTime = endTime - startTime;

                                    String trialData = "{\"result\": \"success\"" +
                                            ", \"amplitude\":" + idCombination.getAmplitude() +
                                            ", \"width\":" + idCombination.getWidth() +
                                            ", \"movement-time\":" + movementTime +
                                            ", \"id-trial-number\":" + idCombination.getAttempted() +
                                            ", \"overall-trial-number\":" + attemptedTrials +
                                            ", \"device\":\"" + device + "\"}\n";

                                    dataWriter.appendResultsToFile(trialData);
                                    idCombination.incrementAttempted();
                                    if (idCombination.completedAllTrials())
                                    {
                                        trialList.remove(idCombination);
                                    }
                                    beginTrials();
                                }
                            }, feedbackDelay);
                        }
                    }
                });

                // Set an onClick for the "error path" where the user mistaps and misses the square_red
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (startButton.getVisibility() == View.VISIBLE) return;

                        if (!circleTargetDisplayingFeedback) {
                            circleTargetDisplayingFeedback = true;
                            circleTarget.setImageResource(R.drawable.square_red);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    circleTarget.setVisibility(View.GONE);
                                    circleTarget.setImageResource(R.drawable.square_blue);
                                    totalTrials += 1;
                                    String trialData = "{\"result\": \"error\"" +
                                            ", \"amplitude\":" + idCombination.getAmplitude() +
                                            ", \"width\":" + idCombination.getWidth() +
                                            ", \"id-trial-number\":" + idCombination.getAttempted() +
                                            ", \"overall-trial-number\":" + attemptedTrials +
                                            ", \"device\":\"" + device + "\"}\n";

                                    dataWriter.appendResultsToFile(trialData);
                                    idCombination.incrementTrialCount();
                                    idCombination.incrementAttempted();
                                    beginTrials();
                                }
                            }, feedbackDelay);
                        }
                    }
                });

                attemptedTrials += 1;
            }
        });
    }

    private Position randomizeViewPosition(View view) {

        Random random = new Random();

        float xPosition = random.nextFloat() * SCREEN_WIDTH;
        float yPosition = random.nextFloat() * SCREEN_HEIGHT;

//        if (xPosition >= (SCREEN_WIDTH - startButton.getWidth())) {
//            xPosition -= startButton.getWidth();
//        }
//
//        if (yPosition >= (SCREEN_HEIGHT - startButton.getHeight())) {
//            yPosition -= startButton.getHeight();
//        }

        int[] location = new int[2];
        view.setX(xPosition);
        view.setY(yPosition);
        view.getLocationInWindow(location);

        return new Position(xPosition, yPosition);
    }

    private void setTargetPosition(Position startButton, IDCombination idCombination) {
        Random random = new Random();

        boolean onScreen = false;
        double amplitude = idCombination.getAmplitude();
        float targetX = 200;
        float targetY = 200;
        double x;
        double y;

        // This could be optimized, but it's still instantaneous so it's most likely good enough for now
        while (!onScreen) {
            // Get a random x-value between (-amplitude, amplitude)
            x = (float)(random.nextInt((int)(amplitude * 2 + 1)) - amplitude);

            // Compute the y-value such that the target will still be amplitude pixels away from the startButton
            y = Math.sqrt(Math.pow(amplitude, 2) - Math.pow(x, 2));

            // y = +-sqrt(r^2 - x^2)    -> This ensures we have a positive (above) or negative (below) option, and
            // that we're not just looking at always spawning the target either above or below, but it is randomized
            if (random.nextBoolean()) {
                y = -y;
            }

            // Set the target's position in relation to the current coordinates of the startButton
            targetX = (float) (startButton.getX() + x);
            targetY = (float) (startButton.getY() + y);

            if (targetFullyOnScreen(targetX, targetY))
                onScreen = true;
        }

        circleTarget.getLayoutParams().height = idCombination.getWidth();
        circleTarget.getLayoutParams().width = idCombination.getWidth();

//        if (targetX >= (SCREEN_WIDTH - circleTarget.getWidth())) {
//            targetX -= 2 * circleTarget.getWidth();
//        }
//
//        if (targetY >= (SCREEN_HEIGHT - circleTarget.getHeight())) {
//            targetY -= 2 * circleTarget.getHeight();
//        }

        circleTarget.setX(targetX);
        circleTarget.setY(targetY);
    }

    private boolean targetFullyOnScreen(float targetX, float targetY) {
        if (targetX >= 0 && targetX <= SCREEN_WIDTH && targetY >= 0 && targetY <= SCREEN_HEIGHT)
            return true;
        else
            return false;
    }

}
