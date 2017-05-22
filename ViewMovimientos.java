import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

public class ViewMovimientos extends MainView{
	
	private static final long serialVersionUID = 1L;
	protected final int num = 5;
	private JComboBox<String> menu_almacenes, menu_fechas;
	private String[] items_almacenes, items_fechas;
	private boolean almacen, fecha;
	private int currentAlmacen;

	public ViewMovimientos(){
		super("Movimientos");
		String[] columnNames = {"Almacenista","Almacén", "Producto" ,"Cantidad", "Fecha"};
		this.setData();
		this.columnNames = columnNames;
		this.addTable(num);
		this.delete.setVisible(false);
		this.remove(this.insertar);
		this.remove(this.delete_disabled);
		this.setItems();
		this.menu_almacenes = new JComboBox<String>(this.items_almacenes);
		this.menu_fechas = new JComboBox<String>(this.items_fechas);
		this.menu_almacenes.setBounds(47, 107, 220, 20);
		this.menu_fechas.setBounds(300, 107, 220, 20);
		this.setMenuItems(this.menu_almacenes);
		this.setMenuItems(this.menu_fechas);
		this.almacen = false;
		this.fecha = false;
		this.currentAlmacen = 0;
	}
	
	public void setData(){
		this.data = db.getViewGerenteMov();
	}
	
	public void setData(String sql){
		this.data = db.getViewGerenteMov(sql);
	}
	
	public void setItems(){
		this.items_almacenes = db.getViewGerenteAlmacenItems();
		this.items_fechas = db.getViewGerenteFechas();
	}
	
	public void setMenuItems(JComboBox<String> menu){
		menu.addItem("Seleccionar todo");
		menu.setBackground(new Color(255, 255, 255));
		menu.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
		menu.setSelectedIndex(-1);
		menu.addActionListener(this);
		add(menu);
	}
	
	public void actionPerformed(ActionEvent e){
		String selectedA = String.valueOf(menu_almacenes.getSelectedItem());
		String selectedF = String.valueOf(menu_fechas.getSelectedItem());
		if(e.getSource() == this.menu_almacenes){
			boolean all = selectedA.equals("Seleccionar todo");
			this.almacen = all?false:true;
			this.currentAlmacen = all?0:db.getNumAlmacen(selectedA);
		}
		if(e.getSource() == this.menu_fechas)
			fecha = selectedF.equals("Seleccionar todo")?false:true;
		if(e.getSource() == this.menu_almacenes || e.getSource() == this.menu_fechas){
			if(almacen && fecha)
				this.setData("select * from gerente_movimientos where trunc(fecha) = to_date('" + selectedF + "','yyyy-mm-dd') and almacen='" + selectedA + "'");
			else if(almacen == false && fecha)
				this.setData("select * from gerente_movimientos where trunc(fecha) = to_date('" + selectedF + "','yyyy-mm-dd')");
			else if(fecha == false && almacen)
				this.setData("select * from gerente_movimientos where almacen='" + selectedA + "'");
			else
				this.setData();
			updateTable(this.num);
		}
	}
	
}
