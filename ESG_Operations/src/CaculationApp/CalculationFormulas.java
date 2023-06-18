package CaculationApp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CalculationFormulas {

	
	public static int Result_VC_CC(int VC,int CC) 
	
	{
		try {
			//System.out.println("Variability Coverage and Cyclomatic Complexity Result");
			
			Double power2VC=Math.pow(VC,2);
			Double power2CC=Math.pow(CC,2);
			//System.out.println("VC : "+VC+" power 2 VC ="+power2VC);
			//System.out.println("CC : "+CC+" power 2 CC ="+power2CC);
			Double TotalNum=power2CC+power2VC;
			
			//System.out.println("Formula result is :" + Math.sqrt(TotalNum));
			System.out.println(Math.sqrt(TotalNum));
			String str ="	"+ String.valueOf(Math.sqrt(TotalNum));
			str=str+"\n";
			try {
			    Files.write(Paths.get("filename.txt"), str.getBytes(), StandardOpenOption.APPEND);
			}catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
			
			// System.out.println("----------------------------");
           
	}
		 catch (Exception e) {
	        	System.out.println("Exception : "+e);
	        }
		return 0;  

}
}
