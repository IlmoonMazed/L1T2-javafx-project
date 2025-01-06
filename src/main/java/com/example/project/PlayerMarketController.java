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

public class PlayerMarketController {

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

    private List<Player> playerMarket;

    public void setPlayerMarket(List<Player> playerMarket) {
        this.playerMarket = playerMarket;
        updatePlayerDetails();
    }

    public void initialize() {
        startAutoRefresh();
    }

    private void startAutoRefresh() {
        new Thread(() -> {
            List<Player> previousMarket = new ArrayList<>();
            while (true) {
                synchronized (Client.playerMarket) {
                    if (!Client.playerMarket.equals(previousMarket)) {
                        previousMarket = new ArrayList<>(Client.playerMarket);
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

        if (playerMarket != null) {
            for (Player player : playerMarket) {
                if (!player.getClub().equalsIgnoreCase(clientClub.getName())) {
                    playerTable.getItems().add(player);
                }
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

            // Setting custom cell factory for actions column (Buy and Details buttons)
            actionColumn.setCellFactory(new Callback<TableColumn<Player, Void>, javafx.scene.control.TableCell<Player, Void>>() {
                @Override
                public javafx.scene.control.TableCell<Player, Void> call(TableColumn<Player, Void> param) {
                    return new javafx.scene.control.TableCell<Player, Void>() {
                        private final Button detailsButton = new Button("Details");
                        private final Button buyButton = new Button("Buy");

                        {
                            detailsButton.setOnAction(event -> onDetailsButtonClicked(getTableRow().getItem(), event));
                            buyButton.setOnAction(event -> onBuyButtonClicked(getTableRow().getItem(), event));
                        }

                        @Override
                        protected void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                HBox actionBox = new HBox(10, detailsButton, buyButton);
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
        playerImage = marketPlayerImages.get(player.getImage());

        // Get the controller and set the player data
        PlayerDetailsController controller = loader.getController();
        controller.setPlayer(player, playerImage);
        controller.setBackButton(false);
    }


    private void onBuyButtonClicked(Player player, ActionEvent actionEvent) {
        // Add your logic for handling the "Buy" button click here
        try {
            player.setClub(clientClub.getName());
            clientClub.addPlayerToClub(player);
            Client.loadClubPlayerImages();
            String msg = "bought," + player.getName();
            socketWrapper.write(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onBackButtonClicked(ActionEvent actionEvent) {
        navigateBack(actionEvent);
    }
}
