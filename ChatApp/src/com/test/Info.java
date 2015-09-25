package com.test;

import java.net.Socket;

public class Info {

	Socket socket;
	Chat chat;
	Info(Socket socket)
	{
		this.socket=socket;
		chat=new Chat();
		
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public Chat getChat() {
		return chat;
	}
	public void setChat(Chat chat) {
		this.chat = chat;
	}
	
	
	
}
