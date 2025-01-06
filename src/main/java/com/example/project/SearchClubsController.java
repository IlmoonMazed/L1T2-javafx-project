package com.example.project;

import javafx.event.ActionEvent;

import static com.example.project.NavigationUtil.navigateBack;
import static com.example.project.NavigationUtil.switchScene;

public class SearchClubsController {

    public void onMaximumSalaryButtonClicked(ActionEvent actionEvent) {
        switchScene(actionEvent, "/com/example/project/maximum-salary-of-a-club.fxml", "Maximum Salary");
    }

    public void onMaximumAgeButtonClicked(ActionEvent actionEvent) {
        switchScene(actionEvent, "/com/example/project/maximum-age-of-a-club.fxml", "Maximum Age");
    }

    public void onBackButtonClicked(ActionEvent event) {
        navigateBack(event);
    }

    public void onMaximumHeightButtonClicked(ActionEvent actionEvent) {
        switchScene(actionEvent, "/com/example/project/maximum-height-of-a-club.fxml", "Maximum Height");
    }

    public void onTotalYearlySalaryButtonClicked(ActionEvent actionEvent) {
        switchScene(actionEvent, "/com/example/project/total-yearly-salary-of-a-club.fxml", "Total Yearly Salary");
    }
}
