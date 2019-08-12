package front;

/**
 *  
 * This program is an attempt to create an SQL database and connect it to a front end application
 *  
 *  @author Darrel2018
 *  @version 0.5
 *  @since 8/3/2019
 */

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import dataTable.DataTable;


public class FRONT extends Canvas {
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	
	private static String title = "SQL Database";
	private static int width = 800, height = 600;
	
	private JPanel prevPanel, mPanel, sidePanel;
	private DataTable table;
	
	// Constructor
	public FRONT(){
		frame = new JFrame();
		table = new DataTable();
		frame.setLayout(null);
		
		createView();
	}
	
	// adds GUI components to JFrame
	public void createView(){
		frame.add(createSidePanel());
	}
	
	// creates main panel component
	private JPanel createMainPanel(){
		mPanel = new JPanel();
		
		mPanel.setLayout(null);
		mPanel.setSize(new Dimension(width, height));
		mPanel.setBackground(setColor(208, 208, 208));
		
		mPanel.add(table.getDataTable());
		
		return mPanel;
	}
	
	// creates side panel component
	private JPanel createSidePanel(){
		sidePanel = new JPanel();
		JSeparator sep = new JSeparator();
		
		
		sidePanel.setLayout(null);
		sidePanel.setSize(new Dimension(570, height));
		sidePanel.setLocation(570, 0);
		sidePanel.setBackground(setColor(0, 0, 153));
		
		sep.setBounds(10, 70, 205, 10);
		sep.setForeground(setColor(255, 153, 0));
		sep.setBackground(setColor(255, 153, 0));
		
		JPanel viewButton = createSidePanelButton(0, 120, setColor(15, 15, 183), "res\\images\\viewdatabase.png", "View Database", 100);
		JPanel searchButton = createSidePanelButton(0, 160, setColor(15, 15, 183), "res\\images\\SearchEntry.png", "Search For Entry", 120);
		JPanel addButton = createSidePanelButton(0, 200, setColor(15, 15, 183), "res\\images\\addEntry.png", "Add Entry", 100);
		JPanel editButton = createSidePanelButton(0, 240, setColor(15, 15, 183), "res\\images\\editEntry.png", "Edit Entry", 100);
		JPanel deleteButton = createSidePanelButton(0, 280, setColor(15, 15, 183), "res\\images\\deleteEntry.png", "Delete Entry", 100);
		JPanel exitButton = createSidePanelButton(0, 480, setColor(15, 15, 183), "res\\images\\Exit.png", "Exit", 100);
		
		addListeners(viewButton, 0);
		addListeners(searchButton, 1);
		addListeners(addButton, 2);
		addListeners(editButton, 3);
		addListeners(deleteButton, 4);
		addListeners(exitButton, 5);
		
		sidePanel.add(sep);
		sidePanel.add(createTextLabel("SQL-Database", new Font("Segoe UI", 0, 24), setColor(255, 153, 0), 40, 7, 150));
		sidePanel.add(viewButton);
		sidePanel.add(searchButton);
		sidePanel.add(addButton);
		sidePanel.add(editButton);
		sidePanel.add(deleteButton);
		sidePanel.add(exitButton);
		
		return sidePanel;
	}
	
	// adds listeners to button panels
	private void addListeners(JPanel panel, int button){
		
		panel.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e){
				
				if(prevPanel == null) prevPanel = new JPanel();
				prevPanel.setBackground(setColor(15, 15, 183));
				prevPanel = panel;
				panel.setBackground(setColor(51, 51, 183));
				
				pressedButton(button);
			}
		});
	}
	
	// checks which button was pressed
	private void pressedButton(int button){
		if(button == 0){
			System.out.println("Pressed View Database");
			frame.add(createMainPanel());
			table.viewData();
		}
		else if(button == 1){
			System.out.println("Pressed SearchEntry");
			mPanel.removeAll();
			mPanel.setBackground(setColor(255, 255, 255));
			frame.repaint();
		}
		else if(button == 2){
			System.out.println("Pressed addEntry");
		}
		else if(button == 3){
			System.out.println("Pressed editEntry");
		}
		else if(button == 4){
			System.out.println("Pressed deleteEntry");
		}
		else if(button == 5){
			System.err.println("Exiting...");
			System.exit(0);
		}
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
