package com.example.jeeves.fittstappingtask.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.jeeves.fittstappingtask.R;

public class ChooseScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_screen_activity);

        final Intent intent = new Intent(this, ExperimentInstructionsActivity.class);

        Button indexFinger = (Button) findViewById(R.id.index_button);
        indexFinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("device", "index");
                startActivity(intent);
            }
        });

        Button thumb = (Button) findViewById(R.id.thumb_button);
        thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("device", "thumb");
                startActivity(intent);
            }
        });
    }
}
