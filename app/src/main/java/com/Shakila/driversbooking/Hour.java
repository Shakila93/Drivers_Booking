package com.Shakila.driversbooking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hour {
    private Map<String, TimeSlot> Slots;
    public Hour(){
        Slots = new HashMap<>();
    }

    public int getCount() {
        return Slots.size();
    }

    public void setTimeSlot(String name, TimeSlot timeSlot) {
        Slots.put(name, timeSlot);

    }
    public TimeSlot getTimeSlot(String name){
        if(Slots.containsKey(name)){
            return Slots.get(name);
        }
        return null;
    }
}
