package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.util.Map;

import static com.example.project.MainController.playerManagement;
import static com.example.project.NavigationUtil.navigateBack;
import static com.example.project.NavigationUtil.switchScene;


public class SearchPlayersController {

    @FXML
    private void initialize() {
    }


    public void onBackButtonClicked(ActionEvent event) {
        navigateBack(event);
    }

    public void onSearchByNameButtonClicked(ActionEvent actionEvent) {
        switchScene(actionEvent, "/com/example/project/search-players-by-name.fxml", "Search Players By Name");
    }

    public void onSearchByClubAndCountryButtonClicked(ActionEvent actionEvent) {
        switchScene(actionEvent, "/com/example/project/search-players-by-club-and-country.fxml", "Search Players By Club and Country");
    }

    public void onSearchByPositionButtonClicked(ActionEvent actionEvent) {
        switchScene(actionEvent, "/com/example/project/search-players-by-position.fxml", "Search Players By Position");
    }

    public void onSearchBySalaryRangeButtonClicked(ActionEvent actionEvent) {
        switchScene(actionEvent, "/com/example/project/search-players-by-salary-range.fxml", "Search Players By Salary Range");
    }

    public void onCountryWisePlayerCountButtonClicked(ActionEvent actionEvent) {
        Map<String, Integer> countryWisePlayerCount = playerManagement.getCountryWisePlayerCount(); // Obtain the map
        FXMLLoader loader = switchScene(actionEvent, "/com/example/project/country-wise-player-count.fxml", "Country Wise Player Count");

        if (loader != null) {
            CountryWisePlayerCountController controller = loader.getController();
            if (controller != null) {
                controller.setCountryWisePlayerCount(countryWisePlayerCount);
            }
        }
    }

}