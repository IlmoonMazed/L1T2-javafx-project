package com.example.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Club implements Serializable {
    private String name;
    private int numberOfPlayers;

    private List<Player> clubPlayerList = new ArrayList<>();         // contains all the player info of the club

    public Club(String name) {
        this.name = name;
        numberOfPlayers = 0;
    }

    public boolean addPlayerToClub(Player p) {
        clubPlayerList.add(p);
        if (clubPlayerList.size() == (numberOfPlayers + 1)) {
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
        return clubPlayerList.size();
    }

    public List<Player> getClubPlayerList() {
        return clubPlayerList;
    }

    public List<Player> maxSalaryPlayer() {
        List<Player> players = new ArrayList<>();

        int salary, maxSalary = 0;

        for (Player player : clubPlayerList) {
            salary = player.getWeeklySalary();
            if (salary > maxSalary) {
                maxSalary = salary;
                players.clear();
                players.add(player);
            } else if (salary == maxSalary) {
                players.add(player);
            }
        }
        return players.isEmpty() ? null : players;
    }


    public List<Player> maxAgePlayer() {
        List<Player> players = new ArrayList<>();

        int age, maxAge = 0;
        for (Player player : clubPlayerList) {
            age = player.getAge();
            if (age > maxAge) {
                maxAge = age;
                players.clear();
                players.add(player);
            } else if (age == maxAge) {
                players.add(player);
            }
        }
        return players.isEmpty() ? null : players;
    }

    public List<Player> maxHeightPlayer() {
        List<Player> players = new ArrayList<>();

        double height, maxHeight = 0;
        for (Player player : clubPlayerList) {
            height = player.getHeight();
            if (height > maxHeight) {
                maxHeight = height;
                players.clear();
                players.add(player);
            } else if (height == maxHeight) {
                players.add(player);
            }
        }
        return players.isEmpty() ? null : players;
    }

    public long totalWeeklySalary() {
        long totalWeeklySalary = 0;
        for (Player player : clubPlayerList) {
            totalWeeklySalary += player.getWeeklySalary();
        }
        return totalWeeklySalary;
    }

}