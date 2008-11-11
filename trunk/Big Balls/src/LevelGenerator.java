	/**Classname LevelGenerator.
 *
 * Version information 1.0
 * 
 * Date 07/11/2008
 * 
 * Copyright notice
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.golden.gamedev.GameEngine;

/**Esta clase se ocupa de cargar la configuracion de cada nivel del juego.
 * Las configuraciones de los diferentes niveles se leen de un archivo XML
 * pasado como parametro al constructor de la clase.
 */
public class LevelGenerator {
	/**Ruta del archivo de configuracion. 
	 */
	private String configRoute = "";

	/**Nivel actual en juego.
	 */
	private int currentLevel = 0;

	/**Documento parseado que contiene las configuraciones de los niveles.
	 */
	private Document dom;
	
	/**Contiene los elementos del documento de configuracion.
	 */
	private Element docEle;

	/**Constructor de la clase.
	 * @param route Especifica la direccion del archivo de configuracion a ser
	 * utilizado.
	 */

	public LevelGenerator(final String route) {
		this.configRoute = route;
		loadLevels(route);
	}

	/**Retorna el proximo nivel e incrementa en uno currentLevel.
	 * En caso de haber sido el ultimo nivel del juego retorna null.
	 * @param parent The parent gameengine
	 * @return  Level Retorna el proximo nivel del juego.
	 */
	public final Level generateLevel(final GameEngine parent) {
		currentLevel++;
		return getLevel(currentLevel - 1);
	}

	/**Retorna la ruta del archivo de configuracion.
	 * @return  Retorna la ruta de configuracion.
	 */
	public final String getConfigRoute() {
		return configRoute;
	}

	/**Setea la ruta de configuracion de los niveles del juego.
	 * @param confRoute  La ruta de configuracion a setear.
	 */
	public final void setConfigRoute(final String confRoute) {
		this.configRoute = confRoute;
	}

	/**Parsea y retorna el documento de configuracion xml pasado como parametro.
	 * @param route Es la ruta de configuracion de los niveles del juego.
	 * @return Document Retorna el documento xml parseado.
	 */
	private Document openDocument(final String route) {
        Document doc = null;
        DocumentBuilderFactory dbf;
        
		//Obteher el objeto DocumentBuilderFactory
        dbf = DocumentBuilderFactory.newInstance();
		try {
            //Usar DocumentBuilderFactory para crear un DocumentBuilder
            DocumentBuilder db = dbf.newDocumentBuilder();
            //Parsear a partir del archivo
            doc = db.parse(route);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
		return doc;
	}
	
	/**Crea una lista con la cantidad de pelotas solicitada.
	 * @param nlBall NodeList conteniendo la informacion de configuracion de
	 * cada pelota.
	 * @param cant Cantidad de pelotas a generar y agregar a la lista.
	 * @param min Valor minimo posible para el valor de la pelota generada.
	 * @param max Valor maximo posible para el valor de la pelota generada.
	 * @return List Retorna una lista con la cantidad de pelotas
	 * especificadas en el parametro cant.
	 */
    private List<Ball> getBalls(final NodeList nlBall, final int cant, 
    		final int min, final int max) {
		double sizePercentage;
		int value;
		Ball ball;
		BufferedImage image;
		List<Ball> listBalls;
		Element elementBall;
		
		listBalls = new ArrayList<Ball>();
		image = null;
		ball = null;
		if (nlBall != null && nlBall.getLength() > 0) {
            for (int i = 0; i < cant; i++) {
            	elementBall = (Element) nlBall.item(i % nlBall.getLength());
				sizePercentage = Math.random() * 0.6 + 0.4;
				value = (int) Math.random() * (max - min) + min;
				try {
					image = ImageIO.read(new File(
							elementBall.getAttribute("image")));
				} catch (Exception e) {
					e.printStackTrace();
				}
		        ball = new Ball(value, "Ball " + i, image, sizePercentage);
                listBalls.add(ball);
			}
		}
        return listBalls;
    }
	
    /**Construye los diferentes niveles del juego especificado en el documento
     * de configuracion.
     * @param level Numero de nivel a retornar.
     * @return List Retorna una lista con los niveles ya configurados.
     */
	private Level getLevel(final int level) {
        Element elementLevel;
		NodeList nlLevel;
		NodeList nlBalls;
        int id;
		int points;  //FALTA PASARLE ESTE VALOR A ALGUN OBJETO QUE HAGA ALGO!!!
		int balls;
		int min;
		int max;
		String time;
		String background;  //PASARLE ESTE VALOR A ALGUN OBJETO QUE HAGA ALGO!!
		List<Ball> listBalls;
		Level levelReturn; 
		Clock clock;
		
		nlLevel = docEle.getElementsByTagName("level");
		nlBalls = docEle.getElementsByTagName("ball");
		levelReturn = null;
		if (nlLevel != null && nlLevel.getLength() > 0) {
            for (int i = 0; i < nlLevel.getLength(); i++) {
                elementLevel = (Element) nlLevel.item(i);
		        id = Integer.parseInt(elementLevel.getAttribute("id"));
		        if (id == level) {
			        time = elementLevel.getAttribute("time");
					points = Integer.parseInt(
							elementLevel.getAttribute("points"));
					balls = Integer.parseInt(
							elementLevel.getAttribute("balls"));
					min = Integer.parseInt(elementLevel.getAttribute("min"));
					max = Integer.parseInt(elementLevel.getAttribute("max"));
					background = elementLevel.getAttribute("background");
					clock = new Clock();
					clock.setTotalTime(time);
					listBalls = getBalls(nlBalls, balls, min, max);
			        levelReturn = new Level(null);
					levelReturn.setLevelNumber(id);
					levelReturn.setClock(clock);
					CompareBall comp = new CompareBall();
					for (int j = 0; j < listBalls.size(); j++) {
			        	levelReturn.addBall(listBalls.get(j), comp);
			        }		
		        }
            }
        }
		return levelReturn;
	}

	/**Realiza la llamada al metodo que parsea el documento xml de
	 *configuracion y asigna la lista de niveles ya configurados a la
	 *variable de clase listLevels.
	 * @param route Especifica la direccion del archivo de configuracion a ser
	 * utilizado.
	 */
	private void loadLevels(final String route) {
		dom = openDocument(route);
		docEle = dom.getDocumentElement();
	}

	/**Devuelve el nivel de juego en el que se encuentra actualmente.
	 * @return  Retorna el nivel de juego actual.
	 */
	public final int getCurrentLevel() {
		return currentLevel;
	}

	/**Setea el nivel de juego actual.
	 * @param currentLev  Valor del nivel de juego a setear.
	 */
	public final void setCurrentLevel(final int currentLev) {
		this.currentLevel = currentLev;
	}

}
