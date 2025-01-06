package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;

import static com.example.project.Client.*;
import static com.example.project.NavigationUtil.*;

public class ClientHomePageController {
    public void onSeePlayersButtonClicked(ActionEvent actionEvent) {
        List<Player> players = clientClub.getClubPlayerList();
        FXMLLoader loader = switchScene(actionEvent, "/com/example/project/dynamic-player-list-details.fxml", "Found Players");
        DynamicPlayerListDetailsController controller = loader.getController();
        controller.setPlayerList(players);

    }

    public void onClubDetailsButtonClicked(ActionEvent actionEvent) {
        FXMLLoader loader = switchScene(actionEvent, "/com/example/project/club-details.fxml", "Club Details");
        ClubDetailsController controller = loader.getController();
        controller.setClub(clientClub);
    }

    public void onBuyPlayersButtonClicked(ActionEvent actionEvent) {
        FXMLLoader loader = switchScene(actionEvent, "/com/example/project/player-market.fxml", "Player Market");
        PlayerMarketController controller = loader.getController();
        controller.setPlayerMarket(playerMarket);
    }

    public void onSellPlayersButtonClicked(ActionEvent actionEvent) {
        List<Player> players = clientClub.getClubPlayerList();
        FXMLLoader loader = switchScene(actionEvent, "/com/example/project/sell-player.fxml", "Sell Players");
        SellPlayerController controller = loader.getController();
        controller.setPlayerList(players);
    }

    public void onLogOutButtonClicked(ActionEvent actionEvent) {
        try {
            // Stop listening for messages if it's running
            if (socketWrapper != null) {
                socketWrapper.stopListening();  // Stop the listening thread
                socketWrapper.closeConnection();  // Close the existing connection (streams and socket)
            }

            navigateBack(actionEvent);

            // Reinitialize the socket (create a new connection)
            socketWrapper = new SocketWrapper("127.0.0.1", 33333); // Replace with the correct host and port

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
