package mindspace.controller;

import mindspace.model.EmotionEntry;
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
import java.time.LocalDate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class StatsController implements Initializable {

    @FXML
    private LineChart<String, Number> graficoEvolucao;

    @FXML
    private Button btnAlegria, btnTristeza, btnRaiva, voltarButton, btnDefault;
    @FXML
    private Label labelEmocaoPrincipalTitulo, labelDiasPositivos, labelEmocaoPrincipalValor, labelDiasPositivosValor, labelMediaTitulo, labelMediaValor;


    private final List<EmotionEntry> entradas = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarDados();
        mostrarGraficoGeral();
        atualizarEstatisticas();
        btnAlegria.setOnAction(e -> 
            mostrarEmocoes(Set.of("Radiante", "Feliz", "Tranquilo"), "Alegria"));

        btnTristeza.setOnAction(e -> 
            mostrarEmocoes(Set.of("Em baixo", "Sensível"), "Tristeza"));

        btnRaiva.setOnAction(e -> 
            mostrarEmocoes(Set.of("Confuso","Frustrado"), "Raiva"));

    }

    private void carregarDados() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try (BufferedReader reader = new BufferedReader(new FileReader("src/mindspace/data/registos.txt"))) {
            String linha;
            LocalDate data = null;
            String emocao = null;
            String nota = "";
            int pontos = 0;

            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("Data:")) {
                   data = LocalDate.parse(linha.replace("Data:", "").trim(), formatter);
                } else if (linha.startsWith("Emoção:")) {
                    emocao = linha.replace("Emoção:", "").trim();
                } else if (linha.startsWith("Nota:")) {
                    nota = linha.replace("Nota:", "").trim();
                } else if (linha.startsWith("Pontos:")) {
                    pontos = Integer.parseInt(linha.replace("Pontos:", "").trim());
                } else if (linha.startsWith("---") && data != null && emocao != null) {
                    entradas.add(new EmotionEntry(data, emocao, nota, pontos));
                    data = null;
                    emocao = null;
                    nota = "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            
    }

    private void mostrarGraficoGeral() {
    XYChart.Series<String, Number> serie = new XYChart.Series<>();
    serie.setName("Tendência emocional");

    // Lista auxiliar para guardar datas únicas
    List<LocalDate> datasUnicas = new ArrayList<>();

    // Recolher todas as datas únicas por ordem de aparecimento
    for (EmotionEntry e : entradas) {
        if (!datasUnicas.contains(e.getDate())) {
            datasUnicas.add(e.getDate());
        }
    }

    // Para cada data, encontrar o maior valor de pontos nessa data
    for (LocalDate data : datasUnicas) {
        int soma = 0;
        int contagem = 0;

        for (EmotionEntry e : entradas) {
            if (e.getDate().equals(data)) {
                soma += e.getPoints();
                contagem++;
            }
        }

        if (contagem > 0) {
            double media = (double) soma / contagem;
            serie.getData().add(new XYChart.Data<>(data.toString(), media));
        }
    }

    graficoEvolucao.getData().clear();
    graficoEvolucao.getData().add(serie);

    // Força o gráfico a mostrar valores negativos também
    NumberAxis eixoY = (NumberAxis) graficoEvolucao.getYAxis();
    eixoY.setAutoRanging(true);
    eixoY.setForceZeroInRange(false); // permite valores negativos
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

    for (EmotionEntry e : entradas) {
        String emocao = e.getEmotion();
        int pontos = e.getPoints();

        for (int i = 0; i < nomes.length; i++) {
            if (nomes[i].equals(emocao)) {
                contadores[i]++;
                totalEmocoes++;
                somaPontos += pontos;
                totalRegistos++;
                break;
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
    int percentagem = totalEmocoes > 0 ? (contadores[maxIndex] * 100 / totalEmocoes) : 0;

    labelEmocaoPrincipalTitulo.setText("Emoção Principal");
    labelEmocaoPrincipalValor.setText(maisFrequente + " (" + percentagem + "%)");

    labelDiasPositivos.setText("Emoções Registadas");
    labelDiasPositivosValor.setText(String.valueOf(totalEmocoes));

    labelMediaTitulo.setText("Média de Pontos");
    labelMediaValor.setText(totalRegistos > 0 ? String.format("%.1f", (double) somaPontos / totalRegistos) : "0");
}


    private void mostrarEmocoes(Set<String> emocoesFiltradas, String nomeSerie) {
    XYChart.Series<String, Number> serie = new XYChart.Series<>();
    serie.setName(nomeSerie);

    List<LocalDate> datasUnicas = new ArrayList<>();
    for (EmotionEntry e : entradas) {
        if (!datasUnicas.contains(e.getDate())) {
            datasUnicas.add(e.getDate());
        }
    }

    for (LocalDate data : datasUnicas) {
        int soma = 0, contagem = 0;
        for (EmotionEntry e : entradas) {
            if (e.getDate().equals(data) && emocoesFiltradas.contains(e.getEmotion())) {
                soma += e.getPoints();
                contagem++;
            }
        }

        if (contagem > 0) {
            double media = (double) soma / contagem;
            serie.getData().add(new XYChart.Data<>(data.toString(), media));
        }
    }

    graficoEvolucao.getData().clear();
    graficoEvolucao.getData().add(serie);

    NumberAxis eixoY = (NumberAxis) graficoEvolucao.getYAxis();
    eixoY.setAutoRanging(true);
    eixoY.setForceZeroInRange(false);

    CategoryAxis eixoX = (CategoryAxis) graficoEvolucao.getXAxis();
    eixoX.setTickLabelRotation(90);
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
    @FXML
    private void hoverAzul(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: #90C3D4; -fx-text-fill: #37474F; -fx-background-radius: 20;");
    }
    @FXML
    private void sairHoverAzul(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color:  #A6DCEF; -fx-text-fill: #37474F; -fx-background-radius: 20;");
    }
    @FXML
    private void hoverLavanda(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #C1A6F5; -fx-text-fill: #37474F; -fx-background-radius: 20;");
    }

    @FXML
    private void sairHoverLavanda(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #D5BFFF; -fx-text-fill: #37474F; -fx-background-radius: 20;");
    }

}
