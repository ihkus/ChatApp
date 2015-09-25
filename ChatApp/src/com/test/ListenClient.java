package com.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class ListenClient extends Thread{
	
	ServerSocket server;
	ListenClient(ServerSocket server)
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
				Clients.add(new Info(client));
				Main.refresh();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			   
		   }
		
		
	}

}
