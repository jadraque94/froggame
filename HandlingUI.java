package org.openjfx.gameCommons;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.openjfx.graphicalElements.Sprite;
import org.openjfx.graphicalElements.UserInterface;

/**
 * Cette classe herite de UserInterface, pour gerer l'interface de jeu
 *
 * @author Thalul -De Marcelin
 */
public class HandlingUI extends UserInterface {
    private StackPane base;

    private BorderPane stats;
    private HBox statsPane;
    private Label froggerStats;
    private Label carStats;

    private BorderPane uiPane;

    private int level;
    private Label levelLabel;

    private StackPane gameOverPane;

    /**
     * Instantiates a new Handling ui.
     *
     * @param canvas the canvas
     */
    public HandlingUI(Canvas canvas) {
        super(canvas);
        base = new StackPane();

        uiPane = new BorderPane(canvas);
        uiPane.setId("uiPane");

        level = 1;
        levelLabel = new Label("LEVEL: " + level);
        levelLabel.setId("levelLabel");

        gameOverPane = new StackPane();
        gameOverPane.setStyle("-fx-background-color: transparent;");
    }

    @Override
    public void create() {
    }

    @Override
    public void placeCanvas(Group root) {
        BorderPane.setAlignment(levelLabel, Pos.CENTER);
        uiPane.setTop(levelLabel);

        StackPane.setAlignment(getCanvas(), Pos.CENTER);
        StackPane.setAlignment(gameOverPane, Pos.CENTER);
        base.getChildren().addAll(uiPane, getCanvas(), gameOverPane);

        root.getChildren().add(base);
    }

    @Override
    public void updateUI(Sprite... sprites) {
        //TODO: Faire des updates de stats en cours de jeux....
    }


    /**
     * Augmenter le niveau(Level) dans le jeu
     */
    public void increaseLevel() {
        level++;
        levelLabel.setText("LEVEL: " + level);
    }

    /**
     * Pour creer le GAME OVER
     */
    public void createGameOver() {
        Text gameOverText = new Text("GAME OVER!\n Space to start again");
        gameOverText.setTextAlignment(TextAlignment.CENTER);
        StackPane.setAlignment(gameOverText, Pos.CENTER);
        gameOverPane.getChildren().add(gameOverText);
        gameOverText.setFont(Font.font("Impact", FontWeight.BOLD, 60));

        // Make gameOverPane and base transparent
        gameOverPane.setStyle("-fx-background-color: rgba(2, 12, 160, 0.73);");
        base.setStyle("-fx-background-color: rgba(250, 10, 129, 0.73);");
    }

    /**
     * Delete l'affichage du game over
     */
    public void removeGameOver() {
        gameOverPane.getChildren().remove(0);
        gameOverPane.setStyle("-fx-background-color: transparent;");
        base.setStyle("-fx-background-color: transparent;");
    }
}
