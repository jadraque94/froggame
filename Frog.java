package org.openjfx.frog;

import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.openjfx.car.Car;
import org.openjfx.graphicalElements.Sprite;

/**
 * Cette classe represente une grenouille dans le jeu, une frog peu avoir une image et
 * peut bouger dans toute les direction de degree UNIT
 *
 * @author Thalul -De Marcelin
 */
public class Frog extends Sprite {
    private static final double UNIT = 30;
    /**
     * The constant DIM_WIDTH.
     */
    public static final double DIM_WIDTH = 60;
    /**
     * The constant DIM_HEIGHT.
     */
    public static final double DIM_HEIGHT = 54;

    private static final double RENDER_WIDTH = 40;
    private static final double RENDER_HEIGHT = 55;

    private double xHitbox;
    private double yHitbox;
    private double widthHitbox;
    private double heightHitbox;

    private static final double XHITBOX_OFFSET = 10;
    private static final double YHITBOX_OFFSET = 5;
    private static final double WIDTH_HITBOX_OFFSET = 20;
    private static final double HEIGHT_HITBOX_OFFSET = 15;

    /**
     * Instantiates a new Frog.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param gc     the gc
     */
    public Frog(double x, double y, double width, double height, GraphicsContext gc) {
        super(x, y, width, height, gc);


        setHalfWidth(getWidth() / 2);
        setHalfHeight(getHeight() / 2);

        createHitbox(getX() + XHITBOX_OFFSET, getY() + YHITBOX_OFFSET, getWidth() - WIDTH_HITBOX_OFFSET,
                getHeight() - HEIGHT_HITBOX_OFFSET);
    }

    /**
     * Pour creer une boite, afin de mieux gerer les collisions
     *
     * @param x
     *            Les coordonees du haut gauche x
     * @param y
     *            Les coordonees du haut gauche y
     * @param width
     *            La Largeur du boite
     * @param height
     *            La Hauteur du boite
     */
    private void createHitbox(double x, double y, double width, double height) {
        setXHitbox(x);
        setYHitbox(y);
        setWidthHitbox(width);
        setHeightHitbox(height);
    }

    @Override
    public boolean didCollideWith(Sprite other) {
        if (other instanceof Car) {
            double carX = ((Car) other).getXHitbox();
            double carY = ((Car) other).getYHitbox();
            double carHeight = ((Car) o3.3
        +6
                    .ther).getHeightHitbox();
            double carWidth = ((Car) other).getWidthHitbox();

            double x = this.getXHitbox();
            double y = this.getYHitbox();
            double height = this.getHeightHitbox();
            double width = this.getWidthHitbox();

            boolean xCond1 = x + width >= carX;
            boolean xCond2 = x + width <= carX + carWidth;
            boolean xCond3 = x >= carX;
            boolean xCond4 = x <= carX + carWidth;
            boolean collidedX = (xCond1 && xCond2) || (xCond3 && xCond4);
            // System.out.println("X: " + collidedX);
            boolean yCond1 = y + height >= carY;
            boolean yCond2 = y + height <= carY + carHeight;
            boolean yCond3 = y >= carY;
            boolean yCond4 = y <= carY + carHeight;
            boolean collidedY = (yCond1 && yCond2) || (yCond3 && yCond4);
            // System.out.println("Y: " + collidedY);

            // System.out.println("frog: " +(collidedX && collidedY));

            return collidedX && collidedY;
        }
        return false;
    }

    @Override
    public void render(GraphicsContext gc) {
        // gc.drawImage(getImage(), getX(), getY(), RENDER_WIDTH, RENDER_HEIGHT);
        gc.setFill(Color.BLUE);
        gc.fillRect(getX(), getY(), RENDER_WIDTH, RENDER_HEIGHT);
    }

    @Override
    public void update(double time) {
        this.x += this.getvX() * time;
        this.y += this.getvY() * time;

        this.setCenterX(this.getX() + this.getHalfWidth());
        this.setCenterY(this.getY() + this.getHalfHeight());

        setXHitbox(getX() + XHITBOX_OFFSET);
        setYHitbox(getY() + YHITBOX_OFFSET);
    }

    /**
     * Pour bouger la grenouille de degree UNIT en haut
     */
    public void moveUp() {
        this.setY(this.getY() - UNIT);
    }

    /**
     * Pour bouger la grenouille de degree UNIT a gauche
     */
    public void moveLeft() {
        this.setX(this.getX() - UNIT);
    }

    /**
     * Pour bouger la grenouille de degree UNIT a droite
     */
    public void moveRight() {
        this.setX(this.getX() + UNIT);
    }

    /**
     * Pour bouger la grenouille de degree UNIT en bas
     */
    public void moveDown() {
        this.setY(this.getY() + UNIT);
    }

    @Override
    public boolean didCollideWithTopWall(Canvas canvas) {
        Bounds bounds = canvas.getBoundsInLocal();

        return getYHitbox() <= bounds.getMinY();
    }

    @Override
    public boolean didCollideWithBotWall(Canvas canvas) {
        Bounds bounds = canvas.getBoundsInLocal();

        return getYHitbox() + getHeightHitbox() >= bounds.getMaxY();
    }

    @Override
    public boolean didCollideWithLeftWall(Canvas canvas) {
        Bounds bounds = canvas.getBoundsInLocal();

        return getXHitbox() <= bounds.getMinX();
    }

    @Override
    public boolean didCollideWithRightWall(Canvas canvas) {
        Bounds bounds = canvas.getBoundsInLocal();

        return getXHitbox() + getWidthHitbox() >= bounds.getMaxX();
    }

    /**
     * Sets x hitbox.
     *
     * @param x the x
     */
    public void setXHitbox(double x) {
        xHitbox = x;
    }

    /**
     * Sets y hitbox.
     *
     * @param y the y
     */
    public void setYHitbox(double y) {
        yHitbox = y;
    }

    /**
     * Sets width hitbox.
     *
     * @param width the width
     */
    public void setWidthHitbox(double width) {
        widthHitbox = width;
    }

    /**
     * Sets height hitbox.
     *
     * @param height the height
     */
    public void setHeightHitbox(double height) {
        heightHitbox = height;
    }

    /**
     * Gets x hitbox.
     *
     * @return the x hitbox
     */
    public double getXHitbox() {
        return xHitbox;
    }

    /**
     * Gets y hitbox.
     *
     * @return the y hitbox
     */
    public double getYHitbox() {
        return yHitbox;
    }

    /**
     * Gets width hitbox.
     *
     * @return the width hitbox
     */
    public double getWidthHitbox() {
        return widthHitbox;
    }

    /**
     * Gets height hitbox.
     *
     * @return the height hitbox
     */
    public double getHeightHitbox() {
        return heightHitbox;
    }
}
