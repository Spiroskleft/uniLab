/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import db.HashingUtil;
import model.Users;
import org.apache.log4j.Logger;

/**
 *
 * @author George Tsotzolas <tsotzolas@gmail.com>
 */
@ManagedBean
@ViewScoped
public class ChangePasswordBean implements Serializable {

    private static final Logger log = Logger.getLogger(ChangePasswordBean.class.getName());
    //variables
    private String username;
    private String oldPassword;
    private String newPassword1;
    private String newPassword2;
    private String message;
    //placeholder obj
    private Users user = new Users();
    @ManagedProperty("#{LoginBean}")
    private LoginBean loginBean;
    private Boolean isFailed = false;
    FacesMessage msg = new FacesMessage();

    /**
     * Creates a new instance of ChangePasswordBean
     */
    public ChangePasswordBean() {
        log.debug("---->ChangePasswordBean constructor called");
    }

    @PostConstruct
    public void init() {
        log.info("---->PostConstructor init() called");
        HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        log.debug("init() completed");
        //msg = new FacesMessage("Επιτυχής Εισαγωγή", (promitheftis.getEponymo()));

        
    }

    /**
     *
     */
    public void changePWD() {
        log.debug("---->changePWD() called");

        user = loginBean.getUser();

        // Σωστή καταχώρηση κωδικού
        if (newPassword1.equals(newPassword2) && user.getPassword().equals(HashingUtil.SHA1(getOldPassword()))
                && newPassword1.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$&/+])(?=\\S+$).{8,}$")
                && !(newPassword1.equals(getOldPassword()))) {

            user.setPassword(HashingUtil.SHA1(newPassword1));
            db.dbTransactions.updateObject(user);


            FacesMessage msg = new FacesMessage("Επιτυχής ","Αλλαγή Κωδικού" );
            FacesContext.getCurrentInstance().addMessage(null, msg);

            //auditing
//            Auditing.getInstance().store(Auditing.CHANGE_PASSWORD, JsfUsers.class.getCanonicalName(), "Before:" + loginBean.getUserBefore().toString() + " After:" + user.toString(), " ");

            isFailed = true;

            // Λανθασμένη εισαγωγή παλαιού κωδικού
        } else if (!user.getPassword().equals(getOldPassword())) {
            FacesMessage msg = new FacesMessage("Σφάλμα","Λανθασμένη εισαγωγή παλαιού κωδικού!" );
            FacesContext.getCurrentInstance().addMessage(null, msg);
//            message = "Λανθασμένη εισαγωγή παλαιού κωδικού!";
            isFailed = false;
            // Δεν ταιριάζουν οι νέοι κωδικοί πρόσβασης
        } else if (!newPassword1.equals(newPassword2)) {
            FacesMessage msg = new FacesMessage("Σφάλμα","Δεν ταιριάζουν οι νέοι κωδικοί πρόσβασης!" );
            FacesContext.getCurrentInstance().addMessage(null, msg);
//            message = "Δεν ταιριάζουν οι νέοι κωδικοί πρόσβασης!";
            isFailed = false;
        } else if (!newPassword1.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$&/+])(?=\\S+$).{8,}$")) {
            FacesMessage msg = new FacesMessage("Σφάλμα","Ο νέος κωδικός δεν πληρεί τις προϋποθέσεις κατά ISO 27001!" );
            FacesContext.getCurrentInstance().addMessage(null, msg);
//            message = "Ο νέος κωδικός δεν πληρεί τις προϋποθέσεις κατά ISO 27001!";
            isFailed = false;
        } else if (newPassword1.equals(getOldPassword())) {
            FacesMessage msg = new FacesMessage("Σφάλμα","Ο νέος κωδικός είναι ίδιος με τον παλαιό!" );
            FacesContext.getCurrentInstance().addMessage(null, msg);
//            message = "Ο νέος κωδικός είναι ίδιος με τον παλαιό!";
            isFailed = false;
        }
    }

    public String redirectLoginPage() {
        log.debug("---->Method redirectLoginPage() called");

        // Ακύρωση του Session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.getSession().setAttribute(LoginBean.AUTH_KEY, null);

        return "/login.jsf?faces-redirect=true";
    }

     public String redirectChangePassword() throws IOException {
        HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            return "/login/changePassword.jsf?faces-redirect=true";
    }

    public String redirectAnaktisisKodikou () throws IOException {
        HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

//        if (SSOIntegration.isSSOEnabled(hsr)) {
//            String casPortalUrl = KepyesCasAuthenticationFilter.getCasPortalUrl();
//
//
//
//            FacesContext.getCurrentInstance().getExternalContext().redirect(casPortalUrl + "/dilosiStoixeiwn.jsf");
//            return null;
//        }
//        else {
//            return "/login/dilosiStoixeiwn.jsf?faces-redirect=true";
//        }

        return null;
    }
    
    //////////////////////////////////////////////////////////////////////
    //                                                                  //
    //                      GETTERS & SETTERS                           //
    //                                                                  //
    //////////////////////////////////////////////////////////////////////
    /**
     * @return the loginBean
     */
    public LoginBean getLoginBean() {
        return loginBean;
    }

    /**
     * @param loginBean the loginBean to set
     */
    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    /**
     * @return the username from login bean username
     */
    public String getUsername() {
        username = loginBean.getUsername();
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the oldPassword
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * @param oldPassword the oldPassword to set
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * @return the newPassword1
     */
    public String getNewPassword1() {
        return newPassword1;
    }

    /**
     * @param newPassword1 the newPassword1 to set
     */
    public void setNewPassword1(String newPassword1) {
        this.newPassword1 = newPassword1;
    }

    /**
     * @return the newPassword2
     */
    public String getNewPassword2() {
        return newPassword2;
    }

    /**
     * @param newPassword2 the newPassword2 to set
     */
    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
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
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the isFailed
     */
    public Boolean getIsFailed() {
        return isFailed;
    }

    /**
     * @param isFailed the isFailed to set
     */
    public void setIsFailed(Boolean isFailed) {
        this.isFailed = isFailed;
    }


    
    

   
}