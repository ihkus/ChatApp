package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

import net.miginfocom.swing.MigLayout;
import client.Client;
import client.fileTransfer.Send;

public class ChatPanel extends JPanel {

	private ChatBox chatBox;
	private JTextArea textArea;
	private JScrollPane scroll;
	private JPanel extra;
	private JButton screen,history;

	public ChatPanel() {

		this.setLayout(new MigLayout("fill","0[]0",""));

		this.setPreferredSize(new Dimension(500, 450));

		chatBox = new ChatBox();
		history=new JButton("History");
		extra = new JPanel(new MigLayout("","0push[]0",""));
		extra.setBackground(Color.white);
		//extra.setPreferredSize(new Dimension(500, 35));

		textArea = new JTextArea();
		//textArea.setPreferredSize(new Dimension(500, 50));
		textArea.setLineWrap(true);
		//textArea.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.black));
		textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));

		
		scroll = new JScrollPane(chatBox);
		
		scroll.setBackground(Color.white);
		setBackground(Color.white);
		//scroll.setPreferredSize(new Dimension(500, 365));
		scroll.setViewportView(chatBox);
		extra.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.black));
		scroll.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.black));
		//scroll.setBorder(BorderFactory.createLineBorder(Color.black,1,true));
		final ImageIcon icon = new ImageIcon(
				ClassLoader.getSystemResource("screen.jpg"));
		screen = new JButton() {

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(icon.getImage(), 0, 0, 35, 35, null);
			}
		};
		//screen.setPreferredSize(new Dimension(35, 35));
		screen.setMargin(new Insets(0, 0, 0, 0));

		screen.setBorderPainted(false);
		screen.setContentAreaFilled(false);
		screen.setToolTipText("request screen access");

		screen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainFrame.requestScreen();
			}
		});
		history.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Calender calender=new Calender();
				calender.setLocationRelativeTo(history);
			}
		});
		extra.add(history,"gapy 1 1");
		this.add(scroll,"height :80%:,dock north,gapy 0 0,wrap");
		this.add(extra,"height :5%:,dock north,wrap,gapy 0 0");
		this.add(textArea,"height :15%:,dock north,wrap,gapy 0 0");

		addListeners();

	}

	void addListeners() {

		textArea.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent evt) {

				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

					textArea.setText("");
				}

			}

			@Override
			public void keyPressed(KeyEvent evt) {

				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

					if (textArea.getText().length() > 0) {
						System.out.println("Sending message ");
						String msg = textArea.getText();
						Client client = getSelectedRow();

						MainFrame.sendMessage(msg, client);

						validate();

					}
					textArea.setText("");
				}

			}
		});

		TransferHandler handler = new TransferHandler() {

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
					data = (List<File>) t
							.getTransferData(DataFlavor.javaFileListFlavor);
				} catch (Exception e) {
					return false;
				}

				for (File file : data) {
					System.out.println(file.getAbsolutePath());

					try {
						System.out.println("sending file----->");
						// Main.fileServer.sendFile(name+"file",file.getAbsolutePath());
						
						System.out.println("panel initiated");
						// MainFrame.addSendFileStatusPanel(filestatus);
						System.out.println("panel added");

						Send send = new Send(file.getAbsolutePath(),
								file.getName(), getSelectedRow(),
								"newFile");
						// send.start();
						System.out.println("thread started");
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

		chatBox.setDragEnabled(true);
		chatBox.setTransferHandler(handler);

	}

	public Client getSelectedRow() {
		String name = MainFrame.getSelectedRow();

		Client client = MainFrame.getClient(name);
		return client;

	}

	public void messageRecieved(String msg, Client client) {
		// TODO Auto-generated method stub
		chatBox.addMessage(msg, client.getName());
	}

	public void messageSent(String msg, String ip) {

		chatBox.addMessage(msg, ip);

	}

	// will take scrollbar on chatbox to bottom
	public void updateScrollBar() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				System.out.println("asfdasdfasdfsdaf");
				int height = (int) chatBox.getHeight();
				Rectangle rect = new Rectangle(0, height, chatBox.getWidth(),
						50);
				chatBox.scrollRectToVisible(rect);
				validate();
				// TODO Auto-generated method stub

			}
		});
	}

	public void populateChatBox(BufferedReader fileReader) {
		// TODO Auto-generated method stub

		chatBox.populateChatBox(fileReader);

	}


	

	public JButton getScreen() {
		return screen;
	}

	public void setScreen(JButton screen) {
		this.screen = screen;
	}

}
