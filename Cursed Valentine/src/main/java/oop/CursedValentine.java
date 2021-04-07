package oop;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;       //jäänuk
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.*;
import java.awt.event.*;                    //jäänukid erinevatest proovimistest, ei tea mida vaja siin
import java.awt.event.InputEvent;
import java.util.HashSet;
import java.util.Set;

/*-------------------------------LUGEMIST-----------------------------------------------------------------------------------------------------------------------
https://docs.oracle.com/javafx/2/events/convenience_methods.htm       -     Example 2-2, eriti see Circle meetod, meie tegelane peaks olema see circle
https://www.tutorialspoint.com/how-to-move-translate-a-javafx-node-from-one-position-to-another     -       Midagi siit ei kasutanud, lihtsalt jäi silma
https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyListener.html#keyPressed(java.awt.event.KeyEvent)       -       Siin see API on
https://gist.github.com/jewelsea/8321740                                                    -       alternatiiv foo meetodile, tundub hella difiicult
https://stackoverflow.com/questions/37393516/two-keys-pressed-at-the-same-time-in-java      -       see on palju loogilisem
---------------------------------------------------------------------------------------------------------------------------------------------------------------*/

public class CursedValentine extends Application implements KeyListener {               //igavene jama selle KeyListeneriga, peab olema abstraktne APIst vaadates
                                                                                        //kuid nägin ka näiteid, et sai ka teisiti tehtud

    public void keyPressed(KeyEvent e) {
        System.out.println("pressed");
    }
    public void keyReleased(KeyEvent e) {
        System.out.println("released");
    }
    public void keyTyped(KeyEvent e) {
        System.out.println("typed");
    }

    private static final int      KEYBOARD_MOVEMENT_DELTA = 5;                          //need kaks rida liikumise transition speed
    private static final Duration TRANSLATE_DURATION      = Duration.seconds(0.25);


    private final Set<Character> Keyspressed = new HashSet<Character>();                //HashSet on kõikide vajutuste salvestamiseks ja siis see foo
                                                                                        //itereerib läbi kõigi jooksvalt

    /*                                                                                  //TODO
    public void keyPressed(KeyEvent e){                                                 //PÕHIPROBLEEM!!! Java ei luba mitut keypressi korraga defaultis,
        pressed.add(e.getKeyChar());                                                    //vaja workaroundi selle jaoks, (Keyspressed.size() > 1) teeb seda
        if (Keyspressed.size() > 1) {                                                   //aga loogika võetud mujalt...
            //size is greator than one which means you                                  //foo on mitte miski - https://stackoverflow.com/questions/20208787/what-does-foo-mean
            //have pressed more than one key.
            //now your set contains all pressed keys. iterate it and fine out which was pressed.
            foo(Keyspressed);
        }
    }
    */

    public void foo(Set<Character> Keyspressed){
        boolean Apressed = false;
        boolean Wpressed = false;
        boolean Spressed = false;
        boolean Dpressed = false;


        for(Character e : Keyspressed){                                                  //workaround on siin aga ei braininud ära kuidas saada kokku see kõik
            if(e==KeyEvent.VK_A){                                                        //https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html
                Apressed = true;                                                         //mis mida extendib ja implementib, mby vaja midagi ise Javas sättida
            }else if(e==KeyEvent.VK_S){                                                  //KeyEvent, InputEvent, KeyAdapter, KeyListener... pea hakkab kiirelt valutama
                Spressed = true;                                                         //
            }else if(e==KeyEvent.VK_D){                                                  //need VK_klahvid on kõik KeyEvent klassis olemas aga miks ta klassi näeb aga meetodeid mitte
                Dpressed = true;                                                         //kommenteeri foo välja kui tahad, et töötaks
            }else if(e==KeyEvent.VK_W){
                Wpressed = true;
            }
        }
        if(Apressed && Spressed){                                                        //loogika tundub hea selge siin, võiks siiani ära saada
            //your logic
        }

    }


    private void moveCircleOnKeyPress(Scene scene, final Circle circle, final TranslateTransition transition) {         //circle = tegelane TODO
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:    circle.setCenterY(circle.getCenterY() - KEYBOARD_MOVEMENT_DELTA); break;    //nooled ei tööta -.-
                case RIGHT: circle.setCenterX(circle.getCenterX() + KEYBOARD_MOVEMENT_DELTA); break;
                case DOWN:  circle.setCenterY(circle.getCenterY() + KEYBOARD_MOVEMENT_DELTA); break;
                case LEFT:  circle.setCenterX(circle.getCenterX() - KEYBOARD_MOVEMENT_DELTA); break;
                case W: {
                        circle.setCenterY(circle.getCenterY() - KEYBOARD_MOVEMENT_DELTA);
                        //circle.setCenterX(circle.getCenterX() + KEYBOARD_MOVEMENT_DELTA);
                        } break;                                                                        //proovisin algul siin lahendada dbl keypressi,
                case D: circle.setCenterX(circle.getCenterX() + KEYBOARD_MOVEMENT_DELTA); break;        //see case süsteem on suht mono :/, ei saa case W && D
                case S:  circle.setCenterY(circle.getCenterY() + KEYBOARD_MOVEMENT_DELTA); break;
                case A:  circle.setCenterX(circle.getCenterX() - KEYBOARD_MOVEMENT_DELTA); break;

            }
        });
    }


    private void moveCircleOnMousePress(Scene scene, final Circle circle, final TranslateTransition transition) {           //hiire meetod
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
                if (!event.isControlDown()) {                                                   //smooth transition, mitte nii smooth tho, mby arendada kui vaja
                    circle.setCenterX(event.getSceneX());
                    circle.setCenterY(event.getSceneY());
                } else {
                    transition.setToX(event.getSceneX() - circle.getCenterX());                 //exact movement
                    transition.setToY(event.getSceneY() - circle.getCenterY());
                    transition.playFromStart();
                }
            }
        });
    }

    private TranslateTransition createTranslateTransition(final Circle circle) {
        final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, circle);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent t) {
                circle.setCenterX(circle.getTranslateX() + circle.getCenterX());
                circle.setCenterY(circle.getTranslateY() + circle.getCenterY());
                circle.setTranslateX(0);
                circle.setTranslateY(0);
            }
        });
        return transition;
    }

    private Label createInstructions() {                                                           //jäänuk näitest, ebaoluline, Label tekstiväli lihtsalt
        Label instructions = new Label(
                "Use the arrow keys to move the circle in small increments\n" +
                        "Click the mouse to move the circle to a given location\n" +
                        "Ctrl + Click the mouse to slowly translate the circle to a given location"
        );
        instructions.setTextFill(Color.FORESTGREEN);
        return instructions;
    }



    private Circle createCircle() {                                                                 //TEGELANE TODO
        final Circle circle = new Circle(200, 150, 50, Color.BLUEVIOLET);
        circle.setOpacity(0.7);
        return circle;
    }

    @Override
    public void start(Stage peaLava) throws Exception {

        Group kogu = new Group();                                               //peagrupp? not the best i guess

        Button btn = new Button();
        btn.setLayoutX(100);
        btn.setLayoutY(80);
        btn.setText("Hello World");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Hello World");
            }
        });
        kogu.getChildren().add(btn);

        Group poligonid = new Group();                                          //poly gruppe vaja niikuinii
        Polygon sild = new Polygon();
        sild.getPoints().addAll(5.0, 100.0,
                245.0, 100.0,
                245.0, 110.0,
                215.0, 110.0,
                225.0, 140.0,
                200.0, 140.0,
                180.0, 110.0,
                70.0, 110.0,
                50.0, 140.0,
                25.0, 140.0,
                25.0, 110.0,
                5.0, 110.0);
        sild.setFill(Color.DARKGREEN);
        poligonid.getChildren().add(sild);

        kogu.getChildren().add(poligonid);

        kogu.setOnKeyPressed(new EventHandler<KeyEvent>() {                     //esialgne katse midagi liikuma saada ise, liigub küll aga ainult ühe korra
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.W) {
                    System.out.println(ke.getText());
                    System.out.println("Key Pressed: " + ke.getText());
                    sild.setTranslateX(50);
                }

            }
        });

        kogu.setOnKeyPressed(e -> {                                             //lambda seiklused, enamus nendest meetoditest on lamdaga
            if (e.getCode() == KeyCode.A) {                                     //lahendatavad nagu IntelliJ pakub
                System.out.println("A key was pressed");
            }
        });


        Circle circle = createCircle();
        Group tegelane = new Group(createInstructions(), circle);               //praegu on see tegelane siis create Instructions + circle aga Label seisab
        TranslateTransition transition = createTranslateTransition(circle);     //paigal, sest need pressed meetodit rakenduvad ainult circle peale
                                                                                //see final teema pole vist meie projekti jaoks hea või ma ei tea TODO

        kogu.getChildren().add(tegelane);

        Scene stseen = new Scene(kogu, 1000, 1000, Color.SNOW);

        moveCircleOnKeyPress(stseen, circle, transition);                       //originaalis polnud klahvide puhul transitioni argumenti meetodis, ju ma üritasin midagi
        moveCircleOnMousePress(stseen, circle, transition);

        peaLava.setScene(stseen);
        peaLava.show();
    }

/*                                                                  //prolly pole seda siia vaja, kas miinusest kinni või delete, niisama jätsin praegu
Example 2-2 Sample Event Handlers for Mouse Events

final Circle circle = new Circle(radius, Color.RED);

circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
    public void handle(MouseEvent me) {
        System.out.println("Mouse entered");
    }
});

circle.setOnMouseExited(new EventHandler<MouseEvent>() {
    public void handle(MouseEvent me) {
        System.out.println("Mouse exited");
    }
});

circle.setOnMousePressed(new EventHandler<MouseEvent>() {
    public void handle(MouseEvent me) {
        System.out.println("Mouse pressed");
    }
});

Example 2-3 Sample Event Handlers for Keyboard Events

final TextField textBox = new TextField();
textBox.setPromptText("Write here");

textBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
    public void handle(KeyEvent ke) {
        System.out.println("Key Pressed: " + ke.getText());
    }
});

textBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
    public void handle(KeyEvent ke) {
        System.out.println("Key Released: " + ke.getText());
    }
});
*/

    public static void main(String[] args) {
        launch(args);
    }
}
