import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.engine.BaseLoader;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.util.ImageUtil;


/**
 * Clase encargada de la administración de los niveles del juego.
 */
public class LevelGenerator {

	/**
	 * @uml.property  name="configRoute"
	 */
	private String configRoute = "";
	
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
	private BufferedImage getImage(BaseLoader bsLoader,String imagefile) {
    	return bsLoader.getImage(imagefile);
    }
	
	/**
	 * @param bsLoader
	 * @param imagefile
	 * @return  BufferedImage[]
	 */
	private BufferedImage[] getImages(GameEngine gameEnginge, String imagefile) {
        BufferedImage[] images = new BufferedImage[5];
        images[0] = ImageUtil.getImage(gameEnginge.bsIO.getURL("Resources/images/card_back.png"),Transparency.TRANSLUCENT);
        BufferedImage[] aux = ImageUtil.getImages(gameEnginge.bsIO.getURL("Resources/images/card_flip.png"), 3, 1,Transparency.TRANSLUCENT);
        images[1] = aux[0];
        images[2] = aux[1];
        images[3] = aux[2];
        images[4] = ImageUtil.getImage(gameEnginge.bsIO.getURL(imagefile),Transparency.TRANSLUCENT);
        return images;
	}

	/**
	 * Este método genera el siguiente nivel al que avanza el juego. 
	 * Si el archivo xml de configuración del juego es válido retorna el 
	 * siguiente nivel, en caso contrario retorna null 
	 * @return  Level
	 */
	public Level generarLevel(GameEngine parent, int levelNumber) {
		int cantidadPares = 0;
		Level level = new Level(parent);
			        
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
		      if ((tagLevel.getAttribute("ID").getIntValue() == levelNumber)) {
		    	  level.setRemainingTimeLevel(tagLevel.getAttribute("time").getIntValue());
		    	  cantidadPares = tagLevel.getAttribute("pairs").getIntValue();
		    	  level.setTableSize(cantidadPares * 2);
		    	  Background background = new ImageBackground(this.getImage(parent.bsLoader,tagLevel.getAttribute("background").getValue())); 			
                  level.setBackground(background);
  		          level.setLevelNumber(levelNumber);
  		      }
		    }
		    for (int i = 1; i <= cantidadPares; i++) {
		    	 Element tagCard = (Element) childrensCards.get(i);
		    	 Card card = new Card();
		    	 card.setValue(tagCard.getAttribute("value").getValue());
		    	 card.setImages(this.getImages(parent,tagCard.getAttribute("image").getValue()));
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

}
