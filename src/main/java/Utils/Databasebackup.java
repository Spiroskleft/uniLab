package Utils;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;

;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by tsotzo on 25/3/2016.
 */
public class Databasebackup implements Serializable {



    public static Boolean databaseBackup(String pathWindows, String pathLinux) throws IOException {



//        String timeStamp = new SimpleDateFormat("dd_MM_yyyy_HH_mm").format(Calendar.getInstance().getTime());
//        System.out.println(timeStamp);
        String[] cmdarray = new String[]{};
        String linuxSavePath =   pathLinux;// "/home/tsotzo/Desktop/galanis"+timeStamp+".sql";
        String windowsSavePath = pathWindows;//"G:\\Μαγαζί\\Μαγαζιού\\databaseBackup\\autoBackup\\galanis_"+timeStamp+".sql";
        String executeCmd="";
        Boolean apotelesma = false;


        if (SystemUtils.IS_OS_LINUX) {
            System.out.println("LINUX");
             executeCmd = "mysqldump -u " + "root" + " -p" + "6973533175"
                    + " " + "galanis" + " > " + linuxSavePath;
        } else if (SystemUtils.IS_OS_WINDOWS) {
             executeCmd = "C:\\xampp\\mysql\\bin\\mysqldump.exe -u " + "root" + " -p" + "6973533175"
                    + " " + "galanis" + " > " + windowsSavePath;
        }

        //  mysqldump -u root -p6973533175 galanis > /home/tsotzo/Desktop/test.sql

        if (SystemUtils.IS_OS_LINUX) {
            System.out.println("LINUX");
            cmdarray = new String[]{"/bin/sh", "-c", executeCmd};
        } else if (SystemUtils.IS_OS_WINDOWS) {
            cmdarray = new String[]{"cmd.exe", "/c", executeCmd};
        }
        Process runtimeProcess;
        try {

            runtimeProcess = Runtime.getRuntime().exec(cmdarray);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                System.out.println("Backup created successfully");
                runtimeProcess.destroy();
               apotelesma= true;
            } else {

                System.out.println("Could not create the backup");
                apotelesma = false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return apotelesma;
    }
}
