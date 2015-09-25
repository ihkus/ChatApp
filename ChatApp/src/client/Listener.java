package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import ui.LetsStart;
import ui.MainFrame;

public class Listener extends Thread
{
	
	Socket socket;
	DataInputStream streamIn;
	DataOutputStream streamOut;
	Client client;
	public Listener(Client client)
	{
		
		
		try {
			this.client=client;
			socket=client.getSocket();
			//socket=new Socket(client.getIp(),22222);
			System.out.println("Connected");
			streamIn=new DataInputStream(socket.getInputStream());
			streamOut=new DataOutputStream(socket.getOutputStream());
			start();
		} catch (IOException e) {
			System.out.println("Error connecting "+client.getIp()+" :22222");
			e.printStackTrace();
		}
		
		
		
	}
	
	public void run()
	{
		
		
		
		while(true)
		{
			
			try {
				System.out.println("listening");
				System.out.println(client.getSocket());
				String msg=streamIn.readUTF();
				System.out.println("reciving message");
				LetsStart.messageRecieved(msg, client);
				
				
			} catch (IOException e) {
				System.out.println(client.getIp()+" : disconnected");
				MainFrame.removeUser(client);
				
			try {
				client.getStreamIn().close();
				client.getSocket().close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				break;
			}
			
			
			
			
		}
		
		
		
	}
}
