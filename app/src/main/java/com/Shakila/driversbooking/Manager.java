package com.Shakila.driversbooking;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//manager class
public class Manager {
    private String[] teachers = {
            "Matt Johnson", "Jane Doe", "Mary Popin", "James Honors", "Stacy Robertson", "Harry Woods", "Robecca Poper", "Leo Johns", "Adam Rob", "Collin Doe"
    };
    //map of days in the caladar
    private Map<String, Day> Dates;

    //singleton manager instance
    private static Manager instance;

    //return the single instance of the manager class
    public static Manager getInstance(){
        if(instance == null){
            instance = new Manager();
        }
        return instance;
    }
//private constructor, so only get-instance function can get it
    private Manager(){
        //map is  interface
        //hashmap is implementation of map
        Dates = new HashMap<>();
    }

    //return day object based off its string represantation of the date
    public Day getDate(String date) {
        if(Dates.containsKey(date)){
            return Dates.get(date);
        }
        return null;
    }
    //saves current set of timeslot to shared prefrences
    public void SaveData(Activity activity){
        //access app prefrences (bundle)
        SharedPreferences.Editor editor = activity.getApplication().getSharedPreferences("DriverBookingPref", Context.MODE_PRIVATE).edit();

        //converting manger object to javascript object notation
        String json = (new Gson()).toJson(this);
        editor.putString("data", json);
        editor.apply();


    }
    //retreiving the time slots from shared prefernces
    public void LoadData(Activity activity){

        SharedPreferences preferences=  activity.getApplication().getSharedPreferences("DriverBookingPref", Context.MODE_PRIVATE);
        Manager data = (new Gson()).fromJson(preferences.getString("data", "{}"), Manager.class);
        Dates = data.Dates;

    }
    //inserts a day object into the slot
    public void setDate(String date, Day daySlot) {
        Dates.put(date,daySlot);
    }

    //return the list of teachers names
    public String[] getTeachers() {
        return teachers;
    }

    //creating daily histogram
    //move through every day between start and end, and add the number of booked lessons into the histogram map
    public Map<String, Integer > getDailyHistogram(LocalDate Start, LocalDate End){
        Map<String, Integer> Histogram = new TreeMap<>();
        Period Time = Period.between(Start, End);
        for(int i = 0; i< Time.getDays(); i++){
            LocalDate Current = Start.plusDays(i);
            //make map of all days between start and end with zero as number of booking we have done
            Histogram.put(Current.toString(),0 );
            Day day = getDate(Current.toString());
            if (day != null){
                int Count = 0;
                for(int j = 9; j< 17; j++){
                    Hour hour = day.getHour(j);
                    if(hour != null){
                        Count += hour.getCount();
                    }
                }
                Histogram.put(Current.toString(),Count);
            }
        }
        return Histogram;
    }

    //creating HOurly histogram
    public Map<String, Integer > getHourlyHistogram(LocalDate Start, LocalDate End){
        Map<String, Integer> Histogram = new TreeMap<>();
        Period Time = Period.between(Start, End);
        for(int i = 0; i< Time.getDays(); i++){
            LocalDate Current = Start.plusDays(i);
            Day day = getDate(Current.toString());
            for(int j =9; j < 17; j++){
                Histogram.put(Current.atTime(j,0).toString(),0 );
                if (day != null){
                    Hour hour = day.getHour(j);
                    if(hour != null){
                        Histogram.put(Current.atTime(j,0).toString(),hour.getCount());
                    }
                }
            }
            //make map of all days between start and end with zero as number of booking we have done


        }
        return Histogram;
    }
    //go through all the hour and match the licence num that we enter
    public List<TimeSlot> getByLicenceNum(String LicenceNum, LocalDate Start, LocalDate End){
        List<TimeSlot> timeSlots = new ArrayList<>();
        Period Time = Period.between(Start, End);
        SimpleDateFormat ymd = new SimpleDateFormat("YYYY-MM-dd");
        for(int i = 0; i < Time.getDays(); i++){
            LocalDate Current = Start.plusDays(i);
            Day day = getDate(ymd.format(Current));
            if(day != null) {
                for(int j = 9; j< 17; j++){
                    Hour hour = day.getHour(j);
                    if(hour != null){
                        //loop through every hour
                        for(TimeSlot t:hour.getAll()){
                            if(t.getLicenceNumber().equals(LicenceNum)){
                                timeSlots.add(t);
                            }
                        }
                    }
                }
            }
        }
        return timeSlots;
    }
    //return all the timeslots given based on its string representation
    public List<TimeSlot> getAllForDay(LocalDate date){
        SimpleDateFormat ymd = new SimpleDateFormat("YYYY-MM-dd");
        List<TimeSlot> timeSlots = new ArrayList<>();
        Day day = getDate(ymd.format(date));
        if(day != null){
            for(int j = 9; j< 17; j++){
                Hour hour = day.getHour(j);
                if(hour != null){
                    timeSlots.addAll(hour.getAll());
                }
            }
        }
        return timeSlots;
    }
}
