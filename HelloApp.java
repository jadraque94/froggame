package org.openjdk.gamefrogger;


import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.*;

public class HelloApp extends Application {

    private AnimationTimer timer; // creer une page qui permet de mettre en place l'animation
    private Pane root; //on créé l'interface sur lequel on va faire le jeu
    private List<Node> cars = new ArrayList<>(); // création de la liste
    private Node frog;

    private Parent createContent() {
        root = new Pane();
        root.setStyle("-fx-background-color: black;");
        root.setPrefSize( 800, 600 );
        Rectangle rect= new Rectangle(800,260, BLUE);
        root.getChildren().add( rect );
        frog = initFrog();

        root.getChildren().add( frog );

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onupdate();
            }
        };
        timer.start();
        return root;

        /*on crée le fond du jeu donc ce sera un tableau de taille 800x600
        en fond noir et un en fond bleu pour l'eau et on ajoute une grenouille et des voitures et ensuite on va lancer
        avec AnimationTimer le jeu avec le start()
         */
    }



    private Node initFrog(){ // création de la grenouille taille 38x38 placer en bas au milieu du tableau selon l'axe X et Y
        Rectangle rect= new Rectangle(38,38, GREEN);
        rect.setTranslateY(600-39);
        rect.setTranslateX(800-439);
        return rect;
    }

    private Node spawnCar(){
        Rectangle rect= new Rectangle(40,40, RED); // création des voitures de taille 40x40 en rouge
        //Rectangle rect2= new Rectangle(80,40, RED);
        //Rectangle rect3= new Rectangle(120,40, RED);
        rect.setTranslateY((int)(Math.random()*7)*40+40*7 ); // on va envoyer 7 voitures en bas du tableau aléatoirement
        //rect2.setTranslateY((int)(Math.random()*14)*40 ); // on va envoyer maxi 14 voitures de taille 80x40 math.random compris entre 0 et 1
        //rect3.setTranslateY((int)(Math.random()*14)*40 ); // on va envoyer maxi 14 voitures de taille 120x40 math.random compris entre 0 et 1
        root.getChildren().add( rect ); //on ajoute la voiture dans l'interafce
        //root.getChildren().add( rect2 );
        //root.getChildren().add( rect3 );
        return rect;
    }

    private Node spawnCar1(){ // la même chose avec une voiture de taile 120x40 donc une voiture qui ressemble plus à un camion

        //Rectangle rect= new Rectangle(40,40, RED);
        //Rectangle rect2= new Rectangle(80,40, RED);
        Rectangle rect3= new Rectangle(120,40, RED);
        //rect.setTranslateY((int)(Math.random()*14)*40 ); // on va envoyer maxi 14 voitures de taille 40x40
        //rect2.setTranslateY((int)(Math.random()*14)*40 ); // on va envoyer maxi 14 voitures de taille 80x40 math.random compris entre 0 et 1
        rect3.setTranslateY((int)(Math.random()*5)*40+40*7 ); // on va envoyer maxi 14 voitures de taille 120x40 math.random compris entre 0 et 1
        //root.getChildren().add( rect );
        //root.getChildren().add( rect2 );
        root.getChildren().add( rect3 );
        return rect3;

    }


    private void onupdate(){
        for (Node car : cars)
            car.setTranslateX( car.getTranslateX()+8);
        if (Math.random()<0.03) { // contrôle la densité de voiture émise
            cars.add(spawnCar());
            cars.add(spawnCar1());
        }

        CheckState();
    }
     /* envoie des voitures et car.setTranslatexx permet de controler la vitesse des voitures
      */

    private void CheckState(){
        for (Node car :cars) {
            if (car.getBoundsInParent().intersects( frog.getBoundsInParent())){ /*
            Check la collision entre la voiture et la grenouille et renvoie la grenouille au départ en cas de collision
            */  frog.setTranslateX(800-439);
                frog.setTranslateY( 600-39 );
                return;
            }
        }
        if (frog.getTranslateY() <= 0){
            timer.stop(); // si la grenouille touche le haut de l'interface on a gagné
            String win ="Gagné";

            HBox hBox=new HBox(); //permet d'afficher le message Gagné au centre de l'image dès que la grenouille touche le haut et fini le jeu
            hBox.setTranslateX( 350 );
            hBox.setTranslateY( 250 );
            root.getChildren().add( hBox );
            for (int i =0;i<win.toCharArray().length ;i++){
                char letter = win.charAt( i );

                Text text = new Text(String.valueOf(letter));
                text.setFont( Font.font( 48 ) );
                text.setOpacity( 0 );

                hBox.getChildren().add( text );
                FadeTransition ft = new FadeTransition( Duration.seconds( 1 ), text);
                ft.setToValue( 1 );
                ft.setDelay( Duration.seconds( i*0.4 ) );
                ft.play();
            }

            }
        }



    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene( new Scene( createContent() ) );

        primaryStage.getScene().setOnKeyPressed( event ->{
            switch (event.getCode()){
                case UP:
                    frog.setTranslateY( frog.getTranslateY() -40 );
                    break;
                case DOWN:
                    frog.setTranslateY( frog.getTranslateY() +40 );

                    break;
                case RIGHT:
                    frog.setTranslateX( frog.getTranslateX() +40 );

                    break;
                case LEFT:
                    frog.setTranslateX( frog.getTranslateX() -40 );
                    break;
                default:
                    break;

            }
        } );
        /*
        Permet de contrôler avec les touches de contrôle droit gauche en haut et en bas, la grenouille
        pour se déplacer dans le jeu
         */

        primaryStage.show();
        primaryStage.setTitle( "Interface grenouille" );


    }

    public static void main(String[] args) {

        launch( args );
    }
}