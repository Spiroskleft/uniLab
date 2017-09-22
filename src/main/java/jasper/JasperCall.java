///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package jasper;
//
//import beans.dyg.monimos.BasikaMonimosBean;
//import db.OracleDbLogin;
//import db.PArseXML;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.io.UnsupportedEncodingException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.util.Map;
//import java.util.logging.Level;
//import javax.faces.bean.ManagedProperty;
//import javax.faces.context.FacesContext;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import model.monimos.Monimos;
//import model.monimos.MonimosMaster;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.JasperRunManager;
//import net.sf.jasperreports.engine.export.JExcelApiExporter;
//import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
//import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
//import net.sf.jasperreports.engine.export.JRPdfExporter;
//import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
//import net.sf.jasperreports.engine.util.JRLoader;
//import org.apache.log4j.Logger;
//
///**
// *
// * @author Charis Koutsouridis
// */
//public class JasperCall {
//
//    private static final Logger log = Logger.getLogger(JasperCall.class);
//    private static JasperCall instance;
//    private File folder;
//    // Dependency Injection
//    @ManagedProperty("#{basikaMonimosBean}")
//    private BasikaMonimosBean basikaMonimosBean;
//    
//    private JasperCall() {
//    }
//
//    public static JasperCall getInstance() {
//        if (instance == null) {
//            instance = new JasperCall();
//        }
//        return instance;
//    }
//
//    public void writeXlsToFile(Map jasperParameters, String jasperPath, String fileName) {
//
//        Connection connection = null;
//
//        try {
//            //Χειρισμός session
//            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//
//            //Αποτροπή της δυνατότητας cache του browser, για δυνατότητα φόρτωσης νέου pdf
//            response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
//            response.setHeader("Pragma", "no-cache"); //HTTP 1.0
//
//            //Αποκατάσταση σύνδεσης με την βάση
//            OracleDbLogin dbLogin = new PArseXML().ExtractDbLoginFromXML(session.getServletContext().getResource("/WEB-INF/classes/hibernate.cfg.xml"));
//            String url = "jdbc:oracle:thin:@" + dbLogin.getServerName() + ":" + dbLogin.getPortaName() + ":" + dbLogin.getDbName();
//            connection = DriverManager.getConnection(url, dbLogin.getUsername(), dbLogin.getPassword());
//
//            JasperReport jasperReport = null;
//            JasperPrint jasperPrint = null;
//            jasperReport = (JasperReport) JRLoader.loadObject(session.getServletContext().getResource(jasperPath));
//            jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameters, connection);
//
//            JExcelApiExporter xlsExporter = new JExcelApiExporter();
//
//            xlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
//            xlsExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
//            xlsExporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
//
//            response.setContentType("application/vnd.ms-excel");
//            response.setHeader("content-disposition", "attachment; filename=" + fileName + "");
//            xlsExporter.exportReport();
//
//            FacesContext.getCurrentInstance().responseComplete();
//
//        } catch (Exception e) {
//            log.error("Error creating report: " + jasperPath, e);
//        } finally {
//            closeConnection(connection);
//        }
//
//    }
//
//    public void writeDocx(Map jasperParameters, String jasperPath,
//            String filename) {
//        //Χειρισμός session
//
//        Connection connection = null;
//        try {
//            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//            FacesContext context = FacesContext.getCurrentInstance();
//            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//            //Αποτροπή της δυνατότητας cache του browser, για δυνατότητα φόρτωσης νέου pdf
//            response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
//            response.setHeader("Pragma", "no-cache"); //HTTP 1.0
//
//            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
//            response.setHeader("content-disposition", "attachment; filename=" + filename + "");
//            OutputStream servletOutputStream = response.getOutputStream();
//
//
//            InputStream reportStream = context.getExternalContext().getResourceAsStream(jasperPath);
//
//            //Αποκατάσταση σύνδεσης με την βάση
//
//            OracleDbLogin dbLogin = new PArseXML().ExtractDbLoginFromXML(session.getServletContext().getResource("/WEB-INF/classes/hibernate.cfg.xml"));
//            String url = "jdbc:oracle:thin:@" + dbLogin.getServerName() + ":" + dbLogin.getPortaName() + ":" + dbLogin.getDbName();
//            connection = DriverManager.getConnection(url, dbLogin.getUsername(), dbLogin.getPassword());
//
//            JasperReport jasperReport = null;
//            JasperPrint jasperPrint = null;
//            jasperReport = (JasperReport) JRLoader.loadObject(session.getServletContext().getResource(jasperPath));
//            jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameters, connection);
//
//            JRDocxExporter docxExporter = new JRDocxExporter();
//
//            docxExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
//            docxExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
//            docxExporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
//
//
//            docxExporter.exportReport();
//
//
//            FacesContext.getCurrentInstance().responseComplete();
//        } catch (Exception e) {
//            log.error("Error creating report: " + jasperPath, e);
//        } finally {
//            closeConnection(connection);
//        }
//
//    }
//
//    public void writePdf(Map jasperParameters, String jasperPath) {
//        //Χειρισμός session
//
//        Connection connection = null;
//        try {
//            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//            FacesContext context = FacesContext.getCurrentInstance();
//            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//            //Αποτροπή της δυνατότητας cache του browser, για δυνατότητα φόρτωσης νέου pdf
//            response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
//            response.setHeader("Pragma", "no-cache"); //HTTP 1.0
//            response.setContentType("application/pdf");
//            OutputStream servletOutputStream = response.getOutputStream();
//
//
//            InputStream reportStream = context.getExternalContext().getResourceAsStream(jasperPath);
//
//            //Αποκατάσταση σύνδεσης με την βάση
//
//            OracleDbLogin dbLogin = new PArseXML().ExtractDbLoginFromXML(session.getServletContext().getResource("/WEB-INF/classes/hibernate.cfg.xml"));
//            String url = "jdbc:oracle:thin:@" + dbLogin.getServerName() + ":" + dbLogin.getPortaName() + ":" + dbLogin.getDbName();
//            connection = DriverManager.getConnection(url, dbLogin.getUsername(), dbLogin.getPassword());
//
//            JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, jasperParameters, connection);
//
//            FacesContext.getCurrentInstance().responseComplete();
//        } catch (Exception e) {
//            log.error("Error creating report: " + jasperPath, e);
//        } finally {
//            closeConnection(connection);
//        }
//
//    }
//
//    
//    
//    public void writelocalPdfTheorish2014(Map jasperParameters, String outputPath, String JasperPath,  String Name, HttpSession Session, Integer a) {
//
//        // Αρικοποιήσεις μεταβλητών
//        String pdfPath = outputPath;
//        String jasperPath = JasperPath;
//        HttpSession session = Session;          // όταν καλείται η μέθοδος writelocalPdfTheorish2014 παίρνει σαν όρισμα και το session 
//        JasperPrint jasperPrint = null;
//        JasperReport jasperReport = null;
//        Connection con = null;
//        
//        try {
//            log.info("---->method writelocalPdfTheorish2014(Map jasperParameters, String outputPath, String JasperPath,  String Name) called");
//
//            //Δημιουργία φακέλου
//            folder = new File(pdfPath);         // δημιουργία του φακέλου για την αποθήκευση των pdf αρχείων που θα δημιουργηθούν με βάση το jasper αρχείο
//            if (!folder.exists()) {
//                folder.mkdirs();
//            }
//            
//            // Φόρτωση του jasper αρχείου
//            jasperReport = (JasperReport) JRLoader.loadObject(session.getServletContext().getResource("/dyg/jasper_dyg/thewrhshBiNoMelh_sub.jasper"));
//           
//            //Αποκατάσταση σύνδεσης με την βάση
//            OracleDbLogin dbLogin = new PArseXML().ExtractDbLoginFromXML(session.getServletContext().getResource("/WEB-INF/classes/hibernate.cfg.xml"));
//            String url = "jdbc:oracle:thin:@" + dbLogin.getServerName() + ":" + dbLogin.getPortaName() + ":" + dbLogin.getDbName();
//            con = DriverManager.getConnection(url, dbLogin.getUsername(), dbLogin.getPassword());
//            
//            // Ονοματοδοσία των pdf αρχείων με κωδικοποίηση MD-5
//            String pdfFileName =  Name + "KepyesBibliaria";
//            log.info("AMKA-------> " + Name);
//            String newpdfFileName = generateHash (pdfFileName);
//            String aykson = Integer.toString(a);               // aykson arithmos
//            pdfFileName = aykson + "_" + newpdfFileName + ".pdf";
//            log.info("pdfFileName-------> " + pdfFileName);
//            
//            // Δημιουργία pdf με exporter 
//            JRPdfExporter exporter = new JRPdfExporter();
//            jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameters, con);
//            exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
//            exporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, pdfPath + "\\" + pdfFileName);
//            exporter.exportReport();
//       
//        } catch (Exception ex) {
//            PrintWriter writer;
//            try {
//                writer = new PrintWriter (new FileWriter(pdfPath + "\\error_accounts.txt", true));
//                writer.println(Name);
//                writer.close();
//            } catch (IOException e) {
//              e.printStackTrace();
//            }
//            ex.printStackTrace();
//        }
//        finally {
//            closeConnection(con);
//        }
//    }
//    
//        
//        // Συνάρτηση για κωδικοποίηση ονομάτων (String)μ1ε MD-5.
//    private static String generateHash(String hAMKA) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
//        
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        byte[] mdata = md.digest(hAMKA.getBytes("UTF-8"));
//        
//        StringBuilder hexString = new StringBuilder();
//        
//        for (int i = 0; i < mdata.length; i++) {
//            String hex = Integer.toHexString(0xFF & mdata[i]);
//            if (hex.length()==1)
//                hexString.append('0');
//            hexString.append(hex);
//        }
//        return hexString.toString(); 
//    }
//    
//    
//    private void closeConnection(Connection connection) {
//        if (connection == null) {
//            log.info("Connection is null");
//        } else {
//            try {
//                connection.close();
//            } catch (Exception e) {
//                log.error("Error closing connection", e);
//            }
//        }
//    }
//}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jasper;

import db.HibernateUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionImplementor;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Transaction;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Charis Koutsouridis
 */
public class JasperCall {

    private static final Logger log = Logger.getLogger(JasperCall.class);
    private static final JasperCall instance = new JasperCall();

    private File folder;

    public static JasperCall getInstance() {
        return instance;
    }

    // Συνάρτηση για κωδικοποίηση ονομάτων (String)μ1ε MD-5.
    private static String generateHash(String hAMKA) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] mdata = md.digest(hAMKA.getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < mdata.length; i++) {
            String hex = Integer.toHexString(0xFF & mdata[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Παράγει ένα xls από αναφορά jasper και το γράφει σε αρχείο στο δίσκο
     *
     * @param jasperParameters οι παράμετροι του jasper
     * @param jasperPath       το μονοπάτι του jasper
     * @param fileName         το μονοπάτι του αρχείου που για το xls
     * @param extraOptions     extra options για τη παραγωγή του Xls (frozen rows/cols, autofilter range κλπ). Αν δεν
     *                         απαιτούνται extra options, περάστε null
     * @param outputFileName   το όνομα του αρχείου που θα κάνει export
     */
    public void writeXlsToFile(Map jasperParameters, String jasperPath, String fileName, WebEsepXlsExporter.XlsExtraOptions extraOptions, String outputFileName) {
        try {
            FileOutputStream fout = new FileOutputStream(fileName);
            writeXls(jasperParameters, jasperPath, fout, extraOptions, outputFileName);
            fout.close();

        } catch (Exception e) {
            log.error("Error creating report: " + jasperPath, e);
        }
    }

    /**
     * Παράγει ένα xls από αναφροά jasper και το γράφει στο http response
     *
     * @param jasperParameters οι παράμετροι του jasper
     * @param path
     * @param jasperPath       το μονοπάτι του jasper
     * @param extraOptions     extra options για τη παραγωγή του Xls (frozen rows/cols, autofilter range κλπ). Αν δεν
     */
    public void writeXlsToHTTPResponse(Map jasperParameters, String path, String jasperPath, WebEsepXlsExporter.XlsExtraOptions extraOptions, String outputFileName) {
        try {

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.reset();

            //Αποτροπή της δυνατότητας cache του browser, για δυνατότητα φόρτωσης νέου pdf
            response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
            response.setHeader("Pragma", "no-cache"); //HTTP 1.0
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + outputFileName + ".xls");

            writeXls(jasperParameters, jasperPath, response.getOutputStream(), extraOptions, outputFileName);
            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception e) {
            log.error("Error creating report: " + jasperPath, e);
        }
    }

    /**
     * Παράγει ένα αρχείο κειμένου από αναφορά jasper και το γράφει στο http response
     *
     * @param jasperParameters οι παράμετροι του jasper
     * @param jasperPath       το μονοπάτι του jasper
     * @param fileName         το όνομα του αρχείου που παράγεται
     * @param betweenPages     το κείμενο που τυπώνεται για τον διαχωρισμό των σελίδων
     * @param pageWidth        ορίζει το πλάτος της σελίδας σε characters
     * @param pageHeight       ορίζει το ύψος της σελίδας σε characters
     */
    public void writeTxtToHTTPResponse(Map jasperParameters, String jasperPath, String fileName,
                                       String betweenPages, int pageWidth, int pageHeight) {
        Connection connection = null;
//        JasperPrint jasperPrint;
//        JasperReport jasperReport;
        Transaction transaction = null;
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.reset();

            //Αποτροπή της δυνατότητας cache του browser, για δυνατότητα φόρτωσης νέου pdf
            response.setHeader("Cache-Control", "no-cache");    //HTTP 1.1
            response.setHeader("Pragma", "no-cache");           //HTTP 1.0
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "filename=" + fileName);

            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(session.getServletContext().getResource(jasperPath));


//            Connection connection = HibernateUtil.getConnection();
            SessionImplementor sessionImplementor = (SessionImplementor) HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            connection = sessionImplementor.getJdbcConnectionAccess().obtainConnection();


            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameters, connection);
            JRTextExporter exporter = new JRTextExporter();

            exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, pageWidth);
            exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, pageHeight);
            exporter.setParameter(JRTextExporterParameter.CHARACTER_ENCODING, "UTF8");
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, fileName);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
            exporter.exportReport();

            connection.close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            log.error("Error creating report: " + jasperPath, e);
        } finally {
            if (connection != null) {
                try {
                    transaction.commit();
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error when closing connection: " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * Παράγει ένα xls από αναφροά jasper και το γράφει στο outputstream
     *
     * @param jasperParameters
     * @param jasperPath
     * @param outputStream
     * @param xlsOptions
     * @throws JRException
     * @throws java.net.MalformedURLException
     * @throws java.sql.SQLException
     */
    private void writeXls(Map jasperParameters, String jasperPath, OutputStream
            outputStream, WebEsepXlsExporter.XlsExtraOptions xlsOptions, String outputFileName) throws
            JRException, MalformedURLException, SQLException {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(session.getServletContext().getResource(jasperPath));
        Transaction transaction = null;
        Connection connection = null;


//        Connection connection = HibernateUtil.getConnection();
        SessionImplementor sessionImplementor = (SessionImplementor) HibernateUtil.getSessionFactory().getCurrentSession();
        transaction = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
        connection = sessionImplementor.getJdbcConnectionAccess().obtainConnection();


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameters, connection);

        WebEsepXlsExporter xlsExporter = new WebEsepXlsExporter();
        try {
            if (xlsOptions != null) {
                if (xlsOptions.getFreezePaneRow() != null && xlsOptions.getFreezePaneCol() != null) {
                    xlsExporter.setFreezePaneOptions(xlsOptions.getFreezePaneRow(), xlsOptions.getFreezePaneCol());
                }

                if (xlsOptions.getAutoFilterRange() != null) {
                    xlsExporter.setAutoFilterRange(xlsOptions.getAutoFilterRange());
                }

                if (xlsOptions.isOnePagePerSheet()) {
                    xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
                }
            }

            xlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputStream);
            xlsExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
            xlsExporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
            xlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, outputFileName);
            xlsExporter.exportReport();
        } catch (Exception e) {
            System.out.println("Error during report creation: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    transaction.commit();
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error when closing connection: " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeDocx(Map jasperParameters, String jasperPath,
                          String filename) {
        //Χειρισμός session
        Connection connection = null;
        Transaction transaction = null;

        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            //Αποτροπή της δυνατότητας cache του browser, για δυνατότητα φόρτωσης νέου pdf
            response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
            response.setHeader("Pragma", "no-cache"); //HTTP 1.0

            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setHeader("content-disposition", "attachment; filename=" + filename + "");
            OutputStream servletOutputStream = response.getOutputStream();

            InputStream reportStream = context.getExternalContext().getResourceAsStream(jasperPath);


//            connection = HibernateUtil.getConnection();
            SessionImplementor sessionImplementor = (SessionImplementor) HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            connection = sessionImplementor.getJdbcConnectionAccess().obtainConnection();



            JasperReport jasperReport = null;
            JasperPrint jasperPrint = null;
            jasperReport = (JasperReport) JRLoader.loadObject(session.getServletContext().getResource(jasperPath));
            jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameters, connection);

            JRDocxExporter docxExporter = new JRDocxExporter();

            docxExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
            docxExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
            docxExporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);

            docxExporter.exportReport();

            FacesContext.getCurrentInstance().responseComplete();

        } catch (Exception e) {
            log.error("Error creating report: " + jasperPath, e);
        } finally {
            if (connection != null) {
                try {
                    transaction.commit();
                    connection.close();
                } catch (SQLException e) {
                    log.error("Error when closing connection", e);
                }
            }
        }
    }

    /**
     * @param baos
     * @param jasperParameters
     * @param jasperPath
     */
    public void fillOutpuStreamWithPdf(ByteArrayOutputStream baos, Map jasperParameters, String jasperPath) {
        Connection connection = null;
        Transaction transaction = null;

        try {
//            connection = HibernateUtil.getConnection();
            SessionImplementor sessionImplementor = (SessionImplementor) HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            connection = sessionImplementor.getJdbcConnectionAccess().obtainConnection();


            FacesContext context = FacesContext.getCurrentInstance();
            InputStream reportStream = context.getExternalContext().getResourceAsStream(jasperPath);

            JasperRunManager.runReportToPdfStream(reportStream, baos, jasperParameters, connection);

            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            log.error("Error creating report: " + jasperPath, e);
        } finally {
            if (connection != null) {
                try {
                    transaction.commit();
                    connection.close();
                } catch (SQLException e) {
                    log.error("Error when closing connection", e);
                }
            }
        }
    }

    /**
     * Η συνάρτηση αυτή είναι wrapper γύρω από την _produceMultiPagePdf. Η κύρια δουλειά της είναι να μετατρέψει τα
     * absolute paths σε InputStreams.
     *
     * @param jasperSourceFileName
     * @param jasperParametersList
     * @param outputStream
     * @throws java.io.FileNotFoundException
     */
    public void produceMultiPagePdf
    (List<String> jasperSourceFileName, List<Map<String, Object>> jasperParametersList, OutputStream
            outputStream) throws FileNotFoundException {
        List<InputStream> newJasperSourceISList = new ArrayList<InputStream>();
        for (String fileName : jasperSourceFileName) {
            newJasperSourceISList.add(new FileInputStream(fileName));
        }
        _produceMultiPagePdf(newJasperSourceISList, jasperParametersList, outputStream);
    }

    /**
     * Η συνάρτηση αυτή είναι wrapper γύρω από την _produceMultiPagePdf. Η κύρια δουλειά της είναι να μετατρέψει τα
     * paths των αρχείων του server σε InputStreams.
     *
     * @param jasperSourceURLName
     * @param jasperParametersList
     * @param outputStream
     * @param session
     * @throws java.io.IOException
     */
    public void produceMultiPagePdf
    (List<String> jasperSourceURLName, List<Map<String, Object>> jasperParametersList, OutputStream
            outputStream, HttpSession session) throws IOException {
        List<InputStream> newJasperSourceISList = new ArrayList<InputStream>();
        for (String fileName : jasperSourceURLName) {
            fileName = session.getServletContext().getRealPath(fileName);
            newJasperSourceISList.add(new FileInputStream(fileName));
        }
        _produceMultiPagePdf(newJasperSourceISList, jasperParametersList, outputStream);
    }

    /**
     * Η συνάρτηση αυτή δεχεται μια λίστα από input jasper αρχεία και παραμέτρους. Το παραγόμενο pdf θα γραφτεί στο output stream
     * που έχει δώσει ο χρήστης.
     *
     * @param jasperSourceISList
     * @param jasperParametersList
     * @param outputStream
     */
    private void _produceMultiPagePdf
    (List<InputStream> jasperSourceISList, List<Map<String, Object>> jasperParametersList, OutputStream
            outputStream) {
        List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
        Connection connection = null;
        JasperPrint jasperPrint;
        JasperReport jasperReport;
        Transaction transaction = null;

        try {
            // Open a new connection with the DB
//            connection = HibernateUtil.getConnection();
            SessionImplementor sessionImplementor = (SessionImplementor) HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            connection = sessionImplementor.getJdbcConnectionAccess().obtainConnection();
            for (int i = 0; i < jasperSourceISList.size(); i++) {
                InputStream is = jasperSourceISList.get(i);
                jasperReport = (JasperReport) JRLoader.loadObject(is);
                jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParametersList.get(i), connection);
                jasperPrintList.add(jasperPrint);
            }

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
            exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.exportReport();

            // FIXME: Ο παρακάτω κώδικας ίσως δε χρειάζεται
            for (InputStream is : jasperSourceISList) {
                is.close();
            }

        } catch (Exception e) {
            System.out.println("Error during report creation: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    transaction.commit();
                    connection.close();

                } catch (SQLException e) {
                    System.out.println("Error when closing connection: " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Χρησιμοποίησε την produceMultiPagePdf για να γράψεις το pdf στο response του HttpSession.
     *
     * @param jasperSourceFiles
     * @param jasperParametersList
     */
    public void writeMultiPagePdf
    (List<String> jasperSourceFiles, List<Map<String, Object>> jasperParametersList) {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            //Αποτροπή της δυνατότητας cache του browser, για δυνατότητα φόρτωσης νέου pdf
            response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
            response.setHeader("Pragma", "no-cache"); //HTTP 1.0
            response.setContentType("application/pdf");
            OutputStream servletOutputStream = response.getOutputStream();
            produceMultiPagePdf(jasperSourceFiles, jasperParametersList, servletOutputStream, session);

            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            log.error("Error creating report: " + jasperSourceFiles, e);
        }
    }

    @SuppressWarnings("unchecked")
    public void writePdf(Map jasperParameters, String jasperPath) {
        Map<String, Object> newJasperParameters = (Map<String, Object>) jasperParameters;
        writeMultiPagePdf(Arrays.asList(jasperPath), Arrays.asList(newJasperParameters));
    }

    public void producePdf(Map<String, Object> jasperParameters, String jasperPath, OutputStream
            outputStream) throws JRException, MalformedURLException, SQLException {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(session.getServletContext().getResource(jasperPath));
        Connection connection = null;
        Transaction transaction = null;

        try {

//            connection = HibernateUtil.getConnection();
            SessionImplementor sessionImplementor = (SessionImplementor) HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            connection = sessionImplementor.getJdbcConnectionAccess().obtainConnection();


            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameters, connection);

            JRPdfExporter exporter = new JRPdfExporter();
            //jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameters, connection);
            exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.exportReport();
        } catch (Exception e) {
            log.error("Error creating report: " + jasperPath, e);
        } finally {
            if (connection != null) {
                try {
                    transaction.commit();
                    connection.close();
                } catch (SQLException e) {
                    log.error("Error when closing connection", e);
                }
            }
        }
    }

    //Εϊναι για την Θεώρηση του 2015 δεν έγινε για το 2014
    public void writelocalPdfTheorish2014(Map jasperParameters, String outputPath, String JasperPath, String
            Name, HttpSession Session, Integer a) {
        //Το Name είναι το ΑΜΚΑ για το 2015
        // Αρικοποιήσεις μεταβλητών
        String pdfPath = outputPath;
        String jasperPath = JasperPath;
        HttpSession session = Session;          // όταν καλείται η μέθοδος writelocalPdfTheorish2014 παίρνει σαν όρισμα και το session
        JasperPrint jasperPrint = null;
        JasperReport jasperReport = null;
        Connection connection = null;
        Transaction transaction = null;
        try {
            log.info("---->method writelocalPdfTheorish2014(Map jasperParameters, String outputPath, String JasperPath,  String Name) called");

            //Δημιουργία φακέλου
            folder = new File(pdfPath);         // δημιουργία του φακέλου για την αποθήκευση των pdf αρχείων που θα δημιουργηθούν με βάση το jasper αρχείο
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Φόρτωση του jasper αρχείου
            jasperReport = (JasperReport) JRLoader.loadObject(session.getServletContext().getResource("/dyg/jasper_dyg/thewrhshBiNoMelh_sub2014.jasper"));

            //Αποκατάσταση σύνδεσης με την βάση
//            OracleDbLogin dbLogin = new PArseXML().ExtractDbLoginFromXML(session.getServletContext().getResource("/WEB-INF/classes/hibernate.cfg.xml"));
//            String url = "jdbc:oracle:thin:@" + dbLogin.getServerName() + ":" + dbLogin.getPortaName() + ":" + dbLogin.getDbName();
//            Connection con = DriverManager.getConnection(url, dbLogin.getUsername(), dbLogin.getPassword());
//            connection = DriverManager.getConnection(url, username, password);

            // Ονοματοδοσία των pdf αρχείων με κωδικοποίηση MD-5
//            String pdfFileName = Name + "KepyesBibliaria!@2014*GESKepyeS";
//            log.info("AFM---------->" + Name);
//            String newpdfFileName = generateHash(pdfFileName);
//            String aykson = Integer.toString(a);               // aykson arithmos
//            String pdfFileName = Name + ".pdf";              // aykson + "_" +
//            log.info("pdfFileName-------> " + pdfFileName);


            // Ονοματοδοσία των pdf αρχείων με κωδικοποίηση MD-5
            // Όπου το Name είναι το ΑΜΚΑ
            // Π Ρ Ο Σ Ο  Χ Η !!!!!
            //Είναι σύμφωνα έτσι οπως τα θέλει το τμήμα του INTERNET
//             String pdfFileName =  Name + "k3py3sbibliaria";
//            String newpdfFileName = generateHash (pdfFileName);
            String pdfFileName = Name + ".pdf";


            // Δημιουργία pdf με exporter
//            connection = HibernateUtil.getConnection();
            SessionImplementor sessionImplementor = (SessionImplementor) HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            connection = sessionImplementor.getJdbcConnectionAccess().obtainConnection();



            JRPdfExporter exporter = new JRPdfExporter();
            jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameters, connection);
            exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, pdfPath + "\\" + pdfFileName);
            exporter.exportReport();

        } catch (Exception ex) {
            if (connection != null) {
                try {
                    transaction.commit();
                    connection.close();
                } catch (SQLException e) {
                    log.error("Error when closing connection", e);
                }
            }

            PrintWriter writer;
            try {
                writer = new PrintWriter(new FileWriter(pdfPath + "\\error_accounts.txt", true));
                writer.println(Name);
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
        }
    }

    public void writePdfMoria2014(Map jasperParameters, String outputPath, String Name, URL jasper_url) {
        // Αρικοποιήσεις μεταβλητών
        String pdfPath = outputPath;

        JasperPrint jasperPrint = null;
        JasperReport jasperReport = null;
        Connection connection = null;
        Transaction transaction = null;

        try {
            log.info("---->method writelocalPdfTheorish2014(Map jasperParameters, String outputPath, String JasperPath,  String Name) called");

            //Δημιουργία φακέλου
            folder = new File(pdfPath);         // δημιουργία του φακέλου για την αποθήκευση των pdf αρχείων που θα δημιουργηθούν με βάση το jasper αρχείο
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Φόρτωση του jasper αρχείου
            jasperReport = (JasperReport) JRLoader.loadObject(jasper_url);

            //Αποκατάσταση σύνδεσης με την βάση
//
//            connection = HibernateUtil.getConnection();
            SessionImplementor sessionImplementor = (SessionImplementor) HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            connection = sessionImplementor.getJdbcConnectionAccess().obtainConnection();



            String pdfFileName = Name + ".pdf";
            log.info("--NAME-->" + Name);

            // Δημιουργία pdf με exporter
            JRPdfExporter exporter = new JRPdfExporter();
            jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameters, connection);
            exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, pdfPath + "\\" + pdfFileName);
            exporter.exportReport();



        } catch (Exception ex) {
//
            ex.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    transaction.commit();
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error when closing connection: " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Παράγει ένα txt από αναφορά jasper και το γράφει σε αρχείο στο δίσκο
     *
     * @param jasperParameters οι παράμετροι του jasper
     * @param jasperPath       το μονοπάτι του jasper
     * @param fileName         το μονοπάτι του αρχείου που για το xls
     */
    public void writeTxtToFile(Map jasperParameters, String jasperPath, String fileName, int pageWidth,
                               int pageHeight) {
        try {
            FileOutputStream fout = new FileOutputStream(fileName);
            writeTxt(jasperParameters, jasperPath, fout, pageWidth, pageHeight);
            fout.close();

        } catch (Exception e) {
            log.error("Error creating report: " + jasperPath, e);
        }
    }

    private void writeTxt(Map jasperParameters, String jasperPath, OutputStream outputStream, int pageWidth,
                          int pageHeight) throws JRException, MalformedURLException, SQLException {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(session.getServletContext().getResource(jasperPath));
        Transaction transaction = null;
        Connection connection = null;

       try{
           SessionImplementor sessionImplementor = (SessionImplementor) HibernateUtil.getSessionFactory().getCurrentSession();
           transaction = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
           connection = sessionImplementor.getJdbcConnectionAccess().obtainConnection();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameters, connection);

        JRTextExporter exporter = new JRTextExporter();

        exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, pageWidth);
        exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, pageHeight);
        exporter.setParameter(JRTextExporterParameter.CHARACTER_ENCODING, "UTF8");
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        exporter.exportReport();

        connection.close();
       } catch (Exception e) {
           System.out.println("Error during report creation: " + e.getMessage());
           e.printStackTrace();
       } finally {
           if (connection != null) {
               try {
                   transaction.commit();
                   connection.close();
               } catch (SQLException e) {
                   System.out.println("Error when closing connection: " + e.getLocalizedMessage());
                   e.printStackTrace();
               }
           }
       }
    }


    public void writeEpetiridaMoria2014(Map jasperParameters, String outputPath, String Name, HttpSession
            Session, Integer a) {
        // Αρικοποιήσεις μεταβλητών
        String pdfPath = outputPath;

        HttpSession session = Session;          // όταν καλείται η μέθοδος writelocalPdfTheorish2014 παίρνει σαν όρισμα και το session
        JasperPrint jasperPrint = null;
        JasperReport jasperReport = null;
        Connection connection = null;
        Transaction transaction = null;

        try {
            log.info("---->method writelocalPdfTheorish2014(Map jasperParameters, String outputPath, String JasperPath,  String Name) called");

            //Δημιουργία φακέλου
            folder = new File(pdfPath);         // δημιουργία του φακέλου για την αποθήκευση των pdf αρχείων που θα δημιουργηθούν με βάση το jasper αρχείο
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Φόρτωση του jasper αρχείου
            jasperReport = (JasperReport) JRLoader.loadObject(session.getServletContext().getResource("/monimos/jasper_monimos/epetiridaMorion.jasper"));

            //Αποκατάσταση σύνδεσης με την βάση
//            OracleDbLogin dbLogin = new PArseXML().ExtractDbLoginFromXML(session.getServletContext().getResource("/WEB-INF/classes/hibernate.cfg.xml"));
//            String url = "jdbc:oracle:thin:@" + dbLogin.getServerName() + ":" + dbLogin.getPortaName() + ":" + dbLogin.getDbName();
//            Connection con = DriverManager.getConnection(url, dbLogin.getUsername(), dbLogin.getPassword());
//            connection = HibernateUtil.getConnection();
            SessionImplementor sessionImplementor = (SessionImplementor) HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            connection = sessionImplementor.getJdbcConnectionAccess().obtainConnection();


            String pdfFileName = Name + ".pdf";
            log.info("--NAME-->" + Name);

            // Δημιουργία pdf με exporter
            JRPdfExporter exporter = new JRPdfExporter();
            jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameters, connection);
            exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, pdfPath + "\\" + pdfFileName);
            exporter.exportReport();

        } catch (Exception e) {
            log.error("Error creating report", e);
        } finally {
            if (connection != null) {
                try {
                    transaction.commit();
                    connection.close();
                } catch (SQLException e) {
                    log.error("Error when closing connection", e);
                }
            }
        }
    }
}
