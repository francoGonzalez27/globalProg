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
    public String banda;
    public String tipoDeMusica;
    public boolean hacerCantar(){
        
        return true;
    }

    public Artista(String instrumentoNombre,String instrumentoTipo, String nombre, String banda, String tipoDeMusica, String tipoDeCantor, Date fechaNacimiento) {
        super(tipoDeCantor, nombre, fechaNacimiento);
        this.instrumento =  new Instrumento(instrumentoNombre,instrumentoTipo);
        this.banda = banda;
        this.tipoDeMusica = tipoDeMusica;
    }
    
    public void setValues(String instrumentoNombre,String instrumentoTipo, String nombre, String banda, String tipoDeMusica, String tipoDeCantor){
        this.instrumento =  new Instrumento(instrumentoNombre,instrumentoTipo);
        this.banda = banda;
        this.setNombre(nombre);
        this.setTipo(tipoDeCantor);
        this.tipoDeMusica = tipoDeMusica;
    }

    @Override
    public void cantar() {
        AudioClip sonido;
      sonido = java.applet.Applet.newAudioClip(getClass().getResource("/sonidos/voz"));
      
    }
}
