import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.engine.BaseIO;
import com.golden.gamedev.engine.BaseLoader;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.background.ImageBackground;


/**
 * Clase encargada de la administración de los niveles del juego.
 */
public class LevelGenerator {

	/**
	 * @uml.property  name="configRoute"
	 */
	private String configRoute = "";
	
	/**
	 * @uml.property  name="bsLoader"
	 */
	private BaseLoader bsLoader;

	/**
	 * Getter of the property <tt>configRoute</tt>
	 * @return  Returns the configRoute.
	 * @uml.property  name="configRoute"
	 */
	public String getConfigRoute() {
		return configRoute;
	}

	/**
	 * Setter of the property <tt>configRoute</tt>
	 * @param configRoute  The configRoute to set.
	 * @uml.property  name="configRoute"
	 */
	public void setConfigRoute(String configRoute) {
		this.configRoute = configRoute;
		  this.bsLoader = new BaseLoader(new BaseIO(this.getClass()), 
				  Color.MAGENTA);
	}


	/**
	 * Constructor de la clase
	 */
	public LevelGenerator(String _route) {
		this.configRoute = _route;
	}
	
	/**
	 * @return  BufferedImage
	 */
	private BufferedImage getImage(String imagefile) {
         return this.bsLoader.getImage(imagefile);
	}

	/**
	 * Este método genera el siguiente nivel al que avanza el juego. 
	 * Si el archivo xml de configuración del juego es válido retorna el 
	 * siguiente nivel, en caso contrario retorna null 
	 * @return  Level
	 */
	public Level generateLevel(GameEngine parent) {
		int cantidadPares = 0;
		Level level = new Level(parent);
		this.currentLevel += 1;
	        
		SAXBuilder builder = new SAXBuilder(true);
			
        try {
	        Document doc = builder.build(new File(this.getConfigRoute()));
	        Element root = doc.getRootElement();  
		    List child = root.getChildren();
		    List childrensConfig = ((Element) child.get(1)).getChildren();                        
		    List childrensLevels = ((Element) childrensConfig.get(0)).getChildren();
		    List childrensCards =  ((Element) childrensConfig.get(1)).getChildren();  
		    for (Iterator iterator = childrensLevels.iterator();
		    	 iterator.hasNext();) {	
		      Element tagLevel = (Element) iterator.next();
		      if ((tagLevel.getAttribute("ID").getIntValue() == this.currentLevel)) {
		    	  level.setRemainingTimeLevel(tagLevel.getAttribute("time").getIntValue());
		    	  cantidadPares = tagLevel.getAttribute("pairs").getIntValue();
		    	  level.setTableSize(cantidadPares * 2);
		    	  Background background = new ImageBackground(this.getImage(tagLevel.getAttribute("backGround").getValue())); 			
                  level.setBackground(background);
  		          level.setLevelNumber(this.currentLevel);
  		      }
		    }
		    for (int i = 1; i <= cantidadPares; i++) {
		    	 Element tagCard = (Element) childrensCards.get(i);
		    	 Card card = new Card();
	             card.setImage(this.getImage(tagCard.getAttribute("images").getValue()));  
	             level.addCard(card);
		    }
		    return level;
		} catch (JDOMException jdomException) {
	        System.out.println("Documento XML mal formado o incorrecto.");
	        jdomException.printStackTrace();
	    } catch (Exception exception) {
	    	exception.printStackTrace();
	    }
	    return null;
	}


	/**
	 * @uml.property  name="currentLevel"
	 */
	private int currentLevel = 0;

	/**
	 * Getter of the property <tt>currentLevel</tt>
	 * @return  Returns the currentLevel.
	 * @uml.property  name="currentLevel"
	 */
	public int getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * Setter of the property <tt>currentLevel</tt>
	 * @param currentLevel  The currentLevel to set.
	 * @uml.property  name="currentLevel"
	 */
	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}
}
