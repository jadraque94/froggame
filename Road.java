package org.openjfx.road;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.openjfx.car.Car;
import org.openjfx.gameCommons.Main;
import org.openjfx.graphicalElements.Sprite;

import java.util.ArrayList;

/**
 * Cette classe represente une route dans le jeu, les voitures devront rouler sur une route(road)
 *
 * @author Thalul -De Marcelin
 */
public class Road extends Sprite {
    private int limit; // Nombre maximal de voiture sur la route

    /**
     * The constant WIDTH.
     */
    public static final double WIDTH = Main.CANVAS_WIDTH;
    /**
     * The constant HEIGHT.
     */
    public static final double HEIGHT = Car.DIM_HEIGHT - 5;
    private static final Color COLOR = Color.GRAY;

    private ArrayList<Car> cars;

    /**
     * Instantiates a new Road.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param gc     the gc
     * @param limit  the limit
     */
    public Road(double x, double y, double width, double height, GraphicsContext gc, int limit) {
        super(x, y, width, height, gc);
        setLimit(limit);
        cars = new ArrayList<Car>();
    }

    @Override
    public boolean didCollideWith(Sprite other) {
        return false;
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
        return false;
    }

    @Override
    public boolean didCollideWithRightWall(Canvas canvas) {
        return false;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(COLOR);
        gc.fillRect(getX(), getY(), WIDTH, HEIGHT);
    }

    /**
     * Add car.
     *
     * @param car the car
     */
    public void addCar(Car car) {
        this.cars.add(car);
    }

    /**
     * Gets limit.
     *
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Sets limit.
     *
     * @param limit the limit
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * Gets cars.
     *
     * @return the cars
     */
    public ArrayList<Car> getCars() {
        return cars;
    }
}
