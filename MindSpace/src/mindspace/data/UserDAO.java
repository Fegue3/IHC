/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mindspace.data;

import com.mongodb.client.*;
import org.bson.Document;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import static com.mongodb.client.model.Filters.eq;

public class UserDAO {

    private final MongoCollection<Document> collection;

    public UserDAO(MongoDatabase database) {
        this.collection = database.getCollection("utilizadores");
    }

    public boolean registerUser(String username, String password) {

        if (usernameExists(username)) {
            System.out.println("Já existe");
            return false;
        }

        String hashedPassword = hashPassword(password);
        Document user = new Document("username", username)
                .append("password", hashedPassword);

        try {
            collection.insertOne(user);
            System.out.println("Inserido com sucesso!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateLogin(String username, String password) {
        String hashedPassword = hashPassword(password);

        Document query = new Document("username", username)
                .append("password", hashedPassword);

        return collection.find(query).first() != null;
    }

    public boolean usernameExists(String username) {
        Document query = new Document("username", username);
        boolean exists = collection.find(query).first() != null;
        return exists;
    }

    public Document getUserByUsername(String username) {
        Document query = new Document("username", username);
        return collection.find(query).first();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert byte array to hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao encriptar a password", e);
        }
    }

    public String getUserIdByUsername(String username) {
        Document user = collection.find(eq("username", username)).first();
        return user != null ? user.getObjectId("_id").toHexString() : null;
    }

}
