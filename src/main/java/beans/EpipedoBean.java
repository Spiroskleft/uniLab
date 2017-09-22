package beans;

import model.Epipedo;
import model.ThematikiEnotita;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@ManagedBean
@ViewScoped
public class EpipedoBean implements Serializable {

    //variables
    private Epipedo epipedo = new Epipedo();


    //Lists
    private List<Epipedo> epipedoList = new ArrayList<Epipedo>();

    public EpipedoBean() {
    }

    @PostConstruct
    public void init() {

        try {
//            colorList= ((List<Color>) (List<?>) db.dbTransactions.getAllObjectsSorted(Color.class.getCanonicalName(), "color", 0));
            setEpipedoList(((List<Epipedo>) (List<?>) db.dbTransactions.getAllObjectsSorted(Epipedo.class.getCanonicalName(), "epipedo", 0)));
            String query = "from epipedo e ";


//            colorList = ((List<Color>) (List<?>) db.dbTransactions.getObjectsBySqlQueryNew(Color.class.getCanonicalName(),query,null,null,null));

        } catch (Exception ex) {
            System.out.println(ex);
        }


    }

    public void clear() {
        setEpipedo(new Epipedo());

    }
    public void storeEpipedo() {

        try {
            db.dbTransactions.storeObject(epipedo);

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                epipedoList.add(epipedo);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }


    public void updateEpipedo() {

        try {
            db.dbTransactions.updateObject(epipedo);

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                epipedoList  = ((List<Epipedo>) (List<?>) db.dbTransactions.getAllObjectsSorted(Epipedo.class.getCanonicalName(), "epipedo", 0));

            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void deleteEpipedo() {

        try {
            db.dbTransactions.deleteObject(epipedo);

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                epipedoList.remove(epipedo);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }


    public Epipedo getEpipedo() {
        return epipedo;
    }

    public void setEpipedo(Epipedo epipedo) {
        this.epipedo = epipedo;
    }

    public List<Epipedo> getEpipedoList() {
        return epipedoList;
    }

    public void setEpipedoList(List<Epipedo> epipedoList) {
        this.epipedoList = epipedoList;
    }
}