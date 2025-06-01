/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mindspace.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author fp226
 */
public class BreathingExController implements Initializable {
    
    @FXML private Label countdownLabel, phaseLabel;
    @FXML private Button btnVoltar, btnMain;
    @FXML private Circle breathingCircle;
    
    private boolean emExecucao = false;
    private final String[] phases = {"Inspira", "Segura", "Expira"};
    private final int phaseDuration = 4;
    private int currentPhase = 0;
    private Timeline timelineAtual;
    
     @FXML
    public void startBreathingCycle() {
        runInitialCountdown(() -> {
            currentPhase = 0;
            nextPhase();
        });
    }
    private void runInitialCountdown(Runnable onFinish) {
        Timeline timeline = new Timeline();
        for (int i = 1; i <= phaseDuration; i++) {
            int count = i;
            KeyFrame kf = new KeyFrame(Duration.seconds(i - 1),
                e -> {
                    phaseLabel.setText(String.valueOf(count));
                    phaseLabel.setStyle("-fx-font-size: 42px; -fx-text-fill: #37474F; -fx-font-weight: bold;");
                    countdownLabel.setText("");
                    animatePulse(phaseLabel);
                });
            timeline.getKeyFrames().add(kf);
        }

        KeyFrame end = new KeyFrame(Duration.seconds(phaseDuration), e -> onFinish.run());
        timeline.getKeyFrames().add(end);
        timelineAtual = timeline;
        timeline.play();
    }

    private void nextPhase() {
        if (currentPhase >= phases.length)
            currentPhase = 0;

        String phase = phases[currentPhase];
        phaseLabel.setText(phase);
        phaseLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: #37474F; -fx-font-weight: bold;");
        animateFadeAndScale(phaseLabel);
        animateCircle(phase);

        if (phase.equals("Inspira") || phase.equals("Expira")) {
            runCountdown(() -> {
                currentPhase++;
                nextPhase();
            });
        } else if (phase.equals("Segura")) {
            countdownLabel.setText("");  // não mostrar contador
            Timeline pause = new Timeline(new KeyFrame(Duration.seconds(phaseDuration), e -> {
                currentPhase++;
                nextPhase();
            }));
            timelineAtual = pause;
            pause.play();
        }
    }

    private void runCountdown(Runnable onFinish) {
        Timeline timeline = new Timeline();
        for (int i = phaseDuration; i >= 1; i--) {
            int count = i;
             KeyFrame kf = new KeyFrame(Duration.seconds(phaseDuration - i), e -> {
                countdownLabel.setText(String.valueOf(count));
                animatePulse(countdownLabel); 
            });
            timeline.getKeyFrames().add(kf);
        }
        KeyFrame end = new KeyFrame(Duration.seconds(phaseDuration), e -> onFinish.run());
        timeline.getKeyFrames().add(end);
        timelineAtual = timeline;
        timeline.play();
    }
    
    private void animatePulse(Label label) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(300), label);
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(1.3);
        scale.setToY(1.3);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        scale.play();
    }
    
    private void animateFadeAndScale(Label label) {
    FadeTransition fade = new FadeTransition(Duration.millis(300), label);
    fade.setFromValue(0);
    fade.setToValue(1);

    ScaleTransition scale = new ScaleTransition(Duration.millis(300), label);
    scale.setFromX(0.9);
    scale.setFromY(0.9);
    scale.setToX(1.1);
    scale.setToY(1.1);

    ParallelTransition transition = new ParallelTransition(fade, scale);
    transition.play();
}


     private void animateCircle(String phase) {
        double fromRadius = breathingCircle.getRadius();
        double toRadius = switch (phase) {
            case "Inspira" -> 115;
            case "Expira" -> 80;
            default -> 100;
        };
        Color targetColor = switch (phase) {
            case "Inspira" -> Color.web("#A6DCEF");
            case "Expira" -> Color.web("#ffcccc");
            case "Segura" -> Color.web("#C1A6F5");
            default -> Color.LIGHTGRAY;
        };
        Timeline circleAnim = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(breathingCircle.radiusProperty(), fromRadius),
                new KeyValue(breathingCircle.fillProperty(), breathingCircle.getFill())),
            new KeyFrame(Duration.seconds(1),
                new KeyValue(breathingCircle.radiusProperty(), toRadius),
                new KeyValue(breathingCircle.fillProperty(), targetColor))
        );
        circleAnim.play();
    }
    
    @FXML
    public void stopBreathingCycle() {
        if (timelineAtual != null) {
            timelineAtual.stop();
        }
        phaseLabel.setText("Parado");
        phaseLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: #37474F; -fx-font-weight: bold;");
        countdownLabel.setText("");
        animateCircle("Parado");
    }
    
    @FXML
    public void handleMainButton() {
        if (!emExecucao) {
            // Começar
            emExecucao = true;
            btnMain.setText("Parar");
            btnMain.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 20;");
            startBreathingCycle();
        } else {
            // Parar
            emExecucao = false;
            btnMain.setText("Começar");
            btnMain.setStyle("-fx-background-color: #A6DCEF; -fx-background-radius: 20;");
            stopBreathingCycle();
        }
    }

    
    @FXML
    private void voltarParaMenuPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mindspace/view/Saude.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void hoverMainButton(MouseEvent event) {
        Button btn = (Button) event.getSource();
        String texto = btn.getText();

        if (texto.equals("Parar")) {
            // Hover do botão "Parar"
            btn.setStyle("-fx-background-color: #f29c9c; -fx-text-fill: #37474F; -fx-background-radius: 20;");
        } else {
            // Hover do botão "Começar"
            btn.setStyle("-fx-background-color: #90C3D4; -fx-text-fill: #37474F; -fx-background-radius: 20;");
        }
    }

    @FXML
    private void sairHoverMainButton(MouseEvent event) {
        Button btn = (Button) event.getSource();
        String texto = btn.getText();

        if (texto.equals("Parar")) {
            // Estado normal do botão "Parar"
            btn.setStyle("-fx-background-color: #ffcccc; -fx-text-fill: #37474F; -fx-background-radius: 20;");
        } else {
            // Estado normal do botão "Começar"
            btn.setStyle("-fx-background-color: #A6DCEF; -fx-text-fill: #37474F; -fx-background-radius: 20;");
        }
    }
    @FXML
    private void hoverLavanda(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #C1A6F5; -fx-text-fill: #37474F; -fx-background-radius: 20;");
    }

    @FXML
    private void sairHoverLavanda(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #D5BFFF; -fx-text-fill: #37474F; -fx-background-radius: 20;");
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
