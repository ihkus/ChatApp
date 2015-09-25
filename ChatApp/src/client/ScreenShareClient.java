package client;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class ScreenShareClient extends Thread {

	private Socket socket = null;
	private JFrame frame;
	private DataInputStream streamIn = null;
	private DataOutputStream streamOut = null;
	private ImageIcon icon;
	private JLabel label;
	private int x=0,y=0;
	private int clickedX=0,clickedY=0,btn=0,num=0;
	public ScreenShareClient(final Socket socket) {

		frame = new JFrame();

		frame.setSize(500, 500);

		icon = new ImageIcon();
		label = new JLabel(icon);
		frame.add(label);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        
				try {
					socket.close();
					frame.dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
		    }
		});
		
		frame.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getClickCount()==2)
				{
					num=2;
				}
				else
					num=1;
				if (SwingUtilities.isLeftMouseButton(e)) {
			          btn=1;
			        }
			        if (SwingUtilities.isMiddleMouseButton(e)) {
			          
			        }
			        if (SwingUtilities.isRightMouseButton(e)) {
			          btn=3;
			        }
				clickedX=e.getX();
				clickedY=e.getY();
			}
		});
		frame.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				x=e.getX();
				y=e.getY();
				System.out.println(e.getX() + " " + e.getY());
			}

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		this.socket = socket;
		try {
			streamIn = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));

			streamOut = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {

		frame.setVisible(true);
		

			try {
				while (true) {
				int len = Integer.parseInt(streamIn.readUTF());

				byte b[] = new byte[len];
				for (int i = 0; i < len; i++)
					b[i] = (byte) streamIn.read();

				ByteArrayInputStream im = new ByteArrayInputStream(b);
				BufferedImage img = ImageIO.read(im);
				icon = new ImageIcon(img);
				label.setIcon(icon);
				frame.validate();
				
				streamOut.writeUTF(x+" "+y+" "+clickedX+" "+clickedY+" "+btn+" "+num);
				System.out.println(num);
				clickedX=0;
				clickedY=0;
				btn=0;
				num=0;
				}
			} catch (Exception e) {
				
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame.dispose();
				e.printStackTrace();
			}
		}

	

}
