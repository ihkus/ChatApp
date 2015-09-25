package com.test;



import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import file.transfer.RecieveDirectory;

public class FileClient
{  private Socket          socket   = null;
   private ServerSocket    server   = null;
   private DataInputStream streamIn =  null;
   private DataOutputStream streamOut=null;
   public FileClient(int port) throws IOException
   {   System.out.println("Binding to port " + port + ", please wait  ..."+" "+InetAddress.getLocalHost());
   setServer(new ServerSocket(port,3,InetAddress.getLocalHost()));  
   System.out.println("Server started: " + getServer());
   ListenClient listenClient=new ListenClient(server);
   listenClient.start();

   }
   public void recieveFile(String addr,String str) throws IOException
   {
	   
	   
	   Socket socket=Clients.getSocket(addr);
	   streamOut=new DataOutputStream(socket.getOutputStream()); 
	   streamOut.writeUTF(str);
	   
	   streamOut.flush();
	   
   }
   public void open() throws IOException
   {  streamIn = new DataInputStream(new BufferedInputStream(getSocket().getInputStream()));
   }
   public void close() throws IOException
   {  if (getSocket() != null)    getSocket().close();
      if (streamIn != null)  streamIn.close();
   }
   public ServerSocket getServer() {
	return server;
}

public void setServer(ServerSocket server) {
	this.server = server;
}

public Socket getSocket() {
	return socket;
}

public void setSocket(Socket socket) {
	this.socket = socket;
}

public static void main(String args[]) throws NumberFormatException, IOException
   {  ChatServer server = null;
   args=new String[]{"8090"};
      if (args.length != 1)
         System.out.println("Usage: java ChatServer port");
      else
         server = new ChatServer(Integer.parseInt(args[0]));
   }
}

class RecieveFile extends Thread
{
	
	 private Socket          socket   = null;
	   private ServerSocket    server   = null;
	   private DataInputStream streamIn =  null;
	   private DataOutputStream streamOut=null;
	   String name="";
	   int port;
	   RecieveFile(Socket socket) throws IOException
	   {
		   this.socket=socket;
		   name=socket.getInetAddress().toString().substring(1)+"file";
		   open();
	   }
	   
	

	public void run()
	{
		try{
		String message="";
		while(true)
	{
		
		
		message=streamIn.readUTF();
		
		if(message.equals("DIR"))
		{
			
			String fileName="";
			
			
			
				fileName=streamIn.readUTF();
			
			System.out.println("dir :" +fileName);
			RecieveDirectory dir=new RecieveDirectory(fileName);
		}
		else
		{
			
			file.transfer.RecieveFile file=new file.transfer.RecieveFile(streamIn, streamOut);
			file.start();
		}
	}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		ListenFileServer.servers.remove(name);
   		Clients.fileMap.remove(name);
   		Main.refresh();
   		
		e.printStackTrace();
	}
		
	}
	 public void open() throws IOException
	   {  streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
	   streamOut = new DataOutputStream(socket.getOutputStream());
	   }
	   public void close() throws IOException
	   {  if (socket != null)    socket.close();
	      if (streamIn != null)  streamIn.close();
	   }
	
}


