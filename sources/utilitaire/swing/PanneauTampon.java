package utilitaire.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Timer;

import javax.swing.JPanel;

import utilitaire.Chronometre;

/**
 * @author Jonathan Guéhenneux
 */
public abstract class PanneauTampon extends JPanel {

	private static final Timer TIMER_RAFRAICHISSEMENT = new Timer();

	protected int xImage;
	protected int yImage;
	private boolean redessiner;

	protected BufferedImage image;
	protected Graphics2D graphique;

	protected int largeur;
	protected int hauteur;

	private Rafrachissement rafraichissement;
	private int rps;

	/**
	 * 
	 */
	public PanneauTampon() {

		xImage = 0;
		yImage = 0;
		redessiner = true;
	}

	@Override
	public void paint(Graphics g) {

		int largeurPanneau = getWidth();
		int hauteurPanneau = getHeight();

		if (image == null || largeurPanneau != largeur || hauteurPanneau != hauteur) {
			creerImage(largeurPanneau, hauteurPanneau);
		}

		if (redessiner) {

			graphique.setColor(getBackground());
			graphique.fillRect(0, 0, largeurPanneau, hauteurPanneau);

			dessiner();

		} else {

			g.setColor(getBackground());
			g.fillRect(0, 0, largeurPanneau, hauteurPanneau);

			redessiner = true;
		}

		g.drawImage(image, xImage, yImage, null);
	}

	/**
	 * redessine l'image sans la recalculer
	 */
	protected void repaintImage() {

		redessiner = false;
		repaint();
	}

	/**
	 * 
	 * @param largeur
	 * @param hauteur
	 */
	private void creerImage(int largeur, int hauteur) {

		this.largeur = largeur;
		this.hauteur = hauteur;

		image = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);
		graphique = image.createGraphics();
		graphique.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	/**
	 * 
	 */
	public abstract void dessiner();

	/**
	 * 
	 */
	public void reprendreRafraichissementAutomatique() {
		rafraichirAutomatiquement(rps);
	}

	/**
	 * 
	 * @param rps
	 *            nombre de rafraichissements par seconde
	 */
	public void rafraichirAutomatiquement(int rps) {

		this.rps = rps;

		interrompreRafraichissementAutomatique();
		rafraichissement = new Rafrachissement(this);
		TIMER_RAFRAICHISSEMENT.schedule(rafraichissement, 0, Chronometre.MILLISECONDES_PAR_SECONDE / rps);
	}

	/**
	 * 
	 */
	public void interrompreRafraichissementAutomatique() {

		if (rafraichissement != null) {
			rafraichissement.cancel();
		}
	}

	/**
	 * @return the rps
	 */
	public int getRps() {
		return rps;
	}
}