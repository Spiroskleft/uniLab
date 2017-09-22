package beans;


import db.dbTransactions;
import model.*;

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
public class MathimataBean implements Serializable {

    //variables
    private Mathimata mathimata = new Mathimata();
    private String countMathimata1 = "";

    //Lists
    private List<Mathimata> mathimataList = new ArrayList<>();
    private List<Epipedo> epipedoList = new ArrayList<>();
    private List<Meloi> meloiMathimataList = new ArrayList<>();
    private List<Meloi> meloiMathimatosList = new ArrayList<>();
    private List<Object> countMathimata = new ArrayList<>();

    public MathimataBean() {
    }

    @PostConstruct
    public void init() {

        try {
        setMathimataList(((((List<Mathimata>) (List<?>) db.dbTransactions.getAllObjectsSorted(Mathimata.class.getCanonicalName(), "titlos", 0)))));
        setEpipedoList(((((List<Epipedo>) (List<?>) db.dbTransactions.getAllObjectsSorted(Epipedo.class.getCanonicalName(), "epipedo", 0)))));
        countMath();
    } catch (Exception ex) {
        System.out.println(ex);
    }


}

    public void clear() {
        setMathimata(new Mathimata());

    }

    public void storeMathimata() {

        try {
            db.dbTransactions.storeObject(getMathimata());

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                FacesMessage msg = new FacesMessage("Επιτυχής Εισαγωγή", " νέου Μαθήματος " + (getMathimata().getTitlos()));
                FacesContext.getCurrentInstance().addMessage(null, msg);
                getMathimataList().add(getMathimata());
            }
countMath();
        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }


    public void updateMathimata() {

        try {
            db.dbTransactions.updateObject(getMathimata());

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Επιτυχής Ενημέρωση Μαθήματος", getMathimata().getTitlos());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                setEpipedoList(((((List<Epipedo>) (List<?>) db.dbTransactions.getAllObjectsSorted(Epipedo.class.getCanonicalName(), "epipedo", 0)))));

            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void deleteMathimata() {

        try {
            db.dbTransactions.deleteObject(getMathimata());

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                FacesMessage msg = new FacesMessage("Επιτυχής Διαγραφή", "Μαθήματος" + (getMathimata().getTitlos()));
                FacesContext.getCurrentInstance().addMessage(null, msg);
                getMathimataList().remove(getMathimata());
            }
countMath();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
    public void meloiMathimatos() {


        setMeloiMathimatosList((List<Meloi>) (List<?>) dbTransactions.getObjectsByStoreProcedure(Meloi.class.getCanonicalName(), "selectMeloiMathimatos", mathimata.getMathimataId()));

        System.out.println(getMeloiMathimatosList().size());

    }

    public void meloiMathimata() {


        setMeloiMathimataList((List<Meloi>) (List<?>) db.dbTransactions.getObjectsByStoreProcedure(Meloi.class.getCanonicalName(), "MeloiMeMathimata", getMathimata().getMathimataId()));

        System.out.println(getMeloiMathimataList().size());

    }
    public void countMath() {
        List countMathimata = dbTransactions.getObjectsByStoreProcedureGeneral("countMathimata", null);
        setCountMathimata1(countMathimata.get(0).toString());

    }


    public Mathimata getMathimata() {
        return mathimata;
    }

    public void setMathimata(Mathimata mathimata) {
        this.mathimata = mathimata;
    }

    public List<Mathimata> getMathimataList() {
        return mathimataList;
    }

    public void setMathimataList(List<Mathimata> mathimataList) {
        this.mathimataList = mathimataList;
    }

    public List<Epipedo> getEpipedoList() {
        return epipedoList;
    }

    public void setEpipedoList(List<Epipedo> epipedoList) {
        this.epipedoList = epipedoList;
    }

    public List<Meloi> getMeloiMathimataList() {
        return meloiMathimataList;
    }

    public void setMeloiMathimataList(List<Meloi> meloiMathimataList) {
        this.meloiMathimataList = meloiMathimataList;
    }

    public String getCountMathimata1() {
        return countMathimata1;
    }

    public void setCountMathimata1(String countMathimata1) {
        this.countMathimata1 = countMathimata1;
    }

    public List<Object> getCountMathimata() {
        return countMathimata;
    }

    public void setCountMathimata(List<Object> countMathimata) {
        this.countMathimata = countMathimata;
    }

    public List<Meloi> getMeloiMathimatosList() {
        return meloiMathimatosList;
    }

    public void setMeloiMathimatosList(List<Meloi> meloiMathimatosList) {
        this.meloiMathimatosList = meloiMathimatosList;
    }
}