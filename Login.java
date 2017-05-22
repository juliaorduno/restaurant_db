import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.sql.*;

import java.util.Properties;

@SuppressWarnings({ "deprecation", "serial", "unused" })
public class Login extends JFrame{
	
	private JPasswordField clave;
	private JPanel panelNumeros;
	private int password;
	private Database db;
	private String[] clavesGerente, clavesAlmacenista;
	private boolean nuevoIntento, claveGerente, claveAlmacenista;	
	
	public Login(){
		super("Al Dente DB");
		
		db = new Database();
		
		this.nuevoIntento = true;
		this.claveGerente = false;
		this.claveAlmacenista = false;
		this.clavesGerente = db.getClavesGerente();
		this.clavesAlmacenista = db.getClavesAlmacenista();
		
		this.setSize(600, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		JPanel panel = (JPanel) this.getContentPane();
		panel.setLayout(new BorderLayout());
		
		this.clave = new JPasswordField("", 4);
		this.clave.setBorder(new EmptyBorder(4, 4, 4, 4));
		this.clave.setFont(new Font("Arial", Font.BOLD, 50));
		this.clave.setHorizontalAlignment(JTextField.CENTER);
		this.clave.setEditable(false);
		this.clave.setBackground(new Color(206,206,206));
		panel.add("North", clave);
		
		this.panelNumeros = new JPanel();
		this.panelNumeros.setLayout(new GridLayout(4, 3));
		this.panelNumeros.setBorder(new EmptyBorder(4, 4, 4, 4));
		this.panelNumeros.setBackground(new Color(12,90,168));
		
		for (int n = 9; n >= 1; n--) {
			nuevoBotonNumerico("" + n);
		}
		
		nuevoBotonOperacion("Delete");
		nuevoBotonNumerico("0");
		nuevoBotonOperacion("Enter");
		
		panel.add("Center", panelNumeros);
		
		validate();
	}
	
	private void nuevoBotonNumerico(String digito){
		JButton btn = new JButton();
		btn.setText(digito);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setBackground(new Color(21,106,191));
		btn.setOpaque(false);
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Arial", Font.BOLD, 50));
		btn.addMouseListener(new MouseAdapter() {
			
			public void mouseReleased(MouseEvent e){
				JButton btn = (JButton) e.getSource();
				numeroPulsado(btn.getText());
			}
			
			public void mouseEntered(MouseEvent e) {
				btn.setOpaque(true);
			}
			public void mouseExited(MouseEvent e) {
				btn.setOpaque(false);
			}
						
		});
		
		this.panelNumeros.add(btn);
	}
	
	private void nuevoBotonOperacion(String operacion){
		JButton btn = new JButton(operacion);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setBackground(new Color(21,106,191));
		btn.setOpaque(false);
		btn.setFont(new Font("Arial", Font.PLAIN, 30));
		if (operacion == "Enter"){
			btn.setForeground(Color.GREEN);
		}
		else if (operacion == "Delete"){
			btn.setForeground(Color.RED);
		}
		
		btn.addMouseListener(new MouseAdapter(){
			
			public void mouseReleased(MouseEvent e){
				JButton btn = (JButton) e.getSource();
				operacionPulsado(btn.getText());
			}
			
			public void mouseEntered(MouseEvent e) {
				btn.setOpaque(true);
			}
			public void mouseExited(MouseEvent e) {
				btn.setOpaque(false);
			}
			
		});
		
		this.panelNumeros.add(btn);
	}

	private void numeroPulsado(String digito){
		if(this.clave.getText().length() < 4){
			if(this.clave.getText().equals("") || this.nuevoIntento){
				this.clave.setText(digito);
			}
			else{
				this.clave.setText(clave.getText() + digito);
			}
			
			this.nuevoIntento = false;
		}
	}
	
	private void operacionPulsado(String tecla){
		if(tecla == "Delete"){
			if (this.clave.getText().equals("") || this.nuevoIntento){}
			else if (this.clave.getText().length() == 1){
				this.clave.setText("");
				this.nuevoIntento = true;
			}
			else if (this.clave.getText().length() > 1){
				this.clave.setText(this.clave.getText().substring(0, this.clave.getText().length() - 1));
			}
		}
		else if(tecla == "Enter"){
			this.claveGerente = false;
			this.claveAlmacenista= false;
			for(int i = 0; i < this.clavesGerente.length; i++){
				if (this.clave.getText().equals(this.clavesGerente[i])){
					this.claveGerente = true;
					break;
				}
			}
			for(int i = 0; i < this.clavesAlmacenista.length; i++){
				if(this.clave.getText().equals(this.clavesAlmacenista[i])){
					this.claveAlmacenista = true;
					break;
				}
			}
			
			if(this.claveGerente){
				Gerente g = new Gerente(this.clave.getText(), this);
				this.setVisible(false);
				System.out.println("IT EXISTS IN TABLE GERENTE!!");
			}
			else if (this.claveAlmacenista){
				Almacenista a = new Almacenista(this.clave.getText(), this);
				this.setVisible(false);
				System.out.println("IT EXISTS IN TABLE ALMACENISTA!!");
			}
			else{
				nuevoIntento = true;
				clave.setText("");
				System.out.println("IT DOESN'T EXIST!");
			}
		}
	}
	
	public void setEmptyClave(){
		this.clave.setText("");
		this.nuevoIntento = true;
	}
	
	public static void main(String[] args) {
		Login start = new Login();
		start.setVisible(true);
	}
	
}
