package utilitaire;

import java.util.List;
import java.util.Random;

/**
 * @author GUEHENNEUX
 */
public class ListeUtilitaire {

	/**
	 * le generateur aleatoire
	 */
	private static final Random RANDOM = new Random();

	/**
	 * @param liste
	 * @param tailleMaximale
	 */
	public static List<?> tronquer(List<?> liste, int tailleMaximale) {

		List<?> listeTronquee;

		int tailleListe = liste.size();

		if (tailleListe > tailleMaximale) {
			listeTronquee = liste.subList(0, tailleMaximale);
		} else {
			listeTronquee = liste;
		}

		return listeTronquee;

	}

	/**
	 * @param liste
	 *            une liste non vide
	 * @return un element choisi arbitrairement dans la liste
	 */
	public static Object choisirElementArbitrairement(List<?> liste) {
		return liste.get(0);
	}

	/**
	 * 
	 * @param liste
	 * @return
	 */
	public static Object choisirElementAleatoirement(List<?> liste) {

		int tailleListe = liste.size();
		int indexElement = RANDOM.nextInt(tailleListe);
		return liste.get(indexElement);

	}

}