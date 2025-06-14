/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mindspace.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mindspace.data.UserDAO;
import mindspace.data.MongoConnection;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import mindspace.model.UserSession;

/**
 * FXML Controller class
 *
 * @author fp226
 */
public class RegisterLoginController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private Hyperlink linkLogin;
    @FXML
    private PasswordField confirmPasswordField;
    private UserDAO userDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MongoDatabase database = MongoConnection.getDatabase();
        userDAO = new UserDAO(database);
    }

    @FXML
    private void handleLoginRedirect() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mindspace/view/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) linkLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erro ao carregar o ecrã de login.");
        }
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Por favor preencha todos os campos.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("As palavras-passe não coincidem.");
            return;
        }

        if (userDAO.usernameExists(username)) {
            showAlert("O nome de utilizador já existe.");
        } else {
            boolean success = userDAO.registerUser(username, password);
            if (success) {
                // 🔑 Iniciar sessão após registo
                String userId = userDAO.getUserIdByUsername(username);
                if (userId != null) {
                    UserSession.start(userId, username);
                    showAlert("Registo efetuado com sucesso!");
                    openMainMenu();
                } else {
                    showAlert("Erro: utilizador registado mas não foi possível iniciar sessão.");
                }
            } else {
                showAlert("Erro ao registar utilizador.");
            }
        }
    }

    private void openMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mindspace/view/MainMenu.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("MindSpace - Menu Principal");
            stage.setScene(new Scene(root));
            stage.show();

            // Fechar janela de registo
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Não foi possível abrir o menu principal.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("MindSpace");
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
