/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp9_Ej2;

import java.io.Serializable;

/**
 *
 * @author frank
 */
public class Momento implements Serializable{
    public String tipo;
    private boolean alegria;

    public Momento(String tipo, boolean alegria) {
        this.tipo = tipo;
        this.alegria = alegria;
    }

    public boolean isAlegria() {
        return alegria;
    }

    public void setAlegria(boolean alegria) {
        this.alegria = alegria;
    }
    
    public String horario(){
        return "el horario al que canta es: "+this.tipo;
    }
    
}
