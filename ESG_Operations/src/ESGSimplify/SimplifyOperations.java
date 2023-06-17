package ESGSimplify;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import CaculationApp.SPL;
import Driver.Driver;
import tr.edu.iyte.esg.cases.CaseStudyFilePathCreationUtilities;
import tr.edu.iyte.esg.conversion.mxe.MXEFiletoESGConverter;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.testgeneration.faulty.CompleteESG;



public class SimplifyOperations {
	
	static private NodeList SPLNodes;
	static private Element eSPLElement;
	
	static private List<String> selectedFeatureList=new ArrayList<>();
	public String FeatureMapXMLFilePath="datas//APOperations//MXEFiles//";
	public static List<String> ESGLine;
	
	public static boolean ESGFileOperations(String FileName, String tempFilename)
	{
		System.out.println("File name : " + FileName);
		tempFilename=FileName;
		System.out.println("File name : " + tempFilename);


		if(tempFilename.contains(":")) {
			tempFilename=FileName.split(":")[1].trim();
			System.out.println("File name : " + tempFilename);
		}
		else 
		{
			System.out.println("File name : " + tempFilename);
		}
		System.out.println("File name : " + tempFilename);
		System.out.println("File name : " + FileName);

		parseESGFile(tempFilename);
		return false;
				}
	public static List<String> getSelectedFeatureSet(String filePath)
	{
		System.out.println("*******GetSelected Feature*********");
		SPLNodes = SPL.SPLTree(filePath, "feature") ;
		 System.out.println("SPLNodes Length : " + SPLNodes.getLength());
		for (int SPLfeatureCount=0; SPLfeatureCount<SPLNodes.getLength();SPLfeatureCount++)
        {
        	
        	Node nSPLNode = SPLNodes.item(SPLfeatureCount);
           
        	if (nSPLNode.getNodeType() == Node.ELEMENT_NODE) {
            	
        		Driver.LogInstance.add("elementNode if statement");
            	eSPLElement = (Element) nSPLNode;
            	Driver.LogInstance.add("Attribute : "+ eSPLElement.getAttribute("name"));                     
            }
        	Driver.LogInstance.add("end SPLNode if");           
        	Driver.LogInstance.add("Attribute : "+ eSPLElement.getAttribute("manual")); 
            if((eSPLElement.getAttribute("manual")).toString().equals("selected"))
				{
            		String FeatureName=(eSPLElement.getAttribute("name")).toString();
            		Driver.LogInstance.add("FeatureName"+FeatureName);
            		try {
            			selectedFeatureList.add(FeatureName);
					} catch (Exception e) {
						// TODO: handle exception
					}
                	
            		Driver.LogInstance.add("Attribute SPL : "+ eSPLElement.getAttribute("name"));   
                }
            }
		System.out.println("List : "+selectedFeatureList+"\nSelected Feture Size : "+selectedFeatureList.size()+"\n\n");
		return selectedFeatureList;
	}
	
	public static NodeList UpdateFeatureMapXML(String featurexmlPath,List<String> eSGList) throws FileNotFoundException, IOException, ParseException
	{
		List<String> FeatureList=null;
		//ParseFeatureXML(featurexmlPath, "featureEdgeSet");
		System.out.println("*******Update Feature xml*********");
		SPLNodes = SPL.SPLTree(featurexmlPath, "featureEdgeSet") ;
		System.out.println("Feature Count from Map : "+SPLNodes.getLength()+"\n");
		Driver.LogInstance.add("\nCurrent SPL Element :" + SPLNodes.item(0));
		int updatedCount=0;
		for (int i=0; i<FeatureList.size();i++) {
			
			Driver.LogInstance.add("Feature list size : "+FeatureList.size());
			for (int SPLfeatureCount=0; SPLfeatureCount<SPLNodes.getLength();SPLfeatureCount++)
	        {
	        	
	        	Node nSPLNode = SPLNodes.item(SPLfeatureCount);
	        	Driver.LogInstance.add("SPL element cycle : "+SPLfeatureCount);
	                  
	            if (nSPLNode.getNodeType() == Node.ELEMENT_NODE) {
	            	eSPLElement = (Element) nSPLNode;            	
	            	Driver.LogInstance.add("Attribute : "+ eSPLElement.getAttribute("relatedFeature"));   
	            }
	            if(FeatureList.get(i).equals(eSPLElement.getAttribute("relatedFeature")))
	            {
	            	Driver.LogInstance.add("Attribute : "+ eSPLElement.getAttribute("relatedFeature")+"is equal to"+FeatureList.get(i)); 
	            	eSPLElement.setAttribute("isSelected", "true");
	            	Driver.LogInstance.add("isSelected : "+eSPLElement.getAttribute("isSelected"));
	            	updatedCount++;
	            }
	            else{
	            	Driver.LogInstance.add("Nothing Change"+"Feature name : "+FeatureList.get(i)+" "+eSPLElement.getAttribute("relatedFeature")+" isSelected : "+eSPLElement.getAttribute("isSelected")); 
	            }
	        }
	     }
		System.out.println("Updated Feature Count : "+updatedCount+"\n\n");
		return SPLNodes;
	}
	
	public static void SimplifyMXE() 
	{
		System.out.println("*******Update ESG*********");
		String fileName="WPS_ESG";
		ESG ESG = null;
		String filePath="datas//APOperations//MXEFiles//";
		try {
			//ESG = MXEFiletoESGConverter.parseMXEFileForESGSimpleCreation(filePath);
			ESG = MXEFiletoESGConverter.parseMXEFileForESGSimpleCreation(filePath + fileName + CaseStudyFilePathCreationUtilities.mxeFileExtension);
			System.out.println("ESG is created");
			CompleteESG asd1=new CompleteESG(ESG);
			System.out.println(asd1);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Number of vertices:" + ESG.getRealVertexList().size()+"  Number of edges:" + ESG.getRealEdgeList().size()+"\n");
		
		System.out.println("EdgeList : "+ESG.getRealEdgeList());
		System.out.println("\nVertexList : "+ESG.getRealVertexList()+"\n");
		
		//Removing Edge via featuremmap xml
		
		String FeatureMapXMLFilePath="datas//APOperations//FeatureMap//WPS_featureSet.xml";
		SPLNodes = SPL.SPLTree(FeatureMapXMLFilePath, "featureEdgeSet") ;
		
		for (int SPLfeatureCount=0; SPLfeatureCount<SPLNodes.getLength();SPLfeatureCount++)
        {
        	
        	Node nSPLNode = SPLNodes.item(SPLfeatureCount);
        	Driver.LogInstance.add("SPL element cycle : "+SPLfeatureCount);
                  
            if (nSPLNode.getNodeType() == Node.ELEMENT_NODE) {
            	eSPLElement = (Element) nSPLNode;            	
            	Driver.LogInstance.add("Attribute : "+ eSPLElement.getAttribute("relatedFeature"));   
            }
            if(eSPLElement.getAttribute("isSelected").equals("false"))
            {
            	Driver.LogInstance.add("Attribute : "+ eSPLElement.getAttribute("relatedFeature")+" is not selected att: "+eSPLElement.getAttribute("isSelected")); 
            	Driver.LogInstance.add("Vertex Name : "+eSPLElement.getAttribute("vertexName"));
            	Driver.LogInstance.add("Edge Names : "+eSPLElement.getAttribute("edgeSet"));
            	
            	
            	
            	//Parse EdgeSet
            	String edges=eSPLElement.getAttribute("edgeSet").toString();
            	String[] arrEdge=edges.split(",");
            	int e=0;
            	System.out.println("edge arr : "+arrEdge[0]+"  "+arrEdge[1]+"\n");
            	for (int a=0; a<ESG.getRealEdgeList().size();a++)
                {
            		e=0;
            		while(e<arrEdge.length){
            		   // String updatedEdge=arrEdge[e].replaceAll("(", "<").replaceAll(")", ">").trim();
            			String updatedEdge=(arrEdge[e].toString());
            			
            			updatedEdge=updatedEdge.replace('(', '<');
            			updatedEdge=updatedEdge.replace(')', '>');
            			Driver.LogInstance.add("edge : "+updatedEdge);
            			Driver.LogInstance.add("equal control ESG: "+ESG.getRealEdgeList().get(a).toString().trim()+" Map: "+updatedEdge.trim());
            			if(ESG.getRealEdgeList().get(a).toString().trim().equals(updatedEdge.trim()))
                		{
                		
    	            		System.out.println("\n"+"Removed Edge : "+ESG.getRealEdgeList().get(a)+" vertexfeaturemap : "+updatedEdge);
    	            		ESG.removeEdge(ESG.getRealEdgeList().get(a));
                		}
            			e++;
            		}
            		
                }
            	
            	
            	String vertex=eSPLElement.getAttribute("vertexName").toString();
            	for (int a=0; a<ESG.getRealVertexList().size();a++)
                {
            		if(ESG.getRealVertexList().get(a).toString().trim().equals(vertex.trim()))
            		{
            		
	            		System.out.println("\n"+"Removed Vertex : "+ESG.getRealVertexList().get(a)+" vertexfeaturemap : "+vertex);
	            		ESG.removeVertex(ESG.getRealVertexList().get(a));
            		}
                }
                
            }
            else{
            	//System.out.println("Nothing Change"); 
            }
        }
		
		//ESG.removeEdge(ESG.getRealEdgeList().get(0));
		

		System.out.println("\nNumber of vertices:" + ESG.getRealVertexList().size()+"  Number of edges:" + ESG.getRealEdgeList().size()+"\n");
		
		System.out.println("\nEdgeList : "+ESG.getRealEdgeList());
		System.out.println("\nVertexList : "+ESG.getRealVertexList()+"\n");
		//new TestSuiteGenerator();
		//new FaultyEventSequenceGenerator(ESG);
		CompleteESG asd1=new CompleteESG(ESG);
		System.out.println(asd1);
				
		String path = "files/Cases/AccesPointPL/AP_SPL.xml";
		FeatureModel featureModelParser = new FeatureModel();
		FeatureModel f = featureModelParser.parseXMLFileForFeatureModelCreation(path);
		System.out.println(f);
		
	}

	public static List<String> parseESGFile(String FeatureMapXMLFilePath){
		try {
			Thread.sleep(1000);
			BufferedReader br = new BufferedReader(new FileReader(FeatureMapXMLFilePath));
			try {
				if(ESGLine != null){
					ESGLine.clear();
				}else{
					ESGLine = new ArrayList<String>();
				}
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();
				while (line != null) {
					sb.append(line);
					sb.append("\n");
					ESGLine.add(line);
					line = br.readLine();
				}
			}
			catch(FileNotFoundException e){
				Driver.LogInstance.add("ERROR : file error. Error code is : " + e.getMessage());
			}finally {
				br.close();
				System.out.println("Test Step Line arr : "+ESGLine);
				Driver.LogInstance.add("INFO : Test step parse operation done successfully.");
				System.out.println("INFO : Test step parse operation done successfully.");
			}
			return ESGLine;
		} catch (Exception e) {
			Driver.LogInstance.add("ERROR : set object error. Error code is : " + e.getMessage());
			return null;
		}

	}
	
}
