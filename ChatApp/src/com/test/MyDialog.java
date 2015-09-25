package com.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import ui.LetsStart;
import ui.MainFrame;
import client.Client;

public class MyDialog extends JFrame {

	String serverName;
	String msg;
	JLabel label;
	JLabel msgLabel;
	JTextArea text;

	MyDialog(final String serverName, String msg, int num)
			throws InterruptedException {

		this.serverName = serverName;
		this.msg = msg;
		label = new JLabel();

		msgLabel = new JLabel(msg);
		text = new JTextArea(2, 250);
		this.setUndecorated(true);

		JPanel buttonPanel = new JPanel();
		JPanel msgPanel = new JPanel();

		final JButton helloButton;
		JButton exitButton;
		msgPanel.setLayout(new BorderLayout());
		JButton namePanel = new JButton();
		namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		helloButton = new JButton("+");
		exitButton = new JButton("x");
		namePanel.setMargin(new Insets(0, 0, 0, 0));
		
		namePanel.setBorderPainted(false);
		namePanel.setContentAreaFilled(false);
		
		helloButton.setMargin(new Insets(0, 0, 0, 0));
		
		helloButton.setBorderPainted(false);
		helloButton.setContentAreaFilled(false);
		exitButton.setMargin(new Insets(0, 0, 0, 0));
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);

		
		namePanel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				helloButton.doClick();
			}
		});
		// Add the buttons to the JPanel.
		exitButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				Main.notificationCount--;
			}
		});
		buttonPanel.add(helloButton);
		buttonPanel.add(exitButton);
		helloButton.setForeground(Color.white);
		helloButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						MainFrame.selectUser(serverName);

						LetsStart.setVisible(true);
						LetsStart.setExtendedState();

						dispose();

					}
				});
			}

		});

		text.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent evt) {

				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

					text.setText("");
					dispose();
				}

			}

			@Override
			public void keyPressed(KeyEvent evt) {

				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

					if (text.getText().length() > 0) {
						System.out.println("Sending message ");
						String msg = text.getText();
						Client client = MainFrame.getClient(MainFrame
								.getSelectedRow());

						MainFrame.sendMessage(msg, client);

						validate();

					}
					text.setText("");
				}

			}
		});

		exitButton.setForeground(Color.white);
		namePanel.setBackground(Color.DARK_GRAY);
		label.setForeground(Color.white);
		this.setSize(250, 100);
		this.setLayout(new BorderLayout());
		label.setText(serverName);

		namePanel.add(label);
		JPanel title = new JPanel();
		title.setLayout(new BorderLayout());
		title.add(namePanel, BorderLayout.WEST);

		title.add(buttonPanel, BorderLayout.EAST);
		buttonPanel.setBackground(Color.DARK_GRAY);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		text.setBorder(border);

		title.setBackground(Color.darkGray);
		this.add(title, BorderLayout.NORTH);
		msgLabel.setPreferredSize(new Dimension(250, 50));
		msgPanel.add(msgLabel, BorderLayout.NORTH);
		msgPanel.setBackground(Color.gray);
		msgPanel.add(text, BorderLayout.SOUTH);
		msgPanel.setBackground(Color.gray);
		msgLabel.setBackground(Color.gray);
		this.add(msgPanel, BorderLayout.CENTER);
		this.setAlwaysOnTop(true);
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = (int) rect.getMaxX() - this.getWidth();
		int y = (int) rect.getMaxY() - this.getHeight();
		this.setLocation(x, y - 40 - (num * (this.getHeight() + 10)));
		this.setVisible(true);

	}

	public static void main(String arg[]) throws InterruptedException {

	}
}
