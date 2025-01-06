package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import static com.example.project.MainController.playerManagement;
import static com.example.project.NavigationUtil.*;

public class AddPlayerController {

    @FXML
    TextField nameTextField;

    @FXML
    TextField countryTextField;

    @FXML
    TextField ageTextField;
    @FXML
    TextField heightTextField;

    @FXML
    TextField clubTextField;

    @FXML
    ComboBox<String> positionComboBox;

    @FXML
    TextField jerseyNumberTextField;

    @FXML
    TextField weeklySalaryTextField;



    public void onCheckValidityButtonClicked(ActionEvent actionEvent) {
        String playerName = nameTextField.getText().trim();
        if(playerManagement.playerAlreadyExists(playerName)) {
            showAlert("Invalid", "This player already exists!\nPlease try different Player");
        }
        else showSuccess("Valid", "This player is valid to be added.");
    }


    public void onBackButtonClicked(ActionEvent actionEvent) {
        navigateBack(actionEvent);
    }

    public void onAddPlayerButtonClicked(ActionEvent actionEvent) {

        if(areFieldsValid()) {
            String name = nameTextField.getText().trim();
            String country = countryTextField.getText().trim();
            int age = Integer.parseInt(ageTextField.getText().trim());
            double height = Double.parseDouble(heightTextField.getText().trim());
            String club = clubTextField.getText().trim();
            String position = positionComboBox.getValue();
            String jerseyNum = jerseyNumberTextField.getText().trim();
            int jerseyNumber;
            if(jerseyNum.trim().isEmpty()) jerseyNumber = -1;
            else jerseyNumber = Integer.parseInt(jerseyNum);
            int weeklySalary = Integer.parseInt(weeklySalaryTextField.getText().trim());
            String image = "default-headshot";

            Player newPlayer = new Player(name, country, age, height, club, position, jerseyNumber, weeklySalary, image);
            boolean success =  playerManagement.addPlayer(newPlayer);

            if(success) showSuccess("Success", "Player added successfully!");

            navigateBack(actionEvent);

        }
    }

    private boolean areFieldsValid() {
        if(nameTextField.getText().trim().isEmpty()) {
            showAlert("Invalid", "Please enter a valid name.\nName is required");
            return false;
        }
        else if(playerManagement.playerAlreadyExists(nameTextField.getText().trim())) {
            showAlert("Invalid", "This player already exists!\nPlease try different Player");
        }

        else if(countryTextField.getText().trim().isEmpty()) {
            showAlert("Invalid", "Please enter a valid country.\nCountry is required");
            return false;
        }

        else if(ageTextField.getText().trim().isEmpty() || !canParseInt(ageTextField.getText().trim())) {
            showAlert("Invalid", "Please enter an integer age.\nAge is required");
            return false;
        }

        else if(heightTextField.getText().trim().isEmpty() || !canParseDouble(heightTextField.getText().trim())) {
            showAlert("Invalid", "Please enter a double height.\nHeight is required");
            return false;
        }

        else if(clubTextField.getText().trim().isEmpty()) {
            showAlert("Invalid", "Please enter a club.\nClub is required");
            return false;
        }

        else if(positionComboBox.getValue() == null) {
            showAlert("Invalid", "Please select a position.\nPosition is required");
            return false;
        }

        else if(weeklySalaryTextField.getText().trim().isEmpty() || !canParseInt(weeklySalaryTextField.getText().trim())) {
            showAlert("Invalid", "Please enter a weekly salary.\nSalary is required");
            return false;
        }

        return true;
    }

    private boolean canParseInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }

    private boolean canParseDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }
}
