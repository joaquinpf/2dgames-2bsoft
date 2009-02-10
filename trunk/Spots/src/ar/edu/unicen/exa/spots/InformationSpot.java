package ar.edu.unicen.exa.spots;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.filter.ElementFilter;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;

import org.xhtmlrenderer.simple.FSScrollPane;
import org.xhtmlrenderer.simple.XHTMLPanel;


public class InformationSpot {
	public static void main(String[] args) throws Exception {
		SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build("spot/spot.xml");
        
        Element e = doc.getRootElement();
        
        String name = e.getChildTextTrim("name");
        String width = e.getChildTextTrim("width");
        String height = e.getChildTextTrim("height");
        String xhtmlPath = e.getChildTextTrim("xhtmlPath");
        
        JDialog frame = new JDialog(new JFrame(), name);
        frame.setSize(Integer.parseInt(width), Integer.parseInt(height));
        frame.getContentPane().setLayout(null);
        
	    XHTMLPanel panel = new XHTMLPanel();
	    panel.setDocument(new File(xhtmlPath));
	    panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
	    
//	    FSScrollPane scroll = new FSScrollPane(panel);
//	    scroll.setBounds(0, 0, frame.getWidth(), frame.getHeight());	    
//	    frame.getContentPane().add(scroll);
	    
	    frame.getContentPane().add(panel);
	    	    
	    Integer zOrder = 0;
	    Element buttons = e.getChild("buttons");        
        Iterator itr = buttons.getDescendants(new ElementFilter());
        while (itr.hasNext()) {
        	Element b = (Element) itr.next();
        	
        	JButton jbtn = new JButton(b.getAttributeValue("text"));
        	jbtn.setBounds(Integer.parseInt(b.getAttributeValue("positionx")),
        				   Integer.parseInt(b.getAttributeValue("positiony")),
        				   Integer.parseInt(b.getAttributeValue("width")),
        				   Integer.parseInt(b.getAttributeValue("height")));
        	
        	frame.getContentPane().add(jbtn);
        	frame.getContentPane().setComponentZOrder(jbtn, zOrder);
        	
        	zOrder = zOrder + 1;
        	
        	Class actionClass = Class.forName(b.getAttributeValue("reference"));         	
        	Object actionObject = actionClass.newInstance(); 
        	jbtn.addActionListener((ActionListener) actionObject);
        }
               
        frame.getContentPane().setComponentZOrder(panel, zOrder);
        
        frame.setVisible(true);        
	}
}
