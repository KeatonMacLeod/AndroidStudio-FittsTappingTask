package com.example.jeeves.fittstappingtask.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.jeeves.fittstappingtask.R;

public class Choose_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__screen);

        Button indexFinger = (Button) findViewById(R.id.index_button);
        indexFinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), IndexFingerInstructionsActivity.class);
                startActivity(startIntent);

            }
        });

        Button thumb = (Button) findViewById(R.id.thumb_button);
        thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), ThumbInstructionsActivity.class);
                startActivity(startIntent);
            }
        });

    }
}
