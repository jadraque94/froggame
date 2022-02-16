package org.openjfx.car;

import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.openjfx.frog.Frog;
import org.openjfx.graphicalElements.Sprite;

import java.util.Random;

/**
 * Cette classe represente une voiture dans le jeu, La voiture peut bouger dans deux direcction
 * et on peut aussi regler la vitesse
 *
 * @author Thalul -De Marcelin
 */
public class Car extends Sprite {
    /**
     * The constant DIM_WIDTH.
     */
    public static final double DIM_WIDTH = 120;
    /**
     * The constant DIM_HEIGHT.
     */
    public static final double DIM_HEIGHT = 60;
    private double maxCarSpeed = 8.0;
    private double minCarSpeed = 3.7;

    private double carSpeed;
    private Random random;

    private double xHitbox;
    private double yHitbox;
    private double widthHitbox;
    private double heightHitbox;

    private static final double XHITBOX_OFFSET_LEFT = 10;
    private static final double YHITBOX_OFFSET_LEFT = 15;
    private static final double WIDTH_HITBOX_OFFSET_LEFT = 20;
    private static final double HEIGHT_HITBOX_OFFSET_LEFT = 20;

    private static final double XHITBOX_OFFSET_RIGHT = 10;
    private static final double YHITBOX_OFFSET_RIGHT = 15;
    private static final double WIDTH_HITBOX_OFFSET_RIGHT = 20;
    private static final double HEIGHT_HITBOX_OFFSET_RIGHT = 0;

    private String name;

    /**
     * Instantiates a new Car.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param gc     the gc
     * @param name   the name
     */
    public Car(double x, double y, double width, double height, GraphicsContext gc, String name) {
        super(x, y, width, height, gc);
        random = new Random();
        this.carSpeed = genRandomInRange(minCarSpeed, maxCarSpeed);

        this.name = name;


        if (name.equals("LeftFacing"))
            createHitbox(getX() + XHITBOX_OFFSET_LEFT, getY() + YHITBOX_OFFSET_LEFT,
                    getWidth() - WIDTH_HITBOX_OFFSET_LEFT, getHeight() - HEIGHT_HITBOX_OFFSET_LEFT);
        else if (name.equals("RightFacing"))
            createHitbox(getX() + XHITBOX_OFFSET_RIGHT, getY() + YHITBOX_OFFSET_RIGHT,
                    getWidth() - WIDTH_HITBOX_OFFSET_RIGHT, getHeight() + HEIGHT_HITBOX_OFFSET_RIGHT);
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

    /**
     * Pour generer un nombre aleatoire entre min et max
     *
     * @param min
     *            Le plus petit
     * @param max
     *            Le plus grand
     * @return Le nombre generee
     */
    private double genRandomInRange(double min, double max) {
        double randomDouble = random.nextDouble();
        double result = min + (randomDouble * (max - min));
        return result;
    }

    @Override
    public boolean didCollideWith(Sprite other) {
        if (other instanceof Frog) {
            double frogX = ((Frog) other).getXHitbox();
            double frogY = ((Frog) other).getYHitbox();
            double frogHeight = ((Frog) other).getHeightHitbox();
            double frogWidth = ((Frog) other).getWidthHitbox();

            double x = this.getXHitbox();
            double y = this.getYHitbox();
            double height = this.getHeightHitbox();
            double width = this.getWidthHitbox();

            boolean xCond1 = x + width >= frogX;
            boolean xCond2 = x + width <= frogX + frogWidth;
            boolean xCond3 = x >= frogX;
            boolean xCond4 = x <= frogX + frogWidth;

            boolean collidedX = (xCond1 && xCond2) || (xCond3 && xCond4);

            boolean yCond1 = y + height >= frogY;
            boolean yCond2 = y + height <= frogY + frogHeight;
            boolean yCond3 = y >= frogY;
            boolean yCond4 = y <= frogY + frogHeight;
            boolean collidedY = (yCond1 && yCond2) || (yCond3 && yCond4);

            return collidedX && collidedY;
        } else if(other instanceof Car) {
            double carX = ((Car) other).getXHitbox();
            double carY = ((Car) other).getYHitbox();
            double carHeight = ((Car) other).getHeightHitbox();
            double frogWidth = ((Car) other).getWidthHitbox();

            double x = this.getXHitbox();
            double y = this.getYHitbox();
            double height = this.getHeightHitbox();
            double width = this.getWidthHitbox();

            boolean xCond1 = x + width >= carX;
            boolean xCond2 = x + width <= carX + frogWidth;
            boolean xCond3 = x >= carX;
            boolean xCond4 = x <= carX + frogWidth;

            boolean collidedX = (xCond1 && xCond2) || (xCond3 && xCond4);

            boolean yCond1 = y + height >= carY;
            boolean yCond2 = y + height <= carY + carHeight;
            boolean yCond3 = y >= carY;
            boolean yCond4 = y <= carY + carHeight;
            boolean collidedY = (yCond1 && yCond2) || (yCond3 && yCond4);

            return collidedX && collidedY;
        }
        return false;
    }

    @Override
    public void render(GraphicsContext gc) {
        // gc.drawImage(getImage(), getX(), getY(), DIM_WIDTH, DIM_HEIGHT);
        gc.setFill(Color.RED);
        gc.fillRect(getX(), getY(), DIM_WIDTH, DIM_HEIGHT);

    }

    @Override
    public void update(double time) {
        this.x += this.getvX() * time;
        this.y += this.getvY() * time;

        this.setCenterX(this.getX() + this.getHalfWidth());
        this.setCenterY(this.getY() + this.getHalfHeight());

        if (getName().equals("LeftFacing")) {
            setXHitbox(getX() + XHITBOX_OFFSET_LEFT);
            setYHitbox(getY() + YHITBOX_OFFSET_LEFT);
        } else if(getName().equals("RightFacing")) {
            setXHitbox(getX() + XHITBOX_OFFSET_RIGHT);
            setYHitbox(getY() + YHITBOX_OFFSET_RIGHT);
        }
    }

    @Override
    public boolean didCollideWithTopWall(Canvas canvas) {
        return false;
    }

    @Override
    public boolean didCollideWithBotWall(Canvas canvas) {
        return false;
    }

    @Override
    public boolean didCollideWithLeftWall(Canvas canvas) {
        Bounds bounds = canvas.getBoundsInLocal();
        return getX() + getWidth() < bounds.getMinX();
    }

    @Override
    public boolean didCollideWithRightWall(Canvas canvas) {
        Bounds bounds = canvas.getBoundsInLocal();
        return getX() > bounds.getMaxX();
    }

    /**
     * Pour bouger la voiture vers la gauche
     */
    public void moveLeft() {
        this.setvX(getCarSpeed() * -1);
    }

    /**
     * Pour bouger la voiture vers la droite
     */
    public void moveRight() {
        this.setvX(getCarSpeed());
    }

    /**
     * Gets car speed.
     *
     * @return the car speed
     */
    public double getCarSpeed() {
        return carSpeed;
    }

    /**
     * Sets car speed.
     *
     * @param carSpeed the car speed
     */
    public void setCarSpeed(double carSpeed) {
        this.carSpeed = carSpeed;
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

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
}
