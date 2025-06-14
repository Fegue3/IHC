/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mindspace.data;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MongoConnection {

    private static MongoClient client = null;

    public static MongoDatabase getDatabase() {
        if (client == null) {
            try {
                Properties props = new Properties();

                InputStream input = MongoConnection.class.getClassLoader()
                        .getResourceAsStream("mindspace/config/config.properties");

                if (input == null) {
                    throw new RuntimeException("❌ config.properties não encontrado no classpath!");
                }

                props.load(input);
                String uri = props.getProperty("MONGO_URI");

                if (uri == null || uri.isEmpty()) {
                    throw new RuntimeException("❌ MONGO_URI não definido.");
                }

                client = MongoClients.create(uri);
                System.out.println("✅ Ligado com sucesso ao MongoDB Atlas!");
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao carregar config.properties");
            }
        }
        return client.getDatabase("mindspace");
    }

}
