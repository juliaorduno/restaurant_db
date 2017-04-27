import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class ViewAlmacenistas extends MainView{
	
	private static final long serialVersionUID = 1L;
	private int claveGerente;
	protected static final int num = 2;

	public ViewAlmacenistas(int claveGerente){
		super("Almacenistas");
		this.claveGerente = claveGerente;
		String[] columnNames = {"Nombre","Teléfono", "Dirección" ,"Gerente"};
		this.setData();
		this.columnNames = columnNames;
		this.addTable(num);
	}
	
	public void setData(){
		this.data = db.getViewGerenteAlmacenista();
	}
	
	private class InsertarAlmacenista extends VentanaAuxiliar{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
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
						updateTable(num);
					} else if(clave.equals("") && nombre.equals("") && telefono.equals("") && direccion.equals(""))
						closeWindow();
					else if(clave.equals("") || nombre.equals(""))
						JOptionPane.showMessageDialog(null, "Llenar campos obligatorios");
					
				}
			});
			
		}
		
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
		case 1:
			db.update_almacenista("telefono", nombre, nuevo);
			break;
		case 2: 
			db.update_almacenista("direccion",nombre, nuevo);
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
			db.delete_almacenista("'" + table.table.getValueAt(table.table.getSelectedRow(), 0).toString() + "'");
			table.removeRow(table.table.getSelectedRow());
		} else if(e.getSource() == this.insertar){
			InsertarAlmacenista nuevoAlmacenista = new InsertarAlmacenista();
		}
	}
}
