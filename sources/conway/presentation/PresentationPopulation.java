package conway.presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import utilitaire.swing.PanneauGlissant;
import conway.controle.Population;

/**
 * 
 * @author guehenneux
 * 
 */
public class PresentationPopulation extends PanneauGlissant {

	private static final Color COULEUR_MORT = Color.WHITE;
	private static final Color COULEUR_VIE = Color.BLACK;
	private static final int RGB_COULEUR_VIE = COULEUR_VIE.getRGB();
	private static final Color COULEUR_GRILLE = Color.LIGHT_GRAY;

	private Population population;

	private int xMin;
	private int yMin;

	private int margeGauche;
	private int margeHaut;

	private int largeurCellule;
	private int hauteurCellule;

	/**
	 * 
	 * @param population
	 */
	public PresentationPopulation(Population population) {

		super(0, 6);

		this.population = population;

		int largeurEchantillon = 400;
		int hauteurEchantillon = 400;

		int largeurIndividu = 1 << zoom;
		int hauteurIndividu = 1 << zoom;

		int largeurIdeale = largeurEchantillon * largeurIndividu;
		int hauteurIdeale = hauteurEchantillon * hauteurIndividu;

		Dimension dimensionIdeale = new Dimension(largeurIdeale, hauteurIdeale);
		setPreferredSize(dimensionIdeale);

		setBackground(COULEUR_MORT);

		EcouteurCreationCellule ecouteurCreationCellule = new EcouteurCreationCellule(this);

		addMouseListener(ecouteurCreationCellule);
		addMouseMotionListener(ecouteurCreationCellule);
	}

	@Override
	public void dessiner() {

		int largeurPopulation = largeur >> zoom;
		int hauteurPopulation = hauteur >> zoom;

		if (largeurPopulation > 0 && hauteurPopulation > 0 && largeurPopulation <= largeur
				&& hauteurPopulation <= hauteur) {

			largeurCellule = largeur / largeurPopulation;
			hauteurCellule = hauteur / hauteurPopulation;

			int largeurOccupee = largeurCellule * largeurPopulation;
			int hauteurOccupee = hauteurCellule * hauteurPopulation;

			margeGauche = (largeur - largeurOccupee) / 2;
			margeHaut = (hauteur - hauteurOccupee) / 2;

			xMin = Math.round(xCentrePanneau - largeurPopulation / 2);
			yMin = Math.round(yCentrePanneau - hauteurPopulation / 2);

			boolean[][] echantillon = population.getEchantillon(xMin, yMin, largeurPopulation, hauteurPopulation);

			if (largeurCellule == 1 && hauteurCellule == 1) {
				dessinerPopulationPoints(echantillon, largeurPopulation, hauteurPopulation);
			} else {
				dessinerPopulationRectangles(echantillon, largeurPopulation, hauteurPopulation);
			}
		}

		population.actualiserPresentation();
	}

	/**
	 * @param echantillon
	 * @param largeurEchantillon
	 * @param hauteurEchantillon
	 */
	private void dessinerPopulationPoints(boolean[][] echantillon, int largeurEchantillon, int hauteurEchantillon) {

		int x, y;
		boolean celluleVivante;

		for (x = 0; x < largeurEchantillon; x++) {

			for (y = 0; y < hauteurEchantillon; y++) {

				celluleVivante = echantillon[x][y];

				if (celluleVivante) {
					image.setRGB(margeGauche + x, margeHaut + y, RGB_COULEUR_VIE);
				}
			}
		}
	}

	/**
	 * 
	 * @param xCurseur
	 * @param yCurseur
	 * @return
	 */
	public Point getCoordonneesCellule(int xCurseur, int yCurseur) {

		int x = xMin + (xCurseur - margeGauche) / largeurCellule;
		int y = yMin + (yCurseur - margeHaut) / hauteurCellule;

		return new Point(x, y);
	}

	/**
	 * 
	 * @param coordonneesCellule
	 */
	public void inverserCellule(Point coordonneesCellule) {
		population.inverserCellule(coordonneesCellule.x, coordonneesCellule.y);
	}

	/**
	 * 
	 * @param echantillon
	 * @param largeurEchantillon
	 * @param hauteurEchantillon
	 */
	private void dessinerPopulationRectangles(boolean[][] echantillon, int largeurEchantillon, int hauteurEchantillon) {

		int x, y;
		boolean celluleVivante;

		graphique.setColor(COULEUR_VIE);

		for (x = 0; x < largeurEchantillon; x++) {

			for (y = 0; y < hauteurEchantillon; y++) {

				celluleVivante = echantillon[x][y];

				if (celluleVivante) {

					graphique.fillRect(margeGauche + x * largeurCellule, margeHaut + y * hauteurCellule,
							largeurCellule, hauteurCellule);
				}
			}
		}

		if (zoom > 1) {

			graphique.setColor(COULEUR_GRILLE);

			for (x = 0; x < largeurEchantillon + 1; x++) {

				graphique.drawLine(margeGauche + x * largeurCellule, margeHaut, margeGauche + x * largeurCellule,
						margeHaut + hauteurEchantillon * hauteurCellule);
			}

			for (y = 0; y < hauteurEchantillon + 1; y++) {

				graphique.drawLine(margeGauche, margeHaut + y * hauteurCellule, margeGauche + largeurEchantillon
						* largeurCellule, margeHaut + y * hauteurCellule);
			}
		}
	}
}