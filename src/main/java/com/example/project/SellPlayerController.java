package com.example.project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.project.Client.*;
import static com.example.project.NavigationUtil.navigateBack;
import static com.example.project.NavigationUtil.popUpScene;

public class SellPlayerController {

    @FXML
    private TableView<Player> playerTable; // Table to show player data
    @FXML
    private TableColumn<Player, String> playerColumn; // Player name column
    @FXML
    private TableColumn<Player, String> clubColumn; // Club column
    @FXML
    private TableColumn<Player, String> positionColumn; // Position column
    @FXML
    private TableColumn<Player, Void> actionColumn; // Action column for buttons


    private List<Player> players;

    public void setPlayerList(List<Player> playerList) {
        this.players = playerList;
        updatePlayerDetails();
    }

    public void initialize() {
        startAutoRefresh();
    }

    private void startAutoRefresh() {
        new Thread(() -> {
            List<Player> previousPlayers = new ArrayList<>();
            while (true) {
                synchronized (clientClub.getClubPlayerList()) {
                    if (!clientClub.getClubPlayerList().equals(previousPlayers)) {
                        previousPlayers = new ArrayList<>(clientClub.getClubPlayerList());
                        Platform.runLater(this::updatePlayerDetails);
                    }
                }
                try {
                    Thread.sleep(1000); // Check for updates every second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private void updatePlayerDetails() {
        // Clear previous entries
        playerTable.getItems().clear();

        if (players != null) {
            for (Player player : players) {
                playerTable.getItems().add(player);
            }
        }

        // Check if the playerTable is empty
        if (playerTable.getItems().isEmpty()) {
            // Create a label to show when no players are available
            Label noPlayersLabel = new Label("No players found!");
            VBox parentContainer = (VBox) playerTable.getParent();
            if (!parentContainer.getChildren().contains(noPlayersLabel)) {
                parentContainer.getChildren().add(noPlayersLabel);
            }
        } else {
            VBox parentContainer = (VBox) playerTable.getParent();
            parentContainer.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("No players found!"));

            // Populate the table with player details
            playerColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            clubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
            positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

            // new one
            actionColumn.setCellFactory(new Callback<>() {
                @Override
                public javafx.scene.control.TableCell<Player, Void> call(TableColumn<Player, Void> param) {
                    return new javafx.scene.control.TableCell<>() {
                        private final Button detailsButton = new Button("Details");
                        private final Button sellOrCancelButton = new Button("");

                        {
                            // Details button action
                            detailsButton.setOnAction(event -> onDetailsButtonClicked(getTableRow().getItem(), event));

                            // Sell/Cancel button action
                            sellOrCancelButton.setOnAction(event -> onSellOrCancelButtonClicked(getTableRow().getItem(), event, sellOrCancelButton));
                        }

                        @Override
                        protected void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                // Get the current player from the row
                                Player player = getTableRow().getItem();

                                // Check if the player is in the process of being sold
                                if (!sellStates.containsKey(player.getName().toUpperCase()))
                                    sellStates.put(player.getName().toUpperCase(), false);
                                boolean isBeingSold = sellStates.get(player.getName().toUpperCase());

                                // Toggle the button label based on the sell status
                                if (isBeingSold) {
                                    sellOrCancelButton.setText("Cancel Sell");
                                } else {
                                    sellOrCancelButton.setText("Sell");
                                }

                                // Set the action buttons
                                HBox actionBox = new HBox(10, detailsButton, sellOrCancelButton);
                                setGraphic(actionBox);
                            }
                        }
                    };
                }
            });
        }
    }


    private void onDetailsButtonClicked(Player player, ActionEvent actionEvent) {
        // Create a new stage for the popup window
        Stage newStage = new Stage();
        FXMLLoader loader = popUpScene(newStage, "player-details.fxml", "Player Details");


        Image playerImage;
        playerImage = clubPlayerImages.get(player.getImage());

        // Get the controller and set the player data
        PlayerDetailsController controller = loader.getController();
        controller.setPlayer(player, playerImage);
        controller.setBackButton(false);
    }

    // Method to handle Sell/Cancel button clicks
    private void onSellOrCancelButtonClicked(Player player, ActionEvent event, Button sellOrCancelButton) {
        if (player == null) return;

        if (sellStates.get(player.getName().toUpperCase())) {
            // If the player is being sold, cancel the sell
            sellStates.put(player.getName().toUpperCase(), false); // Mark as not selling anymore
            sellOrCancelButton.setText("Sell");  // Change button text back to "Sell"
        } else {
            // If the player is not being sold, initiate sell
            sellStates.put(player.getName().toUpperCase(), true); // Mark as being sold
            sellOrCancelButton.setText("Cancel Sell");  // Change button text to "Cancel Sell"
        }

        // Add your additional logic here to handle the sell request, e.g., sending a message to the server
        try {
            String msg = sellStates.get(player.getName().toUpperCase()) ? "sell," + player.getName() : "cancel_sell," + player.getName();
            socketWrapper.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onBackButtonClicked(ActionEvent actionEvent) {
        navigateBack(actionEvent);
    }
}
