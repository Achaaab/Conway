package conway.presentation;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author Jonathan Guéhenneux
 */
public class EcouteurCreationCellule implements MouseListener, MouseMotionListener {

	private PresentationPopulation population;

	private int xCurseur, yCurseur;
	private Point coordonneesCellule;
	private Point coordonneesCellulePrecedente;

	/**
	 * @param population
	 */
	public EcouteurCreationCellule(PresentationPopulation population) {
		this.population = population;
	}

	@Override
	public void mouseClicked(MouseEvent evenement) {

	}

	@Override
	public void mouseEntered(MouseEvent evenement) {

	}

	@Override
	public void mouseExited(MouseEvent evenement) {

	}

	@Override
	public void mousePressed(MouseEvent evenement) {

		if (!evenement.isShiftDown()) {

			xCurseur = evenement.getX();
			yCurseur = evenement.getY();

			coordonneesCellule = population.getCoordonneesCellule(xCurseur, yCurseur);
			population.inverserCellule(coordonneesCellule);
			coordonneesCellulePrecedente = coordonneesCellule;
		}
	}

	@Override
	public void mouseReleased(MouseEvent evenement) {

	}

	@Override
	public void mouseDragged(MouseEvent evenement) {

		if (!evenement.isShiftDown()) {

			xCurseur = evenement.getX();
			yCurseur = evenement.getY();

			coordonneesCellule = population.getCoordonneesCellule(xCurseur, yCurseur);

			if (!coordonneesCellule.equals(coordonneesCellulePrecedente)) {

				population.inverserCellule(coordonneesCellule);
				coordonneesCellulePrecedente = coordonneesCellule;
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent evenement) {

	}
}