package ui;

import java.awt.Color;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class UploadPanel extends JPanel{

	
	
	UploadPanel()
	{
		
		//setPreferredSize(new Dimension(500, 450));
		
		setLayout(new MigLayout("fill","0[fill]0", "[]"));
		setBackground(Color.white);
		
		
	}
	
	public void addFile(String fullFilePath,String fileName, String dateTime,String sender)
	{
		FileUploadPanel panel=new FileUploadPanel(fullFilePath,fileName, dateTime, sender);
		add(panel,"dock north,gapy 1 1,wrap");
		revalidate();
		//LetsStart.validate();
	}


	
	
}
