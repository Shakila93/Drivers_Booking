package com.Shakila.driversbooking;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
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

    private final String[] months = {
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
    };
    private final String[] days = {
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"
            
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title_bar);

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

    private void moveWeek(int i) {
        startDate += i*7;
        MondayButton.setChecked(true);
        TuesdayButton.setChecked(false);
        WednesdayButton.setChecked(false);
        ThursdayButton.setChecked(false);
        FridayButton.setChecked(false);
        setDate(0);
    }
    private void setDate(int modifier){
        String postfix = "";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021,month, startDate+modifier, 0, 0);
        SimpleDateFormat dtf = new SimpleDateFormat("E, MMM d");
        DayText.setText(dtf.format(calendar.getTime()));

        this.day = startDate+modifier;
        Date date = new Date(2021, month, startDate + modifier);
        Day day = manager.getDate(date);
        if(day != null){
            int slot = 0;
            //setting the slots to be number of available slots
            for(int hour = 9;hour <17; hour ++){
                Hour hourObject = day.getHour(hour);
                if(hourObject == null){
                    Buttons.get(hour).setText("Available");
                    continue;
                }
                int count = hourObject.getCount();
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

        startActivity(intent);

    }

}