package com.example.kolin.fintechhomework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //open first screen (first task)
        findViewById(R.id.main_activity_button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonActivity.start(MainActivity.this, 0);
            }
        });

        //open second screen (second task)
        findViewById(R.id.main_activity_button_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonActivity.start(MainActivity.this, 1);
            }
        });
    }
}
