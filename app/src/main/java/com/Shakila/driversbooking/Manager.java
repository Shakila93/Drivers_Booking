package com.Shakila.driversbooking;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Manager {
    private Map<Date, Day> Dates;
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

    public Day getDate(Date date) {
        if(Dates.containsKey(date)){
            return Dates.get(date);
        }
        return null;
    }
    public void SaveData(Activity activity){
        //access app prefrences (bundle)
        SharedPreferences.Editor editor = activity.getPreferences(Context.MODE_PRIVATE).edit();
        String json = (new Gson()).toJson(Dates);
        editor.putString("data", json);
        editor.apply();


    }
    public void LoadData(Activity activity){
        SharedPreferences preferences= activity.getPreferences(Context.MODE_PRIVATE);
        Dates = (new Gson()).fromJson(preferences.getString("data", "{}"), Dates.getClass());

    }
}
