package ar.edu.unicen.exa.spots;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

import ar.edu.unicen.exa.spots.util.GuiUtils;

/** 
 * Builder abstracto para el spot. Se proveen implementaciones para imagenes (png, gif, etc)
 * y xhtml.
 * 
 * @author Joaquín Alejandro Pérez Fuentes
 */
public abstract class AbstractSpotBuilder {

	/**
	 * El dialogo de salida
	 */
	protected JDialog output;
	protected int zOrder = 0;
	
	/**
	 * Constructor de la clase
	 * @param name Nombre del dialogo
	 * @param width Ancho del dialogo
	 * @param height Alto del dialogo
	 */
	public AbstractSpotBuilder(String name, Integer width, Integer height){
		output = new JDialog(new JFrame(), name);
		output.setSize(width,height);
		output.getContentPane().setLayout(null);
		output.setUndecorated(true);
		GuiUtils.centerOnScreen(output);
	}
	
	/**
	 * Setea el fondo del dialogo basado en el archivo apuntado por input.
	 * @param input Archivo a utilizar de fondo, varia segun implementaciones
	 */
	public abstract void setBackground(String input);
	
	/**
	 * Agrega un boton de texto al dialogo
	 * @param text El texto a presentar en el boton
	 * @param width El ancho del boton
	 * @param height El alto del boton
	 * @param posx La posicion x del boton
	 * @param posy La posicion y del boton
	 * @param listener listener el ActionListener asociado al click del mouse 
	 */
	public void addButton(String text,int width,int height,int posx,int posy,
			ActionListener listener){

		SpotButton jbtn = new SpotButton(text);
		jbtn.setBounds(posx,posy,width,height);
		jbtn.addActionListener(listener);				
		jbtn.setParentframe(output);
		
		output.getContentPane().add(jbtn);
		output.getContentPane().setComponentZOrder(jbtn, zOrder);		
		
		zOrder++;
	}
	
	/**
	 * Agrega un boton grafico al dialogo
	 * @param i La imagen del boton
	 * @param width El ancho del boton
	 * @param height El alto del boton
	 * @param posx La posicion x del boton
	 * @param posy La posicion y del boton
	 * @param listener listener el ActionListener asociado al click del mouse 
	 */
	public void addButton(ImageIcon i,int width,int height,int posx,int posy,
			ActionListener listener){	
		SpotButton jbtn = new SpotButton(i);
		jbtn.setBounds(posx,posy,width,height);
		jbtn.addActionListener(listener);	
		
		jbtn.setOpaque(false);
		jbtn.setFocusPainted(false);
		jbtn.setBorderPainted(false);
		jbtn.setContentAreaFilled(false);
		jbtn.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));			
		jbtn.setParentframe(output);
        
		output.getContentPane().add(jbtn);
		output.getContentPane().setComponentZOrder(jbtn, zOrder);
		
		zOrder++;
	}
	
	public JDialog getOutput() {
		return output;
	}
	
	public void setOutput(JDialog output) {
		this.output = output;
	}
}
