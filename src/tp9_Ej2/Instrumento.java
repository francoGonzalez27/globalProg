package tp9_Ej2;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author messi
 */
public class Instrumento {
    public String nombre;
    public String tipo;
    public Clip audioClip;
    public Instrumento(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }
    
    public boolean ejecuta() {
      String seleccion = this.nombre;
      seleccion=seleccion.toLowerCase();
      String url = "/sonidos/"+seleccion+".wav";
        try {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/sonidos/"+seleccion+".wav").getAbsoluteFile());
        audioClip = AudioSystem.getClip();
        audioClip.open(audioInputStream);
        audioClip.start();
        return true;
       } catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex){
         return false;
       }
      }
    
    
    
}
