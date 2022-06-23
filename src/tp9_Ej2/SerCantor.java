/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp9_Ej2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

/**
 *
 * @author frank
 */
public abstract class SerCantor implements PuedeCantar{
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    private String tipo;
    private String nombre;
    private final Date fechaNacimiento;
    public Momento cuando;

    public SerCantor(String tipo, String nombre, Date fechaNacimiento, Momento cuando) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.cuando = cuando;
    }

   
    
    public int Calcular_edad(){
        int edad = 0;
        try {   
            //formato del LocaleDate
            DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            //Creacion de la fecha local
            LocalDate fechaLocal = LocalDate.now();
            //formateo de la fecha
            String fechaActual = fechaLocal.format(dtformat);
            //re formateo a tipo date 
            Date fechaActualDate = formato.parse(fechaActual);
            long timeDiff = Math.abs(fechaActualDate.getTime() - this.getFechaNacimiento().getTime());
            long days = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);            
            System.out.println(days);
            edad = days > 365 ?  (int) days/365 : 1;
            System.out.println(edad);
            } 
            catch (ParseException ex) {
                System.out.println(ex);
                System.out.println("Alguna fecha esta mal");
            }
            return edad;
    } 

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
    
    public abstract void cantar();
    public void hacerCantar(SerCantor canta){
        
    }
    
    
}
