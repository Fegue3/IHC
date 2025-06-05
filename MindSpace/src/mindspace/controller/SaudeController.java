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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sofia Costa
 */
public class SaudeController implements Initializable {

    @FXML private Button btnVoltar, buttonMeditacao, btnRespiracao, btnSons;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    @FXML
    private void voltarParaMenuPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mindspace/view/MainMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void irParaRespiracao(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mindspace/view/BreathingEx.fxml"));
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
    private void irParaSonsRelaxantes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mindspace/view/RelaxingSounds.fxml"));
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
private void irParaMeditacao(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mindspace/view/Meditacao.fxml"));
        Parent root = loader.load();
        Stage novaJanela = new Stage();
        novaJanela.setTitle("Dicas de Meditação");
        novaJanela.setScene(new Scene(root));
        novaJanela.show();
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
