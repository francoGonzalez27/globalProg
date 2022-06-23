/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp9_Ej2;

import java.applet.AudioClip;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
public class Controlador{
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    private final JFrame_CrearArtistas crearArtistas;
    private final JFrame_MostrarArtistas mostrarArtistas;
    private final JFrame_TipoDeCantor tipoDeCantor;
    public int LastIndex = 0;
    public String[][] cantoresArray;
    public ArrayList<Artista> artistasArray = new ArrayList();
    public ArrayList<Gallo> gallosArray = new ArrayList();
    public ArrayList<Canario> canariosArray = new ArrayList();
    public Artista artistaSeleccionado;
    public Gallo galloSeleccionado;
    public Canario canarioSeleccionado;
    
    public Integer indexCantorEditandose;
    public String tipoCantorEditandose;
    public String tipoDeArtistaCrear;
    public String[][] instrumentos = new String[10][2]; 
    
    public ActionListener editarArtista = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearArtista(true,tipoDeArtistaCrear);
            }
    };
    public  ActionListener crearArtista = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearArtista(false,tipoDeArtistaCrear);
            }
    };
    public  ActionListener continuarCreacion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                continuarCreacion(null);
            }
    };
    public  ActionListener continuarEdicion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                continuarCreacion(artistaSeleccionado);
            }
    };
    public Controlador(JFrame_CrearArtistas crearArtistas, JFrame_MostrarArtistas mostrarArtistas, JFrame_TipoDeCantor tipoDeCantor) {
        this.crearArtistas = crearArtistas;
        this.mostrarArtistas = mostrarArtistas;
        this.tipoDeCantor = tipoDeCantor;
        
        this.mostrarArtistas.crearArtistaBtn.addActionListener((ActionEvent e) -> {
            this.tipoDeCantor.SiguienteBotton.removeActionListener(continuarCreacion);
            this.tipoDeCantor.SiguienteBotton.removeActionListener(continuarEdicion);
            this.TipoDeArtista();
            this.tipoDeCantor.SiguienteBotton.addActionListener(continuarCreacion);
            //CambiarACrearArtista(null); 
        });
        this.tipoDeCantor.SiguienteBotton.addActionListener(continuarCreacion);
        this.mostrarArtistas.InfoArtistaBtn.addActionListener((ActionEvent e) -> {
            MostrarInfoArtista(); 
        });
        this.mostrarArtistas.editarArtista.addActionListener((ActionEvent e) -> {
            EditarArtista(); 
        });
        this.mostrarArtistas.eliminarArtistaBtn.addActionListener((ActionEvent e) -> {
            EliminarArtista(); 
        });
        this.mostrarArtistas.ReproducirSon.addActionListener((ActionEvent e) -> {
             boolean flag = this.artistaSeleccionado.instrumento.ejecuta();
             if (flag){
                 JOptionPane.showMessageDialog(null,"escuche...\nPulse el boton para detener la musica");
                 this.artistaSeleccionado.instrumento.audioClip.stop();
             }else{
                 JOptionPane.showMessageDialog(null,"no se puede escuchar la cancion");
             }
        });
        this.mostrarArtistas.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.mostrarArtistas.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exitProcedure();
            }
        });
        
        
        this.crearArtistas.mostrarArtistasBtn.addActionListener((ActionEvent e) -> {
            CambiarAMostrarArtista(); 
        });
        this.crearArtistas.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.crearArtistas.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exitProcedure();
            }
        });
        
        this.tipoDeCantor.CanarioToggle.addActionListener((ActionEvent e) -> {
            this.tipoDeCantor.ArtistaToggle.setSelected(false);
            this.tipoDeCantor.GalloToggle.setSelected(false);
            tipoDeArtistaCrear = "Canario";
        });
        this.tipoDeCantor.ArtistaToggle.addActionListener((ActionEvent e) -> {
            this.tipoDeCantor.CanarioToggle.setSelected(false);
            this.tipoDeCantor.GalloToggle.setSelected(false);
            tipoDeArtistaCrear = "Artista";
        });
        this.tipoDeCantor.GalloToggle.addActionListener((ActionEvent e) -> {
            this.tipoDeCantor.ArtistaToggle.setSelected(false);
            this.tipoDeCantor.CanarioToggle.setSelected(false);
            tipoDeArtistaCrear = "Gallo";
        });
    }

    
    public void exitProcedure() {
        int value = JOptionPane.showConfirmDialog(null,"Desea Cerrar El Programa?");
        if(value==0){
            this.mostrarArtistas.dispose();
            System.exit(0);
        }
    }
    public void iniciar() {
        
        this.mostrarArtistas.setVisible(true);
        this.mostrarArtistas.PanelDataArtista.setVisible(false);
        
      
        this.mostrarArtistas.ArtistasContainer.setListData(RecargarArtistas());
    }
    
    public void TipoDeArtista(){
            
        this.tipoDeCantor.setVisible(true);
    }
    
    public void continuarCreacion(Artista artista){
        this.tipoDeCantor.setVisible(false);
        CambiarACrearArtista(artista,tipoDeArtistaCrear);
    }
    
    public void CambiarACrearArtista(Artista artista,String tipoCantor){
        
        if (tipoCantor == null) tipoCantor = "Artista";
        System.out.println(tipoCantor);
        instrumentos[0][0] = "Voz";instrumentos[0][1] = "Viento";
        instrumentos[1][0] = "Guitarra";instrumentos[1][1] = "Cuerda";
        instrumentos[2][0] = "Piano";instrumentos[2][1] = "Cuerda";
        instrumentos[3][0] = "Bajo";instrumentos[3][1] = "Cuerda";
        instrumentos[4][0] = "Violin";instrumentos[4][1] = "Cuerda";
        instrumentos[5][0] = "Bateria";instrumentos[5][1] = "Percusion";
        instrumentos[6][0] = "Bombo";instrumentos[6][1] = "Percusion";
        instrumentos[7][0] = "Flauta";instrumentos[7][1] = "Viento";
        this.crearArtistas.DesplegableNombreInstrumento.addItem(instrumentos[0][0]);
        this.crearArtistas.DesplegableNombreInstrumento.addItem(instrumentos[1][0]);
        this.crearArtistas.DesplegableNombreInstrumento.addItem(instrumentos[2][0]);
        this.crearArtistas.DesplegableNombreInstrumento.addItem(instrumentos[3][0]);
        this.crearArtistas.DesplegableNombreInstrumento.addItem(instrumentos[4][0]);
        this.crearArtistas.DesplegableNombreInstrumento.addItem(instrumentos[5][0]);
        this.crearArtistas.DesplegableNombreInstrumento.addItem(instrumentos[6][0]);
        this.crearArtistas.DesplegableNombreInstrumento.addItem(instrumentos[7][0]);
        
        
        int e = 0;
        if(artista != null){
            
            this.crearArtistas.crearArtistaBtn.removeActionListener(editarArtista);
            this.crearArtistas.crearArtistaBtn.removeActionListener(crearArtista);
            this.crearArtistas.crearArtistaBtn.addActionListener(editarArtista);
            //System.out.println("CantidadActionListeners: "+this.crearArtistas.crearArtistaBtn.getActionListeners().length);
            
            for (int i = 0; i < this.instrumentos.length; i++) {
                if(this.instrumentos[i][0] == artista.instrumento.nombre){
                    e = i;
                    break;
                }
            }
        }else{
           
            this.crearArtistas.crearArtistaBtn.removeActionListener(crearArtista);
            this.crearArtistas.crearArtistaBtn.removeActionListener(editarArtista);
            this.crearArtistas.crearArtistaBtn.addActionListener(crearArtista);
            //System.out.println("CantidadActionListeners: "+this.crearArtistas.crearArtistaBtn.getActionListeners().length);
        } 
        this.mostrarArtistas.setVisible(false);
        this.crearArtistas.setVisible(true);
        if(tipoCantor == "Gallo" || tipoCantor == "Canario"){
            this.crearArtistas.DesplegableNombreInstrumento.setVisible(false);
            this.crearArtistas.InstrumentoLabel.setVisible(false);
          
        }else   {
            this.crearArtistas.DesplegableNombreInstrumento.setVisible(true);
            this.crearArtistas.InstrumentoLabel.setVisible(true);
            this.crearArtistas.alegreLabel.setVisible(true);
            this.crearArtistas.alegria.setVisible(true);
            this.crearArtistas.horarioLabel.setVisible(true);
            this.crearArtistas.horarioText.setVisible(true);
        }
        this.crearArtistas.NombreArtista.setText(artista==null? "":artista.getNombre());
        if (artista != null) {
            this.crearArtistas.FechaNacimientoArtista.setDate(artista.getFechaNacimiento());
        }
        TextPrompt placeholderNombreArtista = new TextPrompt("Juan,Pepe,Rodrigo...", this.crearArtistas.NombreArtista);
        placeholderNombreArtista.changeAlpha(0.75f);
        TextPrompt placeholderTipoMusicaArtista = new TextPrompt("mañana, tarde, noche, madrugada..", this.crearArtistas.horarioText);
        placeholderTipoMusicaArtista.changeAlpha(0.75f);
     
        
    }
    public void CambiarAMostrarArtista(){
        this.crearArtistas.setVisible(false);
        this.mostrarArtistas.setVisible(true);
        this.mostrarArtistas.PanelDataArtista.setVisible(false);
        
        this.mostrarArtistas.ArtistasContainer.setListData(RecargarArtistas());
    }
    
    public void CrearArtista(Boolean editandoArtis,String tipoDeArtista){
        String nombre = this.crearArtistas.NombreArtista.getText();
        Date fechaNacimiento = this.crearArtistas.FechaNacimientoArtista.getDate();
        boolean alegria=true;
        String horario = "";
        String instrumentoNombre = "";
        String instrumentoTipo = "";
        if("Artista".equals(tipoDeArtista)){
            alegria = this.crearArtistas.alegria.isSelected();
            horario = this.crearArtistas.horarioText.getText();
            instrumentoNombre = this.instrumentos[this.crearArtistas.DesplegableNombreInstrumento.getSelectedIndex()][0];
            instrumentoTipo = this.instrumentos[this.crearArtistas.DesplegableNombreInstrumento.getSelectedIndex()][1];
        }
        System.out.println(fechaNacimiento);
        if(nombre.length()>0) nombre = nombre.substring(0,1).toUpperCase() + nombre.substring(1);
        else return;
        if ("Artista".equals(tipoDeArtista)){
            if(horario.length()>0) horario = horario.substring(0,1).toUpperCase() + horario.substring(1);
            else return;
        }
        Momento momentoCantor= new Momento(horario,alegria);
        
        if(editandoArtis){
            //.println("Editando Artista");
            if ("Artista".equals(tipoDeArtista)){ 
                this.artistasArray.get(indexCantorEditandose).setValues(instrumentoNombre,instrumentoTipo,nombre,momentoCantor);

            }
            else if("Gallo".equals(tipoDeArtista)){
                this.gallosArray.get(indexCantorEditandose).setValues(nombre,momentoCantor);

            }
            else if("Canario".equals(tipoDeArtista)){
                this.canariosArray.get(indexCantorEditandose).setValues(nombre,momentoCantor);

            }
            
            //.println("lastIndex "+LastIndex);
        }else{
            if ("Artista".equals(tipoDeArtista)){
                Instrumento instrumentoCantor =new Instrumento(instrumentoNombre,instrumentoTipo);
                Artista newArtista = new Artista(instrumentoCantor,tipoDeArtista,nombre,fechaNacimiento,momentoCantor);
                artistasArray.add(newArtista);
            }
            else if("Gallo".equals(tipoDeArtista)){
                Gallo newGallo = new Gallo(tipoDeArtista,nombre,fechaNacimiento,momentoCantor);
                gallosArray.add(newGallo);
            }
            else if("Canario".equals(tipoDeArtista)){
                Canario newCanario = new Canario(tipoDeArtista,nombre,fechaNacimiento,momentoCantor);
                canariosArray.add(newCanario);
            }
            
        }
        
        CambiarAMostrarArtista();
    }
    
   
    
    public void MostrarInfoArtista(){
        indexCantorEditandose = this.mostrarArtistas.ArtistasContainer.getSelectedIndex();
        System.out.println(indexCantorEditandose+" index cantor editandoce");
        System.out.println(cantoresArray.length+" logitd array");
        
        if(indexCantorEditandose==-1) return;
        if(cantoresArray[indexCantorEditandose][1] == "Artista"){
            this.artistaSeleccionado = this.artistasArray.get(indexCantorEditandose);
            this.tipoCantorEditandose = cantoresArray[indexCantorEditandose][1];
        }
        else if(cantoresArray[indexCantorEditandose][1] == "Gallo"){
            System.out.println("entre");
            this.galloSeleccionado = this.gallosArray.get(indexCantorEditandose - (this.artistasArray.size()));
            this.tipoCantorEditandose = cantoresArray[indexCantorEditandose][1];
        }
        else if(cantoresArray[indexCantorEditandose][1] == "Canario"){
            this.canarioSeleccionado = this.canariosArray.get(indexCantorEditandose - (this.artistasArray.size()-1) - (this.gallosArray.size()-1));
            this.tipoCantorEditandose = cantoresArray[indexCantorEditandose][1];
        }
        System.out.println(tipoCantorEditandose+" tipo cantor");
        switch(tipoCantorEditandose){
            case "Artista":
                this.mostrarArtistas.NombreArtista.setText("Nombre: " + artistaSeleccionado.getNombre());
                this.mostrarArtistas.InstrumentoArtista.setVisible(true);
                this.mostrarArtistas.InstrumentoArtista.setText("Intrumento: " + artistaSeleccionado.instrumento.nombre + ", es de: "+artistaSeleccionado.instrumento.tipo);
                this.mostrarArtistas.esMusicaAlegre.setText(artistaSeleccionado.cuando.isAlegria()?"la musica es alegre":"la musica no es alegre");
                this.mostrarArtistas.horario.setText(artistaSeleccionado.cuando.horario());
                this.mostrarArtistas.FechaDeNacimientoArtista.setText( "Edad: " + String.valueOf(artistaSeleccionado.Calcular_edad()) );
                break;
            case "Gallo":
                this.mostrarArtistas.NombreArtista.setText("Nombre: " + galloSeleccionado.getNombre());
                this.mostrarArtistas.InstrumentoArtista.setVisible(false);
                this.mostrarArtistas.esMusicaAlegre.setText(galloSeleccionado.cuando.isAlegria()?"la musica es alegre":"la musica no es alegre");
                this.mostrarArtistas.horario.setText(galloSeleccionado.cuando.horario());
                this.mostrarArtistas.FechaDeNacimientoArtista.setText( "Edad: " + String.valueOf(galloSeleccionado.Calcular_edad()) );
                break;
            case "Canario":
                this.mostrarArtistas.NombreArtista.setText("Nombre: " + canarioSeleccionado.getNombre());
                this.mostrarArtistas.InstrumentoArtista.setVisible(false);
                this.mostrarArtistas.esMusicaAlegre.setText(canarioSeleccionado.cuando.isAlegria()?"la musica es alegre":"la musica no es alegre");
                this.mostrarArtistas.horario.setText(canarioSeleccionado.cuando.horario());
                this.mostrarArtistas.FechaDeNacimientoArtista.setText( "Edad: " + String.valueOf(canarioSeleccionado.Calcular_edad()) );
                break;
        }
        
        
        this.mostrarArtistas.PanelDataArtista.setVisible(true);
    }
    
    
    public void EditarArtista(){
        this.tipoDeCantor.SiguienteBotton.removeActionListener(continuarCreacion);
        this.tipoDeCantor.SiguienteBotton.removeActionListener(continuarEdicion);
        this.TipoDeArtista();
        this.tipoDeCantor.SiguienteBotton.addActionListener(continuarEdicion);
    }
    
    public String[] RecargarArtistas(){
        
        Integer dimensionArray = this.artistasArray.size()+this.gallosArray.size()+this.canariosArray.size();
        System.out.println(dimensionArray);
        cantoresArray = new String[dimensionArray][2];
        String arrayList[] = new String[dimensionArray];
        
        int i,j,e,indexArrayList = 0;
        for (i = 0; i < this.artistasArray.size(); i++) {
            arrayList[indexArrayList] = this.artistasArray.get(i).getNombre();
            cantoresArray[i][0]= this.artistasArray.get(i).getNombre();
            cantoresArray[i][1]= this.artistasArray.get(i).getTipo();
            indexArrayList++;
        }
        for (j = 0; j < this.gallosArray.size(); j++) {
            arrayList[indexArrayList] = this.gallosArray.get(j).getNombre();
            cantoresArray[j][0]= this.gallosArray.get(j).getNombre();
            cantoresArray[j][1]= this.gallosArray.get(j).getTipo();
            indexArrayList++;
        }
        for (e = 0; e < this.canariosArray.size(); e++) {
            arrayList[indexArrayList] = this.canariosArray.get(e).getNombre();
            cantoresArray[e][0]= this.canariosArray.get(e).getNombre();
            cantoresArray[e][1]= this.canariosArray.get(e).getTipo();
            indexArrayList++;
        }
        return arrayList;
    }
    public void EliminarArtista(){
        int value = JOptionPane.showConfirmDialog(null,"Desea Borrar El Artista?");
        if(value == 0){
            this.artistasArray.remove(this.artistasArray.get(indexCantorEditandose));
            this.mostrarArtistas.ArtistasContainer.setListData(RecargarArtistas());
            //System.out.println("new Length: "+this.artistasArray.size());
            this.mostrarArtistas.PanelDataArtista.setVisible(false);
        }
        
        
    }

    
     
      
    }

