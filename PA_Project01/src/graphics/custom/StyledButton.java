package graphics.custom;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class StyledButton extends Button {

    public StyledButton(String text) {
        this(text, -1);
    }

    public StyledButton(String text, int minSize) {
        setText(text);
        setFont(Font.font("Arial", FontWeight.BOLD, 25));
        setTextFill(new Color(1, 1, 1, 1));

        if (minSize > -1) {
            setMinWidth(minSize);
        }

        setStyle("-fx-background-color: #932e32;");

        setOnMouseEntered((event) -> {
            setOpacity(0.6);
        });

        setOnMouseExited((event) -> {
            setOpacity(1);
        });

        setPadding(new Insets(10, 15, 10, 15));
    }

}
