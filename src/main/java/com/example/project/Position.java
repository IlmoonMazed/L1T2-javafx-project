package com.example.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Position implements Serializable {
    private String name;
    private int numberOfPlayers;

    private List<Player> positionPlayerList = new ArrayList<>();         // contains all the player info of the country

    public Position(String name) {
        this.name = name;
        this.numberOfPlayers = 0;
    }

    public boolean addPlayerToPosition(Player p) {
        positionPlayerList.add(p);
        if (positionPlayerList.size() == (numberOfPlayers + 1)) {
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
        return positionPlayerList.size();
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public List<Player> getPositionPlayerList() {
        return positionPlayerList;
    }

    public void setPositionPlayerList(List<Player> positionPlayerList) {
        this.positionPlayerList = positionPlayerList;
    }
}
