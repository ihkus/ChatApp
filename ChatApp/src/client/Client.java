package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import ui.MainFrame;

public class Client {

	private Socket socket              = null;
	   private DataInputStream  streamIn   = null;
	   private DataOutputStream streamOut = null;
	   private String ip;
	   private int port;
	private String name;
	private String systemName;
	public Client(Socket socket) throws IOException {
		this.socket=socket;
		streamIn=new DataInputStream(socket.getInputStream());
		streamOut=new DataOutputStream(socket.getOutputStream());
		ip=socket.getInetAddress().getHostAddress();
		port=socket.getPort();
		
		systemName=socket.getInetAddress().getHostName();
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public DataInputStream getStreamIn() {
		return streamIn;
	}

	public void setStreamIn(DataInputStream streamIn) {
		this.streamIn = streamIn;
	}

	public DataOutputStream getStreamOut() {
		return streamOut;
	}

	public void setStreamOut(DataOutputStream streamOut) {
		this.streamOut = streamOut;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static void connectServer(String ip,int p)
	{
		
		try {
			Socket socket=new Socket(ip, p);
			Client client=new Client(socket);
			Listener lister=new Listener(client);
		} catch (IOException e) {
		
			
			// TODO Auto-generated catch block
			
			//System.out.println(localhost.substring(0,localhost.lastIndexOf(".")+1)+addr+" is not online");
			
		}
		
		
	}
	public static void broadcast(String ip,int p) {
		System.out.println("hello");
		
		final String localhost=ip;
		final int port=p;
		
				
				System.out.println("Broadcasting ");
				for(int i=0;i<=244;i++)
				{
					final int addr=i;
					new Thread(new Runnable() {
						
						@Override
						public void run() {
					//System.out.println("Sending request to :"+localhost.substring(0,localhost.lastIndexOf(".")+1)+addr);
							int i=0;
							while(i++<3)
							{
							
					try {
						
					if(!localhost.equals(localhost.substring(0,localhost.lastIndexOf(".")+1)+addr))
						{
							
						Socket socket=new Socket(localhost.substring(0,localhost.lastIndexOf(".")+1)+addr, port);
						
						Client client=new Client(socket);
						client.getStreamOut().writeUTF("name-"+MainFrame.myName+"-name");
						String name=client.getStreamIn().readUTF();
						name=name.split("-")[1];
						client.setName(name);
						MainFrame.addClient(client);
						MainFrame.addUser(client);
						Listener listner=new Listener(client);
						break;
						}
					} catch (IOException e) {
					
						
						// TODO Auto-generated catch block
						
						//System.out.println(localhost.substring(0,localhost.lastIndexOf(".")+1)+addr+" is not online");
						
					}
							}
						}
					}).start();
					
					
				}
			
		
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		try {
			return streamIn.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

}
