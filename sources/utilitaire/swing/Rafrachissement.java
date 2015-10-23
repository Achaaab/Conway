package utilitaire.swing;

import java.awt.Component;
import java.util.TimerTask;

/**
 * 
 * @author GUEHENNEUX
 * 
 */
public class Rafrachissement extends TimerTask {

	private Component composant;

	/**
	 * @param composant
	 */
	public Rafrachissement(Component composant) {
		this.composant = composant;
	}

	@Override
	public void run() {
		composant.repaint();
	}

}