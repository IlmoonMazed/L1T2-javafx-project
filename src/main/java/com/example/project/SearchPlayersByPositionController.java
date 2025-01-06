package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;

import java.util.List;

import static com.example.project.MainController.playerManagement;
import static com.example.project.NavigationUtil.*;

public class SearchPlayersByPositionController {

    @FXML
    ComboBox<String> positionComboBox;


    public void onSearchPlayersByPositionButtonClicked(ActionEvent actionEvent) {
        String position = positionComboBox.getValue();
        List<Player> players = playerManagement.searchByPosition(position);

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
