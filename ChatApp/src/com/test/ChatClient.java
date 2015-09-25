package com.test;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ChatClient extends Thread
{  private Socket socket              = null;
   private DataInputStream  console   = null;
   private DataOutputStream streamOut = null;
   String serverName;
   int serverPort;
   
   public ChatClient(String serverName, int serverPort) throws IOException
   {  
	   
	   this.serverName=serverName;
	   this.serverPort=serverPort;
	   
   }
   
   public void run()
   {
	   
	      try
	      {  socket = new Socket(serverName, serverPort);
	      
	      ListenServer.servers.put(serverName, "1");
	         System.out.println("Connected: " + socket);
	         start1();
	         String line = "";

	         IO io=new IO(console,streamOut,serverName);
	      }
	      catch(UnknownHostException uhe)
	      {  //System.out.println("Host unknown: " + uhe.getMessage());
	    	  ListenServer.servers.remove(serverName);
	    	  if(socket!=null)
	    	  {
	    		  try{
	    	 
	    	  socket.getInputStream().close();
	    	  socket.getOutputStream().close();
	    	  socket.close();
	    		  }catch(Exception e)
	    		  {
	    			  
	    			  
	    		  }
	    	  }
	      }
	      catch(IOException ioe)
	      {  //System.out.println("Unexpected exception: " + ioe.getMessage());
	    	  ListenServer.servers.remove(serverName);
	    	  
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
	      
	      this.interrupt();
	     
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


class fast extends Thread
{
	
	static DataOutputStream out;
	static ArrayList<byte[]> buff=new ArrayList<byte[]>();
	static ArrayList<Integer> limit=new ArrayList<Integer>();
	static int j=0;
	fast(DataOutputStream out)
	{
		this.out=out;

		
	}
	public fast add(byte[] mybytearray, int bytesRead) {
		// TODO Auto-generated method stub
		
		buff.add(mybytearray);
		limit.add(bytesRead);
		//System.out.println(this.getState());
		if(this.getState()==Thread.State.NEW||this.getState()==Thread.State.TERMINATED)
		{
			System.out.println("sizse "+buff.size());
			fast fst=new fast(out);
			fst.start();
		return fst;	
		}
		return this;
			
	}
	public void add(ByteBuffer bff)
{
		
		
}
	
	public void run()
	{
		try
		{
			
			while(j<buff.size())
			{
				System.out.println(j);
				out.write(buff.get(j),0,limit.get(j));
				System.out.println(j);
			j++;
			}
			
			
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	
}
