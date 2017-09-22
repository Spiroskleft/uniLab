
package tester;

import java.sql.SQLException;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.swing.*;

import model.dynameis.SynoloSxhmatismoyBathmoi;
import org.junit.Test;
import org.junit.runner.RunWith;


public class Tester {

    public static void main(String[] args) throws SQLException {


        String version = FacesContext.class.getPackage().getImplementationVersion();
        System.out.println("--------------------->" + version);
        List<SynoloSxhmatismoyBathmoi> sunoloSxhmatismou = (List<SynoloSxhmatismoyBathmoi>) (List<?>) db.dbTransactions.getAllObjects(SynoloSxhmatismoyBathmoi.class.getCanonicalName());

        System.out.println("Size of SunoloSxhmatismou: " + sunoloSxhmatismou.size());
//     if (esso.substring(5,6).equals('1') ) {
//            esso.setCurrentEssoLekt(String.valueOf(esso.getId().getCurrentEssoYear()) + " " + "Α");
//        
////        String viewName="model.monimos.VMonimos10tax";     
////        
////        
////      MonimosMaster    monimos = ((List<MonimosMaster>) (List<?>) db.dbTransactions.getObjectsByProperties(loginBean.viewName(), monimosProperties)).get(0);
////        
//       
//        
//        
////        
//       String query="from ESEP_LOOKUPS.KALLIKRATIS_ELSTAT e order by e.dhmoi_lek";
//        
//        List<model.lookups.Kallikratis> data =(List<model.lookups.Kallikratis>) (List<?>) db.dbTransactions.getObjectsBySqlQueryDistinct(model.lookups.Kallikratis.class.getCanonicalName(),query);
//        System.out.println("---->" + data.size());
//        
//        
//        int count=0;
//       for(model.lookups.Kallikratis k: data){
//       if(k!=null){
//           System.out.println("---->" + k.toString()+ "mun--"+count++);
//       }
//       }
//        


        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////!!!!!!!!ΠΡΟΣΟΧΗ!!!!!!!////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * Δημιουργεί τα UUID για όσους εχουν βιβλιάριο και ΑΜΚΑ μαζικά
         */
//        String query = "from ATOMIKA_MONIMOU e "
//                + "where e.ATOM_MONI_AMKA is not null "
//                + "and e.ATOM_MONI_AR_BIBLIARIOY is not null "
//                + "and e.GUID is null";
//
//        List<AtomikaMonimou> data = (List<AtomikaMonimou>) (List<?>) db.dbTransactions.getObjectsBySqlQuery(AtomikaMonimou.class.getCanonicalName(), query, null, null, null);
//
//        for (AtomikaMonimou at : data) {
//
//            at.setGuid(HashingUtil.MD5(at.getAtomMoniAmka() + at.getAtomMoniArBibliarioy()).toUpperCase());
//            db.dbTransactions.updateObject(at);
//        }
//
//        System.out.println("---->" + data.size());
//        
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////


//       String query = "FROM ESEP_LOOKUPS.YPAG_MONA_4 e WHERE e.MONA_KWD IN ( "
//                + "SELECT DISTINCT YP.YPHR_MONADA FROM ESEP_MONIMOS.YPHRESIAKA_MONIMOU YP, "
//                + "ESEP_MONIMOS.MONIMOS MO WHERE MO.STRK_PROS_KWD = YP.STRK_PROS_KWD"
//                + " AND MO.KATH_MONI_KWD = 1 )";
//                
//       List data = (List<YpagMona4>) (List<?>) db.dbTransactions.getObjectsBySqlQuery(YpagMona4.class.getCanonicalName(), query, null, null, null);
//
//        System.out.println("size: " + data.size());


        //List aithshTopwnProtimhshsList=new ArrayList<AithshToposProtimhshs>();
//        List<AithshToposProtimhshs> aithshTopwnProtimhshsList=(List<AithshToposProtimhshs>) (List<?>) db.dbTransactions.getObjectsByTwoKeyPropertySorted(AithshToposProtimhshs.class.getCanonicalName(), "strProsKwd","00005441800", "aithshEtos", 2012, "aithshSeiraProtimhshs", 0);
//    
//        
//        
//        System.out.println("size: " + aithshTopwnProtimhshsList.size());
//        System.out.println("size: " + aithshTopwnProtimhshsList.get(0).getId().getStrProsKwd());
//        


    }
}
