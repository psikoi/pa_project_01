package graphics.views.menus;

import game.Main;
import game.factories.GameFactory;
import game.models.Game;
import game.models.GameDifficulty;
import graphics.presenters.GamePresenter;
import graphics.views.GameView;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import session.SessionManager;

public class DoubleLoginMenu extends HBox {

    public int loginActions;
    public int automaticLogin;

    public DoubleLoginMenu() {

        loginActions = 0;
        automaticLogin = 0;

        setSpacing(30);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #e6c990;");

        Label player1 = new Label("Jogador #1");
        player1.setFont(Font.font("Arial", 16));

        Label player2 = new Label("Jogador #2");
        player2.setFont(Font.font("Arial", 16));

        SingleLoginMenu leftMenu = new SingleLoginMenu();
        SingleLoginMenu rightMenu = new SingleLoginMenu();

        leftMenu.getLoginButton().setOnAction((event) -> {
            if (leftMenu.login()) {
                loginActions++;
                leftMenu.setReady();
                checkReady();
            } else {
                leftMenu.alertLoginFailure();
            }
        });

        rightMenu.getLoginButton().setOnAction((event) -> {
            if (rightMenu.login()) {
                loginActions++;
                rightMenu.setReady();
                checkReady();
            } else {
                rightMenu.alertLoginFailure();
            }
        });

        leftMenu.getSelectButton().setOnAction((event) -> {
            if (automaticLogin == 0) {
                automaticLogin++;
                loginActions++;
                leftMenu.setReady();
            } else {
                if (!checkSamePlayer(leftMenu, rightMenu)) {
                    loginActions++;
                    rightMenu.setReady();
                }
            }

            checkReady();
        });

        rightMenu.getSelectButton().setOnAction((event) -> {
            if (automaticLogin == 0) {
                automaticLogin++;
                loginActions++;
                rightMenu.setReady();
            } else {
                if (!checkSamePlayer(leftMenu, rightMenu)) {
                    loginActions++;
                    rightMenu.setReady();
                }
            }

            checkReady();
        });

        VBox left = new VBox(10);
        left.setAlignment(Pos.CENTER);

        VBox right = new VBox(10);
        right.setAlignment(Pos.CENTER);

        left.getChildren().add(player1);
        left.getChildren().add(leftMenu);

        right.getChildren().add(player2);
        right.getChildren().add(rightMenu);

        getChildren().add(left);
        getChildren().add(right);

    }

    private void checkReady() {

        if (loginActions >= 2 && SessionManager.getUserSessions().size() == 2) {
            startGame(new GameFactory().create(null));
        }

    }

    private boolean checkSamePlayer(SingleLoginMenu left, SingleLoginMenu right) {
        return left.getUserSelection().getSelectionModel().getSelectedIndex()
                == right.getUserSelection().getSelectionModel().getSelectedIndex();
    }

    private void startGame(Game game) {
        game.start();
        GamePresenter p = new GamePresenter(game, new GameView(game));
        Main.switchContent(p.getView());
    }

}
