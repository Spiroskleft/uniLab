package beans;

import model.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@ManagedBean
@ViewScoped
public class KatigoriaMelousBean implements Serializable {

    //variables
    private Katigoria katigoria = new Katigoria();


    //Lists
    private List<Katigoria> katigoriaList = new ArrayList<Katigoria>();

    public KatigoriaMelousBean() {
    }

    @PostConstruct
    public void init() {

        try {
//            colorList= ((List<Color>) (List<?>) db.dbTransactions.getAllObjectsSorted(Color.class.getCanonicalName(), "color", 0));
            katigoriaList = ((List<Katigoria>) (List<?>) db.dbTransactions.getAllObjectsSorted(Katigoria.class.getCanonicalName(), "katigoria", 0));
            String query = "from katigoria k ";


//            colorList = ((List<Color>) (List<?>) db.dbTransactions.getObjectsBySqlQueryNew(Color.class.getCanonicalName(),query,null,null,null));

        } catch (Exception ex) {
            System.out.println(ex);
        }


    }

    public void clear() {
        katigoria = new Katigoria();

    }


    public void storeKatigoria() {

        try {
            db.dbTransactions.storeObject(katigoria);

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                katigoriaList.add(katigoria);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }


    public void updateKatigoria() {

        try {
            db.dbTransactions.updateObject(katigoria);

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                katigoriaList  = ((List<Katigoria>) (List<?>) db.dbTransactions.getAllObjectsSorted(Katigoria.class.getCanonicalName(), "katigoria", 0));

            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void deleteKatigoria() {

        try {
            db.dbTransactions.deleteObject(katigoria);

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                katigoriaList.remove(katigoria);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }


    public Katigoria getKatigoria() {
        return katigoria;
    }

    public void setKatigoria(Katigoria katigoria) {
        this.katigoria = katigoria;
    }

    public List<Katigoria> getKatigoriaList() {
        return katigoriaList;
    }

    public void setKatigoriaList(List<Katigoria> katigoriaList) {
        this.katigoriaList = katigoriaList;
    }
}