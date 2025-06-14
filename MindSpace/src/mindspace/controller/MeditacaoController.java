package mindspace.controller;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import mindspace.data.MongoConnection;
import mindspace.model.UserSession;
import org.bson.Document;

public class MeditacaoController implements Initializable {

    @FXML
    private VBox dicasContainer;
    @FXML
    private Button btnAdicionar, btnRemover;
    @FXML
    private ScrollPane scrollPane;

    private boolean modoRemover = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String userId = UserSession.getUserId();
        if (userId != null) {
            importarDicasGlobais(userId);
            carregarDicasDoMongo(userId);
        }
    }

    private void importarDicasGlobais(String userId) {
        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> dicasGlobais = db.getCollection("dicas_globais");
        MongoCollection<Document> dicasUser = db.getCollection("dicas");

        long count = dicasUser.countDocuments(eq("userId", userId));
        if (count > 0) return;

        List<Document> globais = dicasGlobais.find().into(new ArrayList<>());
        for (Document doc : globais) {
            Document nova = new Document("userId", userId)
                .append("titulo", doc.getString("titulo"))
                .append("descricao", doc.getString("descricao"));
            dicasUser.insertOne(nova);
        }
    }

    private void carregarDicasDoMongo(String userId) {
        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> dicas = db.getCollection("dicas");

        List<Document> docs = dicas.find(eq("userId", userId)).into(new ArrayList<>());
        for (Document doc : docs) {
            adicionarDica(doc.getString("titulo"), doc.getString("descricao"), doc.getObjectId("_id"));
        }
    }

    private void adicionarDica(String titulo, String descricao, org.bson.types.ObjectId id) {
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
            adicionarBotaoRemover(card, id);
        }
    }

    private void adicionarBotaoRemover(VBox card, org.bson.types.ObjectId id) {
        Button btnX = new Button("✕");
        btnX.getStyleClass().add("btn-remover");
        btnX.setStyle("-fx-background-color: transparent; -fx-text-fill: red; -fx-font-weight: bold;");
        btnX.setOnAction(e -> {
            dicasContainer.getChildren().remove(card);
            removerDicaDoMongo(id);
        });

        VBox.setMargin(btnX, new Insets(0));
        HBox hbox = new HBox(btnX);
        hbox.setPadding(new Insets(0));
        hbox.setStyle("-fx-alignment: top-right;");
        card.getChildren().add(0, hbox);
    }

    private void removerDicaDoMongo(org.bson.types.ObjectId id) {
        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> dicas = db.getCollection("dicas");
        dicas.deleteOne(eq("_id", id));
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
                    salvarDicaNoMongo(titulo, descricao);
                }
            }
        });
    }

    private void salvarDicaNoMongo(String titulo, String descricao) {
        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> dicas = db.getCollection("dicas");
        String userId = UserSession.getUserId();

        Document nova = new Document("userId", userId)
                .append("titulo", titulo)
                .append("descricao", descricao);
        dicas.insertOne(nova);

        // Recarrega a lista (poderias só adicionar 1)
        dicasContainer.getChildren().clear();
        carregarDicasDoMongo(userId);
    }

    @FXML
    private void ativarModoRemover(ActionEvent event) {
        modoRemover = !modoRemover;
        String userId = UserSession.getUserId();
        dicasContainer.getChildren().clear();
        carregarDicasDoMongo(userId);
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
