package com.test;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ui.MainFrame;

public class NotificationPanel extends Thread {

	MyDialog my;
		public NotificationPanel(String sender, String str, int notificationCount) throws InterruptedException
	{
		my=new MyDialog(sender, str,notificationCount);
		my.addMouseListener(new MouseListener() {
			
			
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				interrupt();
			}
		});
		my.text.addMouseListener(new MouseListener() {
			
			
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				interrupt();
			}
		});
		this.start();
		
	}
	
	public void run()
	{
		
		
		try {
			Thread.sleep(5000);
			for(int i=100;i>1;i--)
			{
				my.setOpacity((float)((double)i/100));
				
					Thread.sleep(20);
				
			}
			my.dispose();
			MainFrame.notificationCount--;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("kjlkj");
			my.setOpacity(1.0f);
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String arg[]) throws InterruptedException
	{
		new NotificationPanel("sukhi", "ahello hello hello hellloo hello hello hello he llo hello he llo he llo eh loa",0);
		
		
	}
	
	
	
}
