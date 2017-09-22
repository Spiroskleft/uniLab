package beans;

import model.Epipedo;
import model.KatastasiErgou;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@ManagedBean
@ViewScoped
public class KatastasiErgouBean implements Serializable {

    //variables
    private KatastasiErgou katastasiErgou = new KatastasiErgou();


    //Lists
    private List<KatastasiErgou> katastasiErgouList = new ArrayList<KatastasiErgou>();

    public KatastasiErgouBean() {
    }

    @PostConstruct
    public void init() {

        try {
//            colorList= ((List<Color>) (List<?>) db.dbTransactions.getAllObjectsSorted(Color.class.getCanonicalName(), "color", 0));
            setKatastasiErgouList(((List<KatastasiErgou>) (List<?>) db.dbTransactions.getAllObjectsSorted(KatastasiErgou.class.getCanonicalName(), "katastasi", 0)));
            String query = "from katastasi_ergou k ";


//            colorList = ((List<Color>) (List<?>) db.dbTransactions.getObjectsBySqlQueryNew(Color.class.getCanonicalName(),query,null,null,null));

        } catch (Exception ex) {
            System.out.println(ex);
        }


    }

    public void clear() {
        setKatastasiErgou(new KatastasiErgou());

    }
    public void storeKatastasiErgou() {

        try {
            db.dbTransactions.storeObject(katastasiErgou);

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                katastasiErgouList.add(katastasiErgou);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }


    public void updateKatastasiErgou() {

        try {
            db.dbTransactions.updateObject(katastasiErgou);

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                katastasiErgouList  = ((List<KatastasiErgou>) (List<?>) db.dbTransactions.getAllObjectsSorted(KatastasiErgou.class.getCanonicalName(), "katastasi", 0));

            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void deleteKatastasiErgou() {

        try {
            db.dbTransactions.deleteObject(katastasiErgou);

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                katastasiErgouList.remove(katastasiErgou);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }


    public KatastasiErgou getKatastasiErgou() {
        return katastasiErgou;
    }

    public void setKatastasiErgou(KatastasiErgou katastasiErgou) {
        this.katastasiErgou = katastasiErgou;
    }

    public List<KatastasiErgou> getKatastasiErgouList() {
        return katastasiErgouList;
    }

    public void setKatastasiErgouList(List<KatastasiErgou> katastasiErgouList) {
        this.katastasiErgouList = katastasiErgouList;
    }
}