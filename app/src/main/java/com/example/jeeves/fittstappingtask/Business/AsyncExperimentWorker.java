package com.example.jeeves.fittstappingtask.Business;

import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.example.jeeves.fittstappingtask.Data.IDCombination;
import com.example.jeeves.fittstappingtask.Data.Position;
import com.example.jeeves.fittstappingtask.R;

import java.util.ArrayList;
import java.util.Random;

public class AsyncExperimentWorker extends AsyncTask<Void, Void, Void> {

    private ArrayList<IDCombination> trialList;
    private View parentView;
    private int widthPixels;
    private int heightPixels;

    public AsyncExperimentWorker(ArrayList<IDCombination> trialList, View parentView, int widthPixels, int heightPixels) {
        this.trialList = trialList;
        this.parentView = parentView;
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while (trialList.size() != 0) {

            // Put the start button in a random location
            Button startButton = parentView.findViewById(R.id.start_button);
            Position startButtonPosition = randomizeViewPosition(startButton);

            // Get a random ID combination that still needs 10 successful trials
            Random random = new Random();
            IDCombination idCombination = trialList.get(random.nextInt(trialList.size()));

            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Position randomizeViewPosition(View view) {
        Random random = new Random();

        float xPosition = (random.nextFloat() * widthPixels) - view.getWidth();
        float yPosition = (random.nextFloat() * heightPixels) - view.getHeight();

        if (xPosition < view.getWidth()) {
            xPosition += view.getWidth();
        }

        if (yPosition < view.getHeight()) {
            yPosition += view.getHeight();
        }

        view.setX(xPosition);
        view.setY(yPosition);

        return new Position(xPosition, yPosition);
    }
}
