/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mindspace.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import mindspace.model.EmotionEntry;
import mindspace.model.EmotionManager;

/**
 * FXML Controller class
 *
 * @author fp226
 */

public class RegisterController implements Initializable {

    // Pane principal onde a cor de fundo muda
    @FXML private AnchorPane rootPane;
    @FXML private HBox emotionImagesDetails;
    // Zona que aparece após seleção
    @FXML private VBox emotionDetailsBox;
    
    @FXML private TextArea noteArea;
    @FXML private Button submitButton;
    @FXML private Label selectedEmotionLabel;
    @FXML private Label labelRadiante;
    @FXML private Label labelFeliz;
    @FXML private Label labelTranquilo;
    @FXML private Label labelConfuso;
    @FXML private Label labelEmBaixo;
    @FXML private Label labelSensivel;
    @FXML private Label labelFustrado;
    
    // Ícones (ImageViews) das emoções
    @FXML private ImageView imgRadiante, imgFeliz, imgTranquilo, imgConfuso, imgEmBaixo, imgSensivel, imgFrustrado;

    private String selectedEmotion = null;
    
    private Map<ImageView, TranslateTransition> activeAnimations = new HashMap<>();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    private void handleEmotionHover(MouseEvent event) {
        Object source = event.getSource();

        if (source == imgRadiante) {
            labelRadiante.setVisible(true);
            animateIcon(imgRadiante);
            updateEmotionStyle("Radiante");
        } else if (source == imgFeliz) {
            labelFeliz.setVisible(true);
            animateIcon(imgFeliz);
            updateEmotionStyle("Feliz");
        } else if (source == imgTranquilo) {
            labelTranquilo.setVisible(true);
            animateIcon(imgTranquilo);
            updateEmotionStyle("Tranquilo");
        } else if (source == imgConfuso) {
            labelConfuso.setVisible(true);
            animateIcon(imgConfuso);
            updateEmotionStyle("Confuso");
        } else if (source == imgEmBaixo) {
            labelEmBaixo.setVisible(true);
            animateIcon(imgEmBaixo);
            updateEmotionStyle("Em baixo");
        } else if (source == imgSensivel) {
            labelSensivel.setVisible(true);
            animateIcon(imgSensivel);
            updateEmotionStyle("Sensível");
        } else if (source == imgFrustrado) {
            labelFustrado.setVisible(true);
            animateIcon(imgFrustrado);
            updateEmotionStyle("Frustrado");
        }
    }

private void animateIcon(ImageView icon) {
    // Se já está a animar, ignora
    if (activeAnimations.containsKey(icon)) return;

    TranslateTransition jump = new TranslateTransition(Duration.millis(200), icon);
    jump.setByY(-10);
    jump.setCycleCount(2);
    jump.setAutoReverse(true);

    // Guarda a animação como ativa
    activeAnimations.put(icon, jump);

    // Remove do mapa quando termina
    jump.setOnFinished(e -> activeAnimations.remove(icon));

    jump.play();
}

    private void updateEmotionStyle(String emotion) {
        String backgroundColor = "#EFF7FB"; // Default

        switch (emotion) {
            case "Radiante":  backgroundColor = "#FFF59D"; break;
            case "Feliz":     backgroundColor = "#AEDFB0"; break;
            case "Tranquilo": backgroundColor = "#D6F0F5"; break;
            case "Confuso":   backgroundColor = "#FFF3CD"; break;
            case "Em baixo":  backgroundColor = "#B0BEC5"; break;
            case "Sensível":  backgroundColor = "#FFCAD4"; break;
            case "Frustrado": backgroundColor = "#FFADAD"; break;
        }

        rootPane.setStyle("-fx-background-color: " + backgroundColor + ";");
        emotionImagesDetails.setStyle("-fx-background-color: " + backgroundColor + ";");
    }

    @FXML
    private void handleSubmit() {
        String note = noteArea.getText();

        if (selectedEmotion == null) {
            showAlert("Por favor, escolhe uma emoção primeiro.");
            return;
        }
        int points = EmotionManager.getPoints(selectedEmotion);
        
        // Criar e imprimir registo
        EmotionEntry entry = new EmotionEntry(LocalDate.now(), selectedEmotion, note, points);
        EmotionManager.guardarRegisto(entry);
        System.out.println("Guardado: " + entry.getEmotion() + " - " + entry.getNote());

        // Mostrar alerta
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registo guardado");
        alert.setHeaderText(null);
        alert.setContentText("O teu registo foi guardado com sucesso. Obrigado por partilhares 💙");
        alert.showAndWait();
        Stage stage = (Stage) noteArea.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void handleEmotionExit(MouseEvent event) {
        Object source = event.getSource();

        if (source == imgRadiante) {
            labelRadiante.setVisible(false);
        } else if (source == imgFeliz) {
            labelFeliz.setVisible(false);
        } else if (source == imgTranquilo) {
            labelTranquilo.setVisible(false);
        } else if (source == imgConfuso) {
            labelConfuso.setVisible(false);
        } else if (source == imgEmBaixo) {
            labelEmBaixo.setVisible(false);
        } else if (source == imgSensivel) {
            labelSensivel.setVisible(false);
        } else if (source == imgFrustrado) {
            labelFustrado.setVisible(false);
        }
        
        rootPane.setStyle("-fx-background-color: #EFF7FB;");
        emotionImagesDetails.setStyle("-fx-background-color: #EFF7FB");
    }
    
    @FXML
    public void handleEmotionClick(MouseEvent event) {
        Object source = event.getSource();

        if (source == imgRadiante) {
            selectedEmotion = "Radiante";
        } else if (source == imgFeliz) {
            selectedEmotion = "Feliz";
        } else if (source == imgTranquilo) {
            selectedEmotion = "Tranquilo";
        } else if (source == imgConfuso) {
            selectedEmotion = "Confuso";
        } else if (source == imgEmBaixo) {
            selectedEmotion = "Em baixo";
        } else if (source == imgSensivel) {
            selectedEmotion = "Sensível";
        } else if (source == imgFrustrado) {
            selectedEmotion = "Frustrado";
        }

        selectedEmotionLabel.setText("Selecionado: " + selectedEmotion);
        selectedEmotionLabel.setVisible(true);
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
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
}