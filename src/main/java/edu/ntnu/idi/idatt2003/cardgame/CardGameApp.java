package edu.ntnu.idi.idatt2003.cardgame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CardGameApp extends Application {
    private final DeckOfCards deck = new DeckOfCards();
    private HandOfCards currentHand = null;

    private FlowPane cardDisplayPane;
    private TextField sumField;
    private TextField heartsField;
    private TextField flushField;
    private TextField queenField;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Card Game – JavaFX");
        primaryStage.setScene(buildScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Scene buildScene() {
        // --- Card display pane (left / top area) ---
        cardDisplayPane = new FlowPane();
        cardDisplayPane.setPrefSize(420, 260);
        cardDisplayPane.setMinSize(420, 260);
        cardDisplayPane.setPadding(new Insets(12));
        cardDisplayPane.setHgap(8);
        cardDisplayPane.setVgap(8);
        cardDisplayPane.setAlignment(Pos.TOP_LEFT);
        cardDisplayPane.setStyle(
                "-fx-border-color: #787878; -fx-border-width: 1.5; "
                + "-fx-background-color: #1b581b; -fx-border-radius: 4;");

        Label placeholder = new Label("Press \"Deal hand\" to start");
        placeholder.setTextFill(Color.LIGHTGRAY);
        placeholder.setFont(Font.font("Arial", 14));
        cardDisplayPane.getChildren().add(placeholder);

        Button dealButton  = new Button("Deal hand");
        Button checkButton = new Button("Check hand");
        dealButton.setPrefWidth(120);
        checkButton.setPrefWidth(120);
        dealButton.setStyle("-fx-font-size: 13;");
        checkButton.setStyle("-fx-font-size: 13;");

        dealButton.setOnAction(e -> handleDealHand());
        checkButton.setOnAction(e -> handleCheckHand());

        VBox buttonBox = new VBox(14, dealButton, checkButton);
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.setPadding(new Insets(20, 10, 0, 10));

        HBox topArea = new HBox(16, cardDisplayPane, buttonBox);
        topArea.setAlignment(Pos.TOP_LEFT);

        sumField    = makeReadOnlyField(200);
        heartsField = makeReadOnlyField(200);
        flushField  = makeReadOnlyField(80);
        queenField  = makeReadOnlyField(80);

        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(10);
        infoGrid.setVgap(8);
        infoGrid.setPadding(new Insets(10, 0, 0, 0));

        infoGrid.add(new Label("Sum of the faces:"), 0, 0);
        infoGrid.add(sumField, 1, 0);
        infoGrid.add(new Label("Cards of hearts:"), 2, 0);
        infoGrid.add(heartsField, 3, 0);

        infoGrid.add(new Label("Flush:"), 0, 1);
        infoGrid.add(flushField, 1, 1);
        infoGrid.add(new Label("Queen of spades:"), 2, 1);
        infoGrid.add(queenField, 3, 1);

        statusLabel = new Label("Ready.");
        statusLabel.setTextFill(Color.DARKGRAY);
        statusLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        VBox root = new VBox(14, topArea, infoGrid, statusLabel);
        root.setPadding(new Insets(16));
        root.setAlignment(Pos.TOP_LEFT);

        return new Scene(root, 620, 400);
    }


    private void handleDealHand() {
        currentHand = deck.dealHand(5);
        renderCards();
        clearAnalysisFields();
        statusLabel.setText("Hand dealt. Press \"Check hand\" to analyse.");
    }

    private void handleCheckHand() {
        if (currentHand == null) {
            statusLabel.setText("No hand dealt yet – press \"Deal hand\" first.");
            return;
        }

        sumField.setText(String.valueOf(currentHand.sumOfFaces()));
        heartsField.setText(currentHand.heartsAsString());
        flushField.setText(currentHand.isFlush() ? "Yes" : "No");
        queenField.setText(currentHand.hasQueenOfSpades() ? "Yes" : "No");

        statusLabel.setText("Analysis complete.");
    }

    private void renderCards() {
        cardDisplayPane.getChildren().clear();
        for (PlayingCard card : currentHand.getCards()) {
            Label tile = new Label(card.toString());
            tile.setPrefSize(64, 90);
            tile.setAlignment(Pos.CENTER);
            tile.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            tile.setStyle(cardStyle(card.getSuit()));
            cardDisplayPane.getChildren().add(tile);
        }
    }

    private String cardStyle(char suit) {
        String textColor = (suit == 'H' || suit == 'D') ? "#cc0000" : "#111111";
        return "-fx-background-color: white; "
                + "-fx-border-color: #555; -fx-border-width: 1.5; "
                + "-fx-border-radius: 6; -fx-background-radius: 6; "
                + "-fx-text-fill: " + textColor + ";";
    }

    private void clearAnalysisFields() {
        sumField.clear();
        heartsField.clear();
        flushField.clear();
        queenField.clear();
    }

    private TextField makeReadOnlyField(int width) {
        TextField tf = new TextField();
        tf.setEditable(false);
        tf.setPrefWidth(width);
        tf.setStyle("-fx-background-color: #f0f0f0;");
        return tf;
    }
}
