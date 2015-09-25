package com.test;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.TransferHandler;
import javax.swing.table.DefaultTableModel;

import file.transfer.Test;

public class ServerFrame extends JFrame{
	private JTextArea text;
	

private JLabel name;
private JTable table;
private DefaultTableModel tableModel;
static TrayIcon trayIcon;
static SystemTray tray;
static JPanel chatPanel;
MessagePanel previous;
	ServerFrame()
	{
		
		this.setVisible(true);
		this.setSize(300, 500);
		this.setLayout(new FlowLayout());
		
		table = new JTable();
		
		String str[]={"IP","unread"};
		
		tableModel=new DefaultTableModel(0,0);
		tableModel.setColumnIdentifiers(str);
		table.setModel(tableModel);
		text=new JTextArea();
		name=new JLabel();
		text.setPreferredSize(new Dimension(250, 50));
		text.setLineWrap(true);
		name.setPreferredSize(new Dimension(250, 10));
		
		
		JScrollPane listScroller = new JScrollPane(table);
		listScroller.setPreferredSize(new Dimension(250, 80));
		this.add(listScroller);
		
		
	chatPanel=new JPanel();
		
		JScrollPane chatScroller = new JScrollPane(chatPanel);
		chatScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatPanel.addContainerListener(new ContainerListener() {
			
			@Override
			public void componentRemoved(ContainerEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentAdded(ContainerEvent arg0) {
				
				chatPanel.revalidate();
                int height = (int)chatPanel.getPreferredSize().getHeight();
                Rectangle rect = new Rectangle(0,height,10,10);
                chatPanel.scrollRectToVisible(rect);
				
				
			}
		});
		chatPanel.setLayout(new WrapLayout());
		
		
		
		
//		chatScroller.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
//	        public void adjustmentValueChanged(AdjustmentEvent e) {  
//	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
//	        }
//	    });
		chatScroller.setPreferredSize(new Dimension(250, 300));
		try {
			init();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.add(name);
		
		
		this.getContentPane().add(chatScroller);
		this.add(text);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		
	}
	public void init() throws AWTException
	{
		getText().addKeyListener(new KeyAdapter() 
		{
		    public void keyPressed(KeyEvent evt)
		    {
		    	
		        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
		        {
		        	
		        	System.out.println("enter keuy ");
		            String msg=getText().getText();
					String data[]=getSelectedRow();
					
					
					Main.sendMsg(data[0],msg);
					if(previous!=null&&previous.type==1)
					{
						previous.append(msg);
					}
					else
					{
						previous=new MessagePanel(msg, 1);
					chatPanel.add(previous);
					}
					chatPanel.validate();
					validate();
					getText().setText("");
		        }
		    }
		});
		text.setDragEnabled(true);
		
		TransferHandler handler =   new TransferHandler() {

	        @Override
	        public boolean canImport(TransferHandler.TransferSupport info) {
	            // we only import FileList
	            if (!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
	                return false;
	            }
	            return true;
	        }

	        @Override
	        public boolean importData(TransferHandler.TransferSupport info) {
	            if (!info.isDrop()) {
	                return false;
	            }

	            // Check for FileList flavor
	            if (!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
	                displayDropLocation("List doesn't accept a drop of this type.");
	                return false;
	            }

	            // Get the fileList that is being dropped.
	            Transferable t = info.getTransferable();
	            List<File> data;
	            try {
	                data = (List<File>)t.getTransferData(DataFlavor.javaFileListFlavor);
	            } 
	            catch (Exception e) { return false; }
	            
	            for (File file : data) {
	                System.out.println(file.getAbsolutePath());
	                String name=getSelectedRow()[0];
	                try {
	                	System.out.println("sending file----->");
						//Main.fileServer.sendFile(name+"file",file.getAbsolutePath());
	                	FileStatusPanel filestatus=new FileStatusPanel(file.getName(), 1);
	                	chatPanel.add(filestatus);
	                	Test test=new Test(file.getAbsolutePath(), file.getName(), Clients.getFileSocket(name+"file"),filestatus);
	                	test.start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	            return true;
	        }

	        private void displayDropLocation(String string) {
	            System.out.println(string);
	        }
	    };
	    text.setTransferHandler(handler);
		
		
		init1();
	}
	
	public String getName()
	{
		return name.getText();
	}
	public void setName(String str)
	{
		name.setText(str);
	}
	
	public JTable getTable()
	{
		return table;
		
	}
	
	public DefaultTableModel getTableModel()
	{
		
		return tableModel;
	}
	
	public JTextArea getText()
	{
		return text;
	}
	
	
	
	public String[] getSelectedRow()
	{
		int rownum=getTable().getSelectedRow();
		int colsize=getTable().getColumnCount();
		String data[]=new String[colsize];
		for(int i=0;i<colsize;i++)
		{
		data[i]=(String)tableModel.getValueAt(rownum,i);
		}
		
		return data;
	}
	
	  void addElementToChatBox(String msg)
	{
		  if(previous!=null&&previous.type==2)
			{
				previous.append(msg);
			}
			else
			{
				previous=new MessagePanel(msg, 2);
			chatPanel.add(previous);
			}
		 chatPanel.validate();
		 validate();
	}
	 
	void addElementToChatBox(Message msg)
	{
		
		 if(previous!=null&&previous.type==msg.type)
			{
			 
				previous.append(msg.msg);
			}
			else
			{
				previous=new MessagePanel(msg.msg, msg.type);
			chatPanel.add(previous);
			}
		 chatPanel.validate();
		 validate();
		
	}
	    public  void init1() throws AWTException{
	        
	        System.out.println("creating instance");
	       
	        if(SystemTray.isSupported()){
	            System.out.println("system tray supported");
	            tray=SystemTray.getSystemTray();

	            
	            ActionListener exitListener=new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    System.out.println("Exiting....");
	                    System.exit(0);
	                }
	            };
	            PopupMenu popup=new PopupMenu();
	            MenuItem defaultItem=new MenuItem("Exit");
	            defaultItem.addActionListener(exitListener);
	            popup.add(defaultItem);
	            defaultItem=new MenuItem("Open");
	            defaultItem.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    setVisible(true);
	                    setExtendedState(JFrame.NORMAL);
	                }
	            });
	            popup.add(defaultItem);
	            
	            
	            BufferedImage image1 = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	            Graphics2D g2d = image1.createGraphics();
	            g2d.setColor(Color.black);
	            g2d.drawString("O", 5, 10);
	            
	            
	            g2d.dispose();
	            trayIcon=new TrayIcon(image1, "SystemTray Demo", popup);
	            
	            
	            trayIcon.setImageAutoSize(true);
	            tray.add(trayIcon);
	        }else{
	            System.out.println("system tray not supported");
	        }
//	        addWindowStateListener(new WindowStateListener() {
//	            public void windowStateChanged(WindowEvent e) {
//	            	
//	            	
//	                if(e.getNewState()==ICONIFIED){
//	                    try {
//	                    	
//	                    	
//	                        tray.add(trayIcon);
//	                        setVisible(false);
//	                        Main.inTray=true;
//	                        System.out.println("added to SystemTray");
//	                    } catch (AWTException ex) {
//	                        System.out.println("unable to add to tray");
//	                    }
//	                }
//	        if(e.getNewState()==7){
//	                    try{
//	            tray.add(trayIcon);
//	            setVisible(false);
//	            Main.inTray=true;
//	            System.out.println("added to SystemTray");
//	            }catch(AWTException ex){
//	            System.out.println("unable to add to system tray");
//	        }
//	            }
//	        if(e.getNewState()==MAXIMIZED_BOTH){
//	                    tray.remove(trayIcon);
//	                    setVisible(true);
//	                    Main.inTray=false;
//	                    System.out.println("Tray icon removed");
//	                }
//	                if(e.getNewState()==NORMAL){
//	                    tray.remove(trayIcon);
//	                    setVisible(true);
//	                    Main.inTray=false;
//	                    System.out.println("Tray icon removed");
//	                }
//	            }
//	        });
	        

	        setVisible(true);
	        
	        
	    }
		public void removeChat() {
			// TODO Auto-generated method stub
			chatPanel.removeAll();
			chatPanel.validate();
		}
	 
}
