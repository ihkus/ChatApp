import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ChatServer extends Thread
{  private Socket          socket   = null;
   private ServerSocket    server   = null;
   private DataInputStream streamIn =  null;
   private DataOutputStream streamOut=null;
   public ChatServer(int port) throws IOException
   {   System.out.println("Binding to port " + port + ", please wait  ..."+" "+InetAddress.getLocalHost());
   server = new ServerSocket(port,3,InetAddress.getLocalHost());  
   System.out.println("Server started: " + server);
   
   
   
   }
   public void run()
   {
	   while(true)
	   {
		   try {
			socket=server.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   System.out.println("connecte");
		   Common c=new Common(socket); 
		   c.start();
	   }
   }
   public void open() throws IOException
   {  streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
   }
   public void close() throws IOException
   {  if (socket != null)    socket.close();
      if (streamIn != null)  streamIn.close();
   }
   public static void main(String args[]) throws NumberFormatException, IOException
   {  ChatServer server = null;
   args=new String[]{"33333"};
      if (args.length != 1)
         System.out.println("Usage: java ChatServer port");
      else
         server = new ChatServer(Integer.parseInt(args[0]));
   }
}

class Common extends Thread
{
	
	 private Socket          socket   = null;
	   private ServerSocket    server   = null;
	   private DataInputStream streamIn =  null;
	   private DataOutputStream streamOut=null;
	   int port;
	   Common(Socket socket)
	   {
		   this.socket=socket;
	   }
	   
	public void run()
	{FileOutputStream fos ;
    BufferedOutputStream bos = null;
		try
	    { 	
		
		fos=null;
		bos=null;
	       
	
       
       open();
       boolean done = false;

       System.out.println("transfer start");
   	long time=System.currentTimeMillis();
       byte[] mybytearray = new byte[4194304];

       fos= new FileOutputStream("C:/Users/deepanjan/Desktop/abc.zip");
       
       bos= new BufferedOutputStream(fos);
       int bytesRead= 0;
       fast1 fst=new fast1(bos);
       int count=0;
       while((bytesRead = streamIn.read(mybytearray))>0) {
    	   
       fst=fst.add(mybytearray, bytesRead);
       if(count==50)
       {
    	   
    	   fst.join();
    	   count++;
       }
       
       
       
       
       fst.join();
       bos.close();
       System.out.println(System.currentTimeMillis()-time);
       System.out.println("finished");
    }
    
	}
    catch(Exception ioe)
    {  System.out.println(ioe);
    try {
		close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
	finally
	{
		System.out.println("finished");
		if(bos!=null)
			try {
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
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

