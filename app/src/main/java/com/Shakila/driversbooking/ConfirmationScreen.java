package com.Shakila.driversbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConfirmationScreen extends AppCompatActivity {
    private TextView dateText;
    private TextView teachertext;
    private TextView firstNameText;
    private TextView lastNameText;
    private TextView dateOfBirthText;
    private TextView licenceNumberText;
    private TextView licenceVersionText;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_screen);

        //binding
        dateText = findViewById(R.id.confirmation_date);
        teachertext = findViewById(R.id.confirmation_teacher);
        firstNameText = findViewById(R.id.confirmation_firstname);
        lastNameText = findViewById(R.id.confirmation_lastname);
        dateOfBirthText = findViewById(R.id.confirmation_dateofbirth);
        licenceNumberText = findViewById(R.id.confirmation_licencenumber);
        licenceVersionText = findViewById(R.id.confirmation_licenceversion);
        confirm = findViewById(R.id.confirmation_button);


        final Intent intent = getIntent();
        int month = intent.getIntExtra("month", 0);
        int day =intent.getIntExtra("day", 0);
        int hour = intent.getIntExtra("hour", 0);

        final Calendar calendar = Calendar.getInstance();
        calendar.set(2021,month, day, hour, 0,0);
        SimpleDateFormat dtf = new SimpleDateFormat("E, MMM d 'at' k:00");
        int TeacherID = intent.getIntExtra("teacher",0);
        String TeacherName = Manager.getInstance().getTeachers()[TeacherID];
        dateText.setText(dtf.format(calendar.getTime()));
        teachertext.setText(TeacherName);
        firstNameText.setText(intent.getStringExtra("firstname"));
        lastNameText.setText(intent.getStringExtra("lastname"));
        dateOfBirthText.setText(intent.getStringExtra("dateofbirth"));
        licenceNumberText.setText(intent.getStringExtra("licenceNumber"));
        licenceVersionText.setText(intent.getStringExtra("licenceversion"));
        final Activity thisActivity = this;

        //standarize time
        confirm.setOnClickListener(view -> {
            Manager manager = Manager.getInstance();
            SimpleDateFormat ymd = new SimpleDateFormat("YYYY-MM-dd");
            Day daySlot = manager.getDate(ymd.format(calendar.getTime()));
            if(daySlot == null){
                daySlot = new Day();
            }
            Hour hourSlot = daySlot.getHour(hour);
            if(hourSlot == null){
                hourSlot = new Hour();
            }
            hourSlot.setTimeSlot(TeacherName, new TimeSlot(TeacherName, String.format
                    ("%s %s", firstNameText.getText(), lastNameText.getText()),
                    intent.getStringExtra("licenceNumber"), intent.getStringExtra
                    ("licenceversion"), intent.getStringExtra("dateofbirth")));
            daySlot.setHour(hour, hourSlot);
            manager.setDate(ymd.format(calendar.getTime()),daySlot);
            manager.SaveData(thisActivity);
            /* gotoMain = new Intent(thisActivity, MainActivity.class);
            gotoMain.putExtras(intent.getExtras());
            startActivity(gotoMain);*/
            finish();
        });
    }
}