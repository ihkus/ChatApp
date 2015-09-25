package com.test;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class AddServer extends Thread
{  private Socket socket              = null;
   private DataInputStream  console   = null;
   private DataOutputStream streamOut = null;
   String serverName;
   int serverPort;
   
   public AddServer(String serverName, int serverPort) throws IOException
   {  
	   
	   this.serverName=serverName;
	   this.serverPort=serverPort;
	   
   }
   
   public void run()
   {
	   
	      try
	      {  socket = new Socket(serverName, serverPort);
	      
	      ListenFileServer.servers.put(serverName, "1");
	      
	      System.out.println("recieving file from "+socket);
	      
	         RecieveFile file=new RecieveFile(socket);
	         file.start();
	      }
	      catch(UnknownHostException uhe)
	      {  //System.out.println("Host unknown: " + uhe.getMessage());
	    	  ListenFileServer.servers.remove(serverName);
	    	  this.interrupt();
	      }
	      catch(IOException ioe)
	      {  //System.out.println("Unexpected exception: " + ioe.getMessage());
	    	  ListenFileServer.servers.remove(serverName);
	    	  this.interrupt();
	      }
	      
//	      while (!line.equals(".bye"))
//	      {  try
//	         {  line = console.readLine();
//	            streamOut.writeUTF(line);
//	            streamOut.flush();
//	         }
//	         catch(IOException ioe)
//	         {  System.out.println("Sending error: " + ioe.getMessage());
//	         
//	         }
//	      }
	      
	     
   }
   
   public void start1() throws IOException
   {  console   = new DataInputStream(socket.getInputStream());
      streamOut = new DataOutputStream(socket.getOutputStream());
   }
   public void stop1()
   {  try
      {  if (console   != null)  console.close();
         if (streamOut != null)  streamOut.close();
         if (socket    != null)  socket.close();
      }
      catch(IOException ioe)
      {  System.out.println("Error closing ...");
      }
   }
   
}


