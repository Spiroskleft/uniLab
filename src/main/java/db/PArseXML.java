package db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class PArseXML {

    //Logger
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(PArseXML.class.getName());
    private String delimiter1 = ">";
    private String delimiter2 = "@";
    private String delimiter3 = ":";
    public BufferedReader br = null;
    public ArrayList AL = null;
    public String[] finalConnStr = null;
    public String[] DbServerNamePortLogin = null;
    public OracleDbLogin OracleDb = null;
    private String filePath;

    public PArseXML() {
    }

    public OracleDbLogin ExtractDbLoginFromXML(URL filePath) throws Exception {
        //extract the active property from the xml file
        log.info("ExtractDbLoginFromXML()");
        log.info("ExtractDbLoginFromXML()->filePath-->" + filePath);

        br = new BufferedReader(new InputStreamReader(filePath.openStream()));
        finalConnStr = ExtraxtConnTagFromXML(br);
        DbServerNamePortLogin = extractOracleDbInfo(finalConnStr);
        OracleDbLogin OracleDbobj = new OracleDbLogin(DbServerNamePortLogin[0], DbServerNamePortLogin[1], DbServerNamePortLogin[2], DbServerNamePortLogin[3], DbServerNamePortLogin[4]);

        return OracleDbobj;
    }

    private String[] ExtraxtConnTagFromXML(BufferedReader br) throws Exception {
        ArrayList rows = new ArrayList();
        ArrayList username = new ArrayList();
        ArrayList password = new ArrayList();
        DbServerNamePortLogin = new String[3];

        String currentRecord;
        int linecount = 0;
        while ((currentRecord = br.readLine()) != null) {
            linecount = linecount + 1;
            if (linecount == 7 || linecount == 8) {
                rows.add(currentRecord);
            }

            if (linecount == 9) {
                username.add(currentRecord);
            }

            if (linecount == 10) {
                password.add(currentRecord);
            }
        }

        br.close();

        //extract the Oracle Servername, database name and port
        String all = rows.toString();
        String s1 = "--";

        int index1 = all.indexOf(s1);

        String temp1 = all.substring(0, index1);
        String temp2 = all.substring(index1, all.length());
        String substrs[] = {temp1, temp2};

        int isSemicolon = 0; //to prwto substring exei komma
        for (int i = 0; i < 2; i++) {
            if (substrs[0].indexOf(",") == -1) {
                isSemicolon = 1; //to deutero substring exei komma
            }
        }

        String OracleDbProperty;
        if (isSemicolon == 0) {//an to prwto substring exei komma tote DEN einai sxolio, ara to thelw
            OracleDbProperty = substrs[0];
        } else {
            String temp = substrs[1];
            OracleDbProperty = temp.substring(temp.indexOf(","), temp.length());
        }

        //extract the username and password
        String uname = username.toString();
        String pword = password.toString();

        DbServerNamePortLogin[0] = OracleDbProperty;
        DbServerNamePortLogin[1] = uname;
        DbServerNamePortLogin[2] = pword;

        return DbServerNamePortLogin;
    }

    private String[] extractOracleDbInfo(String[] oracleProperty) {

        String[] DbServerNamePort;
        DbServerNamePortLogin = new String[5];
        String uname;
        String pword;

        int index1 = oracleProperty[0].indexOf("@");
        String temp1 = oracleProperty[0].substring(index1 + 1, oracleProperty[0].length());

        int index2 = temp1.indexOf("</");
        String temp2 = temp1.substring(0, index2);

        DbServerNamePort = temp2.split(":");

        int indexu = oracleProperty[1].indexOf(">");
        int indexp = oracleProperty[2].indexOf(">");
        String utemp = oracleProperty[1].substring(indexu, oracleProperty[1].length());
        String ptemp = oracleProperty[2].substring(indexp, oracleProperty[2].length());
        indexu = utemp.indexOf("</");
        indexp = ptemp.indexOf("</");
        uname = utemp.substring(1, indexu);
        pword = ptemp.substring(1, indexp);


        DbServerNamePortLogin[0] = DbServerNamePort[0];
        DbServerNamePortLogin[1] = DbServerNamePort[1];
        DbServerNamePortLogin[2] = DbServerNamePort[2];
        DbServerNamePortLogin[3] = uname;
        DbServerNamePortLogin[4] = pword;
        return DbServerNamePortLogin;
    }

    public void setDelimiter1(String delimiter1) {
        this.delimiter1 = delimiter1;
    }

    public String getDelimiter1() {
        return delimiter1;
    }

    public void setDelimiter2(String delimiter2) {
        this.delimiter2 = delimiter2;
    }

    public String getDelimiter2() {
        return delimiter2;
    }

    public void setDelimiter3(String delimiter3) {
        this.delimiter3 = delimiter3;
    }

    public String getDelimiter3() {
        return delimiter3;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
