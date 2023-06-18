package ESGSimplify;

import java.io.FileNotFoundException;
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
import tr.edu.iyte.esg.cases.CaseStudyFilePathCreationUtilities;
import tr.edu.iyte.esg.conversion.mxe.MXEFiletoESGConverter;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.testgeneration.TestSuiteGenerator;
import tr.edu.iyte.esg.testgeneration.faulty.CompleteESG;
import tr.edu.iyte.esg.testgeneration.faulty.FaultyEventSequenceGenerator;

public class SimplifyOperations {
	
	static private NodeList SPLNodes;
	static private Element eSPLElement;
	
	static private List<String> selectedFeatureList=new ArrayList<>();
	static private List<String> getSelectedFeatureList=null;
	
	public static List<String> getSelectedFeatureSet(String filePath)
	{
		System.out.println("*******GetSelected Feature*********");
		SPLNodes = SPL.SPLTree(filePath, "feature") ;
		 System.out.println("SPLNodes Length : " + SPLNodes.getLength());
		for (int SPLfeatureCount=0; SPLfeatureCount<SPLNodes.getLength();SPLfeatureCount++)
        {
        	
        	Node nSPLNode = SPLNodes.item(SPLfeatureCount);
            
        	Driver.Driver.LogInstance.add("\nCurrent SPL Element :" + nSPLNode.getNodeName());
        	Driver.Driver.LogInstance.add("SPL element cycle : "+SPLfeatureCount);            
        	//System.out.println(nSPLNode.getNodeType());
           // System.out.println(Node.ELEMENT_NODE);
            if (nSPLNode.getNodeType() == Node.ELEMENT_NODE) {
            	
            	Driver.Driver.LogInstance.add("elementNode if statement");
            	eSPLElement = (Element) nSPLNode;
            	Driver.Driver.LogInstance.add("Attribute : "+ eSPLElement.getAttribute("name"));                     
            }
            Driver.Driver.LogInstance.add("end SPLNode if");           
            Driver.Driver.LogInstance.add("Attribute : "+ eSPLElement.getAttribute("manual")); 
            if((eSPLElement.getAttribute("manual")).toString().equals("selected"))
				{
            		String FeatureName=(eSPLElement.getAttribute("name")).toString();
            		Driver.Driver.LogInstance.add("FeatureName"+FeatureName);
            		try {
            			selectedFeatureList.add(FeatureName);
					} catch (Exception e) {
						// TODO: handle exception
					}
                	
            		Driver.Driver.LogInstance.add("Attribute SPL : "+ eSPLElement.getAttribute("name"));   
                }
            }
		System.out.println("List : "+selectedFeatureList+"\nSelected Feture Size : "+selectedFeatureList.size()+"\n\n");
		return selectedFeatureList;
	}
	
	public static NodeList UpdateFeatureMapXML(List<String> FeatureList, String featurexmlPath) throws FileNotFoundException, IOException, ParseException
	{
		
		//ParseFeatureXML(featurexmlPath, "featureEdgeSet");
		System.out.println("*******Update Feature xml*********");
		SPLNodes = SPL.SPLTree(featurexmlPath, "featureEdgeSet") ;
		System.out.println("Feature Count from Map : "+SPLNodes.getLength()+"\n");
		Driver.Driver.LogInstance.add("\nCurrent SPL Element :" + SPLNodes.item(0));
		int updatedCount=0;
		for (int i=0; i<FeatureList.size();i++) {
			
			Driver.Driver.LogInstance.add("Feature list size : "+FeatureList.size());
			for (int SPLfeatureCount=0; SPLfeatureCount<SPLNodes.getLength();SPLfeatureCount++)
	        {
	        	
	        	Node nSPLNode = SPLNodes.item(SPLfeatureCount);
	            //System.out.println(Driver.Driver.LogInstance.add("SPL element cycle : "+SPLfeatureCount);
	                  
	            if (nSPLNode.getNodeType() == Node.ELEMENT_NODE) {
	            	eSPLElement = (Element) nSPLNode;            	
	            	Driver.Driver.LogInstance.add("Attribute : "+ eSPLElement.getAttribute("relatedFeature"));   
	            }
	            if(FeatureList.get(i).equals(eSPLElement.getAttribute("relatedFeature")))
	            {
	            	Driver.Driver.LogInstance.add("Attribute : "+ eSPLElement.getAttribute("relatedFeature")+"is equal to"+FeatureList.get(i)); 
	            	eSPLElement.setAttribute("isSelected", "true");
	            	Driver.Driver.LogInstance.add("isSelected : "+eSPLElement.getAttribute("isSelected"));
	            	updatedCount++;
	            }
	            else{
	            	Driver.Driver.LogInstance.add("Nothing Change"+"Feature name : "+FeatureList.get(i)+" "+eSPLElement.getAttribute("relatedFeature")+" isSelected : "+eSPLElement.getAttribute("isSelected")); 
	            }
	        }
	     }
		System.out.println("Updated Feature Count : "+updatedCount+"\n\n");
		return SPLNodes;
	}
	
	public static void SimplifyMXE(String MXEfilePath, String ESG_fileName,String SPLFilePath, String FeatureMapXMLFilePath) throws FileNotFoundException, IOException, ParseException 
	{
		System.out.println("*******Update ESG*********");
		ESG_fileName="WPS_ESG";
		MXEfilePath="datas//APOperations//MXEFiles//";
		FeatureMapXMLFilePath="datas//APOperations//FeatureMap//WPS_featureSet.xml";
		SPLFilePath="datas//APOperations//SPL//00001.xml";
		
		
		
		ESG ESG = null;
		try {
			
			ESG = MXEFiletoESGConverter.parseMXEFileForESGSimpleCreation(MXEfilePath + ESG_fileName + CaseStudyFilePathCreationUtilities.mxeFileExtension);
			System.out.println("ESG is created");
			CompleteESG completeESGcheck=new CompleteESG(ESG);
			System.out.println(completeESGcheck);
			
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		System.out.println("Number of vertices:" + ESG.getRealVertexList().size()+"  Number of edges:" + ESG.getRealEdgeList().size()+"\n");
		System.out.println("EdgeList : "+ESG.getRealEdgeList());
		System.out.println("\nVertexList : "+ESG.getRealVertexList()+"\n");
		
		//Removing Edge via featuremmap xml
		
		
		getSelectedFeatureList=getSelectedFeatureSet(SPLFilePath);
		
		
		//SPLNodes = SPL.SPLTree(FeatureMapXMLFilePath, "featureEdgeSet") ;
		SPLNodes =UpdateFeatureMapXML(getSelectedFeatureList, FeatureMapXMLFilePath);
		
		for (int SPLfeatureCount=0; SPLfeatureCount<SPLNodes.getLength();SPLfeatureCount++)
        {
        	
        	Node nSPLNode = SPLNodes.item(SPLfeatureCount);
        	Driver.Driver.LogInstance.add("SPL element cycle : "+SPLfeatureCount);
                  
            if (nSPLNode.getNodeType() == Node.ELEMENT_NODE) {
            	eSPLElement = (Element) nSPLNode;            	
            	Driver.Driver.LogInstance.add("Attribute : "+ eSPLElement.getAttribute("relatedFeature"));   
            }
            if(eSPLElement.getAttribute("isSelected").equals("false"))
            {
            	Driver.Driver.LogInstance.add("Attribute : "+ eSPLElement.getAttribute("relatedFeature")+" is not selected att: "+eSPLElement.getAttribute("isSelected")); 
            	Driver.Driver.LogInstance.add("Vertex Name : "+eSPLElement.getAttribute("vertexName"));
            	Driver.Driver.LogInstance.add("Edge Names : "+eSPLElement.getAttribute("edgeSet"));
            	
            	
            	
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
            			Driver.Driver.LogInstance.add("edge : "+updatedEdge);
            			Driver.Driver.LogInstance.add("equal control ESG: "+ESG.getRealEdgeList().get(a).toString().trim()+" Map: "+updatedEdge.trim());
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
            	Driver.Driver.LogInstance.add("Nothing Change"); 
            }
        }
		
		//ESG.removeEdge(ESG.getRealEdgeList().get(0));
		

		System.out.println("\nNumber of vertices:" + ESG.getRealVertexList().size()+"  Number of edges:" + ESG.getRealEdgeList().size()+"\n");
		
		System.out.println("\nEdgeList : "+ESG.getRealEdgeList());
		System.out.println("\nVertexList : "+ESG.getRealVertexList()+"\n");
		new TestSuiteGenerator();
		new FaultyEventSequenceGenerator(ESG);
		CompleteESG asd1=new CompleteESG(ESG);
		System.out.println(asd1);
		
	}
}
