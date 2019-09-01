package editor.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialization {

	private String filePath = "src/fileToWriteObjects.txt";
	private static Serialization serialzer = null;
	private FileOutputStream fileOutputStream;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	private FileInputStream fileInputStream;
	
	private Serialization() {		
	}
	
	public static Serialization getInstance()
	{
		if(serialzer== null)
		{
			serialzer = new Serialization();
		}
		return serialzer;
	}
	
	public void writeObject(Object obj)
	{
		try 
		{
			fileOutputStream = new FileOutputStream(filePath);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(obj);
			fileOutputStream.close();
			objectOutputStream.close();	
		} 
		catch (IOException e) 
		{	
			e.printStackTrace();
		}
	}
	
	public Object loadObject()
	{
		Object obj = null;
		try 
		{
			fileInputStream = new FileInputStream(filePath);
			objectInputStream = new ObjectInputStream(fileInputStream);
			obj = objectInputStream.readObject();
			fileInputStream.close();
			objectInputStream.close();
			
		}
		catch(IOException | ClassNotFoundException exc)
		{
			System.out.print(exc);
		}
		return obj;
	}
	
}
