package com.example.jeeves.fittstappingtask.Presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.jeeves.fittstappingtask.Business.AsyncExperimentWorker;
import com.example.jeeves.fittstappingtask.Data.IDCombination;
import com.example.jeeves.fittstappingtask.R;

import java.util.ArrayList;
import java.util.Random;

/*
 * This is the activity used to display the tapping tasks for the user so data can be collected about
 * the "device" they are using to tap on targets.
 */

public class TappingActivity extends AppCompatActivity {

    int[] widths = {10, 20, 40};
    int[] amplitudes = {40, 80, 160};
    ArrayList<IDCombination> trialList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tapping_activity);
        initializeTrialList();
        beginTrials();

    }

    //Create all of the IDCombinations for the experiment
    private void initializeTrialList() {
        for (int width: widths)
            for (int amplitude: amplitudes)
                trialList.add(new IDCombination(width, amplitude));
    }

    // Where the experiment takes place and the data is recorded for ID combinations
    private void beginTrials() {
        DisplayMetrics displaymatrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymatrics);

        AsyncExperimentWorker asyncExperimentWorker = new AsyncExperimentWorker(trialList, getWindow().getDecorView().getRootView(), displaymatrics.widthPixels, displaymatrics.heightPixels);
        asyncExperimentWorker.execute();
    }

}
