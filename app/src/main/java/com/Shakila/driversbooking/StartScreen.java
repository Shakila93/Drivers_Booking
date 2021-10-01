package com.Shakila.driversbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class StartScreen extends AppCompatActivity {

    @Override
    //binds button for the main app and histigrom , allows to tansition between the activites
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Button startbutton = findViewById(R.id.startbutton);
        //
        startbutton.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
        Manager.getInstance().LoadData(this);

        findViewById(R.id.view_histogram).setOnClickListener(View -> startActivity(new Intent(this, Histogram.class)));
    }
}