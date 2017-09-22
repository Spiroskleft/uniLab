package beans;

import model.Users;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import db.HashingUtil;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.List;
//        import org.primefaces.showcase.domain.Car;
//        import org.primefaces.showcase.service.CarService;

@ManagedBean(name = "userBean")
@ViewScoped
public class UsersBean implements Serializable {

    private List<Users> userList;
    private String infomsg;
    private Users user = new Users();

    @PostConstruct
    public void init() {
        setUserList((List<Users>) (List<?>) db.dbTransactions.getAllObjectsSorted(Users.class.getCanonicalName(), "username", 0));
//
        clear1();
    }


    public void delete() {
        FacesMessage message = new FacesMessage();
        if (user != null) {
            db.dbTransactions.deleteObject(getUser());
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ανεπιτυχής Διαγραφή", user.getUsername());
        }
        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
            getUserList().remove(getUser());
            FacesMessage msg = new FacesMessage("Επιτυχής Διαγραφή", (user.getUsername()));
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void resetPassword() {
        FacesMessage message = new FacesMessage();
        if (user != null) {
            user.setPassword(HashingUtil.SHA1("123456"));
            db.dbTransactions.updateObject(getUser());
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ανεπιτυχής αρχικοποίηση", user.getUsername());
        }
        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
            getUserList().remove(getUser());
            FacesMessage msg = new FacesMessage("Επιτυχής Αρχικοποίηση", (user.getUsername()));
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void onRowEdit(RowEditEvent event) {

        db.dbTransactions.updateObject(event.getObject());
        //Update Κατηγορίας
        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {
            FacesMessage msg = new FacesMessage("Επιτυχής Ενημέρωσης", ((Users) event.getObject()).getUsername());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    public void clear1() {
        setUser(new Users());
    }


    /**
     * Εισαγωγή μιας νέας εγγραφής
     */
    public void buttonAction(ActionEvent actionEvent) {
        addMessage("Welcome to Primefaces!!");
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    public void insert() {
        FacesMessage message = new FacesMessage();
        if (user != null) {
            user.setPassword(HashingUtil.SHA1("123456"));
            db.dbTransactions.storeObject(getUser());
//            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Επιτυχής Εισαγωγή", User.getUser());
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ανεπιτυχής Εισαγωγή", user.getUsername());
        }
        if (FacesContext.getCurrentInstance().getMaximumSeverity() == null) {

            getUserList().add(getUser());

            FacesMessage msg = new FacesMessage("Επιτυχής Εισαγωγή", (user.getUsername()));
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }


    }


    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Ενημερωση Κατηγορίας Ακυρώθηκε", ((Users) event.getObject()).getUsername());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public List<Users> getUserList() {
        return userList;
    }

    public void setUserList(List<Users> userList) {
        this.userList = userList;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}

