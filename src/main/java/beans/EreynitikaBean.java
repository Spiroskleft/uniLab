package beans;

import db.dbTransactions;
import model.EreynitikaErga;
import model.KatastasiErgou;
import model.Meloi;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@ManagedBean
@ViewScoped
public class EreynitikaBean implements Serializable {

    //variables
    private EreynitikaErga ereynitikaErga = new EreynitikaErga();

    private String countErga1 = "";
    //Lists
    private List<EreynitikaErga> ereynitikaErgaList = new ArrayList<EreynitikaErga>();
    private List<KatastasiErgou> katastasiErgouList = new ArrayList<KatastasiErgou>();


    private List<Meloi> meloiEreynitikaErgaList = new ArrayList<>();

    private List<Object[]> countCommonErgaList = new ArrayList<>();
    private List<Object> countErga = new ArrayList<>();

    public EreynitikaBean() {
    }

    @PostConstruct
    public void init() {

        try {
            setEreynitikaErgaList(((((List<EreynitikaErga>) (List<?>) db.dbTransactions.getAllObjectsSorted(EreynitikaErga.class.getCanonicalName(), "titlos", 0)))));
            setKatastasiErgouList(((((List<KatastasiErgou>) (List<?>) db.dbTransactions.getAllObjectsSorted(KatastasiErgou.class.getCanonicalName(), "katastasi", 0)))));
// Μετράει τον αριθμό των ερευνητικών έργων
            countErey();
            countCommonErga();

        } catch (Exception ex) {
            System.out.println(ex);
        }


    }

    public void clear() {
        setEreynitikaErga(new EreynitikaErga());

    }

    public void storeEreynitikaErga() {

        try {
            db.dbTransactions.storeObject(getEreynitikaErga());

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                FacesMessage msg = new FacesMessage("Επιτυχής Εισαγωγή", " νέου Ερευνητικού Έργου " + "" + (getEreynitikaErga().getTitlos()));
                FacesContext.getCurrentInstance().addMessage(null, msg);
                getEreynitikaErgaList().add(getEreynitikaErga());
            }
            countErey();
        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }


    public void updateEreynitikaErga() {

        try {
            db.dbTransactions.updateObject(getEreynitikaErga());

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Επιτυχής Ενημέρωση Έργου ", getEreynitikaErga().getTitlos());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                setEreynitikaErgaList(((((List<EreynitikaErga>) (List<?>) db.dbTransactions.getAllObjectsSorted(EreynitikaErga.class.getCanonicalName(), "etos", 0)))));

            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void deleteEreynitikaErga() {

        try {
            db.dbTransactions.deleteObject(getEreynitikaErga());

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                FacesMessage msg = new FacesMessage("Επιτυχής Διαγραφή ", " Έργου " +""+ (getEreynitikaErga().getTitlos()));
                FacesContext.getCurrentInstance().addMessage(null, msg);
                getEreynitikaErgaList().remove(getEreynitikaErga());
            }
            countErey();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void meloiErgou() {


        setMeloiEreynitikaErgaList((List<Meloi>) (List<?>) dbTransactions.getObjectsByStoreProcedure(Meloi.class.getCanonicalName(), "selectMeloiErgou", ereynitikaErga.getErgoId()));

        System.out.println(getMeloiEreynitikaErgaList().size());

    }
    public void countCommonErga() {
        countCommonErgaList = (List<Object[]>) (List<?>) db.dbTransactions.getObjectsByStoreProcedureGeneral("countCommonErga ", null);
        System.out.println(countCommonErgaList.size());
    }

    public void countErey() {
        List countErga = dbTransactions.getObjectsByStoreProcedureGeneral("countErga", null);
        setCountErga1(countErga.get(0).toString());

    }


    public EreynitikaErga getEreynitikaErga() {
        return ereynitikaErga;
    }

    public void setEreynitikaErga(EreynitikaErga ereynitikaErga) {
        this.ereynitikaErga = ereynitikaErga;
    }

    public List<EreynitikaErga> getEreynitikaErgaList() {
        return ereynitikaErgaList;
    }

    public void setEreynitikaErgaList(List<EreynitikaErga> ereynitikaErgaList) {
        this.ereynitikaErgaList = ereynitikaErgaList;
    }

    public List<KatastasiErgou> getKatastasiErgouList() {
        return katastasiErgouList;
    }

    public void setKatastasiErgouList(List<KatastasiErgou> katastasiErgouList) {
        this.katastasiErgouList = katastasiErgouList;
    }


    public String getCountErga1() {
        return countErga1;
    }

    public void setCountErga1(String countErga1) {
        this.countErga1 = countErga1;
    }

    public List<Object> getCountErga() {
        return countErga;
    }

    public void setCountErga(List<Object> countErga) {
        this.countErga = countErga;
    }

    public List<Meloi> getMeloiEreynitikaErgaList() {
        return meloiEreynitikaErgaList;
    }

    public void setMeloiEreynitikaErgaList(List<Meloi> meloiEreynitikaErgaList) {
        this.meloiEreynitikaErgaList = meloiEreynitikaErgaList;
    }

    public List<Object[]> getCountCommonErgaList() {
        return countCommonErgaList;
    }

    public void setCountCommonErgaList(List<Object[]> countCommonErgaList) {
        this.countCommonErgaList = countCommonErgaList;
    }
}