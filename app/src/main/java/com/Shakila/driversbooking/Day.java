package com.Shakila.driversbooking;

import java.util.Map;

public class Day {
    private Map<Integer, Hour> Hours;

    public Hour getHour(int hour) {
        if(Hours.containsKey(hour)){
            return Hours.get(hour);
        }
        return null;
    }
}
