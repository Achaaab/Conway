package conway.controle;

/**
 * Permet de localiser une cellule dans la population.
 * 
 * @author guehenneux
 * 
 */
public class CleCellule {

	private static final int PUISSANCE_HACHAGE = 16;
	private static final int MODULUS_HACHAGE = 1 << PUISSANCE_HACHAGE;
	private static final int MASQUE_HACHAGE = MODULUS_HACHAGE - 1;

	private int x;
	private int y;

	private int hashCode;

	/**
	 * @param x
	 * @param y
	 */
	public CleCellule(int x, int y) {

		this.x = x;
		this.y = y;

		hashCode = (x & MASQUE_HACHAGE) << PUISSANCE_HACHAGE | y
				& MASQUE_HACHAGE;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @return
	 */
	public CleCellule[] getClesVoisines() {

		CleCellule[] clesVoisines = new CleCellule[8];

		clesVoisines[0] = new CleCellule(x, y - 1);
		clesVoisines[1] = new CleCellule(x + 1, y - 1);
		clesVoisines[2] = new CleCellule(x + 1, y);
		clesVoisines[3] = new CleCellule(x + 1, y + 1);
		clesVoisines[4] = new CleCellule(x, y + 1);
		clesVoisines[5] = new CleCellule(x - 1, y + 1);
		clesVoisines[6] = new CleCellule(x - 1, y);
		clesVoisines[7] = new CleCellule(x - 1, y - 1);

		return clesVoisines;

	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(Object objet) {

		CleCellule cle = (CleCellule) objet;
		return x == cle.x && y == cle.y;

	}

}