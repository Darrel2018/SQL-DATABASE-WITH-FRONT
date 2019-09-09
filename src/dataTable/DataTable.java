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
import javax.swing.JLabel;
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
	
	private boolean submit_edit = false;
	private String name = "", gender = "", married = "";
	private int rowCount = 0, lastPrimeKey = 0, age = 0, submitButtonClicked = 0;
	
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
		
		table.setSelectionBackground(setColor(15, 15, 183));
		table.setSelectionForeground(setColor(255, 153, 0));
		
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
	private void getSQLdata(String SQLC, int sec){
		
		try(
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/backend_db", "myuser", "boii");
				
				Statement stmt = conn.createStatement();
		){
			
			if(sec == 0){
				String strSelect = SQLC;
				ResultSet rset = stmt.executeQuery(strSelect);
				
				while(rset.next()){
					rowCount++;
				}
				
				data = new Object[rowCount][4];
				
				rowCount = 0;
				rset.beforeFirst();
				while(rset.next()){
					name = rset.getString("name");
					age = rset.getInt("age");
					gender = rset.getString("gender");
					married = rset.getString("married");
					
					
					data[rowCount][0] = name;
					data[rowCount][1] = age;
					data[rowCount][2] = gender;
					data[rowCount][3] = married;
					
					++rowCount;
				}
				
				System.out.println("Total Number of records = " + rowCount);
			}
			else if(sec == 1){
				
				String strInsert = SQLC;
				stmt.executeUpdate(strInsert);
				
				System.out.println("Inserted New Record!");
			}
			else if(sec == 2){
				
				String strSelect = SQLC;
				ResultSet rset = stmt.executeQuery(strSelect);
				
				while(rset.next()){
					lastPrimeKey = rset.getInt("id");
				}
			}
			else if(sec == 3){
				
				String strEdit = SQLC;
				stmt.executeUpdate(strEdit);
				
				System.out.println("Edited Record!");
			}
			else if(sec == 4){
				
				String strEdit = SQLC;
				stmt.executeUpdate(strEdit);
				
				System.out.println("Deleted Record!");
			}
			
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
	private JPanel createHeaderPanel(int width, FRONT fnt, String topHead, String bottomHead){
		JPanel header = new JPanel();
		JSeparator sep = new JSeparator();
		
		header.setBounds(0, 30, width, 100);
		header.setBackground(setColor(15, 15, 183));
		
		sep.setBounds(10, 60, 548, 10);
		sep.setForeground(setColor(255, 153, 0));
		sep.setBackground(setColor(255, 153, 0));
		
		header.add(fnt.createTextLabel(topHead, new Font("Segoe UI", 0, 34), setColor(255, 153, 0), 170, 7, 300));
		header.add(sep);
		header.add(fnt.createTextLabel(bottomHead, new Font("Segoe UI", 0, 14), setColor(255, 153, 0), 50, 55, 500));
		
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
		
		boolean first_column = false;
		String SQLstring = "select * from datatable";
		
		// name
		if(!name.equalsIgnoreCase("enter name") && !name.equalsIgnoreCase("")){
			
			if(!first_column){
				first_column = true;
				
				SQLstring += " where name = '" + name + "'";
			}
			else {
				SQLstring += " AND name = '" + name + "'";
			}
		}
		
		// age
		if(!age.equalsIgnoreCase("enter age") && !age.equalsIgnoreCase("")){
			
			if(!first_column){
				first_column = true;
				
				SQLstring += " where age = " + age;
			}
			else {
				SQLstring += " AND age = " + age;
			}
		}
		
		// gender
		if(!gender.equalsIgnoreCase("enter gender") && !gender.equalsIgnoreCase("")){
			
			if(!first_column){
				first_column = true;
				
				SQLstring += " where gender = '" + gender + "'";
			}
			else {
				SQLstring += " AND gender = '" + gender + "'";
			}
		}
		
		// married
		if(!married.equalsIgnoreCase("enter married") && !married.equalsIgnoreCase("")){
			
			if(!first_column){
				first_column = true;
				
				SQLstring += " where married = '" + married + "'";
			}
			else {
				SQLstring += " AND married = '" + married + "'";
			}
		}
		
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
		
		mPanel.add(createHeaderPanel(mPanel.getWidth(), fnt, "Search for Record",
				"Search for a Record by Entering a Name, Age, Gender, or if Married down below"));
		
		mPanel.add(field_name);
		mPanel.add(field_age);
		mPanel.add(field_gender);
		mPanel.add(field_married);
		
		button_submit.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				button_submit.setBackground(setColor(51, 51, 183));
			}
			
			public void mouseReleased(MouseEvent e){
				button_submit.setBackground(setColor(15, 15, 183));
				fnt.repaintMPanel();
				selectData(getSQLString(field_name.getText(), field_age.getText(),
						field_gender.getText(), field_married.getText()));
				
			}
		});
		
		mPanel.add(button_submit);
	}
	
	// returns a SQL command string
	private String getInsertSQL(String name, String age, String gender, String married){
		
		boolean insertRejected = false;
		String SQLstring = null;
		
		// checks name
		if(name.equalsIgnoreCase("enter name") && !name.equalsIgnoreCase("")){
			insertRejected = true;
		}
		
		// checks age
		if(age.equalsIgnoreCase("enter age") && !age.equalsIgnoreCase("")){
			insertRejected = true;
		}
		else {
			
			try {
				Integer.parseInt(age);
				System.out.println("Age input is valid");
			} catch(NumberFormatException e){
				System.err.println("ERROR: invalid age input...");
				insertRejected = true;
			}
		}
		
		// checks gender
		if(gender.equalsIgnoreCase("enter gender") && !gender.equalsIgnoreCase("")){
			insertRejected = true;
		}
		else {
			
			if(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female")){
				System.out.println("Gender input is valid...");
			}
			else {
				System.err.println("Gender input is not valid...");
				insertRejected = true;
			}
		}
		
		// checks married
		if(married.equalsIgnoreCase("enter married") && !married.equalsIgnoreCase("")){
			insertRejected = true;
		}
		else {
			
			if(married.equalsIgnoreCase("yes") || married.equalsIgnoreCase("no")){
				System.out.println("Married input is valid...");
			}
			else {
				System.err.println("Married input is not valid...");
				insertRejected = true;
			}
		}
		
		// if insertRejected is true the SQL command will be set to false
		if(!insertRejected){
			getSQLdata("select * from datatable", 2);
			SQLstring = "insert into datatable values "
					+ "(" + (lastPrimeKey+1) +", '" + name + "', " + age + ", '" + gender + "', '" + married + "')";
		}
		else {
			SQLstring = "false";
		}
		
		System.out.println(SQLstring);
		
		return SQLstring;
	}
	
	// adds new entrys to the database
	public void createAddEntryTab(FRONT fnt, JPanel mPanel){
		int dis = 40, compCount;
		
		JPanel button_submit = createButton("Submit", "Click Button", fnt, 238, 250);
		JTextField field_name = createTextField("Enter Name", dis, 150, 120, 40);
		JTextField field_age = createTextField("Enter Age", dis + 125, 150, 120, 40);
		JTextField field_gender = createTextField("Enter Gender", dis + 125*2, 150, 120, 40);
		JTextField field_married = createTextField("Enter Married", dis + 125*3, 150, 120, 40);
		
		mPanel.add(createHeaderPanel(mPanel.getWidth(), fnt, "Insert into Database", 
				"Fill in the four text fields and click 'Submit' to insert the data into the database"));
		
		mPanel.add(field_name);
		mPanel.add(field_age);
		mPanel.add(field_gender);
		mPanel.add(field_married);
		
		compCount = mPanel.getComponentCount()+1;
		
		button_submit.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				button_submit.setBackground(setColor(51, 51, 183));
			}
			
			public void mouseReleased(MouseEvent e){
				button_submit.setBackground(setColor(15, 15, 183));
				
				// create new SQL method to manipulate data
				
				insertData(getInsertSQL(field_name.getText(), field_age.getText(),
						field_gender.getText(), field_married.getText()));
				
				if(compCount < mPanel.getComponentCount()){
					mPanel.remove(mPanel.getComponentCount()-1);
				}
				
				JLabel Succlable = fnt.createTextLabel("Success: the record was added!", 
						new Font("Segoe UI", 0, 15), setColor(255, 153, 0), 180, 200, 500);
				
				mPanel.add(Succlable);
				fnt.frame.repaint();
				
				field_name.setBorder(BorderFactory.createLineBorder(setColor(0, 255, 0)));
				field_age.setBorder(BorderFactory.createLineBorder(setColor(0, 255, 0)));
				field_gender.setBorder(BorderFactory.createLineBorder(setColor(0, 255, 0)));
				field_married.setBorder(BorderFactory.createLineBorder(setColor(0, 255, 0)));
			}
		});
		
		mPanel.add(button_submit);
	}
	
	// returns the SQL command for updating a record
	private String getEditSQL(String name, String age, String gender, String married){
		
		String origName = this.name, origGender = this.gender, origMarried = this.married;
		int origAge = this.age;
		
		String SQL = "update datatable set name = '" + name + "', age = " + age + 
				", gender = '" + gender + "', married = '" + married + "'" + 
				" where name = '" + origName + "' AND age = " + origAge + 
				" AND gender = '" + origGender + "' AND married = '" + origMarried + "'";
		
		return SQL;
	}
	
	// creates the edit entry tab
	public void createEditEntryTab(FRONT fnt, JPanel mPanel){
		
		int dis = 40, compCount;
		submit_edit = false;
		submitButtonClicked = 0;
		
		JPanel button_submit = createButton("Submit", "Click Button", fnt, 238, 250);
		JTextField field_name = createTextField("Enter Name", dis, 150, 120, 40);
		JTextField field_age = createTextField("Enter Age", dis + 125, 150, 120, 40);
		JTextField field_gender = createTextField("Enter Gender", dis + 125*2, 150, 120, 40);
		JTextField field_married = createTextField("Enter Married", dis + 125*3, 150, 120, 40);
		
		mPanel.add(createHeaderPanel(mPanel.getWidth(), fnt, "Edit Database Entry", 
				"Edit a Record by Entering a Name, Age, Gender, or if Married down below"));
		
		mPanel.add(field_name);
		mPanel.add(field_age);
		mPanel.add(field_gender);
		mPanel.add(field_married);
		
		compCount = mPanel.getComponentCount()+1;
		
		button_submit.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				button_submit.setBackground(setColor(51, 51, 183));
			}
			
			public void mouseReleased(MouseEvent e){
				button_submit.setBackground(setColor(15, 15, 183));
				
				if(submit_edit && submitButtonClicked == 0){
					
					String SQL = getEditSQL(field_name.getText(), field_age.getText(),
							field_gender.getText(), field_married.getText());
					
					System.out.println(SQL);
					
					editData(SQL);
					
					if(compCount < mPanel.getComponentCount()){
						mPanel.remove(mPanel.getComponentCount()-1);
					}
					
					JLabel Succlable = fnt.createTextLabel("Success: the record was edited!", 
							new Font("Segoe UI", 0, 15), setColor(255, 153, 0), 180, 200, 500);
					
					mPanel.add(Succlable);
					fnt.frame.repaint();
					
					field_name.setBorder(BorderFactory.createLineBorder(setColor(255, 153, 0)));
					field_age.setBorder(BorderFactory.createLineBorder(setColor(255, 153, 0)));
					field_gender.setBorder(BorderFactory.createLineBorder(setColor(255, 153, 0)));
					field_married.setBorder(BorderFactory.createLineBorder(setColor(255, 153, 0)));
					
					submitButtonClicked++;
				}
				
				if(!submit_edit){
					selectData(getSQLString(field_name.getText(), field_age.getText(), 
							field_gender.getText(), field_married.getText()));
				}
				
				if(rowCount <= 0 && !submit_edit){
					
					if(compCount < mPanel.getComponentCount()){
						mPanel.remove(mPanel.getComponentCount()-1);
					}
					
					JLabel ERRORlable = fnt.createTextLabel("ERROR: Zero records found!\n Please try again.", 
							new Font("Segoe UI", 0, 15), setColor(255, 153, 0), 120, 200, 500);
					
					mPanel.add(ERRORlable);
					fnt.frame.repaint();
				}
				
				if(rowCount >= 1 && !submit_edit){
					
					if(rowCount >= 2){
						
						if(compCount < mPanel.getComponentCount()){
							mPanel.remove(mPanel.getComponentCount()-1);
						}
						
						JLabel ERRORlable = fnt.createTextLabel("ERROR: more than one record found!\n Please try again.", 
								new Font("Segoe UI", 0, 15), setColor(255, 153, 0), 120, 200, 500);
						
						mPanel.add(ERRORlable);
						fnt.frame.repaint();
					}
					
					if(rowCount == 1 && !submit_edit){
						
						if(compCount < mPanel.getComponentCount()){
							mPanel.remove(mPanel.getComponentCount()-1);
						}
						
						JLabel infoLable = fnt.createTextLabel("Found 1 Record! change the info in the text fields to edit info", 
								new Font("Segoe UI", 0, 15), setColor(255, 153, 0), 100, 200, 500);
						
						field_name.setText(name);
						field_age.setText("" + age);
						field_gender.setText(gender);
						field_married.setText(married);
						
						field_name.setBorder(BorderFactory.createLineBorder(setColor(0, 255, 0)));
						field_age.setBorder(BorderFactory.createLineBorder(setColor(0, 255, 0)));
						field_gender.setBorder(BorderFactory.createLineBorder(setColor(0, 255, 0)));
						field_married.setBorder(BorderFactory.createLineBorder(setColor(0, 255, 0)));
						
						submit_edit = true;
						
						mPanel.add(infoLable);
						fnt.frame.repaint();
					}
				}
				
				if(submitButtonClicked > 0){
					submit_edit = false;
					submitButtonClicked = 0;
				}
			}
		});
		
		mPanel.add(button_submit);
	}
	
	private String getDeleteSQL(String name, String age, String gender, String married){
		
		boolean first_column = false;
		String SQLstring = "Delete from datatable";
		
		// name
		if(!name.equalsIgnoreCase("enter name") && !name.equalsIgnoreCase("")){
			
			if(!first_column){
				first_column = true;
				
				SQLstring += " where name = '" + name + "'";
			}
			else {
				SQLstring += " AND name = '" + name + "'";
			}
		}
		
		// age
		if(!age.equalsIgnoreCase("enter age") && !age.equalsIgnoreCase("")){
			
			if(!first_column){
				first_column = true;
				
				SQLstring += " where age = " + age;
			}
			else {
				SQLstring += " AND age = " + age;
			}
		}
		
		// gender
		if(!gender.equalsIgnoreCase("enter gender") && !gender.equalsIgnoreCase("")){
			
			if(!first_column){
				first_column = true;
				
				SQLstring += " where gender = '" + gender + "'";
			}
			else {
				SQLstring += " AND gender = '" + gender + "'";
			}
		}
		
		// married
		if(!married.equalsIgnoreCase("enter married") && !married.equalsIgnoreCase("")){
			
			if(!first_column){
				first_column = true;
				
				SQLstring += " where married = '" + married + "'";
			}
			else {
				SQLstring += " AND married = '" + married + "'";
			}
		}
		
		System.out.println(SQLstring);
		return SQLstring;
	}
	
	// creates the delete tab
	public void createDeleteTab(FRONT fnt, JPanel mPanel){
		
		int dis = 40, compCount;
		submit_edit = false;
		submitButtonClicked = 0;
		
		JPanel button_submit = createButton("Submit", "Click Button", fnt, 238, 250);
		JTextField field_name = createTextField("Enter Name", dis, 150, 120, 40);
		JTextField field_age = createTextField("Enter Age", dis + 125, 150, 120, 40);
		JTextField field_gender = createTextField("Enter Gender", dis + 125*2, 150, 120, 40);
		JTextField field_married = createTextField("Enter Married", dis + 125*3, 150, 120, 40);
		
		mPanel.add(createHeaderPanel(mPanel.getWidth(), fnt, "Delete Record",
				"Delete a Record by Entering a Name, Age, Gender, or if Married down below"));
		
		mPanel.add(field_name);
		mPanel.add(field_age);
		mPanel.add(field_gender);
		mPanel.add(field_married);
		
		compCount = mPanel.getComponentCount()+1;
		
		button_submit.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				button_submit.setBackground(setColor(51, 51, 183));
			}
			
			public void mouseReleased(MouseEvent e){
				button_submit.setBackground(setColor(15, 15, 183));
				
				if(submit_edit && submitButtonClicked == 0){
					
					String SQL = getDeleteSQL(field_name.getText(), field_age.getText(),
							field_gender.getText(), field_married.getText());
					
					deleteData(SQL);
					
					if(compCount < mPanel.getComponentCount()){
						mPanel.remove(mPanel.getComponentCount()-1);
					}
					
					JLabel Succlable = fnt.createTextLabel("Success: the record was Deleted!", 
							new Font("Segoe UI", 0, 15), setColor(255, 153, 0), 180, 200, 500);
					
					mPanel.add(Succlable);
					fnt.frame.repaint();
					
					field_name.setBorder(BorderFactory.createLineBorder(setColor(255, 153, 0)));
					field_age.setBorder(BorderFactory.createLineBorder(setColor(255, 153, 0)));
					field_gender.setBorder(BorderFactory.createLineBorder(setColor(255, 153, 0)));
					field_married.setBorder(BorderFactory.createLineBorder(setColor(255, 153, 0)));
					
					field_name.setText("Enter Name");
					field_age.setText("Enter Age");
					field_gender.setText("Enter Gender");
					field_married.setText("Enter Married");
					
					submitButtonClicked++;
				}
				
				if(!submit_edit){
					selectData(getSQLString(field_name.getText(), field_age.getText(), 
							field_gender.getText(), field_married.getText()));
				}
				
				if(rowCount <= 0 && !submit_edit){
					
					if(compCount < mPanel.getComponentCount()){
						mPanel.remove(mPanel.getComponentCount()-1);
					}
					
					JLabel ERRORlable = fnt.createTextLabel("ERROR: Zero records found!\n Please try again.", 
							new Font("Segoe UI", 0, 15), setColor(255, 153, 0), 120, 200, 500);
					
					mPanel.add(ERRORlable);
					fnt.frame.repaint();
				}
				
				if(rowCount >= 1 && !submit_edit){
					
					if(rowCount >= 2){
						
						if(compCount < mPanel.getComponentCount()){
							mPanel.remove(mPanel.getComponentCount()-1);
						}
						
						JLabel ERRORlable = fnt.createTextLabel("ERROR: more than one record found!\n Please try again.", 
								new Font("Segoe UI", 0, 15), setColor(255, 153, 0), 120, 200, 500);
						
						mPanel.add(ERRORlable);
						fnt.frame.repaint();
					}
					
					if(rowCount == 1 && !submit_edit){
						
						if(compCount < mPanel.getComponentCount()){
							mPanel.remove(mPanel.getComponentCount()-1);
						}
						
						JLabel infoLable = fnt.createTextLabel("Found 1 Record! Click Submit again to delete!", 
								new Font("Segoe UI", 0, 15), setColor(255, 153, 0), 150, 200, 500);
						
						field_name.setText(name);
						field_age.setText("" + age);
						field_gender.setText(gender);
						field_married.setText(married);
						
						field_name.setBorder(BorderFactory.createLineBorder(setColor(0, 255, 0)));
						field_age.setBorder(BorderFactory.createLineBorder(setColor(0, 255, 0)));
						field_gender.setBorder(BorderFactory.createLineBorder(setColor(0, 255, 0)));
						field_married.setBorder(BorderFactory.createLineBorder(setColor(0, 255, 0)));
						
						submit_edit = true;
						
						mPanel.add(infoLable);
						fnt.frame.repaint();
					}
				}
				
				if(submitButtonClicked > 0){
					submit_edit = false;
					submitButtonClicked = 0;
				}
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
		getSQLdata("select * from datatable", 0);
		repaintTable();
	}
	
	// selects data from the table
	private void selectData(String SQL){
		removeRows();
		getSQLdata(SQL, 0);
		repaintTable();
	}
	
	// inserts data into the database
	private void insertData(String SQL){
		if(SQL.equalsIgnoreCase("false")) return;
		removeRows();
		getSQLdata(SQL, 1);
	}
	
	// edits data from database
	private void editData(String SQL){
		removeRows();
		getSQLdata(SQL, 3);
	}
	
	// deletes data from database
	private void deleteData(String SQL){
		removeRows();
		getSQLdata(SQL, 4);
	}
}
