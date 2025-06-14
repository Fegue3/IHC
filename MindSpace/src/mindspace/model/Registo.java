/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mindspace.model;

public class Registo {

    private String data;       // formato: "2025-06-12"
    private String emocao;     // ex: "Feliz", "Tranquilo", etc.
    private String nota;       // o comentário do utilizador
    private int pontos;        // valor entre -6 e +6

    public Registo() {
        // Construtor vazio necessário para deserialização
    }

    public Registo(String data, String emocao, String nota, int pontos) {
        this.data = data;
        this.emocao = emocao;
        this.nota = nota;
        this.pontos = pontos;
    }

    // Getters e Setters
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEmocao() {
        return emocao;
    }

    public void setEmocao(String emocao) {
        this.emocao = emocao;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}
