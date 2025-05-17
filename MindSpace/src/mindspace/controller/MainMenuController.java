/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mindspace.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sofia Costa
 */

public class MainMenuController implements Initializable {
    @FXML
    private ImageView imgRegistar;

    @FXML
    private ImageView imgCalendario;
    
    
    @FXML
    private void abrirRegisto() {
        trocarCena("RegisterView.fxml");
    }

    @FXML
    private void abrirCalendario() {
        trocarCena("History.fxml");
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    
}
