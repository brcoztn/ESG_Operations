package ESGSimplify;

import java.io.IOException;
import java.util.List;

public class SimplifyMain {
	
	public static List<String> ESGList;
	
	public static void main(String[] args) throws IOException{
		
		
		//Sub Product Tree xml path static private NodeList ConstraintNodes=SPL.SPLTree("datas//Constraints.xml", "feature") ;
		String SubSPLXMLFilePath="datas//APOperations//SPL//00001.xml";
		
		//Feature Map Json File PAth
		//String FeatureMapJsonFilePath="datas//APOperations//FeatureMap//ACS_featureSet.json";
		String FeatureMapJsonFilePath="datas//APOperations//MXEFiles";
		//Feature Map XML File PAth
		
		if(ESGList != null){
			try {
				System.out.println("ESG List arr : "+ESGList);
				System.out.println("ESG List arr size : "+ESGList.size());
				for (int i = 0; i < ESGList.size(); i++) {
				}
			}catch (Exception e)  {
				Driver.Driver.LogInstance.add("ERROR : Text could not getted. Exception : "+e);
				
				}
				}
		String FeatureMapXMLFilePath="datas//APOperations//MXEFiles//";
				
		List<String> selectedFeature = null;
		//SimplifyOperations.JSONParser();
		
		try {
			ESGList = SimplifyOperations.parseESGFile(FeatureMapJsonFilePath);
			selectedFeature=SimplifyOperations.getSelectedFeatureSet(SubSPLXMLFilePath);
			
		    SimplifyOperations.UpdateFeatureMapXML(FeatureMapXMLFilePath, ESGList);
		    
				//SimplifyOperations.UpdateFeatureMapJson(selectedFeature, FeatureMapJsonFilePath);
		    
			SimplifyOperations.SimplifyMXE();
		     
		    } catch (Exception e) {
		      System.out.println("An error occurred." +e);
		      e.printStackTrace();
		    }
      }


		


}
