package ar.edu.unicen.exa.spots;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.Iterator;

import javax.swing.ImageIcon;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.filter.ElementFilter;
import org.jdom.input.SAXBuilder;

/** 
 * Esta clase maneja la carga de un spot de información.
 * 
 * @author Pablo Vizcay
 */
public class InformationSpot {

	private String routeToXML = null;

	public InformationSpot(String routeToXML){
		this.routeToXML = routeToXML;
	}

	public void loadInformationSpot(){
		try {
			SAXBuilder saxbuilder = new SAXBuilder();
			Document doc;

			doc = saxbuilder.build(routeToXML);

			Element e = doc.getRootElement();
			String name = e.getChildTextTrim("name");
			Integer width = new Integer(e.getChildTextTrim("width"));
			Integer height = new Integer(e.getChildTextTrim("height"));
			String builderclass = e.getChildTextTrim("builder");

			//Initialize builder by reflection
			Class[] types = new Class[] { String.class, Integer.class, Integer.class };
			Constructor cons = Class.forName(builderclass).getConstructor(types);
			Object[] args = new Object[] {name, width, height};
			AbstractSpotBuilder builder = (AbstractSpotBuilder)(cons.newInstance(args));

			//Set background
			String backgroundPath = e.getChildTextTrim("backgroundPath");
			builder.setBackground(backgroundPath);

			//Parse buttons
			Element buttons = e.getChild("buttons");        
			Iterator itr = buttons.getDescendants(new ElementFilter());
			while (itr.hasNext()) {
				Element b = (Element) itr.next();

				Class actionClass = Class.forName(b.getAttributeValue("reference"));         	
				ActionListener actionObject = (ActionListener)actionClass.newInstance(); 

				if(b.getAttributeValue("text") != null){
					builder.addButton(b.getAttributeValue("text"), 
							Integer.parseInt(b.getAttributeValue("width")), 
							Integer.parseInt(b.getAttributeValue("height")), 
							Integer.parseInt(b.getAttributeValue("positionx")),
							Integer.parseInt(b.getAttributeValue("positiony")), 
							actionObject);

				} else if(b.getAttributeValue("image") != null){
					builder.addButton(new ImageIcon(b.getAttributeValue("image")), 
							Integer.parseInt(b.getAttributeValue("width")), 
							Integer.parseInt(b.getAttributeValue("height")), 
							Integer.parseInt(b.getAttributeValue("positionx")),
							Integer.parseInt(b.getAttributeValue("positiony")), 
							actionObject);
				}
			}
			builder.getOutput().setVisible(true);
			builder.getOutput().setEnabled(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
