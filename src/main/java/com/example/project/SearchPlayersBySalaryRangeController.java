package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import java.util.List;

import static com.example.project.MainController.playerManagement;
import static com.example.project.NavigationUtil.*;

public class SearchPlayersBySalaryRangeController {

    @FXML
    private TextField minSalaryTextField;

    @FXML
    private TextField maxSalaryTextField;


    public void onSearchPlayersBySalaryRangeButtonClicked(ActionEvent actionEvent) {
        int minSalary = Integer.parseInt(minSalaryTextField.getText());
        int maxSalary = Integer.parseInt(maxSalaryTextField.getText());

        List<Player> players = playerManagement.searchBySalaryRange(minSalary, maxSalary);

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
