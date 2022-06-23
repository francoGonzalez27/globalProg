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
    public ArrayList<String> tipoCantoresArray = new ArrayList();
    public ArrayList<Artista> artistasArray = new ArrayList();
    public ArrayList<Gallo> gallosArray = new ArrayList();
    public ArrayList<Canario> canariosArray = new ArrayList();
    public Artista artistaSeleccionado;
    public Gallo galloSeleccionado;
    public Canario canarioSeleccionado;
    
    public Integer indexCantorEditandose;
    public String tipoCantorEditandose;
    public String tipoDeArtistaCrear = "Artista";
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
                continuarCreacion(false);
            }
    };
    public  ActionListener continuarEdicion = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                continuarCreacion(true);
            }
    };
    public Controlador(JFrame_CrearArtistas crearArtistas, JFrame_MostrarArtistas mostrarArtistas, JFrame_TipoDeCantor tipoDeCantor) {
        this.crearArtistas = crearArtistas;
        this.mostrarArtistas = mostrarArtistas;
        this.tipoDeCantor = tipoDeCantor;
        
        this.mostrarArtistas.crearArtistaBtn.addActionListener((ActionEvent e) -> {
            continuarCreacion( false);
            //CambiarACrearArtista(null); 
        });
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
        this.crearArtistas.DesplegableTipoArtista.addActionListener((ActionEvent e) -> {
            GuardarTipoArtista();
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
    
    public void GuardarTipoArtista(){
        int id = this.crearArtistas.DesplegableTipoArtista.getSelectedIndex();
        switch (id) {
            case 0:
                tipoDeArtistaCrear = "Artista";
                break;
            case 1:
                tipoDeArtistaCrear = "Gallo";
                break;
            case 2:
                tipoDeArtistaCrear = "Canario";
                break;
            default:
                break;
        }
        if(tipoDeArtistaCrear == "Gallo" || tipoDeArtistaCrear == "Canario"){
            this.crearArtistas.DesplegableNombreInstrumento.setVisible(false);
            this.crearArtistas.InstrumentoLabel.setVisible(false);
          
        }else   {
            this.crearArtistas.DesplegableNombreInstrumento.setVisible(true);
            this.crearArtistas.InstrumentoLabel.setVisible(true);
        }
    }
    
    public void continuarCreacion(boolean editando){
        this.tipoDeCantor.setVisible(false);
        CambiarACrearArtista(tipoDeArtistaCrear,editando);
    }
    
    public void CambiarACrearArtista(String tipoCantor,boolean editando){
        
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
        if(editando){
            
            this.crearArtistas.crearArtistaBtn.removeActionListener(editarArtista);
            this.crearArtistas.crearArtistaBtn.removeActionListener(crearArtista);
            this.crearArtistas.crearArtistaBtn.addActionListener(editarArtista);
            //System.out.println("CantidadActionListeners: "+this.crearArtistas.crearArtistaBtn.getActionListeners().length);
            
            
        }else{
           
            this.crearArtistas.crearArtistaBtn.removeActionListener(crearArtista);
            this.crearArtistas.crearArtistaBtn.removeActionListener(editarArtista);
            this.crearArtistas.crearArtistaBtn.addActionListener(crearArtista);
            //System.out.println("CantidadActionListeners: "+this.crearArtistas.crearArtistaBtn.getActionListeners().length);
        } 
        this.mostrarArtistas.setVisible(false);
        this.crearArtistas.setVisible(true);
        
        if(editando){
            switch (tipoCantor) {
            case "Artista":
                for (int i = 0; i < this.instrumentos.length; i++) {
                        if(this.instrumentos[i][0] == artistaSeleccionado.instrumento.nombre){
                            e = i;
                            break;
                        }
                    }
                this.crearArtistas.NombreArtista.setText(artistaSeleccionado.getNombre());
                this.crearArtistas.FechaNacimientoArtista.setDate(artistaSeleccionado.getFechaNacimiento());
                
                this.crearArtistas.horarioText.setText(artistaSeleccionado.cuando.horario());
                this.crearArtistas.alegria.setSelected(artistaSeleccionado.cuando.isAlegria());
                break;
            case "Gallo":
                this.crearArtistas.NombreArtista.setText(galloSeleccionado.getNombre());
                this.crearArtistas.FechaNacimientoArtista.setDate(galloSeleccionado.getFechaNacimiento());
                
                this.crearArtistas.horarioText.setText(galloSeleccionado.cuando.horario());
                this.crearArtistas.alegria.setSelected(galloSeleccionado.cuando.isAlegria());
                break;
            case "Canario":
                this.crearArtistas.NombreArtista.setText(canarioSeleccionado.getNombre());
                this.crearArtistas.FechaNacimientoArtista.setDate(canarioSeleccionado.getFechaNacimiento());
                
                this.crearArtistas.horarioText.setText(canarioSeleccionado.cuando.horario());
                this.crearArtistas.alegria.setSelected(canarioSeleccionado.cuando.isAlegria());
                break;
            default:
                throw new AssertionError();
            }
        }else{
            this.crearArtistas.NombreArtista.setText(null);
            this.crearArtistas.FechaNacimientoArtista.setDate(null);
            this.crearArtistas.horarioText.setText(null);
            this.crearArtistas.alegria.setSelected(true);
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
        boolean alegria = this.crearArtistas.alegria.isSelected();
        String  horario = this.crearArtistas.horarioText.getText();
        String instrumentoNombre = "";
        String instrumentoTipo = "";
        System.out.println(tipoDeArtista);
        if("Artista".equals(tipoDeArtista)){
            
            instrumentoNombre = this.instrumentos[this.crearArtistas.DesplegableNombreInstrumento.getSelectedIndex()][0];
            instrumentoTipo = this.instrumentos[this.crearArtistas.DesplegableNombreInstrumento.getSelectedIndex()][1];
        }
        if(nombre.length()>0) nombre = nombre.substring(0,1).toUpperCase() + nombre.substring(1);
        else return;
        if(horario.length()>0) horario = horario.substring(0,1).toUpperCase() + horario.substring(1);
        else return;
        
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
        //System.out.println("/////////");
        //System.out.println(indexCantorEditandose);
        if(indexCantorEditandose==-1) return;
        //System.out.println(tipoCantoresArray.get(indexCantorEditandose));
        
        
        if(tipoCantoresArray.get(indexCantorEditandose) == "Artista"){
            //System.out.println(indexCantorEditandose+" index cantor editandoce");
            //System.out.println(artistasArray.size()+" logitd array");
            this.artistaSeleccionado = this.artistasArray.get(indexCantorEditandose);
            this.tipoCantorEditandose = tipoCantoresArray.get(indexCantorEditandose);
        }
        else if(tipoCantoresArray.get(indexCantorEditandose) == "Gallo"){
            //System.out.println(gallosArray.size()+" logitd array");
            int index = indexCantorEditandose - this.artistasArray.size();
            //System.out.println(index+" index");
            this.galloSeleccionado = this.gallosArray.get(index);
            this.tipoCantorEditandose = tipoCantoresArray.get(indexCantorEditandose);
        }
        else if(tipoCantoresArray.get(indexCantorEditandose) == "Canario"){
            //System.out.println(indexCantorEditandose - (this.artistasArray.size()) - (this.gallosArray.size())+" index cantor editandoce");
            int index = indexCantorEditandose - this.artistasArray.size() - this.gallosArray.size();
            //System.out.println(canariosArray.size()+" logitd array");
            //System.out.println(index+" index");
            this.canarioSeleccionado = this.canariosArray.get(index);
            this.tipoCantorEditandose = tipoCantoresArray.get(indexCantorEditandose);
        }
        this.mostrarArtistas.Title.setText("Info de " + this.tipoCantorEditandose);
        switch(tipoCantorEditandose){
            case "Artista":
                this.mostrarArtistas.NombreArtista.setText("Nombre: " + artistaSeleccionado.getNombre());
                this.mostrarArtistas.InstrumentoArtista.setVisible(true);
                this.mostrarArtistas.InstrumentoArtista.setText("Intrumento: " + artistaSeleccionado.instrumento.nombre + ", es de: "+artistaSeleccionado.instrumento.tipo);
                this.mostrarArtistas.esMusicaAlegre.setText(artistaSeleccionado.cuando.isAlegria() == true ?"Su musica es alegre":"Su musica no es alegre");
                this.mostrarArtistas.horario.setText(artistaSeleccionado.cuando.horario());
                this.mostrarArtistas.FechaDeNacimientoArtista.setText( "Edad: " + String.valueOf(artistaSeleccionado.Calcular_edad()) );
                this.mostrarArtistas.ReproducirSon.setVisible(true);
                break;
            case "Gallo":
                this.mostrarArtistas.NombreArtista.setText("Nombre: " + galloSeleccionado.getNombre());
                this.mostrarArtistas.InstrumentoArtista.setVisible(false);
                this.mostrarArtistas.esMusicaAlegre.setText(galloSeleccionado.cuando.isAlegria() == true ?"Su musica es alegre":"Su musica no es alegre");
                this.mostrarArtistas.horario.setText(galloSeleccionado.cuando.horario());
                this.mostrarArtistas.FechaDeNacimientoArtista.setText( "Edad: " + String.valueOf(galloSeleccionado.Calcular_edad()) );
                this.mostrarArtistas.ReproducirSon.setVisible(false);
                break;
            case "Canario":
                this.mostrarArtistas.NombreArtista.setText("Nombre: " + canarioSeleccionado.getNombre());
                this.mostrarArtistas.InstrumentoArtista.setVisible(false);
                this.mostrarArtistas.esMusicaAlegre.setText(canarioSeleccionado.cuando.isAlegria() == true ?"Su musica es alegre":"Su musica no es alegre");
                this.mostrarArtistas.horario.setText(canarioSeleccionado.cuando.horario());
                this.mostrarArtistas.FechaDeNacimientoArtista.setText( "Edad: " + String.valueOf(canarioSeleccionado.Calcular_edad()) );
                this.mostrarArtistas.ReproducirSon.setVisible(false);
                break;
        }
        
        
        this.mostrarArtistas.PanelDataArtista.setVisible(true);
    }
    
    
    public void EditarArtista(){
        continuarCreacion(true);
    }
    
    public String[] RecargarArtistas(){
        
        Integer dimensionArray = this.artistasArray.size()+this.gallosArray.size()+this.canariosArray.size();
        String arrayList[] = new String[dimensionArray];
        tipoCantoresArray.clear();
        int i,j,e,indexArrayList = 0;
        for (i = 0; i < this.artistasArray.size(); i++) {
            arrayList[indexArrayList] = this.artistasArray.get(i).getNombre();
            tipoCantoresArray.add(this.artistasArray.get(i).getTipo());
            indexArrayList++;
        }
        for (j = 0; j < this.gallosArray.size(); j++) {
            arrayList[indexArrayList] = this.gallosArray.get(j).getNombre();
            tipoCantoresArray.add(this.gallosArray.get(j).getTipo());
            indexArrayList++;
        }
        for (e = 0; e < this.canariosArray.size(); e++) {
            arrayList[indexArrayList] = this.canariosArray.get(e).getNombre();
            tipoCantoresArray.add(this.canariosArray.get(e).getTipo());
            indexArrayList++;
        }
        return arrayList;
    }
    public void EliminarArtista(){
        int value = JOptionPane.showConfirmDialog(null,"Desea Borrar El Artista?");
        if(value == 0){
            switch (this.tipoCantorEditandose) {
                case "Artista":
                    this.artistasArray.remove(this.artistasArray.get(indexCantorEditandose));
                    this.mostrarArtistas.ArtistasContainer.setListData(RecargarArtistas());
                    break;
                case "Gallo":
                    this.gallosArray.remove(this.gallosArray.get(this.artistasArray.size() > 0 ?
                        indexCantorEditandose - (this.artistasArray.size()) 
                        : indexCantorEditandose));
                    this.mostrarArtistas.ArtistasContainer.setListData(RecargarArtistas());
                    break;
                case "Canario":
                    this.canariosArray.remove(this.canariosArray.get(
                    this.artistasArray.size() > 0 ?
                        this.gallosArray.size() > 0 ?
                            indexCantorEditandose - this.artistasArray.size() - this.gallosArray.size()
                            : indexCantorEditandose - this.artistasArray.size()
                    :indexCantorEditandose));
                    this.mostrarArtistas.ArtistasContainer.setListData(RecargarArtistas());
                    break;
                default:
                    throw new AssertionError();
            }
            
            this.mostrarArtistas.PanelDataArtista.setVisible(false);
        }
        
        
    }

    
     
      
    }

