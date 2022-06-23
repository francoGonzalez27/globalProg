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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;
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
        AudioClip sonido;
      sonido = java.applet.Applet.newAudioClip(getClass().getResource("/sonidos/voz"));
      
    }
}
