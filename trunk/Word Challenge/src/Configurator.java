/**Classname Configurator.
 *
 * Version information 1.0
 * 
 * Date 19/11/2008
 * 
 * Copyright notice
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**Esta clase se ocupa de cargar la configuracion del juego a partir de la
 * especificacion en un archivo XML pasado como parametro al constructor de
 * la misma.
 * Las palabras de los diferentes idiomas se leen de un archivo .DIC
 * especificado en el XML de configuracion.
 */

public class Configurator {

	/**Documento parseado que contiene las configuraciones de los niveles.
	 */
	private Document dom;
	
	/**Contiene los elementos del documento de configuracion.
	 */
	private Element docEle;

	/**Constructor de la clase.
	 * @param path Direccion del archivo de configuracion.
	 */
	public Configurator(final String path) {
		dom = openDocument(path);
		docEle = dom.getDocumentElement();
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
	
	/**Devuelve el tiempo indicado en el archivo de configuracion.
	 * @return int Tiempo especificado en el archivo de configuracion.
	 */
	public final int getTime() {
		NodeList nlTime;
		Element elementTime;
		int time;
		
		nlTime = docEle.getElementsByTagName("starttime");
		time = 0;
		if (nlTime != null && nlTime.getLength() > 0) {
          	elementTime = (Element) nlTime.item(0);
            time = (Integer.valueOf(elementTime.getTextContent())).intValue();
		}
        return time;
	}


	/**Retorna una lista con todos los lenguajes soportados.
	 * @return List Lista de Strings con los lenguajes soportados.
	 */
	public final List<String> getLenguages() {
		NodeList nlLanguages;
		Element elementLanguage;
		List<String> listLanguages;
		
		nlLanguages = docEle.getElementsByTagName("language");
		listLanguages = new ArrayList<String>();
		if (nlLanguages != null && nlLanguages.getLength() > 0) {
            for (int i = 0; i < nlLanguages.getLength(); i++) {
            	elementLanguage = (Element) nlLanguages.item(i);
                listLanguages.add(new String(elementLanguage.getAttribute(
                		"name")));
			}
		}
        return listLanguages;
	}

	/**
	 * @param language Especifica el lenguaje del cual se debera retornar el
	 * path correspondiente.
	 * @return String Devuelre el path del archivo del diccionario que contiene
	 * las palabras a ser cargadas.
	 */
	private String getPath(final String language) {
		NodeList nlPath;
		Element elementPath;
		String path;
		int i;
		
		nlPath = docEle.getElementsByTagName("language");
		path = null;
		i = 0;
		if (nlPath != null && nlPath.getLength() > 0) {
			elementPath = (Element) nlPath.item(i);
            while ((!elementPath.getAttribute("name").equals(language)) && (
            		elementPath != null)) {
            	i++;
            	if (i < nlPath.getLength()) {
            		elementPath = (Element) nlPath.item(i);
            	} else {
            		elementPath = null;
            	}
            }
            if (elementPath != null) {
            	path = elementPath.getAttribute("dictionary");
            }
		}
        return path;
	}
	
	/**
	 * @param lenguage Especifica el lenguaje sobre el cual se arma y retorna 
	 * el diccionario de palabras.
	 * @return Dictionary Diccionario armado con las palabras especificadas en
	 * el archivo del lenguaje indicado por parametro.
	 */
	public final Dictionary getDicctionary(final String lenguage) {
		HashSet<String> wordList;
		Dictionary dic;
		String path;
		BufferedReader reader;
		File file;
		String text;
		
		wordList = new HashSet<String>();
		path = getPath(lenguage);
		if (path != null) {
			file = new File(path);
	        reader = null;
	        try {
	            reader = new BufferedReader(new FileReader(file));
	            text = null;
	            // repeat until all lines is read
	            while ((text = reader.readLine()) != null) {
	                wordList.add(text);
	            }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (reader != null) {
	                    reader.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
		dic = new Dictionary(wordList);
		return dic;
	}
}
