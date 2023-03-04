package campionato;



public class Punteggio implements Comparable<Punteggio>{
    String nomeSquadra;
    int punteggio = 0;
    int golF = 0;
    int golS = 0;

    public Punteggio(){
    
    }
    public Punteggio(String nomeSquadra, int punteggio, int golF, int golS) throws Eccezione{
        this.nomeSquadra = nomeSquadra;
        this.punteggio = punteggio;
        this.golF = golF;
        this.golS = golS;
        if(punteggio < 0 || golF < 0 || golS < 0){
            throw new Eccezione("Dati inseriti non validi");
        }
    }

    public String getNomeSquadra() {
        return nomeSquadra;
    }

    public void setNomeSquadra(String nomeSquadra) {
        this.nomeSquadra = nomeSquadra;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(int punteggio) throws Eccezione {
        this.punteggio = punteggio;
        if(punteggio < 0 ){
            throw new Eccezione("Dati inseriti non validi");
        }
    }

    public int getGolF() {
        return golF;
    }

    public void setGolF(int golF) throws Eccezione {
        this.golF = golF;
        if(golF < 0 ){
            throw new Eccezione("Dati inseriti non validi");
        }
    }

    public int getGolS() {
        return golS;
    }

    public void setGolS(int golS) throws Eccezione {
        this.golS = golS;
        if(golS < 0 ){
            throw new Eccezione("Dati inseriti non validi");
        }
    }

    @Override
    public int compareTo(Punteggio p) {
        if(punteggio> p.punteggio){
           return 1;
        }else if(punteggio == p.punteggio){
           return 0;
        }else{
           return -1;
        }
    }   
}
