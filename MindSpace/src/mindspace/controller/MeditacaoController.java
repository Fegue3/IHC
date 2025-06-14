package mindspace.controller;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MeditacaoController implements Initializable {

    @FXML
    private VBox dicasContainer;

    @FXML
    private Button btnAdicionar, btnRemover;

    @FXML
    private ScrollPane scrollPane;

    private boolean modoRemover = false;

    // Caminho para o ficheiro dentro da pasta data
    private final File ficheiroDicas = new File("src/mindspace/data/dicas.txt");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        criarPastaSeNecessario();
        carregarDicasGuardadas();
    }

    private void criarPastaSeNecessario() {
        File pasta = new File("src/mindspace/data");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
    }

    private void adicionarDica(String titulo, String descricao) {
        VBox card = new VBox();
        card.setPadding(new Insets(10));
        card.setSpacing(5);
        card.getStyleClass().add("tip-card");
        card.setStyle("-fx-background-color: #E6F7FA; -fx-background-radius: 10;");

        Label tituloLabel = new Label(titulo);
        tituloLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        tituloLabel.setWrapText(true);

        Label descLabel = new Label(descricao);
        descLabel.setWrapText(true);

        card.getChildren().addAll(tituloLabel, descLabel);
        dicasContainer.getChildren().add(card);

        if (modoRemover) {
            adicionarBotaoRemover(card);
        }
    }

    private void adicionarBotaoRemover(VBox card) {
        Button btnX = new Button("✕");
        btnX.getStyleClass().add("btn-remover");
        btnX.setStyle("-fx-background-color: transparent; -fx-text-fill: red; -fx-font-weight: bold;");
        btnX.setOnAction(e -> {
            dicasContainer.getChildren().remove(card);
            removerDoFicheiro(card);
        });

        VBox.setMargin(btnX, new Insets(0, 0, 0, 0));
        HBox hbox = new HBox(btnX);
        hbox.setPadding(new Insets(0));
        hbox.setStyle("-fx-alignment: top-right;");
        card.getChildren().add(0, hbox);
    }

    @FXML
    private void adicionarDicaDialog(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Nova Dica");
        dialog.setHeaderText("Adicionar nova dica de meditação");

        TextField tituloField = new TextField();
        tituloField.setPromptText("Título da dica");

        TextArea descricaoArea = new TextArea();
        descricaoArea.setPromptText("Descrição da dica");
        descricaoArea.setWrapText(true);
        descricaoArea.setPrefRowCount(4);

        VBox content = new VBox(10, tituloField, descricaoArea);
        content.setPadding(new Insets(10));

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                String titulo = tituloField.getText().trim();
                String descricao = descricaoArea.getText().trim();
                if (!titulo.isEmpty() && !descricao.isEmpty()) {
                    adicionarDica(titulo, descricao);
                    guardarDicaEmFicheiro(titulo, descricao);
                }
            }
        });
    }

    private void guardarDicaEmFicheiro(String titulo, String descricao) {
        try (FileWriter writer = new FileWriter(ficheiroDicas, true)) {
            writer.write(titulo.replace("|", " ") + "|" + descricao.replace("|", " ") + "\n");
        } catch (IOException e) {
            System.err.println("Erro ao guardar dica: " + e.getMessage());
        }
    }

    private void carregarDicasGuardadas() {
        if (!ficheiroDicas.exists()) {
            try {
                ficheiroDicas.createNewFile();
            } catch (IOException e) {
                System.err.println("Erro ao criar ficheiro: " + e.getMessage());
            }
            return;
        }

        try (Scanner scanner = new Scanner(ficheiroDicas)) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (linha.contains("|")) {
                    String[] partes = linha.split("\\|", 2);
                    if (partes.length == 2) {
                        adicionarDica(partes[0].trim(), partes[1].trim());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar dicas: " + e.getMessage());
        }
    }

    private void removerDoFicheiro(VBox card) {
        String titulo = "";
        String descricao = "";

        for (Node n : card.getChildren()) {
            if (n instanceof Label label) {
                if (titulo.isEmpty()) {
                    titulo = label.getText();
                } else {
                    descricao = label.getText();
                }
            }
        }

        String linhaAlvo = titulo + "|" + descricao;
        File tempFile = new File("src/mindspace/data/dicas_temp.txt");

        try (
                BufferedReader reader = new BufferedReader(new FileReader(ficheiroDicas)); BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.trim().equals(linhaAlvo.trim())) {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao remover dica do ficheiro: " + e.getMessage());
            return;
        }

        ficheiroDicas.delete();
        tempFile.renameTo(ficheiroDicas);
    }

    @FXML
    private void ativarModoRemover(ActionEvent event) {
        modoRemover = !modoRemover;
        for (Node node : dicasContainer.getChildren()) {
            if (node instanceof VBox card) {
                if (modoRemover) {
                    if (card.lookup(".btn-remover") == null) {
                        adicionarBotaoRemover(card);
                    }
                } else {
                    card.getChildren().removeIf(child
                            -> child instanceof HBox hbox
                            && hbox.getChildren().stream().anyMatch(c -> c.getStyleClass().contains("btn-remover"))
                    );
                }
            }
        }
    }

    @FXML
    private void voltar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
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
