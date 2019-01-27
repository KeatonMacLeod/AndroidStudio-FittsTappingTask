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
        

        Button nextButton = (Button)findViewById(R.id.thumb_instructions_begin_trials_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThumbInstructionsActivity.this, TappingActivity.class));
            }
        });
    }
}
