import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	private boolean gameMode;
	private static int[] array = { 4, 15, 7, 3, 8, 9 };
	int coins[];
	FlowPane coinsInput;
	VBox inputLayout;
	@Override
	public void start(Stage stage) throws Exception {

		VBox layout = new VBox();
		layout.setSpacing(20);
		layout.setPadding(new Insets(20));
		// Label
		Label label = new Label("Choose Game Mode:");
		label.setFont(Font.font("Arial", 20));

		// Create buttons for mode selection
		Button playerVsPlayerBtn = new Button("Player vs Player");
		playerVsPlayerBtn
				.setStyle("-fx-padding: 15px 30px; " + "-fx-font-size: 16px; " + "-fx-background-color: #107AB0; "
						+ "-fx-background-radius: 30px; " + "-fx-text-fill: white; " + "-fx-border-width: 2px; ");

		Button computerVsComputerBtn = new Button("Computer vs Computer");
		computerVsComputerBtn
				.setStyle("-fx-padding: 15px 30px; " + "-fx-font-size: 16px; " + "-fx-background-color: #107AB0; "
						+ "-fx-background-radius: 30px; " + "-fx-text-fill: white; " + "-fx-border-width: 2px; ");

		layout.getChildren().addAll(label, playerVsPlayerBtn, computerVsComputerBtn);
		// Set actions for buttons
		playerVsPlayerBtn.setOnAction(e -> {
			handlePlayerVsPlayer(stage);
			showInputMethodScene(stage);

		});

		computerVsComputerBtn.setOnAction(e -> {
			handleComputerVsComputer(stage);
			showInputMethodScene(stage);

		});
		Scene scene = new Scene(layout, 500, 500);
		stage.setScene(scene);
		stage.show();
	}

	private void handlePlayerVsPlayer(Stage primaryStage) {
		gameMode = false;
	}

	private void handleComputerVsComputer(Stage primaryStage) {
		gameMode = true;
	}

	private void showInputMethodScene(Stage primaryStage) {
		// Create a new scene layout for input method selection
		inputLayout = new VBox(20);
		inputLayout.setSpacing(20);
		inputLayout.setPadding(new Insets(20));

		// Label for input method selection
		Label inputLabel = new Label("Choose Input Method:");
		inputLabel.setFont(Font.font("Arial", 20));

		// Create buttons for input method selection
		Button fileInputBtn = new Button("File Input");
		fileInputBtn.setStyle("-fx-padding: 15px 30px; " + "-fx-font-size: 16px; " + "-fx-background-color: #107AB0; "
				+ "-fx-background-radius: 30px; " + "-fx-text-fill: white; " + "-fx-border-width: 2px; ");
		Button manualInputBtn = new Button("Manual Input");
		manualInputBtn.setStyle("-fx-padding: 15px 30px; " + "-fx-font-size: 16px; " + "-fx-background-color: #107AB0; "
				+ "-fx-background-radius: 30px; " + "-fx-text-fill: white; " + "-fx-border-width: 2px; ");
		Button randomInputBtn = new Button("Random Input");
		randomInputBtn.setStyle("-fx-padding: 15px 30px; " + "-fx-font-size: 16px; " + "-fx-background-color: #107AB0; "
				+ "-fx-background-radius: 30px; " + "-fx-text-fill: white; " + "-fx-border-width: 2px; ");

		// Add buttons to the layout
		inputLayout.getChildren().addAll(inputLabel, fileInputBtn, manualInputBtn, randomInputBtn);

		// Set actions for buttons
		fileInputBtn.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

			// Open file and read coins
			File file = fileChooser.showOpenDialog(primaryStage);
			if (file != null) {
				readCoinsFromFile(file);
				if(coins != null) {
					GameDesign gameDesign = new GameDesign(coins,gameMode);
					gameDesign.start(primaryStage);
				}
			}
		});

		manualInputBtn.setOnAction(e -> {
			VBox box = new VBox();
			box.setPadding(new Insets(20));
			box.setAlignment(Pos.CENTER);
			box.setSpacing(20); // Space between elements
			StepperExample se = new StepperExample();
			box.getChildren().add(se.getStepperLayout());
			Button next = new Button("Next");
			box.getChildren().add(next);
			next.setOnAction(s -> {
				coins = new int[se.getSpinnerValue()];
			    VBox coinsManulaView = new VBox(10);
				// Step 2: Create a VBox to hold the coin inputs (TextFields)
				coinsInput = new FlowPane(); // VBox to hold text fields for coin values
				coinsInput.setAlignment(Pos.CENTER);
				coinsInput.setVgap(10);  // Vertical gap between rows
				coinsInput.setHgap(10);  // Horizontal gap between coins
				coinsInput.setPrefWrapLength(300);  // Set the preferred width for wrapping

				// Step 3: Button to submit the inputs
				Button submitButton = new Button("Submit");
				submitButton.setOnAction(ddd ->{
					processInput();
					GameDesign gameDesign= new GameDesign(coins,gameMode);
					gameDesign.start(primaryStage);
				}); // Handle button click

				// coin input fields and submit button to the layout
				generateCoinInputs(se.getSpinnerValue()); // Generate the TextFields based on the number of coins
				coinsManulaView.getChildren().addAll(new Label("Enter the coin values:"), coinsInput, submitButton);
				Scene scene = new Scene(coinsManulaView, 500, 500);
				primaryStage.setScene(scene);
			});
			Scene scene = new Scene(box, 500, 500);
			primaryStage.setScene(scene);

		});

		randomInputBtn.setOnAction(e -> {
			
			handleRandomInput(primaryStage);
			GameDesign gameDesign = new GameDesign(coins,gameMode);
			gameDesign.start(primaryStage);
	
		});

		// Create scene for input method and display
		Scene scene = new Scene(inputLayout, 400, 550);
		primaryStage.setScene(scene);
	}

	private void readCoinsFromFile(File file) {
		try {
			Scanner reader = new Scanner(file);
			String numCoinsLine = reader.nextLine();
			int numCoins = Integer.parseInt(numCoinsLine.trim()); // First line: Number of coins

			// Read the coin values from the second line
			String coinsLine = reader.nextLine();
			String[] coinStrings = coinsLine.split(" ");

			if (coinStrings.length != numCoins) {
				Label erorLabel= new Label("number of coins should be even and above four");
				inputLayout.getChildren().add(erorLabel);
				return;
			}

			coins = new int[numCoins];
			int i = 0;
			for (String coin : coinStrings) {
				coins[i] = (Integer.parseInt(coin));
				i++;
			}

			
			reader.close();
		} catch (IOException e) {
			System.out.println("error" + e);
		} catch (NumberFormatException e) {
			System.out.println("error" + e);
		}
	}

	private void handleRandomInput(Stage primaryStage) {
		// Generate a even random number of coins
		Random random = new Random();

		// bettween 4-14
		int randomEven = random.nextInt(6) * 2 + 4;
		coins = new int[randomEven];
		for (int i = 0; i < randomEven; i++) {
			// between 1-20
			int coinNumber = (int) (random.nextInt(20) + 1);
			coins[i] = coinNumber; // Random coin values
		}
	
	}

	private void generateCoinInputs(int numOfCoins) {
		coinsInput.getChildren().clear();
		for (int i = 0; i < numOfCoins; i++) {
			TextField coinField = new TextField();
			coinField.setPromptText("Coin " + (i + 1)); // Set a prompt text for each coin
			coinsInput.getChildren().add(coinField); // Add the text field to the VBox
		}
	}

	private void processInput() {
		String[] coinValues = new String[coinsInput.getChildren().size()];
		for (int i = 0; i < coinValues.length; i++) {
			TextField field = (TextField) coinsInput.getChildren().get(i);
			coinValues[i] = field.getText();
		}
		try {
			coins = new int[coinValues.length];
			for (int i = 0; i < coinValues.length; i++)
				coins[i] = Integer.parseInt(coinValues[i]); 
		
		} catch (NumberFormatException e) {
		}
	}

	public static void main(String[] args) {
		launch(args);

	}

	public static int m(int i, int j) {
		if (i == j)
			return array[i];
		if (j < i)
			return 0;
		if (j - i == 1)
			return Math.max(array[i], array[j]);

		return Math.max(array[i] + Math.min(m(i + 2, j), m(i + 1, j - 1)),
				array[j] + Math.min(m(i, j - 2), m(i + 1, j - 1)));

	}

	class StepperExample {
		Spinner<Integer> spinner;
		VBox stepperLayout = new VBox();

		public StepperExample() {

			spinner = new Spinner<>();
			spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 20, 4, 2)); // Min, Max,
			spinner.setTooltip(new Tooltip("Select an even number between 4 and 10"));
			stepperLayout.getChildren().addAll(new Label("Select Size of array:"), spinner);

		}

		public VBox getStepperLayout() {
			return stepperLayout;
		}

		public int getSpinnerValue() {
			return spinner.getValue();
		}
	}

}
