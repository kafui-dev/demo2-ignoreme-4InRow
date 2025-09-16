package org.example.demo2ignoreme4inrow;

import javafx.scene.paint.Color;

/**The class that represent the players in the FOUR IN ROW Game.
 * @author Kafui Homevo
 * @version 29th April 2025
 */
public class Player {

    private String name;
    private Color color;
    private boolean isWinner;

    /**Constructor of the player class
     * @param name: Name of the player
     * @param color: Color of the player
     */
    public Player(String name, Color color){
        this.name = name;
        this.color = color;
        this.isWinner = false;
    }

    /**Constructor of the player class, without parameters.
     * Especially done to initiate the players at the beginning of the game.
     */
    public Player(){
        this.isWinner = false;
    }

    /**Returns the name of the player.
     * @return The name of the player
     */
    public String getName() {
        return name;
    }

    /**Sets the name of the player to the given parameter.
     * @param name The new name of the player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**Provides the color of the player.
     * @return The color chosen by the player
     */
    public Color getColor() {
        return color;
    }

    /**Sets the color for the player.
     * @param color The new color of the player.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**Tells us whether the player won the game.
     * @return true if this player won, false if not.
     */
    public boolean isWinner() {
        return isWinner;
    }

    /**Sets the winning state of the player.
     * @param winner The new winning state of the player.
     */
    public void setWinner(boolean winner) {
        isWinner = winner;
    }

}
