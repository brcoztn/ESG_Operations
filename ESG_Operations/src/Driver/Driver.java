package Driver;

import java.util.ArrayList;

public class Driver extends  ArrayList<String> {
	     public static ArrayList<String> TraceLogs = new ArrayList<String>();
	     public static int DebugLevel = 2;
	     public static String ConfigFile = "";
	     public static LogsDriver LogInstance = new LogsDriver();
	     public static String TraceLogsName = "";
	     public static String _RunBG = "enable";
}
