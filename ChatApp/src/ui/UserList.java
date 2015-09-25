package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import client.Client;

public class UserList extends JTable{

	
	DefaultTableModel tableModel;
	public UserList()
	{
		//setPreferredSize(new Dimension(200,447));
		
		setRowHeight(25);
		
		tableModel=new DefaultTableModel(new String[]{null,null},0){
			
			
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
			
			
		};
		
		tableModel.setColumnIdentifiers(new String[]{null,null});
		setFont(new Font("Serif",Font.PLAIN, 15));
		
		
		setBackground(Color.white);
		setShowGrid(false);
		setShowHorizontalLines(true);
		setTableHeader(null);
		setModel(tableModel);
		getColumnModel().getColumn(0).setPreferredWidth(170);
		getColumnModel().getColumn(1).setPreferredWidth(30);
		
		setSelectionBackground(Color.gray);
		//setIntercellSpacing(new Dimension(10,5));
       
		
		DefaultListSelectionModel selectionModel=new DefaultListSelectionModel();
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		setSelectionModel(selectionModel);
		
		getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent evt) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						tableModel.setValueAt("", getSelectedRow(),1);
						MainFrame.setFile(getSelected()[0]);
					}
				});
				
				
			}
		});
		
		
	}
	
	
	protected void configureEnclosingScrollPane()
	{
		
		setTableHeader(null);
		
	}
	void addUser(Client client)
	{
		
		tableModel.addRow(new String[]{client.getName()});
		
		
	}
	
	public String[] getSelected()
	{
		
		int rownum=getSelectedRow();
		int colsize=getColumnCount();
		String data[]=new String[colsize];
		for(int i=0;i<colsize;i++)
		{
		data[i]=(String)tableModel.getValueAt(rownum,i);
		
		}
		
		return data;
	}


	public void remove(Client client) {
		// TODO Auto-generated method stub
		int rownum=getRowCount();
		
		String data="";
		int index=-1;
		System.out.println(rownum);
		for(int i=0;i<rownum;i++)
		{
		data=(String)tableModel.getValueAt(i,0);
		if(data.equals(client.getName()))
		{
			
			index=i;
			break;
			
		}
		}
		
		tableModel.removeRow(index);
	
		
	}
	private Border paddingBorder = BorderFactory.createEmptyBorder(2, 3, 2, 3);

	@Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component comp = super.prepareRenderer(renderer, row, column);

        if (JComponent.class.isInstance(comp)){
            ((JComponent)comp).setBorder(paddingBorder);
        }

        return comp;
    }
	

	public void incrementUnreadCounter(String name) {
		
		
int rownum=getRowCount();
		
		String data="";
		
		System.out.println(rownum);
		for(int i=0;i<rownum;i++)
		{
		data=(String)tableModel.getValueAt(i,0);
		if(data.equals(name))
		{
			int count=0;
			if(tableModel.getValueAt(i, 1)!=null&&!tableModel.getValueAt(i, 1).equals(""))
			{
				count=Integer.parseInt((String)tableModel.getValueAt(i, 1));
				
			}
			count++;
			tableModel.setValueAt(count+"", i, 1);
			
			break;
			
		}
		}
		
	}


	public void selectUser(String name) {
		
		
		String data="";
		int rownum=getRowCount();
		
		for(int i=0;i<rownum;i++)
		{
		data=(String)tableModel.getValueAt(i,0);
		if(data.equals(name))
		{
			setRowSelectionInterval(i, i);
			
			break;
			
		}
		}
		
		
	}
}


