package org.openjfx.graphicalElements;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Cette classe abstraite contient la definition de base pour un sprite dans le jeu, Chaque sprite gere ses collisions, affichage etc...
 *
 * @author Thalul -De Marcelin
 */
public abstract class Sprite {
    /**
     * The X.
     */
    protected double x; // Top left x
    /**
     * The Y.
     */
    protected double y; // Top left y
    private double vX;
    private double vY;
    private double width;
    private double height;

    // Pour le collisions
    private double centerX;
    private double centerY;
    private double halfWidth;
    private double halfHeight;

    private Image image;

    /**
     * Pour creer un sprite et l'afficher a l'ecran, en utilisant le gc
     *
     * @param x      The upper left x coordinate
     * @param y      The upper left y coordinate
     * @param width  La Largeur
     * @param height La Hauteur
     * @param gc     Le GraphicsContext sur lequel on dessine
     */
    public Sprite(double x, double y, double width, double height, GraphicsContext gc) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);

        this.setHalfWidth(this.getWidth() / 2);
        this.setHalfHeight(this.getHeight() / 2);

        this.setCenterX(this.getX() + this.getHalfWidth());
        this.setCenterY(this.getY() + this.getHalfHeight());

        this.render(gc);
    }


    /**
     * Pour determiner si le sprite est en contact avec un autre sprite
     *
     * @param other L'autre sprite
     * @return Si oui ou non, il y'a contact
     */
    public abstract boolean didCollideWith(Sprite other);


    /**
     * Pour determiner si le sprite est en contact avec le mur du haut
     *
     * @param canvas Le canvas avec lequel on devra travailler pour trouver les coordonner du haut
     * @return Si oui ou non, il y'a  contact
     */
    public abstract boolean didCollideWithTopWall(Canvas canvas);


    /**
     * Pour determiner si le sprite est en contact avec le mur du bas
     *
     * @param canvas Le canvas avec lequel on devrat travailler pour trouver les coordonner du bas
     * @return Si oui ou non, il y'a  contact
     */
    public abstract boolean didCollideWithBotWall(Canvas canvas);


    /**
     * Pour determiner si le sprite est en contact avec le mur gauche
     *
     * @param canvas Le canvas avec lequel on devrat travailler pour trouver les coordonner du mur gauche
     * @return Si oui ou non, il y'a  contact
     */
    public abstract boolean didCollideWithLeftWall(Canvas canvas);


    /**
     * Pour determiner si le sprite est en contact avec le mur droit
     *
     * @param canvas Le canvas avec lequel on devrat travailler pour trouver les coordonner du mur droit
     * @return Si oui ou non, il y'a  contact
     */
    public abstract boolean didCollideWithRightWall(Canvas canvas);


    /**
     * Pour dessiner le sprite en utilisant le gc
     *
     * @param gc Le graphicsContext odu Canvas.
     */
    public abstract void render(GraphicsContext gc);


    /**
     * Pour mettre a jour la position du sprite en tenant compte du temps
     *
     * @param time le temps
     */
    public void update(double time) {
        this.x += this.getvX() * time;
        this.y += this.getvY() * time;

        this.setCenterX(this.getX() + this.getHalfWidth());
        this.setCenterY(this.getY() + this.getHalfHeight());
    }


    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getvX() {
        return vX;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getvY() {
        return vY;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }


    /**
     * Gets half width.
     *
     * @return the half width
     */
    public double getHalfWidth() {
        return halfWidth;
    }

    /**
     * Gets half height.
     *
     * @return the half height
     */
    public double getHalfHeight() {
        return halfHeight;
    }


    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Sets x.
     *
     * @param vX the v x
     */
    public void setvX(double vX) {
        this.vX = vX;
    }

    /**
     * Sets y.
     *
     * @param vY the v y
     */
    public void setvY(double vY) {
        this.vY = vY;
    }

    /**
     * Sets width.
     *
     * @param width the width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Sets height.
     *
     * @param height the height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Sets center x.
     *
     * @param centerX the center x
     */
    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    /**
     * Sets center y.
     *
     * @param centerY the center y
     */
    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    /**
     * Sets half width.
     *
     * @param halfWidth the half width
     */
    public void setHalfWidth(double halfWidth) {
        this.halfWidth = halfWidth;
    }

    /**
     * Sets half height.
     *
     * @param halfHeight the half height
     */
    public void setHalfHeight(double halfHeight) {
        this.halfHeight = halfHeight;
    }
}
