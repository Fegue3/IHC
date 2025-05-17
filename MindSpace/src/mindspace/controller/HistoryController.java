/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mindspace.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HistoryController implements Initializable {

    @FXML private Spinner<Integer> spinnerAno;
    @FXML private ComboBox<String> comboMes;
    @FXML private Button btnTimeMachine;
    @FXML private GridPane gridCalendario;
    @FXML private Button voltarButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spinnerAno.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 2100, LocalDate.now().getYear()));
        
        voltarButton.setOnMouseEntered(e ->
            voltarButton.setStyle("-fx-background-color: #4e8fb5; -fx-text-fill: white;")
        );

        voltarButton.setOnMouseExited(e ->
            voltarButton.setStyle("-fx-background-color: #A2D5F2; -fx-text-fill: white;")
        );
        
        btnTimeMachine.setOnMouseEntered(e ->
            btnTimeMachine.setStyle("-fx-background-color: #4e8fb5; -fx-text-fill: white;")
        );

        btnTimeMachine.setOnMouseExited(e ->
            btnTimeMachine.setStyle("-fx-background-color: #A2D5F2; -fx-text-fill: white;")
        );
        
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

        int ano = spinnerAno.getValue();
        int mes = comboMes.getSelectionModel().getSelectedIndex() + 1;
        YearMonth ym = YearMonth.of(ano, mes);
        LocalDate primeiroDia = ym.atDay(1);
        int diasNoMes = ym.lengthOfMonth();
        int diaSemana = primeiroDia.getDayOfWeek().getValue(); // 1 = Monday

        int coluna = diaSemana - 1;
        int linha = 1;

        for (int dia = 1; dia <= diasNoMes; dia++) {
            Label lbl = new Label(String.valueOf(dia));
            lbl.setPrefSize(60, 40);
            lbl.setStyle("-fx-alignment: center; -fx-border-color: gray;");

            if (LocalDate.now().equals(LocalDate.of(ano, mes, dia))) {
                lbl.setStyle(lbl.getStyle() + "-fx-background-color: #ffffcc;");
            }

            gridCalendario.add(lbl, coluna, linha);
            coluna++;
            if (coluna == 7) {
                coluna = 0;
                linha++;
            }
        }
    }
}
