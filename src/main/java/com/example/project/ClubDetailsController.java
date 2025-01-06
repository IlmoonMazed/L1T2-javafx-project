package com.example.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import static com.example.project.NavigationUtil.*;

public class ClubDetailsController {

    @FXML
    Label clubDetailsLabel;

    @FXML
    private PieChart pieChart;

    public void setClub(Club clientClub) {

        String clubName = clientClub.getName();
        int numOfPlayers = clientClub.getNumberOfPlayers();
        long totalSalary = clientClub.totalWeeklySalary();
        int numOfBatsMan = 0, numOfBowler = 0, numOfAllrounder = 0, numOfWicketkeeper = 0;

        for (Player player : clientClub.getClubPlayerList()) {
            if (player.getPosition().equalsIgnoreCase("batsman")) numOfBatsMan++;
            else if (player.getPosition().equalsIgnoreCase("bowler")) numOfBowler++;
            else if (player.getPosition().equalsIgnoreCase("allrounder")) numOfAllrounder++;
            else if (player.getPosition().equalsIgnoreCase("wicketkeeper")) numOfWicketkeeper++;
        }


        if (clientClub != null) {
            // Build the player details string based on available fields
            StringBuilder details = new StringBuilder();
            details.append(String.format("Name: %s\n", clubName));
            details.append(String.format("Number Of Players: %d\n", numOfPlayers));
            details.append(String.format("Total Weekly Salary: %,d\n", totalSalary));

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("Batsman (" + numOfBatsMan + ")", numOfBatsMan), new PieChart.Data("Bowler (" + numOfBowler + ")", numOfBowler), new PieChart.Data("Allrounder (" + numOfAllrounder + ")", numOfAllrounder), new PieChart.Data("Wicket Keeper (" + numOfWicketkeeper + ")", numOfWicketkeeper));

            pieChart.setData(pieChartData);
            pieChart.setTitle("Player Distribution");

            // Set the final formatted text for the label
            clubDetailsLabel.setText(details.toString());
        } else {
            clubDetailsLabel.setText("No Details found!");
        }
    }

    public void onBackButtonClicked(ActionEvent actionEvent) {
        navigateBack(actionEvent);
    }
}
