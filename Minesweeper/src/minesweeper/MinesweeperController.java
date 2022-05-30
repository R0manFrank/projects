package minesweeper;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class MinesweeperController implements Initializable {

    @FXML
    private Label gameState;
    Scanner scanner = new Scanner(System.in);
    private Button[][] knopfs = new Button[10][10];
    int[][] minefield = new int[10][10];
    public int state = 0;
    private int check = 0;
    private int length = 10;
    private int height = 10;
    private int minen = 0;
    @FXML
    private TextField mineCount;
    Random random = new Random();
    private int flagCount = 0;
    @FXML
    private TextField lengthText;
    @FXML
    private TextField heightText;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label flagCounter;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gameState.setText("Please start the game.");
    }

    public void putNumbers() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                for (int a = -1; a <= 1; a++) {
                    for (int b = -1; b <= 1; b++) {
                        if (a == 0 && b == 0) {
                            continue;
                        }
                        try {
                            if (minefield[i + a][j + b] >= 9) {
                                minefield[i][j]++;
                            }
                        } catch (Exception x) {
                        }
                    }
                }
            }
        }
    }

    @FXML
    private void handleActionStartGame(ActionEvent event) {
        length = Integer.parseInt(lengthText.getText());
        height = Integer.parseInt(heightText.getText());
        flagCount = 0;
        flagCounter.setText("You have placed " + flagCount + " flags. \n There are " + (minen-flagCount) + " bombs left.");
        /*
        if (length > 30){
            length = 30;
            lengthText.setText("30");
        }
        if (height > 30){
            height = 30;
            heightText.setText("30");
        }*/
        knopfs = new Button[length][height];
        minefield = new int[length][height];
        borderPane.setCenter(null);
        GridPane gridPane = new GridPane();
        borderPane.setCenter(gridPane);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                Button button = new Button();
                button.setPrefHeight(1000);
                button.setPrefWidth(1000);
                button.setStyle("-fx-font-size:1");
                button.setPadding(new Insets(0,0,0,0)); // <----- this one works. Standard value is 5.0.
                button.setOnMouseClicked(this::handleButtonClicked);
                gridPane.add(button, i, j);
                knopfs[i][j] = button;
            }
        }
        gameState.setText("Game Started");
        state = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                minefield[i][j] = 0;
            }
        }
        minen = Integer.parseInt(mineCount.getText());
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                knopfs[i][j].setText("");
                knopfs[i][j].setStyle("-fx-background-color: lightgreen;");
            }
        }
        putNumbers();
    }
    

    private void handleButtonClicked(MouseEvent event) {
        Button button = (Button) event.getSource();
        if (state == 0 && minen <= (length * height - 1) && minen >= 1) {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < height; j++) {
                    if (knopfs[i][j] == button) {
                        knopfs[i][j].setStyle("-fx-background-color: lightgreen; -fx-font-color: lightgreen;");
                        if (minen <= (length * height - 10)) {
                            for (int a = -1; a <= 1; a++) {
                                for (int b = -1; b <= 1; b++) {
                                    if (a == 0 && b == 0) {
                                        continue;
                                    }
                                    try {
                                        knopfs[i + a][j + b].setStyle("-fx-background-color: lightgreen; -fx-font-color: lightgreen;");
                                    } catch (Exception x) {
                                    }
                                }
                            }
                        }
                    }
                }
            }

            int mineCheck = 0;
            while (mineCheck < minen) {
                int m = random.nextInt(length * height + 1);
                int zero = 0;
                while (zero < m) {
                    for (int i = 0; i < length; i++) {
                        for (int j = 0; j < height; j++) {
                            zero++;
                            if (zero == m && minefield[i][j] != 9 && !knopfs[i][j].getStyle().equals("-fx-background-color: lightgreen; -fx-font-color: lightgreen;")) {
                                minefield[i][j] = 9;
                                mineCheck++;
                            }
                            if (zero >= m) {
                                break;
                            }
                        }
                        if (zero >= m) {
                            break;
                        }
                    }
                }
            }
            putNumbers();
            state = 1;
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < height; j++) {
                    knopfs[i][j].setStyle("-fx-background-color: lightgreen;");
                }
            }
        }
        if (state == 1 && event.getButton() == MouseButton.PRIMARY) {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < height; j++) {
                    if (knopfs[i][j] == button && state == 1) {
                        if (minefield[i][j] == 0) {
                            button.setStyle("-fx-background-color: #dcdcdc;");
                            for (int z = 0; z < 100; z++) {
                                for (int x = 0; x < length; x++) {
                                    for (int y = 0; y < height; y++) {
                                        if (minefield[x][y] == 0 && knopfs[x][y].getStyle().equals("-fx-background-color: #dcdcdc;")) {
                                            for (int a = -1; a <= 1; a++){
                                                for (int b = -1; b <= 1; b++){
                                                    if (a == 0 && b == 0){
                                                        continue;
                                                    }
                                                    try{
                                                        if (knopfs[x + a][y + b].getStyle().equals("-fx-background-color: lightgreen;") && minefield[x + a][y + b] <= 8) {
                                                            if (minefield[x + a][y + b] == 0) {
                                                                knopfs[x + a][y + b].setStyle("-fx-background-color: #dcdcdc;");
                                                            } else {
                                                                knopfs[x + a][y + b].setStyle("-fx-background-color: #d3d3d3;");
                                                            }
                                                        }  
                                                    }
                                                    catch(Exception e){
                                                    }
                                                }
                                            }
                                        }
                                        if (knopfs[x][y].getStyle().equals("-fx-background-color: #d3d3d3;") && knopfs[x][y].getText().equals("")) {
                                            if (minefield[x][y] == 1) {
                                                knopfs[x][y].setText(String.valueOf(minefield[x][y]));
                                                knopfs[x][y].setStyle("-fx-text-fill: blue; -fx-font-weight: bold; -fx-background-color: #d3d3d3;");
                                            }
                                            if (minefield[x][y] == 2) {
                                                knopfs[x][y].setText(String.valueOf(minefield[x][y]));
                                                knopfs[x][y].setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-background-color: #d3d3d3;");
                                            }
                                            if (minefield[x][y] == 3) {
                                                knopfs[x][y].setText(String.valueOf(minefield[x][y]));
                                                knopfs[x][y].setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-background-color: #d3d3d3;");
                                            }
                                            if (minefield[x][y] == 4) {
                                                knopfs[x][y].setText(String.valueOf(minefield[x][y]));
                                                knopfs[x][y].setStyle("-fx-text-fill: darkblue; -fx-font-weight: bold; -fx-background-color: #d3d3d3;");
                                            }
                                            if (minefield[x][y] == 5) {
                                                knopfs[x][y].setText(String.valueOf(minefield[x][y]));
                                                knopfs[x][y].setStyle("-fx-text-fill: darkred; -fx-font-weight: bold; -fx-background-color: #d3d3d3;");
                                            }
                                            if (minefield[x][y] == 6) {
                                                knopfs[x][y].setText(String.valueOf(minefield[x][y]));
                                                knopfs[x][y].setStyle("-fx-text-fill: turquoise; -fx-font-weight: bold; -fx-background-color: #d3d3d3;");
                                            }
                                            if (minefield[x][y] == 7) {
                                                knopfs[x][y].setText(String.valueOf(minefield[x][y]));
                                                knopfs[x][y].setStyle("-fx-text-fill: purple; -fx-font-weight: bold; -fx-background-color: #d3d3d3;");
                                            }
                                            if (minefield[x][y] == 8) {
                                                knopfs[x][y].setText(String.valueOf(minefield[x][y]));
                                                knopfs[x][y].setStyle("-fx-text-fill: gray; -fx-font-weight: bold; -fx-background-color: #d3d3d3;");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (minefield[i][j] == 1 && knopfs[i][j].getStyle().equals("-fx-background-color: lightgreen;")) {
                            button.setText(String.valueOf(minefield[i][j]));
                            button.setStyle("-fx-text-fill: blue; -fx-font-weight: bold; -fx-background-color: #d3d3d3;");
                        } else if (minefield[i][j] == 2 && knopfs[i][j].getStyle().equals("-fx-background-color: lightgreen;")) {
                            button.setText(String.valueOf(minefield[i][j]));
                            button.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-background-color: #d3d3d3;");
                        } else if (minefield[i][j] == 3 && knopfs[i][j].getStyle().equals("-fx-background-color: lightgreen;")) {
                            button.setText(String.valueOf(minefield[i][j]));
                            button.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-background-color: #d3d3d3;");
                        } else if (minefield[i][j] == 4 && knopfs[i][j].getStyle().equals("-fx-background-color: lightgreen;")) {
                            button.setText(String.valueOf(minefield[i][j]));
                            button.setStyle("-fx-text-fill: darkblue; -fx-font-weight: bold; -fx-background-color: #d3d3d3;");
                        } else if (minefield[i][j] == 5 && knopfs[i][j].getStyle().equals("-fx-background-color: lightgreen;")) {
                            button.setText(String.valueOf(minefield[i][j]));
                            button.setStyle("-fx-text-fill: darkred; -fx-font-weight: bold; -fx-background-color: #d3d3d3;");
                        } else if (minefield[i][j] == 6 && knopfs[i][j].getStyle().equals("-fx-background-color: lightgreen;")) {
                            button.setText(String.valueOf(minefield[i][j]));
                            button.setStyle("-fx-text-fill: turquoise; -fx-font-weight: bold; -fx-background-color: #d3d3d3;");
                        } else if (minefield[i][j] == 7 && knopfs[i][j].getStyle().equals("-fx-background-color: lightgreen;")) {
                            button.setText(String.valueOf(minefield[i][j]));
                            button.setStyle("-fx-text-fill: purple; -fx-font-weight: bold; -fx-background-color: #d3d3d3;");
                        } else if (minefield[i][j] == 8 && knopfs[i][j].getStyle().equals("-fx-background-color: lightgreen;")) {
                            button.setText(String.valueOf(minefield[i][j]));
                            button.setStyle("-fx-text-fill: gray; -fx-font-weight: bold; -fx-background-color: #d3d3d3;");
                        } else if (minefield[i][j] >= 9 && knopfs[i][j].getStyle().equals("-fx-background-color: lightgreen;")) {
                            button.setStyle("-fx-background-color: darkred;");
                            gameState.setText("Game Over");
                            state = 2;
                        }
                    }
                    if (state == 2) {
                        for (int a = 0; a < length; a++) {
                            for (int b = 0; b < height; b++) {
                                if (minefield[a][b] >= 9) {
                                    knopfs[a][b].setStyle("-fx-background-color: darkred;");
                                }
                            }
                        }
                    }
                    check = 0;
                    for (int a = 0; a < length; a++) {
                        for (int b = 0; b < height; b++) {
                            if (minefield[a][b] <= 8 && (knopfs[a][b].getStyle().equals("-fx-background-color: #dcdcdc;") || !knopfs[a][b].getText().equals(""))) {
                                check++;
                            }
                        }
                    }
                    if (check == (length * height - minen)) {
                        state = 3;
                        gameState.setText("You win.");
                    }
                }
            }
        }
        if (state == 1 && event.getButton() == MouseButton.SECONDARY) {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < height; j++) {
                    if (knopfs[i][j] == button) {
                        if (knopfs[i][j].getStyle().equals("-fx-background-color: lightgreen;")) {
                            knopfs[i][j].setStyle("-fx-background-color: #FF8060; -fx-text-fill: white; -fx-font-weight: bold; -fx-font: 10 regular;");
                            flagCount++;
                            flagCounter.setText("You have placed " + flagCount + " flags. \n There are " + (minen-flagCount) + " bombs left.");
                        } else if (knopfs[i][j].getStyle().equals("-fx-background-color: #FF8060; -fx-text-fill: white; -fx-font-weight: bold; -fx-font: 10 regular;")) {
                            knopfs[i][j].setStyle("-fx-background-color: lightgreen;");
                            flagCount--;
                            flagCounter.setText("You have placed " + flagCount + " flags. \n There are " + (minen-flagCount) + " bombs left.");
                        }
                    }
                }
            }
        }
    }
}
