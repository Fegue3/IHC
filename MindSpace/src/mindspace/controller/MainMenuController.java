/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mindspace.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sofia Costa
 */

public class MainMenuController implements Initializable {
    @FXML private ImageView imgRegistar;
    @FXML private ImageView imgCalendario;
    @FXML private ImageView imgAutocuidado;
    @FXML private ImageView imgEstatisticas;
    @FXML private VBox btnRegistar;
    @FXML private Label labelRegistar;
    

    @FXML
    private void abrirRegisto(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mindspace/view/RegisterView.fxml"));
            Parent root = loader.load();
            Stage novaJanela = new Stage();
            novaJanela.setTitle("Exercício de Respiração");
            novaJanela.setScene(new Scene(root));
            novaJanela.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void abrirCalendario() {
        trocarCena("History.fxml");
    }
    @FXML 
    private void abrirStats(){
        trocarCena("Stats.fxml");
    }
    
    @FXML 
    private void abrirSaude(){
        trocarCena("Saude.fxml");
    }
    
    private void trocarCena(String nomeFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mindspace/view/" + nomeFxml));
            Parent root = loader.load();
            Stage stage = (Stage) imgRegistar.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void aplicarHover(ImageView img) {
    img.setOnMouseEntered(e -> {
        img.setScaleX(1.1);
        img.setScaleY(1.1);
        img.setOpacity(1.0);
    });
    img.setOnMouseExited(e -> {
        img.setScaleX(1.0);
        img.setScaleY(1.0);
        img.setOpacity(0.85);
        img.setEffect(null);
    });
}
    private void aplicarHoverBotaoAdicionar() {
    imgRegistar.setOnMouseEntered(e -> {
        imgRegistar.setScaleX(1.1);
        imgRegistar.setScaleY(1.1);
        imgRegistar.setOpacity(1.0);
        labelRegistar.setVisible(true); // ← mostrar label
    });

    imgRegistar.setOnMouseExited(e -> {
        imgRegistar.setScaleX(1.0);
        imgRegistar.setScaleY(1.0);
        imgRegistar.setOpacity(0.9);
        imgRegistar.setEffect(null);
        labelRegistar.setVisible(false); // ← esconder label
    });
}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aplicarHover(imgCalendario);
        aplicarHover(imgAutocuidado);
        aplicarHover(imgEstatisticas);
        aplicarHoverBotaoAdicionar();
    }    
    
    
}
