package no.ntnu.cardgame;

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

/**
 * JavaFX application for the card game (Oppgave 4 & 5).
 *
 * <p>The GUI contains:
 * <ul>
 *   <li>A card display area showing the current hand</li>
 *   <li>A "Deal hand" button – deals 5 random cards</li>
 *   <li>A "Check hand" button – runs stream analysis on the hand</li>
 *   <li>Read-only text fields showing: sum of faces, hearts cards, flush, queen of spades</li>
 * </ul>
 *
 * <p>No FXML is used; the entire UI is built in code.
 */
public class CardGameApp extends Application {

    // -------------------------------------------------------------------------
    // Model
    // -------------------------------------------------------------------------
    private final DeckOfCards deck = new DeckOfCards();
    private HandOfCards currentHand = null;

    // -------------------------------------------------------------------------
    // UI controls we need to update at runtime
    // -------------------------------------------------------------------------
    private FlowPane cardDisplayPane;
    private TextField sumField;
    private TextField heartsField;
    private TextField flushField;
    private TextField queenField;
    private Label statusLabel;

    // -------------------------------------------------------------------------
    // JavaFX entry point
    // -------------------------------------------------------------------------

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Card Game – JavaFX");
        primaryStage.setScene(buildScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // -------------------------------------------------------------------------
    // Scene / layout construction (Oppgave 4 – no FXML)
    // -------------------------------------------------------------------------

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
                "-fx-border-color: #888; -fx-border-width: 1.5; "
                + "-fx-background-color: #2d6a2d; -fx-border-radius: 4;");

        Label placeholder = new Label("Press \"Deal hand\" to start");
        placeholder.setTextFill(Color.LIGHTGRAY);
        placeholder.setFont(Font.font("Arial", 14));
        cardDisplayPane.getChildren().add(placeholder);

        // --- Buttons (right column) ---
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

        // --- Top area: card display + buttons side by side ---
        HBox topArea = new HBox(16, cardDisplayPane, buttonBox);
        topArea.setAlignment(Pos.TOP_LEFT);

        // --- Analysis grid (Oppgave 5) ---
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

        // --- Status label ---
        statusLabel = new Label("Ready.");
        statusLabel.setTextFill(Color.DARKGRAY);
        statusLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        // --- Root layout ---
        VBox root = new VBox(14, topArea, infoGrid, statusLabel);
        root.setPadding(new Insets(16));
        root.setAlignment(Pos.TOP_LEFT);

        return new Scene(root, 620, 400);
    }

    // -------------------------------------------------------------------------
    // Button handlers
    // -------------------------------------------------------------------------

    /** Deals 5 new cards and displays them on screen. */
    private void handleDealHand() {
        currentHand = deck.dealHand(5);
        renderCards();
        // Clear analysis fields until "Check hand" is pressed
        clearAnalysisFields();
        statusLabel.setText("Hand dealt. Press \"Check hand\" to analyse.");
    }

    /** Runs stream analysis on the current hand and fills the info fields. */
    private void handleCheckHand() {
        if (currentHand == null) {
            statusLabel.setText("No hand dealt yet – press \"Deal hand\" first.");
            return;
        }

        // Oppgave 5 – streams
        sumField.setText(String.valueOf(currentHand.sumOfFaces()));
        heartsField.setText(currentHand.heartsAsString());
        flushField.setText(currentHand.isFlush() ? "Yes" : "No");
        queenField.setText(currentHand.hasQueenOfSpades() ? "Yes" : "No");

        statusLabel.setText("Analysis complete.");
    }

    // -------------------------------------------------------------------------
    // Helper methods
    // -------------------------------------------------------------------------

    /**
     * Renders each card in {@link #currentHand} as a coloured label tile inside
     * {@link #cardDisplayPane}.
     */
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

    /** Returns a CSS style string for a card tile depending on suit. */
    private String cardStyle(char suit) {
        String textColor = (suit == 'H' || suit == 'D') ? "#cc0000" : "#111111";
        return "-fx-background-color: white; "
                + "-fx-border-color: #555; -fx-border-width: 1.5; "
                + "-fx-border-radius: 6; -fx-background-radius: 6; "
                + "-fx-text-fill: " + textColor + ";";
    }

    /** Clears all analysis text fields. */
    private void clearAnalysisFields() {
        sumField.clear();
        heartsField.clear();
        flushField.clear();
        queenField.clear();
    }

    /** Creates a non-editable {@link TextField} with the given preferred width. */
    private TextField makeReadOnlyField(int width) {
        TextField tf = new TextField();
        tf.setEditable(false);
        tf.setPrefWidth(width);
        tf.setStyle("-fx-background-color: #f0f0f0;");
        return tf;
    }

    // -------------------------------------------------------------------------
    // Main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        launch(args);
    }
}
