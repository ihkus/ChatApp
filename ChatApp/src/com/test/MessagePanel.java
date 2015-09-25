package com.test;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessagePanel extends JPanel{
	JLabel label;
	StringBuffer msg=new StringBuffer();
	int type;
	MessagePanel(String str,int type)
	{
		
		this.type=type;
		this.msg.append(str);
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
label=new JLabel(lab);

		add(label);
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
}