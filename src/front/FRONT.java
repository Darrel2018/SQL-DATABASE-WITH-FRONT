package front;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 *  
 * This program is an attempt to create an SQL database and connect it to a front end application
 *  
 *  @author Darrel2018
 *  @version 0.1
 *  @since 8/3/2019
 */

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class FRONT extends Canvas {
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private JPanel sidePanel, mPanel;
	
	private static String title = "SQL Database";
	private static int width = 800, height = 600;
	
	public FRONT(){
		frame = new JFrame();
		
		createView();
	}
	
	public void createView(){
		frame.setLayout(null);
		frame.add(createSidePanel());
		frame.add(createMainPanel());
	}
	
	private JPanel createMainPanel(){
		mPanel = new JPanel();
		
		mPanel.setLayout(null);
		mPanel.setSize(new Dimension(width, height));
		mPanel.setBackground(setColor(208, 208, 208));
		
		return mPanel;
	}
	
	private JPanel createSidePanel(){
		JPanel sidePanel = new JPanel();
		JSeparator sep = new JSeparator();
		
		
		sidePanel.setLayout(null);
		sidePanel.setSize(new Dimension(width - 570, height));
		sidePanel.setLocation(570, 0);
		sidePanel.setBackground(setColor(0, 0, 153));
		
		sep.setBounds(10, 70, 205, 10);
		
		sidePanel.add(sep);
		sidePanel.add(createTextLabel("SQL-Database", new Font("Segoe UI", 0, 24), setColor(255, 255, 255), 40, 7, 150));
		
		return sidePanel;
	}
	
	private JLabel createTextLabel(String text, Font font, Color color, int x, int y, int textLength){
		JLabel label = new JLabel();
		
		label.setText(text);
		label.setFont(font);
		label.setBounds(x, y, textLength, 50);
		label.setForeground(color);
		
		return label;
	}
	
	public Color setColor(int r, int g, int b){
		Color color = new Color(r, g, b);
		return color;
	}
	
	public static void main(String[] args){
		
		FRONT front = new FRONT();
		
		front.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		front.frame.add(front);
		front.frame.setSize(width, height);
		front.frame.setLocationRelativeTo(null);
		front.frame.setResizable(false);
		front.frame.setTitle(title);
		front.frame.setUndecorated(true);
		
		front.frame.setVisible(true);
	}
}
