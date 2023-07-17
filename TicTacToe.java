/**
 * Objective: This Java application implements a Tic-Tac-Toe game where two players take turns marking cells in a 3x3 grid.
 * The objective of the game is to get three marks in a row, column, or diagonal before the opponent does.
 * 
 * Algorithm: The game is implemented using JavaFX for the GUI. It consists of a grid of cells, represented by a 2D array.
 * Each cell can be empty (' '), marked with 'X', or marked with 'O'. The players take turns clicking on empty cells to
 * place their marks. The game checks for a win or draw condition after each move. If a player wins, the game ends and
 * displays the winner. If all cells are filled without a win, the game ends in a draw. The game status is displayed using
 * a label at the bottom of the window.
 * 
 * Input and Output: The input is provided by the players through mouse clicks on the cells. The output is displayed in the
 * game window, indicating the current player's turn, win status, or draw status.
 * 
 * Created by: Juan Sixtos
 * Date: 7/16/2023
 * Version: 1.00
 */
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;

public class TicTacToe extends Application {

    // 2D array to represent the TicTacToe grid
    private Cell[][] cell = new Cell[3][3];
    private Label lblStatus = new Label("X's turn to play"); // Label to display game status
    private char whoseTurn = 'X'; // Current player's turn ('X' or 'O')

    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
        // Create the TicTacToe grid and add cells to the grid pane
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pane.add(cell[i][j] = new Cell(), j, i);
            }
        }
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane); // Set the grid pane at the center
        borderPane.setBottom(lblStatus); // Set the status label at the bottom
        Scene scene = new Scene(borderPane, 450, 170);
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Check if the grid is full
    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cell[i][j].getToken() == ' ') {
                    return false; // If any cell is empty, the grid is not full
                }
            }
        }
        return true; // All cells are filled, the grid is full
    }

    // Check if the specified token has won the game
    public boolean isWon(char token) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (cell[i][0].getToken() == token && cell[i][1].getToken() == token && cell[i][2].getToken() == token) {
                return true; // Token has won in a row
            }
        }
        // Check columns
        for (int j = 0; j < 3; j++) {
            if (cell[0][j].getToken() == token && cell[1][j].getToken() == token && cell[2][j].getToken() == token) {
                return true; // Token has won in a column
            }
        }
        // Check diagonals
        if (cell[0][0].getToken() == token && cell[1][1].getToken() == token && cell[2][2].getToken() == token) {
            return true; // Token has won in the main diagonal
        }
        if (cell[0][2].getToken() == token && cell[1][1].getToken() == token && cell[2][0].getToken() == token) {
            return true; // Token has won in the other diagonal
        }
        return false; // Token has not won
    }

    // Cell class representing a single cell in the TicTacToe grid
    public class Cell extends Pane {

        private char token = ' '; // Token in the cell ('X', 'O', or empty)

        public Cell() {
            setStyle("-fx-border-color:black"); // Set cell border color
            this.setPrefSize(800, 800); // Set preferred size of the cell
            this.setOnMouseClicked(e -> handleMouseClick()); // Handle mouse click event
        }

        public char getToken() {
            return token; // Get the token in the cell
        }

        public void setToken(char c) {
            token = c; // Set the token in the cell

            // Display the token in the cell as a line for 'X' and an ellipse for 'O'
            if (token == 'X') {
                Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
                line1.endXProperty().bind(this.widthProperty().subtract(10));
                line1.endYProperty().bind(this.heightProperty().subtract(10));
                Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
                line2.startYProperty().bind(this.heightProperty().subtract(10));
                line2.endXProperty().bind(this.widthProperty().subtract(10));
                this.getChildren().addAll(line1, line2);
            } else if (token == 'O') {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2 - 10,
                        this.getHeight() / 2 - 10);
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                ellipse.setStroke(Color.BLACK);
                ellipse.setFill(Color.WHITE);
                this.getChildren().add(ellipse);
            }
        }

        private void handleMouseClick() {
            if (token == ' ' && whoseTurn != ' ') {
                setToken(whoseTurn); // Set the token in the clicked cell

                // Check for a win or draw condition
                if (isWon(whoseTurn)) {
                    lblStatus.setText(whoseTurn + " won! The game is over!");
                    whoseTurn = ' ';
                } else if (isFull()) {
                    lblStatus.setText("Draw! The game is over!");
                    whoseTurn = ' ';
                } else {
                    whoseTurn = (whoseTurn == 'X') ? 'O' : 'X'; // Switch the turn to the other player
                    lblStatus.setText(whoseTurn + "'s turn!");
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args); // Launch the application
    }
}