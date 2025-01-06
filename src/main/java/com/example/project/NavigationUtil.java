package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class NavigationUtil {

    // Stack to maintain navigation history
    private static final Stack<String> historyStack = new Stack<>();

    public static FXMLLoader switchScene(ActionEvent event, String fxmlFilePath, String title) {
        try {
            String css = NavigationUtil.class.getResource("styles.css").toExternalForm();

            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(NavigationUtil.class.getResource(fxmlFilePath));
            Parent root = fxmlLoader.load();

            // Get the current stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create and set a new scene for the stage
            Scene scene = new Scene(root);

            scene.getStylesheets().add(css);

            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();


            // Push the current page onto the history stack
            historyStack.push(fxmlFilePath);

            return fxmlLoader;
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load scene: " + fxmlFilePath);
        }
        return null;
    }


    public static void stageScene(Stage stage, String fxmlFilePath, String title) {
        try {
            String css = NavigationUtil.class.getResource("styles.css").toExternalForm();

            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFilePath));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);

            scene.getStylesheets().add(css);

            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

            // Push the current page onto the history stack
            historyStack.push(fxmlFilePath);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load scene: " + fxmlFilePath);
        }
    }

    public static FXMLLoader popUpScene(Stage stage, String fxmlFilePath, String title) {
        try {
            String css = NavigationUtil.class.getResource("styles.css").toExternalForm();

            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFilePath));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);

            scene.getStylesheets().add(css);

            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

            return fxmlLoader;

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load scene: " + fxmlFilePath);
        }
        return null;
    }

    public static void navigateBack(ActionEvent event) {
        try {
            String css = NavigationUtil.class.getResource("styles.css").toExternalForm();
            // Get the current stage and its scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Check if the history stack is empty
            if (historyStack.isEmpty()) {
                // If the stack is empty, navigate to the main-view.fxml
                String mainViewPath = "/com/example/project/main-view.fxml";
                FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(mainViewPath));
                Parent root = loader.load();

                // Set the new scene
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } else {
                String previousPage = null;

                historyStack.pop();
                if (historyStack.isEmpty()) {
                    previousPage = "/com/example/project/main-view.fxml"; // Default to main-view if history is empty
                } else {
                    previousPage = historyStack.peek();
                }

                // Load the previous page
                FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(previousPage));
                Parent root = loader.load();

                // Replace the scene with the previous page
                Scene scene = new Scene(root);

                scene.getStylesheets().add(css);

                stage.setScene(scene);
                stage.setTitle(null);


            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to navigate to the previous scene.");
        }
    }

    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        // Customizing the style to make it look like a "success" dialog
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(NavigationUtil.class.getResource("styles.css").toExternalForm());
        dialogPane.getStyleClass().add("error");

        alert.showAndWait();
    }

    public static void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("Success");
        alert.setContentText(message);

        // Customizing the style to make it look like a "success" dialog
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(NavigationUtil.class.getResource("styles.css").toExternalForm());
        dialogPane.getStyleClass().add("success");

        alert.showAndWait();
    }

}
