package com.example.prajil;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.util.Objects;

public class GameOver implements Serializable {
    static Stage stage;
    int highestScore;
    transient Pane nodesToFadeOutContainer;
    transient FadeTransition fadeOut;
    transient Label currentScoreLabel;
    transient Label bestScoreLabel;

    GameOver(int score) throws IOException {
        highestScore=loadHighestScoreFromFile();
        this.bestScoreLabel = new Label("  BEST:\n     "+ highestScore);
        this.currentScoreLabel= new Label("SCORE:\n    "+ score);
        updateIfHighScore(score);
        nodesToFadeOutContainer = new Pane();
        FadeOut();
    }


    public void FadeOut() {
        Pane root = (Pane) stage.getScene().getRoot();
        for (Node existingNode : root.getChildren()) {
            fadeOut = new FadeTransition(Duration.millis(100), existingNode);
            // Set the initial and final opacity values
            fadeOut.setFromValue(1.0);
            if (existingNode instanceof Line) {
                fadeOut.setToValue(0.4);
                // Start the fade-out animation for each existing node
            } else {
                fadeOut.setToValue(0.6);
            }
            fadeOut.play();
            if (existingNode instanceof Button) {
                Button pause = (Button) existingNode;
                if ("pauseButton".equals(pause.getId())) {
                    existingNode.setVisible(false);
                    System.out.println("tru");
                }
            }
            if (existingNode instanceof TextField) {
                TextField score= (TextField) existingNode;
                if ("score".equals(score.getId())) {
                    existingNode.setVisible(false);
                    System.out.println("false");
                }
            }
        }

        fadeOut.setOnFinished(e -> {
            addGameOverElements();
        });
    }


    public void addGameOverElements(){
        Pane root = (Pane) stage.getScene().getRoot();
        // Label
        Label gameOverLabel = new Label("GAME OVER!!");
        gameOverLabel.setAlignment(javafx.geometry.Pos.CENTER);
        gameOverLabel.setLayoutX(85.0);
        gameOverLabel.setLayoutY(78.0);
        gameOverLabel.setPrefHeight(63.0);
        gameOverLabel.setPrefWidth(270.0);
        gameOverLabel.setFont(new javafx.scene.text.Font("System Bold", 41.0));

        // Restart Button
        Button restartButton = new Button("Restart");
        restartButton.setLayoutX(232.0);
        restartButton.setLayoutY(387.0);
        restartButton.setPrefHeight(50.0);
        restartButton.setPrefWidth(58.0);
        restartButton.setContentDisplay(javafx.scene.control.ContentDisplay.GRAPHIC_ONLY);

//        // ImageView for Restart Button
        ImageView restartImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("restart.png"))));
        restartImageView.setFitHeight(42.0);
        restartImageView.setFitWidth(32.0);
        restartImageView.setNodeOrientation(NodeOrientation.INHERIT);
        restartImageView.setPickOnBounds(true);
        restartImageView.setPreserveRatio(true);
        restartButton.setGraphic(restartImageView);
//
        restartButton.setOnAction(e -> {
            try {
                switchToPlay();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }); // Assuming switchToPlay() is your action handler


        // Home Button
        Button homeButton = new Button("Home");
        homeButton.setLayoutX(142.0);
        homeButton.setLayoutY(387.0);
        homeButton.setPrefHeight(50.0);
        homeButton.setPrefWidth(58.0);
        homeButton.setContentDisplay(javafx.scene.control.ContentDisplay.GRAPHIC_ONLY);

        // ImageView for Home Button
        ImageView homeImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("home.png"))));
        homeImageView.setFitHeight(47.0);
        homeImageView.setFitWidth(42.0);
        homeImageView.setNodeOrientation(NodeOrientation.INHERIT);
        homeImageView.setPickOnBounds(true);
        homeImageView.setPreserveRatio(true);
        homeButton.setGraphic(homeImageView);

        homeButton.setOnAction(e -> {
            try {
                switchToHome();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }); // Assuming switchToHome() is your action handler


        // AnchorPane
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setLayoutX(107.0);
        anchorPane.setLayoutY(166.0);
        anchorPane.setPrefHeight(177.0);
        anchorPane.setPrefWidth(227.0);
        anchorPane.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        // Score Label
        currentScoreLabel.setLayoutX(80.0);
        currentScoreLabel.setLayoutY(14.0);
        currentScoreLabel.setPrefHeight(63.0);
        currentScoreLabel.setPrefWidth(67.0);
        currentScoreLabel.setTextFill(javafx.scene.paint.Color.valueOf("#181717"));
        currentScoreLabel.setTextAlignment(javafx.scene.text.TextAlignment.JUSTIFY);

        Font scoreFont = new Font(20.0);
        currentScoreLabel.setFont(scoreFont);

        // Best Score Label

        bestScoreLabel.setLayoutX(74.0);
        bestScoreLabel.setLayoutY(77.0);
        bestScoreLabel.setPrefHeight(82.0);
        bestScoreLabel.setPrefWidth(78.0);
        bestScoreLabel.setTextFill(javafx.scene.paint.Color.valueOf("#201f1f"));
        bestScoreLabel.setTextAlignment(javafx.scene.text.TextAlignment.JUSTIFY);

        Font bestScoreFont = new Font(20.0);
        bestScoreLabel.setFont(bestScoreFont);

        // Add labels to AnchorPane
        anchorPane.getChildren().addAll(currentScoreLabel, bestScoreLabel);
        root.getChildren().addAll(restartButton,homeButton,gameOverLabel, anchorPane);


    }




    private void switchToPlay() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("playScene.fxml")));
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void switchToHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homePage.fxml")));
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void updateIfHighScore(int score){
        if (score> highestScore){
            highestScore=score;
            bestScoreLabel.setText("  BEST:\n     "+ highestScore);
            saveHighestScoreToFile();
        }
    }

    public static void setStage(Stage stageAsParameter){
        stage=stageAsParameter;
    }

    private void saveHighestScoreToFile() { // serialize
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("highestScore.txt"))) {
            outputStream.writeObject(this);
            System.out.println("High score saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int loadHighestScoreFromFile() {  //deserialize
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("highestScore.txt"))) {
            GameOver loadedGameOver = (GameOver) inputStream.readObject();
            this.highestScore = loadedGameOver.highestScore;
//            bestScoreLabel.setText("  BEST:\n     " + highestScore);
            System.out.println("Highest score loaded from file");
        } catch (IOException | ClassNotFoundException e) {
            // Handle the case when the file doesn't exist or cannot be read
            System.out.println("Could not load highest score from file");
        }
        return this.highestScore;
    }

}
