package com.Shakila.driversbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class HourScreen extends AppCompatActivity {
    private TextView timetext;
    private TextView[] bookingtext;
    private int month;
    private int day;
    private  int hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hour_screen);


        timetext = findViewById(R.id.hour_text);
        bookingtext = new TextView[10];
       bookingtext[0] = findViewById(R.id.teacher1text);
        bookingtext[1] = findViewById(R.id.teacher2text);
        bookingtext[2] = findViewById(R.id.teacher3text);
        bookingtext[3] = findViewById(R.id.teacher4text);
        bookingtext[4] = findViewById(R.id.teacher5text);
        bookingtext[5] = findViewById(R.id.teacher6text);
        bookingtext[6] = findViewById(R.id.teacher7text);
        bookingtext[7] = findViewById(R.id.teacher8text);
        bookingtext[8] = findViewById(R.id.teacher9text);
        bookingtext[9] = findViewById(R.id.teacher10text);

        for(int i = 0; i < 10; i++){
            int finalI = i;
            bookingtext[i].setOnClickListener(view -> gotoBooking(finalI));
        }
        //getting data(int) from the bundle
        Intent intent = getIntent();

        month = intent.getIntExtra("month", 0);
        day =intent.getIntExtra("day", 0);
        hour = intent.getIntExtra("hour", 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2021,month, day, hour, 0);
        SimpleDateFormat dtf = new SimpleDateFormat("E, MMM d 'at' k:00");
        timetext.setText(dtf.format(calendar.getTime()));



    }
    private void gotoBooking(int teacherId){
        Intent intent = new Intent(this, BookingForm.class);
        Bundle bundle = getIntent().getExtras();
        bundle.putInt("teacher", teacherId);

        intent.putExtras(bundle);

        startActivity(intent);
    }
}