package vistas;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MainView extends JPanel implements MouseListener{
	
	protected JLabel title;
	protected Table table;
	protected String[][] data;
	protected Database db;
	protected JButton delete;
	protected String[] columnNames;

	public MainView(String title){
		super();
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		this.setPreferredSize(new Dimension(840,614));
		
		this.db = new Database();
		
		this.title = new JLabel(title);
		this.title.setFont(new Font("Microsoft JhengHei UI Light", Font.BOLD, 20));
		this.title.setBounds(47, 47, 239, 49);
		add(this.title);
		
		delete = new JButton("Borrar");
		this.delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				db.delete_almacenista("'" + table.table.getValueAt(table.table.getSelectedRow(), 0).toString() + "'");
				table.removeRow(table.table.getSelectedRow());
			}
		});
		delete.setEnabled(false);
		delete.setForeground(Color.WHITE);
		delete.setBackground(new Color(102, 102, 102));
		this.delete.setBorderPainted(false);
		this.delete.setFocusPainted(false);
		delete.setBounds(659, 107, 124, 32);
		add(delete);
		
		this.addMouseListener(this);
		
		
		
	}
	
	public void addTable(){
		this.table = new Table(this.columnNames,this.data);
		this.table.table.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e)
			{
				delete.setEnabled(true);
			}
		});
		
		//this.table.setBounds(54, 179, 730, 404);
		this.table.setBounds(54, 245, 730, 338);
		this.add(this.table);
	}
	
	public void updateTable(){
		this.remove(this.table);
		this.addTable();
		this.revalidate();
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() != this.table)
			delete.setEnabled(false);
	}
	public void mouseEntered(MouseEvent arg0) {
	}
	public void mouseExited(MouseEvent arg0) {
	}
	public void mousePressed(MouseEvent arg0) {	
	}
	public void mouseReleased(MouseEvent arg0) {

	}
}
