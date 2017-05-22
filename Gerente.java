
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
public class Gerente extends JFrame {

	private JPanel sidebar, almacenes, almacenistas, navbar, current, insumos, proveedores, movimientos;
	private JButton b_almacenes, b_almacenistas, b_insumos, b_proveedores, b_logout, b_movimientos;
	private JLabel user, name;
	private String[] gerenteInfo;
	private Database db;
	private Integer clave;
	private int currentNum;

	public Gerente(String clave, Login l) {
		super("Al Dente DB - Gerente");
		initialize(clave, l);
	}

	private void initialize(String clave, Login l) {
		this.setBounds(10, 10, 1000, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setResizable(false);

		this.db = new Database();

		this.gerenteInfo = db.getGerenteInfo(clave);
		this.clave = Integer.parseInt(this.gerenteInfo[0]);
		this.almacenes = new ViewAlmacenes();
		this.almacenistas = new ViewAlmacenistas(this.clave);
		this.insumos = new ViewInsumos(this.clave);
		this.proveedores = new ViewProveedores();
		this.movimientos = new ViewMovimientos();
		this.current = this.almacenes;
		this.currentNum = 1;
		this.current.setBounds(145, 50, 855, 650);
		this.getContentPane().add(this.current);
		this.navbar = new JPanel();
		this.navbar.setBackground(new Color(0, 51, 204));
		this.navbar.setBounds(0, 0, 1000, 50);
		this.getContentPane().add(this.navbar);
		this.navbar.setLayout(null);

		this.b_logout = new JButton("Cerrar sesión");
		this.b_logout.setBounds(858, 11, 116, 27);

		this.sidebar = new JPanel();
		this.sidebar.setBackground(new Color(0, 0, 153));
		this.sidebar.setBounds(0, 50, 145, 650);
		this.getContentPane().add(this.sidebar);
		this.sidebar.setLayout(null);

		this.b_almacenes = new JButton("Almacenes");
		b_almacenes.setHorizontalAlignment(SwingConstants.LEFT);
		this.b_almacenes.setBounds(10, 207, 125, 34);

		this.b_almacenistas = new JButton("Almacenistas");
		b_almacenistas.setHorizontalAlignment(SwingConstants.LEFT);
		this.b_almacenistas.setBounds(10, 252, 125, 34);

		this.b_insumos = new JButton("Insumos");
		b_insumos.setHorizontalAlignment(SwingConstants.LEFT);
		this.b_insumos.setBounds(10, 297, 125, 34);

		this.b_proveedores = new JButton("Proveedores");
		b_proveedores.setHorizontalAlignment(SwingConstants.LEFT);
		this.b_proveedores.setBounds(10, 342, 125, 34);

		this.b_movimientos = new JButton("Inspeccionar");
		b_movimientos.setHorizontalAlignment(SwingConstants.LEFT);
		this.b_movimientos.setBounds(10, 387, 125, 34);

		JButton[] buttons = {this.b_almacenes, this.b_almacenistas, this.b_insumos, this.b_proveedores, this.b_movimientos, this.b_logout};

		for(JButton i: buttons){
			i.setBackground(new Color(0, 51, 204));
			i.setBorderPainted(false);
			i.setFocusPainted(false);
			i.setOpaque(false);
			i.setForeground(Color.WHITE);
			i.addMouseListener(new MouseListener(){
				public void mouseClicked(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {
					i.setOpaque(true);
				}
				public void mouseExited(MouseEvent e) {
					i.setOpaque(false);
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {
					JPanel newPanel;
					if(i == b_almacenes){
						newPanel = almacenes;
						((ViewAlmacenes) almacenes).setData();
						currentNum = 1;
					}
					else if(i == b_almacenistas){
						newPanel = almacenistas;
						((ViewAlmacenistas) almacenistas).setData();
						currentNum = 2;
					}
					else if(i == b_insumos){
						newPanel = insumos;
						((ViewInsumos) insumos).setData();
						currentNum = 3;
					}
					else if(i == b_proveedores){
						newPanel = proveedores;
						((ViewProveedores) proveedores).setData();
						currentNum = 4;
					}
					else if(i == b_movimientos){
						newPanel = movimientos;
						((ViewMovimientos) movimientos).setData();
						currentNum = 5;
					}
					else{
						newPanel = current;
					}

					if(newPanel != current){

						getContentPane().remove(current);
						current = newPanel;
						current.setBounds(145, 50, 855, 650);
						((MainView) current).updateTable(currentNum);
						getContentPane().add(current);
						getContentPane().revalidate();
						getContentPane().repaint();
					}
				}
			});
		}

		this.b_logout.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {
				l.setVisible(true);
				l.setEmptyClave();
				dispose();
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			
		});
		this.sidebar.add(this.b_almacenes);
		this.sidebar.add(this.b_almacenistas);
		this.sidebar.add(this.b_insumos);
		this.sidebar.add(this.b_proveedores);
		this.sidebar.add(this.b_movimientos);
		this.navbar.add(this.b_logout);
		this.b_logout.setBackground(new Color(0, 0, 153));
		this.name = new JLabel(this.gerenteInfo[1].toString());
		name.setFont(new Font("Microsoft JhengHei UI Light", Font.PLAIN, 14));
		name.setBounds(90, 11, 470, 27);
		navbar.add(name);
		this.name.setForeground(Color.WHITE);

		this.user = new JLabel("Gerente:");
		user.setFont(new Font("Microsoft JhengHei UI Light", Font.PLAIN, 14));
		user.setBounds(25, 11, 55, 27);
		navbar.add(user);
		this.user.setForeground(Color.WHITE);

		this.setVisible(true);

	}

}
