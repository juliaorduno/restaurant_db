import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ViewProveedores extends MainView{

	protected final int num = 4;
	
	public ViewProveedores(){
		super("Proveedores");
		String[] columnNames = {"Nombre","Teléfono", "Email"};
		this.setData();
		this.columnNames = columnNames;
		this.addTable(num);
	}
	
	public void setData(){
		this.data = db.getViewGerenteProveedores();
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
        	db.update_proveedor("nombre", nombre, nuevo);
		case 1:
			db.update_proveedor("telefono", nombre, nuevo);
			break;
		case 2: 
			db.update_proveedor("email",nombre, nuevo);
			break;
		}
        setData();
	}
	
	public void mouseReleased(MouseEvent e){
		if(e.getSource() != this.table){
			this.remove(delete);
			this.add(delete_disabled);
			this.revalidate();
			this.repaint();
		}
		if(e.getSource() == this.delete){
			db.delete_proveedor("'" + table.table.getValueAt(table.table.getSelectedRow(), 0).toString() + "'");
			table.removeRow(table.table.getSelectedRow());
		} else if(e.getSource() == this.insertar){
			Proveedor proveedor = new Proveedor();
		}
	}
	
	private class Proveedor extends VentanaAuxiliar{
		private String nombre, telefono, email, clave;
		private JTextField tf_clave, tf_nombre, tf_telefono, tf_email;
		private JLabel l_clave, l_email, l_nombre, l_telefono;
		private JTextArea header;
		
		public Proveedor(){
			super("Nuevo Proveedor");
			this.setBounds(100, 100, 382, 343);
			
			this.l_clave = new JLabel("*Clave: ");
			this.l_clave.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_clave.setBounds(32, 82, 70, 26);
			this.getContentPane().add(this.l_clave);
			
			this.l_nombre = new JLabel("*Nombre: ");
			this.l_nombre.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_nombre.setBounds(32, 119, 70, 26);
			this.getContentPane().add(this.l_nombre);
			
			this.l_telefono = new JLabel("Teléfono: ");
			this.l_telefono.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_telefono.setBounds(32, 156, 70, 26);
			this.getContentPane().add(this.l_telefono);
			this.header = new JTextArea("Ingresar datos del nuevo proveedor. \nCampos señalados con * son obligatorios.");
			this.header.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.header.setEditable(false);
			this.header.setBounds(22, 24, 323, 37);
			this.getContentPane().add(this.header);
			
			this.l_email = new JLabel("E-Mail: ");
			this.l_email.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_email.setBounds(32, 193, 70, 26);
			this.getContentPane().add(this.l_email);
			
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
			
			this.tf_email = new JTextField();
			this.tf_email.setColumns(10);
			this.tf_email.setBounds(91, 197, 254, 20);
			this.getContentPane().add(this.tf_email);
			
			this.aceptar.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					clave = tf_clave.getText();
					nombre = tf_nombre.getText();
					telefono = tf_telefono.getText();
					email = tf_email.getText();
					if(!clave.equals("") && !nombre.equals("")){
						db.insertarProveedor(Integer.parseInt(clave), nombre, telefono, email);
						closeWindow();
						setData();
						updateTable(num);
					} else if(clave.equals("") && nombre.equals("") && telefono.equals("") && email.equals(""))
						closeWindow();
					else if(clave.equals("") || nombre.equals(""))
						JOptionPane.showMessageDialog(null, "Llenar campos obligatorios");
					
				}
			});
			
		}
	}
}
