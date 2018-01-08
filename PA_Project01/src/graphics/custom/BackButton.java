/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics.custom;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Tiago
 */
public class BackButton extends Button {

    private Image image;
    private ImageView imageView;

    public BackButton(Runnable action) {
        super();

        image = new Image("/images/icon_arrow_back.png");

        imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);

        setPrefHeight(60);
        setPrefWidth(50);
        setGraphic(imageView);
        setStyle("-fx-background-color: #932e32");

        setOnMouseEntered(e -> {
            setOpacity(0.6);
        });

        setOnMouseExited(e -> {
            setOpacity(1);
        });

        setOnAction(e -> {
            run(action);
        });
    }
    
    private void run(Runnable action){
        action.run();
    }
    
    public void changeAction(Runnable action){
        setOnAction(e -> {
            run(action);
        });
    }
}
