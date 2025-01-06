package com.example.project;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.project.MainController.playerManagement;
import static com.example.project.NavigationUtil.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stageScene(stage, "main-view.fxml", "Main View");
        stage.setOnCloseRequest(event -> {
            try {
                playerManagement.savePlayers();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}