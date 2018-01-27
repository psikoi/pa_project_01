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
public class ComputerStatistics extends VBox{
    private BackButton back;
    private PieChart pieChart;
    private Label timePlayedText;
    private Label timePlayed;
    
    public ComputerStatistics(String username, String difficulty){
        setSpacing(10);

        setStyle("-fx-background-color: #e6c990;");
        setAlignment(Pos.CENTER);
        
        timePlayedText = new Label();
        timePlayedText.setAlignment(Pos.CENTER);
        timePlayedText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        timePlayedText.setText("Tempo jogado:");
        
        timePlayed = new Label();
        timePlayed.setAlignment(Pos.CENTER);
        timePlayed.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        makePieChart(difficulty);
        
        back = new BackButton(new Runnable() {
            @Override
            public void run() {
                Main.switchContent(new ComputerStatisticsOptions(username));
            }
        });
        
        
        HBox topHbox = new HBox();
        topHbox.getChildren().add(back);
        topHbox.setMaxHeight(20);
        topHbox.setTranslateY(-67);
        
        pieChart.setTranslateY(-67);
        
        getChildren().addAll(topHbox, pieChart, timePlayedText, timePlayed);
    }
    
    private void makePieChart(String difficulty){
        Machine machine = DataHandler.getMachine();
        
        if(difficulty.equalsIgnoreCase("easy")){
            ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Jogos jogados", machine.getEasyGamesPlayed()),
                new PieChart.Data("Vitórias", machine.getEasyVictories()),
                new PieChart.Data("Derrotas", machine.getEasyLosses()));
            
                pieChart = new PieChart(pieChartData);
                pieChart.setTitle("Estatisticas Dificuldade Fácil");
                
                timePlayed.setText(TimeUtils.getTimePlayed(machine.getEasyTimePlayed()));
                
        }else if(difficulty.equalsIgnoreCase("hard")){
            ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Jogos jogados", machine.getHardGamesPlayed()),
                new PieChart.Data("Vitórias", machine.getHardVictories()),
                new PieChart.Data("Derrotas", machine.getHardLosses()));
            
                pieChart = new PieChart(pieChartData);
                pieChart.setTitle("Estatisticas Dificuldade Difícil");
                
                timePlayed.setText(TimeUtils.getTimePlayed(machine.getHardTimePlayed()));
                
        }
    }
}
