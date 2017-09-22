/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Meletiadis Vasilis <kurtz.pentagon@gmail.com>
 */
public class StringUtils {
    
    public static String getLatinFromGreek(String input){
        String result;
        input=input.toLowerCase();
        
        
        String[] REGEXdouble ={"αι","αυ([θκξπσςτφχψ]|\\s|$)","αυ","οι","ου","ει",
            "ευ([θκξπσςτφχψ]|\\s|$)","ευ","(^|\\s)μπ","μπ(\\s|$)","μπ","ντ","τσ",
            "τζ","γγ","γκ","ηυ([θκξπσςτφχψ]|\\s|$)","ηυ","θ","χ","ψ"    
        };
        String[] REPLACEdouble = {"ai","af$1","av","oi","ou","ei","ef$1","ev","$1b",
            "b$1","mp","nt","ts","tz","ng","gk","if$1","iy","th","ch","ps"
            };
        
        Pattern p=Pattern.compile(REGEXdouble[0]);
        Matcher m = p.matcher (input);
        result= m.replaceAll(REPLACEdouble[0]);
        
        
        for (int i=1;i<REGEXdouble.length;i++)
        {
           p=Pattern.compile(REGEXdouble[i]);
           m=p.matcher(result);
           result=m.replaceAll(REPLACEdouble[i]);
           
        }
        
        
        String REGEX ="αάβγδεέζηήιίΐϊκλμνξοόπρστυύϋΰφωώς";
        String REPLACE = "aabgdeeziiiiiiklmnxooprstyyiifoos";
        
         p=Pattern.compile(REGEX.substring(0, 1));
         m = p.matcher (result);
        result= m.replaceAll(REPLACE.substring(0, 1));
        
        
        for (int i=1;i<REGEX.length();i++)
        {
           p=Pattern.compile(REGEX.substring(i, i+1));
           m=p.matcher(result);
           result=m.replaceAll(REPLACE.substring(i, i+1));
           
        }
        
        
        
        return result;
    } 
}
