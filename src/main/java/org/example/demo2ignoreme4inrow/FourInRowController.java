package org.example.demo2ignoreme4inrow;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**General controller for the game. The root (stackpane) handles all the 3 screens:
 * - welcome screen
 * - choice screen
 * - board screen
 * @author Kafui
 * @version 01st September 2025
 */
public class FourInRowController {

    /*The root that contains all the screens is a stack pane*/
    @FXML public StackPane root;

    /*Declaration of the value that will be the duration of the game*/
    //TODO: Make the time on the game screen counting each second dynamically

    /*--------------------------------------------INITIALIZING THE VIEW TO THE WELCOME SCREEN--------------------------------------------
    * Must be set in the .FXML at the beginning:
    * Fist screen visible, and the others not*/

    /*--------------------------------------------WELCOME SCREEN LOGIC--------------------------------------------*/

    @FXML public VBox welcomeScreen;

    // TODO: update the behavior of this event; idea: player click ENTER before next screen comes
    /**Triggered by onMouseMoved (see .FXML file)
     * Go to the next screen (choice screen) where players make their choices.
     * @param mouseEvent: The cptured event, click on the button.
     */
    @FXML public void goToChoiceScreen(javafx.scene.input.MouseEvent mouseEvent) {
        /*setting the 2nd screen visible and the others invisible*/
        root.getChildren().get(0).setVisible(false);root.getChildren().get(0).setDisable(true);
        root.getChildren().get(1).setVisible(true);
        root.getChildren().get(2).setVisible(false);
    }

    /*--------------------------------------------CHOICE SCREEN LOGIC--------------------------------------------*/

    @FXML public VBox choiceScreen;

    /*Declaring the players' instances*/
    Player player1;
    Player player2;

    /*Variables containing the players' names and colors*/
    @FXML public TextField player1NameField;
    @FXML public ColorPicker player1ColorPicker;
    @FXML public Button saveButton1;

    @FXML public TextField player2NameFIeld;
    @FXML public ColorPicker player2ColorPicker;
    @FXML public Button saveButton2;

    /*Setting the save buttons' action to save the datas in players' fields*/
    //TODO: change the color of the save buttons when infos are saved.
    // An idea is to change the css class of the buttons when clicked

    /**Triggered when the save button under player 1's form is clicked.
     * Saves the infos in the text field as name, and color picker as color (of player 1).
      */
    @FXML public void savePlayer1Info(){
        /*Create a new instance of the player with the custom values*/
        player1 = new Player(
                player1NameField.getText(),
                player1ColorPicker.getValue()
        );

        /*Changing the save button's text to notify that datas have been saved*/
        saveButton1.setText("Saved");
    }

    /**Triggered when the save button under player 2's form is clicked.
     * Saves the infos in the text field as name, and color picker as color (of player 2).
     */
    @FXML public void savePlayer2Info(){
        /*Create a new instance of the player with the custom values*/
        player2 = new Player(
                player2NameFIeld.getText(),
                player2ColorPicker.getValue()
        );

        /*Changing the save button's text to notify that datas have been saved*/
        saveButton2.setText("Saved");
    }

    /*Validating the datas in the fields before going to the next screen (the actual board)*/
    @FXML public Button startGameButton;

    /**Checks if all the infos are good before going to the next screen.
     * These are the conditions for the info to be valid:
     * - datas are saved
     * - players' NAMES and COLORS are DIFFERENT
     * - no FIELD is left EMPTY
     * - COLORS must be DIFFERENT from WHITE(too difficult to see) and WHITESMOKE(the default color)
     * If none of these conditions are met, a warning will be displayed with the message:
     * "Players MUST have different pseudos and colors (which must be different from white)."
     * If all is good, we go to the next screen
     * @param actionEvent: When we click on the "Start Game" button
     */
    @FXML public void confirmChoicesAndStartGame(ActionEvent actionEvent) {
        //TODO: customize in a css file the appearance of the notification frame.

        //Before doing anything, we check that everything has been saved. That is to make ourselves sure that the players' instances get the values in the fields.
        if(saveButton1.getText().equals("Saved") && saveButton2.getText().equals("Saved")){
            if(
                    player1NameField.getText().isBlank() || player1NameField.getText().isEmpty() ||
                            player2NameFIeld.getText().isBlank() || player2NameFIeld.getText().isEmpty() ||
                            player1NameField.getText().equals(player2NameFIeld.getText()) ||
                            player1ColorPicker.getValue().equals(player2ColorPicker.getValue()) ||
                            player1ColorPicker.getValue().equals(Color.WHITE) ||
                            player2ColorPicker.getValue().equals(Color.WHITE) ||
                            player1ColorPicker.getValue().equals(Color.WHITESMOKE) ||
                            player2ColorPicker.getValue().equals(Color.WHITESMOKE)
            ){
                /*Infos are either incomplete or incorrect. We show a notif and don't go to the next screen.*/
                Notifications errorNotif = Notifications.create();
                errorNotif.title("Incorrect info");
                errorNotif.text("Please review and re-submit the info you provided.\n" +
                        "Players MUST have DIFFERENT pseudos and colors (which must be different from WHITE.");
                errorNotif.showError();
                errorNotif.position(Pos.CENTER);

            }else{

                /*Everything's okay; we show a notification to say it's okay. Then we move on the last screen*/
                Notifications validNotif = Notifications.create()
                        .title("Info saved")
                        .text("Players' info successfully saved.\n You can start playing!");
                validNotif.showInformation();

                /*setting the 3rd screen visible and the others invisible*/
                root.getChildren().get(0).setVisible(false);
                root.getChildren().get(1).setVisible(false);
                root.getChildren().get(2).setVisible(true);

                initiateGameScreenDetails();

            }

        }else {

            /*Infos aren't even saved*/
            Notifications notSavedNotif = Notifications.create();
            notSavedNotif.title("Players' info not saved");
            notSavedNotif.text("Please save your datas before going further!");
            notSavedNotif.showWarning();
            notSavedNotif.position(Pos.CENTER);
        }

    }

    /*--------------------------------------------BRIDGE BETWEEN CHOICE AND GAME SCREENS--------------------------------------------*/

    @FXML public VBox gameScreen;

    /*Elements of the game screen*/

    /*Player 1's infos*/
    @FXML public Label player1NameLabel;
    @FXML public Circle player1ColorMarker;

    /*Player 2's infos*/
    @FXML public Label player2NameLabel;
    @FXML public Circle player2ColorMarker;

    /*Time played counting that stays at the top center of the screen*/
    @FXML public Label timePlayed;

    @FXML public Label currentPlayerLabel;

    public Player currentPlayer;


    /**Triggers when the "Start Game" button of the previous screen (choice screen) is clicked on.
     * It initializes the game screen details with each player's name and color.
     * It also sets player 1 as the one who starts the game; this is important as a reference when changing players(and colors) in the game.
     */

    long gameStartTime;
    public void initiateGameScreenDetails(){
        /*Updating player 1's details*/
        player1NameLabel.setText(player1.getName());
        player1ColorMarker.setFill(player1.getColor());

        /*Updating player 2's details*/
        player2NameLabel.setText(player2.getName());
        player2ColorMarker.setFill(player2.getColor());

        //Setting the first player as the current one to start playing
        currentPlayer = player1;
        currentPlayerLabel.setText(currentPlayer.getName() + "'s turn");

        /*Setting the start time*/
        gameStartTime = System.currentTimeMillis();

        /*Working on the timer that counts the time*/
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long duration = System.currentTimeMillis() - gameStartTime;
                timePlayed.setText(String.format(
                        "%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(duration),
                        TimeUnit.MILLISECONDS.toMinutes(duration) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(duration) % 60
                ));
            }
        };
        timer.start();


        /*TODO: Setting the clock to count dynamically*/

    }

    /*--------------------------------------------GAME LOGIC--------------------------------------------*/

    /*The board itself*/
    @FXML public GridPane board;

    /**This is where the game occurs. This colors the board with the current player's turn's color.
     * We check if it's possible to play. It's impossible to play when:
     * - the board is FULL, or
     * - there is a winner, or
     * - the circle is already colored
     * @param mouseEvent The click of the mouse on the gridpane(board).
     */
    @FXML public void colorCircleIfPossible(MouseEvent mouseEvent) {

        /*Getting the picked object*/
        Object object = mouseEvent.getPickResult().getIntersectedNode();

        /*Moving only when it's a circle*/
        if(object instanceof Circle circle){


            /*Plays only when the current node is empty; that means its color is the default one, WHITESMOKE
            * That means the circle is free, empty*/
            if(circle.getFill() == Color.WHITESMOKE){

                /*We already set the current player to be player 1 when we clicked on the "Start Game"
                 * Now, we change the color based on the current player's. When it's done, we set the current player to player 2, and reciproquely.
                 * We make sure that the 2nd condition is in the else block, otherwise it will also color the circle with the second color.
                 * That would mean the clicked circle will always have only one color, player 2's color.*/
                if(currentPlayer == player1){

                    /*We also make sure that the adversary doesn't already color the selected circle.
                     * So that player will still have the turn because it only changes when the circle is successfully colored*/
                    if(circle.getFill() != player2.getColor()) {
                        /*The player plays*/
                        circle.setFill(player1.getColor());
                        /*Now we must check if the player is a winner*/
                        notifyGameState(board,player1.getColor());
                        /*setting the next player as the current one*/
                        currentPlayer = player2;
                        /*Writing at the bottom whose turn it is now that we've played*/
                        currentPlayerLabel.setText(currentPlayer.getName() + "'s turn");
                    }

                } else if(currentPlayer == player2) {
                    if(circle.getFill() != player1.getColor()){
                        circle.setFill(player2.getColor());
                        notifyGameState(board,player2.getColor());
                        /*Writing at the bottom whose turn it is now that we've played*/
                        currentPlayer = player1;
                        /*Writing at the bottom whose turn it is now that we've played*/
                        currentPlayerLabel.setText(currentPlayer.getName() + "'s turn");
                    }
                }

            } else{

                if(circle.getFill() == player2.getColor()){
                    Notifications alreadyColored = Notifications.create()
                            .title("Place already occupied by " + player2.getName())
                            .text("Please choose another FREE place as soon as possible!");
                    alreadyColored.showError();

                } else if(circle.getFill() == player1.getColor()) {
                    Notifications alreadyColored = Notifications.create()
                            .title("Place already occupied by " + player1.getName())
                            .text("Please choose another FREE place as soon as possible!");
                    alreadyColored.showError();
                }
            }
        }
    }

    /**
     * Shows a notification if someone wins the game, or if the board is full and no winner (draw).
     * It uses a color to ease the analysis, as we must check things about a player when he plays. Not that we must verify for both players each time one of them plays.
     * @param board: The current state of the board.
     * @param color: The current color to check if this is the winner or not
     */
    private void notifyGameState(GridPane board, Color color) {

        if(winnerExists(board, color)){

            /*When someone wins the game*/
            Notifications gameWon = Notifications.create()
                    .title("Winner is :" + (player1.isWinner() ? player1.getName() : player2.getName())) //returns the name of the player who is the winner
                    .text("Congratulations!");
            gameWon.showInformation();

            System.out.println("Winner is :" + (player1.isWinner() ? player1.getName() : player2.getName()));
        }

        if(boardIsFull(board) && !winnerExists(board, color)){
            /*When the board is full and there is no winner. Otherwise, it will show again if it happens that a player wins and just at the last pawn makes the board full*/
            Notifications boardFullNotif = Notifications.create()
                    .title("Draw !")
                    .text("It's a draw, there is no winner for this seession.");
            boardFullNotif.showConfirm();
        }
    }

    /**
     * This is where we see if the last player that plays wins.
     * It's the case when, as its name suggests it(4 in row), any player manages to align 4 of his 'pawns' in any direction: horizontally, vertically, or diagonally.
     * It also updates the winner's isWinner attribute (see Player's class's code).
     * @param board: The current state of the board.
     * @param currentColor: Color of the last player.
     * @return true, if a winner exists, falso if not.
     */
    private boolean winnerExists(GridPane board, final Color currentColor) {
        /*The numbers representing the streaks of the same colors*/
        int rowStreak;
        int colStreak;

        /*Now, we can work on the matrix instead of directly using the board(grid pane).
        Here we work on the column and the rows.*/
        Color nodeColor;
        for(int i = 0; i < board.getRowCount(); i++) {

            /*Firstly, we check the rows-----------------------------------------------------------------------.
            At each column of the current row, we must check if the next 3 columns (so 4 circles in total) are colored the same way.
            * So when the remaining columns are numbered less than 3 (because we include the current node in the counting), it will always be impossible to meet the condition.*/
            for(int j = 0; j < board.getColumnCount() ; j++) {

                /*Getting and initializing the current node's color for comparizon*/
                nodeColor = (Color) (Objects.requireNonNull(getChildrenByIndex(i, j))).getFill();

                /*Color MUST be different from the default color of the circle, which is WHITESMOKE (#F5F5F5)
                And it must only be the current color, the player that plays at the instant (meaning the latest one)
                * TODO: to make the code more generic, we can declare a final constant that will be the circles' default color*/
                if(nodeColor == currentColor){

                    /*Initializing the counting to 1, because we include the current*/
                    rowStreak = 1;

                    /*Counting the next columns if the colors match the current circle's color. As soon as it's different, we move onto the next column.*/
                    for(int k = j+1; k < board.getColumnCount() ; k++) {
                        if((Objects.requireNonNull(getChildrenByIndex(i, k)).getFill() == currentColor)) {
                            rowStreak++;
                            /*If the condition is met for a winner*/
                            if(rowStreak == 4) {
                                System.out.println("Row Streak 4 for colour: " + currentColor);
                                setWinner(currentColor);
                                return true;
                            }
                        }else{
                            /*Whatever color it is, we only care about the current color (the latest player's). As such, we must reinitialize the counting to 0 whenever we come across any other color, be it the other player's (because it's not his turn) or an empty node's (WHITESMOKE)*/
                            rowStreak = 0;
                        }
                    }
                }
            }
        }

        /*Now, we check the columns, using the same logic as when working with the rows.*/
        for(int i = 0; i < board.getRowCount() ; i++) {
             /* We use the same process as for the rows; the difference is that we check here the rows.*/
            for(int j = 0; j < board.getRowCount(); j++) {

                /*Getting and initializing the current node's color for comparizon*/
                nodeColor = (Color) (Objects.requireNonNull(getChildrenByIndex(j, i))).getFill();

                //IMPORTANT !
                if(nodeColor == currentColor){

                    /*Initializing the counting to 1, because we include the current*/
                    colStreak = 1;

                    /*Counting the next rows if the colors match the current circle's color. As soon as it's different, we move onto the next column.*/
                    for(int k = j+1; k < board.getRowCount() ; k++) {
                        if((Objects.requireNonNull(getChildrenByIndex(k, i)).getFill() == currentColor)){
                            colStreak++;
                            /*If the condition is met for a winner*/
                            if(colStreak == 4){
                                System.out.println("Col Streak 4 for colour: " + currentColor);
                                setWinner(currentColor);
                                return true;
                            }
                        }else {
                            colStreak = 0;
                        }
                    }

                }

            }
        }

        /*Working on diagonals--------------------------------------------------
         Now we work on the diagonals..*/
        for(int i = 0; i <board.getRowCount(); i++) {
            for(int j = 0; j < board.getColumnCount(); j++) {
                /*Room for optimization:
                We'll go through every element, from row 1 to row 3, and column 1 to column 4. Because we need a set of at least 4-elements long.
                * We'll only do things as usual, as we don't need to go to all 4 directions at each element we get. And we can also see that, in this fashion, all possibilities are met.
                * At each element in this range, we'll go down back and forth.*/

                int count;
                nodeColor = (Color) (Objects.requireNonNull(getChildrenByIndex(i, j))).getFill();

                //Diagonal down in advance. We use the same logic as when going through the rows and columns
                if(nodeColor == currentColor){
                    System.out.println("Start column down front analysis for color " + nodeColor.toString() + ": " + i + "," + j);
                    count = 1;

                    /*Replacing for loops by while loops, and increasing simultaneously the indexes.
                    * Because the for loops go through all columns on a line, stopping the counting even when in the following row there is a color that must be counted.
                    * We want only one element per line and column. So both of the indexes must change together.*/

                    /*Initializing counters*/
                    int k = i+1;
                    int l = j+1;

                    /*Conditions to stay in the loop: indexes not out of bounds*/
                    while(k < board.getRowCount() && l < board.getColumnCount()){
                        if((Objects.requireNonNull(getChildrenByIndex(k, l))).getFill() == currentColor){
                            count++;
                            System.out.println("Count: " + count);
                            /*Increasing both line and column indexes each time we successfully count the color*/
                            k++;
                            l++;
                            if(count == 4){
                                setWinner(currentColor);
                                return true;
                            }
                        } else {
                            System.out.println("No color, break.\n-------------------------------");
                            /*Color different from what we search, break.*/
                            count = 0;
                            break;
                        }
                    }
                }

                /*Same logic with diagonal down, back. */
                //Diagonal down, back.
                nodeColor = (Color) (Objects.requireNonNull(getChildrenByIndex(i, j))).getFill();
                if(nodeColor == currentColor){
                    System.out.println("Start column down back analysis for color " + nodeColor.toString() + ": " + i + "," + j);
                    count = 1;

                    int k = i+1;
                    int l = j-1;

                    while(k < board.getRowCount() && l >= 0){
                        if((Objects.requireNonNull(getChildrenByIndex(k, l))).getFill() == currentColor){
                            count++;
                            System.out.println("Count: " + count);
                            k++;
                            l--;
                            if(count == 4){
                                setWinner(currentColor);
                                return true;
                            }
                        } else {
                            System.out.println("No color, break.\n-------------------------------");
                            count = 0;
                            break;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**This method checks the owner of the color, and then sets its attribute isWinner to 'true'.
     * @param currentColor: The color that mets the winning condition (4 in a row in any direction).
     */
    private void setWinner(Color currentColor) {

        if(player1.getColor() == currentColor){
            player1.setWinner(true);
        } else if(player2.getColor() == currentColor) {
            player2.setWinner(true);
        }
    }

    /**Returns the node in the gridpane. Usefull because the gridpane itself doesn't have a method for getting a node at a specified location.
     * The method calling must ensure that the parameters are not outbound.
     * @param rowIndex The row index of the wanted node.
     * @param columnIndex The column index of the wanted node.
     * @return The node(circle) if indexes are correct, null if they are outbound.
     */
    private Circle getChildrenByIndex(int rowIndex, int columnIndex) {

        /*The indexes of the board children, the circles*/
        int childRowIndex;
        int childColumnIndex;

        /*If indexes outbound, return null. Otherwise, we get the circle if the node is an instance of Circle*/
        if(rowIndex >= 0 && rowIndex < board.getRowCount() &&
                columnIndex >= 0 && columnIndex < board.getColumnCount()) {

            for(Node node : board.getChildren()) {
                if(node instanceof Circle circle) {

                    /*Getting the node's indexes*/
                    childColumnIndex = GridPane.getColumnIndex(circle);
                    childRowIndex = GridPane.getRowIndex(circle);

                    /*If node's indexes matches what we seek, return the node*/
                    if(childColumnIndex == columnIndex && childRowIndex == rowIndex) {
                        return circle;
                    }
                }
            }
        }
        return null;
    }

    /**Tells us if the board in its current state is full or not. It is when all circles are colored.
     * In other words, as soon as we see a circle that has the same color as the default one (WHITESMOKE or #F5F5F5),
     * we're sure the board isn't full.
     * @param board The current state of the board.
     * @return true if the board is full, false if not
     */
    private boolean boardIsFull(GridPane board) {
        boolean isFull = true;

        for(Node node : board.getChildren()){
            if(node instanceof Circle){
                if(((Circle)node).getFill() == Color.WHITESMOKE){
                    isFull = false;
                }
            }
        }

        return isFull;
    }
}