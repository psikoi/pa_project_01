package graphics.custom;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SmallStyledButton extends Button {

    public boolean clickable;

    public SmallStyledButton(String text) {
        this(text, -1);
    }

    public SmallStyledButton(String text, int minSize) {
        setText(text);
        setFont(Font.font("Arial", 18));
        setTextFill(new Color(1, 1, 1, 1));

        if (minSize > -1) {
            setMinWidth(minSize);
        }

        setStyle("-fx-background-color: #932e32;");

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (clickable) {
                    setOpacity(0.6);
                }
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                setOpacity(1);
            }
        });

        setPadding(new Insets(10, 5, 10, 5));
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
        setDisable(!clickable);
    }

}
