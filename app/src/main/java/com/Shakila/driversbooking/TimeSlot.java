package com.Shakila.driversbooking;

public class TimeSlot {
    private String Instructor;
    private String User;
    private String licenceNumber;
    private String licenceVersion;
    private String DateOfBirth;

    public TimeSlot(String teacher, String name, String lNumber, String lVersion, String DOB){
        Instructor = teacher;
        User = name;
        licenceNumber = lNumber;
        licenceVersion = lVersion;
        DateOfBirth = DOB;


    }
}

