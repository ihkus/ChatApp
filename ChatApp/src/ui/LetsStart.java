package ui;

import javax.swing.JFrame;
import javax.swing.text.BadLocationException;

import client.Client;

public class LetsStart {
	static MainFrame ui;
	public static void main(String[] args) throws BadLocationException {
		// TODO Auto-generated method stub
		ui=new MainFrame();
		
	}

	public static void messageRecieved(String msg, Client client) {
		// TODO Auto-generated method stub
		ui.messageRecieved(msg, client);
	}

	public static void setVisible(boolean b) {
		// TODO Auto-generated method stub
		ui.setVisible(b);
	}

	public static void setExtendedState() {
		// TODO Auto-generated method stub
		ui.setExtendedState(JFrame.NORMAL);
	}

	public static void validate() {
		// TODO Auto-generated method stub
		ui.revalidate();
		ui.repaint();
	}

	
	
}
