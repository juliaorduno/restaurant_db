

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;


public class ViewAlmacenes extends MainView{

	private static final long serialVersionUID = 1L;
	private JComboBox<String> menu;
	private JLabel nombre, empresa, direccion, edit;
	private String[] menu_items, info;
	private int currentAlmacen;
	private Almacen nuevo;
	protected final int num = 1;

	public ViewAlmacenes(){
		super("Almacenes");

		this.setMenuItems();
		
		this.nombre = new JLabel();
		this.nombre.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		this.nombre.setBounds(54, 150, 729, 19);
		this.add(this.nombre);
		
		this.empresa = new JLabel();
		this.empresa.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		this.empresa.setBounds(54, 175, 729, 19);
		this.add(this.empresa);
		
		this.direccion = new JLabel();
		this.direccion.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		this.direccion.setBounds(54, 201, 729, 19);
		this.add(this.direccion);
		
		this.edit = new JLabel(new ImageIcon(ViewAlmacenes.class.getResource("/img/pencil.png")));
		this.edit.addMouseListener(this);
		this.edit.setBounds(724, 180, 38, 39);
		this.add(this.edit);
		
		this.currentAlmacen = 1;
		String[] columnNames ={"Producto","Par Stock", "Existencia" ,"Estado"};
		this.columnNames = columnNames;
		this.setInfo();
	}
	
	public void setMenuItems(){
		
		this.menu_items = db.getViewGerenteAlmacenItems();
		this.menu = new JComboBox<String>(this.menu_items);
		this.menu.addItem("Agregar nuevo");
		this.menu.setBackground(new Color(255, 255, 255));
		this.menu.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
		this.menu.setBounds(47, 107, 220, 20);
		this.menu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String selected = String.valueOf(menu.getSelectedItem());
				if(selected.equals("Agregar nuevo"))
					nuevo = new Almacen("insertar");
				else{
					currentAlmacen = db.getNumAlmacen(selected);
					remove(table);
					setInfo();
					revalidate();
					repaint();
				}
			}
		});
		add(this.menu);
	}
	
	public void setData(){
		this.data = db.getViewGerenteAlmacenInsumos(this.currentAlmacen);
	}
	
	public void setInfo(){
		this.info = db.getViewGerenteAlmacenInfo(this.currentAlmacen);
		this.nombre.setText("Nombre: " + this.info[1]);
		this.direccion.setText("Dirección: " + info[3]);
		this.empresa.setText("Empresa: " + info[2]);
		this.add(this.nombre);
		this.add(this.direccion);
		this.add(this.empresa);
		this.setData();
		this.addTable(num);
	}
	
	public void updateHeader(){
		this.remove(this.menu);
		this.setMenuItems();
		this.setInfo();
		this.revalidate();
		this.repaint();
	}
	
	public void actionPerformed(ActionEvent e){
		TableCellListener tcl = (TableCellListener)e.getSource();
        table.row = tcl.getRow();
        table.column = tcl.getColumn();
        table.oldValue = tcl.getOldValue().toString();
        table.newValue = tcl.getNewValue().toString();
        String producto = (String) table.table.getValueAt(table.row, 0);
        int nuevaCantidad;
        switch(table.column){
		case 1:
			nuevaCantidad = Integer.parseInt(table.newValue);
			db.update_cantidad("necesaria", currentAlmacen, producto , nuevaCantidad);
			break;
		case 2: 
			nuevaCantidad = Integer.parseInt(table.newValue);
			db.update_cantidad("actual",currentAlmacen, producto , nuevaCantidad);
			break;
		}
        setData();
        updateTable(num);
	}
	
	public void removeTable(){
		  this.remove(this.table);
	}
	
	private class Almacen extends VentanaAuxiliar{

		private static final long serialVersionUID = 1L;
		private String nombre, empresa, direccion,numero;
		private JTextField tf_numero, tf_nombre, tf_empresa, tf_direccion;
		private JLabel l_numero, l_direccion, l_nombre, l_empresa;
		private JTextArea header;
		private String action;
		
		public Almacen(String action){
			super();
			this.action = action;
			if(this.action.equals("insertar")){
				this.tf_numero = new JTextField();
				this.tf_nombre = new JTextField();
				this.tf_empresa = new JTextField();
				this.tf_direccion = new JTextField();
				this.header = new JTextArea("Ingresar datos del nuevo almacén. \nCampos señalados con * son obligatorios.");
				this.setTitle("Nuevo almacén");
			} else if(this.action.equals("update")){
				this.tf_numero = new JTextField(info[0]);
				this.tf_numero.setEditable(false);
				this.tf_nombre = new JTextField(info[1]);
				this.tf_empresa = new JTextField(info[2]);
				this.tf_direccion = new JTextField(info[3]);
				this.header = new JTextArea("Actualizar datos del almacén. \nCampos señalados con * son obligatorios.");
				this.setTitle("Editar almacén " + info[1]);
				this.add(this.borrar);
			}
			this.setBounds(100, 100, 382, 343);
			
			this.l_numero = this.action.equals("insertar")?new JLabel("*Número: "):new JLabel("Número:");
			this.l_numero.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_numero.setBounds(32, 82, 70, 26);
			this.getContentPane().add(this.l_numero);
			
			this.l_nombre = new JLabel("*Nombre: ");
			this.l_nombre.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_nombre.setBounds(32, 119, 70, 26);
			this.getContentPane().add(this.l_nombre);
			
			this.l_empresa = new JLabel("*Empresa: ");
			this.l_empresa.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_empresa.setBounds(32, 156, 70, 26);
			this.getContentPane().add(this.l_empresa);
			
			
			this.l_direccion = new JLabel("Dirección: ");
			this.l_direccion.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_direccion.setBounds(32, 193, 70, 26);
			this.getContentPane().add(this.l_direccion);
			
			this.header.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.header.setEditable(false);
			this.header.setBounds(22, 24, 323, 37);
			this.getContentPane().add(this.header);
			
			this.tf_numero.setBounds(91, 86, 254, 20);
			this.getContentPane().add(this.tf_numero);
			this.tf_numero.setColumns(10);
			
			
			this.tf_nombre.setColumns(10);
			this.tf_nombre.setBounds(91, 123, 254, 20);
			this.getContentPane().add(this.tf_nombre);
			
			
			this.tf_empresa.setColumns(10);
			this.tf_empresa.setBounds(93, 160, 252, 20);
			this.getContentPane().add(this.tf_empresa);
			
			
			this.tf_direccion.setColumns(10);
			this.tf_direccion.setBounds(91, 197, 254, 20);
			this.getContentPane().add(this.tf_direccion);
			
			this.aceptar.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					numero = tf_numero.getText();
					nombre = tf_nombre.getText();
					empresa = tf_empresa.getText();
					direccion = tf_direccion.getText();
					if(!numero.equals("") && !nombre.equals("") && !empresa.equals("")){
						if(action.equals("insertar"))
							db.insertarAlmacen(Integer.parseInt(numero), nombre, empresa, direccion);
						else{
							db.update_almacen(Integer.parseInt(info[0]),"nombre",nombre);
							db.update_almacen(Integer.parseInt(info[0]),"empresa",empresa);
							db.update_almacen(Integer.parseInt(info[0]),"direccion",direccion);
						}
						currentAlmacen = Integer.parseInt(numero);
						removeTable();
						updateHeader();
						closeWindow();
						
					} else if(numero.equals("") && nombre.equals("") && empresa.equals("") && direccion.equals(""))
						closeWindow();
					else if(numero.equals("") || nombre.equals("") || empresa.equals(""))
						JOptionPane.showMessageDialog(null, "Llenar campos obligatorios");
					
				}
			});
			
			this.borrar.addActionListener(new ActionListener(){

			    public void actionPerformed(ActionEvent e) {
			     numero = tf_numero.getText();
			     db.delete_almacen(Integer.parseInt(numero));
			     currentAlmacen = 1;
			     removeTable();
			     updateHeader();
			     closeWindow();
			    }
			    
			   });
		}
		
	}

	private class Insumo extends VentanaAuxiliar{

		private static final long serialVersionUID = 1L;
		private String producto,cantNec;
		private int cantActual;
		private JTextField tf_cantNec, tf_cantActual;
		private JLabel l_cantActual, l_producto, l_cantNec;
		private JTextArea header;
		private JComboBox<String> menu_productos;
		
		public Insumo(){
			super("Nuevo Insumo");
			this.setBounds(100, 100, 382, 343);
			
			this.l_producto = new JLabel("*Producto: ");
			this.l_producto.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_producto.setBounds(32, 119, 70, 26);
			this.getContentPane().add(this.l_producto);
			
			this.l_cantNec = new JLabel("*Par Stock: ");
			this.l_cantNec.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_cantNec.setBounds(32, 193, 121, 26);
			this.getContentPane().add(this.l_cantNec);
			this.header = new JTextArea("Ingresar datos del nuevo insumo. \nCampos señalados con * son obligatorios.");
			this.header.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.header.setEditable(false);
			this.header.setBounds(22, 24, 323, 37);
			this.getContentPane().add(this.header);
			
			this.l_cantActual = new JLabel("Existencia: ");
			this.l_cantActual.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_cantActual.setBounds(32, 156, 121, 26);
			this.getContentPane().add(this.l_cantActual);
			
			this.tf_cantNec = new JTextField();
			this.tf_cantNec.setColumns(10);
			this.tf_cantNec.setBounds(163, 197, 182, 20);
			this.getContentPane().add(this.tf_cantNec);
			
			this.tf_cantActual = new JTextField();
			this.tf_cantActual.setColumns(10);
			this.tf_cantActual.setBounds(163, 160, 182, 20);
			this.getContentPane().add(this.tf_cantActual);
			
			this.producto = "";

			this.aceptar.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					String temp = tf_cantActual.getText();
					producto = String.valueOf(menu_productos.getSelectedItem());
					cantNec = tf_cantNec.getText();
					if(!cantNec.equals("") && !producto.equals("")){
						cantActual = temp.equals("")?0:Integer.parseInt(temp);
						db.insertarInsumo(currentAlmacen, producto, Integer.parseInt(cantNec),cantActual);
						setData();
						updateTable(num);
						closeWindow();
					} else if(producto.equals("") && tf_cantActual.getText().equals("") && cantNec.equals(""))
						closeWindow();
					else if(cantNec.equals("") || producto.equals(""))
						JOptionPane.showMessageDialog(null, "Llenar campos obligatorios");
					
				}
			});
			
			menu_productos = new JComboBox<String>(db.getViewGerenteInsumoItems(currentAlmacen));
			menu_productos.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			menu_productos.setBackground(Color.WHITE);
			menu_productos.setBounds(107, 123, 238, 20);
			menu_productos.setSelectedIndex(-1);
			getContentPane().add(menu_productos);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() != this.table){
			this.remove(delete);
			this.add(delete_disabled);
			this.revalidate();
			this.repaint();
		}
		if(e.getSource() == this.edit){
			Almacen update = new Almacen("update");
		} else if(e.getSource() == this.insertar){
			Insumo insumo = new Insumo();
		}
		else if(e.getSource() == this.delete){
			db.delete_insumo(currentAlmacen, table.table.getValueAt(table.table.getSelectedRow(), 0).toString());
			table.removeRow(table.table.getSelectedRow());
		}
	}

}
