package com.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class IO {

	
	IO()
	{
		
		
		
	}

	public IO(DataInputStream console, DataOutputStream streamOut,String serverName) {
		
		Reader reader=new Reader(console,streamOut,serverName);
		reader.start();
		
		
	}
	
	
	
	
}


