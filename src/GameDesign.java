
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameDesign extends Application {

	private boolean isComputerMode;
	// Data field to represent the number of coins (example array of 5 coins)
	private int[] coins = { 5, 4, 3,14, 5, 6,2 ,5}; // You can change this array as needed
	private Label player1ScoreLabel;
	private Label player2ScoreLabel;
	private Label winner;
	GameState gameState;
	FlowPane buttonsBox;
	Label player2Name;
	Label player1Name;
	Label turnLabel;
	VBox mainLayout;
	public GameDesign(int[] coins, boolean isComputerMode) {
		super();
		this.coins = coins;
		this.isComputerMode = isComputerMode;
		gameState = new GameState(coins);
		for(int coin : coins)
			System.out.println(coin);
	}

	@Override
	public void start(Stage primaryStage) {
		// Player 1's Name and Score Box
		player1Name = new Label("Player 1");
		player1Name.setTextFill(Color.rgb(16, 122, 176));
		player1Name.setFont(new Font(20));

		player1ScoreLabel = new Label("Score: 0");
		player1ScoreLabel.setFont(new Font(20));

		VBox player1Box = new VBox(10, player1Name, player1ScoreLabel);
		player1Box.setAlignment(Pos.CENTER);
		player1Box.setStyle("-fx-border-color: #107AB0; -fx-border-width: 2px; -fx-padding: 20px;");
		player1Box.setMinWidth(150);
		// Player 2's Name and Score Box
		player2Name = new Label("Player 2");
		player2Name.setTextFill(Color.rgb(255, 87, 51));
		player2Name.setFont(new Font(20));
		player2ScoreLabel = new Label("Score: 0");
		player2ScoreLabel.setFont(new Font(20));

		VBox player2Box = new VBox(10, player2Name, player2ScoreLabel);
		player2Box.setAlignment(Pos.CENTER);
		player2Box.setStyle("-fx-border-color: #ff0000; -fx-border-width: 2px; -fx-padding: 20px;");
		player2Box.setMinWidth(150);

		Glow glow1 = new Glow();
		Glow glow2 = new Glow();
        glow1.setLevel(10);
        glow2.setLevel(0);
        player2Name.setEffect(glow2);
        player1Name.setEffect(glow1);
		// Create dynamic buttons for the coins array
        FlowPane buttonsBox = createCoinButtons();

		
		// Main container for both player boxes (Horizontal Box)
		HBox gameBox = new HBox(20, player1Box, player2Box); // Set padding between the boxes
		gameBox.setAlignment(Pos.CENTER);
		turnLabel = new Label("Player 1's Turn");
		turnLabel.setFont(Font.font("Arial", 20));
		turnLabel.setTextFill(Color.BLUE);
		winner = new Label();
		mainLayout = new VBox();
		if(isComputerMode) {
			Button playNext = new Button("play next");
			playNext.setOnAction(e->{
				if(gameState.isGameOver()) { 
					playNext.setDisable(true);
					return;
				}
				
				GameAI gameAI = new GameAI(coins);
				gameState.pickCoin(gameAI.bestMove(gameState.playableIndexLeft(), gameState.playableIndexRight()));
				updateUI();
			});

			mainLayout = new VBox(20, gameBox, buttonsBox, playNext,turnLabel, winner);
			
		}
		else
			mainLayout = new VBox(20, gameBox, buttonsBox, turnLabel, winner);
		mainLayout.setAlignment(Pos.CENTER);
		
		// Scene and Stage Setup
		Scene scene = new Scene(mainLayout, 800, 550); // Adjusted size for better display
		primaryStage.setTitle("Game Design");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	//	Method to create a VBox with buttons based on the number of coins
	private FlowPane createCoinButtons() {
        buttonsBox = new FlowPane();
        buttonsBox.setVgap(10);  // Vertical gap between rows
        buttonsBox.setHgap(10);  // Horizontal gap between coins
        buttonsBox.setPrefWrapLength(300);  // Set the preferred width for wrapping

		buttonsBox.setAlignment(Pos.CENTER);

		// Loop through the coins array to create buttons
		for (int i = 0; i < coins.length; i++) {
			
			
			Button coinButton = new Button("" + gameState.getCoin(i).getValue());
			coinButton.setStyle("-fx-padding: 15px 30px; " + "-fx-font-size: 16px; " + "-fx-background-color: #FFD700; "
					+ "-fx-background-radius: 30px; " + "-fx-text-fill: white; " + "-fx-border-width: 2px; ");

			coinButton.setOnAction(e -> {
				if(isComputerMode)
					return;
				gameState.pickCoin(buttonsBox.getChildren().indexOf(coinButton));
				updateUI();
			});
			buttonsBox.getChildren().add(coinButton);
			for(int j=0; j< buttonsBox.getChildren().size(); j++) {
				Button button =(Button) buttonsBox.getChildren().get(j);
				if(j==0 || j == buttonsBox.getChildren().size()-1) {
					continue;
				}
				else {
					button.setDisable(true);
				}
				
			}
		}
		return buttonsBox;
	}
	
	
	
	private void updateScoreText() {
		Glow glow1 = new Glow();
		Glow glow2 = new Glow();
        glow1.setLevel(10);
        glow2.setLevel(0);
		if(gameState.isPlayer1Turn()) {
			player1ScoreLabel.setText("Score: "+gameState.getPlayer1Score());
	        player1Name.setEffect(glow2);
	        player2Name.setEffect(glow1);
            turnLabel.setText("Player 2's Turn");
            turnLabel.setTextFill(Color.rgb(255, 0, 0));

		}
		else {
			player2ScoreLabel.setText("Score: "+gameState.getPlayer2Score());
	        player2Name.setEffect(glow2);
	        player1Name.setEffect(glow1);
            turnLabel.setText("Player 1's Turn");
            turnLabel.setTextFill(Color.rgb(16, 122, 176));

		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void updateWineer() {
		if(gameState.winner().equals("draw"))
			winner.setText("Draw");
		else{
			winner.setText("Winner is: "+ gameState.winner());
		}
		winner.setFont(Font.font("Impact", 50));
		turnLabel.setText("");
		if(gameState.winner().equals("player1")) {
			winner.setTextFill(Color.rgb(16, 122, 176));  

		}else {
			winner.setTextFill(Color.rgb(255, 0, 0));  

		}
	}
	public void updateCoinsBtnsState() {
		for(int j=0; j< buttonsBox.getChildren().size(); j++) {
			Button button =(Button) buttonsBox.getChildren().get(j);
			Coin coin = gameState.getCoin(j);

			if (coin.getChosenBy().equals("player1")) {
				button.setStyle("-fx-padding: 15px 30px; " + "-fx-font-size: 16px; " + "-fx-background-color: #107AB0; "
						+ "-fx-background-radius: 30px; " + "-fx-text-fill: white; " + "-fx-border-width: 2px; ");
			} else if (coin.getChosenBy().equals("player2")) {
				button.setStyle("-fx-padding: 15px 30px; " + "-fx-font-size: 16px; " + "-fx-background-color: #ff0000; "
						+ "-fx-background-radius: 30px; " + "-fx-text-fill: white; " + "-fx-border-width: 2px; ");
			}
			button.setDisable(!gameState.isCoinPlayable(j));

		}
		
		gameState.switchTurn();
		
	}
	public void updateUI() {
		updateScoreText();
		
		updateCoinsBtnsState();
		if(gameState.isGameOver()) {
			updateWineer();
			Button showDP = new Button("Show DP Table");
			mainLayout.getChildren().add(showDP);
			showDP.setOnAction(e->{
				DPTable dpTable= new DPTable(coins);
				Stage stage = new Stage();
				dpTable.start(stage);
				
			});
		}
	}
}
