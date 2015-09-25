package ui;

import java.awt.Color;
import java.io.BufferedReader;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.ComponentView;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.View;
public class ChatBox extends JTextPane{

	StyleContext context = new StyleContext();
    StyledDocument doc = new DefaultStyledDocument(context);
    Style labelStyle = context.getStyle(StyleContext.DEFAULT_STYLE);
	Style bold,black,blue;
	
	ChatBox()
	{
		
		
	    setDocument(doc);
		setEditable(false);
		bold = addStyle("bold", null);
		black = addStyle("black", null);
		blue = addStyle("blue", null);
		StyleConstants.setForeground(black, Color.black);
		StyleConstants.setBold(bold,true);
        StyleConstants.setForeground(bold, Color.black);
        StyleConstants.setBold(blue, true);        
        
	     
	}
	
	public void startTransfer()
	{
		
		
	}
	public  void addMessage(final String message,final String sender)
	{
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try { 
					doc.insertString(doc.getLength(), sender+" : ",bold);
					doc.insertString(doc.getLength(), message+"\n",black); 
					
					
					
					
					
				}
		        catch (BadLocationException e){
		        	
		        	
		        }
				
			}
		});

		
	}
	


	
	public void populateChatBox(BufferedReader fileReader) {
		// TODO Auto-generated method stub
		
		setText("");
		String line="";
		try{
		while((line=fileReader.readLine())!=null)
		{
			
			String split[]=line.split("~!@#");
			
			try { 
				doc.insertString(doc.getLength(), split[0]+" : ",bold);
				doc.insertString(doc.getLength(), split[2]+"\n",black); 
				
				
				
				
				
			}
	        catch (BadLocationException e){
	        	
	        	
	        }
			
			
		}
		
		}
		catch(Exception e)
		{
			System.out.println("error occured while reading file");
			e.printStackTrace();
			
		}
	}



	
	

}
class WrapComponentView extends ComponentView {
    public WrapComponentView(Element elem) {
        super(elem);
    }

    @Override
    public float getMinimumSpan(int axis) {
        switch (axis) {
        case View.X_AXIS:
            return 0;
        case View.Y_AXIS:
            return super.getMinimumSpan(axis);
        default:
            throw new IllegalArgumentException("Invalid axis: " + axis);
        }
    }
}
