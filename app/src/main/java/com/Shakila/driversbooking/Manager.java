package com.Shakila.driversbooking;

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
}
