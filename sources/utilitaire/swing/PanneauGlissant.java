package utilitaire.swing;

/**
 * Panneau ou l'on peut zoomer et dezoomer grace a la molette de la souris. On
 * peut aussi le deplacerer en faisant un glisser-deposer avec la touche
 * majuscule enfoncee.
 * 
 * @author Jonathan Guéhenneux
 */
public abstract class PanneauGlissant extends PanneauTampon {

	protected float xCentrePanneau;
	protected float yCentrePanneau;
	protected int zoom;

	protected int zoomMinimum;
	protected int zoomMaximum;

	/**
	 * 
	 * @param zoomMinimum
	 * @param zoomMaximum
	 */
	public PanneauGlissant(int zoomMinimum, int zoomMaximum) {

		this.zoomMinimum = zoomMinimum;
		this.zoomMaximum = zoomMaximum;

		xCentrePanneau = 0;
		yCentrePanneau = 0;
		zoom = 0;

		setFocusable(true);

		EcouteurDeplacementPanneau ecouteurDeplacement = new EcouteurDeplacementPanneau(
				this);

		addMouseListener(ecouteurDeplacement);
		addMouseMotionListener(ecouteurDeplacement);
		addMouseWheelListener(ecouteurDeplacement);
		addKeyListener(ecouteurDeplacement);
	}

	/**
	 * 
	 */
	public void validerDeplacement() {

		deplacerFenetre(-xImage, -yImage);

		xImage = 0;
		yImage = 0;
	}

	/**
	 * 
	 * @param dx
	 *            deplacement horizontal en pixels (vers la droite)
	 * @param dy
	 *            deplacement vertical en pixels (vers le bas)
	 */
	public void deplacerFenetre(int dx, int dy) {

		float dxReel = (float) (dx / Math.pow(2, zoom));
		float dyReel = (float) (dy / Math.pow(2, zoom));

		xCentrePanneau += dxReel;
		yCentrePanneau += dyReel;
	}

	/**
	 * 
	 * @param xImage
	 * @param yImage
	 */
	public void deplacerImage(int xImage, int yImage) {

		this.xImage = xImage;
		this.yImage = yImage;

		repaintImage();
	}

	/**
	 * @return the zoom
	 */
	public int getZoom() {
		return zoom;
	}

	/**
	 * 
	 * @param zoom
	 * @param x
	 * @param y
	 */
	public void setZoom(int zoom, int x, int y) {

		if (zoom < zoomMinimum) {
			zoom = zoomMinimum;
		}

		if (zoom > zoomMaximum) {
			zoom = zoomMaximum;
		}

		/*
		 * on recentre sur la selection
		 */

		deplacerFenetre(x - largeur / 2, y - hauteur / 2);

		/*
		 * on applique le nouveau zoom
		 */

		this.zoom = zoom;

		/*
		 * on recentre sur le curseur
		 */

		deplacerFenetre(largeur / 2 - x, hauteur / 2 - y);
	}
}