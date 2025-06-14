/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mindspace.controller;

import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mindspace.data.MongoConnection;
import mindspace.data.UserDAO;
import mindspace.model.UserSession;
import org.bson.Document;

public class LoginController implements Initializable {

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private TextField loginUsernameField;

    @FXML
    private Hyperlink linkRegistar;

    @FXML
    private CheckBox lembrarUtilizadorCheck;

    @FXML
    private void handleRegistarRedirect() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mindspace/view/RegisterLogin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) linkRegistar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erro ao carregar o ecrã de login.");
        }
    }

    @FXML
    private void handleLogin() {
        String username = loginUsernameField.getText().trim();
        String password = loginPasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Por favor preencha todos os campos.");
            return;
        }

        MongoDatabase db = MongoConnection.getDatabase();
        UserDAO userDAO = new UserDAO(db);

        Task<Boolean> loginTask = new Task<>() {
            @Override
            protected Boolean call() {
                return userDAO.validateLogin(username, password);
            }

            @Override
            protected void succeeded() {
                if (getValue()) {
                    Document userDoc = userDAO.getUserByUsername(username);
                    if (userDoc != null) {
                        String userId = userDoc.getObjectId("_id").toHexString();
                        UserSession.start(userId, username);
                        if (lembrarUtilizadorCheck.isSelected()) {
                            salvarUsername(username);
                        } else {
                            apagarUsernameGuardado();
                        }

                        openMainMenu();
                    }
                } else {
                    showAlert("Credenciais inválidas. Verifique o utilizador e a palavra-passe.");
                }
            }

            @Override
            protected void failed() {
                showAlert("Erro ao tentar fazer login.");
            }
        };

        new Thread(loginTask).start();
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
            Stage currentStage = (Stage) loginUsernameField.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Não foi possível abrir o menu principal.");
        }
    }

    private void salvarUsername(String username) {
        try {
            Properties props = new Properties();
            props.setProperty("rememberedUsername", username);
            FileOutputStream out = new FileOutputStream("src/mindspace/config/user.properties");
            props.store(out, null);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void apagarUsernameGuardado() {
        try {
            File f = new File("src/mindspace/config/user.properties");
            if (f.exists()) {
                Properties props = new Properties();
                FileInputStream in = new FileInputStream(f);
                props.load(in);
                in.close();

                props.remove("rememberedUsername");

                FileOutputStream out = new FileOutputStream(f);
                props.store(out, null);
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void preencherUsernameSeGuardado() {
    try {
        File f = new File("src/mindspace/config/user.properties");
        if (f.exists()) {
            Properties props = new Properties();
            FileInputStream in = new FileInputStream(f);
            props.load(in);
            in.close();

            String rememberedUsername = props.getProperty("rememberedUsername");

            if (rememberedUsername != null && !rememberedUsername.isEmpty()) {
                loginUsernameField.setText(rememberedUsername);
                lembrarUtilizadorCheck.setSelected(true);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("MindSpace");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preencherUsernameSeGuardado();
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
