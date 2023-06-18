package CaculationApp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AppMain {
	
	//static private NodeList SPLNodes = SPL.SPLTree("C://Users//burcu.ergun//eclipse-workspace//products//00001//00001.xml", "feature") ;
	
	
public static void main(String[] args) throws IOException{
	
	File myObj = new File("filename.txt");
	FileWriter myWriter = new FileWriter("filename.txt");
			
			try {
			      
			      if (myObj.createNewFile()) {
			        //System.out.println("File created: " + myObj.getName());
			      } else {
			       // System.out.println("File already exists.");
			      }
			    } catch (IOException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			    }
			 try {
			      
			      myWriter.write("Start Calculate Value\n");
			      myWriter.close();
			      //System.out.println("Successfully wrote to the file.");
			    } catch (IOException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			    }
			int count=170421;
			String generatedString=null;
	        int a=0;
	        
	       // CalculationFormulas.Result_VC_CC(24, 6);
	        
			while(a<count)
			{
				System.out.println(a);
				String str = String.valueOf(a);
				str=str+" ";
				try {
				    Files.write(Paths.get("filename.txt"), str.getBytes(), StandardOpenOption.APPEND);
				}catch (IOException e) {
				    //exception handling left as an exercise for the reader
				}
				String myfileName=String.format("%05d", a);
				//System.out.println("generated string : C://Users//burcu.ergun//eclipse-workspace//products//"+myfileName+"//"+myfileName+".xml");
				generatedString="C://Users//burcu.ergun//eclipse-workspace//products//"+myfileName+"//"+myfileName+".xml";
				CalculatePrioritzation.VC_CC_Calculation(generatedString);
				//CalculatePrioritzation.VC_CC_Calculation(null);
				a++;
				if(a==count) {
					break;
				}
			}
			/**for (int i=33779;i<count;i++) {
				
				String myfileName=String.format("%05d", i);
				//System.out.println("generated string : C://Users//burcu.ergun//eclipse-workspace//products//"+myfileName+"//"+myfileName+".xml");
				generatedString="C://Users//burcu.ergun//eclipse-workspace//products//"+myfileName+"//"+myfileName+".xml";
				CalculatePrioritzation.VC_CC_Calculation(generatedString);
				//CalculatePrioritzation.VC_CC_Calculation(null);
			}**/
        }












}
