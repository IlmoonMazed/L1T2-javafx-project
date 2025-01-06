package com.example.project;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private int age, jerseyNumber, weeklySalary;
    private double height;
    private String club;
    private Country country = new Country(null);
    private Position position = new Position(null);
    private String image;

    public Player(String name, String country, int age, double height, String club, String position, int jerseyNumber, int weeklySalary, String image) {
        this.name = name;
        this.country.setName(country);
        this.age = age;
        this.height = height;
        this.club = club;
        this.position.setName(position);
        this.jerseyNumber = jerseyNumber;
        this.weeklySalary = weeklySalary;
        this.image = image;
    }

    public Player(String name, String country, int age, double height, String club, String position, int weeklySalary, String image) {
        this.name = name;
        this.country.setName(country);
        this.age = age;
        this.height = height;
        this.club = club;
        this.position.setName(position);
        this.weeklySalary = weeklySalary;
        this.jerseyNumber = -1;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country.getName();
    }

    public String getClub() {
        return club;
    }

    public String getPosition() {
        return position.getName();
    }

    public int getAge() {
        return age;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public int getWeeklySalary() {
        return weeklySalary;
    }

    public double getHeight() {
        return height;
    }

    public String getImage() {
        return image;
    }

    public void setClub(String newClub) {
        this.club = newClub;
    }

    @Override
    public String toString() { // to match the input file info format

        if (jerseyNumber == -1)     // jersey number not available
            return name + "," + country.getName() + "," + age + "," + height + "," + club + "," + position.getName() + ",," + weeklySalary + "," + image;

        else
            return name + "," + country.getName() + "," + age + "," + height + "," + club + "," + position.getName() + "," + jerseyNumber + "," + weeklySalary + "," + image;
    }
}
