package ui;

import java.awt.AWTException;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;

import net.miginfocom.swing.MigLayout;
import server.Server;
import server.fileTransfer.Recieve;
import client.Client;
import client.ScreenShareClient;

import com.test.NotificationPanel;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UserListPanel userListPanel;
	private static ChatPanel chatPanel;
	private static JPanel rightPanel;
	private static DownloadPanel downloadPanel;
	private static UploadPanel uploadPanel;
	private static Server server;
	private static boolean closing = false;
	private SystemTray tray;
	private TrayIcon trayIcon;
	private static boolean closed;
	private static boolean active = true;
	public static String myName, mySystemName;
	static File chatFile;
	static BufferedReader fileReader;
	static PrintWriter fileWriter;
	private MenuBar menuBar;
	public static int notificationCount;
	JPanel panel;
	public static int id;
	public static String myIp;
	JPanel toolBar;
	JButton chat, downloads, shared, screenShare;
	private static ArrayList<String> downloadList;
	CardLayout cards;

	MainFrame() throws BadLocationException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panel = new JPanel();

		setLocation(100, 100);
		panel.setLayout(new MigLayout("", "0[]2[]0", ""));
		userListPanel = new UserListPanel();
		chatPanel = new ChatPanel();
		downloadPanel = new DownloadPanel();
		uploadPanel = new UploadPanel();
		rightPanel = new JPanel(new CardLayout());

		JScrollPane downloadScroll = new JScrollPane(downloadPanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		downloadScroll.setPreferredSize(new Dimension(500, 450));
		downloadScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		JScrollPane uploadScroll = new JScrollPane(uploadPanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		uploadScroll.setPreferredSize(new Dimension(500, 450));
		uploadScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		rightPanel.add(chatPanel, "chat");
		rightPanel.add(downloadScroll, "downloads");
		rightPanel.add(uploadScroll, "upload");
		toolBar = new JPanel(new MigLayout("insets 0", "0[]0[]0[]0[]0", "0[]0"));
		chat = new JButton("chat");
		downloads = new JButton("downloads");
		shared = new JButton("Shared");
		screenShare = new JButton("Screen Share");
		screenShare.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				requestScreen();

			}
		});
		toolBar.add(chat);
		toolBar.add(downloads);
		toolBar.add(shared);
		toolBar.add(screenShare);
		this.setResizable(false);
		cards = (CardLayout) rightPanel.getLayout();

		chat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				cards.show(rightPanel, "chat");

			}
		});

		downloads.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				cards.show(rightPanel, "downloads");

			}
		});

		shared.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				cards.show(rightPanel, "upload");

			}
		});

		// toolBar.setPreferredSize(new Dimension(700,50));
		panel.setBackground(Color.white);
		setIconImage(new ImageIcon(ClassLoader.getSystemResource("icon1.png"))
				.getImage());

		panel.add(toolBar, "span 2,wrap");
		panel.add(userListPanel, "gapy 1 1");
		panel.add(rightPanel, "gapy 1 1");

		panel.validate();
		menuBar = new MenuBar();
		Menu menu = new Menu("Settings");

		MenuItem menuItem = new MenuItem("File Location");
		final Preferences prefs = Preferences
				.userNodeForPackage(MainFrame.class);
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int result;

				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Select Folder");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				//
				// disable the "All files" option.
				//
				chooser.setAcceptAllFileFilterUsed(false);
				//

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					System.out.println("getCurrentDirectory(): "
							+ chooser.getCurrentDirectory());
					System.out.println("getSelectedFile() : "
							+ chooser.getSelectedFile());

					prefs.put("downloadLocation", chooser.getSelectedFile()
							.getAbsolutePath());
				} else {
					System.out.println("No Selection ");
				}

			}
		});

		menu.add(menuItem);
		menuBar.add(menu);
		setMenuBar(menuBar);
		this.add(panel);
		this.pack();

		String name = prefs.get("name", "default");
		if (name.equals("default")) {

			name = JOptionPane.showInputDialog("Enter user name : ");
			prefs.put("name", name);

		}
		myName = name;

		startServer();

		try {
			systemTray();
			windowListener();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// downloadPanel.addFile("somthing", "file name", "today", "pending",
		// "sukhdeep singh", "ip");
		loadDownloadListFromFile(new Date());

		this.setVisible(true);

	}

	public static void addUser(Client client) {

		userListPanel.addUser(client);

	}

	public void messageRecieved(final String msg, final Client client) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				if (closed) {
					try {
						new NotificationPanel(client.getName(), msg,
								notificationCount++);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (!active) {

					new Thread(new Runnable() {

						@Override
						public void run() {

							String s[] = { "icon1.png", "icon2.png" };
							int count = 0;
							while (!active) {

								try {
									Thread.sleep(300);

									setIconImage(new ImageIcon(ClassLoader
											.getSystemResource(s[count++]))
											.getImage());
									count %= 2;
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							setIconImage(new ImageIcon(ClassLoader
									.getSystemResource(s[0])).getImage());

						}
					}).start();

				}

				if (getSelectedRow().equals(client.getName())) {
					chatPanel.messageRecieved(msg, client);
					chatPanel.updateScrollBar();
					fileWriter.println(client.getName() + "~!@#"
							+ client.getSystemName() + "~!@#" + msg);
					fileWriter.flush();
				} else {

					String fileName = "";
					fileName = myName + client.getName();
					String date = null;
					date = new SimpleDateFormat("yyyyMMdd").format(new Date());
					fileName += date;
					try {
						PrintWriter writer = new PrintWriter(
								new BufferedWriter(new FileWriter(fileName,
										true)));
						incrementUnreadCounter(client);
						writer.println(client.getName() + "~!@#"
								+ client.getSystemName() + "~!@#" + msg);
						writer.flush();
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		});
	}

	protected static void incrementUnreadCounter(Client client) {

		userListPanel.incrementUnreadCounter(client.getName());

	}

	public static void clientDisconnected(Client client) {

	}

	public static void sendMessage(final String msg, final Client client) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				chatPanel.messageSent(msg, myName);
				chatPanel.updateScrollBar();
				server.sendMessage(msg, client);
				fileWriter.println(myName + "~!@#" + mySystemName + "~!@#"
						+ msg);
				fileWriter.flush();

			}
		});
	}

	static void startServer() {
		if (closing)
			return;

		try {
			server = new Server(22222);
			server.start();

			Client.broadcast(server.getServer().getInetAddress()
					.getHostAddress(), 22222);

		} catch (Exception e) {
			System.out.println("Something happened");
			e.printStackTrace();

		}
	}

	public static String getSelectedRow() {
		String name = "";
		try {
			name = userListPanel.getSelectedRow();
		} catch (Exception e) {
			name = "";
		}
		return name;
	}

	public static Client getClient(String name) {
		// TODO Auto-generated method stub
		return server.getClient(name);
	}

	public static Server getServer() {
		return server;
	}

	public static void setServer(Server server) {
		MainFrame.server = server;
	}

	public static void removeUser(Client client) {
		// TODO Auto-generated method stub

		userListPanel.removeUser(client);
		server.remove(client);

	}

	public static void stop() {
		System.out.println("stopping server");
		try {

			if (!server.getServer().isClosed())
				server.getServer().close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void addClient(Client client) {

		server.addClient(client);
	}

	public static void setFile(String fileName) {

		fileName = myName + fileName;
		String date = null;
		date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		fileName += date;
		System.out.println(fileName);
		try {
			if (fileWriter != null) {
				fileWriter.flush();
				fileWriter.close();

			}

			File file = new File(fileName);
			file.createNewFile();

			fileReader = new BufferedReader(new FileReader(fileName));

			populateChatBox();

			fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(
					fileName, true)));

		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("error occured while saving or loading file");

		}

	}

	private static void populateChatBox() {
		// TODO Auto-generated method stub
		chatPanel.populateChatBox(fileReader);
	}

	public void systemTray() throws AWTException {

		System.out.println("creating instance");

		if (SystemTray.isSupported()) {
			System.out.println("system tray supported");
			tray = SystemTray.getSystemTray();

			ActionListener exitListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					System.out.println("Closing window");
					closing = true;
					try {

						List<String> ips = server.getAllIp();
						tray.remove(trayIcon);
						for (String ip : ips)
							server.getClient(ip).getSocket().close();
						server.getServer().close();
						server.getFileServer().close();
						fileWriter.flush();
						fileWriter.close();

					} catch (Exception ex) {

					}

					System.exit(0);
				}
			};
			PopupMenu popup = new PopupMenu();

			MenuItem defaultItem = new MenuItem("Open");
			defaultItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(true);
					setExtendedState(JFrame.NORMAL);
				}
			});
			popup.add(defaultItem);
			defaultItem = new MenuItem("Exit");
			defaultItem.addActionListener(exitListener);
			popup.add(defaultItem);

			trayIcon = new TrayIcon(new ImageIcon(
					ClassLoader.getSystemResource("icon1.png")).getImage(),
					"SystemTray", popup);

			trayIcon.setImageAutoSize(true);
			tray.add(trayIcon);
		} else {
			System.out.println("system tray not supported");
		}
		setVisible(true);

	}

	void windowListener() {
		setDefaultCloseOperation(HIDE_ON_CLOSE);

		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("opened");

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("iconfied");
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("deiconified");
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				active = false;
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				closed = true;
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				active = true;
				closed = false;
			}
		});

		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {

				System.out.println(isVisible());

			}
		});

	}

	public static void selectUser(String name) {
		// TODO Auto-generated method stub
		userListPanel.selectUser(name);
	}

	public static void requestScreen() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				String name = getSelectedRow();

				Client client = getClient(name);

				client.getIp();
				try {
					Socket socket = new Socket(client.getIp(), 44444);

					System.out.println(socket);

					new DataOutputStream(socket.getOutputStream())
							.writeUTF(myName);

					String ack = new DataInputStream(socket.getInputStream())
							.readUTF();

					if (ack.equals("yes")) {
						ScreenShareClient scr = new ScreenShareClient(socket);
						scr.start();
					}

				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}

	public static void sendRecieveFileAck(int id) {

	}

	public static void goToDownloadLocation(int id2) {
		// TODO Auto-generated method stub

	}

	public static void uploadNewFile(String filePath, String fileName,
			String who) {

		uploadPanel.addFile(filePath, fileName, new Date().toString(), who);

	}

	public static void recievedNewFile(String info, boolean toFile) {

		// add to file history

		String[] str = info.split("::");

		String fileLocation = str[0];
		String who = str[1];
		String size = str[2];
		String fileName = str[3];
		String filePath = str[4];
		String date = str[5];
		String status = str[6];
		System.out.println(info);

		// add to downlaodPanel
		downloadPanel.addFile(filePath, fileName, date, status, who,
				downloadList.size(), fileLocation);

		if (toFile) {
			downloadList.add(info);
			updateFileStatusinFile(new Date());

		}

	}

	public static void sendFileRequest(String fileName, String sender,
			FilePanel me) {
		try {
			System.out.println(sender + " " + fileName);
			Client client = getClient(sender);
			Socket socket = new Socket(client.getIp(), 33333);
			System.out.println(socket);
			new DataOutputStream(socket.getOutputStream()).writeUTF("sendFile");
			new DataOutputStream(socket.getOutputStream()).writeUTF(fileName
					+ "::someOtherParams");
			Recieve file = new Recieve(socket, me);
			file.start();
		} catch (Exception e) {
			me.errorOccured();
			e.printStackTrace();
		}

	}

	public static void loadDownloadListFromFile(Date date) {
		BufferedReader br = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			Preferences prefs = Preferences.userNodeForPackage(MainFrame.class);
			String path = prefs.get("downloadLocation", "ChatDownloads");
			File file = new File(path + "/history/downloads/downloads"
					+ format.format(date));
			if (!file.exists()) {

				File dir = new File(path + "/history/downloads");
				dir.mkdirs();
				file.createNewFile();
			}

			br = new BufferedReader(new FileReader(file));
			downloadList = new ArrayList<String>();
			String str = null;

			while ((str = br.readLine()) != null) {
				System.out.println(str);
				recievedNewFile(str, false);
				downloadList.add(str);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void updateFileStatusinFile(int index, String status,
			final Date date) {
		String str = downloadList.get(index);
		str = str.substring(0, str.lastIndexOf("::"));
		str += "::" + status;
		System.out.println(index + " " + status);
		downloadList.set(index, str);

		new Thread(new Runnable() {
			@Override
			public void run() {
				updateFileStatusinFile(date);
			}
		}).start();
	}

	public static void updateFileStatusinFile(Date date) {

		String fileName;
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Preferences prefs = Preferences.userNodeForPackage(MainFrame.class);
		String path = prefs.get("downloadLocation", "ChatDownloads");
		File file = new File(path + "/history/downloads/downloads"
				+ format.format(date));
		try {
			BufferedWriter wr = new BufferedWriter(new FileWriter(file));

			for (String str : downloadList)
				wr.append(str + "\n");

			wr.flush();
			wr.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getAppDataDirectory() {
	    return System.getProperty("user.home") + File.separator + ".chatApp" + File.separator ;
	}
	
}
