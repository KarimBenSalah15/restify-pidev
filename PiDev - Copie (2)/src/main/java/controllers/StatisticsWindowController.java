package controllers;

import entities.Reclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsWindowController {
    @FXML
    private PieChart statisticsPieChart;
@FXML
List<Reclamation> reclamations;

    public void initialize() {
        System.out.println("StatisticsWindowController initialized.");

    }

    public void setReclamationsData(List<Reclamation> reclamations) {
        System.out.println("Setting reclamations data in StatisticsWindowController.");
        createPieChart(reclamations);
        System.out.println(reclamations);
    }

    private void createPieChart(List<Reclamation> reclamations) {
        Map<String, Long> typeCountMap = reclamations.stream()
                .collect(Collectors.groupingBy(Reclamation::getType, Collectors.counting()));

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        typeCountMap.forEach((type, count) -> {
            PieChart.Data data = new PieChart.Data(type, count);
            pieChartData.add(data);
        });

        // Call setData on the instance of PieChart (statisticsPieChart), not on the class (PieChart)
        statisticsPieChart.setData(pieChartData);
    }

}
