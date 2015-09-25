package com.test;

import java.util.ArrayList;

public class Chat {

	
	ArrayList<Message> messages;
	
	int unread;
	
	Chat()
	{
		
		messages=new ArrayList<Message>();
	}
	
	public void addMessage(Message msg)
	{
		messages.add(msg);
		
	}
	public int getUnread()
	{
		return unread;
	}
	
}
class Message 
{
	String sender;String msg;
	int type;
Message(String sender,String msg,int type)
{this.msg=msg;
this.sender=sender;
	this.type=type;
	
	
}

}