package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static com.example.project.MainController.playerManagement;
import static com.example.project.NavigationUtil.navigateBack;

public class TotalYearlySalaryOfClubController {

    @FXML
    private TextField clubNameTextField;

    @FXML
    private Label totalSalaryLabel;

    public void onTotalYearlySalaryOfClubButtonClicked(ActionEvent actionEvent) {
        // Get the club name from the text field
        String clubName = clubNameTextField.getText().trim();

        // Calculate the total yearly salary for the given club
        long totalYearlySalary = playerManagement.totalYearlySalary(clubName);

        // Display the result in the label
        if (totalYearlySalary > 0) {
            totalSalaryLabel.setText("Total Yearly Salary: " + totalYearlySalary + " INR");
        } else {
            totalSalaryLabel.setText("No data found for club: " + clubName);
        }
    }

    public void onBackButtonClicked(ActionEvent actionEvent) {
        navigateBack(actionEvent);
    }
}
