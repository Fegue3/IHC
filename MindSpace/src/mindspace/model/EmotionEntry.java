/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mindspace.model;

/**
 *
 * @author fp226
 */

import java.time.LocalDate;

public class EmotionEntry {
    private LocalDate date;
    private String emotion;
    private String note;

    public EmotionEntry(LocalDate date, String emotion, String note) {
        this.date = date;
        this.emotion = emotion;
        this.note = note;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getEmotion() {
        return emotion;
    }

    public String getNote() {
        return note;
    }
    
     @Override
    public String toString() {
        return "Data: " + date + "\nEmoção: " + emotion + "\nNota: " + note;
    }
}