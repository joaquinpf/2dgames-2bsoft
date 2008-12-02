import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;


public class MiniLetter extends Sprite {

	private BufferedImage letterVisible;
	
	private BufferedImage letterNoVisible;
	
	private char value;
	
	private boolean visible;
	
	public MiniLetter(final BufferedImage imgNoVisible,
			final BufferedImage imgVisible, final char valor) {
		super();
		this.value = valor;
		this.letterVisible = AdvanceImageUtil.drawString(imgVisible, null, 21,value);
		this.letterNoVisible = imgNoVisible;
		this.visible = false;
		this.setImage(this.letterNoVisible);
		
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
		if (this.visible) {
			this.setImage(letterVisible);
		} else {
			this.setImage(letterNoVisible);
		}
	}
	public char getValue() {
		return this.value;
	}
	
}
