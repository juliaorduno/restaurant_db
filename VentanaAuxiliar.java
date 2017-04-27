package vistas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class VentanaAuxiliar extends JFrame{
	protected JButton cancelar, aceptar;
	
	public VentanaAuxiliar(){
		super();
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);
		this.setVisible(true);
		
		this.cancelar = new JButton("Cancelar");
		this.cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeWindow();
			}
		});
		this.cancelar.setBounds(256, 244, 89, 23);
		this.cancelar.setBackground(new Color(0, 51, 204));
		this.cancelar.setBorderPainted(false);
		this.cancelar.setFocusPainted(false);
		this.cancelar.setForeground(Color.WHITE);
		this.getContentPane().add(this.cancelar);
		this.aceptar = new JButton("Aceptar");
		this.aceptar.setForeground(Color.WHITE);
		this.aceptar.setFocusPainted(false);
		this.aceptar.setBorderPainted(false);
		this.aceptar.setBackground(new Color(0, 51, 204));
		this.aceptar.setBounds(157, 244, 89, 23);
		this.getContentPane().add(this.aceptar);
	}
	
	public VentanaAuxiliar(String title){
		this();
		this.setTitle(title);
	}

	
	
	public void closeWindow(){
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
}
