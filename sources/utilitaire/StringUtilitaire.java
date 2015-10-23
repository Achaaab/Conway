package utilitaire;

/**
 * 
 * @author guehenneux
 * 
 */
public class StringUtilitaire {

	private static final String SUSPENSION = "...";
	private static final int LONGUEUR_SUSPENSION = SUSPENSION.length();

	private static final int MIN = 192;

	private static final int MAX = 255;

	private static final String[] REMPLACEMENT_SANS_ACCENT = initialiserTableSansAccent();

	private static String[] initialiserTableSansAccent() {

		String[] tableSansAccent = new String[MAX - MIN + 1];

		int indexCaractere = 0;

		java.lang.String remplacement = null;

		remplacement = "A";
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "AE";
		tableSansAccent[indexCaractere++] = remplacement;
		remplacement = "C";

		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "E";
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "I";
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "D";
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "N";
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "O";
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "x";
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "O";
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "U";
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "Y";
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "b";
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "B";
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "a";
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "ae";
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "c";
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "e";
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "i";
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "d";
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "n";
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "o";
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = " ";
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "o";
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "u";
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "y";
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "b";
		tableSansAccent[indexCaractere++] = remplacement;

		remplacement = "y";
		tableSansAccent[indexCaractere++] = remplacement;

		return tableSansAccent;

	}

	/**
	 * Transforme une chaine pouvant contenir des accents dans une version sans
	 * accent
	 * 
	 * @param chaine
	 *            Chaine a convertir sans accent
	 * @return Chaine dont les accents ont été supprimé
	 */
	public static String normaliser(String chaine) {

		chaine = chaine.toLowerCase();

		int longueurChaine = chaine.length();
		StringBuilder builderChaineNormalisee = new StringBuilder();

		char caractere;
		String remplacement;

		for (int indexCaractere = 0; indexCaractere < longueurChaine; indexCaractere++) {

			caractere = chaine.charAt(indexCaractere);

			if (caractere >= MIN && caractere <= MAX) {
				remplacement = REMPLACEMENT_SANS_ACCENT[caractere - MIN];
			} else if (caractere < 'A' || caractere > 'Z') {
				remplacement = " ";
			} else {
				remplacement = Character.toString(caractere);
			}

			builderChaineNormalisee.append(remplacement);

		}

		return builderChaineNormalisee.toString();

	}

	/**
	 * 
	 * @param objet
	 * @param longueurMaximale
	 * @param suspension
	 * @param remplissage
	 * @param caractereRemplissage
	 * @return
	 */
	public static final String formater(Object objet, int longueurMaximale,
			boolean suspension, boolean remplissage, char caractereRemplissage) {

		String chaine = objet.toString();

		int longueurChaine = chaine.length();

		if (longueurChaine > longueurMaximale) {

			if (suspension) {

				if (LONGUEUR_SUSPENSION > longueurMaximale) {

					chaine = SUSPENSION.substring(longueurMaximale);

				} else {

					chaine = chaine.substring(0, longueurMaximale
							- LONGUEUR_SUSPENSION);
					chaine += SUSPENSION;

				}

			} else {

				chaine = chaine.substring(0, longueurMaximale);

			}

		} else if (longueurChaine < longueurMaximale) {

			StringBuilder fabriqueChaine = new StringBuilder(chaine);

			for (int i = longueurChaine; i < longueurMaximale; i++) {
				fabriqueChaine.append(caractereRemplissage);
			}

			chaine = fabriqueChaine.toString();

		}

		return chaine;

	}

}