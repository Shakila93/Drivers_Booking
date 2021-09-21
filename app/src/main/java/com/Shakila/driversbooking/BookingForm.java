package com.Shakila.driversbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BookingForm extends AppCompatActivity {
    private EditText fNametext;
    private EditText lNametext;
    private EditText DOBtext;
    private EditText lNumbertext;
    private EditText lVersiontext;
    private TextView teachertext;
    private TextView datetext;

    private Button SubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);
        fNametext = findViewById(R.id.booking_first_name);
        lNametext = findViewById(R.id.booking_last_name);
        DOBtext = findViewById(R.id.booking_dateofbirth);
        lNumbertext = findViewById(R.id.booking_licencenumber);
        lVersiontext = findViewById(R.id.booking_licenceversion);
        teachertext = findViewById(R.id.booking_instructor);
        datetext = findViewById(R.id.booking_time);
        SubmitButton = findViewById(R.id.button);

        final Intent intent = getIntent();
        int month = intent.getIntExtra("month", 0);
        int day =intent.getIntExtra("day", 0);
        int hour = intent.getIntExtra("hour", 0);

        final Calendar calendar = Calendar.getInstance();
        calendar.set(2021,month, day, hour, 0);
        SimpleDateFormat dtf = new SimpleDateFormat("E, MMM d 'at' k:00");
        datetext.setText(dtf.format(calendar.getTime()));
        teachertext.setText(Manager.getInstance().getTeachers()[intent.getIntExtra("teacher", 0)]);
        final Activity thisActivity = this;


        SubmitButton.setOnClickListener(view -> {
            Bundle bundle = intent.getExtras();
            bundle.putString("firstname", fNametext.getText().toString());
            bundle.putString("lastname", lNametext.getText().toString());
            bundle.putString("dateofbirth", DOBtext.getText().toString());
            bundle.putString("licenceNumber", lNumbertext.getText().toString());
            bundle.putString("licenceversion", lVersiontext.getText().toString());
            Intent gotoConfirmation = new Intent(thisActivity, ConfirmationScreen.class);
            gotoConfirmation.putExtras(bundle);
            startActivity(gotoConfirmation);



        });
    }
}