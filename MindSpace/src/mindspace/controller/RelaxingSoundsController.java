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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author fp226
 */
public class RelaxingSoundsController implements Initializable {
    @FXML private Button btnVoltar, btnParar;
    @FXML private ComboBox<String> comboBoxSons;
    @FXML private Label labelSomAtual, labelTempo;
    @FXML private Slider VolumeSLider, sliderProgresso;
    @FXML private ImageView iconSom, playPauseIcon;
    @FXML private VBox audioVBox;
    private boolean isPlaying = false;
    private boolean somCarregado = false; 
    private Image playImage;
    private Image pauseImage;
    
    private MediaPlayer mediaPlayer;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxSons.getItems().addAll("Som da Natureza", "Som da Água", "Música Ambiental");
        playImage = new Image(getClass().getResourceAsStream("/mindspace/resources/play.png"));
        pauseImage = new Image(getClass().getResourceAsStream("/mindspace/resources/pause.png"));
        playPauseIcon.setImage(playImage);
        playPauseIcon.setOnMouseClicked(e -> togglePlayPause());
        comboBoxSons.setOnAction(e -> tocarSomSelecionado());

        VolumeSLider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newVal.doubleValue());
            }
        });
        sliderProgresso.setOnMousePressed(event -> {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.seconds(sliderProgresso.getValue()));
            }
        });
        sliderProgresso.setOnMouseReleased(event -> {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.seconds(sliderProgresso.getValue()));
            }
        });
        sliderProgresso.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
        if (!isChanging && mediaPlayer != null) {
            mediaPlayer.seek(Duration.seconds(sliderProgresso.getValue()));
            }
        });
        sliderProgresso.setOnMouseClicked(e -> {
        if (mediaPlayer != null) {
            double mouseX = e.getX();
            double percent = mouseX / sliderProgresso.getWidth();
            double seekTime = percent * sliderProgresso.getMax();
            mediaPlayer.seek(Duration.seconds(seekTime));
            }
        });
    }    
    @FXML
    private void voltarParaMenuPrincipal() {
        pararSom();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mindspace/view/Saude.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   private void tocarSomSelecionado() {
    pararSom();

    String somEscolhido = comboBoxSons.getValue();
    if (somEscolhido == null) return;

    String path = switch (somEscolhido) {
        case "Som da Natureza" -> "/mindspace/resources/sounds/natureza.mp3";
        case "Som da Água" -> "/mindspace/resources/sounds/ambiental.mp3";
        case "Música Ambiental" -> "/mindspace/resources/sounds/ruido.mp3";
        default -> null;
    };

    URL resource = getClass().getResource(path);
    if (resource == null) {
        System.err.println("Ficheiro nao encontrado: " + path);
        return;
    }

    Media media = new Media(resource.toExternalForm());
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setVolume(VolumeSLider.getValue());

    mediaPlayer.setOnReady(() -> {
        Duration total = mediaPlayer.getTotalDuration();
        sliderProgresso.setMax(total.toSeconds());

        mediaPlayer.play(); // começa a tocar assim que estiver pronto
        isPlaying = true;
        if (playPauseIcon != null && pauseImage != null)
            playPauseIcon.setImage(pauseImage); // mostra o botão de "pause"
    });

    mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
        if (!sliderProgresso.isValueChanging()) {
            sliderProgresso.setValue(newTime.toSeconds());
        }

        int minutes = (int) newTime.toMinutes();
        int seconds = (int) newTime.toSeconds() % 60;
        labelTempo.setText(String.format("%02d:%02d", minutes, seconds));
    });

    labelSomAtual.setText(somEscolhido);
    iconSom.setOpacity(0.7);
}

    private void togglePlayPause() {
    if (mediaPlayer == null) return;

    if (isPlaying) {
        mediaPlayer.pause();
        playPauseIcon.setImage(playImage);
    } else {
        mediaPlayer.play();
        playPauseIcon.setImage(pauseImage);
    }

    isPlaying = !isPlaying;
}

    @FXML
    private void pararSom() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
        labelSomAtual.setText("Nenhum som a tocar");
        labelTempo.setText("00:00");
        iconSom.setOpacity(0.3);
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
