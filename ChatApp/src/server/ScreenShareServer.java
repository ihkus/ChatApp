package server;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ScreenShareServer extends Thread{

	private Socket socket = null;

	private DataInputStream streamIn = null;
	private DataOutputStream streamOut = null;
	private boolean escPressed=false;
	public ScreenShareServer(Socket socket) {
		this.socket = socket;
		try {
			streamIn = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
		
		streamOut = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

	            @Override
	            public boolean dispatchKeyEvent(KeyEvent ke) {
	                synchronized (ScreenShareServer.class) {
	                    switch (ke.getID()) {
	                    case KeyEvent.KEY_PRESSED:
	                        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
	                            escPressed = true;
	                        }
	                        break;

	                   
	                    }
	                    return false;
	                }
	            }
	        });
	 
		
		
	}
	
	public void run()
	{
		
		System.out.println("startted screen sharing");
		
		
		 Iterator iter = ImageIO.getImageWritersByFormatName("jpeg");
		   ImageWriter writer = (ImageWriter)iter.next();
		   ImageWriteParam iwp = writer.getDefaultWriteParam();
		   iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		    
		   Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		   
		   float quality=0.5f;
		   iwp.setCompressionQuality(quality);
		  
		   try{
			   Robot robot=new Robot();
		   while(!escPressed)
		   {
			   
			   
			   
			   BufferedImage capture = new Robot().createScreenCapture(screenRect);
			   
			   //resizing
//			   int y=(int)(screenRect.getMaxY()-(5f*screenRect.getMaxY())/100);
//			   int x =(int)(screenRect.getMaxX()-(5f*screenRect.getMaxX())/100);
////			   x=(int) screenRect.getMaxX();
////			   y=(int) screenRect.getMaxY();
//			   BufferedImage resizedImage = new BufferedImage(x,y,1);
//			   Graphics2D g = resizedImage.createGraphics();
//				g.drawImage(capture, 0, 0, x,y, null);
//				g.dispose();	
//				g.setComposite(AlphaComposite.Src);
//	
//				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//				g.setRenderingHint(RenderingHints.KEY_RENDERING,
//				RenderingHints.VALUE_RENDER_QUALITY);
//				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//				RenderingHints.VALUE_ANTIALIAS_ON);
			   
			    
			  //compresssion
			   IIOImage image = new IIOImage(capture, null, null);
//			   
//			   
			   
			   ByteArrayOutputStream baos = new ByteArrayOutputStream();
			   
			   ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
			   writer.setOutput(ios);
		        writer.write(null, image, iwp);
		        
			   //ImageIO.write(image.getRenderedImage(), "jpg", baos);
			   
			   
		        byte[] b= baos.toByteArray();
		        
			   streamOut.writeUTF(""+b.length);
		        streamOut.write(b);
		        
		        

			   streamOut.flush();
			   
			   String line[]=streamIn.readUTF().split(" ");
			   
			   int x=Integer.parseInt(line[0]);
			   int y=Integer.parseInt(line[1]);
			   int clickedX=Integer.parseInt(line[2]);
			   int clickedY=Integer.parseInt(line[3]);
			   int btn=Integer.parseInt(line[4]);
			   int num=Integer.parseInt(line[5]);
			   
			  
			   robot.mouseMove(clickedX, clickedY);
			   if(btn==1)
			   {
				   
				   robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				   robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
				   if(num==2)
					   robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				   robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
				   System.out.println(line[0]+" "+line[1]+" "+line[2]+" "+line[3]+" "+line[4]);
			   }
			   if(btn==3)
			   {
				   
				   robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
				   robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
				   System.out.println(line[0]+" "+line[1]+" "+line[2]+" "+line[3]+" "+line[4]);
			   }
			   
			   robot.mouseMove(x, y);
		   }
		socket.close();
		   }catch(Exception e)
		   {
			   
			   try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			   e.printStackTrace();
		   }
	}

	
	

}
