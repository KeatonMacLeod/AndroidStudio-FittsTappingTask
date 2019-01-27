package com.example.jeeves.fittstappingtask.Presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jeeves.fittstappingtask.R;

/*
 * This activity thanks the user for being a part of our test. It also lets them know that they have
 * completed all of the trials and that they can hand the phone back to the experimenter.
 */

public class ClosingRemarksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closing_remarks_activity);
    }
}
