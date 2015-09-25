package client.fileTransfer;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class SendFile {
	private DataInputStream  streamIn   = null;
	   private DataOutputStream streamOut = null;
	   private String fileName,destPath;
	   
	public SendFile(DataInputStream  streamIn,DataOutputStream streamOut,String fileName,String destPath)
	{
		
		this.streamIn=streamIn;
		this.streamOut=streamOut;
		this.fileName=fileName;
				this.destPath=destPath;
		
	}
	
	
	public void start() 
	{
		  try {
		File myFile=new File(fileName);
	    streamOut.writeUTF("File");
	    streamOut.writeUTF(myFile.length()+"");
	    streamOut.writeUTF(destPath);
	    byte[] mybytearray = new byte[4194304];
	    
	    	BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
	    	int bytesRead=0;
	 			while ((bytesRead = bis.read(mybytearray))>0) {
			   streamOut.write(mybytearray,0,bytesRead);
			   
			}
		
	    System.out.println("sent");
	    bis.close();
	    //streamOut.close();
	    
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
}
