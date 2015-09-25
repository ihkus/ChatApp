package client.fileTransfer;
import java.io.DataInputStream;
import java.io.DataOutputStream;


public class SendDirectory {

	public SendDirectory(String fileName, DataInputStream streamIn,
			DataOutputStream streamOut) {
		
		try{
		streamOut.writeUTF("DIR");
		streamOut.writeUTF(fileName);
		}
		catch(Exception e)
		{
			
		}
	}

}
