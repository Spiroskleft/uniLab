package beans;

import db.HashingUtil;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Users;
import org.primefaces.context.RequestContext;

/**
 * Αυθεντικοποιεί τους χρήστες και ανεβάζει στο session το αντικείμενο
 * <code></code> για τον συνδεδεμένο χρήστη.
 */
@ManagedBean(name = "LoginBean")
@SessionScoped
public class LoginBean implements Serializable {

    public static final String AUTH_KEY = "username_auth";
    //variables
    private boolean changePwd = false;
    private String username = "";
    private String password = "";
    //Lists
    private List<Users> usersList;
    //Oblects
    private Users user;


    public LoginBean() {
    }

    @PostConstruct
    public void init() {

        try {
            //Ανακτά το όνομα της βάσης 
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String validateUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        FacesMessage message = new FacesMessage();
        if (username.isEmpty() || username == ""|| username ==null) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ανεπιτυχής Είσοδος", "Πρέπει να συμπληρώσετε ένα username ");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return null;
        } else if (password.isEmpty() || password == "") {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ανεπιτυχής Είσοδος", "Πρέπει να συμπληρώσετε ένα password ");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return null;
        } else {


            // Αναζήτηση για το χρήστη
            usersList = (List<Users>) (List<?>) db.dbTransactions.getObjectsByProperty(Users.class.getCanonicalName(), "username", getUsername());
            System.out.println("usersList.size()------------------->" + usersList.size());
            // Αν είναι άδεια η λίστα, επιστροφή
            if (usersList.isEmpty()) {
                String failMessage = username + ":" + password + " failed";
                request.getSession().setAttribute(AUTH_KEY, null);
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ανεπιτυχής Είσοδος", "Πρέπει να συμπληρώσετε σωστά το username και το password");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return "null";
            }

            // Αν είναι σωστός ο συνδυασμός username/password, εισαγωγή στην εφαρμογή
            user = usersList.get(0);
            if (getUsername().equals(user.getUsername()) && (getPassword()).equals(user.getPassword())) {
                request.getSession().setAttribute(AUTH_KEY, getUsername());

                // Ανάκτηση δικαωμάτων
                // Ανάκτηση στοιχείων μονίμου
//            monimos = (Monimos) db.dbTransactions.getObjectByIdString(Monimos.class.getCanonicalName(), user.getStrkProsKwd());
                String defaultPassword = HashingUtil.SHA1("123456");
                if (user.getPassword().equals(defaultPassword)) {
                    return "/webContent/changePassword.jsf";
                } else {
                    return "/webContent/hellopage.jsf";
                }
            } else {
                String failMessage = username + ":" + password + " failed";
                request.getSession().setAttribute(AUTH_KEY, null);
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR  , "Ανεπιτυχής Είσοδος", "Πρέπει να συμπληρώσετε σωστά το username και το password");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return null;
               // return "/login.jsf";
            }
        }

    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the user
     */
    public Users getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(Users user) {
        this.user = user;
    }

    /**
     * @return the changePwd
     */
    public boolean isChangePwd() {
        return changePwd;
    }

    /**
     * @param changePwd the changePwd to set
     */
    public void setChangePwd(boolean changePwd) {
        this.changePwd = changePwd;
    }


}