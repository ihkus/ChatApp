import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class Client {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		 RandomAccessFile aFile = new RandomAccessFile
	                ("C:/Users/deepanjan/Desktop/New folder/abc.zip", "r");
	        FileChannel inChannel = aFile.getChannel();
	        ByteBuffer buffer = ByteBuffer.allocate(1024);
	        
	       FileOutputStream fos= new FileOutputStream("C:/Users/deepanjan/Desktop/abc.zip");
	        
	       BufferedOutputStream bos= new BufferedOutputStream(fos);
	        while(inChannel.read(buffer) > 0)
	        {
	            buffer.flip();
	            for (int i = 0; i < buffer.limit(); i++)
	            {
	                bos.write(buffer.get());
	            }
	            buffer.clear(); // do something with the data and clear/compact it.
	        }
	        inChannel.close();
	        aFile.close();
	        bos.flush();
	        bos.close();
	}

}
