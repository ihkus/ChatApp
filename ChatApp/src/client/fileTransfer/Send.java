package client.fileTransfer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import ui.MainFrame;
import client.Client;



public class Send extends Thread {
	DataInputStream streamIn = null;
	DataOutputStream streamOut = null;
	Socket socket;
	
	long size = 0;
	String fileName, destPath;

	public void open() throws IOException {
		System.out.println(socket);
		streamIn = new DataInputStream(new BufferedInputStream(
				socket.getInputStream()));
		streamOut = new DataOutputStream(socket.getOutputStream());
	}

	public Send(String fileName, String destPath, Client client,
String action) throws IOException {
		
		
		
		
		
		System.out.println("hello -- " + client.getIp());
		this.socket = new Socket(client.getIp(),33333);
		
		
		
		streamIn = new DataInputStream(new BufferedInputStream(
				socket.getInputStream()));
		streamOut = new DataOutputStream(socket.getOutputStream());
		File file = new File(fileName);
		if (file.isDirectory())
			size = getFolderSize(file);
		else
			size = file.length();
	
		
		
		if(action.equals("newFile"))
		{
		streamOut.writeUTF("newFile");
		SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yy h:mm");
		
		streamOut.writeUTF("::"+MainFrame.myName+"::"+size+"::"+file.getName()+"::"+file.getAbsolutePath()+"::"+formatter.format(new Date())+"::Pending");
		MainFrame.uploadNewFile(file.getAbsolutePath(), file.getName(), client.getName());
		socket.getOutputStream().close();
		socket.close();
		}
		
		else
		{
			
			this.start();
		}
		
		this.fileName = fileName;
		this.destPath = destPath;
		
		// sendfiles(fileName, destPath, streamIn, streamOut);
	}

	public Send(String fileName, String destPath, Socket socket, String status) {

			try {
				Client client=new Client(socket);
			System.out.println("fuiile"+fileName);
			this.fileName=fileName;
			streamIn = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			streamOut = new DataOutputStream(socket.getOutputStream());
			File file = new File(fileName);
			if (file.isDirectory())
				size = getFolderSize(file);
			else
				size = file.length();
			
			this.socket=socket;
					
			
			System.out.println("in constructor");
			streamOut.writeUTF(MainFrame.myName);
			streamOut.writeUTF(size+"");
			streamOut.writeUTF(fileName);
			
				
				
			
			
			this.fileName = fileName;
			this.destPath = destPath;
			
			this.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void run() {

		try{
			
			
			
			
			sendfiles(fileName, destPath, streamIn, streamOut);
			
			
			}
	
		catch(Exception e)
			{
				try {
					
					System.out.println("sent");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		

	}

	public void sendfiles(String fileName, String destPath,
			DataInputStream streamIn, DataOutputStream streamOut) {
		File file = new File(fileName);
		System.out.println("sending " + destPath);
		System.out.println(fileName);
		System.out.println(file.getAbsolutePath());
		if (file.isDirectory()) {
			SendDirectory sendDir = new SendDirectory(destPath, streamIn,
					streamOut);
			System.out.println("dir"+file.getAbsolutePath());

			File[] files = file.listFiles();

			for (int i = 0; i < files.length; i++) {
				if (destPath == null)
					sendfiles(files[i].getAbsolutePath(), files[i].getName(),
							streamIn, streamOut);
				else {
					sendfiles(files[i].getAbsolutePath(), destPath + "/"
							+ files[i].getName(), streamIn, streamOut);
				}

			}

		} else {
			SendFile sendFile = new SendFile(streamIn, streamOut, fileName,
					destPath);
			sendFile.start();
			try {
				open();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public long getFolderSize(File dir) {
		long size = 0;
		for (File file : dir.listFiles()) {
			if (file.isFile()) {

				size += file.length();
			} else
				size += getFolderSize(file);
		}
		return size;
	}

	public static String appendString(Object hello) {
		return "'" + hello.toString() + "'";
	}

}