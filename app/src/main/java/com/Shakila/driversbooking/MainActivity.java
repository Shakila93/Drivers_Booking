package com.Shakila.driversbooking;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public TextView DayText;
    private RadioButton MondayButton;
    private RadioButton TuesdayButton;
    private RadioButton WednesdayButton;
    private RadioButton ThursdayButton;
    private RadioButton FridayButton;
    private Button PrevButton;
    private Button NextButton;
    private int month;
    private int day;
    private int startDate;
    private Map<Integer, TextView> Buttons;
    private Manager manager;

    //string representation of months and days
    private final String[] months = {
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
    };
    private final String[] days = {
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"
    };
    //colors representation our heat map based on the number of slots that are booked
    private final Color[] heatMap = {
            Color.valueOf(0.03f, 0.58f,0.12f),
            Color.valueOf(0.27f, 0.92f, 0.19f),
            Color.valueOf(0.43f, 0.98f, 0.02f),
            Color.valueOf(0.72f, 0.98f,  0.02f),
            Color.valueOf(0.98f, 0.78f,  0.02f),
            Color.valueOf(0.98f, 0.94f,  0.02f),
            Color.valueOf(0.98f, 0.58f,  0.02f),
            Color.valueOf(0.98f, 0.41f,  0.02f),
            Color.valueOf(0.98f, 0.32f,  0.02f),
            Color.valueOf(0.98f, 0.23f,  0.02f),
            Color.valueOf(0.98f,  0.02f,  0.02f)
    };
    @Override
    //setting up all the views
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title_bar);

    //binding, connecting to the visual component
        DayText = findViewById(R.id.day_text);
        MondayButton = findViewById(R.id.monday);
        TuesdayButton = findViewById(R.id.tuesday);
        WednesdayButton = findViewById(R.id.wednesday);
        ThursdayButton = findViewById(R.id.thursday);
        FridayButton = findViewById(R.id.friday);
        NextButton = findViewById(R.id.next_week);
        PrevButton = findViewById(R.id.prv_week);

        Calendar date =  Calendar.getInstance();

    month = date.get(Calendar.MONTH);
    date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    startDate = date.get(Calendar.DATE);
    //sets the handlers , performs set date
        MondayButton.setOnClickListener(view -> setDate(0));
        TuesdayButton.setOnClickListener(view -> setDate(1));
        WednesdayButton.setOnClickListener(view -> setDate(2));
        ThursdayButton.setOnClickListener(view -> setDate(3));
        FridayButton.setOnClickListener(view -> setDate(4));
        
        PrevButton.setOnClickListener(view -> moveWeek(-1));
        NextButton.setOnClickListener(view -> moveWeek(1));

        manager = Manager.getInstance();

        Buttons = new HashMap<>();
        Buttons.put(9, findViewById(R.id.nineamtext));
        Buttons.put(10, findViewById(R.id.tenamtext));
        Buttons.put(11, findViewById(R.id.elevenamtext));
        Buttons.put(12, findViewById(R.id.twelvepmtext));
        Buttons.put(13, findViewById(R.id.thirteenpmtext));
        Buttons.put(14, findViewById(R.id.fourteenpmtext));
        Buttons.put(15, findViewById(R.id.fifteenpmtext));
        Buttons.put(16, findViewById(R.id.sixteenpmtext));

        for(int i = 9; i<16; i++){
            int finalI = i;
            Buttons.get(i).setOnClickListener(view ->gotoHour(finalI));

        }

        setDate(0); //set to monday
    }
//changes the dates forword by week, reset the buttons
    private void moveWeek(int i) {
        startDate += i*7;
        MondayButton.setChecked(true);
        TuesdayButton.setChecked(false);
        WednesdayButton.setChecked(false);
        ThursdayButton.setChecked(false);
        FridayButton.setChecked(false);
        setDate(0);
    }
    //getting information for calender from mamnger class and setting up the calender obj to allow for the next stage
    private void setDate(int modifier){
        String postfix = "";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021,month, startDate+modifier, 0, 0, 0);
        SimpleDateFormat dtf = new SimpleDateFormat("E, MMM d");
        SimpleDateFormat ymd = new SimpleDateFormat("YYYY-MM-dd");
        DayText.setText(dtf.format(calendar.getTime()));

        //getting the slots info, by the hour
        this.day = startDate+modifier;
        Day day = manager.getDate(ymd.format(calendar.getTime()));
        if(day != null){
            int slot = 0;
            //setting the slots to be number of available slots
            for(int hour = 9;hour <17; hour ++){
                Hour hourObject = day.getHour(hour);
                if(hourObject == null){
                    Buttons.get(hour).setBackgroundColor(heatMap[0].toArgb());
                    Buttons.get(hour).setText("Available");
                    continue;
                }
                int count = hourObject.getCount();
                //gets the hour and set their background color according to the heatmap instruction
                Buttons.get(hour).setBackgroundColor(heatMap[count].toArgb());
                Buttons.get(hour).setText("Booked");
                if(count < 10){
                    //we are available
                    Buttons.get(hour).setText("Available");
                }
            }
            return;
        }
        //setting all slots default information
        for(int hour = 9;hour <17; hour ++){
            Buttons.get(hour).setText("Available");
        }

    }
    //takes user the to the selected hour screen
    private void gotoHour(int hour){
        Intent intent = new Intent(this, HourScreen.class);
        Bundle bundle = new Bundle(3);
        bundle.putInt("month", month);
        bundle.putInt("day", day);
        bundle.putInt("hour", hour);
        intent.putExtras(bundle);
    // goes to the next activity and once finish reload the data
        startActivity(intent);
        setDate(day-startDate);

    }

}