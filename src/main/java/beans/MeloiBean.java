package beans;

import db.dbTransactions;
import model.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@ManagedBean
@ViewScoped
public class MeloiBean implements Serializable {

    //Objects
    private Meloi melos = new Meloi();
    private EreynitikaErga ereynhtikaErga = new EreynitikaErga();
    private Dimosieuseis dimosieuseis = new Dimosieuseis();
    private Mathimata mathimata = new Mathimata();
    private Integer countmelos;
    private String countdimelous1 = "";
    private String countergamelous1 = "";
    private String countmathimatamelous1 = "";


    //Lists

    private List<Meloi> meloiList = new ArrayList<>();
    private List<Katigoria> katigoriaList = new ArrayList<>();
    private List<Object> countDiMelous = new ArrayList<>();
    private List<Object> countErgaMelous = new ArrayList<>();
    private List<Object> countMathimataMelous = new ArrayList<>();


    //Ερευνητικά Έργα
    private List<EreynitikaErga> ergaMeloysList = new ArrayList<>();
    private List<EreynitikaErga> ereynitikaErgaList = new ArrayList<>();
    private List<EreynitikaErga> ereynitikaErgaNotInUserList = new ArrayList<>();
    private List<EreynitikaErga> ereynitikaErgaForInsertList = new ArrayList<>();

    //Δημοσιεύσεις
    private List<Dimosieuseis> meloiDimosieuseisList = new ArrayList<>();
    private List<Dimosieuseis> dimosieuseisList = new ArrayList<>();
    private List<Dimosieuseis> dimosieuseisNotInUserList = new ArrayList<>();
    private List<Dimosieuseis> dimosieuseisForInsertList = new ArrayList<>();

    //Μαθήματα
    private List<Mathimata> meloiMathimataList = new ArrayList<>();
    private List<Mathimata> mathimataList = new ArrayList<>();
    private List<Mathimata> mathimataNotInUserList = new ArrayList<>();
    private List<Mathimata> mathimataForInsertList = new ArrayList<>();

    public MeloiBean() {
    }

    @PostConstruct
    public void init() {

        try {
            //Φέρνει τη λίστα με όλα τα μέλη
            setMeloiList((List<Meloi>) (List<?>) dbTransactions.getAllObjectsSorted(Meloi.class.getCanonicalName(), "eponymo", 0));
            //Φενει τη λίστα με όλα τα Ερευνιτικά έργα
            setEreynitikaErgaList((List<EreynitikaErga>) (List<?>) dbTransactions.getAllObjectsSorted(EreynitikaErga.class.getCanonicalName(), "titlos", 1));
            //Φέρνει τη λίστα με τις κατηγορίες του Μέλους
            setKatigoriaList((List<Katigoria>) (List<?>) dbTransactions.getAllObjectsSorted(Katigoria.class.getCanonicalName(), "katigoria", 0));
            //Φέρνει τη λίστα με τις δημοσιεύσεις του Μέλους
            setDimosieuseisList((List<Dimosieuseis>) (List<?>) dbTransactions.getAllObjectsSorted(Dimosieuseis.class.getCanonicalName(), "titlos", 1));
            //Φέρνει τη λίστα με τα μαθήματα του Μέλους
            setMathimataList((List<Mathimata>) (List<?>) dbTransactions.getAllObjectsSorted(Mathimata.class.getCanonicalName(), "titlos", 1));

            countMeloi();


//            String countmelos11 =((countmelos1.get(0)).toString());
//            countmelos = Integer.parseInt(countmelos11);

//            System.out.println("--");
//            System.out.println(countmelos);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void clear() {
        setMelos(new Meloi());
    }


    /**
     * Κάνει εισαγωγή το νέο μέλος
     */
    public void storeMeloi() {

        //Είναι για να κάνουμε το insert στο many-to-many
        //Προσθετουμε Τα ερευνητικά έργα που έχουμε επιλέξει για το μέλος
        Set<EreynitikaErga> ergaForInsertSet = new HashSet<>();
        ergaForInsertSet.addAll(getEreynitikaErgaForInsertList());

        //Προσθέτουμε τις Δημοσιεύσεις που έχει κάθε μέλος
        Set<Dimosieuseis> dimosieuseisForInsertSet = new HashSet<>();
        dimosieuseisForInsertSet.addAll(getDimosieuseisForInsertList());
        //Προσθέτουμε τα Μαθήματα που έχει κάθε μέλος
        Set<Mathimata> mathimataForInsertSet = new HashSet<>();
        mathimataForInsertSet.addAll(getMathimataForInsertList());

        //Ενημερώνουμε το αντικείμενο melos
        getMelos().setEreynitikaErgas(ergaForInsertSet);
        getMelos().setDimosieuseises(dimosieuseisForInsertSet);
        getMelos().setMathimatas(mathimataForInsertSet);
        try {
            dbTransactions.storeObject(getMelos());
            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                FacesMessage msg = new FacesMessage("Επιτυχής Εισαγωγή", "Μέλους " + "" + (getMelos().getEponymo()));
                FacesContext.getCurrentInstance().addMessage(null, msg);
                getMeloiList().add(getMelos());
                countMeloi();

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    /**
     * Κάνει update το μέλος
     */

    public void updateMelos() {
        System.out.println(getErgaMeloysList().size());


        //Είναι για να κάνουμε το insert στο many-to-many
        //Προσθετουμε Τα ερευνητικά έργα που έχουμε επιλέξει για το μέλος
        Set<EreynitikaErga> ergaForInsert = new HashSet<>();
        ergaForInsert.addAll(getErgaMeloysList());

        //Προσθέτουμε τις Δημοσιεύσεις που έχει κάθε μέλος
        Set<Dimosieuseis> dimosieuseisForInsertSet = new HashSet<>();
        dimosieuseisForInsertSet.addAll(getMeloiDimosieuseisList());

        //Προσθέτουμε τα μαθήματα που έχει κάθε μέλος
        Set<Mathimata> mathimataForInsertSet = new HashSet<>();
        mathimataForInsertSet.addAll(getMeloiMathimataList());

        getMelos().setEreynitikaErgas(ergaForInsert);
        getMelos().setDimosieuseises(dimosieuseisForInsertSet);
        getMelos().setMathimatas(mathimataForInsertSet);

        try {
            dbTransactions.updateObject(getMelos());
            //Αν όλα πάνε καλά τότε να κάνε το insert
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Επιτυχής Ενημέρωση Μέλους " + "", getMelos().getEponymo());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                setMeloiList(((List<Meloi>) (List<?>) dbTransactions.getAllObjectsSorted(Meloi.class.getCanonicalName(), "eponymo", 0)));
                countMeloi();

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Διαγραφή του Μέλους
     */
    public void deleteMelos() {
        try {
            dbTransactions.deleteObject(getMelos());
            //Αν όλα πάνε καλά τότε να κάνε το delete
            if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
                FacesMessage msg = new FacesMessage("Επιτυχής Διαγραφή", "Μέλους " + "" + (getMelos().getEponymo()));
                FacesContext.getCurrentInstance().addMessage(null, msg);
                getMeloiList().remove(getMelos());
                countMeloi();

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Φέρνει τα έργα που έχει το μέλος
     * Καθώς και αυτά τα οποία δεν έχει.
     */

    public void meloiData() {

        //-------------------Για τα εργα-------------------
        //Αρχικοποίηση
        setErgaMeloysList(new ArrayList<>());
        setEreynitikaErgaNotInUserList(new ArrayList<>());
        //Βρίσκει τα ερευνητικά έργα που δεν είναι κάθε μέλος
        setEreynitikaErgaNotInUserList((List<EreynitikaErga>) (List<?>) dbTransactions.getObjectsByStoreProcedure(EreynitikaErga.class.getCanonicalName(), "ErgaNotInMelos", getMelos().getMelosId()));
//Φέρνει το σύνολο των Έργων που έχει κάθε μέλος
        countErgaMelous();
        //Βρίσκει τα ερευνητικά έργα που είναι κάθε μέλος
        setErgaMeloysList((List<EreynitikaErga>) (List<?>) dbTransactions.getObjectsByStoreProcedure(EreynitikaErga.class.getCanonicalName(), "meloimeerga", getMelos().getMelosId()));

        //-------------------Για τις δημοσιεύσεις-----------
        setMeloiDimosieuseisList(new ArrayList<>());
        setDimosieuseisNotInUserList(new ArrayList<>());
        //Φέρνει τις Δημοσιεύσεις που έχει κάθε μέλος
        setMeloiDimosieuseisList((List<Dimosieuseis>) (List<?>) dbTransactions.getObjectsByStoreProcedure(Dimosieuseis.class.getCanonicalName(), "Meloimedimosieuseis", getMelos().getMelosId()));
        //Φέρνει το σύνολο των Δημοσιεύσεων που έχει κάθε μέλος
        countDimelous();
        //Βρίσκει τις δημοσιεύεσεις που δεν είναι κάθε μέλος
        setDimosieuseisNotInUserList((List<Dimosieuseis>) (List<?>) dbTransactions.getObjectsByStoreProcedure(Dimosieuseis.class.getCanonicalName(), "DimosieuseisNotInMelos", getMelos().getMelosId()));
//-------------------Για τα Μαθήματα-----------
        setMeloiMathimataList(new ArrayList<>());
        setMathimataNotInUserList(new ArrayList<>());
        //Φέρνει τα μαθήματα που έχει κάθε μέλος
        setMeloiMathimataList((List<Mathimata>) (List<?>) dbTransactions.getObjectsByStoreProcedure(Mathimata.class.getCanonicalName(), "MeloiMeMathimata", getMelos().getMelosId()));
        //Φέρνει το σύνολο των Μαθημάτων που έχει κάθε μέλος
countMathimataMelous();
        //Βρίσκει τα μαθήματα που δεν είναι κάθε μέλος
        setMathimataNotInUserList((List<Mathimata>) (List<?>) dbTransactions.getObjectsByStoreProcedure(Mathimata.class.getCanonicalName(), "MathimataNotInMelos", getMelos().getMelosId()));

    }

    /**
     * Προσθέτει στη λίστα με τα έργα που επιλέγουμε
     * για το insert
     */
    public void addErga() {
        getEreynitikaErgaForInsertList().add(getEreynhtikaErga());
        getEreynitikaErgaList().remove(getEreynhtikaErga());
    }

    /**
     * Αφαιρούμε απο την λιστα με τα εργα που θέλουμε
     * για το insert
     */
    public void removeErga() {
        getEreynitikaErgaForInsertList().remove(getEreynhtikaErga());
        getEreynitikaErgaList().add(getEreynhtikaErga());
    }


    /**
     * Προσθέτει στη λίστα με τις δημοσιεύσεις που επιλέγουμε
     * για το insert
     */
    public void addDimosieuseis() {
        getDimosieuseisForInsertList().add(getDimosieuseis());
        getDimosieuseisList().remove(getDimosieuseis());
    }

    /**
     * Αφαιρούμε απο την λιστα με τις δημοσιεύσεις που θέλουμε
     * για το insert
     */
    public void removeDimosieuseis() {
        getDimosieuseisForInsertList().remove(getDimosieuseis());
        getDimosieuseisList().add(getDimosieuseis());
    }

    /**
     * Προσθέτει στη λίστα με τα μαθήματα που επιλέγουμε
     * για το insert
     */
    public void addMathimata() {
        getMathimataForInsertList().add(getMathimata());
        getMathimataList().remove(getMathimata());
    }

    /**
     * Αφαιρούμε απο την λιστα με τις δημοσιεύσεις που θέλουμε
     * για το insert
     */
    public void removeMathimata() {
        getMathimataForInsertList().remove(getMathimata());
        getMathimataList().add(getMathimata());
    }


    /**
     * Πριν απο την εισαγωγή καθαρίζουμε τη λίστα με τα εργα που θέλουμε να εισαγουμε
     * και αρχικοποιούμε την λίστα με τα εργα
     */
    public void prepareInsert() {
        setMelos(new Meloi());
        setEreynhtikaErga(new EreynitikaErga());
        setEreynitikaErgaForInsertList(new ArrayList<>());
        setEreynitikaErgaList(((List<EreynitikaErga>) (List<?>) dbTransactions.getAllObjectsSorted(EreynitikaErga.class.getCanonicalName(), "etos", 1)));

        //Φέρνει όλες τις Δημοσιεύσεις
        setDimosieuseisList((((List<Dimosieuseis>) (List<?>) dbTransactions.getAllObjectsSorted(Dimosieuseis.class.getCanonicalName(), "etos", 1))));

        //Φέρνει όλα τα Μαθήματα
        setMathimataList((((List<Mathimata>) (List<?>) dbTransactions.getAllObjectsSorted(Mathimata.class.getCanonicalName(), "titlos", 1))));
    }


    /**
     * Προσθέτη στη λίστα με τα έργα που επιλέγουμε
     * γιατι update
     */
    public void addErgaForUpdate() {
        getErgaMeloysList().add(getEreynhtikaErga());
        getEreynitikaErgaNotInUserList().remove(getEreynhtikaErga());
    }

    /**
     * Αφαιρούμε απο την λιστα με τα εργα που θέλουμε
     * γιατι update
     */
    public void removeErgaForUpdate() {
        getErgaMeloysList().remove(getEreynhtikaErga());
        getEreynitikaErgaNotInUserList().add(getEreynhtikaErga());
    }


    /**
     * Προσθέτη στη λίστα με τις δημοσιεύσεις που επιλέγουμε
     * για το update
     */
    public void addDimosieuseisForUpdate() {
        getMeloiDimosieuseisList().add(getDimosieuseis());
        getDimosieuseisNotInUserList().remove(getDimosieuseis());
    }

    /**
     * Αφαιρούμε απο την λιστα με τις δημοσιεύσεις που θέλουμε
     * για το update
     */
    public void removeDimosieuseisForUpdate() {
        getMeloiDimosieuseisList().remove(getDimosieuseis());
        getDimosieuseisNotInUserList().add(getDimosieuseis());
    }

    /**
     * Προσθέτη στη λίστα με τα μαθήματα που επιλέγουμε
     * για το update
     */
    public void addMathimataForUpdate() {
        getMeloiMathimataList().add(getMathimata());
        getMathimataNotInUserList().remove(getMathimata());
    }

    /**
     * Αφαιρούμε απο την λιστα με τις δημοσιεύσεις που θέλουμε
     * για το update
     */
    public void removeMathimataForUpdate() {
        getMeloiMathimataList().remove(getMathimata());
        getMathimataNotInUserList().add(getMathimata());
    }


    public void countMeloi() {

        BigInteger countmelos1 = (BigInteger) dbTransactions.getObjectsByStoreProcedureGeneral1("countMeloi", null);
        countmelos = countmelos1.intValue();
    }

    public void countDimelous() {
        List countDiMelous = dbTransactions.getObjectsByStoreProcedureGeneral("countDimosieuseisMelous", getMelos().getMelosId());
        setCountdimelous1(countDiMelous.get(0).toString());

    }

    public void countErgaMelous() {
        List countErgaMelous = dbTransactions.getObjectsByStoreProcedureGeneral("countEreynitikaMelous", getMelos().getMelosId());
        setCountergamelous1(countErgaMelous.get(0).toString());

    }

    public void countMathimataMelous() {
        List countMathimataMelous = dbTransactions.getObjectsByStoreProcedureGeneral("countMathimataMelous", getMelos().getMelosId());
        setCountmathimatamelous1(countMathimataMelous.get(0).toString());

    }


    /**
     * Getters - setters
     */
    public Meloi getMelos() {
        return melos;
    }

    public void setMelos(Meloi melos) {
        this.melos = melos;
    }

    public EreynitikaErga getEreynhtikaErga() {
        return ereynhtikaErga;
    }

    public void setEreynhtikaErga(EreynitikaErga ereynhtikaErga) {
        this.ereynhtikaErga = ereynhtikaErga;
    }

    public Dimosieuseis getDimosieuseis() {
        return dimosieuseis;
    }

    public void setDimosieuseis(Dimosieuseis dimosieuseis) {
        this.dimosieuseis = dimosieuseis;
    }

    public Mathimata getMathimata() {
        return mathimata;
    }

    public void setMathimata(Mathimata mathimata) {
        this.mathimata = mathimata;
    }

    public List<Meloi> getMeloiList() {
        return meloiList;
    }

    public void setMeloiList(List<Meloi> meloiList) {
        this.meloiList = meloiList;
    }

    public List<Katigoria> getKatigoriaList() {
        return katigoriaList;
    }

    public void setKatigoriaList(List<Katigoria> katigoriaList) {
        this.katigoriaList = katigoriaList;
    }

    public List<EreynitikaErga> getErgaMeloysList() {
        return ergaMeloysList;
    }

    public void setErgaMeloysList(List<EreynitikaErga> ergaMeloysList) {
        this.ergaMeloysList = ergaMeloysList;
    }

    public List<EreynitikaErga> getEreynitikaErgaList() {
        return ereynitikaErgaList;
    }

    public void setEreynitikaErgaList(List<EreynitikaErga> ereynitikaErgaList) {
        this.ereynitikaErgaList = ereynitikaErgaList;
    }

    public List<EreynitikaErga> getEreynitikaErgaNotInUserList() {
        return ereynitikaErgaNotInUserList;
    }

    public void setEreynitikaErgaNotInUserList(List<EreynitikaErga> ereynitikaErgaNotInUserList) {
        this.ereynitikaErgaNotInUserList = ereynitikaErgaNotInUserList;
    }

    public List<EreynitikaErga> getEreynitikaErgaForInsertList() {
        return ereynitikaErgaForInsertList;
    }

    public void setEreynitikaErgaForInsertList(List<EreynitikaErga> ereynitikaErgaForInsertList) {
        this.ereynitikaErgaForInsertList = ereynitikaErgaForInsertList;
    }

    public List<Dimosieuseis> getMeloiDimosieuseisList() {
        return meloiDimosieuseisList;
    }

    public void setMeloiDimosieuseisList(List<Dimosieuseis> meloiDimosieuseisList) {
        this.meloiDimosieuseisList = meloiDimosieuseisList;
    }

    public List<Dimosieuseis> getDimosieuseisList() {
        return dimosieuseisList;
    }

    public void setDimosieuseisList(List<Dimosieuseis> dimosieuseisList) {
        this.dimosieuseisList = dimosieuseisList;
    }

    public List<Dimosieuseis> getDimosieuseisNotInUserList() {
        return dimosieuseisNotInUserList;
    }

    public void setDimosieuseisNotInUserList(List<Dimosieuseis> dimosieuseisNotInUserList) {
        this.dimosieuseisNotInUserList = dimosieuseisNotInUserList;
    }

    public List<Dimosieuseis> getDimosieuseisForInsertList() {
        return dimosieuseisForInsertList;
    }

    public void setDimosieuseisForInsertList(List<Dimosieuseis> dimosieuseisForInsertList) {
        this.dimosieuseisForInsertList = dimosieuseisForInsertList;
    }

    public List<Mathimata> getMeloiMathimataList() {
        return meloiMathimataList;
    }

    public void setMeloiMathimataList(List<Mathimata> meloiMathimataList) {
        this.meloiMathimataList = meloiMathimataList;
    }

    public List<Mathimata> getMathimataList() {
        return mathimataList;
    }

    public void setMathimataList(List<Mathimata> mathimataList) {
        this.mathimataList = mathimataList;
    }

    public List<Mathimata> getMathimataNotInUserList() {
        return mathimataNotInUserList;
    }

    public void setMathimataNotInUserList(List<Mathimata> mathimataNotInUserList) {
        this.mathimataNotInUserList = mathimataNotInUserList;
    }

    public List<Mathimata> getMathimataForInsertList() {
        return mathimataForInsertList;
    }

    public void setMathimataForInsertList(List<Mathimata> mathimataForInsertList) {
        this.mathimataForInsertList = mathimataForInsertList;
    }

    public Integer getCountmelos() {
        return countmelos;
    }

    public void setCountmelos(Integer countmelos) {
        this.countmelos = countmelos;
    }

    public String getCountdimelous1() {
        return countdimelous1;
    }

    public void setCountdimelous1(String countdimelous1) {
        this.countdimelous1 = countdimelous1;
    }

    public List<Object> getCountDiMelous() {
        return countDiMelous;
    }

    public void setCountDiMelous(List<Object> countDiMelous) {
        this.countDiMelous = countDiMelous;
    }

    public String getCountergamelous1() {
        return countergamelous1;
    }

    public void setCountergamelous1(String countergamelous1) {
        this.countergamelous1 = countergamelous1;
    }

    public String getCountmathimatamelous1() {
        return countmathimatamelous1;
    }

    public void setCountmathimatamelous1(String countmathimatamelous1) {
        this.countmathimatamelous1 = countmathimatamelous1;
    }

    public List<Object> getCountErgaMelous() {
        return countErgaMelous;
    }

    public void setCountErgaMelous(List<Object> countErgaMelous) {
        this.countErgaMelous = countErgaMelous;
    }

    public List<Object> getCountMathimataMelous() {
        return countMathimataMelous;
    }

    public void setCountMathimataMelous(List<Object> countMathimataMelous) {
        this.countMathimataMelous = countMathimataMelous;
    }


//-------------------------------------------------------------------------------------------------------------//


}