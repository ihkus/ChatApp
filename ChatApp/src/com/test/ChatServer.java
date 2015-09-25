package com.test;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ChatServer
{  private Socket          socket   = null;
   private ServerSocket    server   = null;
   private DataInputStream streamIn =  null;
   private DataOutputStream streamOut=null;
   public ChatServer(int port) throws IOException
   {   
	   
	   System.out.println("Binding to port " + port + ", please wait  ..."+" "+InetAddress.getLocalHost());
   setServer(new ServerSocket(port,3,InetAddress.getLocalHost()));  
   System.out.println("Server started: " + getServer());
   ListenClient listenClient=new ListenClient(server);
   listenClient.start();

   }
   public void sendMsg(String addr,String str) throws IOException
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




class fast1 extends Thread
{
	
	static BufferedOutputStream out;
	static ArrayList<byte[]> buff=new ArrayList<byte[]>();
	static ArrayList<Integer> limit=new ArrayList<Integer>();
	static int j=0;
	fast1(BufferedOutputStream bos)
	{
		this.out=bos;

		
	}
	public fast1 add(byte[] mybytearray, int bytesRead) {
		// TODO Auto-generated method stub
		
		buff.add(mybytearray);
		limit.add(bytesRead);
		//System.out.println(this.getState());
		if(this.getState()==Thread.State.NEW||this.getState()==Thread.State.TERMINATED)
		{
			System.out.println("sizse "+buff.size());
			fast1 fst=new fast1(out);
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

