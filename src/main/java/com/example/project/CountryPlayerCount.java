package com.example.project;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CountryPlayerCount {

    private final StringProperty country;
    private final IntegerProperty playerCount;

    public CountryPlayerCount(String country, int playerCount) {
        this.country = new SimpleStringProperty(country);
        this.playerCount = new SimpleIntegerProperty(playerCount);
    }

    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public StringProperty countryProperty() {
        return country;
    }

    public int getPlayerCount() {
        return playerCount.get();
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount.set(playerCount);
    }

    public IntegerProperty playerCountProperty() {
        return playerCount;
    }
}