package ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import client.Client;

public class UserListPanel extends JPanel{

	private UserList userList;
	private JScrollPane scroll;
	public UserListPanel() {
		
		
		userList=new UserList();
		scroll=new JScrollPane(userList);
		scroll.setBackground(Color.white);
		setLayout(new MigLayout("fill","0[]0",""));
		setBackground(Color.white);
		scroll.setPreferredSize(new Dimension(200,450));
		scroll.setBorder(BorderFactory.createLineBorder(Color.black,1,true));
		this.add(scroll);
	}

	public void addUser(Client client) {
		// TODO Auto-generated method stub
		userList.addUser(client);
	}

	public String getSelectedRow() {
		// TODO Auto-generated method stub
		String name[]=userList.getSelected();
		return name[0];
	}

	public void removeUser(Client client) {
		// TODO Auto-generated method stub
		userList.remove(client);
	}

	public void incrementUnreadCounter(String name) {
		
		userList.incrementUnreadCounter(name);
		
	}

	public void selectUser(String name) {
		
		userList.selectUser(name);
		
	}
	
	
}
