package editor.utils;
import java.util.List;

public class Logger {

	private static Logger logger;
	
	private Logger() {};
	
	public static Logger getInstance()
	{
		if(logger == null)
		{
			logger = new Logger();
		}
		return logger;
	}
	
	public void printArrayList(List<? extends Object> printList)
	{
		for(Object obj : printList)
		{
			System.out.println(obj.toString());
		}
	}
	
	public void printMessage(String logMessage)
	{
		System.out.println(logMessage);
	}
}
