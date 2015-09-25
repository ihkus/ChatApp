import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;


public class Test  {

	

	
	public static void main(String arg[]) throws AWTException
	{

		 JFrame frame;
		frame = new JFrame();

		
		
		frame.setSize(500, 500);
		frame.setVisible(true);
		
		
	
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
System.out.println("hello");
frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.toFront();
		
	
	}
	
	
	
}
