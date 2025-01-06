package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

import static com.example.project.NavigationUtil.switchScene;

public class ClientLogInPageController {
    @FXML
    private TextField clubName;

    @FXML
    private PasswordField clubPassword;

    @FXML
    private Button logInButton;

    public void logInButtonClicked(ActionEvent actionEvent) {
        String club = clubName.getText().trim();
        String password = clubPassword.getText();
        String logInRequest = "login," + club + "," + password;
        try {
            // Send the club name to the server
            Client.socketWrapper.write(logInRequest);

            // Receive Club object
            Object obj = Client.socketWrapper.read();
            if (obj != null && obj instanceof Club) {
                Client.clientClub = (Club) obj;
                Client.loadClubPlayerImages();
                // Switch to home page if Club is received
                switchScene(actionEvent, "client-home-page.fxml", "Home Page");

                // Receive player market list
                obj = Client.socketWrapper.read();
                if (obj instanceof List<?>) {
                    List<?> list = (List<?>) obj;
                    if (!list.isEmpty() && list.get(0) instanceof Player) {
                        synchronized (Client.playerMarket) {
                            Client.playerMarket.clear();
                            Client.playerMarket.addAll((List<Player>) list);
                            Client.marketMap();
                        }
                    }
                }
                Client.readyForListening();     // now it keeps listening from the server in a separate thread
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
