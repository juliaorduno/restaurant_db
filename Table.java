package vistas;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class Table extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] columnNames;
	private String[][] data;
	protected JTable table;
	private JScrollPane scrollPane;
	protected int row, column;
	protected String oldValue, newValue;

	public Table(String[] names, String[][] data) {
		setBackground(new Color(255, 255, 255));
		this.setPreferredSize(new Dimension(730,355));
		this.columnNames = names;
		this.data = data;
		DefaultTableModel model = new DefaultTableModel(this.data, this.columnNames){
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int r, int c) {
		    	if(c == 3)
		    		return false; 
		    	else
		    		return true;
		    }

		   };
		this.table = new JTable(model);
		this.table.setBounds(0,0,730,360);
		this.table.setPreferredScrollableViewportSize(new Dimension(700,300));
		this.table.setFillsViewportHeight(true);
		this.scrollPane = new JScrollPane(this.table);
		this.scrollPane.setBounds(0, 0, 730, 355);
		scrollPane.setBackground(new Color(255, 255, 255));
		this.add(this.scrollPane);
		this.table.setFont(new Font("Arial", Font.PLAIN, 14));
		this.table.getTableHeader().setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		this.table.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		this.table.setRowHeight(table.getRowHeight()+30);
	}
	
	public void removeRow(int rowNum){
		((DefaultTableModel) table.getModel()).removeRow(rowNum);
	}

	
}
