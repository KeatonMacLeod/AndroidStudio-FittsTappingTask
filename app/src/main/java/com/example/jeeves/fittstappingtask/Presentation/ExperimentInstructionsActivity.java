package com.example.jeeves.fittstappingtask.Presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jeeves.fittstappingtask.R;

/*
 * This is the first activity that the user sees when they begin the test, this gives them the general
 * outline of what they will have to do through the course of the experiment.
 */

public class ExperimentInstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experiment_instructions_activity);

        String device = checkForDevice();
        final Intent intent;

        if (device.equals("thumb"))
        {
            intent = new Intent(this, ThumbInstructionsActivity.class);
        }
        else
        {
            intent = new Intent(this, IndexFingerInstructionsActivity.class);
        }

        Button nextButton = (Button)findViewById(R.id.experiment_instructions_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private String checkForDevice() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle.getString("device") != null) {
                return bundle.getString("device");
            }
        }
        return "";
    }

}
