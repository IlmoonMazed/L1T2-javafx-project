package com.example.project;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {

    private ServerSocket serverSocket;
    public PlayerManagement playerManagement = new PlayerManagement();
    public static Map<String, String> clubDatabase = new HashMap<>();
    private CopyOnWriteArrayList<SocketWrapper> clientList = new CopyOnWriteArrayList<>();
    public List<Player> playerMarket = new ArrayList<>();


    Server() {

        try {
            playerManagement.loadPlayers();
            loadPlayerMarket();
            loadClubDatabase();
            serverSocket = new ServerSocket(33333);

            // Add shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                savePlayerMarket();
                savePlayerManagement();
            }));

            while (true) {
                Socket clientSocket = serverSocket.accept();
                SocketWrapper socketWrapper = new SocketWrapper(clientSocket);
                serve(socketWrapper);
                clientList.add(socketWrapper); // Add client to the list
            }
        } catch (Exception e) {
            System.err.println("Server starts:" + e);
        }
    }

    public void serve(SocketWrapper socketWrapper) throws IOException {
        new ReadThreadServer(socketWrapper, this);
    }

    public synchronized void removeClient(SocketWrapper client) {
        clientList.remove(client);
    }

    public void broadcastUpdate(Object update) {
        for (SocketWrapper client : clientList) {
            try {
                if (update != null && update instanceof List<?>) {
                    List<?> list = (List<?>) update;
                    if (list.get(0) instanceof Player) {
                        List<Player> pm = (List<Player>) update;
                        List<Player> newPlayerMarket = new ArrayList<>();
                        newPlayerMarket.addAll(pm);
                        synchronized (newPlayerMarket) {
                            client.write("playerMarket" + playerMarket.toString());
                        }
                    }
                } else if (update == null) {
                    client.write("playerMarket[]");
                }

            } catch (IOException e) {
                removeClient(client); // Remove disconnected client
            }
        }
    }

    public void broadcastUpdateExcept(Object update, SocketWrapper clientSocket) {
        for (SocketWrapper client : clientList) {
            if (client != clientSocket) {
                try {
                    if (update instanceof String) {
                        client.write(update);
                    }
                } catch (IOException e) {
                    removeClient(client); // Remove disconnected client
                }
            }

        }
    }

    public boolean loadPlayerMarket() {

        String INPUT_FILE_NAME = "playerMarket.txt";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                PlayerManagement.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME)))) {
            ReadThreadServer.class.getClassLoader().getResourceAsStream(INPUT_FILE_NAME);
            if (br == null) {
                throw new FileNotFoundException("File not found: " + INPUT_FILE_NAME);
            }
            while (true) {

                String line = br.readLine(); // reading line from file. each line contains info of exactly on player
                if (line == null)
                    break;
                String[] data = line.split(","); // splitting the line into different info fields
                Player newPlayer;
                if (data.length == 9 && data[6].equalsIgnoreCase("")) { // jersey number available
                    newPlayer = new Player(data[0], data[1], Integer.parseInt(data[2]),
                            Double.parseDouble(data[3]), data[4], data[5], Integer.parseInt(data[7]), data[8]);
                } else if (data.length == 9) { // jersey number NOT available
                    newPlayer = new Player(data[0], data[1], Integer.parseInt(data[2]),
                            Double.parseDouble(data[3]), data[4], data[5], Integer.parseInt(data[6]),
                            Integer.parseInt(data[7]), data[8]);
                } else { // failed to split into 9 info fields
                    return false;
                }

                playerMarket.add(newPlayer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;

    }

    public void loadClubDatabase() {
        String INPUT_FILE_NAME = "clubDatabase.txt";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(INPUT_FILE_NAME)))) {
            if (br == null) {
                throw new FileNotFoundException("File not found: " + INPUT_FILE_NAME);
            }
            while (true) {

                String line = br.readLine(); // reading line from file. each line contains info of exactly on player
                if (line == null)
                    break;
                String[] data = line.split(","); // splitting the line into different info fields
                clubDatabase.put(data[0].toUpperCase(), data[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void UpdatePlayerMarketAndManagement(String playerName, String newClub) {
        Iterator<Player> iterator = playerMarket.iterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (player.getName().equalsIgnoreCase(playerName)) {
                iterator.remove(); // Safely remove the player

                // Update the club information
                playerManagement.changeClub(player, newClub);
                break; // Player found and removed, no need to continue
            }
        }

    }

    public void UpdatePlayerMarket(String playerName) {
        for (Club club : playerManagement.clubList) {
            for (Player player : club.getClubPlayerList()) {
                if (player.getName().equalsIgnoreCase(playerName)) {
                    playerMarket.add(player);
                    break;
                }
            }
        }
    }

    private void savePlayerManagement() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/players.txt"));
            for (Club club : playerManagement.clubList) {
                for (Player player : club.getClubPlayerList()) {
                    bw.write(player.toString());
                    bw.write(System.lineSeparator());
                }
            }
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void savePlayerMarket() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/playerMarket.txt"));
            for (Player player : playerMarket) {
                bw.write(player.toString());
                bw.write(System.lineSeparator());
            }
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Server();
    }


}
