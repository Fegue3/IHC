/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mindspace.tests;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mindspace.data.MongoConnection;
import org.bson.Document;

import java.util.Arrays;

public class Seeder {

    public static void main(String[] args) {
        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> collection = db.getCollection("registos");

        String userId = "684ca050ab03733283064364";

        String dados = """
        Data: 01/05/2025
        Emoção: Tranquilo
        Nota: dia calmo
        Pontos: 2
        ---------------------------
        Data: 02/05/2025
        Emoção: Sensível
        Nota: preocupação com exames
        Pontos: -4
        ---------------------------
        Data: 03/05/2025
        Emoção: Feliz
        Nota: boas notícias
        Pontos: 4
        ---------------------------
        Data: 04/05/2025
        Emoção: Frustrado
        Nota: projeto falhado
        Pontos: -6
        ---------------------------
        Data: 05/05/2025
        Emoção: Radiante
        Nota: saí com amigos
        Pontos: 6
        ---------------------------
        Data: 06/05/2025
        Emoção: Em baixo
        Nota: sem motivação
        Pontos: -2
        ---------------------------
        Data: 07/05/2025
        Emoção: Confuso
        Nota: muitas decisões
        Pontos: 0
        ---------------------------
        Data: 08/05/2025
        Emoção: Tranquilo
        Nota: dia produtivo
        Pontos: 2
        ---------------------------
        Data: 09/05/2025
        Emoção: Feliz
        Nota: recebi elogios
        Pontos: 4
        ---------------------------
        Data: 10/05/2025
        Emoção: Frustrado
        Nota: perdi o foco
        Pontos: -6
        ---------------------------
        Data: 11/05/2025
        Emoção: Radiante
        Nota: dia ótimo
        Pontos: 6
        ---------------------------
        Data: 12/05/2025
        Emoção: Em baixo
        Nota: noite mal dormida
        Pontos: -2
        ---------------------------
        Data: 13/05/2025
        Emoção: Sensível
        Nota: lembranças antigas
        Pontos: -4
        ---------------------------
        Data: 14/05/2025
        Emoção: Tranquilo
        Nota: rotina agradável
        Pontos: 2
        ---------------------------
        Data: 15/05/2025
        Emoção: Confuso
        Nota: indecisão
        Pontos: 0
        ---------------------------
        Data: 16/05/2025
        Emoção: Feliz
        Nota: consegui terminar tarefa
        Pontos: 4
        ---------------------------
        Data: 17/05/2025
        Emoção: Sensível
        Nota: ansiedade
        Pontos: -4
        ---------------------------
        Data: 18/05/2025
        Emoção: Radiante
        Nota: viagem marcada
        Pontos: 6
        ---------------------------
        Data: 19/05/2025
        Emoção: Em baixo
        Nota: me senti sozinho
        Pontos: -2
        ---------------------------
        Data: 20/05/2025
        Emoção: Frustrado
        Nota: plano não funcionou
        Pontos: -6
        ---------------------------
        Data: 21/05/2025
        Emoção: Tranquilo
        Nota: dia equilibrado
        Pontos: 2
        ---------------------------
        Data: 22/05/2025
        Emoção: Feliz
        Nota: caminhei ao sol
        Pontos: 4
        ---------------------------
        Data: 23/05/2025
        Emoção: Confuso
        Nota: trabalho acumulado
        Pontos: 0
        ---------------------------
        Data: 24/05/2025
        Emoção: Sensível
        Nota: saudades
        Pontos: -4
        ---------------------------
        Data: 25/05/2025
        Emoção: Em baixo
        Nota: desmotivado
        Pontos: -2
        ---------------------------
        Data: 26/05/2025
        Emoção: Frustrado
        Nota: erro num relatório
        Pontos: -6
        ---------------------------
        Data: 27/05/2025
        Emoção: Tranquilo
        Nota: dormi bem
        Pontos: 2
        ---------------------------
        Data: 28/05/2025
        Emoção: Radiante
        Nota: conversa boa com amigo
        Pontos: 6
        ---------------------------
        Data: 29/05/2025
        Emoção: Confuso
        Nota: decisões difíceis
        Pontos: 0
        ---------------------------
        Data: 30/05/2025
        Emoção: Em baixo
        Nota: dia nublado
        Pontos: -2
        ---------------------------
        Data: 31/05/2025
        Emoção: Sensível
        Nota: pensamentos intensos
        Pontos: -4
        ---------------------------
        Data: 01/06/2025
        Emoção: Feliz
        Nota: comecei bem o mês
        Pontos: 4
        ---------------------------
        Data: 02/06/2025
        Emoção: Frustrado
        Nota: falhou uma reunião
        Pontos: -6
        ---------------------------
        Data: 03/06/2025
        Emoção: Tranquilo
        Nota: dia comum
        Pontos: 2
        ---------------------------
        Data: 04/06/2025
        Emoção: Radiante
        Nota: consegui algo importante
        Pontos: 6
        ---------------------------
        Data: 04/06/2025
        Emoção: Sensível
        Nota: ao mesmo tempo cansado
        Pontos: -4
        ---------------------------
        Data: 05/06/2025
        Emoção: Confuso
        Nota: emoções mistas
        Pontos: 0
        ---------------------------
        Data: 06/06/2025
        Emoção: Em baixo
        Nota: fim de semana chegando
        Pontos: -2
        ---------------------------
        Data: 05/06/2025
        Emoção: Radiante
        Nota: (sem comentário)
        Pontos: 6
        ---------------------------
        Data: 06/06/2025
        Emoção: Radiante
        Nota: Não sei
        Pontos: 6
        ---------------------------
        Data: 06/06/2025
        Emoção: Radiante
        Nota: (sem comentário)
        Pontos: 6
        ---------------------------
        Data: 12/06/2025
        Emoção: Feliz
        Nota: o dia correu bem
        Pontos: 4
        ---------------------------
        Data: 12/06/2025
        Emoção: Em baixo
        Nota: (sem comentário)
        Pontos: -2
        ---------------------------
        Data: 12/06/2025
        Emoção: Tranquilo
        Nota: (sem comentário)
        Pontos: 2
        ---------------------------
        Data: 12/06/2025
        Emoção: Radiante
        Nota: kokjnjn
        Pontos: 6
        ---------------------------
        
        """;

        Arrays.stream(dados.split("---------------------------"))
                .map(String::strip)
                .filter(bloco -> !bloco.isEmpty())
                .forEach(bloco -> {
                    String[] linhas = bloco.split("\n");

                    String data = linhas[0].replace("Data: ", "").trim();
                    String emotion = linhas[1].replace("Emoção: ", "").trim();
                    String note = linhas[2].replace("Nota: ", "").trim();
                    int points = Integer.parseInt(linhas[3].replace("Pontos: ", "").trim());

                    Document doc = new Document("userId", userId)
                            .append("data", data)
                            .append("emotion", emotion)
                            .append("note", note)
                            .append("points", points);

                    collection.insertOne(doc);
                });

        System.out.println("Registos inseridos com sucesso!");
    }
}
