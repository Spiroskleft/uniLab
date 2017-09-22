//package beans;
//
//import model.Color;
//
//import javax.annotation.PostConstruct;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
//import javax.faces.context.FacesContext;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Αυθεντικοποιεί τους χρήστες και ανεβάζει στο session το αντικείμενο
// * <code>Monimos</code> για τον συνδεδεμένο χρήστη.
// */
//@ManagedBean
//@ViewScoped
//public class TestBean implements Serializable {
//
//    //variables
//    private Color color = new Color();
//
//
//    //Lists
//    private List<Color> colorList = new ArrayList<Color>();
//
//    public TestBean() {
//    }
//
//    @PostConstruct
//    public void init() {
//
//        try {
////            colorList= ((List<Color>) (List<?>) db.dbTransactions.getAllObjectsSorted(Color.class.getCanonicalName(), "color", 0));
//            colorList = ((List<Color>) (List<?>) db.dbTransactions.getAllObjectsSorted(Color.class.getCanonicalName(), "color", 0));
//            String query = "from color e ";
//
//
////            colorList = ((List<Color>) (List<?>) db.dbTransactions.getObjectsBySqlQueryNew(Color.class.getCanonicalName(),query,null,null,null));
//
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//
//
//    }
//
//    public void clear() {
//        color = new Color();
//
//    }
//
//
//    public void storeColor() {
//
//        try {
//            db.dbTransactions.storeObject(color);
//
//            //Αν όλα πάνε καλά τότε να κάνε το insert
//            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
//                colorList.add(color);
//            }
//
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//
//    }
//
//
//    public void updateColor() {
//
//        try {
//            db.dbTransactions.updateObject(color);
//
//            //Αν όλα πάνε καλά τότε να κάνε το insert
//            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
//                colorList = ((List<Color>) (List<?>) db.dbTransactions.getAllObjectsSorted(Color.class.getCanonicalName(), "color", 0));
//
//            }
//
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//
//    }
//
//    public void deleteColor() {
//
//        try {
//            db.dbTransactions.deleteObject(color);
//
//            //Αν όλα πάνε καλά τότε να κάνε το insert
//            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
//                colorList.remove(color);
//            }
//
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//
//    }
//
//
//    public Color getColor() {
//        return color;
//    }
//
//    public void setColor(Color color) {
//        this.color = color;
//    }
//
//    public List<Color> getColorList() {
//        return colorList;
//    }
//
//    public void setColorList(List<Color> colorList) {
//        this.colorList = colorList;
//    }
//}