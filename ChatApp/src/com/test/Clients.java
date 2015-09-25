package com.test;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Clients {

	
	
	static ArrayList<Info> clients=new ArrayList<Info>();
	static Map<String,Info> map=new HashMap<String,Info>();
	static Map<String,Socket> fileMap=new HashMap<String,Socket>();
	static void add(Info s)
	{
		clients.add(s);
		map.put(s.getSocket().getInetAddress().toString().substring(1),s);
	}
	public static Info get(String key)
	{
		return map.get(key);
	}
	
	public static Chat getChat(String key)
	{
		if(map.get(key)!=null)
		return map.get(key).getChat();
		return null;
	}
	public static Socket getSocket(String key)
	{
		if(map.get(key)!=null)
		return map.get(key).getSocket();
		return null;
	}
	public static void addFileClient(Socket socket)
	{
		
		fileMap.put(socket.getInetAddress().toString().substring(1)+"file",socket);
		
	}
	public static Socket getFileSocket(String key)
	{
		if(fileMap.get(key)!=null)
			return fileMap.get(key);
			return null;
		
	}
}
