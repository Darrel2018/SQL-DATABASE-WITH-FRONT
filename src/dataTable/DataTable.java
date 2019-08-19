package dataTable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import front.FRONT;

public class DataTable {
	
	private Object[][] data;
	private DefaultTableModel dModel;
	
	private int rowCount = 0;
	
	// creates data Table
	private JScrollPane createDataTable(){
			
		String[] columnNames = {"Name", "AGE", "Gender", "Married"};
		JTable table;
		if(data == null)data = new String[0][0];
			
		dModel = new DefaultTableModel(data, columnNames);
			
		dModel.setColumnIdentifiers(columnNames);
			
		table = new JTable(dModel);
		table.setPreferredScrollableViewportSize(new Dimension(400, 200));
		table.setFillsViewportHeight(true);
		
		
		JTableHeader head = table.getTableHeader();
		head.setBorder(BorderFactory.createLineBorder(setColor(0, 0, 153)));
		head.setBackground(setColor(0, 0, 153));
		head.setForeground(setColor(255, 153, 0));
		
		table.setShowGrid(false);
		table.setBackground(setColor(0, 0, 153));
		table.setForeground(setColor(255, 153, 0));
			
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(0, 0, 570, 601);
		pane.setBorder(BorderFactory.createLineBorder(setColor(0, 0, 153)));
			
		return pane;
	}
	
	// adds all the new rows to the table
	private void repaintTable(){
		
		if(rowCount == 0) return;
		
		// ob sets space to row length
		Object[] ob = new Object[data[0].length];
		
		// loops depending on how many rows there are.
		for(int i = 0; i < data.length; i++){
			
			// if part of data is null break the loop
			if(data[i][0] == null){
				break;
			}
			ob[0] = data[i][0];
			ob[1] = data[i][1];
			ob[2] = data[i][2];
			ob[3] = data[i][3];
			
			dModel.addRow(ob);
		}
	}
	
	// stores data from SQLdatabase into data array
	private void getSQLdata(String SQLC){
		
		try(
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/backend_db", "myuser", "boii");
				
				Statement stmt = conn.createStatement();
		){
			
			String strSelect = SQLC;
			ResultSet rset = stmt.executeQuery(strSelect);
			
			while(rset.next()){
				rowCount++;
			}
			
			data = new Object[rowCount][4];
			
			rowCount = 0;
			rset.beforeFirst();
			while(rset.next()){
				String name = rset.getString("name"), 
					gender = rset.getString("gender"), 
					married = rset.getString("married");
				
				int age = rset.getInt("age");
				
				data[rowCount][0] = name;
				data[rowCount][1] = age;
				data[rowCount][2] = gender;
				data[rowCount][3] = married;
				
				++rowCount;
			}
			
			System.out.println("Total Number of records = " + rowCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// removes rows from dModel and resets data
	private void removeRows(){
		
		if(dModel.getRowCount() > 0){
			data = new Object[0][0];
			
			while(dModel.getRowCount() > 0){
				dModel.removeRow(dModel.getRowCount()-1);
			}
		}
	}
	
	// allows for easy use of RGB color scheme
	private Color setColor(int r, int g, int b){
		Color color = new Color(r, g, b);
		return color;
	}
	
	// creates a text field for the user to use
	private JTextField createTextField(String defaultTEXT, int x, int y, int textWidth, int textHight){
		JTextField textField = new JTextField(defaultTEXT);
		
		textField.setBounds(x, y, textWidth, textHight);
		textField.setBackground(setColor(15, 15, 183));
		textField.setForeground(setColor(255, 153, 0));
		textField.setBorder(BorderFactory.createLineBorder(setColor(255, 153, 0)));
		
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setCaretColor(setColor(255, 153, 0));
		
		return textField;
	}
	
	// creates a header panel for the Selection panel
	private JPanel createHeaderPanel(int width, FRONT fnt){
		JPanel header = new JPanel();
		JSeparator sep = new JSeparator();
		
		header.setBounds(0, 30, width, 100);
		header.setBackground(setColor(15, 15, 183));
		
		sep.setBounds(10, 60, 548, 10);
		sep.setForeground(setColor(255, 153, 0));
		sep.setBackground(setColor(255, 153, 0));
		
		header.add(fnt.createTextLabel("Search for Record", new Font("Segoe UI", 0, 34), setColor(255, 153, 0), 170, 7, 300));
		header.add(sep);
		header.add(fnt.createTextLabel("Search for a Record by Entering a Name, Age, Gender, or if Married down below",
				new Font("Segoe UI", 0, 14), setColor(255, 153, 0), 50, 55, 500));
		
		return header;
	}
	
	// creates a JPanel button
	private JPanel createButton(String text, String toolTip, FRONT fnt, int x, int y){
		JPanel button = new JPanel();
		
		button.setBounds(x, y, 100, 50);
		
		button.setBackground(setColor(15, 15, 183));
		button.setForeground(setColor(255, 153, 0));
		button.setToolTipText(toolTip);
		
		button.setBorder(BorderFactory.createLineBorder(setColor(51, 51, 183)));
		
		button.add(fnt.createTextLabel(text, new Font("Segoe UI", 0, 15), setColor(255, 153, 0), 26, -2, 300));
		
		return button;
	}
	
	// returns an SQL command String
	private String getSQLString(String name, String age, String gender, String married){
		
		// create way to select all columns or 1 or 2 or 3.
		
		String SQLstring = "select name, age, gender, married from datatable where name = '" + name + 
				"' AND age = '" + age + "' AND gender = '" + gender + "' AND married = '" + married + "'";
		
		System.out.println(SQLstring);
		return SQLstring;
	}
	
	// creates and adds the Selection tab to the main Panel
	public void createSelectTab(FRONT fnt, JPanel mPanel){
		
		int dis = 40;
		
		JPanel button_submit = createButton("Submit", "Click Button", fnt, 238, 250);
		JTextField field_name = createTextField("Enter Name", dis, 150, 120, 40);
		JTextField field_age = createTextField("Enter Age", dis + 125, 150, 120, 40);
		JTextField field_gender = createTextField("Enter Gender", dis + 125*2, 150, 120, 40);
		JTextField field_married = createTextField("Enter Married", dis + 125*3, 150, 120, 40);
		
		mPanel.add(createHeaderPanel(mPanel.getWidth(), fnt));
		
		mPanel.add(field_name);
		mPanel.add(field_age);
		mPanel.add(field_gender);
		mPanel.add(field_married);
		
		button_submit.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				button_submit.setBackground(setColor(51, 51, 183));
				fnt.repaintMPanel();
				selectData(getSQLString(field_name.getText(), field_age.getText(), field_gender.getText(), field_married.getText()));
			}
			
			public void mouseReleased(MouseEvent e){
				button_submit.setBackground(setColor(15, 15, 183));
			}
		});
		
		mPanel.add(button_submit);
	}
	
	// returns dataTable
	public JScrollPane getDataTable(){
		return createDataTable();
	}
	
	// updates tableData
	public void viewData(){
		removeRows();
		getSQLdata("select * from datatable");
		repaintTable();
	}
	
	// selects data from the table
	private void selectData(String SQL){
		
		removeRows();
		getSQLdata(SQL);
		repaintTable();
	}
}
