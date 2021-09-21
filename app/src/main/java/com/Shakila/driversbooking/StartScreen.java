package com.Shakila.driversbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Button startbutton = findViewById(R.id.startbutton);
        startbutton.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
        Manager.getInstance().LoadData(this);
    }
}