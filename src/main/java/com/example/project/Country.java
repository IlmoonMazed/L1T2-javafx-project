package com.example.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Country implements Serializable {
    private String name;
    private int numberOfPlayers;

    private List<Player> countryPlayerList = new ArrayList<>();         // contains all the player info of the country

    public Country(String name) {
        this.name = name;
        this.numberOfPlayers = 0;
    }

    public boolean addPlayerToCountry(Player p) {
        countryPlayerList.add(p);
        if (countryPlayerList.size() == (numberOfPlayers + 1)) {
            numberOfPlayers++;
            return true;
        } else return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfPlayers() {
        return countryPlayerList.size();
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public List<Player> getCountryPlayerList() {
        return countryPlayerList;
    }

    public void setCountryPlayerList(List<Player> countryPlayerList) {
        this.countryPlayerList = countryPlayerList;
    }

}
