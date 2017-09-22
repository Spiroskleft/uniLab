package model;
// Generated Dec 6, 2016 11:32:43 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Dimosieuseis generated by hbm2java
 */
public class Dimosieuseis  implements java.io.Serializable {


     private Integer dimosieusiId;
    private EidosDimosieusis eidosDimosieusis = new EidosDimosieusis();
    private ThematikiEnotita thematikiEnotita = new ThematikiEnotita();
     private String titlos = "";
    private Date etos = new Date();
    private Set melois = new HashSet(0);

    public Dimosieuseis() {
    }

	
    public Dimosieuseis(EidosDimosieusis eidosDimosieusis, ThematikiEnotita thematikiEnotita, String titlos, Date etos) {
        this.eidosDimosieusis = eidosDimosieusis;
        this.thematikiEnotita = thematikiEnotita;
        this.titlos = titlos;
        this.etos = etos;
    }

    public Dimosieuseis(EidosDimosieusis eidosDimosieusis, ThematikiEnotita thematikiEnotita, String titlos, Date etos, Set melois) {
       this.eidosDimosieusis = eidosDimosieusis;
       this.thematikiEnotita = thematikiEnotita;
       this.titlos = titlos;
       this.etos = etos;
        this.melois = melois;
    }
   
    public Integer getDimosieusiId() {
        return this.dimosieusiId;
    }
    
    public void setDimosieusiId(Integer dimosieusiId) {
        this.dimosieusiId = dimosieusiId;
    }
    public EidosDimosieusis getEidosDimosieusis() {
        return this.eidosDimosieusis;
    }
    
    public void setEidosDimosieusis(EidosDimosieusis eidosDimosieusis) {
        this.eidosDimosieusis = eidosDimosieusis;
    }
    public ThematikiEnotita getThematikiEnotita() {
        return this.thematikiEnotita;
    }
    
    public void setThematikiEnotita(ThematikiEnotita thematikiEnotita) {
        this.thematikiEnotita = thematikiEnotita;
    }
    public String getTitlos() {
        return this.titlos;
    }
    
    public void setTitlos(String titlos) {
        this.titlos = titlos;
    }
    public Date getEtos() {
        return this.etos;
    }
    
    public void setEtos(Date etos) {
        this.etos = etos;
    }

    public Set getMelois() {
        return this.melois;
    }

    public void setMelois(Set melois) {
        this.melois = melois;
    }




}


