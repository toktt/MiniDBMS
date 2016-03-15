package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import parser.*;

import java.util.StringTokenizer;


public class View implements ActionListener 
{
	JFrame mainframe = new JFrame("DBMS");
	JButton login_btn = new JButton("“o“ü");
	JPanel panel = new JPanel(); 

	JTextArea input_text = new JTextArea();
	String yee;
	
	public void mainframe() 
	{
		panel.setLayout(new GridLayout(2,1));
		panel.add(input_text);
		panel.add(login_btn);
		login_btn.addActionListener(this);
		mainframe.setSize(800, 600);
		mainframe.setLocationRelativeTo(null);
		mainframe.add(panel);
		mainframe.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		  if(e.getSource() == login_btn)
		  {
			  
			  yee = new String(input_text.getText());
			  
			  try {
				  
				new Parser().test(yee);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 mainframe.setVisible(true);    
		  }
	}  
	
}