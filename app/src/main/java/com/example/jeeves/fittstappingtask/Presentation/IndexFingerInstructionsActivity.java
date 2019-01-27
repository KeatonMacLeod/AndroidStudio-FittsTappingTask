package com.example.jeeves.fittstappingtask.Presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jeeves.fittstappingtask.R;

/*
 * This activity is shown right before the user begins the index-finger tapping test, where they are shown
 * how they should hold the device for the duration of the trials within the index-finger tapping test.
 */


public class IndexFingerInstructionsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_finger_instructions_activity);

        Button nextButton = (Button)findViewById(R.id.index_finger_begin_trials_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndexFingerInstructionsActivity.this, ClosingRemarksActivity.class));
            }
        });
    }
}
