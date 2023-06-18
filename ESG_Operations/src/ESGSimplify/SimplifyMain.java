package ESGSimplify;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import Entities.FeatureListEntities;
import FileOperation.FeatureFileFinder;
import FileOperation.directoryFileParser;

public class SimplifyMain {
	
	
	public static List<FeatureListEntities> FeatureMapFileList;
	private static List<FeatureListEntities> ESGFileList;
	String filePath; 
	String fileName;
	List<String> FeatureList; 
	String FeatureMapXMLFilePath;

	public static void main(String[] args) throws IOException{
		

		//Sub Product Tree xml path static private NodeList ConstraintNodes=SPL.SPLTree("datas//Constraints.xml", "feature") ;
		//String SubSPLXMLFilePath="datas//APOperations//SPL//00001.xml";
		
		//Feature Map File PAth
		//String FeatureMapXMLFilePath="datas//APOperations//FeatureMap//ACS_featureSet.xml";
		//Feature Map XML File Path
		//String FeatureMapXMLFilePath="datas//APOperations//FeatureMap";
		
		//ESG MXE File Path
		
		String MXEfilePath=Entities.FilePathEntities._MXEfilePath;
		String FeatureMapXMLFile=Entities.FilePathEntities._FeatureMapXMLFilePath;
		String SPLFilePath=Entities.FilePathEntities._SPLFileFilePath;
				
		final File folder = new File(MXEfilePath);
		ESGFileList=directoryFileParser.listFilesForFolder(folder);
		
        //Reduce operation for each ESG
		for (int ESGCount=0; ESGCount<ESGFileList.size();ESGCount++)
        {
			try {
				
					String FeatureMapXml=FeatureMapXMLFile+FeatureFileFinder.FeatureMapFileNameGet(ESGFileList.get(ESGCount).Feature);
					SimplifyOperations.SimplifyMXE(ESGFileList.get(ESGCount).FilePath,ESGFileList.get(ESGCount).Feature,FeatureMapXml,SPLFilePath);
				
			     
			    } catch (IOException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
		
	
      }


		


}
