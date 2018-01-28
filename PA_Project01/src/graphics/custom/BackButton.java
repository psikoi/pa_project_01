package graphics.custom;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Tiago
 */
public class BackButton extends Button {

    public BackButton(Runnable action) {
        super();

        ImageView imageView = new ImageView(new Image("/images/icon_arrow_back.png"));
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

    private void run(Runnable action) {
        action.run();
    }

    public void changeAction(Runnable action) {
        setOnAction(e -> {
            run(action);
        });
    }
}
