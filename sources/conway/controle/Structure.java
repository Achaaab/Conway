package conway.controle;

/**
 * Structure de cellules.
 * 
 * @author Jonathan Gu�henneux
 */
public interface Structure {

	/**
	 * @param population
	 * @param x
	 * @param y
	 */
	public abstract void creer(Population population, int x, int y);
}