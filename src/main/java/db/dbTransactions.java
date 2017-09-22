package db;

import java.math.BigDecimal;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Provides Database Interaction Layer for the Entire Project. dbTransactions
 * should be Statically called by any invoker e.g. dbTransactions.addUser(args)
 *
 * @author Panagiotis Gouvas
 */
public class dbTransactions {

    public static final int ORDER_ASC = 0;
    public static final int ORDER_DESC = 1;
    public static final String GT = ">";
    public static final String GE = ">=";
    public static final String LT = "<";
    public static final String LE = "<=";
    public static final String NE = "!=";
    public static final String EQ = "=";
    public static final String IS_NULL = "IS NULL";
    public static final String IS_NOT_NULL = "IS NOT NULL";
    public static final String LIKE = "LIKE";
    public static final String NOT_LIKE = "NOT LIKE";
    public static final String I_LIKE = "ILIKE";
    private static final Logger log = Logger.getLogger(dbTransactions.class.getName());

    /**
     * Method getObjectById(String id) loads the appropriate hibernate Object
     * from the database.
     *
     * @param classname	The classname of the loaded class e.g. model.User
     * @param id	The id as mapped in the database
     * @return	The hibernate Object that is loaded or null
     */
    public static Object getObjectById(String classname, Object id) {

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;

        //Model Initialisation
        Object obj = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();

            if (id instanceof String) {
                obj = hibeSession.get(Class.forName(classname), (String) id);
            } else if (id instanceof Integer) {
                obj = hibeSession.get(Class.forName(classname), (Integer) id);
            } else if (id instanceof Short) {
                obj = hibeSession.get(Class.forName(classname), (Short) id);
            } else if (id instanceof Date) {
                obj = hibeSession.get(Class.forName(classname), (Date) id);
            } else if (id instanceof Long) {
                obj = hibeSession.get(Class.forName(classname), (Long) id);
            } else if (id instanceof Byte) {
                obj = hibeSession.get(Class.forName(classname), (Byte) id);
            }

            hibeTransaction.commit();
        } catch (Exception ex) {
            log.error("dbTransactions.getObjectById(" + id + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectById(" + id + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }
        log.info("dbTransactions.getObjectById(" + id + ") returns Object:" + ((obj != null) ? obj.getClass() : null));

        return obj;
    }//EoM getObjectById 

    /**
     * Wrapper μέθοδος για την ανάκτηση αντικειμένων βάσει συγκεκριμένων πεδίων.
     *
     * @param classname
     * @param properties
     * @return
     */
    public static List<Object> getObjectsByProperties(String classname, Map<String, Object> properties) {
        return getObjectsByProperties(classname, properties, null, null, null);
    }//EoM getObjectsByProperties

    /**
     * Wrapper μέθοδος για την ανάκτηση αντικειμένων βάσει συγκεκριμένων πεδίων
     * και χρήση σελιδοποίησης.
     *
     * @param classname
     * @param properties
     * @param page
     * @param pagesize
     * @return
     */
    public static List<Object> getObjectsByProperties(String classname, Map<String, Object> properties, Integer page, Integer pagesize) {
        return getObjectsByProperties(classname, properties, page, pagesize, null);
    }//EoM getObjectsByProperties

    /**
     * Wrapper μέθοδος για την ανάκτηση αντικειμένων βάσει συγκεκριμένων πεδίων,
     * με χρήση σελιδοποίησης και ταξινόμησης.
     *
     * @param classname
     * @param properties
     * @param page
     * @param pagesize
     * @param sortproperties
     * @return
     */
    public static List<Object> getObjectsByProperties(String classname, Map<String, Object> properties,
            Integer page, Integer pagesize, Map<String, Integer> sortproperties) {

        return getObjectsByProperties(classname, properties, null, page, pagesize, sortproperties);
    }//EoM getObjectsByProperties
    /**
     * Καλεί μια storeProcedure και επιστρέφει τις εγγραφές σε μια λίστα απο oblect.
     * Δεν χρειάζεται συγκεκριμένο classname
     * @param procedureName
     * @param param
     * @return
     */
    public static List getObjectsByStoreProcedureGeneral(String procedureName,Object param) {
        Query q;
        List results = null;
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
//            String finalQuery = "SELECT * " + query;
            String finalQuery = "";
            if (param==null) {
                finalQuery = "call " + procedureName ;
            }else {
                finalQuery = "call " + procedureName + "(" + param + ")";
            }
            q = hibeSession.createSQLQuery(finalQuery);
            results = q.list();

        } catch (Exception ex) {
            log.error("Error in getObjectsByCustomQuery: " + ex.getLocalizedMessage());
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }
        return results;
    }//EoM getObjectsBySqlQuery



    /**
     * Καλεί μια storeProcedure και επιστρέφει τις εγγραφές σε μια λίστα απο oblect.
     * Δεν χρειάζεται συγκεκριμένο classname
     * @param procedureName
     * @param param
     * @return
     */
    public static Object getObjectsByStoreProcedureGeneral1(String procedureName,Object param) {
        Query q;
        Object results = null;
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
//            String finalQuery = "SELECT * " + query;
            String finalQuery = "";
            if (param==null) {
                finalQuery = "call " + procedureName ;
            }else {
                finalQuery = "call " + procedureName + "(" + param + ")";
            }
            q = hibeSession.createSQLQuery(finalQuery);
            results = q.list().get(0);

        } catch (Exception ex) {
            log.error("Error in getObjectsByCustomQuery: " + ex.getLocalizedMessage());
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }
        return results;
    }//EoM getObjectsBySqlQuery

    /**
     *
     * @param procedureName
     * @param procedureVariables
     */
    public static void executeProcedure(String procedureName, Map<String, Object> procedureVariables) {

        // construct query
        String sql = "CALL " + procedureName + "(";

        int counter = 1;
        for (String variableName : procedureVariables.keySet()) {
            if (counter == 1) {
                sql += variableName + "')";
            }
        }

        log.info(sql);

        Session hibeSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibeTransaction = null;

        try {
            hibeTransaction = hibeSession.beginTransaction();
            Query query = hibeSession.createSQLQuery(sql);
            log.info("Executing query: " + sql);

            query.executeUpdate();
            hibeSession.getTransaction().commit();
            hibeSession.flush();
        } catch (Exception ex) {
            log.error("dbTransactions.getObjectsByProperty(): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("An error occured in executeProcedure");
                ex.printStackTrace();
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }
    }//EoM executeProcedure


    /**
     * @return Listi<Oblect></>
     */
    public static List<Object>  getObjectsByStoreProcedure(String classname, String procedureName,Object param) {
        Query q;
        List<Object> obj = null;
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String finalQuery = "call " + procedureName+ "("+param+")";


            // Applying pagination on query level. Otherwise, without HQL, the pagination is applied in the acquired resultset.


            q = hibeSession.createSQLQuery(finalQuery).addEntity("e", classname);


            obj = q.list();


        } catch (Exception ex) {
            log.error("Error in getObjectsByCustomQuery: " + ex.getLocalizedMessage());
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }
        return obj;
    }
    //EoM getObjectsByStoreProcedure

    public static List<Object> getObjectsByProperties(String classname, Map<String, Object> properties, Map<String, Object[]> advancedProperties,
            Integer page, Integer pagesize, Map<String, Integer> sortproperties) {

        // construct query
        int counter = 1;
        String query = "From " + classname + " c " + " where ";

        // add properties
        for (String propertyKey : properties.keySet()) {
            if (counter == 1) {
                query += "upper(" + propertyKey + ") like upper(:param" + counter++ + ") ";
            } else {
                query += " AND upper(" + propertyKey + ") like upper(:param" + counter++ + ") ";
            }
        }

        // add advanced properties
        if (advancedProperties != null) {
            for (String propertyKey : advancedProperties.keySet()) {
                if (counter == 1) {
                    query += propertyKey + " " + advancedProperties.get(propertyKey)[0] + " :param" + counter++ + " ";
                } else {
                    query += " AND " + propertyKey + " " + advancedProperties.get(propertyKey)[0] + " :param" + counter++ + " ";
                }
            }
        }

        // add sorting properties
        if (sortproperties != null) {
            counter = 1;
            for (String sortKey : sortproperties.keySet()) {
                if (counter == 1) {
                    query += " order by " + sortKey + " " + ((sortproperties.get(sortKey) == 0) ? "ASC" : "DESC");
                } else {
                    query += " ," + sortKey + " " + ((sortproperties.get(sortKey) == 0) ? "ASC" : "DESC");
                }
                counter++;
            }
        }

        Session hibeSession = null;
        Transaction hibeTransaction = null;
        List<Object> retlist = null;

        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            Query q = hibeSession.createQuery(query);
            log.info("Executing query " + query);
            counter = 1;
            for (Object propertyValue : properties.values()) {
                if (propertyValue instanceof String) {
                    q.setParameter("param" + counter++, "%" + (String) propertyValue + "%");
                } else if (propertyValue instanceof Integer) {
                    q.setParameter("param" + counter++, propertyValue);
                } else if (propertyValue instanceof Short) {
                    q.setParameter("param" + counter++, propertyValue);
                } else if (propertyValue instanceof Date) {
                    q.setParameter("param" + counter++, propertyValue);
                } else if (propertyValue instanceof Long) {
                    q.setParameter("param" + counter++, propertyValue);
                }
            }
            if (advancedProperties != null) {
                for (Object[] o : advancedProperties.values()) {
                    if (o[1] instanceof String) {
                        q.setParameter("param" + counter++, "%" + (String) o[1] + "%");
                    } else if (o[1] instanceof Integer) {
                        q.setParameter("param" + counter++, (Integer) o[1]);
                    } else if (o[1] instanceof Short) {
                        q.setParameter("param" + counter++, (Short) o[1]);
                    } else if (o[1] instanceof Date) {
                        q.setDate("param" + counter++, (Date) o[1]);
                    }
                }
            }

            // enable pagination only if values are set
            if (page != null && pagesize != null) {
                q.setFirstResult((page - 1) * pagesize);
                q.setMaxResults(pagesize);
            }
            retlist = q.list();
        } catch (Exception ex) {
            log.error("dbTransactions.getObjectsByProperty(): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("An error occured in getObjectsByProperties with classname " + classname
                        + " and properties " + properties);
                ex.printStackTrace();
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }
        return retlist;
    }//EoM executeProcedure

    /**
     * Counts all objects against String and Integer property values.
     *
     * @param classname
     * @param stringPropertyValues
     * @param integerPropertyValues
     * @return
     */
    public static Integer countAllObjectsByProperties(String classname, Map<String, Object> properties) {
        return countAllObjectsByProperties(classname, properties, null);
    }//EoM countAllObjectsByProperties

    /**
     * Counts all objects against String and Integer property values.
     *
     * @param classname
     * @param stringPropertyValues
     * @param integerPropertyValues
     * @return
     */
    public static Integer countAllObjectsByProperties(String classname, Map<String, Object> properties, Map<String, Object[]> advancedProperties) {

        // construct query
        String query = "Select count(*) from " + classname + " c where ";
        int counter = 1;
        for (String propertyKey : properties.keySet()) {
            if (counter == 1) {
                query += "upper(" + propertyKey + ") like upper(:param" + counter++ + ") ";
            } else {
                query += " AND upper(" + propertyKey + ") like upper(:param" + counter++ + ") ";
            }
        }

        // add advanced properties
        if (advancedProperties != null) {
            for (String propertyKey : advancedProperties.keySet()) {
                if (counter == 1) {
                    query += propertyKey + " " + advancedProperties.get(propertyKey)[0] + " :param" + counter++ + " ";
                } else {
                    query += " AND " + propertyKey + " " + advancedProperties.get(propertyKey)[0] + " :param" + counter++ + " ";
                }
            }
        }

        Session hibeSession = null;
        Transaction hibeTransaction = null;
        Integer num = null;
        Query q = null;

        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            q = hibeSession.createQuery(query);

            counter = 1;
            for (Object propertyValue : properties.values()) {
                if (propertyValue instanceof String) {
                    q.setParameter("param" + counter++, "%" + propertyValue + "%");
                } else if (propertyValue instanceof Integer) {
                    q.setParameter("param" + counter++, propertyValue);
                } else if (propertyValue instanceof Short) {
                    q.setParameter("param" + counter++, propertyValue);
                } else if (propertyValue instanceof Date) {
                    q.setParameter("param" + counter++, propertyValue);
                } else if (propertyValue instanceof Long) {
                    q.setParameter("param" + counter++, propertyValue);
                }
            }
            if (advancedProperties != null) {
                for (Object[] o : advancedProperties.values()) {
                    if (o[1] instanceof String) {
                        q.setParameter("param" + counter++, "%" + (String) o[1] + "%");
                    } else if (o[1] instanceof Integer) {
                        q.setParameter("param" + counter++, (Integer) o[1]);
                    } else if (o[1] instanceof Short) {
                        q.setParameter("param" + counter++, (Short) o[1]);
                    } else if (o[1] instanceof Date) {
                        q.setDate("param" + counter++, (Date) o[1]);
                    }
                }
            }

            num = ((Long) q.uniqueResult()).intValue();

        } catch (Exception ex) {
            log.error("dbTransactions.getObjectsByProperties(" + classname + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        return num;
    }//EoM countAllObjectsByProperties

    public static List<Object> getObjectsBySqlQuery(String classname, String query, List<Object> params, Integer page, Integer pagesize) {

        Query q;
        List<Object> obj = null;
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String finalQuery = "SELECT {e.*} " + query;
            if(classname == null){
                finalQuery = "SELECT * " + query;
            }

            // Applying pagination on query level. Otherwise, without HQL, the pagination is applied in the acquired resultset.
            if (page != null && pagesize != null) {
                finalQuery = "SELECT * FROM (SELECT row_.*, rownum rownum_ FROM ( " + finalQuery + " ) row_) WHERE rownum_ > " + ((page - 1) * pagesize) + " AND rownum_ <= " + (((page - 1) * pagesize) + pagesize);
            }
            q = hibeSession.createSQLQuery(finalQuery);
            if (classname != null) {
                ((SQLQuery) q).addEntity("e", classname);
            }
            if (params != null) {
                int counter = 1;
                for (Object o : params) {
                    if (o instanceof String) {
                        q.setParameter("param" + counter++, (String) o);
                    } else if (o instanceof Integer) {
                        q.setParameter("param" + counter++, o);
                    } else if (o instanceof Date) {
                        q.setDate("param" + counter++, (Date) o);
                    }
                }
            }

            obj = q.list();

        } catch (Exception ex) {
            log.error("Error in getObjectsByCustomQuery: " + ex.getLocalizedMessage());
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }
        return obj;

    }//EoM getObjectsBySqlQuery

    /**
     * Παίρνει ακριβώς το query με distinct, όμως πρέπει να φτιαχτεί κατάλληλη
     * κλάση για την διαχείρηη των αποτελεσμάτων sto hbm
     *
     * @param classname
     * @param query
     * @return
     */
    public static List<Object> getObjectsBySqlQueryDistinct(String classname, String query) {

        Query q;
        List<Object> obj = null;
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String finalQuery = "SELECT distinct {e.*} " + query;


            q = hibeSession.createSQLQuery(finalQuery).addEntity("e", classname);

            obj = q.list();

        } catch (Exception ex) {
            log.error("Error in getObjectsByCustomQuery: " + ex.getLocalizedMessage());
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectsByProperty(" + classname + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }
        return obj;

    }

    /**
     *
     * @param classname
     * @param query
     * @param params
     * @return
     */
    public static Integer countObjectsBySqlQuery(String classname, String query, List<Object> params) {

        Query q;
        Integer obj = null;
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            q = hibeSession.createSQLQuery("SELECT count(*) " + query);

            if (params != null) {
                int counter = 1;
                for (Object o : params) {
                    if (o instanceof String) {
                        q.setParameter("param" + counter++, (String) o);
                    } else if (o instanceof Integer) {
                        q.setParameter("param" + counter++, o);
                    } else if (o instanceof Date) {
                        q.setDate("param" + counter++, (Date) o);
                    } else if (o instanceof Long) {
                        q.setLong("param" + counter++, (Long) o);
                    } else if (o instanceof Short) {
                        q.setShort("param" + counter++, (Short) o);
                    } else if (o instanceof Double) {
                        q.setDouble("param" + counter++, (Double) o);
                    }
                }
            }

            obj = ((BigDecimal) q.uniqueResult()).intValue();

        } catch (Exception ex) {
            log.error("Error in countObjectsByCustomQuery: " + ex.getLocalizedMessage());
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectsByProperty(" + classname + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }
        return obj;
    }




    public static List<Object> getObjectsBySqlQueryNew(String classname, String query, List<Object> params, Integer page, Integer pagesize) {

        Query q;
        List<Object> obj = null;
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String finalQuery = "SELECT {e.*} " + query;

            // Applying pagination on query level. Otherwise, without HQL, the pagination is applied in the acquired resultset.
            if (page != null && pagesize != null) {
                finalQuery = "SELECT * FROM (SELECT row_.*, rownum rownum_ FROM ( " + finalQuery + " ) row_) WHERE rownum_ > " + ((page - 1) * pagesize) + " AND rownum_ <= " + (((page - 1) * pagesize) + pagesize);
            }
            q = hibeSession.createSQLQuery(finalQuery).addEntity("e", classname);

            if (params != null) {
                int counter = 1;
                for (Object o : params) {
                    if (o instanceof String) {
                        q.setParameter("param" + counter++, (String) o);
                    } else if (o instanceof Integer) {
                        q.setParameter("param" + counter++, o);
                    } else if (o instanceof Date) {
                        q.setDate("param" + counter++, (Date) o);
                    }
                }
            }

            obj = q.list();

        } catch (Exception ex) {
            log.error("Error in getObjectsByCustomQuery: " + ex.getLocalizedMessage());
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }
        return obj;
    }

    public static List getObjectsBySqlQueryNew2(String query) {
        Query q;
        List results = null;
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String finalQuery = "SELECT * " + query;

            q = hibeSession.createSQLQuery(finalQuery);
            //q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            results = q.list();

        } catch (Exception ex) {
            log.error("Error in getObjectsByCustomQuery: " + ex.getLocalizedMessage());
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }
        return results;
    }//EoM getObjectsBySqlQuery



    /**
     * Επιστρέφει όλα τα αντικείμενα (εγγραφές) ενός πίνακα.
     *
     * @param classname
     * @param query
     * @param params
     * @return
     */
    public static List<Object> getAllObjects(String classname) {

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        List<Object> retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();

            String query = "from " + classname + " c ";
            log.debug("dbTransactions.getAllObjects(): Querying: select * " + query);
            Query q = hibeSession.createQuery(query);

            //Perform The Query
            retlist = (List<Object>) q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.getAllObjects(" + classname + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getAllObjects(" + classname + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.debug("dbTransactions.getAllObjects(" + classname + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getAllObjects    

    /**
     * Method getAllObjects(String classname) loads all hibernate Objects from
     * the database. This method should be avoided if the amount of entries is
     * excessive
     *
     * @param classname	The classname as mapped in the hibernate configuration
     * @return	The hibernate List of classes or null
     */
    public static ScrollableResults getAllObjectsScrolled(String classname) {
        //log.info("dbTransactions.getAllObjects("+classname+") called");

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        ScrollableResults retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();

            String query = "from " + classname + " c ";
            //log.debug("dbTransactions.getAllObjects(): Querying: "+query);  
            Query q = hibeSession.createQuery(query);

            //Perform The Query
            retlist = q.scroll();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.getAllObjects(" + classname + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getAllObjects(" + classname + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.debug("dbTransactions.getAllObjects(" + classname + ") returns List<Object>.size:" + ((retlist != null) ? retlist : null));
        return retlist;
    }//EoM getAllObjects    

    /**
     * Method getObjectsPaginated(String classname, int page, int pagesize)
     * loads hibernate Objects from the database that belong to specific
     * page(i.e. not all objects). This method should be used to improve
     * performance during data-fetch
     *
     * @param classname	The classname as mapped in the hibernate configuration
     * @param page The required page pivot
     * @param pagesize The required page size
     * @return	The hibernate List of classes or null
     */
    public static List<Object> getObjectsPaginated(String classname, int page, int pagesize) {
        log.debug("dbTransactions.getObjectsPaginated(" + classname + "," + page + "," + pagesize + ") called");

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        List<Object> retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String query = "from " + classname + " c ";
            log.info("dbTransactions.getObjectsPaginated(" + classname + "," + page + "," + pagesize + "): Querying: " + query);  //sos to be added
            Query q = hibeSession.createQuery(query);
            q.setFirstResult((page - 1) * pagesize);
            q.setMaxResults(pagesize);

            //Perform The Query
            retlist = q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.getObjectsPaginated(" + classname + "," + page + "," + pagesize + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectsPaginated(" + classname + "," + page + "," + pagesize + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getObjectsPaginated(" + classname + "," + page + "," + pagesize + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getObjectsPaginated  

    public static List<Object> getAllObjectsSortedDistinct2(String classname, String sortproperty, int sorttype, String distinctproperty1, String distinctproperty2) {
        log.debug("dbTransactions.getAllObjectsSorted(" + classname + "," + sortproperty + "," + sorttype + ") called");

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        List<Object> retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String query = "select distinct c." + distinctproperty1 + ",c." + distinctproperty2 + " from " + classname + " c order by c." + sortproperty + " " + ((sorttype == 0) ? "asc" : "desc");
            log.debug("dbTransactions.getAllObjectsSorted(" + classname + "," + sortproperty + "," + sorttype + "): Querying: " + query);
            Query q = hibeSession.createQuery(query);

            //Perform The Query
            retlist = q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.getAllObjectsSorted(" + classname + "," + sortproperty + "," + sorttype + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getAllObjectsSorted(" + classname + "," + sortproperty + "," + sorttype + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getAllObjectsSorted(" + classname + "," + sortproperty + "," + sorttype + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getAllObjectsSorted 
    
    
      /**
     * Method getAllObjectsSortedBykeyProperty(String classname, String sortprop,int sort)
     * loads all hibernate Objects from the database sorted by a class parameter
     * (e.g. firstname). This method should be avoided if the amount of entries
     * is excessive
     *
     * @param classname The classname as mapped in the hibernate configuration
     * @param sortkeyproperty The property that will be used during sorting
     * @param sorttype If 0 Sorting will be ASC else Sorting will be DESC
     * @return	The hibernate List of classes or null
     */
    public static List<Object> getAllObjectsSortedBykeyProperty(String classname, String sortkeyproperty, int sorttype) {

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        List<Object> retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String query = "from " + classname + " c order by c.id." + sortkeyproperty + " " + ((sorttype == 0) ? "asc" : "desc");
            log.debug("dbTransactions.getAllObjectsSortedBykeyProperty(" + classname + "," + sortkeyproperty + "," + sorttype + "): Querying: " + query);
            Query q = hibeSession.createQuery(query);

            //Perform The Query
            retlist = q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.getAllObjectsSortedBykeyProperty(" + classname + "," + sortkeyproperty + "," + sorttype + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getAllObjectsSortedBykeyProperty(" + classname + "," + sortkeyproperty + "," + sorttype + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getAllObjectsSortedBykeyProperty(" + classname + "," + sortkeyproperty + "," + sorttype + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getAllObjectsSortedBykeyProperty  
    
    
    
    

    /**
     * Method getAllObjectsSorted(String classname, String sortprop,int sort)
     * loads all hibernate Objects from the database sorted by a class parameter
     * (e.g. firstname). This method should be avoided if the amount of entries
     * is excessive
     *
     * @param classname The classname as mapped in the hibernate configuration
     * @param sortproperty The property that will be used during sorting
     * @param sorttype If 0 Sorting will be ASC else Sorting will be DESC
     * @return	The hibernate List of classes or null
     */
    public static List<Object> getAllObjectsSorted(String classname, String sortproperty, int sorttype) {
        //log.debug("dbTransactions.getAllObjectsSorted("+classname+","+sortproperty+","+sorttype+") called");

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        List<Object> retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String query = "from " + classname + " c order by c." + sortproperty + " " + ((sorttype == 0) ? "asc" : "desc");
            log.debug("dbTransactions.getAllObjectsSorted(" + classname + "," + sortproperty + "," + sorttype + "): Querying: " + query);
            Query q = hibeSession.createQuery(query);

            //Perform The Query
            retlist = q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.getAllObjectsSorted(" + classname + "," + sortproperty + "," + sorttype + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getAllObjectsSorted(" + classname + "," + sortproperty + "," + sorttype + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getAllObjectsSorted(" + classname + "," + sortproperty + "," + sorttype + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getAllObjectsSorted   

    /**
     * Method countAllObjects(String classname) counts all hibernate Objects
     * from the database without loading them (lazy way)
     *
     * @param classname	The classname as mapped in the hibernate configuration
     * @return	The amount of classes or null
     */
    public static Integer countAllObjects(String classname) {
        log.debug("dbTransactions.countAllObjects(" + classname + ") called");
        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Initialisation
        Integer count = 0;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();

            count = ((Long) hibeSession.createQuery("select count(*) from " + classname).iterate().next()).intValue();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.countAllObjects(" + classname + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.countAllObjects(" + classname + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.countAllObjects(" + classname + ") returns count:" + count);

        return count;
    }//EoM countAllObjects

    /**
     * Method countAllObjects(String classname) counts all hibernate Objects
     * from the database without loading them (lazy way)
     *
     * @param classname	The classname as mapped in the hibernate configuration
     * @return	The amount of classes or null
     */
    public static Integer countObjectsByProperty(String classname, String propertyName) {
        log.debug("dbTransactions.countAllObjects(" + classname + ") called");
        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Initialisation
        Integer count = 0;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();

            String query = "Select count(*) from " + classname + " c group by c." + propertyName;
            count = ((Long) hibeSession.createQuery(query).iterate().next()).intValue();
            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.countAllObjects(" + classname + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.countAllObjects(" + classname + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.countAllObjects(" + classname + ") returns count:" + count);

        return count;
    }//EoM countAllObjects    

    /**
     * Method getMaxByProperty(String classname)
     *
     * @param classname	The classname as mapped in the hibernate configuration
     * @return	The amount of classes or null
     */
    public static Object getMaxByProperty(String classname, String propertyName) {

        Session hibeSession = null;
        Transaction hibeTransaction = null;
        Object obj = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();

            Query q = hibeSession.createQuery("select max(" + propertyName + ") from " + classname);
            obj = q.uniqueResult();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.countAllObjects(" + classname + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.countAllObjects(" + classname + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.countAllObjects(" + classname);

        return obj;
    }//EoM getMaxByProperty    

    /**
     * Method getObjectsByProperty(String classname, String propertyname,String
     * propertyvalue) loads all hibernate Objects from the database after
     * applying a custom where clause related to the property (e.g.
     * "firstname","first1").
     *
     * @param classname The classname as mapped in the hibernate configuration
     * @param propertyname The property that will be used during sorting
     * @param propertyvalue The property value that will be used during sorting
     * @return	The hibernate List of classes or null
     */
    public static List<Object> getObjectsByProperty(String classname, String propertyname, Object propertyvalue) {
        //log.debug("dbTransactions.getObjectsByProperty("+classname+","+propertyname+","+propertyvalue+") called");

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        List<Object> retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String query = "from " + classname + " c where c." + propertyname + "=:param1";
            log.debug("dbTransactions.getObjectsByProperty(" + classname + "," + propertyname + "," + propertyvalue + "): Querying: " + query);
            Query q = hibeSession.createQuery(query);

            if (propertyvalue instanceof String) {
                q.setParameter("param1", (String) propertyvalue);
            } else if (propertyvalue instanceof Long) {
                q.setParameter("param1", (Long) propertyvalue);
            } else if (propertyvalue instanceof Integer) {
                q.setParameter("param1", (Integer) propertyvalue);
            } else if (propertyvalue instanceof BigDecimal) {
                q.setParameter("param1", (BigDecimal) propertyvalue);
            } else if (propertyvalue instanceof Short) {
                q.setParameter("param1", (Short) propertyvalue);
            }
            //Perform The Query
            retlist = q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.getObjectsByProperty(" + classname + "," + propertyname + "," + propertyvalue + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectsByProperty(" + classname + "," + propertyname + "," + propertyvalue + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getObjectsByProperty(" + classname + "," + propertyname + "," + propertyvalue + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getObjectsByProperty  

    /**
     * Method getObjectsByProperty(String classname, String propertyname,String
     * propertyvalue) loads all hibernate Objects from the database after
     * applying a custom where clause related to the property (e.g.
     * "firstname","first1").
     *
     * @param classname The classname as mapped in the hibernate configuration
     * @param propertyname The property that will be used during sorting
     * @param propertyvalue The property value that will be used during sorting
     * @return	The hibernate List of classes or null
     */
    public static List<Object> getObjectsByPropertyPaginated(String classname, String propertyname, Object propertyvalue, Integer page, Integer pageSize) {

        Session hibeSession = null;
        Transaction hibeTransaction = null;
        List<Object> retlist = null;

        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String query = "from " + classname + " c where c." + propertyname + "=:param1";

            Query q = hibeSession.createQuery(query);
            q.setParameter("param1", propertyvalue);

            // Paginate
            q.setFirstResult((page - 1) * pageSize);
            q.setMaxResults(pageSize);

            // Perform The Query
            retlist = q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.getObjectsByProperty(" + classname + "," + propertyname + "," + propertyvalue + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectsByProperty(" + classname + "," + propertyname + "," + propertyvalue + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getObjectsByProperty(" + classname + "," + propertyname + "," + propertyvalue + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getObjectsByProperty  

    /**
     * Method getObjectsByPropertyLike(String classname, String
     * propertyname,String propertyvalue) loads all hibernate Objects from the
     * database after applying a custom where clause related to the property
     * (e.g. "firstname","first1").
     *
     * @param classname The classname as mapped in the hibernate configuration
     * @param propertyname The property that will be used during sorting
     * @param propertyvalue The property value that will be used during sorting
     * @return	The hibernate List of classes or null
     */
    public static List<Object> getObjectsByPropertyLike(String classname, String propertyname, String propertyvalue) {

        Session hibeSession = null;
        Transaction hibeTransaction = null;
        List<Object> retlist = null;

        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String query = "from " + classname + " c where c." + propertyname + " like :param1";
            log.debug("dbTransactions.getObjectsByProperty(" + classname + "," + propertyname + "," + propertyvalue + "): Querying: " + query);
            Query q = hibeSession.createQuery(query);
            q.setParameter("param1", propertyvalue + "%");
            //Perform The Query
            retlist = q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.getObjectsByProperty(" + classname + "," + propertyname + "," + propertyvalue + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectsByProperty(" + classname + "," + propertyname + "," + propertyvalue + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getObjectsByProperty(" + classname + "," + propertyname + "," + propertyvalue + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getObjectsByProperty  

    public static void storeObjectsDeleteObjects(List storeObjects,List deleteObjects) {
        log.debug("dbTransactions.storeObjectsUpdateObjects(" + storeObjects + "," + deleteObjects + ") called");

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();

            for(Object obj: deleteObjects){
                hibeSession.delete(obj);
            }
            
            for (Object obj : storeObjects) {
                hibeSession.save(obj);
            }
            
            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("dbTransactions.storeObjectsUpdateObjects(" + storeObjects + "," + deleteObjects + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.storeObjectsUpdateObjects(" + storeObjects + "," + deleteObjects +  "): An error occurred attempting to roll back the transaction.", re);
            }
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Σφάλμα κατά την εισαγωγή", "Σφάλμα κατά την εισαγωγή");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }
    }//EoM storeObjectsDeleteObjects
    
        /**
     * I methodos auti kalite apo to SSO web service
     * @param obj
     * @return
     * @throws Exception 
     */
    public static Object storeObjectFromSsoService(Object obj) throws Exception{
        log.debug("dbTransactions.storeObjectFromSsoService(" + obj + ") called");

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();

            hibeSession.save(obj);

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("dbTransactions.storeObjectFromSsoService(" + obj + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.storeObjectFromSsoService(" + obj + "): An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }
        return (obj);
    }//EoM storeObjectFromSsoService
    
    public static Object storeObject(Object obj) {
        log.debug("dbTransactions.storeObject(" + obj + ") called");

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();

            hibeSession.save(obj);

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("dbTransactions.storeObject(" + obj + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.storeObject(" + obj + "): An error occurred attempting to roll back the transaction.", re);
            }
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Σφάλμα κατά την εισαγωγή", "Σφάλμα κατά την εισαγωγή");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }
        return (obj);
    }//EoM storeObject

    /**
     *
     * @param classname
     * @param query
     * @param params
     * @return
     */
    public static Object storeObjectGetId(Object obj) {
        log.debug("dbTransactions.storeObject(" + obj + ") called");

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();

            Long ret = (Long) hibeSession.save(obj);
            System.out.println("-->" + ret);
            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("dbTransactions.storeObject(" + obj + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.storeObject(" + obj + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
            return obj;
        }
    }//EoM storeObject    

    /**
     *
     * @param classname
     * @param query
     * @param params
     * @return
     */
    public static int storeObjectId(Object obj) {
        log.debug("dbTransactions.storeObject(" + obj + ") called");
        int ret = -1;
        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();

            Integer integer = (Integer) hibeSession.save(obj);
            ret = integer;
            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("dbTransactions.storeObject(" + obj + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.storeObject(" + obj + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }
        return ret;
    }//EoM storeObject    

    /**
     *
     * @param classname The classname as mapped in the hibernate configuration
     * @param query
     * @param params
     * @return
     */
    public static void updateObject(Object obj) {
        log.debug("dbTransactions.updateObject(" + obj + ") called");

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();

            hibeSession.update(obj);

            hibeTransaction.commit();

        } catch (Exception ex) {
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                System.out.println("dbTransactions.updateObject(" + obj + "): An error occurred attempting to roll back the transaction."+ re);
            }

            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Σφάλμα κατά την ενημέρωση", "Σφάλμα κατά την ενημέρωση");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }
        log.info("dbTransactions.updateObject(" + obj + ") returns");
    }//EoM updateObject        

    /**
     *
     * @param classname The classname as mapped in the hibernate configuration
     * @param query
     * @param params
     * @return
     */
    public Set convertListToSet(List list, String classname) {
        Set ret = new HashSet(0);
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object obj1 = iter.next();
            ret.add(obj1);
            //rest of the code block removed
        }
        return ret;
    }//EoM convertListToSet

    /**
     * Method getObjectsByManyToOneLong(String classname,String
     * manyToOnename,String refidname,String manyToOnevalue) loads all hibernate
     * Objects from the database after applying a custom where clause related to
     * the property (e.g. "firstname","first1").
     *
     * @param classname The classname as mapped in the hibernate configuration
     * @param propertyname The property that will be used during sorting
     * @param propertyvalue The property value that will be used during sorting
     * @return	The hibernate List of classes or null
     */
    public static List<Object> getObjectsByManyToOneLong(String classname, String manyToOnename, String refidname, String manyToOnevalue) {
        log.debug("dbTransactions.getObjectsByProperty(" + classname + ", called");

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        List<Object> retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String query = "from " + classname + " c where c." + manyToOnename + "." + refidname + "=:param1";
            log.debug("dbTransactions.getObjectsByProperty(" + classname + "): Querying: " + query);
            Query q = hibeSession.createQuery(query);
            q.setParameter("param1", new Long(manyToOnevalue));
            //Perform The Query
            retlist = q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("dbTransactions.getObjectsByProperty(" + classname + "),: An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectsByProperty(" + classname + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getObjectsByProperty(" + classname + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getObjectsByProperty      

    /**
     * Method getObjectsByManyToOneString(String classname,String
     * manyToOnename,String refidname,String manyToOnevalue) loads all hibernate
     * Objects from the database after applying a custom where clause related to
     * the property (e.g. "firstname","first1").
     *
     * @param classname The classname as mapped in the hibernate configuration
     * @param propertyname The property that will be used during sorting
     * @param propertyvalue The property value that will be used during sorting
     * @return	The hibernate List of classes or null
     */
    public static List<Object> getObjectsByManyToOneString(String classname, String manyToOnename, String refidname, String manyToOnevalue) {
        //log.debug("dbTransactions.getObjectsByProperty("+classname+","+propertyname+","+propertyvalue+") called");

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        List<Object> retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String query = "from " + classname + " c where c." + manyToOnename + "." + refidname + "=:param1";
            log.debug("dbTransactions.getObjectsByProperty(" + classname + "): Querying: " + query);
            Query q = hibeSession.createQuery(query);
            q.setParameter("param1", manyToOnevalue);
            //Perform The Query
            retlist = q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("dbTransactions.getObjectsByProperty(" + classname + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectsByProperty(" + classname + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getObjectsByProperty(" + classname + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getObjectsByProperty

    /**
     * Method Object deleteObject(Object obj) deletes from the database the
     * according record of the object obj, based on primary key(unique or
     * composite ID)
     *
     * @param obj The object which accords to the record that we want to delete
     * from the database
     * @return	The hibernate List of classes or null The hibernate List of
     * classes or null
     */
    public static Object deleteObject(Object obj) {

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();

            hibeSession.delete(obj);

            hibeTransaction.commit();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
            }

            //   throw new FacesException("Σφάλμα κατά την διαγραφή");
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Σφάλμα κατά την διαγραφή", "Σφάλμα κατά την διαγραφή");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }
        return (obj);
    }//EoM getObjectsByProperty

    /**
     *
     * @param classname
     * @param query
     * @param params
     * @return
     */
    public static void deleteObjectsByManyToOneLong(String classname, String manyToOnename, String refidname, String manyToOnevalue) {
        log.debug("dbTransactions.getObjectsByProperty(" + classname + ") called");

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        List<Object> retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String query = "from " + classname + " c where c." + manyToOnename + "." + refidname + "=:param1";
            log.debug("dbTransactions.getObjectsByProperty(" + classname + "): Querying: " + query);
            Query q = hibeSession.createQuery(query);
            q.setParameter("param1", new Long(manyToOnevalue));
            //Perform The Query
            retlist = q.list();

            if (retlist != null) {
                Iterator itr = retlist.iterator();
                while (itr.hasNext()) {
                    hibeSession.delete(itr.next());
                }//eowhile
            }

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("dbTransactions.getObjectsByProperty(" + classname + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectsByProperty(" + classname + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getObjectsByProperty(" + classname + "+) returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));

    }//EoM getObjectsByProperty   

    /**
     * Method getObjectsByManyToOneLong(String classname,String
     * manyToOnename,String refidname,String manyToOnevalue) loads all hibernate
     * Objects from the database after applying a custom where clause related to
     * the property (e.g. "firstname","first1").
     *
     * @param classname The classname as mapped in the hibernate configuration
     * @param propertyname The property that will be used during sorting
     * @param propertyvalue The property value that will be used during sorting
     * @param sortproperty The prperty for shorting
     * @param sorttype if 0 sort is asc or 1 desc
     * @return	The hibernate List of classes or null
     */
    public static List<Object> getObjectsByManyToOneLongSorted(String classname, String manyToOnename, String refidname, String manyToOnevalue, String sortproperty, int sorttype) {
        //log.debug("dbTransactions.getObjectsByProperty("+classname+","+propertyname+","+propertyvalue+") called");

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        List<Object> retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String query = "from " + classname + " c where c." + manyToOnename + "." + refidname + "=:param1" + " order by c." + sortproperty + " " + ((sorttype == 0) ? "asc" : "desc");

            log.debug("dbTransactions.getObjectsByProperty(" + classname + "+): Querying: " + query);
            Query q = hibeSession.createQuery(query);
            q.setParameter("param1", new Long(manyToOnevalue));
            //Perform The Query
            retlist = q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("dbTransactions.getObjectsByProperty(" + classname + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectsByProperty(" + classname + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getObjectsByProperty(" + classname + "," + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getObjectsByProperty

    /**
     * Method getObjectsByKeyProperty(String classname, String
     * keyPropertyname,String keyPropertyvalue) loads all hibernate Objects from
     * the database after applying a custom where clause related to the
     * keyProperty (e.g. "firstname","first1").
     *
     * @param classname The classname as mapped in the hibernate configuration
     * @param keyPropertyname The property that will be used during sorting
     * @param keyPropertyvalue The property value that will be used during
     * sorting
     * @return	The hibernate List of classes or null
     */
    public static List<Object> getObjectsByKeyProperty(String classname, String keyPropertyname, Object keyPropertyvalue) {

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        List<Object> retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String query = "from " + classname + " c where c.id." + keyPropertyname + "=:param1";

            Query q = hibeSession.createQuery(query);
            if (keyPropertyvalue instanceof String) {
                q.setParameter("param1", (String) keyPropertyvalue);
            } else if (keyPropertyvalue instanceof Long) {
                q.setParameter("param1", (Long) keyPropertyvalue);
            } else if (keyPropertyvalue instanceof Integer) {
                q.setParameter("param1", (Integer) keyPropertyvalue);
            } else if (keyPropertyvalue instanceof Date) {
                q.setParameter("param1", (Date) keyPropertyvalue);
            } else if (keyPropertyvalue instanceof BigDecimal) {
                q.setParameter("param1", (BigDecimal) keyPropertyvalue);
            }

            //Perform The Query
            retlist = q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.getObjectsByProperty(" + classname + "," + keyPropertyname + "," + keyPropertyvalue + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectsByProperty(" + classname + "," + keyPropertyname + "," + keyPropertyvalue + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getObjectsByProperty(" + classname + "," + keyPropertyname + "," + keyPropertyvalue + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getObjectsByProperty 

    /**
     * Method getObjectsByKeyPropertySorted(String classname, String
     * keyPropertyname,String keyPropertyvalue,String sortproperty, int
     * sorttype) loads all hibernate Objects from the database after applying a
     * custom where clause related to the keyProperty (e.g.
     * "firstname","first1").
     *
     * @param classname The classname as mapped in the hibernate configuration
     * @param keyPropertyname The keyproperty that will be used during search
     * @param keyPropertyvalue The keyproperty value that will be used during
     * search
     * @param sortproperty The property that will be used during sorting
     * @param sorttype If 0 Sorting will be ASC else Sorting will be DESC
     * sorting
     * @return	The hibernate List of classes or null
     */
    public static List<Object> getObjectsByKeyPropertySorted(String classname, String keyPropertyname, Object keyPropertyvalue, String sortproperty, int sorttype) {

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        List<Object> retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String query = "from " + classname + " c where c.id." + keyPropertyname + "=:param1" + " order by c.id." + sortproperty + " " + ((sorttype == 0) ? "asc" : "desc");

            Query q = hibeSession.createQuery(query);
            if (keyPropertyvalue instanceof String) {
                q.setParameter("param1", (String) keyPropertyvalue);
            } else if (keyPropertyvalue instanceof Long) {
                q.setParameter("param1", (Long) keyPropertyvalue);
            } else if (keyPropertyvalue instanceof Integer) {
                q.setParameter("param1", (Integer) keyPropertyvalue);
            } else if (keyPropertyvalue instanceof Date) {
                q.setParameter("param1", (Date) keyPropertyvalue);
            } else if (keyPropertyvalue instanceof BigDecimal) {
                q.setParameter("param1", (BigDecimal) keyPropertyvalue);
            }

            //Perform The Query
            retlist = q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.getObjectsByProperty(" + classname + "," + keyPropertyname + "," + keyPropertyvalue + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectsByProperty(" + classname + "," + keyPropertyname + "," + keyPropertyvalue + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getObjectsByKeyPropertySorted(" + classname + "," + keyPropertyname + "," + keyPropertyvalue + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getObjectsByKeyPropertySorted 
    
       /**
     * Method getObjectsByKeyPropertySorted(String classname, String
     * keyPropertyname,String keyPropertyvalue,String sortproperty, int
     * sorttype) loads all hibernate Objects from the database after applying a
     * custom where clause related to the keyProperty (e.g.
     * "firstname","first1").
     *
     * @param classname The classname as mapped in the hibernate configuration
     * @param keyPropertyname The keyproperty that will be used during search
     * @param keyPropertyvalue The keyproperty value that will be used during
     * search
     * @param sortproperty The property that will be used during sorting
     * @param sorttype If 0 Sorting will be ASC else Sorting will be DESC
     * sorting
     * @return	The hibernate List of classes or null
     */
    public static List<Object> getObjectsByKeyPropertySorted1(String classname, String keyPropertyname, Object keyPropertyvalue, String sortproperty, int sorttype) {

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        List<Object> retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String query = "from " + classname + " c where c.id." + keyPropertyname + "=:param1" + " order by c." + sortproperty + " " + ((sorttype == 0) ? "asc" : "desc");

            Query q = hibeSession.createQuery(query);
            if (keyPropertyvalue instanceof String) {
                q.setParameter("param1", (String) keyPropertyvalue);
            } else if (keyPropertyvalue instanceof Long) {
                q.setParameter("param1", (Long) keyPropertyvalue);
            } else if (keyPropertyvalue instanceof Integer) {
                q.setParameter("param1", (Integer) keyPropertyvalue);
            } else if (keyPropertyvalue instanceof Date) {
                q.setParameter("param1", (Date) keyPropertyvalue);
            } else if (keyPropertyvalue instanceof BigDecimal) {
                q.setParameter("param1", (BigDecimal) keyPropertyvalue);
            }

            //Perform The Query
            retlist = q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.getObjectsByProperty(" + classname + "," + keyPropertyname + "," + keyPropertyvalue + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectsByProperty(" + classname + "," + keyPropertyname + "," + keyPropertyvalue + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getObjectsByKeyPropertySorted(" + classname + "," + keyPropertyname + "," + keyPropertyvalue + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getObjectsByKeyPropertySorted 
    
    
    

    /**
     * Method getObjectsByTwoKeyPropertySorted(String classname, String
     * keyPropertyname1,String keyPropertyvalue1,String keyPropertyname2,String
     * keyPropertyvalue2,String sortproperty, int sorttype) loads all hibernate
     * Objects from the database after applying a custom where clause related to
     * the keyProperty (e.g. "firstname","first1").
     *
     * @param classname The classname as mapped in the hibernate configuration
     * @param keyPropertyname1 The keyproperty that will be used during search
     * @param keyPropertyvalue1 The keyproperty value that will be used during
     * search
     * @param keyPropertyname2 The keyproperty that will be used during search
     * @param keyPropertyvalue2 The keyproperty value that will be used during
     * search
     * @param sortproperty The property that will be used during sorting
     * @param sorttype If 0 Sorting will be ASC else Sorting will be DESC
     * sorting
     * @return	The hibernate List of classes or null
     */
    public static List<Object> getObjectsByTwoKeyPropertySorted(String classname, String keyPropertyname1, Object keyPropertyvalue1, String keyPropertyname2, Object keyPropertyvalue2, String sortproperty, int sorttype) {

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        List<Object> retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String query = "from " + classname + " c where c.id." + keyPropertyname1 + "=:param1 and c.id." + keyPropertyname2 + "=:param2 order by c.id." + sortproperty + " " + ((sorttype == 0) ? "asc" : "desc");

            Query q = hibeSession.createQuery(query);

            if (keyPropertyvalue1 instanceof String) {
                q.setParameter("param1", (String) keyPropertyvalue1);
            } else if (keyPropertyvalue1 instanceof Long) {
                q.setParameter("param1", (Long) keyPropertyvalue1);
            } else if (keyPropertyvalue1 instanceof Integer) {
                q.setParameter("param1", (Integer) keyPropertyvalue1);
            } else if (keyPropertyvalue1 instanceof Date) {
                q.setParameter("param1", (Date) keyPropertyvalue1);
            } else if (keyPropertyvalue1 instanceof BigDecimal) {
                q.setParameter("param1", (BigDecimal) keyPropertyvalue1);
            }

            if (keyPropertyvalue2 instanceof String) {
                q.setParameter("param2", (String) keyPropertyvalue2);
            } else if (keyPropertyvalue2 instanceof Long) {
                q.setParameter("param2", (Long) keyPropertyvalue2);
            } else if (keyPropertyvalue2 instanceof Integer) {
                q.setParameter("param2", (Integer) keyPropertyvalue2);
            } else if (keyPropertyvalue2 instanceof Date) {
                q.setParameter("param2", (Date) keyPropertyvalue2);
            } else if (keyPropertyvalue2 instanceof BigDecimal) {
                q.setParameter("param2", (BigDecimal) keyPropertyvalue2);
            }

            //Perform The Query
            retlist = q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.getObjectsByProperty(" + classname + "," + keyPropertyname1 + "," + keyPropertyvalue1 + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectsByProperty(" + classname + "," + keyPropertyname1 + "," + keyPropertyvalue1 + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getObjectsByProperty(" + classname + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getObjectsByTwoKeyPropertySorted 

    /**
     * @param classname The classname as mapped in the hibernate configuration
     * @param keyPropertyname The keyproperty that will be used during search
     * @param keyPropertyvalue The keyproperty value that will be used during
     * search
     * @param propertyname The property that will be used during search
     * @param propertyvalue The property value that will be used during search
     * @param sortproperty The key property that will be used during sorting
     * @param sorttype If 0 Sorting will be ASC else Sorting will be DESC
     * sorting
     * @return	The hibernate List of classes or null
     */
    public static List<Object> getObjectsByKeyPropertyAndPropertySortedByKeyProperty(String classname, String keyPropertyname, Object keyPropertyvalue, String propertyname, Object propertyvalue, String sortproperty, int sorttype) {

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        List<Object> retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String query = "from " + classname + " c where c.id." + keyPropertyname + "=:param1 and c." + propertyname + "=:param2 order by c.id." + sortproperty + " " + ((sorttype == 0) ? "asc" : "desc");

            Query q = hibeSession.createQuery(query);

            if (keyPropertyvalue instanceof String) {
                q.setParameter("param1", (String) keyPropertyvalue);
            } else if (keyPropertyvalue instanceof Long) {
                q.setParameter("param1", (Long) keyPropertyvalue);
            } else if (keyPropertyvalue instanceof Integer) {
                q.setParameter("param1", (Integer) keyPropertyvalue);
            } else if (keyPropertyvalue instanceof Date) {
                q.setParameter("param1", (Date) keyPropertyvalue);
            } else if (keyPropertyvalue instanceof BigDecimal) {
                q.setParameter("param1", (BigDecimal) keyPropertyvalue);
            }

            if (propertyvalue instanceof String) {
                q.setParameter("param2", (String) propertyvalue);
            } else if (propertyvalue instanceof Long) {
                q.setParameter("param2", (Long) propertyvalue);
            } else if (propertyvalue instanceof Integer) {
                q.setParameter("param2", (Integer) propertyvalue);
            } else if (propertyvalue instanceof Date) {
                q.setParameter("param2", (Date) propertyvalue);
            } else if (propertyvalue instanceof BigDecimal) {
                q.setParameter("param2", (BigDecimal) propertyvalue);
            }

            //Perform The Query
            retlist = q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.getObjectsByProperty(" + classname + "," + keyPropertyname + "," + keyPropertyvalue + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectsByProperty(" + classname + "," + keyPropertyname + "," + keyPropertyvalue + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getObjectsByProperty(" + classname + "," + keyPropertyname + "," + keyPropertyvalue + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getObjectsByKeyPropertyAndPropertySortedByKeyProperty 

    /**
     * Method getObjectsByPropertySorted(String classname, String
     * propertyname,String propertyvalue,String sortproperty, int sorttype)
     * loads all hibernate Objects from the database after applying a custom
     * where clause related to the property (e.g. "firstname","first1").
     *
     * @param classname The classname as mapped in the hibernate configuration
     * @param propertyname The property that will be used during sorting
     * @param propertyvalue The property value that will be used during sorting
     * @param sortproperty The property that will be used during sorting
     * @param sorttype If 0 Sorting will be ASC else Sorting will be DESC
     * @return	The hibernate List of classes or null
     */
    public static List<Object> getObjectsByPropertySorted(String classname, String propertyname, Object propertyvalue, String sortproperty, int sorttype) {
        log.debug("dbTransactions.getObjectsByProperty(" + classname + "," + propertyname + "," + propertyvalue + ") called");

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        List<Object> retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();

            String query = "from " + classname + " c where c." + propertyname + "=:param1" + " order by c." + sortproperty + " " + ((sorttype == 0) ? "asc" : "desc");
            log.debug("dbTransactions.getObjectsByProperty(" + classname + "," + propertyname + "," + propertyvalue + "): Querying: " + query);
            Query q = hibeSession.createQuery(query);

            if (propertyvalue instanceof String) {
                q.setParameter("param1", (String) propertyvalue);
            } else if (propertyvalue instanceof Long) {
                q.setParameter("param1", (Long) propertyvalue);
            } else if (propertyvalue instanceof Integer) {
                q.setParameter("param1", (Integer) propertyvalue);
            } else if (propertyvalue instanceof BigDecimal) {
                q.setParameter("param1", (BigDecimal) propertyvalue);
            } else if (propertyvalue instanceof Short) {
                q.setParameter("param1", (Short) propertyvalue);
            }
            //Perform The Query
            retlist = q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error("dbTransactions.getObjectsByProperty(" + classname + "," + propertyname + "," + propertyvalue + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectsByProperty(" + classname + "," + propertyname + "," + propertyvalue + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getObjectsByProperty(" + classname + "," + propertyname + "," + propertyvalue + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getObjectsByProperty  

    /**
     * Method getObjectsByManyToOneString(String classname,String
     * manyToOnename,String refidname,String manyToOnevalue,String sortproperty,
     * int sorttype) loads all hibernate Objects from the database after
     * applying a custom where clause related to the property (e.g.
     * "firstname","first1").
     *
     * @param classname The classname as mapped in the hibernate configuration
     * @param propertyname The property that will be used during sorting
     * @param propertyvalue The property value that will be used during sorting
     * @param sortproperty The property that will be used during sorting
     * @param sorttype If 0 Sorting will be ASC else Sorting will be DESC
     * @return	The hibernate List of classes or null
     */
    public static List<Object> getObjectsByManyToOneStringSorted(String classname, String manyToOnename, String refidname, String manyToOnevalue, String sortproperty, int sorttype) {
        log.debug("dbTransactions.getObjectsByProperty(" + classname + "+) called");

        //Hibernate Initialisation
        Session hibeSession = null;
        Transaction hibeTransaction = null;
        //Model Initialisation
        List<Object> retlist = null;
        try {
            hibeSession = HibernateUtil.getSessionFactory().openSession();
            hibeTransaction = hibeSession.beginTransaction();
            String query = "from " + classname + " c where c." + manyToOnename + "." + refidname + "=:param1" + " order by c." + sortproperty + " " + ((sorttype == 0) ? "asc" : "desc");
            //log.debug("dbTransactions.getObjectsByProperty("+classname+","+propertyname+","+propertyvalue+"): Querying: "+query); 
            Query q = hibeSession.createQuery(query);
            q.setParameter("param1", manyToOnevalue);
            //Perform The Query
            retlist = q.list();

            hibeTransaction.commit();

        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("dbTransactions.getObjectsByProperty(" + classname + "): An error occurred during the transaction. Attempting to RollBack", ex);
            try {
                hibeTransaction.rollback();
            } catch (Exception re) {
                log.error("dbTransactions.getObjectsByProperty(" + classname + "): An error occurred attempting to roll back the transaction.", re);
            }
        } finally {
            if (hibeSession != null) {
                hibeSession.close();
            }
        }

        log.info("dbTransactions.getObjectsByProperty(" + classname + ") returns List<Object>.size:" + ((retlist != null) ? retlist.size() : null));
        return retlist;
    }//EoM getObjectsByProperty
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////