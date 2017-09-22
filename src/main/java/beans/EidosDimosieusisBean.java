package beans;

import model.EidosDimosieusis;
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
public class EidosDimosieusisBean implements Serializable {

    //variables
    private EidosDimosieusis eidosDimosieusis = new EidosDimosieusis();


    //Lists
    private List<EidosDimosieusis> eidosDimosieusisList = new ArrayList<EidosDimosieusis>();

    public EidosDimosieusisBean() {
    }

    @PostConstruct
    public void init() {

        try {
//            colorList= ((List<Color>) (List<?>) db.dbTransactions.getAllObjectsSorted(Color.class.getCanonicalName(), "color", 0));
            setEidosDimosieusisList(((List<EidosDimosieusis>) (List<?>) db.dbTransactions.getAllObjectsSorted(EidosDimosieusis.class.getCanonicalName(), "eidos", 0)));
            String query = "from eidos_dimosieusis e ";


//            colorList = ((List<Color>) (List<?>) db.dbTransactions.getObjectsBySqlQueryNew(Color.class.getCanonicalName(),query,null,null,null));

        } catch (Exception ex) {
            System.out.println(ex);
        }


    }

    public void clear() {
        setEidosDimosieusis(new EidosDimosieusis());

    }
    public void storeEidosDimosieusis() {

        try {
            db.dbTransactions.storeObject(getEidosDimosieusis());

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                getEidosDimosieusisList().add(getEidosDimosieusis());
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }


    public void updateEidosDimosieusis() {

        try {
            db.dbTransactions.updateObject(getEidosDimosieusis());

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                setEidosDimosieusisList(((List<EidosDimosieusis>) (List<?>) db.dbTransactions.getAllObjectsSorted(EidosDimosieusis.class.getCanonicalName(), "eidos", 0)));

            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void deleteEidosDimosieusis() {

        try {
            db.dbTransactions.deleteObject(getEidosDimosieusis());

            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                getEidosDimosieusisList().remove(getEidosDimosieusis());
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }




    public EidosDimosieusis getEidosDimosieusis() {
        return eidosDimosieusis;
    }

    public void setEidosDimosieusis(EidosDimosieusis eidosDimosieusis) {
        this.eidosDimosieusis = eidosDimosieusis;
    }

    public List<EidosDimosieusis> getEidosDimosieusisList() {
        return eidosDimosieusisList;
    }

    public void setEidosDimosieusisList(List<EidosDimosieusis> eidosDimosieusisList) {
        this.eidosDimosieusisList = eidosDimosieusisList;
    }
}