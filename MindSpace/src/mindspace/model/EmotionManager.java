/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mindspace.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.io.File;
import com.mongodb.client.MongoCollection;
import mindspace.data.MongoConnection;
import mindspace.model.UserSession;
import org.bson.Document;

public class EmotionManager {

    private static final String FILE_PATH = "src/mindspace/data/registos.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static int getPoints(String emotion) {
        switch (emotion) {
            case "Radiante":
                return 6;
            case "Feliz":
                return 4;
            case "Tranquilo":
                return 2;
            case "Confuso":
                return 0;
            case "Em baixo":
                return -2;
            case "Sensível":
                return -4;
            case "Frustrado":
                return -6;
            default:
                return 0;
        }
    }

    public static void guardarRegisto(EmotionEntry entry) {

        // Guardar também no MongoDB
        MongoCollection<Document> collection = MongoConnection.getDatabase().getCollection("registos");

        Document doc = new Document("userId", UserSession.getUserId())
                .append("data", entry.getDate().format(formatter))
                .append("emotion", entry.getEmotion())
                .append("note", entry.getNote().isEmpty() ? "(sem comentário)" : entry.getNote())
                .append("points", entry.getPoints());

        collection.insertOne(doc);
        System.out.println("Registo guardado no MongoDB com sucesso.");
    }
}
