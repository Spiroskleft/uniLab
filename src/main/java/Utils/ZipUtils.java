/*
 *Δημιουργεί zip αρχεία 
 */

package Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils
{

private List<String> fileList;
private   String outputZipFile;// "C:\\jboss-as-7.1.1.Final_8888\\standalone\\deployments\\resources.war\\localjaspers";
private   String sourceFolder;// = "C:\\jboss-as-7.1.1.Final_8888\\standalone\\deployments\\resources.war\\localjaspers"; // SourceFolder path
private String foldername;

//in the constructor is setted the path of the output zip file, the path of the folder to be zipped and the name of the folder
public ZipUtils(String outputZipFile,String outputSourceFolder,String foldername)
{
   fileList = new ArrayList<String>();
   this.outputZipFile = outputZipFile;
   this.sourceFolder = outputSourceFolder;
   this.foldername = foldername;
}
//

//main method is just for checking
public static void main(String[] args)
{
    Date now = new Date();
    String folderName = String.valueOf(now.getTime());
   ZipUtils appZip = new ZipUtils("\\\\192.9.200.250\\localjaspers\\" + "1396595694331", "\\\\192.9.200.250\\localjaspers", "1396595694331");
        appZip.generateFileList(new File("\\\\192.9.200.250\\localjaspers\\" + "1396595694331"));
    try {
        String zipFilePath = appZip.zipIt();
    } catch (IOException ex) {
        Logger.getLogger(ZipUtils.class.getName()).log(Level.SEVERE, null, ex);
    }
}
//this method get a folder  and make a zip of it in it
public String zipIt() throws IOException
{
   byte[] buffer = new byte[1024];
   String source = "";
   FileOutputStream fos = null;
   ZipOutputStream zos = null;
   FileInputStream in = null;
   String zipFilePath= outputZipFile+"\\"+foldername+".zip";
   source = "\\";//this is the root folder in the tree of zipfile.For example with / after unzip all the files will be in the root folder, with f1/f2/f3 as values all files will be in the subfolder f3
     
      
     fos = new FileOutputStream(zipFilePath);
     zos = new ZipOutputStream(fos);

     //System.out.println("Output to Zip : " + zipFile);
     

     for (String file : this.fileList)
     {
        System.out.println("File Added : " + file);
        ZipEntry ze = new ZipEntry(file);
       try {
           zos.putNextEntry(ze);//χσδφσδφ zip entries into zip outputstream
       } catch (IOException ex) {
           Logger.getLogger(ZipUtils.class.getName()).log(Level.SEVERE, null, ex);
       }
        try
        {
           in = new FileInputStream(sourceFolder + file);//copy zipoutputstream to a fileInputstream ans create the zipfile
           
           int len;
           while ((len = in.read(buffer)) > 0)
           {
              zos.write(buffer, 0, len);
           }
        }
       catch (FileNotFoundException ex) {
           Logger.getLogger(ZipUtils.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(ZipUtils.class.getName()).log(Level.SEVERE, null, ex);
       } finally
        {
           in.close();
        }
     }

     zos.closeEntry();
     zos.close();
     fos.close();
    // System.out.println("Folder successfully compressed");

  return zipFilePath;
}

public void generateFileList(File node)
{
    
  // add file only
  if (node.isFile())
  {
     fileList.add(generateZipEntry(node.toString()));

  }

  if (node.isDirectory())
  {
     String[] subNote = node.list();
     for (String filename : subNote)
     {
        generateFileList(new File(node, filename));
     }
  }
}
//this method take the file name of every file to be included in the final zip file 
private String generateZipEntry(String file)
{
   return file.substring(this.sourceFolder.length(), file.length());
}
//this method return the path of the zipfile
public String getZipFile(){
    return this.outputZipFile;
}
}    
