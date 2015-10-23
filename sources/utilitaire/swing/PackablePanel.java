package utilitaire.swing;

import java.awt.Container;
import java.awt.Window;

import javax.swing.JPanel;

/**
 * 
 * @author GUEHENNEUX
 * 
 */
public class PackablePanel extends JPanel {

	/**
	 * UID genere le 29/04/2010
	 */
	private static final long serialVersionUID = 3267189768017322526L;

	/**
	 * 
	 */
	public void pack() {

		Container parent = getParent();

		while (parent != null && !(parent instanceof Window)) {
			parent = parent.getParent();
		}

		if (parent != null) {

			Window fenetre = (Window) parent;
			fenetre.pack();

		}

	}

}