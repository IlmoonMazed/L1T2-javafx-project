package com.example.project;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static com.example.project.NavigationUtil.*;
import static java.lang.System.exit;

public class Client extends Application {

    public static SocketWrapper socketWrapper;
    public static Club clientClub = null;
    public static Map<String, Image> clubPlayerImages = new HashMap<>();
    public static List<Player> playerMarket = new ArrayList<>();
    public static Map<String, Image> marketPlayerImages = new HashMap<>();
    public static Map<String, Boolean> sellStates = new HashMap<>();

    public static void marketMap() {
        for (Player player : clientClub.getClubPlayerList()) {
            sellStates.put(player.getName().toUpperCase(), false);
        }
        for (Player player : playerMarket) {
            if (player.getClub().equals(clientClub.getName())) {
                sellStates.put(player.getName().toUpperCase(), true);
            }
        }

        loadMarketPlayerImages();
    }

    @Override
    public void start(Stage stage) throws IOException {
        socketWrapper = new SocketWrapper("127.0.0.1", 33333);
        stageScene(stage, "client-log-in-page.fxml", "Log In");


        // Set the on-close request handler
        stage.setOnCloseRequest(event -> {
            closeApplication();
        });
    }

    private static void closeApplication() {

        try {
            socketWrapper.stopListening();
            socketWrapper.closeConnection();
            socketWrapper.closeSocket();
            exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void readyForListening() throws IOException, ClassNotFoundException {

        socketWrapper.startListening(message -> {
            synchronized (message) {
                if (message instanceof String) {
                    String line = (String) message;
                    if (line.contains("playerMarket")) preparePlayerMarket(line);

                    else {
                        String[] tokens = line.split(",");
                        if (tokens.length > 1) {
                            if (tokens[0].equals("sold")) {
                                String soldPlayer = tokens[1];
                                Iterator<Player> iterator = clientClub.getClubPlayerList().iterator();
                                while (iterator.hasNext()) {
                                    Player p = iterator.next();
                                    if (p.getName().equals(soldPlayer)) {
                                        iterator.remove(); // Safely remove the player from the list
                                        sellStates.remove(p.getName().toUpperCase()); // newly added for sellstates
                                        break; //new added
                                    }
                                }


                            }
                        }
                    }
                }
            }
        });
    }

    static void preparePlayerMarket(String data) {
        // Remove the prefix "playerMarket" and square brackets
        data = data.substring(data.indexOf("[") + 1, data.lastIndexOf("]"));

        if (data.isEmpty()) {
            playerMarket.clear();
            return;
        }

        // Split the data
        String[] fields = data.split(",");
        List<Player> players = new ArrayList<>();

        // Parse each player (9 fields per player)
        for (int i = 0; i < fields.length; i += 9) {
            String name = fields[i].trim();
            String country = fields[i + 1].trim();
            int age = Integer.parseInt(fields[i + 2].trim());
            double height = Double.parseDouble(fields[i + 3].trim());
            String club = fields[i + 4].trim();
            String role = fields[i + 5].trim();
            int jerseyNumber = fields[i + 6].trim().isEmpty() ? -1 : Integer.parseInt(fields[i + 6].trim()); // Handle missing numbers
            int salary = Integer.parseInt(fields[i + 7].trim());
            String image = fields[i + 8].trim();


            Player player = new Player(name, country, age, height, club, role, jerseyNumber, salary, image);
            players.add(player);

        }

        playerMarket.clear();
        playerMarket.addAll(players);
        loadMarketPlayerImages();
    }


    public SocketWrapper getSocketWrapper() {
        return socketWrapper;
    }

    public static void loadMarketPlayerImages() {
        marketPlayerImages.clear();
        for (Player player : playerMarket) {
            Image tempImg = ImageLoader.getPlayerImage(player.getImage());
            marketPlayerImages.put(player.getImage(), tempImg);
        }
    }

    public static void loadClubPlayerImages() {
        clubPlayerImages.clear();
        for (Player player : clientClub.getClubPlayerList()) {
            Image tempImg = ImageLoader.getPlayerImage(player.getImage());
            clubPlayerImages.put(player.getImage(), tempImg);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}