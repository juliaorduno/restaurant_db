import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ViewInsumos extends MainView{
	
	protected static final int num = 3;
	private int claveGerente;

	public ViewInsumos(int claveGerente){
		super("Insumos");
		String[] columnNames = {"Producto","Tipo", "Proveedor" ,"Gerente"};
		this.columnNames = columnNames;
		this.setData();
		this.addTable(num);
		this.claveGerente = claveGerente;
	}
	
	public void setData(){
		this.data = db.getViewGerenteInsumos();
	}
	
	public void actionPerformed(ActionEvent e) {
		TableCellListener tcl = (TableCellListener)e.getSource();
        table.row = tcl.getRow();
        table.column = tcl.getColumn();
        table.oldValue = tcl.getOldValue().toString();
        table.newValue = tcl.getNewValue().toString();
        String nombre = (String) table.table.getValueAt(table.row, 0);
        String nuevo = table.newValue;
        switch(table.column){
		case 0:
			db.update_producto_nombre(table.oldValue, table.newValue);
			break;
		}
        setData();
	}
	
	public void mouseClicked(MouseEvent e){
		if(e.getSource() != this.table){
			this.remove(delete);
			this.add(delete_disabled);
			this.revalidate();
			this.repaint();
		}
		if(e.getSource() == this.delete){
			db.delete_producto("'" + table.table.getValueAt(table.table.getSelectedRow(), 0).toString() + "'");
			table.removeRow(table.table.getSelectedRow());
		} else if(e.getSource() == this.insertar){
			Producto producto = new Producto();
		}
	}
	
	private class Producto extends VentanaAuxiliar{
		
		private String nombre, tipo, proveedor, clave;
		private JTextField tf_clave, tf_nombre, tf_tipo;
		private JLabel l_clave, l_proveedor, l_nombre, l_tipo;
		private JTextArea header;
		private JComboBox<String> menu_proveedores;
		
		public Producto(){
			super("Nuevo Producto");
			this.setBounds(100, 100, 382, 343);
			
			this.l_clave = new JLabel("*Clave: ");
			this.l_clave.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_clave.setBounds(32, 82, 70, 26);
			this.getContentPane().add(this.l_clave);
			
			this.l_nombre = new JLabel("*Nombre: ");
			this.l_nombre.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_nombre.setBounds(32, 119, 70, 26);
			this.getContentPane().add(this.l_nombre);
			
			this.l_tipo = new JLabel("Tipo: ");
			this.l_tipo.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_tipo.setBounds(32, 156, 70, 26);
			this.getContentPane().add(this.l_tipo);
			this.header = new JTextArea("Ingresar datos del nuevo producto. \nCampos se�alados con * son obligatorios.");
			this.header.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.header.setEditable(false);
			this.header.setBounds(22, 24, 323, 37);
			this.getContentPane().add(this.header);
			
			this.l_proveedor = new JLabel("*Proveedor: ");
			this.l_proveedor.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_proveedor.setBounds(32, 193, 70, 26);
			this.getContentPane().add(this.l_proveedor);
			
			this.tf_clave = new JTextField();
			this.tf_clave.setBounds(91, 86, 254, 20);
			this.getContentPane().add(this.tf_clave);
			this.tf_clave.setColumns(10);
			
			this.tf_nombre = new JTextField();
			this.tf_nombre.setColumns(10);
			this.tf_nombre.setBounds(91, 123, 254, 20);
			this.getContentPane().add(this.tf_nombre);
			
			this.tf_tipo = new JTextField();
			this.tf_tipo.setColumns(10);
			this.tf_tipo.setBounds(93, 160, 252, 20);
			this.getContentPane().add(this.tf_tipo);
			
			menu_proveedores = new JComboBox<String>(db.getViewGerenteInsumoItems());
			menu_proveedores.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			menu_proveedores.setBackground(Color.WHITE);
			menu_proveedores.setBounds(110, 193, 238, 20);
			menu_proveedores.setSelectedIndex(-1);
			getContentPane().add(menu_proveedores);
			
			this.proveedor = "";
			
			this.aceptar.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					clave = tf_clave.getText();
					nombre = tf_nombre.getText();
					tipo = tf_tipo.getText();
					proveedor = String.valueOf(menu_proveedores.getSelectedItem());
					if(!clave.equals("") && !nombre.equals("") && !proveedor.equals("")){
						db.insertarProducto(Integer.parseInt(clave), nombre, tipo, proveedor, claveGerente);
						closeWindow();
						updateTable(num);
					} else if(clave.equals("") && nombre.equals("") && tipo.equals("") && proveedor.equals(""))
						closeWindow();
					else if(clave.equals("") || nombre.equals("") || proveedor.equals(""))
						JOptionPane.showMessageDialog(null, "Llenar campos obligatorios");
					
				}
			});
			
		}
	}

}
