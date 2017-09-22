package beans;

import model.Anakoinwseis;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import db.dbTransactions;

/**
 * Αυθεντικοποιεί τους χρήστες και ανεβάζει στο session το αντικείμενο
 * <code>Monimos</code> για τον συνδεδεμένο χρήστη.
 */
@ManagedBean
@ViewScoped
public class AnakoinwseisBean implements Serializable {

    //variables
    private Anakoinwseis anakoinwseis = new Anakoinwseis();


    private String countAnakoinwseis1 = "";
    //Lists
    private List<Anakoinwseis> anakoinwseisList = new ArrayList<Anakoinwseis>();
    private List<Object> countAnakoinwseis = new ArrayList<>();

    public AnakoinwseisBean() {
    }

    @PostConstruct
    public void init() {

        try {

            setAnakoinwseisList(((List<Anakoinwseis>) (List<?>) db.dbTransactions.getAllObjectsSorted(Anakoinwseis.class.getCanonicalName(), "titlos", 0)));
            String query = "from anakoinwseis a ";

countAnak();



        } catch (Exception ex) {
            System.out.println(ex);
        }


    }

    public void clear() {
        setAnakoinwseis(new Anakoinwseis());

    }
    public void storeAnakoinwseis() {

        try {
            db.dbTransactions.storeObject(anakoinwseis);

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                anakoinwseisList.add(anakoinwseis);
            }
countAnak();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }


    public void updateAnakoinwseis() {

        try {
            db.dbTransactions.updateObject(anakoinwseis);

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                anakoinwseisList  = ((List<Anakoinwseis>) (List<?>) db.dbTransactions.getAllObjectsSorted(Anakoinwseis.class.getCanonicalName(), "titlos", 0));

            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void deleteAnakoinwseis() {

        try {
            db.dbTransactions.deleteObject(anakoinwseis);

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                anakoinwseisList.remove(anakoinwseis);
            }
countAnak();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
    public void countAnak() {
        List countAnakoinwseis = dbTransactions.getObjectsByStoreProcedureGeneral("countAnakoinwseis", null);
        setCountAnakoinwseis1(countAnakoinwseis.get(0).toString());

    }


    public Anakoinwseis getAnakoinwseis() {
        return anakoinwseis;
    }

    public void setAnakoinwseis(Anakoinwseis anakoinwseis) {
        this.anakoinwseis = anakoinwseis;
    }

    public List<Anakoinwseis> getAnakoinwseisList() {
        return anakoinwseisList;
    }

    public void setAnakoinwseisList(List<Anakoinwseis> anakoinwseisList) {
        this.anakoinwseisList = anakoinwseisList;
    }


    public String getCountAnakoinwseis1() {
        return countAnakoinwseis1;
    }

    public void setCountAnakoinwseis1(String countAnakoinwseis1) {
        this.countAnakoinwseis1 = countAnakoinwseis1;
    }

    public List<Object> getCountAnakoinwseis() {
        return countAnakoinwseis;
    }

    public void setCountAnakoinwseis(List<Object> countAnakoinwseis) {
        this.countAnakoinwseis = countAnakoinwseis;
    }
}