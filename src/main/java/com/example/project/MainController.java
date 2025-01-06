package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.project.NavigationUtil.*;


public class MainController {

    public static PlayerManagement playerManagement = new PlayerManagement();
    public static Map<String, Image> images = new HashMap<>();
    private static boolean isLoaded = false;

    @FXML
    private void initialize() {
        if (!isLoaded) {
            try {
                boolean flag = playerManagement.loadPlayers();
                if (flag) {
                    isLoaded = true;
                } else {
                    showAlert("Load Failed", "Failed to load the player list!");
                }
                flag = loadImages();
                if (!flag) showAlert("Load Failed", "Failed to load the players' images!");
            } catch (IOException e) {
                showAlert("Error", "Error loading the player list!");
            }
        }
    }

    private boolean loadImages() throws IOException {
        for (Club club : playerManagement.clubList) {
            for (Player p : club.getClubPlayerList()) {
                Image tempImg = ImageLoader.getPlayerImage(p.getImage());
                images.put(p.getImage(), tempImg);
            }
        }
        return true;
    }

    @FXML
    private void onLogInButtonClick(ActionEvent event) {

        try {
            Client client = new Client();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            client.start(currentStage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void onSearchPlayersButtonClick(ActionEvent event) {
        // Open the "Search Players" scene
        FXMLLoader loader = switchScene(event, "/com/example/project/search-players.fxml", "Search Players");
    }

    @FXML
    private void onSearchClubsButtonClick(ActionEvent event) {
        // Open the "Search Clubs" scene
        FXMLLoader loader = switchScene(event, "/com/example/project/search-clubs.fxml", "Search Clubs");
    }

    @FXML
    private void onAddPlayerButtonClick(ActionEvent event) {
        // Open the "Add Player" scene
        FXMLLoader loader = switchScene(event, "/com/example/project/add-player.fxml", "Add Player");
    }

    @FXML
    private void onExitButtonClick(ActionEvent event) {
        try {
            playerManagement.savePlayers();
        } catch (IOException e) {
            System.err.println("Failed to save player list: " + e.getMessage());
        }
        System.exit(0); // Exit the application
    }
}