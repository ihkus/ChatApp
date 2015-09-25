package com.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class ListenFileClient extends Thread{
	
	ServerSocket server;
	ListenFileClient(ServerSocket server)
	{
		
		this.server=server;
		
	}
	
	public void run()
	{
		
		while(true)
		   
		{
			   System.out.println("connecte");
			   
			try {
				Socket client=server.accept();
				Clients.addFileClient(client);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			   
		   }
		
		
	}

}
