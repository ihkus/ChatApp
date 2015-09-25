package com.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Reader extends Thread{

	DataInputStream console;
	DataOutputStream streamOut;
	String serverName;
	
	Reader()
	{
	
	}
	public Reader(DataInputStream console, DataOutputStream streamOut,String serverName) {
		// TODO Auto-generated constructor stub
		this.console=console;
		this.streamOut=streamOut;
		this.serverName=serverName;
	}
	public void run()
	{
		
		try{      
			
			String line="";
		    while (true)
		    {  try
		       {  
		    	System.out.println(serverName+" servber");
		    	line = console.readUTF();

		    		

		          Main.write(line,serverName);

		       }
		       catch(IOException ioe)
		       {  System.out.println("Sending error: \n" );
		       ioe.printStackTrace();
		       	
		       		
		       		
		       		ListenServer.servers.remove(serverName);
		       		Socket socket=Clients.map.get(serverName).getSocket();
		       		
		       		socket.getInputStream().close();
		       		socket.getOutputStream().close();
		       		socket.close();
		       		
		       		Clients.map.remove(serverName);
		       		Main.refresh();
		       		break;
		       }
		    }
		      
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
	}
	
	
}
