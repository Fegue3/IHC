/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mindspace.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HistoryController implements Initializable {
    
    private final String FICHEIRO_DADOS = "src/mindspace/data/registos.txt";
    private Map<String, List<String>> mapaDados = new HashMap<>();

    
    @FXML private Spinner<Integer> spinnerAno;
    @FXML private ComboBox<String> comboMes;
    @FXML private Button btnTimeMachine;
    @FXML private GridPane gridCalendario;
    @FXML private Button voltarButton;
    
    private void carregarDadosFicheiro() {
    mapaDados.clear();

    try {
        List<String> linhas = Files.readAllLines(Paths.get(FICHEIRO_DADOS));
        String dataAtual = null;
        StringBuilder bloco = new StringBuilder();

        for (String linha : linhas) {
            if (linha.startsWith("Data: ")) {
                dataAtual = linha.substring(6).trim();
                bloco = new StringBuilder(); // nova entrada
            }
            if (linha.equals("---------------------------")) {
                if (dataAtual != null) {
                    mapaDados.computeIfAbsent(dataAtual, k -> new ArrayList<>()).add(bloco.toString().trim());
                }
                dataAtual = null;
                bloco = new StringBuilder();
            } else {
                bloco.append(linha).append("\n");
            }
        }
    } catch (IOException e) {
        System.out.println("Erro ao ler o ficheiro de dados: " + e.getMessage());
        }
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spinnerAno.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 2100, LocalDate.now().getYear()));
        
        for (Month month : Month.values()) {
            comboMes.getItems().add(month.getDisplayName(TextStyle.FULL, new Locale("pt", "PT")));
        }
        comboMes.getSelectionModel().select(LocalDate.now().getMonthValue() - 1);

        // Listeners
        spinnerAno.valueProperty().addListener((obs, oldVal, newVal) -> atualizarCalendario());
        comboMes.valueProperty().addListener((obs, oldVal, newVal) -> atualizarCalendario());
        
        btnTimeMachine.setOnAction(e -> {
            LocalDate hoje = LocalDate.now();
            spinnerAno.getValueFactory().setValue(hoje.getYear());
            comboMes.getSelectionModel().select(hoje.getMonthValue() - 1);
        });

        atualizarCalendario();
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
    
    private void atualizarCalendario() {
    gridCalendario.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

    carregarDadosFicheiro();

    int ano = spinnerAno.getValue();
    int mes = comboMes.getSelectionModel().getSelectedIndex() + 1;
    YearMonth ym = YearMonth.of(ano, mes);
    LocalDate primeiroDia = ym.atDay(1);
    int diasNoMes = ym.lengthOfMonth();
    int diaSemana = primeiroDia.getDayOfWeek().getValue(); // 1 = Monday

    int coluna = diaSemana - 1;
    int linha = 1;

    for (int dia = 1; dia <= diasNoMes; dia++) {
        LocalDate dataAtual = LocalDate.of(ano, mes, dia);
        String dataStr = String.format("%02d/%02d/%d", dia, mes, ano);

        Label lbl = new Label(String.valueOf(dia));
        lbl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        lbl.setFont(new Font(16));
        lbl.setStyle("-fx-alignment: center; -fx-border-color: gray;");

        // cor de fundo se tiver registo
        if (mapaDados.containsKey(dataStr)) {
            lbl.setStyle(lbl.getStyle() + "-fx-background-color: lightgreen;");
        }

        // destacar hoje
        if (LocalDate.now().equals(dataAtual)) {
            lbl.setStyle(lbl.getStyle() + "-fx-background-color: #ffffcc;");
        }

        // evento ao clicar no dia
        lbl.setOnMouseClicked(e -> {
            if (mapaDados.containsKey(dataStr)) {
                List<String> entradas = mapaDados.get(dataStr);
                String msg = String.join("\n---------------------\n", entradas);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registos do dia " + dataStr);
                alert.setHeaderText(null);
                alert.setContentText(msg);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sem registo");
                alert.setHeaderText(null);
                alert.setContentText("Não existe registo para " + dataStr + ".");
                alert.showAndWait();
            }
        });

        gridCalendario.add(lbl, coluna, linha);
        coluna++;
        if (coluna == 7) {
            coluna = 0;
            linha++;
        }
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
