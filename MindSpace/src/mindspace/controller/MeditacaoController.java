/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mindspace.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sofia Costa
 */
public class MeditacaoController implements Initializable {

    @FXML
    private VBox dicasContainer;

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Lista de dicas — pode ser lido de um ficheiro externo se preferires
        String[][] dicas = {
            {"🧘 Respiração Consciente", "Foca a tua atenção no ar a entrar e sair do teu corpo durante 2 minutos."},
            {"🕯️ Atenção à chama", "Observa a chama de uma vela durante um minuto. Deixa os pensamentos fluírem."},
            {"🌅 Visualização Guiada", "Imagina um lugar calmo, como uma praia ou montanha. Explora-o com a mente."},
            {"🎧 Sons da Natureza", "Escuta sons de chuva, ondas ou floresta para acalmar a tua mente."},
            {"📿 Mantra Pessoal", "Repete uma palavra ou frase que te tranquilize, como 'paz' ou 'estou bem'."}
        };

        for (String[] dica : dicas) {
            adicionarDica(dica[0], dica[1]);
        }
    }

    private void adicionarDica(String titulo, String descricao) {
        VBox card = new VBox();
        card.setPadding(new Insets(10));
        card.setSpacing(5);
        card.setStyle("-fx-background-color: #E6F7FA; -fx-background-radius: 10;");
        
        Label tituloLabel = new Label(titulo);
        tituloLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        tituloLabel.setWrapText(true);

        Label descLabel = new Label(descricao);
        descLabel.setWrapText(true);

        card.getChildren().addAll(tituloLabel, descLabel);
        dicasContainer.getChildren().add(card);
    }

    @FXML
    private void voltar(ActionEvent event) {
        // Fecha a janela atual
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        // ou muda de cena se quiseres usar navegação interna
    }
}