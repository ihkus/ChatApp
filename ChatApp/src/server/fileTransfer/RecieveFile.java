package server.fileTransfer;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

import ui.FilePanel;


public class RecieveFile {

	private DataInputStream  streamIn   = null;
	   private DataOutputStream streamOut = null;
	   private String fileName;
	   private FilePanel fileStatus;
	   private String prefix=null;
	public RecieveFile(DataInputStream  streamIn,DataOutputStream streamOut,String prefix, FilePanel fileStatus)
	{
		this.streamIn=streamIn;
		this.streamOut=streamOut;
		this.fileStatus=fileStatus;
		this.prefix=prefix;
	}
	
	public void start()
	{FileOutputStream fos ;
    BufferedOutputStream bos = null;
		try
	    {
			
			
		String length=streamIn.readUTF();	
		System.out.println(length);
		
		long size=Long.parseLong(length);
	    fileName=streamIn.readUTF();
	    System.out.println(fileName);
		fileName=prefix+fileName;
	    boolean done = false;

	    System.out.println("transfer start");
	   	long time=System.currentTimeMillis();
	    byte[] mybytearray = new byte[4194304];

	       fos= new FileOutputStream(fileName);
	       
	       bos= new BufferedOutputStream(fos);
	      
	       
	       int count=0;
	       System.out.println(size);
	       while(size>0)
	       {
	    	   
	    	   
	       int bytesRead = streamIn.read(mybytearray,0,(int)(Math.min(mybytearray.length,size)));
	       bos.write(mybytearray, 0, bytesRead);
	       fileStatus.addValue(bytesRead);
	       size=size-bytesRead;
	       }
	       bos.flush();
	       bos.close();
	       
	       System.out.println(System.currentTimeMillis()-time);
	       //streamOut.writeUTF("finished");
		
		
		
		
	}
		catch(Exception e)
		{
			
			e.printStackTrace();
			try{
				if(bos!=null)
			 bos.close();
		      
		       
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	
	
}
}