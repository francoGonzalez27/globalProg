/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tp9_Ej2;

import javax.swing.JFrame;

/**
 * @author Jesus
 */
public class TP9 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame_CrearArtistas crearArtistas = new JFrame_CrearArtistas();
        JFrame_MostrarArtistas mostrarArtistas = new JFrame_MostrarArtistas();
        JFrame_TipoDeCantor tipoDeCantor = new JFrame_TipoDeCantor();
        crearArtistas.setTitle("Crear Artista");
        mostrarArtistas.setTitle("Artistas");
        Controlador control = new Controlador(crearArtistas,mostrarArtistas, tipoDeCantor);
        control.iniciar();
    }

}
