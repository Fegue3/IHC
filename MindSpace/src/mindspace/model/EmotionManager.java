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

public class EmotionManager {

    private static final String FILE_PATH = "data/registos.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void guardarRegisto(EmotionEntry entry) {
        File pasta = new File("data");
        if (!pasta.exists()) pasta.mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write("Data: " + entry.getDate().format(formatter));
            writer.newLine();
            writer.write("Emoção: " + entry.getEmotion());
            writer.newLine();
            writer.write("Nota: " + (entry.getNote().isEmpty() ? "(sem comentário)" : entry.getNote()));
            writer.newLine();
            writer.write("---------------------------");
            writer.newLine();
            System.out.println("Registo guardado com sucesso em " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Erro ao guardar o registo: " + e.getMessage());
        }
    }
}
