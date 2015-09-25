package com.test;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class FileStatusPanel extends JPanel{
	JLabel label;
	JProgressBar progress;
	StringBuffer msg=new StringBuffer();
	int type;
	long size;
	long max;
	public void setMax(long max)
	{
		this.max=max;
	}
	public void addVal(int bytesRead) {
		// TODO Auto-generated method stub
		size+=bytesRead;
		
		progress.setValue((int) (((double)size/max)*100));
	}
	
	public FileStatusPanel(String str,int type)
	{
		progress=new JProgressBar(0, 100);
		progress.setSize(200, 10);
		progress.setStringPainted(true);
		this.type=type;
		this.msg.append(str);
		String lab="";
		if(type==1)
		{
			lab = String.format("<html><div WIDTH=250><div text-align:center;\">"+msg.toString()+"</div></div><html>", 230);
			setBackground(Color.gray);
		}
		else
		{
			lab = String.format("<html><div WIDTH=250><div text-align:center;\">"+msg.toString()+"</div></div><html>", 230);
			
		}
label=new JLabel(str);

		add(label);
		add(progress);
		setBorder(BorderFactory.createLineBorder(Color.black));
		 
	}
	
	public void append(String str)
	{
		msg.append("<br>").append(str);
		String lab="";
		if(type==1)
		{
			lab = String.format("<html><div WIDTH=200><div style=\"margin-right: 50px;text-align:left;\">"+msg.toString()+"</div></div><html>", 230);
			setBackground(Color.gray);
		}
		else
		{
			lab = String.format("<html><div WIDTH=200><div style=\"margin-left: 50px;text-align:right;\">"+msg.toString()+"</div></div><html>", 230);
			
		}
		
		label.setText(lab);
	}

public static void main(String arg[])
{
	
	JFrame frame=new JFrame();
	frame.setSize(250,500);
	frame.setVisible(true);
	
	frame.add(new FileStatusPanel("something", 1));
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.validate();
}
	
}