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

import model.Meloi;

@ManagedBean
@ViewScoped
public class DimosieuseisBean implements Serializable {

    //variables
    private Dimosieuseis dimosieuseis = new Dimosieuseis();

    private String countDimosieuseis1 = "";
    //Lists
    private List<Dimosieuseis> dimosieuseisList = new ArrayList<Dimosieuseis>();
    private List<EidosDimosieusis> eidosDimosieusisList = new ArrayList<EidosDimosieusis>();
    private List<ThematikiEnotita> thematikiEnotitaList = new ArrayList<ThematikiEnotita>();
    private List<Meloi> meloiDimosieusisList = new ArrayList<>();
    private List<Object> countDimosieuseis = new ArrayList<>();
    private List<Object[]> countCommonDimosieuseisList = new ArrayList<>();

    public DimosieuseisBean() {
    }

    @PostConstruct
    public void init() {

        try {
            setDimosieuseisList(((((List<Dimosieuseis>) (List<?>) db.dbTransactions.getAllObjectsSorted(Dimosieuseis.class.getCanonicalName(), "titlos", 0)))));
            setEidosDimosieusisList(((((List<EidosDimosieusis>) (List<?>) db.dbTransactions.getAllObjectsSorted(EidosDimosieusis.class.getCanonicalName(), "eidos", 0)))));
            setThematikiEnotitaList(((((List<ThematikiEnotita>) (List<?>) db.dbTransactions.getAllObjectsSorted(ThematikiEnotita.class.getCanonicalName(), "titlos", 0)))));
           //Φέρνει τα μέλη της συγκεκριμένης Δημοσίευσης
            countDim();
            countCommonDimosieuseis();




        } catch (Exception ex) {
            System.out.println(ex);
        }


    }
    public void meloiDimosieusis() {


        meloiDimosieusisList = (List<Meloi>) (List<?>) db.dbTransactions.getObjectsByStoreProcedure(Meloi.class.getCanonicalName(), "selectMeloiDimosieusis", dimosieuseis.getDimosieusiId());

        System.out.println(meloiDimosieusisList.size());

    }

    public void countDim() {
        List countDimosieuseis = dbTransactions.getObjectsByStoreProcedureGeneral("countDimosieuseis", null);
        countDimosieuseis1 = countDimosieuseis.get(0).toString();

    }
    public void countCommonDimosieuseis() {
        countCommonDimosieuseisList = (List<Object[]>) (List<?>) db.dbTransactions.getObjectsByStoreProcedureGeneral("countCοmmonDimosieuseis", null);
        System.out.println(countCommonDimosieuseisList.size());
    }


    public void clear() {
        setDimosieuseis(new Dimosieuseis());

    }

    public void storeDimosieuseis() {

        try {
            db.dbTransactions.storeObject(getDimosieuseis());

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                FacesMessage msg = new FacesMessage("Επιτυχής Εισαγωγή", " νέας Δημοσίευσης " + (dimosieuseis.getTitlos()));
                FacesContext.getCurrentInstance().addMessage(null, msg);
                getDimosieuseisList().add(getDimosieuseis());
            }
            countDim();


        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }


    public void updateDimosieuseis() {

        try {
            db.dbTransactions.updateObject(getDimosieuseis());

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Επιτυχής Ενημέρωση Δημοσίευσης", getDimosieuseis().getTitlos());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                setEidosDimosieusisList(((((List<EidosDimosieusis>) (List<?>) db.dbTransactions.getAllObjectsSorted(EidosDimosieusis.class.getCanonicalName(), "eidos", 0)))));

            }
            countDim();


        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void deleteDimosieuseis() {

        try {
            db.dbTransactions.deleteObject(getDimosieuseis());

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                FacesMessage msg = new FacesMessage("Επιτυχής Διαγραφή", "Μέλους" + (getDimosieuseis().getTitlos()));
                FacesContext.getCurrentInstance().addMessage(null, msg);
                getDimosieuseisList().remove(getDimosieuseis());
            }
            countDim();

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }




    public Dimosieuseis getDimosieuseis() {
        return dimosieuseis;
    }

    public void setDimosieuseis(Dimosieuseis dimosieuseis) {
        this.dimosieuseis = dimosieuseis;
    }

    public List<Dimosieuseis> getDimosieuseisList() {
        return dimosieuseisList;
    }

    public void setDimosieuseisList(List<Dimosieuseis> dimosieuseisList) {
        this.dimosieuseisList = dimosieuseisList;
    }

    public List<EidosDimosieusis> getEidosDimosieusisList() {
        return eidosDimosieusisList;
    }

    public void setEidosDimosieusisList(List<EidosDimosieusis> eidosDimosieusisList) {
        this.eidosDimosieusisList = eidosDimosieusisList;
    }

    public List<ThematikiEnotita> getThematikiEnotitaList() {
        return thematikiEnotitaList;
    }

    public void setThematikiEnotitaList(List<ThematikiEnotita> thematikiEnotitaList) {
        this.thematikiEnotitaList = thematikiEnotitaList;
    }



    public List<Object> getCountDimosieuseis() {
        return countDimosieuseis;
    }

    public void setCountDimosieuseis(List<Object> countDimosieuseis) {
        this.countDimosieuseis = countDimosieuseis;
    }

    public String getCountDimosieuseis1() {
        return countDimosieuseis1;
    }

    public void setCountDimosieuseis1(String countDimosieuseis1) {
        this.countDimosieuseis1 = countDimosieuseis1;
    }


    public List<Object[]> getCountCommonDimosieuseisList() {
        return countCommonDimosieuseisList;
    }

    public void setCountCommonDimosieuseisList(List<Object[]> countCommonDimosieuseisList) {
        this.countCommonDimosieuseisList = countCommonDimosieuseisList;
    }

    public List<Meloi> getMeloiDimosieusisList() {
        return meloiDimosieusisList;
    }

    public void setMeloiDimosieusisList(List<Meloi> meloiDimosieusisList) {
        this.meloiDimosieusisList = meloiDimosieusisList;
    }
}