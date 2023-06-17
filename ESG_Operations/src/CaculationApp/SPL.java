package CaculationApp;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class SPL {
	
	private static File inputFile;
	//private static JSONObject jo;
	
	public static NodeList SPLTree(String inputFile_str, String tagName) 
	
	{
		NodeList nList = null;
		try {
       	 //System.out.println("Root element :");
       	
            inputFile = new File(inputFile_str);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            nList = doc.getElementsByTagName(tagName);
            //System.out.println("----------------------------");
           
	}
		 catch (Exception e) {
	        	//System.out.println(System.getProperty("os.name").toLowerCase());
	        }
		return nList;  

	}
}
