/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import db.NewHibernateUtil;
import org.hibernate.SessionFactory;
//import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;

/**
 *Επιστρέφει το όνομα του πίνακα απο το mapping της βάσης.
 * Παίρνει σαν input το όνομα του model πχ model.efedros.VStratevmenosKypros31 
 * και σου επιστρέφει ESEP_EFEDROS.V_STRATEVMENOS_KYPROS_3_1
 * ReturnTableName.getTableName(model.efedros.VStratevmenosKypros31.class)
 * @author George Tsotzolas
 */
public class ReturnTableName {
    
    
    public static String getTableName(Class modelName){
        SessionFactory sessionFactory;
        String tableName;
        
//        sessionFactory = new AnnotationConfiguration().configure("WEB-INF/classes/hibernate.cfg.xml").buildSessionFactory();
sessionFactory = NewHibernateUtil.getSessionFactory();
        ClassMetadata hibernateMetadata = sessionFactory.getClassMetadata(modelName);

        AbstractEntityPersister persister = (AbstractEntityPersister) hibernateMetadata;
        tableName = persister.getTableName();
        String[] columnNames = persister.getKeyColumnNames();
        
        return tableName;
      
    }
    
            
            
}

