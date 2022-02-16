package org.openjfx.graphicalElements;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

/**
 * Cette classe abstraite construit les elements de l' UI pour le jeu
 *
 * @author Thalul -De Marcelin
 */
public abstract class UserInterface extends Pane {
    private Canvas canvas;

    /**
     * Instantiates a new User interface.
     *
     * @param canvas the canvas
     */
    public UserInterface(Canvas canvas) {
        this.canvas = canvas;
    }


    /**
     * Pour placer le canvas dans un groupe principal
     * Les sprites et autres elements de jeu seront dessinés sur le canvas
     *
     * @param root the root
     */
    public abstract void placeCanvas(Group root);

    /**
     * Pour creer l'interface de jeu
     */
    public abstract void create();

    /**
     * Met a jour l'interface grace aux sprites recus en parametres
     *
     * @param sprites the sprites
     */
    public abstract void updateUI(Sprite... sprites);

    /**
     * Gets canvas.
     *
     * @return the canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }
}
