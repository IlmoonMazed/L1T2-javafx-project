package com.example.project;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManagement {
    private static final String INPUT_FILE_NAME = "players.txt";
    private static final String OUTPUT_FILE_NAME = "src/main/resources/players.txt";

    public List<Club> clubList = new ArrayList<>(); // contains all the clubs
    private static List<Country> countryList = new ArrayList<>(); // contains all the countries
    private static List<Position> positionList = new ArrayList<>(); // contains all the positions

    public boolean loadPlayers() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(PlayerManagement.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME)))) {
            PlayerManagement.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME);
            if (br == null) {
                throw new FileNotFoundException("File not found: " + INPUT_FILE_NAME);
            }
            while (true) {

                String line = br.readLine(); // reading line from file. each line contains info of exactly one player
                if (line == null) break;
                String[] data = line.split(","); // splitting the line into different info fields
                Player newPlayer;
                if (data.length == 9 && data[6].equalsIgnoreCase("")) { // jersey number available
                    newPlayer = new Player(data[0], data[1], Integer.parseInt(data[2]), Double.parseDouble(data[3]), data[4], data[5], Integer.parseInt(data[7]), data[8]);
                } else if (data.length == 9) { // jersey number NOT available
                    newPlayer = new Player(data[0], data[1], Integer.parseInt(data[2]), Double.parseDouble(data[3]), data[4], data[5], Integer.parseInt(data[6]), Integer.parseInt(data[7]), data[8]);
                } else { // failed to split into 8 info fields
                    return false;
                }

                addPlayerToClubList(newPlayer);
                addPlayerToCountryList(newPlayer);
                addPlayerToPositionList(newPlayer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;

    }

    // Adding to the clubList
    private boolean addPlayerToClubList(Player newPlayer) {
        for (Club club : clubList) {
            if (newPlayer.getClub().equalsIgnoreCase(club.getName())) {
                return club.addPlayerToClub(newPlayer);
            }
        }

        Club newClub = new Club(newPlayer.getClub());
        boolean flag1 = newClub.addPlayerToClub(newPlayer);
        boolean flag2 = clubList.add(newClub);
        return flag1 && flag2;

    }

    // Adding to the countryList
    private boolean addPlayerToCountryList(Player newPlayer) {
        for (Country country : countryList) {
            if (newPlayer.getCountry().equalsIgnoreCase(country.getName())) {
                return country.addPlayerToCountry(newPlayer);
            }
        }

        Country newCountry = new Country(newPlayer.getCountry());
        boolean flag1 = newCountry.addPlayerToCountry(newPlayer);
        boolean flag2 = countryList.add(newCountry);
        return flag1 && flag2;

    }

    // Adding to the positionList
    private boolean addPlayerToPositionList(Player newPlayer) {
        for (Position position : positionList) {
            if (newPlayer.getPosition().equalsIgnoreCase(position.getName())) {
                return position.addPlayerToPosition(newPlayer);
            }
        }

        Position newPosition = new Position(newPlayer.getPosition());
        boolean flag1 = newPosition.addPlayerToPosition(newPlayer);
        boolean flag2 = positionList.add(newPosition);
        return flag1 && flag2;

    }

    public boolean savePlayers() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        for (Club club : clubList) {
            for (Player player : club.getClubPlayerList()) {
                bw.write(player.toString());
                bw.write(System.lineSeparator());
            }
        }
        bw.close();
        return true;
    }

    public boolean playerAlreadyExists(String name) {
        for (Club club : clubList) {
            for (Player player : club.getClubPlayerList()) {
                if (player.getName().equalsIgnoreCase(name)) return true;
            }
        }
        return false;
    }

    public Player searchByName(String name) {
        for (Club club : clubList) {
            for (Player player : club.getClubPlayerList()) {
                if (player.getName().equalsIgnoreCase(name)) {
                    return player;
                }
            }
        }
        return null;
    }

    public List<Player> searchByClubAndCountry(String clubName, String country) {
        List<Player> matchedPlayers = new ArrayList<>();

        if (clubName.equalsIgnoreCase("ANY")) {
            for (Club club : clubList) {
                for (Player player : club.getClubPlayerList()) {
                    if (player.getCountry().equalsIgnoreCase(country)) matchedPlayers.add(player);
                }
            }
        } else {
            for (Club club : clubList) {
                if (club.getName().equalsIgnoreCase(clubName)) {
                    for (Player player : club.getClubPlayerList()) {
                        if (player.getCountry().equalsIgnoreCase(country)) {
                            matchedPlayers.add(player);
                        }
                    }
                    break;
                }
            }
        }
        return matchedPlayers.isEmpty() ? null : matchedPlayers;
    }

    public List<Player> searchByPosition(String position) {
        List<Player> matchedPlayers = new ArrayList<>();

        for (Club club : clubList) {
            for (Player player : club.getClubPlayerList()) {
                if (player.getPosition().equalsIgnoreCase(position)) matchedPlayers.add(player);
            }
        }

        return matchedPlayers.isEmpty() ? null : matchedPlayers;
    }

    public List<Player> searchBySalaryRange(int min, int max) {
        List<Player> matchedPlayers = new ArrayList<>();

        for (Club club : clubList) {
            for (Player player : club.getClubPlayerList()) {
                if (player.getWeeklySalary() >= min && player.getWeeklySalary() <= max) matchedPlayers.add(player);
            }
        }

        return matchedPlayers.isEmpty() ? null : matchedPlayers;
    }

    public Map<String, Integer> getCountryWisePlayerCount() {
        Map<String, Integer> countryWisePlayerCount = new HashMap<>();

        for (Country country : countryList) {
            countryWisePlayerCount.put(country.getName(), country.getNumberOfPlayers());
        }

        return countryWisePlayerCount.isEmpty() ? null : countryWisePlayerCount;
    }

    public List<Player> maxSalaryPlayer(String clubName) {

        for (Club club : clubList) {
            if (club.getName().equalsIgnoreCase(clubName)) {
                return club.maxSalaryPlayer();
            }
        }
        return null;
    }

    public List<Player> maxAgePlayer(String clubName) {

        for (Club club : clubList) {
            if (club.getName().equalsIgnoreCase(clubName)) {
                return club.maxAgePlayer();
            }
        }
        return null;
    }

    public List<Player> maxHeightPlayer(String clubName) {
        for (Club club : clubList) {
            if (club.getName().equalsIgnoreCase(clubName)) {
                return club.maxHeightPlayer();
            }
        }
        return null;
    }

    public long totalYearlySalary(String clubName) {
        for (Club club : clubList) {
            if (club.getName().equalsIgnoreCase(clubName)) {
                return club.totalWeeklySalary() * 52;
            }
        }
        return -1;
    }

    public boolean addPlayer(Player newPlayer) {
        return addPlayerToClubList(newPlayer) && addPlayerToCountryList(newPlayer) && addPlayerToPositionList(newPlayer);
    }

    public void changeClub(Player player, String newClub) {
        String oldClub = player.getClub();

        // Remove player from the old club
        for (Club club : clubList) {
            if (club.getName().equalsIgnoreCase(oldClub)) {
                club.getClubPlayerList().removeIf(p -> p.getName().equalsIgnoreCase(player.getName())); // Safely remove player
                break;
            }
        }

        // Update the player's club
        player.setClub(newClub);

        // Add player to the new club
        for (Club club : clubList) {
            if (club.getName().equalsIgnoreCase(newClub)) {
                club.addPlayerToClub(player); // Assume this method safely handles the addition
                break;
            }
        }
    }

}