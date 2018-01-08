package graphics.views.menus;

import game.Main;
import game.models.User;
import graphics.custom.SmallStyledButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import session.Authentication;
import session.SessionManager;

public class SingleLoginMenu extends VBox {

    SmallStyledButton loginButton;
    SmallStyledButton selectButton;
    
    TextField usernameField;
    PasswordField passwordField;
    
    ComboBox<String> userSelection;

    public SingleLoginMenu() {

        setSpacing(30);
        setStyle("-fx-background-color: #e6c990;");
        setAlignment(Pos.CENTER);

        DropShadow shadow = new DropShadow(7, Color.web("#d3b884"));
        shadow.setOffsetY(5);

        VBox loginContainer = new VBox(5);
        loginContainer.setPrefSize(240, 280);
        loginContainer.setMaxWidth(240);
        loginContainer.setStyle("-fx-background-color: #ededed;");
        loginContainer.setEffect(shadow);
        loginContainer.setPadding(new Insets(35));

        Label usernameLabel = new Label("Nome de utilizador:");
        usernameLabel.setFont(Font.font("Arial", 14));

        usernameField = new TextField();
        usernameField.setFocusTraversable(false);
        usernameField.setMinHeight(35);
        usernameField.setStyle("-fx-focus-color: #bebebe ; -fx-faint-focus-color: #bebebe ;");

        Label passwordLabel = new Label("Palavra passe:");
        passwordLabel.setFont(Font.font("Arial", 14));
        passwordLabel.setPadding(new Insets(15, 0, 0, 0));

        passwordField = new PasswordField();
        passwordField.setFocusTraversable(false);
        passwordField.setMinHeight(35);
        passwordField.setStyle("-fx-focus-color: #bebebe ; -fx-faint-focus-color: #bebebe ;");

        Pane spacer = new Pane();
        spacer.setPrefSize(20, 20);

        loginButton = new SmallStyledButton("Entrar", 180);
        loginButton.setOnAction((event) -> {

            if (login()) {
                Main.switchContent(new ComputerDifficultySelectionMenu());
            } else {
                alertLoginFailure();
            }

        });

        loginContainer.getChildren().add(usernameLabel);
        loginContainer.getChildren().add(usernameField);
        loginContainer.getChildren().add(passwordLabel);
        loginContainer.getChildren().add(passwordField);
        loginContainer.getChildren().add(spacer);
        loginContainer.getChildren().add(loginButton);

        VBox selectionContainer = new VBox(15);
        selectionContainer.setPrefSize(250, 200);
        selectionContainer.setMaxWidth(250);
        selectionContainer.setStyle("-fx-background-color: #ededed;");
        selectionContainer.setEffect(shadow);
        selectionContainer.setPadding(new Insets(20));
        selectionContainer.setAlignment(Pos.CENTER);

        Label selectionLabel = new Label("Já tem sessão iniciada?\nSelecione a sua conta abaixo.");
        selectionLabel.setFont(Font.font("Arial", 14));
        selectionLabel.setTextAlignment(TextAlignment.CENTER);

        userSelection = new ComboBox<>();
        if (SessionManager.getLoggedInUsers() != null) {
            for (User user : SessionManager.getLoggedInUsers()) {
                userSelection.getItems().add(user.getUsername());
            }
            userSelection.getSelectionModel().select(0);
        }
        userSelection.setMinSize(180, 35);
        userSelection.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        selectButton = new SmallStyledButton("Entrar", 180);
        selectButton.setOnAction((event) -> {

            User s = SessionManager.getLoggedInUsers().get(userSelection.getSelectionModel().getSelectedIndex());

            if (s != null) {
                Main.switchContent(new ComputerDifficultySelectionMenu());
            }

        });

        selectionContainer.getChildren().add(selectionLabel);
        selectionContainer.getChildren().add(userSelection);
        selectionContainer.getChildren().add(selectButton);

        getChildren().add(loginContainer);
        getChildren().add(selectionContainer);

    }

    public boolean login() {
        return Authentication.login(usernameField.getText(), passwordField.getText());
    }

    public void setReady() {

        loginButton.setText("Pronto!");
        loginButton.setClickable(false);
        loginButton.setStyle("-fx-background-color: #60925a;");

        selectButton.setText("Pronto!");
        selectButton.setClickable(false);
        selectButton.setStyle("-fx-background-color: #60925a;");

    }

    public void alertLoginFailure() {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Erro");
        alert.setHeaderText("Ocorreu um erro na autenticação");
        alert.setContentText("Tente novamente, confirmando que escreveu a informação correcta.");

        alert.showAndWait();
    }

    public SmallStyledButton getLoginButton() {
        return loginButton;
    }

    public SmallStyledButton getSelectButton() {
        return selectButton;
    }

    public ComboBox<String> getUserSelection() {
        return userSelection;
    }
    
    

}
