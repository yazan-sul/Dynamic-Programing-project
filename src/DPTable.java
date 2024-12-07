import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class DPTable extends Application {
    int[] coins = {4, 6, 8, 2, 5, 1};
    VBox layout;

    public DPTable(int[] coins) {
        super();
        this.coins = coins;
    }

    // Entry point for JavaFX application
    @Override
    public void start(Stage primaryStage) {
        // Initialize DP table with a 2D array
        int[][] dp = new int[coins.length][coins.length];

        // Fill the DP table using your logic
        for (int j = 0; j < dp.length; j++) {
            for (int i = j; i >= 0; i--) {
                dp[i][j] = m(i, j);
            }
        }

        // Create a GridPane to display the DP table
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);  // Vertical gap between rows
        gridPane.setHgap(10);  // Horizontal gap between cells
        gridPane.setPadding(new javafx.geometry.Insets(20));  // Padding for the entire grid

        // Define column constraints to ensure each column is wide enough for 3-digit numbers
        for (int col = 0; col < dp.length + 1; col++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setMinWidth(70);  // Minimum width for each column (wide enough for 3 digits)
            colConstraints.setHgrow(Priority.ALWAYS);  // Allow columns to grow horizontally
            gridPane.getColumnConstraints().add(colConstraints);
        }

        // Create headers for the table (column indices
        for (int col = 0; col < dp.length; col++) {
            Label headerLabel = new Label("" + coins[col]);
            headerLabel.setStyle("-fx-font-weight: bold; -fx-background-color: #6fa3ef; -fx-padding: 5px;");
            gridPane.add(headerLabel, col + 1, 0);  // Place headers in the first row, starting from the second column
        }

        // Fill the DP table with values in grid cells
        for (int i = 0; i < dp.length; i++) {
            // Add row header (for row indices)
            Label rowLabel = new Label("" + coins[i]);
            rowLabel.setStyle("-fx-font-weight: bold; -fx-background-color: #6fa3ef; -fx-padding: 5px;");
            gridPane.add(rowLabel, 0, i + 1);  // Row header in the first column

            for (int j = 0; j < dp.length; j++) {
                // Add DP table value in each cell
                Label valueLabel = new Label(String.valueOf(dp[i][j]));
                valueLabel.setStyle("-fx-background-color: #e8f1ff; -fx-padding: 5px;");
                gridPane.add(valueLabel, j + 1, i + 1);  // Add value to grid starting from (1, 1)
            }
        }

        // Create layout and add the GridPane to it
        layout = new VBox(20);
        layout.setPadding(new javafx.geometry.Insets(20));
        layout.getChildren().addAll(new Label("Dynamic Programming Table"), gridPane);

        // Wrap the VBox inside a ScrollPane for scrolling when content overflows
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true);  // Ensure content fits the width of the scroll pane
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);  // Vertical scrollbar appears as needed
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);  // Horizontal scrollbar appears as needed

        // Create Scene and Stage
        Scene scene = new Scene(scrollPane, 800, 600);

        // Set the title and scene for the stage
        primaryStage.setTitle("DP Table");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public VBox getLayout() {
        return layout;
    }

    // Method to generate the DP value
    public int m(int i, int j) {
        if (i == j) return coins[i];
        if (j < i) return 0;
        if (j - i == 1) return Math.max(coins[i], coins[j]);

        return Math.max(coins[i] + Math.min(m(i + 2, j), m(i + 1, j - 1)),
                        coins[j] + Math.min(m(i, j - 2), m(i + 1, j - 1)));
    }

}
