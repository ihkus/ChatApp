import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class ChatClient extends Thread
{  private Socket socket              = null;
   private DataInputStream  console   = null;
   private DataOutputStream streamOut = null;
   String serverName; int serverPort;
   public ChatClient(String serverName, int serverPort) throws IOException
   {  
	   
	   this.serverName=serverName;
	   this.serverPort=serverPort;
   }
   public void run()
   {
   System.out.println("Establishing connection. Please wait ...");
      try
      {  socket = new Socket(serverName, serverPort);
         System.out.println("Connected: " + socket);
         start1();
      
      String line = "";

	long time=System.currentTimeMillis();
	
	File myFile=new File("abc.avi");
    
    System.out.println(myFile.length());
    byte[] mybytearray = new byte[4194304];
    
    
    
    System.out.println(mybytearray.length);
    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
    
    int bytesRead=0;
    fast fst=new fast(streamOut);
    int count=0;
    
    while ((bytesRead = bis.read(mybytearray))>0) {
       fst= fst.add(mybytearray,bytesRead);
        count++;
        if(count==50)
        {
        	fst.join();
        	count=0;
        }
    }
    
    fst.join();
    
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
//      while (!line.equals(".bye"))
//      {  try
//         {  line = console.readLine();
//            streamOut.writeUTF(line);
//            streamOut.flush();
//         }
//         catch(IOException ioe)
//         {  System.out.println("Sending error: " + ioe.getMessage());
//         
//         }
//      }
   }
   public void start1() throws IOException
   {  console   = new DataInputStream(System.in);
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
   public static void main(String args[]) throws NumberFormatException, IOException
   {  ChatClient client = null;
   args=new String[]{"192.168.0.109","33333"};
      if (args.length != 2)
         System.out.println("Usage: java ChatClient host port");
      else
         client = new ChatClient(args[0], Integer.parseInt(args[1]));
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
