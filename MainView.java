

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


@SuppressWarnings("serial")
public class MainView extends JPanel implements MouseListener,Action{
	
	protected JLabel title, delete, insertar, delete_disabled;
	protected Table table;
	protected String[][] data;
	protected Database db;
	protected String[] columnNames;

	public MainView(String title){
		super();
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		this.db = new Database();
		
		this.title = new JLabel(title);
		this.title.setFont(new Font("Microsoft JhengHei UI Light", Font.BOLD, 20));
		this.title.setBounds(47, 47, 239, 49);
		add(this.title);
		
		this.insertar = new JLabel(new ImageIcon(MainView.class.getResource("/img/add.png")));
		this.insertar.setBounds(678, 64, 38, 39);
		this.insertar.addMouseListener(this);
		this.add(this.insertar);
		this.delete = new JLabel(new ImageIcon(MainView.class.getResource("/img/delete.png")));
		this.delete.setBounds(724, 64, 38, 39);
		this.delete.addMouseListener(this);
		this.delete_disabled = new JLabel(new ImageIcon(MainView.class.getResource("/img/delete-disabled.png")));
		this.delete_disabled.setBounds(724, 64, 38, 39);
		this.add(this.delete_disabled);
		this.addMouseListener(this);
	}
	
	public void addTable(int num){
		this.table = new Table(this.columnNames,this.data, num);
		this.table.addMouseListener(this);
		this.table.table.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e)
			{
				remove(delete_disabled);
				add(delete);
				revalidate();
				repaint();
			}
		});
		if(this.title.getText().equals("Almacenes"))
			this.table.setBounds(54, 245, 730, 338);
		else
			this.table.setBounds(54, 171, 730, 404);
		this.add(this.table);
		TableCellListener tcl = new TableCellListener(table.table, this);
	}
	
	public void updateTable(int num){
		this.remove(this.table);
		this.addTable(num);
		this.revalidate();
		this.repaint();
	}

	public void mouseClicked(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent arg0) {
	}
	public void mouseExited(MouseEvent arg0) {
	}
	public void mousePressed(MouseEvent arg0) {	
	}
	public void mouseReleased(MouseEvent arg0) {
	}
	public void actionPerformed(ActionEvent e) {
	}
	public Object getValue(String key) {
		return null;
	}
	public void putValue(String key, Object value) {
	}

}
