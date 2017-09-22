/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;


import beans.LoginBean;
import db.HibernateUtil;
import jasper.JasperCall;

import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import org.apache.commons.io.FileUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
//import org.hibernate.engine.SessionFactoryImplementor;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Charis Koutsouridis
 */
public class FilesDownloading implements Serializable {

    private static final String zipFolderPath = "/../../resources/localjaspers/";
    private static final String server1Ip = "\\\\192.9.200.250\\";
    private static final String server2Ip = "\\\\192.9.200.251\\";
    private static final String webappTarget = "localjaspers\\";
    @ManagedProperty("#{LoginBean}")
    private static LoginBean loginBean;

    public static Map<String, String> getFolderNames() {
        Map results = new HashMap<String, String>();
        Date now = new Date();//every folder is unique because has name current timesatmp
        String query = "from (select  rawtohex(sys_guid()) as test from dual )e";
        List<Object[]> testList = (List<Object[]>) (List<?>) db.dbTransactions.getObjectsBySqlQueryNew2(query);
//      HashMap<String,Object> propMap = (HashMap<String,Object>) testList.get(0);
        String uniqueId = String.valueOf(testList.get(0));
        String folderName = uniqueId;
        String folderNamezip = zipFolderPath + folderName + "/" + folderName + ".zip";//the folder of the files is in a webapp into application server 
        String pdfPath = server1Ip + webappTarget + folderName + "\\";
        String pdfPath2 = server2Ip + webappTarget + folderName + "\\";
        results.put("folderNamezip", folderNamezip);
        results.put("pdfPath", pdfPath);
        results.put("pdfPath2", pdfPath2);
        results.put("folderName", folderName);
        return results;
    }

    public static String createPdf(Long monaKwd, Map<String, String> paths, String username, String strProsKwd) throws IOException {
//
//        String folderNamezip = paths.get("folderNamezip");
//
//        File file1 = new File(paths.get("pdfPath"));//creation of the folders
//        File file2 = new File(paths.get("pdfPath2"));
//
//        String monaLek = getmonaLek(monaKwd);
//        printMoriaMonadas(String.valueOf(monaKwd), username, paths);
//        ZipUtils appZip = new ZipUtils(server1Ip + webappTarget + paths.get("folderName"), server1Ip + webappTarget, paths.get("folderName"));
//        appZip.generateFileList(new File(paths.get("pdfPath")));
//        String zipFilePath1 = appZip.zipIt();
//        ZipUtils appZip2 = new ZipUtils(server2Ip + webappTarget + paths.get("folderName"), server2Ip + webappTarget, paths.get("folderName"));
//        appZip2.generateFileList(new File(paths.get("pdfPath2")));
//        String zipFilePath2 = appZip2.zipIt();
//
//        audit("method:jasperPrintMoriaMonadas", monaLek, strProsKwd);
//        return folderNamezip;
return null;
    }

    public static String createXlsMonimwnEfedrwn(String name, Map<String, String> paths, String username, String strProsKwd, String category) throws IOException {

        String folderNamezip = paths.get("folderNamezip");

        File file1 = new File(paths.get("pdfPath"));//creation of the folders
        File file2 = new File(paths.get("pdfPath2"));
        file1.mkdir();
        file2.mkdir();

        if (category.equals("monimos")) {
            printXlsMonimwn(name, paths);
        } else {
            printXlsEfedrwn(name, paths);
        }

        ZipUtils appZip = new ZipUtils(server1Ip + webappTarget + paths.get("folderName"), server1Ip + webappTarget, paths.get("folderName"));
        appZip.generateFileList(new File(paths.get("pdfPath")));
        String zipFilePath1 = appZip.zipIt();
        ZipUtils appZip2 = new ZipUtils(server2Ip + webappTarget + paths.get("folderName"), server2Ip + webappTarget, paths.get("folderName"));
        appZip2.generateFileList(new File(paths.get("pdfPath2")));
        String zipFilePath2 = appZip2.zipIt();

       // audit("method:jasperXSLDedomenaMonimwn", "jasper:metaforaDedomenwnMonimwn.jasper/metaforaDedomenwnEfedron.jasper", loginBean.getAfm().toString());
        return folderNamezip;

    }
    
    
    public static Connection callConnection () {


//        Connection connection = null;
//        Session hibeSession = HibernateUtil.getSessionFactory().openSession();
//        Transaction hibeTransaction = null;
////        SessionFactoryImplementor sessionFactoryImplementor = (SessionFactoryImplementor) hibeSession.getSessionFactory();
//        try {
//            connection = sessionFactoryImplementor.getConnectionProvider().getConnection();
//        } catch (SQLException ex) {
//            Logger.getLogger(FilesDownloading.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return connection;
        return null;

    }
    
    

    private static void audit(String message, String monaLek, String strProsKwd) {
    }

    /**
     * @return the zipFolderPath
     */
    public static String getZipFolderPath() {
        return zipFolderPath;
    }

    /**
     * @return the server1Ip
     */
    public static String getServer1Ip() {
        return server1Ip;
    }

    /**
     * @return the server2Ip
     */
    public static String getServer2Ip() {
        return server2Ip;
    }

    /**
     * @return the webappTarget
     */
    public static String getWebappTarget() {
        return webappTarget;
    }





    private static void printXlsMonimwn(String name, Map<String, String> paths) {
        String view = "";
        String fileName = "";
        if (name.equals("model.monimos.Monimos")) {
            view = "MONIMOS";
            fileName = "MONIMOS.xls";
        } //        else if(name.equals("model.monimos.VMonimosKypros31")){
        //        fileName = "V_MONIMOS_KYPROS31.xls";
        //         view = "V_MONIMOS_KYPROS_3_1";
        //        }
        else {
            Class<?> cls = null;
            try {
                cls = Class.forName(name);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FilesDownloading.class.getName()).log(Level.SEVERE, null, ex);
            }
            view = ReturnTableName.getTableName(cls);
            fileName = name.replaceFirst("model.monimos.VMonimos", "").toUpperCase() + "_MONIMOS.xls";
        }

        try {
            //Πέρασμα παραμέτρων στο jasper
            HashMap jasperParameter = new HashMap();
            jasperParameter.put("viewName_ext", view);

            JasperCall.getInstance().writeXlsToHTTPResponse(jasperParameter, "/dynameisMonadwn/jasper_dynameisMonadwn/metaforaDedomenwnMonimwn.jasper", paths.get("pdfPath") + fileName,null,null);
//            JasperCall.getInstance().writeXlsx(jasperParameter, "/dynameisMonadwn/jasper_dynameisMonadwn/metaforaDedomenwnMonimwn.jasper", paths.get("pdfPath2")+fileName,null);
            File file1 = new File(paths.get("pdfPath") + fileName);
            File file2 = new File(paths.get("pdfPath2") + fileName);
            FileUtils.copyFile(file1, file2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //auditing

    }

    private static void printXlsEfedrwn(String name, Map<String, String> paths) {
        String view = "";

        String fileName = "";
        if (name.equals("model.efedros.Stratevmenos")) {
            view = "STRATEVMENOS";
            fileName = "STRATEVMENOS.xls";
        } else {

            Class<?> cls = null;
            try {
                cls = Class.forName(name);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FilesDownloading.class.getName()).log(Level.SEVERE, null, ex);
            }
            view = ReturnTableName.getTableName(cls);
            fileName = name.replaceFirst("model.efedros.VStratevmenos", "").toUpperCase() + "_STRATEVMENOS.xls";
        }

        try {
            //Πέρασμα παραμέτρων στο jasper
            HashMap jasperParameter = new HashMap();
            jasperParameter.put("viewName_ext", view);
            JRTableModelDataSource data = callProcedure(view);
            jasperParameter.put("DataSource", data);
            JasperCall.getInstance().writeXlsToHTTPResponse(jasperParameter, "/dynameisMonadwn/jasper_dynameisMonadwn/metaforaDedomenwnEfedrwn.jasper", paths.get("pdfPath") + fileName, null,null);
//            JasperCall.getInstance().writeXlsx(jasperParameter, "/dynameisMonadwn/jasper_dynameisMonadwn/metaforaDedomenwnEfedrwn.jasper", paths.get("pdfPath2")+fileName,null);

            File file1 = new File(paths.get("pdfPath") + fileName);
            File file2 = new File(paths.get("pdfPath2") + fileName);
            FileUtils.copyFile(file1, file2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //auditing
    }

    private static JRTableModelDataSource callProcedure(String view) throws SQLException {
//        String sql = "CALL MKDB006.CREATE_METAF_EFED_FILE_TABLE( \'" + view + "\' ) ";
//        Connection connection = null;
//        JRTableModelDataSource data = null;
//        Session hibeSession = HibernateUtil.getSessionFactory().openSession();
//        Transaction hibeTransaction = null;
//        SessionFactoryImplementor sessionFactoryImplementor = (SessionFactoryImplementor) hibeSession.getSessionFactory();
//        try {
//            connection = sessionFactoryImplementor.getConnectionProvider().getConnection();
//        } catch (SQLException ex) {
//            Logger.getLogger(FilesDownloading.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        // Call CREATE_METAF_EFED_FILE_TABLE(VIEW) procedure
//        try {
//            hibeTransaction = hibeSession.beginTransaction();
//            Query query = hibeSession.createSQLQuery(sql);
//            query.executeUpdate();
//            hibeSession.getTransaction().commit();
//            hibeSession.flush();
//            data = createDataSourceEfedrwn(hibeTransaction, hibeSession);
//        } catch (Exception ex) {
//
//            try {
//                hibeTransaction.rollback();
//            } catch (Exception er) {
//                er.printStackTrace();
//                //log.error("dbTransactions.getObjectsByProperty("+classname+","+propertyname+","+propertyvalue+"): An error occurred attempting to roll back the transaction.", re);
//            }
//        } finally {
//            if (hibeSession != null) {
//                hibeSession.close();
//                connection.close();
//            }
//        }
//
//        return data;
        return null;
    }

    private static JRTableModelDataSource createDataSourceEfedrwn(Transaction hibeTransaction, Session hibeSession) {
        hibeTransaction = hibeSession.beginTransaction();
        String sql = "select *\n"
                + "from METAFORA_EFEDRON_XLS";
//            "from METAFORA_EFEDRON_XLS met, YPAG_MONA_0 yp\n" +
//            "WHERE YP.MONA_LEK=met.MONA_LEK";
        Query query = hibeSession.createSQLQuery(sql);
        List list = query.list();
        List<String> columnNames = new ArrayList<String>();
        columnNames.add("STRK_PROS_KWD");
        columnNames.add("BATH_SYN");
        columnNames.add("OS_LEK");
        columnNames.add("STRA_EPWNYMO");
        columnNames.add("STRA_ONOMA");
        columnNames.add("YPHR_ESSO");
        columnNames.add("YPHR_ESSO_TMHMA");
        columnNames.add("YPAG_LEK");
        columnNames.add("EIDI_LEK");
        columnNames.add("MONA_LEK");
        columnNames.add("TOPO_LEK");
        columnNames.add("NOMO_LEK");
        columnNames.add("EPAG_LEK");
        columnNames.add("SXOLH_1");
        columnNames.add("SXOLH_2");
        columnNames.add("SXOLH_3");
        columnNames.add("AGGLIKA");
        columnNames.add("GALLIKA");
        columnNames.add("GLOSSA_1");
        columnNames.add("GLOSSA_2");
        columnNames.add("GLOSSA_3");
        columnNames.add("IKAN_EFED_SYN");

        String[][] dedomenaEfedrwn = new String[list.size()][columnNames.size()];
        for (int i = 0; i < list.size(); i++) {
            Object[] o = (Object[]) list.get(i);
            for (int j = 0; j < columnNames.size(); j++) {
                dedomenaEfedrwn[i][j] = (String) o[j];
            }
        }

        DefaultTableModel metTableModel = new DefaultTableModel(dedomenaEfedrwn, columnNames.toArray());
        JRTableModelDataSource metDataSource = new JRTableModelDataSource(metTableModel);
        return metDataSource;
    }

}
