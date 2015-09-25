package com.test;

import java.util.HashMap;
import java.util.Map;

public class ListenServer extends Thread{

	String ip;
	static Map<String,String> servers=new HashMap<String,String>();
	ListenServer(String ip)
	{
		this.ip=ip;
		
		this.start();
		
	}
	
	public void run()
	{
		
		
		
			while(true)
			{
			for(int i=1;i<=244;i++)
			{
				if(servers.get(ip+"."+i)!=null)
				{
					
					continue;
				}
				try{
					servers.put(ip+"."+i,"1");
				ChatClient chat=new ChatClient(ip+"."+i,22222);
				chat.start();
				
				}
				catch(Exception e)
				{
					servers.remove(ip+"."+i);
					
				}
				
			}
			}
			
			
		
		
		
		
	}
	
	
	
}



