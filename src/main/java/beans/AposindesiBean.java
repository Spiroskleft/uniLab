package beans;

//import beans.dyg.monimos.BasikaMonimosBean;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import beans.*;

/**
 *
 * @author kepyes_4a_6
 */
@ManagedBean(name = "AposindesiBean")
public class AposindesiBean implements Serializable {

    private static final Logger log = Logger.getLogger(AposindesiBean.class.getName());
    

    /**
     * Creates a new instance of aposindesiBean
     */
    public AposindesiBean() {
    }

    @PostConstruct
    public void init() {
    }

    public String aposindesiUser() {

//       basikaMonimosBean.clear();

        
        // Ακύρωση του Session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.getSession().setAttribute(LoginBean.AUTH_KEY, null);

        return "/webContent/login.jsf?faces-redirect=true";
    }

    public String changePassword() {
        return "/login/changePassword.jsf?faces-redirect=true";
    }
    
    public String anakoinwseis() {
        return "/login/anakoinwseis.jsf?faces-redirect=true";
    }

    public String diaxeirisiAnakoinwsewn() {
        return "/diaxeirisi/diaxeirisiAnakoinwsewn.jsf?faces-redirect=true";
    }

    public String diaxeirisiMinimatwn() {
        return "/diaxeirisi/diaxeirisiMinimatwn.jsf?faces-redirect=true";
    }

    public String messages() {
        return "/login/minimata.jsf?faces-redirect=true";
    }

    public String minimataUser() {
        return "/login/minimata.jsf?faces-redirect=true";
    }

    public String downloadsPagDiatages() {
        return "/login/downloadsPagDiatages.jsf?faces-redirect=true";
    }

//    /**
//     * @return the basikaMonimosBean
//     */
//    public BasikaMonimosBean getBasikaMonimosBean() {
//        return basikaMonimosBean;
//    }
//
//    /**
//     * @param basikaMonimosBean the basikaMonimosBean to set
//     */
//    public void setBasikaMonimosBean(BasikaMonimosBean basikaMonimosBean) {
//        this.basikaMonimosBean = basikaMonimosBean;
//    }

}
