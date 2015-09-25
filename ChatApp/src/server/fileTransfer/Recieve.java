package server.fileTransfer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.prefs.Preferences;

import ui.FilePanel;
import ui.MainFrame;

public class Recieve extends Thread {

	private Socket socket = null;

	private DataInputStream streamIn = null;
	private DataOutputStream streamOut = null;
	
private String senderName;
String prefix;
	int port;
	FilePanel fileStatus;
	Preferences pref;
	long size=0;
	public Recieve(Socket socket, FilePanel fileStatus) throws IOException {
		this.socket = socket;
		
		open();
		
		senderName=streamIn.readUTF();
		System.out.println(senderName);
		size=Long.parseLong(streamIn.readUTF());
		String fileName=streamIn.readUTF();
		this.fileStatus=fileStatus;
		fileStatus.setMax(size);
		
		pref = Preferences.userNodeForPackage(MainFrame.class);
		String location=pref.get("downloadLocation", "ChatDownloads");
		prefix=location+"/"+senderName+"/";
	
		
		
		File file=new File(prefix);
		
		
			System.out.println(file.mkdirs());
			fileStatus.setFileLocation(file.getAbsolutePath());
		
	}

	
	public void run() {
		try {
			String message = "";
			
			
			
			System.out.println("somethinfg ihaoiafie in reciebe flass");
			
			while (true) {

				message = streamIn.readUTF();

				if (message.equals("DIR")) {

					String fileName = "";

					fileName = streamIn.readUTF();

					System.out.println("dir :" + fileName);
					RecieveDirectory dir = new RecieveDirectory(prefix+fileName);
				} else {

					
					RecieveFile file = new RecieveFile(streamIn, streamOut,prefix,fileStatus);
					file.start();
				}
				
			}
		}
		catch(EOFException e)
		{
			
			
		}
		catch (IOException e) {
			
			
			fileStatus.errorOccured();
		}

	}

	public void open() throws IOException {
		streamIn = new DataInputStream(new BufferedInputStream(
				socket.getInputStream()));
		streamOut = new DataOutputStream(socket.getOutputStream());
	}

	public void close() throws IOException {
		if (socket != null)
			socket.close();
		if (streamIn != null)
			streamIn.close();
	}

}
