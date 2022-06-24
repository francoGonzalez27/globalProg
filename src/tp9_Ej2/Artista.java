/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp9_Ej2;

/**
 *
 * @author messi
 */

import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class Artista extends SerCantor {
    
    public Instrumento instrumento;
    
    
    public boolean hacerCantar(){
        
        return true;
    }

    public Artista(Instrumento instrumento, String tipo, String nombre, Date fechaNacimiento, Momento momentoCantor) {
        super(tipo, nombre, fechaNacimiento, momentoCantor);
        this.instrumento = instrumento;
    }

    
    
    public void setValues(String instrumentoNombre,String instrumentoTipo, String nombre, Momento momentoCantor){
        this.instrumento =  new Instrumento(instrumentoNombre,instrumentoTipo);
        this.setNombre(nombre);
        this.cuando=momentoCantor;
    }

    @Override
    public void cantar() {
       try {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/sonidos/voz.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        
       } catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex){
           System.out.println("se produjo un error en el sonido del canario");
           System.out.println(ex);
       }
      
    }
}
