package mindspace.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.scene.control.Label;

public class StatsController implements Initializable {

    @FXML
    private LineChart<String, Number> graficoEvolucao;

    @FXML
    private Button btnAlegria, btnTristeza, btnRaiva, voltarButton;
    @FXML
    private Label labelEmocaoPrincipalTitulo, labelDiasPositivos, labelEmocaoPrincipalValor, labelDiasPositivosValor, labelMediaTitulo, labelMediaValor;


    private final Map<String, Map<String, Integer>> dadosPorData = new TreeMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarDados();
        mostrarGraficoGeral();
        atualizarEstatisticas();
        btnAlegria.setOnAction(e -> mostrarEmocao("Radiante"));
        btnTristeza.setOnAction(e -> mostrarEmocao("Em baixo"));
        btnRaiva.setOnAction(e -> mostrarEmocao("Frustrado"));
    }

    private void carregarDados() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/registos.txt"))) {
            String linha, data = null, emocao = null;
            int pontos = 0;

            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("Data:")) {
                    data = linha.replace("Data:", "").trim();
                } else if (linha.startsWith("Emoção:")) {
                    emocao = linha.replace("Emoção:", "").trim();
                } else if (linha.startsWith("Pontos:")) {
                    pontos = Integer.parseInt(linha.replace("Pontos:", "").trim());
                } else if (linha.startsWith("---") && data != null && emocao != null) {
                    dadosPorData.putIfAbsent(data, new HashMap<>());
                    dadosPorData.get(data).put(emocao, pontos);
                    data = null;
                    emocao = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarGraficoGeral() {
        XYChart.Series<String, Number> serieGeral = new XYChart.Series<>();
        serieGeral.setName("Tendência emocional");

        for (String data : dadosPorData.keySet()) {
            Map<String, Integer> emocoes = dadosPorData.get(data);
            int max = -1;
            for (int valor : emocoes.values()) {
                if (valor > max) max = valor;
            }
            serieGeral.getData().add(new XYChart.Data<>(data, max));
        }

        graficoEvolucao.getData().clear();
        graficoEvolucao.getData().add(serieGeral);
    }
    @FXML
    private void mostrarGraficoGeral(ActionEvent event) {
        mostrarGraficoGeral();
    }
    
    private void atualizarEstatisticas() {
        String[] nomes = {"Radiante", "Feliz", "Tranquilo", "Confuso", "Em baixo", "Sensível", "Frustrado"};
        int[] contadores = new int[nomes.length];
        int totalEmocoes = 0;
        int somaPontos = 0;
        int totalRegistos = 0;

        for (Map<String, Integer> emocoes : dadosPorData.values()) {
            for (String e : emocoes.keySet()) {
                for (int i = 0; i < nomes.length; i++) {
                    if (nomes[i].equals(e)) {
                        contadores[i]++;
                        totalEmocoes++;
                        somaPontos += emocoes.get(e);
                        totalRegistos++;
                        break;
                    }
                }
            }
        }

        int maxIndex = 0;
        for (int i = 1; i < contadores.length; i++) {
            if (contadores[i] > contadores[maxIndex]) {
                maxIndex = i;
            }
        }

        String maisFrequente = nomes[maxIndex];
        int percentagem = (int) ((contadores[maxIndex] * 100.0f) / totalEmocoes);

        labelEmocaoPrincipalTitulo.setText("Emoção Principal");
        labelEmocaoPrincipalValor.setText(maisFrequente + " (" + percentagem + "%)");

        labelDiasPositivos.setText("Emoções Registadas");
        labelDiasPositivosValor.setText(String.valueOf(totalEmocoes));

        labelMediaTitulo.setText("Média de Pontos");
        labelMediaValor.setText(totalRegistos > 0 ? String.format("%.1f", (double) somaPontos / totalRegistos) : "0");
    }

    private void mostrarEmocao(String emocao) {
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName(emocao);

        for (String data : dadosPorData.keySet()) {
            Integer pontos = dadosPorData.get(data).getOrDefault(emocao, 0);
            serie.getData().add(new XYChart.Data<>(data, pontos));
        }

        graficoEvolucao.getData().clear();
        graficoEvolucao.getData().add(serie);
    }

    @FXML
    private void voltarParaMenuPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mindspace/view/MainMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
