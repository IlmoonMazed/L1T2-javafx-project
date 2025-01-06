package com.example.project;

import java.io.IOException;

import static com.example.project.Server.clubDatabase;


public class ReadThreadServer implements Runnable {
    private final Thread thr;
    private final SocketWrapper socketWrapper;
    private Server server;
    private String clientName = null;

    public ReadThreadServer(/* PlayerManagement playerManagement, */ SocketWrapper socketWrapper, Server server) {

        this.socketWrapper = socketWrapper;
        this.server = server;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = socketWrapper.read();

                if (o != null) {
                    if (o instanceof String) {
                        String line = (String) o;
                        String[] tokens = line.split(",");
                        switch (tokens[0]) {
                            case "login":
                                clientName = tokens[1];
                                String password = tokens[2];
                                if (clubDatabase.containsKey(clientName.toUpperCase())) {
                                    if (clubDatabase.get(clientName.toUpperCase()).equals(password)) {
                                        for (Club club : server.playerManagement.clubList) {
                                            if (clientName.equalsIgnoreCase(club.getName())) {
                                                clientName = club.getName();
                                                socketWrapper.write(club);
                                                socketWrapper.write(server.playerMarket);
                                            }
                                        }
                                    } else {
                                        socketWrapper.write(null);
                                    }
                                } else {
                                    socketWrapper.write(null);
                                }

                                break;
                            case "bought":
                            case "cancel_sell": {
                                String playerName = tokens[1];
                                server.UpdatePlayerMarketAndManagement(playerName, clientName);
                                if (!server.playerMarket.isEmpty()) {
                                    for (Player p : server.playerMarket) {
                                    }

                                    server.broadcastUpdate(server.playerMarket);
                                } else server.broadcastUpdate(null);
                                server.broadcastUpdateExcept(("sold," + playerName), socketWrapper);
                                break;
                            }
                            case "sell": {
                                String playerName = tokens[1];
                                server.UpdatePlayerMarket(playerName);
                                for (Player p : server.playerMarket) {
                                }

                                server.broadcastUpdate(server.playerMarket);
                                break;
                            }
                        }
                    }


                }
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            // Ensure client is removed from the list
            server.removeClient(socketWrapper);
            try {
                socketWrapper.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



