package com.example.jeeves.fittstappingtask.Presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jeeves.fittstappingtask.R;

/*
 * This activity is shown right before the user begins the thumb tapping test, where they are shown
 * how they should hold the device for the duration of the trials within the thumb tapping test.
 */

public class ThumbInstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thumb_instructions_activity);

        //Construct the state object used for the thumb tapping activity
        String device = "thumb";
        final Intent intent = new Intent(ThumbInstructionsActivity.this, TappingActivity.class);
        intent.putExtra("device", device);

        // Determine if this is the last activity
        String last = checkForPassedIntent();
        if (!last.equals("")) {
            intent.putExtra("last", "");
        }

        Button nextButton = (Button)findViewById(R.id.thumb_instructions_begin_trials_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private String checkForPassedIntent() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle.getString("last") != null) {
                return bundle.getString("last");
            }
        }
        return "";
    }
}
