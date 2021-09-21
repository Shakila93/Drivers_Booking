package com.Shakila.driversbooking;

import java.util.HashMap;
import java.util.Map;

public class Day {
    private Map<Integer, Hour> Hours;
    public Day(){
        Hours = new HashMap<>();

    }

    public Hour getHour(int hour) {
        if(Hours.containsKey(hour)){
            return Hours.get(hour);
        }
        return null;
    }

    public void setHour(int hour, Hour data) {
        Hours.put(hour, data);
    }
}
