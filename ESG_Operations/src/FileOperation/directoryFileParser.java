package FileOperation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Entities.FeatureListEntities;

public class directoryFileParser {
	
	private static List<FeatureListEntities> ESGFileList;
	private static String ESGFilePath=null;
	private static String ESGFileName=null;
	
	public static List<FeatureListEntities> listFilesForFolder(final File folder) {
		
		if(ESGFileList != null){
			ESGFileList.clear();
			System.out.println("ESG File List Clear");
		}
		else{
			ESGFileList = new ArrayList<FeatureListEntities>();
			System.out.println("new ESG file list will be  created");
		}
		System.out.println("Step list size : "+ ESGFileList.size());
		System.out.println("Step list : "+ ESGFileList);
		
		int count=0;
		
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(folder+"\\"+fileEntry.getName());
	            ESGFilePath=folder.toString()+"\\";
	            ESGFileName=fileEntry.getName();
	            String replace = ESGFileName.replace(".mxe", "");  
	            FeatureListEntities tmpElement= new FeatureListEntities(replace,ESGFilePath);
	            ESGFileList.add(tmpElement);
	            System.out.println("FileName "+count+" : "+ESGFileList.get(count).Feature);               
				System.out.println("FilePAth "+count+" : "+ESGFileList.get(count).FilePath);
				String asd=FeatureFileFinder.FeatureMapFileNameGet(ESGFileList.get(count).Feature);
				System.out.println("FetaureMapFile "+count+" : "+asd);
	            count++;
	        }
	    }
	    return ESGFileList;
	}
	
	
	public static List<String> getFilesFromDir(String filePath)
	{
		return null;
	}

}
