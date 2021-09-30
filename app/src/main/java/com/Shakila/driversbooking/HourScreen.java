package com.Shakila.driversbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
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
    private TextView[] teacherText;
    private int month;
    private int day;
    private  int hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hour_screen);

        //getting data(int) from the bundle
        Intent intent = getIntent();

        month = intent.getIntExtra("month", 0);
        day =intent.getIntExtra("day", 0);
        hour = intent.getIntExtra("hour", 0);

        timetext = findViewById(R.id.hour_text);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2021,month, day, hour, 0);
        SimpleDateFormat dtf = new SimpleDateFormat("E, MMM d 'at' k:00");
        SimpleDateFormat ymd = new SimpleDateFormat("YYYY-MM-dd");
        timetext.setText(dtf.format(calendar.getTime()));

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

        teacherText = new TextView[10];
        teacherText[0] = findViewById(R.id.teacher1);
        teacherText[1] = findViewById(R.id.teacher2);
        teacherText[2] = findViewById(R.id.teacher3);
        teacherText[3] = findViewById(R.id.teacher4);
        teacherText[4] = findViewById(R.id.teacher5);
        teacherText[5] = findViewById(R.id.teacher6);
        teacherText[6] = findViewById(R.id.teacher7);
        teacherText[7] = findViewById(R.id.teacher8);
        teacherText[8] = findViewById(R.id.teacher9);
        teacherText[9] = findViewById(R.id.teacher10);
        String [] Teachers = Manager.getInstance().getTeachers();
        Day dateObj = Manager.getInstance().getDate(ymd.format(calendar.getTime()));
        Hour hourObj = null;
        if(dateObj != null){
            hourObj = dateObj.getHour(hour);
        }
        if(hourObj == null){
            hourObj = new Hour();
        }

        for(int i = 0; i < 10; i++){
            int finalI = i;
            teacherText[i].setText(Teachers[i]);
            TimeSlot Slot = hourObj.getTimeSlot(Teachers[i]);
            if(Slot==null){
                bookingtext[i].setOnClickListener(view -> gotoBooking(finalI));
            }
            else{
                bookingtext[i].setText("Not Available");
                bookingtext[i].setBackgroundResource(R.color.red);
                bookingtext[i].setTextColor(Color.WHITE);
            }

        }

    }
    private void gotoBooking(int teacherId){
        Intent intent = new Intent(this, BookingForm.class);
        Bundle bundle = getIntent().getExtras();
        bundle.putInt("teacher", teacherId);

        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }
}