package com.example.jeeves.fittstappingtask.Presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jeeves.fittstappingtask.R;

/*
 * This activity encourages the users to take a break if they need after the first set of trials.
 */

public class BreakInstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.break_instructions_activity);

        Button nextButton = (Button)findViewById(R.id.break_instructions_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BreakInstructionsActivity.this, IndexFingerInstructionsActivity.class));
            }
        });
    }
}
