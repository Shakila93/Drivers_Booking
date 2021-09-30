package com.Shakila.driversbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class Histogram extends AppCompatActivity {
    private TextView[] Days;
    private TextView[] Hours;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histogram);

        Days = new TextView[5];
        Days[0] = findViewById(R.id.histogram_monday);
        Days[1] = findViewById(R.id.histogram_tuesday);
        Days[2] = findViewById(R.id.histogram_wednesday);
        Days[3] = findViewById(R.id.histogram_thursday);
        Days[4] = findViewById(R.id.histogram_friday);

        Hours = new TextView[40];
        Hours[0] = findViewById(R.id.histogram_monday_09am);
        Hours[1] = findViewById(R.id.histogram_monday_10am);
        Hours[2] = findViewById(R.id.histogram_monday_11am);
        Hours[3] = findViewById(R.id.histogram_monday_12pm);
        Hours[4] = findViewById(R.id.histogram_monday_13pm);
        Hours[5] = findViewById(R.id.histogram_monday_14pm);
        Hours[6] = findViewById(R.id.histogram_monday_15pm);
        Hours[7] = findViewById(R.id.histogram_monday_16pm);

        Hours[8] = findViewById(R.id.histogram_tuesday_09am);
        Hours[9] = findViewById(R.id.histogram_tuesday_10am);
        Hours[10] = findViewById(R.id.histogram_tuesday_11am);
        Hours[11] = findViewById(R.id.histogram_tuesday_12pm);
        Hours[12] = findViewById(R.id.histogram_tuesday_13pm);
        Hours[13] = findViewById(R.id.histogram_tuesday_14pm);
        Hours[14] = findViewById(R.id.histogram_tuesday_15pm);
        Hours[15] = findViewById(R.id.histogram_tuesday_16pm);

        Hours[16] = findViewById(R.id.histogram_wednesday_09am);
        Hours[17] = findViewById(R.id.histogram_wednesday_10am);
        Hours[18] = findViewById(R.id.histogram_wednesday_11am);
        Hours[19] = findViewById(R.id.histogram_wednesday_12pm);
        Hours[20] = findViewById(R.id.histogram_wednesday_13pm);
        Hours[21] = findViewById(R.id.histogram_wednesday_14pm);
        Hours[22] = findViewById(R.id.histogram_wednesday_15pm);
        Hours[23] = findViewById(R.id.histogram_wednesday_16pm);

        Hours[24] = findViewById(R.id.histogram_thursday_09am);
        Hours[25] = findViewById(R.id.histogram_thursday_10am);
        Hours[26] = findViewById(R.id.histogram_thursday_11am);
        Hours[27] = findViewById(R.id.histogram_thursday_12pm);
        Hours[28] = findViewById(R.id.histogram_thursday_13pm);
        Hours[29] = findViewById(R.id.histogram_thursday_14pm);
        Hours[30] = findViewById(R.id.histogram_thursday_15pm);
        Hours[31] = findViewById(R.id.histogram_thursday_16pm);

        Hours[32] = findViewById(R.id.histogram_friday_09am);
        Hours[33] = findViewById(R.id.histogram_friday_10am);
        Hours[34] = findViewById(R.id.histogram_friday_11am);
        Hours[35] = findViewById(R.id.histogram_friday_12pm);
        Hours[36] = findViewById(R.id.histogram_friday_13pm);
        Hours[37] = findViewById(R.id.histogram_friday_14pm);
        Hours[38] = findViewById(R.id.histogram_friday_15pm);
        Hours[39] = findViewById(R.id.histogram_friday_16pm);

        Calendar date =  Calendar.getInstance();
        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        TimeZone tz = date.getTimeZone();
        ZoneId zID = ZoneId.systemDefault();
        if(tz != null){
            zID = tz.toZoneId();
        }
        LocalDate start = LocalDateTime.ofInstant(date.toInstant(), zID).toLocalDate();
        Map<String, Integer> DailyHistogram = Manager.getInstance().getDailyHistogram(start, start.plusDays(5));
        List<Integer> Values = new ArrayList<>(DailyHistogram.values());
        for(int i = 0; i < DailyHistogram.size(); i++){
            int value = Values.get(i)/8;
            Days[i].setBackgroundColor(heatMap[value].toArgb());
        }
        Map<String, Integer> HourlyHistogram = Manager.getInstance().getHourlyHistogram(start, start.plusDays(5));
        Values = new ArrayList<>(HourlyHistogram.values());
        for(int i = 0; i < HourlyHistogram.size(); i++){
            Hours[i].setBackgroundColor(heatMap[Values.get(i)].toArgb());
        }
    }

}