package ar.edu.unicen.exa.spots.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * @author Joaquín Alejandro Pérez Fuentes
 */
public class GuiUtils {
	public static void centerOnScreen(final Component target) {
		if (target != null) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension dialogSize = target.getSize();
			if (dialogSize.height > screenSize.height) {
				dialogSize.height = screenSize.height;
			}
			if (dialogSize.width > screenSize.width) {
				dialogSize.width = screenSize.width;
			}
			target.setLocation((screenSize.width - dialogSize.width) / 2,
					(screenSize.height - dialogSize.height) / 2);
		}
	}
}
