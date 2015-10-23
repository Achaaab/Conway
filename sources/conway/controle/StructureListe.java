package conway.controle;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Structure de cellules stockées sous forme de liste.
 * 
 * @author Jonathan Guéhenneux
 */
public class StructureListe implements Structure {

	private List<Point> cellules;

	/**
	 * 
	 */
	public StructureListe() {
		cellules = new ArrayList<Point>();
	}

	/**
	 * @param cellules
	 */
	public StructureListe(List<Point> cellules) {
		this.cellules = cellules;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void ajouterCellule(int x, int y) {
		cellules.add(new Point(x, y));
	}

	@Override
	public void creer(Population population, int x, int y) {

		for (Point cellule : cellules) {
			population.creerCelluleVivante(x + cellule.x, y + cellule.y);
		}
	}
}