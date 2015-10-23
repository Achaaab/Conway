package utilitaire.swing;

import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

/**
 * 
 * @author guehenneux
 * 
 */
public class FenetrePleinEcran extends JFrame {

	/**
	 * UID genere le 19/05/2010
	 */
	private static final long serialVersionUID = 9021677185684513938L;

	/**
	 * 
	 */
	public FenetrePleinEcran(Component composant) {

		setUndecorated(true);

		GraphicsDevice ecranPrincipal = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice();

		add(composant);

		ecranPrincipal.setFullScreenWindow(this);

	}

}