/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics.views.menus;

import game.Main;
import graphics.custom.BackButton;
import graphics.custom.SmallStyledButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import session.Authentication;

/**
 *
 * @author Tiago
 */
public class RegisterMenu extends VBox {

    private BackButton back;

    private TextField usernameField;
    private PasswordField passwordField;
    private TextField emailField;

    SmallStyledButton registerButton;

    public RegisterMenu() {
        setSpacing(30);
        setStyle("-fx-background-color: #e6c990;");
        setAlignment(Pos.CENTER);

        DropShadow shadow = new DropShadow(7, Color.web("#d3b884"));
        shadow.setOffsetY(5);

        VBox registerContainer = new VBox(5);
        registerContainer.setPrefSize(240, 373);
        registerContainer.setMaxWidth(240);
        registerContainer.setStyle("-fx-background-color: #ededed;");
        registerContainer.setEffect(shadow);
        registerContainer.setPadding(new Insets(35));

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

        Label emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font("Arial", 14));
        emailLabel.setPadding(new Insets(15, 0, 0, 0));

        emailField = new TextField();
        emailField.setFocusTraversable(false);
        emailField.setMinHeight(35);
        emailField.setStyle("-fx-focus-color: #bebebe ; -fx-faint-focus-color: #bebebe ;");

        registerButton = new SmallStyledButton("Registar", 180);
        registerButton.setOnAction((event) -> {

            if (register()) {
                Main.switchContent(new HomeScreenMenu());
            }
            

        });
        
        back = new BackButton(new Runnable() {
            @Override
            public void run() {
                Main.switchContent(new HomeScreenMenu());
            }
        });
        
        HBox topHbox = new HBox();
        topHbox.getChildren().add(back);
        topHbox.setMaxHeight(20);
        topHbox.setTranslateY(-103);
        topHbox.setTranslateX(0);
        
        Pane spacer = new Pane();
        spacer.setPrefSize(20, 20);

        registerContainer.setTranslateY(-50);
        
        registerContainer.getChildren().addAll(usernameLabel, usernameField,
                                              passwordLabel, passwordField,
                                              emailLabel, emailField,
                                              spacer, registerButton);

        getChildren().addAll(topHbox, registerContainer);
    }
    
    public boolean register() {
         try{
             Authentication.register(usernameField.getText(), passwordField.getText(), emailField.getText());
             return true;
         }catch(RuntimeException e){
             alertRegisterFailure(e.getMessage());
             return false;
         }
    }
    
    public void alertRegisterFailure(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erro");
        alert.setHeaderText("Ocorreu um erro no registo");
        alert.setContentText(message);

        alert.showAndWait();
    }
}
