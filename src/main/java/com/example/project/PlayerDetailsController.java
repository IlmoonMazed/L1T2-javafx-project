package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static com.example.project.NavigationUtil.navigateBack;

public class PlayerDetailsController {

    @FXML
    private Label playerDetailsLabel;

    @FXML
    private StackPane imageContainer; // The container for the image

    @FXML
    private ImageView playerImageView; // For displaying the player image

    private boolean isBackButton = true;

    public void setBackButton(boolean backButton) {
        this.isBackButton = backButton;
    }

    public void setPlayer(Player player, Image playerImage) {
        if (player != null) {

            // Load the photocard image
            Image photocardImage = new Image("file:src/main/resources/com/example/project/images/photocard.png");

            // Create an ImageView for the photocard background
            ImageView photocardImageView = new ImageView(photocardImage);
            photocardImageView.setPreserveRatio(true);
            photocardImageView.setFitWidth(250); // Set desired width for the photocard
            photocardImageView.setFitHeight(280); // Set desired height for the photocard

            // Set the player image to the playerImageView
            playerImageView.setImage(playerImage);
            playerImageView.setPreserveRatio(true);
            playerImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 4, 4);");

            // Create a rounded rectangle to clip the player image for rounded corners
            double clipRadius = 25.0;  // Match the radius for rounded corners
            Rectangle clip = new Rectangle(300, 300); // Define the size of the ImageView
            clip.setArcHeight(clipRadius);  // Rounded corners height
            clip.setArcWidth(clipRadius);   // Rounded corners width
            playerImageView.setClip(clip);  // Apply the clip to the ImageView

            // Clear the StackPane children to manage order
            imageContainer.getChildren().clear();

            // Add the photocard first
            imageContainer.getChildren().add(photocardImageView);

            // Add the player image on top of the photocard
            imageContainer.getChildren().add(playerImageView);

            // Build the player details string
            StringBuilder details = new StringBuilder();
            details.append(String.format("Name: %s\n", player.getName()));
            details.append(String.format("Age: %d\n", player.getAge()));
            details.append(String.format("Club: %s\n", player.getClub()));
            details.append(String.format("Country: %s\n", player.getCountry()));
            details.append(String.format("Position: %s\n", player.getPosition()));
            details.append(String.format("Salary: %,d INR\n", player.getWeeklySalary()));
            details.append(String.format("Height: %.2f m\n", player.getHeight()));

            if (player.getJerseyNumber() >= 0) {
                details.append(String.format("Jersey Number: %d\n", player.getJerseyNumber()));
            }

            // Set the final formatted text for the label
            playerDetailsLabel.setText(details.toString());

        } else {
            playerDetailsLabel.setText("No player found!");
        }
    }


    public void onBackButtonClicked(ActionEvent actionEvent) {
        if (isBackButton) {
            navigateBack(actionEvent);
        } else {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close(); // Close the current window
        }
    }
}
