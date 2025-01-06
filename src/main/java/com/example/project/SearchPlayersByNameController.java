package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import static com.example.project.MainController.images;
import static com.example.project.MainController.playerManagement;
import static com.example.project.NavigationUtil.*;

public class SearchPlayersByNameController {

    @FXML
    TextField playerNameTextField;


    public void onSearchPlayerNameButtonClicked(ActionEvent actionEvent) {
        String name = playerNameTextField.getText().trim();
        Player player = playerManagement.searchByName(name);


        try {
            Image playerImage;
            if (player == null || player.getImage().equalsIgnoreCase("default-headshot"))
                playerImage = new Image("file:src/main/resources/com/example/project/images/default.png");
            else playerImage = images.get(player.getImage());

            FXMLLoader loader = switchScene(actionEvent, "/com/example/project/player-details.fxml", "Player Details");
            PlayerDetailsController controller = loader.getController();
            controller.setPlayer(player, playerImage);
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    public void onBackButtonClicked(ActionEvent actionEvent) {
        navigateBack(actionEvent);
    }

}
