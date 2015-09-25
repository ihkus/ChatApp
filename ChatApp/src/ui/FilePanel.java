package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import net.miginfocom.swing.MigLayout;

public class FilePanel extends JPanel {

	private JLabel dateTime;
	private JLabel fileName;
	private JLabel status;
	private JLabel sender;
	private FilePanel me;
	private JButton download;

	
	private JProgressBar bar;
	private long max;
	private long size;
	private String fileLocation;
	private int index = -1;

	FilePanel(final String fullFilePath, final String filename,
			String dateTime, String stat, final String sender, int index, String fileLoc) {

		this.index = index;
		this.fileLocation=fileLoc;
		setLayout(new MigLayout("", "[]push[right][right][right]", "2[]2"));
		download = new JButton("Download");

		me = this;
		this.dateTime = new JLabel(dateTime);
		this.fileName = new JLabel(filename);
		this.status = new JLabel(stat + " |");
		this.sender = new JLabel(sender + " |");
		bar = new JProgressBar(0, 100);

		// UIManager.put("ProgressBar.background", Color.ORANGE);

		setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
		bar.setForeground(Color.red);

		add(this.fileName, "span 2");
		add(this.sender);
		add(this.status);
		add(this.dateTime, "wrap,gapy 0");
		setBackground(Color.white);

		if (stat.equals("Pending")) {

			download.setText("Download");
		} else if (stat.equals("Done")) {

			setBackground(new Color(0x80, 0xFF, 0x80));
			setBorder(BorderFactory.createLineBorder(
					new Color(0x00, 0xC0, 0x00), 2, true));
			download.setText("Re-Download");

		} else if (stat.equals("Failed")) {

			setBorder(BorderFactory.createLineBorder(Color.red, 2, true));
			setBackground(new Color(0xFF, 0x80, 0x80));
			download.setText("Retry");

		}

		add(download, "span 5,align right");

		download.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						download.setText("please wait");
						download.setEnabled(false);
						
						revalidate();
						repaint();
						LetsStart.validate();
						setBorder(BorderFactory.createLineBorder(Color.orange,
								2, true));
						setBackground(new Color(0xFF, 0xFF, 0x80));
						remove(download);
						status.setText("In progress |");
						add(bar, "span 5,growx,gapy 5");
						revalidate();
						LetsStart.validate();
						MainFrame.sendFileRequest(fullFilePath, sender, me);
					}
				}).start();
			}
		});
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
				me.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(fileLocation.length()!=0)
				me.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
				try {
					Runtime.getRuntime().exec("explorer.exe /select , F:/hhello");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(fileLocation.length()!=0)
				{
					
					try {
						Desktop.getDesktop().open(new File(fileLocation));
						//Runtime.getRuntime().exec("explorer.exe /select,"+fileLocation+"/"+filename);
						System.out.println(fileLocation+"/"+filename);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				
			}
		});
	}

	public void addValue(int bytesRead) {
		size += bytesRead;

		bar.setValue((int) (((double) size / max) * 100));
		if (bar.getValue() == 100) {

			setBackground(new Color(0x80, 0xFF, 0x80));
			setBorder(BorderFactory.createLineBorder(
					new Color(0x00, 0xC0, 0x00), 2, true));
			status.setText("Done |");
			remove(bar);
			size = 0;
			bar.setValue(0);
			download.setEnabled(true);
			download.setText("Re-download");
			add(download, "span 5,align right");
			MainFrame.updateFileStatusinFile(index, "Done", new Date());
			LetsStart.validate();
		}
	}

	public void errorOccured() {
		download.setText("Retry");
		size = 0;
		bar.setValue(0);
		status.setText("Failed |");
		download.setEnabled(true);
		remove(bar);
		add(download, "span 5,align right");
		setBorder(BorderFactory.createLineBorder(Color.red, 2, true));
		setBackground(new Color(0xFF, 0x80, 0x80));
		MainFrame.updateFileStatusinFile(index, "Failed", new Date());
	}
	public void setMax(long val) {
		max = val;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
}
