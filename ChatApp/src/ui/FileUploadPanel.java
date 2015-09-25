package ui;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class FileUploadPanel extends JPanel{

	
	JLabel dateTime;
	JLabel fileName;
	
	JLabel sender;
	FileUploadPanel me;
	
	
	
	
	
	SimpleDateFormat formatter;
	
	FileUploadPanel(final String fullFilePath,final String fileName,String dateTime,final String sender)
	{
		
		setLayout(new MigLayout("", "[]push[right][right][right]", ""));
		formatter = new SimpleDateFormat("dd MMM yy h:mm");
		me=this;
		this.dateTime=new JLabel(formatter.format(new Date()));
		this.fileName=new JLabel(fileName);
	
		this.sender=new JLabel(sender+" |");
		
		
		//UIManager.put("ProgressBar.background", Color.ORANGE);
		
		setBorder(BorderFactory.createLineBorder(Color.black,1,true));
		add(this.fileName,"span 2");
		add(this.sender);
		add(this.dateTime,"wrap,gapy 0");
		setBackground(Color.white);	
	}
}


