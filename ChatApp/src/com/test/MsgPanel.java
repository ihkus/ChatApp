package com.test;

import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLEditorKit;

public class MsgPanel extends JFrame{
	JTextPane editor;
	StringBuffer html;
	public MsgPanel() throws BadLocationException
	{
		this.setVisible(true);
		this.setSize(300,550);
		editor = new JTextPane();
		HTMLEditorKit kit=new HTMLEditorKit();
	    editor.setEditorKit(kit);
	    this.add(editor);
	    
	    html=new StringBuffer("<html><body>");
	    
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	void addMessage(String msg,int type)
	{
	if(type==1)
		html.append("<div WIDTH=250><div style=\"margin-right: 50px;text-align:left;\">"+msg+"</div>");
	else
		html.append("<div WIDTH=250><div style=\"margin-left: 50px;text-align:right;\">"+msg+"</div>");
	
	editor.setText(html+"</body></html");
	}
	
	public static void main(String[] args) throws BadLocationException {
		// TODO Auto-generated method stub
MsgPanel msg=new MsgPanel();
msg.addMessage("hello asdf asd fa dasdf asdf a as dfa a asd f a a sdf  a a df a  a df a a f", 1);
msg.addMessage("hello asdf asd fa dasdf asdf a as dfa a asd f a a sdf  a a df a  a df a a f", 2);
msg.addMessage("hello asdf asd fa dasdf asdf a as dfa a asd f a a sdf  a a df a  a df a a f", 1);
msg.addMessage("hello asdf asd fa dasdf asdf a as dfa a asd f a a sdf  a a df a  a df a a f", 2);
		
//		insertRight("hello how are you hello how are you",20,20,1,70);
		
	}
	
	public static  String insertLeft(String str,int left,int right,int align,int total)
	{
		
		StringTokenizer token=new StringTokenizer(str);
		StringBuffer buff=new StringBuffer();
		int permitted=total-right-left;
		for(int i=0;i<left;i++)
			buff.append(" ");
		boolean newLine=false;
		String next=token.nextToken();
		while(true)
		{
			
			if(newLine)
			{
				for(int i=0;i<left;i++)
					buff.append(" ");
				newLine=false;
			}
				if(next==null)
				{
					if(token.hasMoreElements())
						next=token.nextToken();
					else
						break;
					
				}
		if(next.length()<=permitted)
		{
			buff.append(next);
			
			permitted-=next.length();
			next=null;
			
		}
		if(permitted>0)
		{
			
			permitted--;
			buff.append(" ");
		}
		if(permitted==0)
		{
			
			for(int i=0;i<right;i++)
			{
				
				buff.append(" ");
				
			}
			newLine=true;
			buff.append("\n");
			permitted=total-left-right;
		}
		}
		
			buff.append("\n\n");
		
	return	buff.toString();
	}
	
	
	public static  String insertRight(String str,int left,int right,int align,int total)
	{
		
		StringTokenizer token=new StringTokenizer(str);
		StringBuffer buff=new StringBuffer();
		int permitted=total-right-left;
		for(int i=0;i<left;i++)
			buff.append(" ");
		boolean newLine=false;
		String next=token.nextToken();
		while(true)
		{
			
			if(newLine)
			{
				for(int i=0;i<left;i++)
					buff.append(" ");
				newLine=false;
			}
				if(next==null)
				{
					if(token.hasMoreElements())
						next=token.nextToken();
					else
						break;
					
				}
		if(next.length()<=permitted)
		{
			buff.append(next);
			
			permitted-=next.length();
			next=null;
			
		}
		if(permitted>0)
		{
			
			permitted--;
			buff.append(" ");
		}
		if(permitted==0)
		{
			
			for(int i=0;i<right;i++)
			{
				
				buff.append(" ");
				
			}
			newLine=true;
			buff.append("\n");
			permitted=total-left-right;
		}
		}
		
			buff.append("\n\n");
			
			
			token=new StringTokenizer(buff.toString(),"\n");
			StringBuffer bf=new StringBuffer();
			System.out.println(buff);
			while(token.hasMoreElements())
			{
				String s=token.nextToken();
				s=s.trim();
				int length=s.length();
				for(int i=0;i<right;i++)
					s+=" ";
				for(int i=0;i<total-length-right;i++)
					s=" "+s ;
				
				
				bf.append(s); 
				bf.append("\n");
			}
			System.out.println(bf);
		
	return	bf.toString();
	}

}
