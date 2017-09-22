package beans;

import model.Katigoria;
import model.ThematikiEnotita;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Αυθεντικοποιεί τους χρήστες και ανεβάζει στο session το αντικείμενο
 * <code>Monimos</code> για τον συνδεδεμένο χρήστη.
 */
@ManagedBean
@ViewScoped
public class ThematikiBean implements Serializable {

    //variables
    private ThematikiEnotita thematiki = new ThematikiEnotita();


    //Lists
    private List<ThematikiEnotita> thematikiList = new ArrayList<ThematikiEnotita>();

    public ThematikiBean() {
    }

    @PostConstruct
    public void init() {

        try {
//            colorList= ((List<Color>) (List<?>) db.dbTransactions.getAllObjectsSorted(Color.class.getCanonicalName(), "color", 0));
            setThematikiList(((List<ThematikiEnotita>) (List<?>) db.dbTransactions.getAllObjectsSorted(ThematikiEnotita.class.getCanonicalName(), "titlos", 0)));
            String query = "from thematiki_enotita t ";


//            colorList = ((List<Color>) (List<?>) db.dbTransactions.getObjectsBySqlQueryNew(Color.class.getCanonicalName(),query,null,null,null));

        } catch (Exception ex) {
            System.out.println(ex);
        }


    }

    public void clear() {
        setThematiki(new ThematikiEnotita());

    }
    public void storeThematiki() {

        try {
            db.dbTransactions.storeObject(thematiki);

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                thematikiList.add(thematiki);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }


    public void updateThematiki() {

        try {
            db.dbTransactions.updateObject(thematiki);

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                thematikiList  = ((List<ThematikiEnotita>) (List<?>) db.dbTransactions.getAllObjectsSorted(ThematikiEnotita.class.getCanonicalName(), "titlos", 0));

            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void deleteThematiki() {

        try {
            db.dbTransactions.deleteObject(thematiki);

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                thematikiList.remove(thematiki);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }


    public ThematikiEnotita getThematiki() {
        return thematiki;
    }

    public void setThematiki(ThematikiEnotita thematiki) {
        this.thematiki = thematiki;
    }

    public List<ThematikiEnotita> getThematikiList() {
        return thematikiList;
    }

    public void setThematikiList(List<ThematikiEnotita> thematikiList) {
        this.thematikiList = thematikiList;
    }
}