package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import java.util.List;

import static com.example.project.MainController.playerManagement;
import static com.example.project.NavigationUtil.*;

public class MaximumAgeOfAClubController {

    @FXML
    TextField clubNameTextField;

    public void onSearchMaximumAgeOfClubButtonClicked(ActionEvent actionEvent) {
        String clubName = clubNameTextField.getText().trim();
        List<Player> players = playerManagement.maxAgePlayer(clubName);

        try {
            FXMLLoader loader = switchScene(actionEvent, "/com/example/project/player-list-details.fxml", "Found Players");
            PlayerListDetailsController controller = loader.getController();
            controller.setPlayerList(players);
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }

    }

    public void onBackButtonClicked(ActionEvent actionEvent) {
        navigateBack(actionEvent);
    }
}
