import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AlmacenistaView extends MainView{
	
	private static final long serialVersionUID = 1L;
	private JLabel nombre, empresa, direccion;
	private String[] menu_items, info;
	private int currentAlmacen;
	protected final int num = 6;
	
	public AlmacenistaView(){
		super("Almacen");
		this.delete.setVisible(false);
		this.remove(this.insertar);
		this.remove(this.delete_disabled);
		
		this.nombre = new JLabel();
		this.nombre.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		this.nombre.setBounds(54, 100, 729, 19);
		this.add(this.nombre);
		
		this.empresa = new JLabel();
		this.empresa.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		this.empresa.setBounds(54, 125, 729, 19);
		this.add(this.empresa);
		
		this.direccion = new JLabel();
		this.direccion.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		this.direccion.setBounds(54, 151, 729, 19);
		this.add(this.direccion);
		
		this.currentAlmacen = 1;
		String[] columnNames ={"Producto","Par Stock", "Existencia" ,"Estado"};
		this.columnNames = columnNames;
		this.setInfo();
	}
	
	public void setData(){
		this.data = db.getViewGerenteAlmacenInsumos(this.currentAlmacen);
	}
	
	public void setMenuItems(){
		this.menu_items = db.getViewGerenteAlmacenItems();
	}
	
	public void setCurrentAlmacen(int c){
		this.currentAlmacen = c;
		remove(table);
		setInfo();
		revalidate();
		repaint();
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
		case 2: 
			nuevaCantidad = Integer.parseInt(table.newValue);
			db.update_cantidad("actual",currentAlmacen, producto , nuevaCantidad);
			break;
		}
        setData();
        updateTable(num);
	}
}
