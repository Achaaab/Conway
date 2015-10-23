package conway.controle;

/**
 * Cellule qui peut �tre vivante ou morte.
 * 
 * @author Jonathan Gu�henneux
 */
public class Cellule {

	protected int x;
	protected int y;

	protected boolean vivante;
	protected int nombreVoisins;

	/**
	 * @param x
	 * @param y
	 */
	public Cellule(int x, int y) {

		this.x = x;
		this.y = y;

		vivante = false;
		nombreVoisins = 0;
	}
}