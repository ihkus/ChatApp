package ui;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;

public class Calender extends JFrame{

	JComboBox<String> month,year;
	String months[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	String[] years={"2015"};
	String[] days={"Mo","Tu","We","Th","Fr","Sa","Su"};
	JButton daysNameButton[];
	JPanel panel;
	JButton[] daysButton;
	Calender()
	{
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		panel=new JPanel();
		panel.setLayout(new MigLayout("","2[]0[]0[]0[]0[]0[]0[]2",""));
		month=new JComboBox<String>(months);
		year=new JComboBox<String>(years);
		daysNameButton=new JButton[7];
		panel.add(month,"span 3");
		panel.add(year,"span 4,wrap");
		daysButton=new JButton[37];
		setUndecorated(true);
		setBackground(Color.white);
		for(int i=0;i<7;i++)
		{
			daysNameButton[i]=new JButton(days[i]);
			daysNameButton[i].setMargin(new Insets(3,3,3,3));
			daysNameButton[i].setBorderPainted(false);
			daysNameButton[i].setContentAreaFilled(false);
			if(i==6)
			panel.add(daysNameButton[i],"wrap");
			else
				panel.add(daysNameButton[i]);
				
		}
		for(int i=0;i<37;i++)
		{
			daysButton[i]=new JButton("00");
			daysButton[i].setMargin(new Insets(3, 3, 3, 3));
			daysButton[i].setContentAreaFilled(false);
			daysButton[i].setBorderPainted(false);
			if(i%7==6)
			panel.add(daysButton[i],"wrap");
			else
				panel.add(daysButton[i]);
		}
		panel.setBackground(Color.white);
		panel.setBorder(BorderFactory.createLineBorder(Color.black,2, true));
		add(panel);
		//setPreferredSize(new Dimension(100, 100));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		validate();
		setVisible(true);
		
		month.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				int m=month.getSelectedIndex()+1;
				String y=(String)year.getSelectedItem();
				
				
				
				Calendar cal=Calendar.getInstance();
				
				String input_date="01/"+m+"/"+y;
				  SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
				  try {
					Date dt1=format1.parse(input_date);
					cal.setTime(dt1);
					System.out.println(cal.get(Calendar.DAY_OF_MONTH));
					System.out.println(cal.get(Calendar.YEAR));
					System.out.println(getDay(cal.get(Calendar.DAY_OF_WEEK)));
					int max=cal.getActualMaximum(Calendar.DAY_OF_MONTH); 
					
					fillCal(getDay(cal.get(Calendar.DAY_OF_WEEK)),max);
					revalidate();
					repaint();
					
					
					
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH);
		this.year.setSelectedItem(""+year);
		this.month.setSelectedIndex(month);
		
		
	}
			
	public void fillCal(int start,int max)
	{
		
		int count=1;
		
		for(int i=0;i<start;i++)
			daysButton[i].setText("");
		
		for(int i=start;i<start+max;i++)
		{
			if(count<10)
			{
			daysButton[i].setText(""+count);
			}
			else
			{
				daysButton[i].setText(""+count);
			}
			count++;
		}
		for(int i=start+max;i<37;i++)
			daysButton[i].setText("");
		
	}
	
	public int getDay(int day)
	{
		
		day=day-2;
		if(day<0)
			day=6;
		
		return day;
		
	}
			public static void main(String[] s)
			{
				
				
				new Calender();
			}
	
	
	
}
