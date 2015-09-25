package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import ui.MainFrame;
import client.Client;
import client.Listener;
import client.fileTransfer.Send;

public class Server {

	private Socket socket = null;

	private ServerSocket server = null;
	private ServerSocket fileServer = null;
	private ServerSocket screenShareServer = null;

	private DataInputStream streamIn = null;
	private DataOutputStream streamOut = null;
	private String myIp = null;
	private String myName = null;
	private String mySystemName = null;

	
	
	private HashMap<String, Client> clientList;

	public Server(int port) throws IOException {

		setServer(new ServerSocket(port, 255, InetAddress.getLocalHost()));
		System.out.println(getServer());
		MainFrame.myIp=getServer().getInetAddress().getHostAddress();
		
		setFileServer(new ServerSocket(33333, 255, InetAddress.getLocalHost()));

		System.out.println(getFileServer());

		setScreenShareServer(new ServerSocket(44444, 255,
				InetAddress.getLocalHost()));
		setMyIp(getServer().getInetAddress().getHostAddress());
		clientList = new HashMap<String, Client>();
	}

	public void start() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("Screen sharing Server started");
				while (true) {
					try {
						Socket socket = getScreenShareServer().accept();
						System.out.println("screen sharing servr connected " + socket);

						
						String name=new DataInputStream(socket.getInputStream()).readUTF();
						String[] options = new String[2];
						options[0] = new String("Grant access");
						options[1] = new String("Reject");
						
						int choice=JOptionPane.showOptionDialog(null,name+" requested screen access !","", 0,JOptionPane.QUESTION_MESSAGE,null,options,null);
						System.out.println(choice);
					    if(choice==1)
					    {
					    	
					    	new DataOutputStream(socket.getOutputStream()).writeUTF("no");
					    	socket.close();
					    	break;
					    	
					    }
					    else
					    {
					    new DataOutputStream(socket.getOutputStream()).writeUTF("yes");
						
						
						ScreenShareServer screenShare = new ScreenShareServer(socket);
						screenShare.start();
					    }
					} catch (Exception e)

					{

					}
				}

			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("File Server started");
				while (true) {
					try {
						Socket socket = getFileServer().accept();
						System.out.println("file servr connected " + socket);
						
						
						String command=new DataInputStream(socket.getInputStream()).readUTF();
						
						if(command.equals("newFile"))
						{
							
							String info=new DataInputStream(socket.getInputStream()).readUTF();
							
							MainFrame.recievedNewFile(info,true);
							socket.getOutputStream().close();
							socket.close();
							
							
							
						}
						else if(command.equals("sendFile"))
						{
							
							String info=new DataInputStream(socket.getInputStream()).readUTF();
							
							String str[]=info.split("::");
							String fileName=str[0];
							File file=new File(fileName);
							System.out.println(info+"----- in server");
							Send send=new Send(file.getAbsolutePath(), file.getName(), socket,"sendFile");
							
							
						}
						
//						Recieve recieve = new Recieve(socket);
//						recieve.start();
						
					
					} catch (Exception e)

					{

					}
				}

			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("Server started");
				while (true) {
					try {
						Socket socket = getServer().accept();

						Client client = new Client(socket);
						String name = client.getStreamIn().readUTF();
						name = name.split("-")[1];
						client.setName(name);

						System.out.println("CONNECTED TO : " + client.getIp());
						System.out.println(clientList);
						if (clientList.get(client.getName()) == null) {
							clientList.put(client.getName(), client);

							if (!client.getIp().equals(myIp)) {
								client.getStreamOut().writeUTF(
										"name-" + MainFrame.myName + "-name");
								MainFrame.addUser(client);
								System.out.println("Listening to : "
										+ client.getIp());

								Listener listener = new Listener(client);
							}

						} else {
							System.out.println("socket closed " + socket);
							socket.getInputStream().close();
							// socket.getOutputStream().close();
							socket.close();
						}
					} catch (Exception e) {

						System.out
								.println("Something happened while accepting client in Server class\n");
						e.printStackTrace();
						break;
					}
				}

			}
		}).start();

	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}

	public DataInputStream getStreamIn() {
		return streamIn;
	}

	public void setStreamIn(DataInputStream streamIn) {
		this.streamIn = streamIn;
	}

	public DataOutputStream getStreamOut() {
		return streamOut;
	}

	public void setStreamOut(DataOutputStream streamOut) {
		this.streamOut = streamOut;
	}

	public Client getClient(String name) {
		// TODO Auto-generated method stub
		return clientList.get(name);
	}

	public void sendMessage(String msg, Client client) {

		DataOutputStream streamOut = client.getStreamOut();
		System.out.println(client.getSocket());
		try {
			System.out.println("sending message");

			streamOut.writeUTF(msg);
			streamOut.flush();
			System.out.println("message sent");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("error while sending message");
		}

	}

	public void remove(Client client) {
		// TODO Auto-generated method stub
		try {
			client.getStreamIn().close();
			client.getSocket().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		clientList.remove(client.getName());

	}

	public String getMyIp() {
		return myIp;
	}

	public void setMyIp(String myIp) {
		this.myIp = myIp;
	}

	public void addClient(Client client) {

		clientList.put(client.getName(), client);

	}

	public List<String> getAllIp() {

		return new ArrayList<String>(clientList.keySet());

	}

	public String getMySystemName() {
		return mySystemName;
	}

	public void setMySystemName(String mySystemName) {
		this.mySystemName = mySystemName;
	}

	public ServerSocket getFileServer() {
		return fileServer;
	}

	public void setFileServer(ServerSocket fileServer) {
		this.fileServer = fileServer;
	}

	public ServerSocket getScreenShareServer() {
		return screenShareServer;
	}

	public void setScreenShareServer(ServerSocket screenShareServer) {
		this.screenShareServer = screenShareServer;
	}
}
