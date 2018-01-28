package graphics.custom;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SmallStyledButton extends Button {

    private boolean clickable;

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

        setOnMouseEntered((event) -> {
            if (clickable) {
                setOpacity(0.6);
            }
        });

        setOnMouseExited((event) -> {
            setOpacity(1);
        });

        setPadding(new Insets(10, 5, 10, 5));
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
        setDisable(!clickable);
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
