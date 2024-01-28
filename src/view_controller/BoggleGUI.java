package view_controller;

/*
 * Author: Milo Osterman
 * Course: CSC335
 */
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import model.BoggleGame;
import model.DiceTray;

public class BoggleGUI extends Application {
	private DiceTray diceTray = new DiceTray();
	private TextArea trayArea = new TextArea();
	private BorderPane borderPane = new BorderPane();
	private Text score = new Text("Score: 0");
	private Label guessLabel = new Label("Guesses: ");
	private TextField guessField = new TextField();
	private Button guessSubmit = new Button("Submit");
	private Label foundLabel = new Label("Found Words: ");
	private TextArea found = new TextArea();
	private Label incorrectLabel = new Label("Incorrect Words: ");
	private TextArea incorrect = new TextArea();
	private Label missedLabel = new Label("Missed Words: ");
	private TextArea missed = new TextArea();

	@Override
	public void start(Stage stage) {
		stage.setTitle("Boggle Game");
		borderPane.setPadding(new Insets(12, 12, 12, 12));
		HBox buttonBox = addButtons();
		borderPane.setTop(buttonBox);
		VBox trayBox = addTray();
		borderPane.setLeft(trayBox);
		VBox wordBox = addWord();
		borderPane.setCenter(wordBox);
		Scene scene = new Scene(borderPane, 500, 300);
		stage.setScene(scene);
		stage.show();

	}

	private VBox addWord() {
		VBox wordBox = new VBox();
		wordBox.setPadding(new Insets(12, 12, 12, 12));
		guessSubmit.setOnAction(e -> handleSubmit());
		found.setEditable(false);
		incorrect.setEditable(false);
		missed.setEditable(false);
		found.setWrapText(true);
		incorrect.setWrapText(true);
		missed.setWrapText(true);
		wordBox.getChildren().addAll(guessLabel, guessField, guessSubmit, foundLabel, found, incorrectLabel, incorrect,
				missedLabel, missed);
		return wordBox;

	}

	private void handleSubmit() {
		String input = guessField.getText();
		String[] guesses = input.split(" ");
		BoggleGame boggleGame = new BoggleGame();
		boggleGame.play(guesses);
	}

	private VBox addTray() {
		VBox trayBox = new VBox();
		trayBox.setSpacing(12);
		trayArea.setMaxSize(150, 150);
		trayArea.setEditable(false);
		Font font = Font.font("Courier New", FontWeight.BOLD, 16);
		trayArea.setFont(font);
		trayArea.setText(diceTray.toString());
		trayBox.getChildren().addAll(trayArea, score);
		return trayBox;
	}

	private HBox addButtons() {
		HBox buttonBox = new HBox();
		buttonBox.setPadding(new Insets(0, 0, 12, 130));
		buttonBox.setSpacing(50);
		Button newButton = new Button("New Game");
		newButton.setOnAction(e -> handleNewGame());
		Button endButton = new Button("End Program");
		buttonBox.getChildren().addAll(newButton, endButton);
		return buttonBox;
	}

	private void handleNewGame() {
		diceTray = new DiceTray();
		trayArea.setText(diceTray.toString());
		guessField.clear();
		found.clear();
		incorrect.clear();
		missed.clear();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
