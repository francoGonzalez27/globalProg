/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp9_Ej2;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author frank
 */
public class Canario extends SerCantor{

    public Canario(String tipo, String nombre, Date fechaNacimiento) {
        super(tipo, nombre, fechaNacimiento);
    }
    
    public void setValues(String nombre){
        this.setNombre(nombre);
    }
    @Override
    public void cantar() {
       try {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/sonidos/canario.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        
       } catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex){
           System.out.println("se produjo un error en el sonido del canario");
           System.out.println(ex);
       }
      
    }
    
}
