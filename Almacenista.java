import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Almacenista extends JFrame{

	private JPanel sidebar, almacenes, navbar;
	private JButton b_logout;
	private JLabel user, name;
	private String[] almacenistaInfo, almacenesItems;
	private Database db;
	private Integer clave;
	private int currentAlmacen;
	
	public Almacenista(String clave, Login l){
		super("Al Dente DB - Almacenista");
		initialize(clave, l);
	}
	
	private void initialize(String clave, Login l){
		this.setBounds(10,10,1055,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setResizable(false);
		
		this.db = new Database();
		
		this.almacenistaInfo = db.getAlmacenistaInfo(clave);
		this.clave = Integer.parseInt(this.almacenistaInfo[0]);
		this.almacenes = new AlmacenistaView();
		this.almacenes.setBounds(200, 50, 910, 650);
		this.getContentPane().add(this.almacenes);
		this.navbar = new JPanel();
		this.navbar.setBackground(new Color(0, 51, 204));
		this.navbar.setBounds(0, 0, 1055, 50);
		this.getContentPane().add(this.navbar);
		this.navbar.setLayout(null);
		this.almacenesItems = db.getViewGerenteAlmacenItems();
		this.currentAlmacen = 1;
		this.db.insert_usario(this.clave, this.currentAlmacen);
		((AlmacenistaView) this.almacenes).setCurrentAlmacen(this.currentAlmacen);
		
		this.b_logout = new JButton("Cerrar sesión");
		this.b_logout.setBounds(908, 11, 116, 27);
		this.b_logout.setBackground(new Color(0, 51, 204));
		this.b_logout.setBorderPainted(false);
		this.b_logout.setFocusPainted(false);
		this.b_logout.setOpaque(false);
		this.b_logout.setForeground(Color.WHITE);
		this.b_logout.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				l.setVisible(true);
				db.delete_usuario(Integer.parseInt(clave));
				l.setEmptyClave();
				dispose();
			}
			
		});
		
		this.sidebar = new JPanel();
		this.sidebar.setBackground(new Color(0, 0, 153));
		this.sidebar.setBounds(0, 50, 200, 650);
		this.getContentPane().add(this.sidebar);
		this.sidebar.setLayout(null);
		
		JButton[] buttons = new JButton[almacenesItems.length];
		for(int i = 0; i < almacenesItems.length; i++){
			if(almacenesItems[i] == null){
				break;
			}
			buttons[i] = nuevoBotonAlmacen(almacenesItems[i]);
		}
		
		for(int i = 0; i < almacenesItems.length; i++){
			buttons[i].setBounds(10, 207 + (i * 45), 180, 34);
			this.sidebar.add(buttons[i]);
		}
		
		this.navbar.add(this.b_logout);
		this.b_logout.setBackground(new Color(0, 0, 153));
		this.name = new JLabel(this.almacenistaInfo[1].toString());
		name.setFont(new Font("Microsoft JhengHei UI Light", Font.PLAIN, 14));
		name.setBounds(135, 11, 470, 27);
		navbar.add(name);
		this.name.setForeground(Color.WHITE);

		this.user = new JLabel("Almacenista:");
		user.setFont(new Font("Microsoft JhengHei UI Light", Font.PLAIN, 14));
		user.setBounds(25, 11, 90, 27);
		navbar.add(user);
		this.user.setForeground(Color.WHITE);

		this.setVisible(true);
	}
	
	private JButton nuevoBotonAlmacen(String nombre){
		JButton btn = new JButton(nombre);
		btn.setHorizontalAlignment(SwingConstants.CENTER);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setBackground(new Color(0, 51, 204));
		btn.setOpaque(false);
		btn.setForeground(Color.WHITE);
		btn.addMouseListener(new MouseAdapter() {
			
			public void mouseReleased(MouseEvent e){
				JButton btn = (JButton) e.getSource();
				String selected = btn.getText();
				currentAlmacen = db.getNumAlmacen(selected);
				db.update_usuario(clave, currentAlmacen);
				((AlmacenistaView) almacenes).setCurrentAlmacen(currentAlmacen);
			}
			
			public void mouseEntered(MouseEvent e) {
				btn.setOpaque(true);
			}
			public void mouseExited(MouseEvent e) {
				btn.setOpaque(false);
			}
			
		});
		
		return btn;
	}
}
