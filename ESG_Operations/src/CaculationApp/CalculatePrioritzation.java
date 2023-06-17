/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CaculationApp;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Burcu
 */

public class CalculatePrioritzation {
	
	static private NodeList ConstraintNodes=SPL.SPLTree("datas//Constraints.xml", "feature") ;
	static private NodeList VariabilityNodes=SPL.SPLTree("datas//VCParam.xml", "feature") ;
	//static private NodeList SPLNodes = SPL.SPLTree("C://Users//burcu.ergun//eclipse-workspace//products//00001//00001.xml", "feature") ;
	static private NodeList SPLNodes = null;
	static private Element eSPLElement=null;
	static private Element eConstraintElement=null;
	static private Element eVariabilityElement=null;
	static public int countCC=0;
	static public int countVC=0;
	//public static int[] ans=new int[2];
  
    public static void VC_CC_Calculation(String SPLNodes_str){
    	
    	
        try {
        	
        	
        	
        	//System.out.println(ConstraintNodes.getLength());
        	               
        	SPLNodes = SPL.SPLTree(SPLNodes_str, "feature") ;
        	//System.out.println("Product directory : "+SPLNodes_str);
        	countCC=0;
        	countVC=0;
                for (int SPLfeatureCount=0; SPLfeatureCount<SPLNodes.getLength();SPLfeatureCount++)
                {
                	
                	Node nSPLNode = SPLNodes.item(SPLfeatureCount);
                    
                   // System.out.println("\nCurrent SPL Element :" + nSPLNode.getNodeName());
                    //System.out.println("SPL element cycle : "+SPLfeatureCount);
                    //System.out.println(nSPLNode.getNodeType());
                    //System.out.println(Node.ELEMENT_NODE);
                    if (nSPLNode.getNodeType() == Node.ELEMENT_NODE) {
                    	
                    	//System.out.println("elementNode if statement");
                    	eSPLElement = (Element) nSPLNode;
                    	//System.out.println("Attribute : "+ eSPLElement.getAttribute("name"));                     
                    }
                    //System.out.println("end SPLNode if");
                    for (int constraintCount = 0; constraintCount < ConstraintNodes.getLength(); constraintCount++) {
                        Node nNode = ConstraintNodes.item(constraintCount);
                        
                        //System.out.println("\nCurrent Constraint Element :" + nNode.getNodeName());
                        //System.out.println("Constraint cycle : "+constraintCount);
                        //System.out.println(nNode.getNodeType());
                        //System.out.println(Node.ELEMENT_NODE);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        	
                        	//System.out.println("elementNode if statement");
                        	eConstraintElement = (Element) nNode;
                        	//System.out.println("Attribute : "+ eConstraintElement.getAttribute("name"));                     
                        }
                        //System.out.println("end Constraint if");
                        //System.out.println(countCC);
                       	
						if((eConstraintElement.getAttribute("name")).toString().equals((eSPLElement.getAttribute("name")).toString())&&(eSPLElement.getAttribute("manual")).toString().equals("selected"))
						{
	                    	countCC++;
	                    	//System.out.println("Attribute SPL : "+ eSPLElement.getAttribute("name")+" and Attribute Constraint : "+eConstraintElement.getAttribute("name")+" Cyclomatic Complexity Count : "+countCC);   
	                    }
                    }
                    for(int variabilityCount = 0; variabilityCount < VariabilityNodes.getLength(); variabilityCount++) {
                    	Node nNode = VariabilityNodes.item(variabilityCount);
                        
                        //System.out.println("\nCurrent Variability Element :" + nNode.getNodeName());
                        //System.out.println("Variability cycle : "+variabilityCount);
                        //System.out.println(nNode.getNodeType());
                        //System.out.println(Node.ELEMENT_NODE);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        	
                        	//System.out.println("elementNode if statement");
                        	eVariabilityElement = (Element) nNode;
                        	//System.out.println("Attribute : "+ eVariabilityElement.getAttribute("name"));                     
                        }
                        //System.out.println("end Variability if");
                        //System.out.println(countVC);
                        if((eVariabilityElement.getAttribute("name")).toString().equals((eSPLElement.getAttribute("name")).toString())&&(eSPLElement.getAttribute("manual")).toString().equals("selected"))
                        {
                        	countVC++;
	                    	//System.out.println("Attribute SPL : "+ eSPLElement.getAttribute("name")+" and Attribute Variability : "+eVariabilityElement.getAttribute("name")+" Cyclomatic Complexity Count : "+countVC);   
	                    
                        }
                    }
                    
             }
             //System.out.println("CC Count is the "+ countCC);
             //System.out.println("VC Count is the "+ countVC);
             //ans[0] = countVC;
             //ans[1] = countCC;
             CalculationFormulas.Result_VC_CC(countVC,countCC);
             String str1 = String.valueOf(countVC);
             String str2 = String.valueOf(countCC);
					str1=str1+"	";
					str2=str2+"	";
					String str3=str1+str2+"	";
				try {
				    Files.write(Paths.get("filename.txt"), str3.getBytes(), StandardOpenOption.APPEND);
				}catch (IOException e) {
				    //exception handling left as an exercise for the reader
				}
             
        } catch (Exception e) {
        	System.out.println(System.getProperty("os.name").toLowerCase());
        }
		//return ans;
        
    }
   
}
