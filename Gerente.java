package vistas;
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Gerente extends JFrame {

	private JPanel sidebar, almacenes, almacenistas, navbar, current;
	private JButton b_almacenes, b_almacenistas, b_insumos, b_proveedores, b_logout;
	private JLabel user, name;
	private String[] gerenteInfo;
	private Database db;
	private JLabel label;
	private JScrollPane scrollPane;
	private Integer clave;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gerente window = new Gerente();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Gerente() {
		super("Restaurant Database");
		initialize();
	}

	private void initialize() {	
		this.setBounds(10, 10, 1000, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setResizable(false);
		
		this.db = new Database();
		
		this.gerenteInfo = db.getGerenteInfo();
		this.clave = Integer.parseInt(this.gerenteInfo[0]);
		this.almacenes = new ViewAlmacenes();
		this.almacenistas = new ViewAlmacenistas(this.clave);
		this.current = this.almacenes;
		this.navbar = new JPanel();
		this.navbar.setBackground(new Color(0, 51, 204));
		this.navbar.setBounds(0, 0, 985, 50);
		this.getContentPane().add(this.navbar);
		this.navbar.setLayout(null);
		
		this.b_logout = new JButton("Cerrar sesión");
		this.b_logout.setBounds(858, 11, 116, 27);
		
		this.sidebar = new JPanel();
		this.sidebar.setBackground(new Color(0, 0, 153));
		this.sidebar.setBounds(0, 50, 145, 611);
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
		
		JButton[] buttons = {this.b_almacenes, this.b_almacenistas, this.b_insumos, this.b_proveedores, this.b_logout};
		
		for(JButton i: buttons){
			i.setBackground(new Color(0, 51, 204));
			i.setBorderPainted(false);
			i.setFocusPainted(false);
			i.setOpaque(false);
			i.setForeground(Color.WHITE);
			i.addMouseListener(new MouseListener(){
				public void mouseClicked(MouseEvent e) {
					JPanel newPanel;
					if(i == b_almacenes){
						newPanel = almacenes;
					}
					else if(i == b_almacenistas){
						newPanel = almacenistas;
					}
					else{
						newPanel = current;
					}
					
					if(newPanel != current){
						scrollPane.remove(current);
						addScroll(newPanel);
						getContentPane().revalidate();
						getContentPane().repaint();
			            current = newPanel;

					}	
				}
				public void mouseEntered(MouseEvent e) {
					i.setOpaque(true);
				}
				public void mouseExited(MouseEvent e) {
					i.setOpaque(false);
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
			});
		}
		
		this.sidebar.add(this.b_almacenes);
		this.sidebar.add(this.b_almacenistas);
		this.sidebar.add(this.b_insumos);
		this.sidebar.add(this.b_proveedores);
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
		this.scrollPane = new JScrollPane();
		this.scrollPane.setBounds(144, 47, 840, 620);
		this.addScroll(this.current);
		this.getContentPane().add(scrollPane);
		this.setVisible(true);
		
	}
	
	public void addScroll(JPanel panel){
		scrollPane.setViewportView(panel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	}
}
