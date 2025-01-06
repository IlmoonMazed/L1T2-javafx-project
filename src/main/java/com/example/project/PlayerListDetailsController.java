package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

import static com.example.project.MainController.images;
import static com.example.project.NavigationUtil.navigateBack;

public class PlayerListDetailsController {

    // Load photocard image as a class-level variable
    private final Image photocardImage = new Image("file:src/main/resources/com/example/project/images/photocard.png");
    @FXML
    private ListView<Player> playerDetailsListView;
    private List<Player> playerList;

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
        updatePlayerDetails();
    }

    private void updatePlayerDetails() {
        if (playerList == null || playerList.isEmpty()) {
            playerDetailsListView.getItems().add(null);
        } else {
            playerDetailsListView.getItems().addAll(playerList);
        }

        playerDetailsListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Player player, boolean empty) {
                super.updateItem(player, empty);
                if (empty || player == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(10);
                    hBox.setStyle("-fx-padding: 10; -fx-alignment: center-left;");
                    hBox.setSpacing(20);

                    VBox vBox = new VBox(5);
                    vBox.setStyle("-fx-padding: 5;");
                    VBox.setVgrow(vBox, Priority.ALWAYS);

                    // Add player details text
                    Text name = new Text("Name: " + player.getName());
                    Text age = new Text("Age: " + player.getAge());
                    Text club = new Text("Club: " + player.getClub());
                    Text country = new Text("Country: " + player.getCountry());
                    Text position = new Text("Position: " + player.getPosition());
                    Text salary = new Text("Salary: " + String.format("%,d INR", player.getWeeklySalary()));
                    Text height = new Text(String.format("Height: %.2f m", player.getHeight()));

                    vBox.getChildren().addAll(name, age, club, country, position, salary, height);

                    // Load and display the player's image with a photocard background
                    StackPane imageStack = new StackPane();
                    imageStack.setPrefSize(150, 150);

                    // Photocard background
                    ImageView photocardView = new ImageView(photocardImage);
                    photocardView.setFitWidth(120);
                    photocardView.setFitHeight(150);
                    photocardView.setPreserveRatio(false);

                    // Player image
                    ImageView playerImageView = new ImageView(images.get(player.getImage()));
                    playerImageView.setFitWidth(130);
                    playerImageView.setFitHeight(130);
                    playerImageView.setPreserveRatio(true);

                    // Add photocard and player image to the StackPane
                    imageStack.getChildren().addAll(photocardView, playerImageView);

                    // Push the VBox content to the left
                    HBox.setHgrow(vBox, Priority.ALWAYS);
                    hBox.getChildren().addAll(vBox, imageStack);

                    setGraphic(hBox);
                }
            }
        });
    }

    public void onBackButtonClicked(ActionEvent actionEvent) {
        navigateBack(actionEvent);
    }
}
