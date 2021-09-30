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

public class Manager {
    private String[] teachers = {
            "Matt Johnson", "Jane Doe", "Mary Popin", "James Honors", "Stacy Robertson", "Harry Woods", "Robecca Poper", "Leo Johns", "Adam Rob", "Collin Doe"
    };
    private Map<String, Day> Dates;
    private static Manager instance;
    public static Manager getInstance(){
        if(instance == null){
            instance = new Manager();
        }
        return instance;
    }

    private Manager(){
        //map is  interface
        //hashmap is implementation of map
        Dates = new HashMap<>();
    }

    public Day getDate(String date) {
        if(Dates.containsKey(date)){
            return Dates.get(date);
        }
        return null;
    }
    public void SaveData(Activity activity){
        //access app prefrences (bundle)
        SharedPreferences.Editor editor = activity.getApplication().getSharedPreferences("DriverBookingPref", Context.MODE_PRIVATE).edit();
        String json = (new Gson()).toJson(this);
        editor.putString("data", json);
        editor.apply();


    }
    public void LoadData(Activity activity){

        SharedPreferences preferences=  activity.getApplication().getSharedPreferences("DriverBookingPref", Context.MODE_PRIVATE);
        Manager data = (new Gson()).fromJson(preferences.getString("data", "{}"), Manager.class);
        Dates = data.Dates;

    }

    public void setDate(String date, Day daySlot) {
        Dates.put(date,daySlot);
    }

    public String[] getTeachers() {
        return teachers;
    }

    //creating daily histogram
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
