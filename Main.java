    package org.openjfx.gameCommons;

    import javafx.animation.AnimationTimer;
    import javafx.application.Application;
    import javafx.event.EventHandler;
    import javafx.scene.Group;
    import javafx.scene.Scene;
    import javafx.scene.canvas.Canvas;
    import javafx.scene.canvas.GraphicsContext;
    import javafx.scene.input.KeyEvent;
    import javafx.stage.Stage;
    import org.openjfx.car.Car;
    import org.openjfx.frog.Frog;
    import org.openjfx.road.Road;

    import java.util.ArrayList;
    import java.util.HashSet;
    import java.util.Random;
    import java.util.Set;


    /**
     * Cette classe est la classe principal pour gerer le lancement du jeu
     *
     * @author Thalul -De Marcelin
     */
    public class Main extends Application {

        /**
         * The constant CANVAS_WIDTH.
         */
        public static final double CANVAS_WIDTH = 600;
        /**
         * The constant CANVAS_HEIGHT.
         */
        public static final double CANVAS_HEIGHT = 720;
        private Canvas canvas;
        private GraphicsContext gc;

        private Frog frog;
        private static final double FROG_DIM_WIDTH = Frog.DIM_WIDTH;
        private static final double FROG_DIM_HEIGHT =Frog.DIM_HEIGHT;
        private static final double FROG_SPAWN_X = CANVAS_WIDTH / 2 - (FROG_DIM_WIDTH / 2);
        private static final double FROG_SPAWN_Y = CANVAS_HEIGHT - FROG_DIM_WIDTH;

        private ArrayList<Car> cars = new ArrayList<Car>();
        private static final String CAR_LEFT_NAME = "LeftFacing";
        private static final String CAR_RIGHT_NAME = "RightFacing";
        private static final double CAR_DIM_HEIGHT = Car.DIM_HEIGHT;
        private static final double CAR_DIM_WIDTH = Car.DIM_WIDTH;

        private static final double ELAPSED_TIME_SPEED = 1;

        private Set<String> pressedKeys = new HashSet<String>();

        private HandlingUI handlingUI;

        private int numCars = 5;
        private static final double LEVEL_EDGE = 60; // Min y coordinate where cars cannot spawn
        private static final double SAFEZONE = 630; // Max y coordinate where cars cannot spawn

        // private ArrayList<Track> roads = new ArrayList<Track>();
        private Road[] roads;
        private int limit = 4;

        private boolean isGameOver = false;

        private KeyPressHandler keyPressHandler = new KeyPressHandler();
        private KeyReleasedHandler keyReleasedHandler = new KeyReleasedHandler();

        @Override
        public void start(Stage primaryStage) throws Exception {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            Group root = new Group();
            Scene scene = new Scene(root);

            canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
            gc = canvas.getGraphicsContext2D();

            // Set-up UI
            handlingUI = new HandlingUI(canvas);
            handlingUI.placeCanvas(root);

            primaryStage.setScene(scene);

            System.out.println("Here");

            canvas.setFocusTraversable(true);
            canvas.setOnKeyPressed(keyPressHandler);
            canvas.setOnKeyReleased(keyReleasedHandler);

            spawn(gc, 5);

            new AnimationTimer() {

                @Override
                public void handle(long now) {
                    if(frog.didCollideWithBotWall(canvas))
                        frog.setY(CANVAS_HEIGHT - FROG_DIM_HEIGHT);

                    if(frog.didCollideWithLeftWall(canvas))
                        frog.setX(0);

                    if(frog.didCollideWithRightWall(canvas))
                        frog.setX(CANVAS_WIDTH - FROG_DIM_WIDTH);

                    if (frog.didCollideWithTopWall(canvas)) {
                        handlingUI.increaseLevel();

                        numCars += 2;

                        gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

                        roads = null;
                        cars = new ArrayList<Car>();
                        frog = null;

                        spawn(gc, numCars);
                    }

                    frog.update(ELAPSED_TIME_SPEED);

                    for (int i = 0; i < cars.size(); i++) {
                        Car car = cars.get(i);

                        if (car.didCollideWithLeftWall(canvas)) {
                            car.setX(CANVAS_WIDTH);
                        } else if (car.didCollideWithRightWall(canvas)) {
                            car.setX(0 - car.getWidth());
                        }

                        if ((car.didCollideWith(frog) || frog.didCollideWith(car)) && !isGameOver) {
                            handlingUI.createGameOver();
                            isGameOver = true;
                        }

                        car.update(ELAPSED_TIME_SPEED);
                    }

                    gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

                    for (int i = 0; i < roads.length; i++) {
                        Road road = roads[i];
                        if (road != null)
                            road.render(gc);
                    }

                    frog.render(gc);

                    for (int i = 0; i < cars.size(); i++) {
                        cars.get(i).render(gc);
                    }

                }

            }.start();

            primaryStage.show();
        }

        /**
         * The entry point of application.
         *
         * @param args the input arguments
         */
        public static void main(String[] args) {
            launch(args);
        }

        /**
         * Pour generer tous les sprites.
         *
         * @param gc
         *            Le Context graphique pour dessiner
         */
        private void spawn(GraphicsContext gc, int numCars) {
            // Spawning frog
            frog = new Frog(FROG_SPAWN_X, FROG_SPAWN_Y, FROG_DIM_WIDTH,FROG_DIM_HEIGHT, gc);

            // Get possible spawns for a car
            double[] xSpawns = getSpawns(CAR_DIM_WIDTH, 0, CANVAS_WIDTH - CAR_DIM_WIDTH);
            double[] ySpawns = getSpawns(CAR_DIM_HEIGHT, LEVEL_EDGE, SAFEZONE);

            // Array is for determining where roads get spawned
            boolean[] trackSpawnedHere = new boolean[ySpawns.length];
            roads = new Road[ySpawns.length];

            // Start spawning cars
            for (int i = 0; i < numCars; i++) {
                Random r = new Random();

                // Get a random xSpawn and ySpawn
                int xSpawnInd = getRandomIndex(xSpawns);
                double xSpawn = xSpawns[xSpawnInd];
                int ySpawnInd = getRandomIndex(ySpawns);
                double ySpawn = ySpawns[ySpawnInd];

                Car car = (r.nextBoolean() ? spawnLeftCar(gc, xSpawn, ySpawn) : spawnRightCar(gc, xSpawn, ySpawn));

                // Spawning roads
                if (!trackSpawnedHere[ySpawnInd]) {
                    Road track = new Road(0, ySpawn + 7.5, Road.WIDTH, Road.HEIGHT, gc, limit);
                    track.addCar(car);

                    roads[ySpawnInd] = track;
                    trackSpawnedHere[ySpawnInd] = true;
                } else {
                    Road road = roads[ySpawnInd];
                    Car trackCar = road.getCars().get(0);

                    // Make cars go in same direction
                    if (!(car.getName().equals(trackCar.getName()))) {
                        car = (trackCar.getName().equals(CAR_LEFT_NAME) ? spawnLeftCar(gc, xSpawn, ySpawn)
                                : spawnRightCar(gc, xSpawn, ySpawn));
                    }

                    // Reposition car if in same spawn as trackCar
                    if (car.didCollideWith(trackCar)) {
                        car.setX(trackCar.getX() + CAR_DIM_WIDTH);
                    }

                    car.setvX(trackCar.getvX());
                }

                cars.add(car);
            }
        }

        /**
         * Pour Generer un index aleatoire
         *
         * @param array
         *            Un tableau
         * @return Un index aleatoire dans le tableau
         */
        private int getRandomIndex(double[] array) {
            Random random = new Random();
            int ind = random.nextInt(array.length);
            return ind;
        }

        /**
         * Retourne un array qui contient tous les voitures generer
         *
         * @param increase
         *            La valeur qu'on devra augmenter les spawns
         * @param min
         *            La valeur minimal pour laquel les generations devront etre superierur
         * @param max
         *            la valeur maxiaml pour laquel les generations devront etre inferieur
         * @return un array contenant tous les generations possible
         */
        private double[] getSpawns(double increase, double min, double max) {
            double[] spawns = new double[(int) ((max - min) / increase)];
            double ySpawn = min;

            for (int i = 0; i < spawns.length; i++) {
                spawns[i] = ySpawn;
                ySpawn += increase;
            }

            return spawns;
        }

        /**
         * Genere une voiture partant de la gauche.
         *
         * @param gc
         *            Le GraphicsContext sur lequel on dessine.
         */
        private Car spawnLeftCar(GraphicsContext gc, double xSpawn, double ySpawn) {
            Car car = new Car(xSpawn, ySpawn, CAR_DIM_WIDTH, CAR_DIM_HEIGHT, gc, CAR_LEFT_NAME);
            car.moveLeft();
            return car;
        }

        /**
         * Genere une voiture partant de la droite.
         *
         * @param gc
         *            Le GraphicsContext sur lequel on dessine.
         */
        private Car spawnRightCar(GraphicsContext gc, double xSpawn, double ySpawn) {
            Car car = new Car(xSpawn, ySpawn, CAR_DIM_WIDTH, CAR_DIM_HEIGHT, gc, CAR_RIGHT_NAME);
            car.moveRight();
            return car;
        }

        /**
         * Pour generer un double aleatoire, entre min et max
         *
         * @param min
         *            Le plus petit nombre
         * @param max
         *            Le plus grand nombre
         * @return Le nombre generer
         */
        private double genRandomInRange(double min, double max) {
            Random random = new Random();
            double randomDouble = random.nextDouble();
            double result = min + (randomDouble * (max - min));
            return result;
        }

        // Pour controller la grenouille avec les touches
        private class KeyPressHandler implements EventHandler<KeyEvent> {

            @Override
            public void handle(KeyEvent event) {
                if (!isGameOver) {
                    switch (event.getCode()) {
                        case UP:
                            if (!pressedKeys.contains(event.getCode().toString()) && pressedKeys.size() == 0) {
                                frog.moveUp();
                                pressedKeys.add(event.getCode().toString());
                            }
                            break;
                        case LEFT:
                            if (!pressedKeys.contains(event.getCode().toString()) && pressedKeys.size() == 0) {
                                frog.moveLeft();
                                pressedKeys.add(event.getCode().toString());
                            }
                            break;
                        case DOWN:
                            if (!pressedKeys.contains(event.getCode().toString()) && pressedKeys.size() == 0) {
                                frog.moveDown();
                                pressedKeys.add(event.getCode().toString());
                            }
                            break;
                        case RIGHT:
                            if (!pressedKeys.contains(event.getCode().toString()) && pressedKeys.size() == 0) {
                                frog.moveRight();
                                pressedKeys.add(event.getCode().toString());
                            }
                            break;
                    }
                } else { // Restarting the level
                    switch (event.getCode()) {
                        case SPACE:
                            gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

                            roads = null;
                            cars = new ArrayList<Car>();
                            frog = null;

                            handlingUI.removeGameOver();

                            spawn(gc, numCars);

                            isGameOver = false;
                            break;
                    }
                }
            }
        }

        // Pour empecher a la grenouille de bouger indefinement
        private class KeyReleasedHandler implements EventHandler<KeyEvent> {

            @Override
            public void handle(KeyEvent event) {
                pressedKeys.remove(event.getCode().toString());
            }

        }

    }