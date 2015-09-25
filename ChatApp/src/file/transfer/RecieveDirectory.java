package file.transfer;
import java.io.File;


public class RecieveDirectory {

	
	public RecieveDirectory(String fileName)
	{
		File file=new File(fileName);
		System.out.println(file.getName());
		if(!file.exists())
		{
			file.mkdir();
		
		}
	}
	
	
}
