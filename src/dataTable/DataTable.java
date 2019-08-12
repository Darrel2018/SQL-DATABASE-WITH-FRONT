package dataTable;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
			
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(0, 0, 570, 601);
			
		return pane;
	}
	
	// adds all the new rows to the table
	private void repaintTable(){
		
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
	private void getSQLdata(){
		
		try(
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/backend_db", "myuser", "boii");
				
				Statement stmt = conn.createStatement();
		){
			
			String strSelect = "select * from datatable";
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
	
	// returns dataTable
	public JScrollPane getDataTable(){
		return createDataTable();
	}
	
	// updates tableData
	public void viewData(){
		removeRows();
		getSQLdata();
		repaintTable();
	}
}
