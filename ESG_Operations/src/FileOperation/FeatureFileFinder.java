package FileOperation;

public class FeatureFileFinder {
	
	static String FeatureMapFile;
	
public static String FeatureMapFileNameGet(String ESGName) {
		
		FeatureMapFile=null;
		String replace = ESGName.replace("ESG", "featureSet.xml");
		FeatureMapFile=replace;	
	    
	    return FeatureMapFile;
	}

}
