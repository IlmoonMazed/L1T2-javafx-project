package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Map;

import static com.example.project.NavigationUtil.*;

public class CountryWisePlayerCountController {

    @FXML
    private TableView<CountryPlayerCount> countryTable;

    @FXML
    private TableColumn<CountryPlayerCount, String> countryColumn;

    @FXML
    private TableColumn<CountryPlayerCount, Integer> playerCountColumn;

    private Map<String, Integer> countryWisePlayerCount;

    // set the map and update the UI
    public void setCountryWisePlayerCount(Map<String, Integer> countryWisePlayerCount) {
        this.countryWisePlayerCount = countryWisePlayerCount;
        updateTable();
    }

    // Populate the TableView with data from the map
    private void updateTable() {
        // Convert map entries to an ObservableList
        ObservableList<CountryPlayerCount> data = FXCollections.observableArrayList();

        for (Map.Entry<String, Integer> entry : countryWisePlayerCount.entrySet()) {
            data.add(new CountryPlayerCount(entry.getKey(), entry.getValue()));
        }

        // Set data to the TableView
        countryTable.setItems(data);
    }

    @FXML
    private void initialize() {
        // Bind the table columns with the properties of CountryPlayerCount
        countryColumn.setCellValueFactory(cellData -> cellData.getValue().countryProperty());
        playerCountColumn.setCellValueFactory(cellData -> cellData.getValue().playerCountProperty().asObject());
    }

    public void onBackButtonClicked(ActionEvent actionEvent) {
        navigateBack(actionEvent);
    }
}