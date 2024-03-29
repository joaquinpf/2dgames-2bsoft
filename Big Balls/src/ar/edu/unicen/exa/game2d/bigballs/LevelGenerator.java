package ar.edu.unicen.exa.game2d.bigballs; 
/**Classname LevelGenerator.
 *
 * Version information 1.0
 * 
 * Date 07/11/2008
 * 
 * Copyright notice
 */

import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.golden.gamedev.GameEngine;
import com.golden.gamedev.util.ImageUtil;

/**Esta clase se ocupa de cargar la configuracion de cada nivel del juego.
 * Las configuraciones de los diferentes niveles se leen de un archivo XML
 * pasado como parametro al constructor de la clase.
 * 
 * @author Marcos Steimbach y Joaqu�n P�rez Fuentes
 */
public class LevelGenerator {
	/**Ruta del archivo de configuracion. 
	 */
	private String configRoute = "";

	/**Documento parseado que contiene las configuraciones de los niveles.
	 */
	private Document dom;
	
	/**Contiene los elementos del documento de configuracion.
	 */
	private Element docEle;
	
	/**Genera numeros o booleans aleatorios.
	 */
	private Random random = new Random();
	
	/**Constructor de la clase.
	 * @param Strring route Especifica la direccion del archivo de configuracion a ser
	 * utilizado.
	 */

	public LevelGenerator(final String route) {
		this.configRoute = route;
		loadLevels(route);
	}

	/**Retorna el proximo nivel e incrementa en uno currentLevel.
	 * En caso de haber sido el ultimo nivel del juego retorna null.
	 * @param GameEngine parent. The parent gameengine
	 * @param int level. El nro de nivel a construir
	 * @return  Level. Retorna el proximo nivel del juego.
	 */
	public final Level generateLevel(final GameEngine parent, final int level) {
		return getLevel(level, parent);
	}

	/**Retorna la ruta del archivo de configuracion.
	 * @return  String. Retorna la ruta de configuracion.
	 */
	public final String getConfigRoute() {
		return configRoute;
	}

	/**Setea la ruta de configuracion de los niveles del juego.
	 * @param String confRoute. La ruta de configuracion a setear.
	 */
	public final void setConfigRoute(final String confRoute) {
		this.configRoute = confRoute;
	}

	/**Parsea y retorna el documento de configuracion xml pasado como parametro.
	 * @param String route. Es la ruta de configuracion de los niveles del juego.
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
	 * @param NodeList nlBall. Contiene la informacion de configuracion de
	 * cada pelota.
	 * @param int cant. Cantidad de pelotas a generar y agregar a la lista.
	 * @param int min. Valor minimo posible para el valor de la pelota generada.
	 * @param int max. Valor maximo posible para el valor de la pelota generada.
	 * @return List<Ball> Retorna una lista con la cantidad de pelotas
	 * especificadas en el parametro cant.
	 */
    @SuppressWarnings("deprecation")
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
		
		boolean drawChar = random.nextBoolean();
		
		if (nlBall != null && nlBall.getLength() > 0) {
            for (int i = 0; i < cant; i++) {
            	            	
				int randomBall = (int) (Math.random() * nlBall.getLength());
            	elementBall = (Element) nlBall.item(randomBall % nlBall.getLength());
				
				try {
					File f = new File(elementBall.getAttribute("image"));
					image = ImageUtil.getImage(f.toURL(), Transparency.TRANSLUCENT);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				sizePercentage = Math.random() * 0.72 + 0.3;
				
				if (drawChar) {
					//Si el nivel es de texto, se debe randomizar entre
					//el 65 y 90, A-Z en ascii.
					value = (int) (random.nextDouble() * (90 - 65) + 65);
					value = Math.abs(value);
					String v = new String();
					v += (char) value;
					ball = new Ball(value, v, image, sizePercentage);			        
				} else {
					//Si el nivel es numerico, se utilizan los limites
					//normalmente.
					value = (int) (random.nextDouble() * (max - min) + min);
					ball = new Ball(value, Integer.toString(value), image, sizePercentage);
				}
				
		        //Setea la imagen utilizada en la pelota
				ball.setImageUsed(elementBall.getAttribute("image"));
		        
		        //Randomiza la velocidad horizontal y vertical del sprite
		        double speedx = (Math.random() - 0.5) * 0.1;
		        double speedy = (Math.random() - 0.5) * 0.1;
		        ball.setHorizontalSpeed(speedx);
		        ball.setVerticalSpeed(speedy);
		        
		        //Randomiza la velocidad de rotacion del sprite
		        int rotation = (int) (((Math.random() - 0.5) + 0.1) * 30);
		        
		        ball.setSpinVelocity(rotation);
		        
		        //Agrega la pelota
		        listBalls.add(ball);
			}
		}
        return listBalls;
    }
	
    /**Construye los diferentes niveles del juego especificado en el documento
     * de configuracion.
     * @param int level. Numero de nivel a retornar.
     * @param GameEngine parent. El GameEngine padre del Level a construir
     * @return Level. Retorna el nivel pedido ya configurado.
     */
	private Level getLevel(final int level, final GameEngine parent) {
        Element elementLevel;
		NodeList nlLevel;
		NodeList nlBalls;
        int id;
		int points; 
		int balls;
		int min;
		int max;
		String time;
		String background;
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
					clock.setRemainingTime(Integer.parseInt(time));
					listBalls = getBalls(nlBalls, balls, min, max);
			        levelReturn = new Level(parent);
					levelReturn.setLevelNumber(id);
					levelReturn.setClock(clock);
					levelReturn.setBackground(background);
					levelReturn.setPossiblePoints(points);
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
	 * @param String route. Especifica la direccion del archivo de configuracion a ser
	 * utilizado.
	 */
	private void loadLevels(final String route) {
		dom = openDocument(route);
		docEle = dom.getDocumentElement();
	}

}
