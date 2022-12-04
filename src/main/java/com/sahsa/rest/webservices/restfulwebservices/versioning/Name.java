package com.sahsa.rest.webservices.restfulwebservices.versioning;

public class Name {
    private String firstName;
    private String LustName;

    public Name(String firstName, String lustName) {
        this.firstName = firstName;
        LustName = lustName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLustName() {
        return LustName;
    }

    @Override
    public String toString() {
        return "Name{" +
                "firstName='" + firstName + '\'' +
                ", LustName='" + LustName + '\'' +
                '}';
    }
}
