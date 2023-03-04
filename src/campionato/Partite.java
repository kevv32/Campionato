package campionato;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import static java.lang.Character.toUpperCase;
import java.util.ArrayList;
import static java.util.Arrays.sort;
import java.util.Date;


public class Partite implements Serializable{
    private char girone;
    private int giornata;
    private Date data;
    private String squadraCasa;
    private String squadraOspite;
    private int golCasa;
    private int golOspite;

    public Partite() {
    }

    public Partite(char girone, int giornata, Date data, String squadraCasa,
            String squadraOspite, int golCasa, int golOspite) throws Eccezione {
        this.giornata = giornata;
        this.data = data;
        this.squadraCasa = squadraCasa;
        this.squadraOspite = squadraOspite;
        this.golCasa = golCasa;
        this.golOspite = golOspite;
        this.girone = toUpperCase(girone);
        if(this.girone != 'A' && this.girone != 'R'){
            throw new Eccezione("Girone inesistente");
        }
        if(giornata < 0){
            throw new Eccezione("La giornata non può essere negativa");
        }
        if(this.squadraCasa == null){
            throw new Eccezione("Il nome della squadra non può essere nullo");
        }
        if(this.squadraOspite == null){
            throw new Eccezione("Il nome squadra non può essere nullo");
        }
        if(golCasa < 0 || golOspite < 0){
            throw new Eccezione("I gol devono essere positivi");
        }
        if(squadraCasa.equals(squadraOspite)){
            throw new Eccezione("Il nome delle squadre deve essere diverso");
        }
    }

    public char getGirone() {
        return girone;
    }

    public void setGirone(char girone) throws Eccezione {
        this.girone = girone;
        girone = toUpperCase(girone);
        if(girone != 'A' && girone != 'R'){
            throw new Eccezione("Girone inesistente");
        }
    }

    public int getGiornata() {
        return giornata;
    }

    public void setGiornata(int giornata) throws Eccezione {
        this.giornata = giornata;
        if(giornata < 0){
            throw new Eccezione("La giornata non può essere negativa");
        }
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getSquadraCasa(){
        return squadraCasa;
    }

    public void setSquadraCasa(String squadraCasa) throws Eccezione {
        this.squadraCasa = squadraCasa;
        if(squadraCasa == null || squadraCasa.equals(squadraOspite)){
            throw new Eccezione("Dati inseriti non validi");
        }
    }

    public String getSquadraOspite() {
        return squadraOspite;
    }

    public void setSquadraOspite(String squadraOspite) throws Eccezione {
        this.squadraOspite = squadraOspite;
        if(squadraOspite == null || squadraCasa.equals(squadraOspite)){
            throw new Eccezione("Dati inseriti non validi");
        }
    }

    public int getGolCasa() {
        return golCasa;
    }

    public void setGolCasa(int golCasa) throws Eccezione {
        this.golCasa = golCasa;
        if(golCasa < 0){
            throw new Eccezione("I gol devono essere positivi");
        }
    }

    public int getGolOspite() {
        return golOspite;
    }

    public void setGolOspite(int golOspite) throws Eccezione {
        this.golOspite = golOspite;
        if(golOspite < 0){
            throw new Eccezione("I gol devono essere positivi");
        }
    }

    @Override
    public String toString() {
        return "" + squadraCasa +" "+ golCasa + " - " +  golOspite+" "+ squadraOspite;
    }
    
    public boolean creaFile() throws IOException{
        File file = new File("partite.dat");
        if(file.exists()){
            return false;
        } else {
            file.createNewFile();
        }
        return true;
    }
    public void eliminaFile(){
        File file = new File("partite.dat");
        file.delete();
    }
    public Punteggio[] ritornaClassifica() throws FileNotFoundException, IOException, ClassNotFoundException, Eccezione{
        Punteggio[] v;
        Partite[] partite = new Partite[0];
        partite = leggiFile();
        if(partite != null){
            v = toPunteggio(partite);
            sort(v);
            return v;
        } else {
            return null;
        }
    } 
    
    private Punteggio[] toPunteggio(Partite[] partite) throws Eccezione{
       Punteggio[] v = new Punteggio[0];
       int pos;
       add(v[0], partite[0], 'c' );
       add(v[1], partite[0], 'o' );
       for(int i = 1; i < partite.length; i++){
           pos = isPresente(v, partite[i].getSquadraCasa());
           if(pos == -1){
                add(v[v.length], partite[i], 'c');
           } else{
                add(v[pos], partite[i], 'c');
           }
           pos = isPresente(v, partite[i].getSquadraOspite());
           if(pos == -1){
                add(v[v.length], partite[i], 'o');
           } else{
                add(v[pos], partite[i], 'o');
           }
       }    
        return v;
    }
    
    private int isPresente(Punteggio[] v, String squadra) {
        for(int i = 0; i < v.length; i++){
            if(v[i].nomeSquadra.equals(squadra)){
                return i;
            }
        }
        return -1;
    }

    private void add(Punteggio punti, Partite partita, char par) throws Eccezione {
        int pun;
        if(par == 'c'){
            punti.setNomeSquadra(partita.getSquadraCasa());
            punti.setGolF(punti.getGolF() + partita.getGolCasa());
            punti.setGolS(punti.getGolS() + partita.getGolOspite());
            if(partita.getGolCasa() > partita.getGolOspite()){
                pun = 3;
            } else if(partita.getGolCasa() == partita.getGolOspite()){
                pun = 1;
            } else {
                pun = 0;
            }
            punti.setPunteggio(punti.getPunteggio() + pun);
        } else {
            punti.setNomeSquadra(partita.getSquadraOspite());
            punti.setGolF(punti.getGolF() + partita.getGolOspite());
            punti.setGolS(punti.getGolS() + partita.getGolCasa());
            if(partita.getGolOspite() > partita.getGolCasa()){
                pun = 3;
            } else if(partita.getGolOspite() == partita.getGolCasa()){
                pun = 1;
            } else {
                pun = 0;
            }
            punti.setPunteggio(punti.getPunteggio() + pun);
        }
    }
    public void updateFile() throws FileNotFoundException, IOException, ClassNotFoundException{
        Partite[] p = new Partite[0];
       
         p = leggiFile();
        
        File file = new File("Classifica.dat");
        FileOutputStream fos = new FileOutputStream(file, false);
        ObjectOutputStream out = new ObjectOutputStream(fos);
        for (int i = 0; i < p.length; i++) {
            out.writeObject(p[i]);
        }
        out.writeObject(this);
        fos.close();
    }

    private Partite[] leggiFile() throws IOException, ClassNotFoundException {
        ArrayList<Partite> v = new ArrayList<Partite>();
        Partite[] classifica = new Partite[0];
        try{
            boolean eof = false;
        File file = new File("Classifica.dat");
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fis);
        while (!eof) {
            try {
                v.add((Partite)in.readObject());
            } catch (EOFException e) {
                eof = true;
            }
        }

        classifica = v.toArray(new Partite[v.size()]);
        }catch(FileNotFoundException e){
            
        }
             
        return classifica;
    }

}



