package com.test;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileServer
{  private Socket          socket   = null;
   private ServerSocket    server   = null;
   private DataInputStream streamIn =  null;
   private DataOutputStream streamOut=null;
   public FileServer(int port) throws IOException
   {   System.out.println("Binding to port " + port + ", please wait  ..."+" "+InetAddress.getLocalHost());
   setServer(new ServerSocket(port,3,InetAddress.getLocalHost()));  
   System.out.println("Server started: " + getServer());
   ListenFileClient listenClient=new ListenFileClient(server);
   listenClient.start();

   }
   public  void sendFile(String addr,String str) throws IOException
   {
	   
	   
	   Socket socket=Clients.getFileSocket(addr);
	   
	   System.out.println(addr);
	   System.out.println(socket);
	   
	   SendFile sendFile=new SendFile(socket,str);
	   sendFile.start();
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



class SendFile extends Thread
{
	
	 private Socket          socket   = null;
	   private ServerSocket    server   = null;
	   private DataInputStream streamIn =  null;
	   private DataOutputStream streamOut=null;
	   private String filepath;
	   int port;
	   SendFile(Socket socket,String filepath)
	   {
		   this.socket=socket;
		   this.filepath=filepath;
	   }
	   
	public void run()
	{
		   System.out.println("Establishing connection. Please wait ...");
		      try
		      {  
		         System.out.println("Connected: " + socket);
		         start1();
		      
		      String line = "";

			long time=System.currentTimeMillis();
			
			File myFile=new File(filepath);
		    
		    System.out.println(myFile.length());
		    byte[] mybytearray = new byte[4194304];
		    
		    
		    
		    System.out.println(mybytearray.length);
		    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
		    
		    int bytesRead=0;
		  //  fast fst=new fast(streamOut);
		    int count=0;
		    
		    while ((bytesRead = bis.read(mybytearray))>0) {
		    //   fst= fst.add(mybytearray,bytesRead);
		    	streamOut.write(mybytearray, 0, bytesRead);
//		        count++;
//		        if(count==50)
//		        {
//		        	fst.join();
//		        	count=0;
//		        }
		    }
		    
		   // fst.join();
		    
		  System.out.println(System.currentTimeMillis()-time);
		      
		      
		     System.out.println("ASdfasdfffffff");
		      streamOut.flush();
		      streamOut.close();
		      System.out.println("Asdfasf");
		      
		      }
		      catch(UnknownHostException uhe)
		      {  System.out.println("Host unknown: " + uhe.getMessage());
		      }
		      catch(IOException ioe)
		      {  System.out.println("Unexpected exception: " + ioe.getMessage());
		      }
		      catch(Exception e)
		{
			e.printStackTrace();
			
		}
//		      while (!line.equals(".bye"))
//		      {  try
//		         {  line = console.readLine();
//		            streamOut.writeUTF(line);
//		            streamOut.flush();
//		         }
//		         catch(IOException ioe)
//		         {  System.out.println("Sending error: " + ioe.getMessage());
//		         
//		         }
//		      }
		   }
	public void start1() throws IOException
	   {  streamIn   = new DataInputStream(System.in);
	      streamOut = new DataOutputStream(socket.getOutputStream());
	   }
	   public void stop1()
	   {  try
	      {  if (streamIn   != null)  streamIn.close();
	         if (streamOut != null)  streamOut.close();
	         if (socket    != null)  socket.close();
	      }
	      catch(IOException ioe)
	      {  System.out.println("Error closing ...");
	      }
	   }
	
}
}

