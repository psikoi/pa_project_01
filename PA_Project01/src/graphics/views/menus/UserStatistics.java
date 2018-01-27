/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics.views.menus;

import utils.TimeUtils;
import data.DataHandler;
import game.Main;
import game.models.Machine;
import game.models.User;
import graphics.custom.BackButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Tiago
 */
public class UserStatistics extends VBox {

    private BackButton back;
    private PieChart pieChart;
    private Label timePlayedText;
    private Label timePlayed;
    private Label gamesPlayed;

    public UserStatistics(String username, String difficulty) {
        setSpacing(10);

        setStyle("-fx-background-color: #e6c990;");
        setAlignment(Pos.CENTER);
        
        gamesPlayed = new Label();
        gamesPlayed.setAlignment(Pos.CENTER);
        gamesPlayed.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        timePlayedText = new Label();
        timePlayedText.setAlignment(Pos.CENTER);
        timePlayedText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        timePlayedText.setText("Tempo jogado:");
        
        timePlayed = new Label();
        timePlayed.setAlignment(Pos.CENTER);
        timePlayed.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        makePieChart(username, difficulty);

        back = new BackButton(new Runnable() {
            @Override
            public void run() {
                Main.switchContent(new UserStatisticsOptions(username));
            }
        });

        HBox topHbox = new HBox();
        topHbox.getChildren().add(back);
        topHbox.setMaxHeight(20);
        topHbox.setTranslateY(-52);

        pieChart.setTranslateY(-52);
                
        getChildren().addAll(topHbox, pieChart, gamesPlayed, timePlayedText, timePlayed);
    }

    private void makePieChart(String username, String difficulty) {
        User user = DataHandler.selectUser(username);
        
        if (difficulty.equalsIgnoreCase("easy")) {
            ObservableList<PieChart.Data> pieChartData
                    = FXCollections.observableArrayList(
                            new PieChart.Data("Vitórias", user.getEasyVictories()),
                            new PieChart.Data("Derrotas", user.getEasyLosses()));

            pieChart = new PieChart(pieChartData);
            pieChart.setTitle("Estatisticas Dificuldade Fácil");
            
            timePlayed.setText(TimeUtils.getTimePlayed(user.getEasyTimePlayed()));
            gamesPlayed.setText("Jogos jogados: " + user.getEasyGamesPlayed());
            
        } else if (difficulty.equalsIgnoreCase("hard")) {
            ObservableList<PieChart.Data> pieChartData
                    = FXCollections.observableArrayList(
                            new PieChart.Data("Vitórias", user.getHardVictories()),
                            new PieChart.Data("Derrotas", user.getHardLosses()));

            pieChart = new PieChart(pieChartData);
            pieChart.setTitle("Estatisticas Dificuldade Difícil");
            
            timePlayed.setText(TimeUtils.getTimePlayed(user.getHardTimePlayed()));
            gamesPlayed.setText("Jogos jogados: " + user.getHardGamesPlayed());
            
        } else if (difficulty.equalsIgnoreCase("player")) {
            ObservableList<PieChart.Data> pieChartData
                    = FXCollections.observableArrayList(
                            new PieChart.Data("Vitórias", user.getPVPVictories()),
                            new PieChart.Data("Derrotas", user.getPVPLosses()));

            pieChart = new PieChart(pieChartData);
            pieChart.setTitle("Estatisticas Jogador vs Jogador");
            
            timePlayed.setText(TimeUtils.getTimePlayed(user.getPVPTimePlayed()));
            gamesPlayed.setText("Jogos jogados: " + user.getPVPGamesPlayed());
            
        }
    }

}
