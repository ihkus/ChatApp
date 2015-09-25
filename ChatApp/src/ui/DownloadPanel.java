package ui;

import java.awt.Color;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class DownloadPanel extends JPanel{

	
	
	DownloadPanel()
	{
		
		//setPreferredSize(new Dimension(500, 450));
		
		setLayout(new MigLayout("fill","0[fill]0", "1[]1"));
		setBackground(Color.white);
		
		
	}
	
	public void addFile(String fullFilePath,String fileName, String dateTime,String status,String sender,int index,String fileLocation)
	{
		FilePanel panel=new FilePanel(fullFilePath,fileName, dateTime, status, sender,index,fileLocation);
		add(panel,"dock north,gapy 1 1,wrap");
		revalidate();
		//LetsStart.validate();
	}


	
	
}
