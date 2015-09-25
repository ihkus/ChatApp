package com.test;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Main {

	static ServerFrame frame;
	
	
	static DefaultTableModel tableModel;
	static ChatServer server;
	static FileServer fileServer;
	static ListenFileServer listenFileServer;
	static int notificationCount=0;
	static boolean inTray=false;
	public static void main(String[] args) throws IOException {
		
frame=new ServerFrame();
frame.validate();

tableModel=frame.getTableModel();
server =new ChatServer(22222);
fileServer=new FileServer(33333);
String ip=server.getServer().getInetAddress().toString().substring(1);
ListenServer list=new ListenServer(ip.substring(ip.indexOf("/")+1, ip.lastIndexOf("."))); 

listenFileServer =new ListenFileServer(ip.substring(ip.indexOf("/")+1, ip.lastIndexOf(".")));



ListSelectionListener listSelectionListener = new ListSelectionListener() {
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
      
      //String sender=(String)jlist.getSelectedValue();
    	if(frame.getTable().getSelectedRow()==-1)
    		return ;
    	String sender=(String)tableModel.getValueAt(frame.getTable().getSelectedRow(),0);
    	System.out.println(sender);
      frame.setName(sender);
      Chat chat=Clients.getChat(sender);
      frame.removeChat();
      frame.validate();
      frame.previous=null;
      chat.unread=0;
      tableModel.setValueAt("",frame.getTable().getSelectedRow(),1);
      for(Message str:chat.messages)
      {
    	  
    	  frame.addElementToChatBox(str);
      }
      frame.validate();
      frame.repaint();
            
    }
  };
  frame.getTable().getSelectionModel().addListSelectionListener(listSelectionListener);
	}
	static void sendMsg(String serverName,String msg)
	{
		System.out.println("sending to "+serverName);
		Clients.getChat(serverName).addMessage(new Message(serverName,msg,1));
		try {
			server.sendMsg(serverName, msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static void refresh()
	{
		String str="";
		
		
		while(tableModel.getRowCount()!=0)
			tableModel.removeRow(tableModel.getRowCount()-1);
		
		ArrayList<String> key=new ArrayList<String>(Clients.map.keySet());
		for(String s:key)
		{
			//model.addElement(s);
			String unread="";
			
			if(Clients.getChat(s)!=null)
							unread=Clients.getChat(s).unread==0?"":Clients.getChat(s).unread+"";
			
			tableModel.addRow(new String[]{s,unread});
			
		}
		frame.validate();
	
	}
	
static  void write(String str,String sender)
{
	
	try {
		if(!Main.frame.isVisible())
		{
		new NotificationPanel(sender,str,notificationCount++);

//
//		
//		BufferedImage image1 = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g2d = image1.createGraphics();
//        g2d.setColor(Color.black);
//        g2d.drawString(""+notificationCount, 5, 10);
//        
//        
//        g2d.dispose();
//        
//        ServerFrame.trayIcon=new TrayIcon(image1, "SystemTray Demo", null);
//        
//        
//        ServerFrame.trayIcon.setImageAutoSize(true);
		
		}
		
        
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	Clients.getChat(sender).addMessage(new Message(sender,str,2));
	
	if(sender.equals(frame.getName()))
	{
		System.out.println(str);
	
		frame.addElementToChatBox(str);
	frame.validate();
	}
	else
	{
		Clients.getChat(sender).unread++;
		
		for(int i=0;i<tableModel.getRowCount();i++)
		{
		if(sender.equals((String)tableModel.getValueAt(i,0)))
		{
			tableModel.setValueAt(Clients.getChat(sender).unread,i,1);
			break;
		}
		}
	}
	
	
}

}
