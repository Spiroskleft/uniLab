package model;
// Generated Dec 6, 2016 11:32:43 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EreynitikaErga generated by hbm2java
 */
public class EreynitikaErga  implements java.io.Serializable {


    private Integer ergoId;
    private KatastasiErgou katastasiErgou = new KatastasiErgou();
    private String titlos = "";
    private Date etos = new Date();
    private Date etos_ews = new Date();
    private String perigrafi = "";
    private String xrimOrgmanismos = "";
    private Set melois = new HashSet(0);

    public EreynitikaErga() {
    }


    public EreynitikaErga(KatastasiErgou katastasiErgou, String titlos, Date etos, Date etos_ews) {
        this.katastasiErgou = katastasiErgou;
        this.titlos = titlos;
        this.etos = etos;
        this.etos_ews = etos_ews;
    }

    public EreynitikaErga(KatastasiErgou katastasiErgou, String titlos, Date etos,Date etos_ews, String perigrafi, String xrimOrgmanismos, Set melois) {
        this.katastasiErgou = katastasiErgou;
        this.titlos = titlos;
        this.etos = etos;
        this.etos_ews = etos_ews;
        this.perigrafi = perigrafi;
        this.xrimOrgmanismos = xrimOrgmanismos;
        this.melois = melois;
    }

    public Integer getErgoId() {
        return this.ergoId;
    }

    public void setErgoId(Integer ergoId) {
        this.ergoId = ergoId;
    }

    public KatastasiErgou getKatastasiErgou() {
        return this.katastasiErgou;
    }

    public void setKatastasiErgou(KatastasiErgou katastasiErgou) {
        this.katastasiErgou = katastasiErgou;
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

    public Date getEtos_ews() {
        return this.etos_ews;
    }

    public void setEtos_ews(Date etos_ews) {
        this.etos_ews = etos_ews;
    }

    public String getPerigrafi() {
        return this.perigrafi;
    }

    public void setPerigrafi(String perigrafi) {
        this.perigrafi = perigrafi;
    }

    public String getXrimOrgmanismos() {
        return this.xrimOrgmanismos;
    }

    public void setXrimOrgmanismos(String xrimOrgmanismos) {
        this.xrimOrgmanismos = xrimOrgmanismos;
    }

    public Set getMelois() {
        return this.melois;
    }

    public void setMelois(Set melois) {
        this.melois = melois;
    }
}




