/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mindspace.data;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

import java.util.List;
import java.util.ArrayList;

public class RegistoDAO {

    private final MongoCollection<Document> collection;

    public RegistoDAO(MongoDatabase db) {
        this.collection = db.getCollection("registos");
    }

    public List<Document> getRegistosPorUser(String userId) {
        return collection.find(eq("userId", userId)).into(new ArrayList<>());
    }
}
