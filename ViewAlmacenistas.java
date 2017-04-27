package vistas;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

public class ViewAlmacenistas extends JPanel implements MouseListener{
	
	private JLabel title;
	private Table table;
	private String[][] data;
	private Database db;
	private JButton insertar, delete;
	private int currentAlmacen;
	private int claveGerente;

	public ViewAlmacenistas(int claveGerente){
		super();
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		this.setPreferredSize(new Dimension(840,614));
		
		this.db = new Database();
		this.claveGerente = claveGerente;
		this.title = new JLabel("Almacenistas");
		this.title.setFont(new Font("Microsoft JhengHei UI Light", Font.BOLD, 20));
		this.title.setBounds(47, 47, 239, 49);
		add(this.title);
		
		this.insertar = new JButton("Nuevo Almacenista");
		this.insertar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				InsertarAlmacenista nuevoAlmacenista = new InsertarAlmacenista();
			}
		});
		this.insertar.setForeground(new Color(255, 255, 255));
		this.insertar.setBackground(new Color(102, 102, 102));
		this.insertar.setBorderPainted(false);
		this.insertar.setFocusPainted(false);
		this.insertar.setBounds(659, 64, 124, 32);
		add(this.insertar);
		
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
		
		this.addTable();
		this.addMouseListener(this);
		this.getTableChanges();
	}
	
	public void setData(){
		this.data = db.getViewGerenteAlmacenista();
	}
	
	public void addTable(){
		String[] columnNames = {"Nombre","Teléfono", "Dirección" ,"Gerente"};
		this.setData();
		this.table = new Table(columnNames, this.data);
		this.table.table.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e)
			{
				delete.setEnabled(true);
			}
		});
		this.table.setBounds(54, 179, 730, 404);
		this.add(this.table);
	}
	
	public void updateTable(){
		this.remove(this.table);
		this.addTable();
		this.revalidate();
		this.repaint();
	}
	
	public void getTableChanges(){
		Action action = new AbstractAction()
		{

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
		    {
				
		        TableCellListener tcl = (TableCellListener)e.getSource();
		        table.row = tcl.getRow();
		        table.column = tcl.getColumn();
		        table.oldValue = tcl.getOldValue().toString();
		        table.newValue = tcl.getNewValue().toString();
		        String nombre = (String) table.table.getValueAt(table.row, 0);
		        String nuevo = table.newValue;
		        switch(table.column){
				case 1:
					db.update_almacenista("telefono", nombre, nuevo);
					updateTable();
					break;
				case 2: 
					db.update_almacenista("direccion",nombre, nuevo);
					updateTable();
					break;
				}
		        
		    }
		};

		TableCellListener tcl = new TableCellListener(table.table, action);
		
	}
	
	private class InsertarAlmacenista extends VentanaAuxiliar{
		private String nombre, telefono, direccion, clave;
		private JTextField tf_clave, tf_nombre, tf_telefono, tf_direccion;
		private JLabel l_clave, l_direccion, l_nombre, l_empresa;
		private JTextArea header;
		
		public InsertarAlmacenista(){
			super("Nuevo Almacenista");
			this.setBounds(100, 100, 382, 343);
			
			this.l_clave = new JLabel("*Clave: ");
			this.l_clave.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_clave.setBounds(32, 82, 70, 26);
			this.getContentPane().add(this.l_clave);
			
			this.l_nombre = new JLabel("*Nombre: ");
			this.l_nombre.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_nombre.setBounds(32, 119, 70, 26);
			this.getContentPane().add(this.l_nombre);
			
			this.l_empresa = new JLabel("Teléfono: ");
			this.l_empresa.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_empresa.setBounds(32, 156, 70, 26);
			this.getContentPane().add(this.l_empresa);
			this.header = new JTextArea("Ingresar datos del nuevo almacenista. \nCampos señalados con * son obligatorios.");
			this.header.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.header.setEditable(false);
			this.header.setBounds(22, 24, 323, 37);
			this.getContentPane().add(this.header);
			
			this.l_direccion = new JLabel("Dirección: ");
			this.l_direccion.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_direccion.setBounds(32, 193, 70, 26);
			this.getContentPane().add(this.l_direccion);
			
			this.tf_clave = new JTextField();
			this.tf_clave.setBounds(91, 86, 254, 20);
			this.getContentPane().add(this.tf_clave);
			this.tf_clave.setColumns(10);
			
			this.tf_nombre = new JTextField();
			this.tf_nombre.setColumns(10);
			this.tf_nombre.setBounds(91, 123, 254, 20);
			this.getContentPane().add(this.tf_nombre);
			
			this.tf_telefono = new JTextField();
			this.tf_telefono.setColumns(10);
			this.tf_telefono.setBounds(93, 160, 252, 20);
			this.getContentPane().add(this.tf_telefono);
			
			this.tf_direccion = new JTextField();
			this.tf_direccion.setColumns(10);
			this.tf_direccion.setBounds(91, 197, 254, 20);
			this.getContentPane().add(this.tf_direccion);
			
			this.aceptar.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					clave = tf_clave.getText();
					nombre = tf_nombre.getText();
					telefono = tf_telefono.getText();
					direccion = tf_direccion.getText();
					if(!clave.equals("") && !nombre.equals("")){
						db.insertarAlmacenista(Integer.parseInt(clave), nombre, telefono, direccion, claveGerente);
						closeWindow();
						updateTable();
					} else if(clave.equals("") && nombre.equals("") && telefono.equals("") && direccion.equals(""))
						closeWindow();
					else if(clave.equals("") || nombre.equals(""))
						JOptionPane.showMessageDialog(null, "Llenar campos obligatorios");
					
				}
			});
			
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() != this.table)
			delete.setEnabled(false);
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
}
