package utilitaire.swing;

import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * 
 * @author guehenneux
 * 
 */
public class EcouteurDeplacementPanneau implements MouseListener,
		MouseMotionListener, MouseWheelListener, KeyListener {

	private static final Cursor CURSEUR_MAIN_OUVERTE = new Cursor(
			Cursor.HAND_CURSOR);

	private static final Cursor CURSEUR_DEFAUT = Cursor.getDefaultCursor();

	private PanneauGlissant panneauGlissant;

	private int xOrigine;
	private int yOrigine;

	/**
	 * @param panneauGlissant
	 */
	public EcouteurDeplacementPanneau(PanneauGlissant panneauGlissant) {
		this.panneauGlissant = panneauGlissant;
	}

	@Override
	public void mouseDragged(MouseEvent evenement) {

		if (evenement.isShiftDown()) {

			int x = evenement.getX();
			int y = evenement.getY();

			int xImage = x - xOrigine;
			int yImage = y - yOrigine;

			panneauGlissant.deplacerImage(xImage, yImage);

		}

	}

	@Override
	public void mouseMoved(MouseEvent evenement) {

	}

	@Override
	public void mouseClicked(MouseEvent evenement) {

	}

	@Override
	public void mouseEntered(MouseEvent evenement) {
		panneauGlissant.requestFocus();
	}

	@Override
	public void mouseExited(MouseEvent evenement) {
		panneauGlissant.setCursor(CURSEUR_DEFAUT);
	}

	@Override
	public void mousePressed(MouseEvent evenement) {

		if (evenement.isShiftDown()) {

			if (panneauGlissant.getRps() > 0) {
				panneauGlissant.interrompreRafraichissementAutomatique();
			}

			xOrigine = evenement.getX();
			yOrigine = evenement.getY();

		}

	}

	@Override
	public void mouseReleased(MouseEvent evenement) {

		if (evenement.isShiftDown()) {

			panneauGlissant.validerDeplacement();

			if (panneauGlissant.getRps() > 0) {
				panneauGlissant.reprendreRafraichissementAutomatique();
			}

		}

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent evenement) {

		int rotation = evenement.getWheelRotation();
		int x = evenement.getX();
		int y = evenement.getY();

		int zoom = panneauGlissant.getZoom();
		zoom -= rotation;

		panneauGlissant.setZoom(zoom, x, y);

	}

	@Override
	public void keyPressed(KeyEvent evenement) {

		int touche = evenement.getKeyCode();

		if (touche == KeyEvent.VK_SHIFT) {
			panneauGlissant.setCursor(CURSEUR_MAIN_OUVERTE);
		}

	}

	@Override
	public void keyReleased(KeyEvent evenement) {

		int touche = evenement.getKeyCode();

		if (touche == KeyEvent.VK_SHIFT) {
			panneauGlissant.setCursor(CURSEUR_DEFAUT);
		}

	}

	@Override
	public void keyTyped(KeyEvent evenement) {

	}

}