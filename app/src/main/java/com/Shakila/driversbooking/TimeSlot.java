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

    public String getInstructor() {
        return Instructor;
    }

    public void setInstructor(String instructor) {
        Instructor = instructor;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public String getLicenceVersion() {
        return licenceVersion;
    }

    public void setLicenceVersion(String licenceVersion) {
        this.licenceVersion = licenceVersion;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }
}

