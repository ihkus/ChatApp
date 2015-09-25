package file.transfer;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Recieve {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub

		Socket socket = new Socket("192.168.0.119", 8888);
		

		System.out.println(socket);
		
		 DataInputStream  streamIn   = null;
		    DataOutputStream streamOut = null;
		streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		   streamOut = new DataOutputStream(socket.getOutputStream());
		String message="";
		while(true)
		{
			
			try{
			message=streamIn.readUTF();
			}
			catch(Exception e)
			{
				
			}
			if(message.equals("DIR"))
			{
				
				String fileName=streamIn.readUTF();
				System.out.println("dir :" +fileName);
				RecieveDirectory dir=new RecieveDirectory(fileName);
			}
			else
			{
				
				RecieveFile file=new RecieveFile(streamIn, streamOut);
				file.start();
			}
			
		}
		
	}

}
