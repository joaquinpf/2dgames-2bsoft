

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.util.ImageUtil;

/**
 * Esta clase modela el funcionamiento de una pelota.
 * Se encarga de la rotacion, traslacion y dibujado
 * 
 * @author Pablo Vizcay
 */

public class Ball extends Sprite {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3447645079012315490L;
	/**
	 * Valor que contiene la pelota.
	 * @uml.property  name="value"
	 */
	private int value;

	/**
	 * Fuente a utilizar sobre la pelota.
	 * @uml.property  name="ttfFont"
	 */
	private String ttfFont = "Arial";
	
	/**
	 * Tamaño de la fuente de la pelota.
	 * @uml.property  name="fontSize"
	 */
	private int fontSize = 36;
	
	/**
	 * Texto que describe a la pelota. Es el valor a dibujar.
	 * @uml.property  name="description"
	 */
	private String description = "";

	/**
	 * La direccion a la imagen utilizada en esta pelota.
	 * @uml.property  name="imageUsed"
	 */
	private String imageUsed = "";

	/**
	 * Velocidad de rotación.
	 * @uml.property  name="spinVelocity"
	 */
	private int spinVelocity;

	
	/**
	 */
	private BufferedImage ballImageCopy;
	
	/**
	 */
	private double acumulatedRotation;
	
	/**
	 * Actualiza la pelota segun el motor GTGE.
	 * @param elapsedTime Tiempo transcurrido desde el ultimo update
	 * @see com.golden.gamedev.GameObject#update(long)
	 */
	@Override
	public final void update(final long elapsedTime) {
		acumulatedRotation = acumulatedRotation + spinVelocity * elapsedTime / 100;
		super.update(elapsedTime);
	}
	
	/**
	 * Dibuja la escena. Override de GTGE.
	 * @param g El objeto grafico sobre el cual se dibuja
	 * @param x 4
	 * @param y 4  
	 * @see com.golden.gamedev.GameObject#render(java.awt.Graphics2D)
	 */
	@Override
	public final void render(final Graphics2D g, final int x, final int y) {		
		// aplicar la rotacion acumulada y setear la Image del Sprite //
		this.setImage(rotate(acumulatedRotation, ballImageCopy));		
		
		super.render(g, x, y);
	}
	
	/**
	 * Constructor de la pelota. 
	 * @param newValue El valor numerico que representa al texto
	 * @param text El texto que se escribe sobre la pelota
	 * @param ballImage La imagen a usar para la pelota
	 * @param sizePercentage El factor de correccion de tamaño
	 */
	public Ball(final int newValue, final String text, 
			final BufferedImage ballImage, final double sizePercentage) { 
		super();
		
		/* crear copia de la imagen ya escalada */
		ballImageCopy = resize(ballImage, sizePercentage);
		this.setImage(ballImage);
		
		// le seteo el texto luego (para que no quede distorsionado) //
		this.drawString(text, ballImageCopy);		
		
		this.value = newValue;
		this.description = text;
	}
	
	/**
	 * Rota una imagen de acuerdo al angulo dado.
	 * @param angle El angulo a rotar
	 * @param image La imagen a rotar
	 * @return La imagen transformada
	 */
	private BufferedImage rotate(final double angle, 
			final BufferedImage image) {
		 
		return ImageUtil.rotate(image, (int) angle);
		
		//AffineTransform tx = new AffineTransform();
		//tx.rotate(Math.PI / 180 * angle, image.getWidth() / 2, image.getHeight() / 2);
		//AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	
		//return op.filter(image, null);
	}
	
	/**
	 * Cambia el tamaño de la imagen segun el factor dado.
	 * @param image La imagen a transformar
	 * @param sizePercentage El factor de correccion de tamaño como
	 * porcentaje
	 * @return La imagen transformada
	 */
	private BufferedImage resize(final BufferedImage image, 
			final double sizePercentage) {
		
		return ImageUtil.resize(image, (int) (image.getWidth() * sizePercentage), 
				(int) (image.getHeight() * sizePercentage));
		/*AffineTransform tx = new AffineTransform();
		tx.scale(sizePercentage, sizePercentage);
		AffineTransformOp op = 
			new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(image, null);*/
	}
			
	/**
	 * Dibuja el string pasado como parametro sobre la imagen dada.
	 * Toma en cuenta ancho de linea para centrar el texto.
	 * @param text El texto a dibujar en la imagen
	 * @param image La imagen sobre la que se dibuja
	 */
	private void drawString(final String text, final BufferedImage image) {
		Graphics2D g2d = image.createGraphics();

		//Setea el color y la fuente
		Font font = new Font(ttfFont, Font.PLAIN, fontSize);
		g2d.setColor(Color.black);
		g2d.setFont(font);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		
	    AttributedString as = new AttributedString(text);
	    as.addAttribute(TextAttribute.FONT, font);
	    
	    //Subrayar si es un 9, 6, W o M
	    if(doUnderline(text)) {
	    	as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	    }
	    
		//Obtiene el ancho del texto a imprimir para poder corregir
		//la posicion en pantalla
		FontMetrics fm = g2d.getFontMetrics();
		int textWidth = fm.stringWidth(text);
		
		g2d.drawString(as.getIterator(), (image.getWidth() / 2) - (textWidth / 2), 
				(image.getHeight() / 2) + 12);
		g2d.dispose();
	}
	
	private boolean doUnderline (String text) {
		if(text.equals("9") || text.equals("6") || text.equals("66") 
	    		|| text.equals("99") || text.equals("W") || text.equals("M")){
			return true;
		}
		return false;
	}
	
	/**
	 * Getter of the property <tt>value</tt>.
	 * @return  Returns the value.
	 * @uml.property  name="value"
	 */
	public final int getValue() {
		return value;
	}

	/**
	 * Setter of the property <tt>value</tt>.
	 * @param newValue  The value to set.
	 * @uml.property  name="value"
	 */
	public final void setValue(final int newValue) {
		this.value = newValue;
	}
	
	/**
	 * Getter of the property <tt>description</tt>.
	 * @return  Returns the description.
	 * @uml.property  name="description"
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * Setter of the property <tt>description</tt>.
	 * @param newDescription  The description to set.
	 * @uml.property  name="description"
	 */
	public final void setDescription(final String newDescription) {
		this.description = newDescription;
	}

	/**
	 * Getter of the property <tt>imageUsed</tt>.
	 * @return  Returns the imageUsed.
	 * @uml.property  name="imageUsed"
	 */
	public final String getImageUsed() {
		return imageUsed;
	}

	/**
	 * Setter of the property <tt>imageUsed</tt>.
	 * @param newImageUsed  The imageUsed to set.
	 * @uml.property  name="imageUsed"
	 */
	public final void setImageUsed(final String newImageUsed) {
		this.imageUsed = newImageUsed;
	}
	
	/**
	 * Getter of the property <tt>spinVelocity</tt>.
	 * @return  Returns the spinVelocity.
	 * @uml.property  name="spinVelocity"
	 */
	public final int getSpinVelocity() {
		return spinVelocity;
	}

	/**
	 * Setter of the property <tt>spinVelocity</tt>.
	 * @param newSpinVelocity  The spinVelocity to set.
	 * @uml.property  name="spinVelocity"
	 */
	public final void setSpinVelocity(final int newSpinVelocity) {
		this.spinVelocity = newSpinVelocity;
	}

	/**
	 * Getter of the property <tt>spinVelocity</tt>.
	 * @return  Returns the ttfFont
	 * @uml.property  name="ttfFont"
	 */	
	public final String getTtfFont() {
		return ttfFont;	
	}
	
	/**
	 * Setter of <tt>ttfFont</tt>
	 * @param newttfFont the new font.
	 * @uml.property  name="ttfFont"
	 */	 
	public final void setTtfFont(String newttfFont) {
		ttfFont = newttfFont;
	}
	
	/**
	 * Getter of the property <tt>fontSize</tt>.
	 * @return  Returns the font size
	 * @uml.property  name="fontSize"
	 */	
	public final int getFontSize() {
		return fontSize;	
	}
	
	/**
	 * Setter of <tt>fontSize</tt>
	 * @param newFontSize the new font size.
	 * @uml.property  name="fontSize"
	 */	
	public final void setFontSize(int newFontSize) {
		fontSize = newFontSize;
	}	
	
	/**
	 * Getter of the property <tt>ballImageCopy</tt>.
	 * @return  Returns the ballImageCopy
	 * @uml.property  name="ballImageCopy"
	 */	
	public final BufferedImage getBallImageCopy() {
		return ballImageCopy;	
	}
	
	/**
	 * Setter of <tt>ballImageCopy</tt>
	 * @param newBallImageCopy the new ballImageCopy
	 * @uml.property  name="ballImageCopy"
	 */	
	public final void setBallImageCopy(BufferedImage newBallImageCopy) {
		ballImageCopy = newBallImageCopy;
	}	
	
	/**
	 * Getter of the property <tt>AcumulatedRotation</tt>.
	 * @return  Returns the AcumulatedRotation
	 * @uml.property  name="AcumulatedRotation"
	 */	
	public final double getAcumulatedRotation() {
		return acumulatedRotation;	
	}
	
	/**
	 * Setter of <tt>AcumulatedRotation</tt>
	 * @param newAcumulatedRotation the new AcumulatedRotation
	 * @uml.property  name="AcumulatedRotation"
	 */	
	public final void setBallImageCopy(double newAcumulatedRotation) {
		acumulatedRotation = newAcumulatedRotation;
	}		
}
