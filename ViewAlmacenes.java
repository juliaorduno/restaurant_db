package vistas;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ViewAlmacenes extends JPanel implements MouseListener{
	private JComboBox menu;
	private JLabel title,nombre, empresa, direccion, edit;
	private Table table;
	private String[] menu_items, info;
	private String[][] data;
	private Database db;
	private JButton deleteInsumo, insertar, nuevoInsumo;
	private int currentAlmacen;

	public ViewAlmacenes(){
		super();
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		this.setPreferredSize(new Dimension(840,614));
		this.db = new Database();
		
		this.title = new JLabel("Almacenes");
		this.title.setFont(new Font("Microsoft JhengHei UI Light", Font.BOLD, 20));
		this.title.setBounds(47, 47, 239, 49);
		add(this.title);

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
		
		this.insertar = new JButton("Nuevo Almacén");
		this.insertar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Almacen nuevoAlmacen = new Almacen("insertar");
			}
		});
		this.insertar.setForeground(new Color(255, 255, 255));
		this.insertar.setBackground(new Color(102, 102, 102));
		this.insertar.setBorderPainted(false);
		this.insertar.setFocusPainted(false);
		this.insertar.setBounds(659, 64, 124, 32);
		add(this.insertar);
		
		deleteInsumo = new JButton("Borrar");
		this.deleteInsumo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				db.delete_insumo(currentAlmacen, table.table.getValueAt(table.table.getSelectedRow(), 0).toString());
				table.removeRow(table.table.getSelectedRow());
			}
		});
		deleteInsumo.setEnabled(false);
		deleteInsumo.setForeground(Color.WHITE);
		deleteInsumo.setBackground(new Color(102, 102, 102));
		this.deleteInsumo.setBorderPainted(false);
		this.deleteInsumo.setFocusPainted(false);
		deleteInsumo.setBounds(659, 107, 124, 32);
		add(deleteInsumo);
		
		this.addMouseListener(this);
		
		this.edit = new JLabel(new ImageIcon(ViewAlmacenes.class.getResource("/vistas/img/pencil.png")));
		this.edit.addMouseListener(this);
		this.edit.setForeground(Color.WHITE);
		this.edit.setBackground(Color.WHITE);
		this.edit.setBounds(724, 180, 38, 39);
		this.add(this.edit);
		
		nuevoInsumo = new JButton("Nuevo Insumo");
		this.nuevoInsumo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Insumo insumo = new Insumo();
			}
		});
		nuevoInsumo.setForeground(Color.WHITE);
		nuevoInsumo.setFocusPainted(false);
		nuevoInsumo.setBorderPainted(false);
		nuevoInsumo.setBackground(new Color(102, 102, 102));
		nuevoInsumo.setBounds(521, 64, 124, 32);
		add(nuevoInsumo);
		
		this.setInfo(1);
		this.getTableChanges();
	}
	
	public void setMenuItems(){
		String[] items = db.getViewGerenteAlmacenItems();
		this.menu_items = items;
		this.menu = new JComboBox(this.menu_items);
		this.menu.setBackground(new Color(255, 255, 255));
		this.menu.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
		this.menu.setBounds(47, 107, 220, 20);
		this.menu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String selected = String.valueOf(menu.getSelectedItem());
				int almacen = db.getNumAlmacen(selected);
				remove(table);
				setInfo(almacen);
				revalidate();
				repaint();
			}
		});
		add(this.menu);
		
	}
	
	public void setData(int selectedItem){
		this.data = db.getViewGerenteAlmacenInsumos(selectedItem);
	}
	
	public void addTable(int selected){
		String[] columnNames = {"Producto","Cantidad Necesaria", "Cantidad Actual" ,"Estado"};
		this.setData(selected);
		this.table = new Table(columnNames, this.data);
		this.table.table.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e)
			{
				deleteInsumo.setEnabled(true);
			}
		});
		this.table.setBounds(54, 245, 730, 338);
		this.add(this.table);
	}
	
	public void setInfo(int nAlmacen){
		this.currentAlmacen = nAlmacen;
		this.info = db.getViewGerenteAlmacenInfo(nAlmacen);
		this.nombre.setText("Nombre: " + this.info[1]);
		this.direccion.setText("Dirección: " + info[3]);
		this.empresa.setText("Empresa: " + info[2]);
		this.add(this.nombre);
		this.add(this.direccion);
		this.add(this.empresa);
		this.addTable(nAlmacen);
	}
	
	public void updateHeader(){
		this.remove(this.menu);
		this.setMenuItems();
		this.setInfo(this.currentAlmacen);
		this.revalidate();
		this.repaint();
	}
	
	public void updateTable(){
		this.remove(this.table);
		this.addTable(this.currentAlmacen);
		this.revalidate();
		this.repaint();
	}
	
	public void getTableChanges(){
		Action action = new AbstractAction()
		{
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
		    {
				
		        TableCellListener tcl = (TableCellListener)e.getSource();
		        table.row = tcl.getRow();
		        table.column = tcl.getColumn();
		        table.oldValue = tcl.getOldValue().toString();
		        table.newValue = tcl.getNewValue().toString();
		        String producto = (String) table.table.getValueAt(table.row, 0);
		        int nuevaCantidad;
		        switch(table.column){
				case 0:
					db.update_producto_nombre(table.oldValue, table.newValue);
					break;
				case 1:
					nuevaCantidad = Integer.parseInt(table.newValue);
					db.update_cantidad("necesaria", currentAlmacen, producto , nuevaCantidad);
					updateTable();
					break;
				case 2: 
					nuevaCantidad = Integer.parseInt(table.newValue);
					db.update_cantidad("actual",currentAlmacen, producto , nuevaCantidad);
					updateTable();
					break;
				}
		    }
		};

		TableCellListener tcl = new TableCellListener(table.table, action);
		
	}
	
	private class Almacen extends VentanaAuxiliar{
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
				this.setTitle("Editar almacén " + info[0]);
			}
			this.setBounds(100, 100, 382, 343);
			
			this.l_numero = new JLabel("*Número: ");
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
						updateHeader();
						closeWindow();
						
					} else if(numero.equals("") && nombre.equals("") && empresa.equals("") && direccion.equals(""))
						closeWindow();
					else if(numero.equals("") || nombre.equals("") || empresa.equals(""))
						JOptionPane.showMessageDialog(null, "Llenar campos obligatorios");
					
				}
			});
		}
		
	}

	private class Insumo extends VentanaAuxiliar{

		private String producto,cantActual;
		private int cantNec;
		private JTextField tf_cantNec, tf_cantActual;
		private JLabel l_cantActual, l_producto, l_cantNec;
		private JTextArea header;
		private JComboBox menu_productos;
		
		public Insumo(){
			super("Nuevo Insumo");
			this.setBounds(100, 100, 382, 343);
			
			this.l_producto = new JLabel("*Producto: ");
			this.l_producto.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_producto.setBounds(32, 119, 70, 26);
			this.getContentPane().add(this.l_producto);
			
			this.l_cantNec = new JLabel("Cantidad Necesaria: ");
			this.l_cantNec.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.l_cantNec.setBounds(32, 193, 121, 26);
			this.getContentPane().add(this.l_cantNec);
			this.header = new JTextArea("Ingresar datos del nuevo insumo. \nCampos señalados con * son obligatorios.");
			this.header.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			this.header.setEditable(false);
			this.header.setBounds(22, 24, 323, 37);
			this.getContentPane().add(this.header);
			
			this.l_cantActual = new JLabel("*Cantidad Actual: ");
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
			
			this.cancelar = new JButton("Cancelar");
			cancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					closeWindow();
				}
			});
			this.cancelar.setBounds(256, 244, 89, 23);
			this.getContentPane().add(this.cancelar);
			this.cancelar.setBackground(new Color(0, 51, 204));
			this.cancelar.setBorderPainted(false);
			this.cancelar.setFocusPainted(false);
			this.cancelar.setForeground(Color.WHITE);
			this.producto = "";

			this.aceptar.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					String temp = tf_cantNec.getText();
					producto = String.valueOf(menu_productos.getSelectedItem());
					cantActual = tf_cantActual.getText();
					if(!cantActual.equals("") && !producto.equals("")){
						cantNec = temp.equals("")?0:Integer.parseInt(temp);
						db.insertarInsumo(currentAlmacen, producto, cantNec, Integer.parseInt(cantActual));
						updateTable();
						closeWindow();
					} else if(producto.equals("") && tf_cantNec.getText().equals("") && cantActual.equals(""))
						closeWindow();
					else if(cantActual.equals("") || producto.equals(""))
						JOptionPane.showMessageDialog(null, "Llenar campos obligatorios");
					
				}
			});
			
			menu_productos = new JComboBox(db.getViewGerenteInsumoItems(currentAlmacen));
			menu_productos.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
			menu_productos.setBackground(Color.WHITE);
			menu_productos.setBounds(107, 123, 238, 20);
			menu_productos.setSelectedIndex(-1);
			getContentPane().add(menu_productos);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() != this.table)
			deleteInsumo.setEnabled(false);
		if(e.getSource() == this.edit){
			Almacen update = new Almacen("update");
		}
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
