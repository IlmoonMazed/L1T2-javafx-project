package com.example.project;

import javafx.scene.image.Image;

public class ImageLoader {
    private static final String BASE_URL = "https://documents.iplt20.com/ipl/IPLHeadshot2024/"; // Base URL for images
    private static final String DEFAULT_IMAGE_URL = "file:src/main/resources/com/example/project/images/default.png"; // Default image URL

    public static Image getPlayerImage(String image) {
        // Validate the image argument
        if (image == null || image.isEmpty() || image.equalsIgnoreCase("default-headshot")) {
            return new Image(DEFAULT_IMAGE_URL, true);
        }

        // Construct the full URL
        String imageUrl = BASE_URL + image + ".png";

        try {
            // Attempt to load the image from the URL
            return new Image(imageUrl, true);
        } catch (Exception e) {
            // If loading fails, fall back to the default image
            return new Image(DEFAULT_IMAGE_URL, true);
        }
    }
}
