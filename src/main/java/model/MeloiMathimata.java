package model;




/**
 * MeloiMathimata generated by hbm2java
 */
public class MeloiMathimata  implements java.io.Serializable {


     private MeloiMathimataId id;
     private Mathimata mathimata;
     private Meloi meloi;

    public MeloiMathimata() {
    }

    public MeloiMathimata(MeloiMathimataId id, Mathimata mathimata, Meloi meloi) {
       this.id = id;
       this.mathimata = mathimata;
       this.meloi = meloi;
    }
   
    public MeloiMathimataId getId() {
        return this.id;
    }
    
    public void setId(MeloiMathimataId id) {
        this.id = id;
    }
    public Mathimata getMathimata() {
        return this.mathimata;
    }
    
    public void setMathimata(Mathimata mathimata) {
        this.mathimata = mathimata;
    }
    public Meloi getMeloi() {
        return this.meloi;
    }
    
    public void setMeloi(Meloi meloi) {
        this.meloi = meloi;
    }




}


