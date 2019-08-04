package front;

/**
 *  
 * This program is an attempt to create an SQL database and connect it to a front end application
 *  
 *  @author Darrel2018
 *  @version 0.2
 *  @since 8/3/2019
 */

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;

public class FRONT extends Canvas {
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	
	private static String title = "SQL Database";
	private static int width = 800, height = 600;
	
	// Constructor
	public FRONT(){
		frame = new JFrame();
		frame.setLayout(null);
		
		createView();
	}
	
	// adds GUI components to JFrame
	public void createView(){
		frame.add(createSidePanel());
		frame.add(createMainPanel());
	}
	
	// creates main panel component
	private JPanel createMainPanel(){
		JPanel panel = new JPanel();
		
		panel.setLayout(null);
		panel.setSize(new Dimension(width, height));
		panel.setBackground(setColor(208, 208, 208));
		
		panel.add(createDataTable());
		
		return panel;
	}
	
	private JScrollPane createDataTable(){
		
		String[] columnNames = {"Name", "AGE", "Gender", "Married"};
		String[][] data = {
				
				{"Jack", "23", "Male", "No"},
				{"Emily", "20", "Female", "Yes"},
				{"Mack", "32", "Male", "Yes"}
				
		};
		
		JTable table = new JTable(data, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(400, 200));
		table.setFillsViewportHeight(true);
		
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(0, 0, 575, 601);
		
		return pane;
	}
	
	// creates side panel component
	private JPanel createSidePanel(){
		JPanel panel = new JPanel();
		JSeparator sep = new JSeparator();
		
		
		panel.setLayout(null);
		panel.setSize(new Dimension(570, height));
		panel.setLocation(570, 0);
		panel.setBackground(setColor(0, 0, 153));
		
		sep.setBounds(10, 70, 205, 10);
		sep.setForeground(setColor(255, 153, 0));
		sep.setBackground(setColor(255, 153, 0));
		
		panel.add(sep);
		panel.add(createTextLabel("SQL-Database", new Font("Segoe UI", 0, 24), setColor(255, 153, 0), 40, 7, 150));
		panel.add(createSidePanelButton(0, 120, setColor(15, 15, 183), "res\\images\\viewdatabase.png", "View Database", 100));
		panel.add(createSidePanelButton(0, 160, setColor(15, 15, 183), "res\\images\\addEntry.png", "Add Entry", 100));
		panel.add(createSidePanelButton(0, 200, setColor(15, 15, 183), "res\\images\\editEntry.png", "Edit Entry", 100));
		panel.add(createSidePanelButton(0, 240, setColor(15, 15, 183), "res\\images\\deleteEntry.png", "Delete Entry", 100));
		
		return panel;
	}
	
	// creates side panel buttons
	private JPanel createSidePanelButton(int x, int y, Color color, String imgPath, String text, int textSize){
		JPanel panel = new JPanel();
		
		panel.setLayout(null);
		panel.setBounds(x, y, 230, 40);
		panel.setBackground(color);
		
		JLabel imgLabel = new JLabel();
		imgLabel.setIcon(new ImageIcon(imgPath));
		imgLabel.setBounds(15, -5, 50, 50);
		
		panel.add(createTextLabel(text, new Font("Segoe UI", 0, 15), setColor(255, 153, 0), 60, -7, textSize));
		panel.add(imgLabel);
		
		return panel;
	}
	
	// creates a label component
	private JLabel createTextLabel(String text, Font font, Color color, int x, int y, int textLength){
		JLabel label = new JLabel();
		
		label.setText(text);
		label.setFont(font);
		label.setBounds(x, y, textLength, 50);
		label.setForeground(color);
		
		return label;
	}
	
	// allows for easy use of RGB color scheme
	public Color setColor(int r, int g, int b){
		Color color = new Color(r, g, b);
		return color;
	}
	
	//----====MAIN====----
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
