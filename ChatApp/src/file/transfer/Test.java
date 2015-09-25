package file.transfer;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import com.test.FileStatusPanel;


public class Test extends Thread
{
   DataInputStream streamIn = null;
   DataOutputStream streamOut = null;
   Socket socket;
FileStatusPanel filestatus;
 long size=0;
 String fileName,destPath;
  public  void open()
    throws IOException
  {
	  System.out.println(socket);
    streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    streamOut = new DataOutputStream(socket.getOutputStream());
  }

  
  public Test(String fileName, String destPath,Socket socket, FileStatusPanel filestatus) throws IOException
  {
	  this.filestatus=filestatus;
	  System.out.println("hello -- "+socket);
	  this.socket=socket;
	  streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
	    streamOut = new DataOutputStream(socket.getOutputStream());
	    File file=new File(fileName);
	    if(file.isDirectory())
	    	size=getFolderSize(file);
	    else
	    size=file.length();
	    filestatus.setMax(size);
	    this.fileName=fileName;
	    this.destPath=destPath;
	  //  sendfiles(fileName, destPath, streamIn, streamOut);
  }

public void run()
{
	
	sendfiles(fileName, destPath, streamIn, streamOut);
	
}
		
  public  void sendfiles(String fileName, String destPath, DataInputStream streamIn, DataOutputStream streamOut)
  {
    File file = new File(fileName);
    System.out.println("sending " + destPath);
    if (file.isDirectory())
    {
      SendDirectory sendDir = new SendDirectory(destPath, streamIn, streamOut);

      File[] files = file.listFiles();

      for (int i = 0; i < files.length; i++)
      {
        if (destPath == null)
          sendfiles(files[i].getAbsolutePath(), files[i].getName(), streamIn, streamOut);
        else {
          sendfiles(files[i].getAbsolutePath(), destPath + "/" + files[i].getName(), streamIn, streamOut);
        }

      }

    }
    else
    {
      SendFile sendFile = new SendFile(streamIn, streamOut, fileName, destPath,filestatus);
      sendFile.start();
      try {
        open();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  public  long getFolderSize(File dir) {
	    long size = 0;
	    for (File file : dir.listFiles()) {
	        if (file.isFile()) {
	            
	            size += file.length();
	        }
	        else
	            size += getFolderSize(file);
	    }
	    return size;
	}

  public static String appendString(Object hello)
  {
    return "'" + hello.toString() + "'";
  }

  
}