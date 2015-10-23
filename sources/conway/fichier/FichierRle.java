package conway.fichier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import utilitaire.GestionnaireException;
import conway.controle.Population;
import conway.controle.StructureListe;

/**
 * @author Jonathan Guéhenneux
 */
public class FichierRle extends FichierConway {

	private static final String PREFIXE_COORDONEES = "#R";

	private StructureListe structure;

	private int x;
	private int y;

	private BufferedReader reader;

	/**
	 * @param fichier
	 * @throws FileNotFoundException
	 */
	public FichierRle(File fichier) throws FileNotFoundException {

		reader = new BufferedReader(new InputStreamReader(new FileInputStream(fichier)));

		x = 0;
		y = 0;

		try {

			lireFichier();
			reader.close();

		} catch (IOException erreur) {
			GestionnaireException.traiter(erreur);
		}
	}

	/**
	 * @throws IOException
	 */
	private void lireFichier() throws IOException {

		structure = new StructureListe();

		String ligne;
		int longueurLigne;

		Scanner scanner;

		boolean coordonneesSpecifiques = false;

		int indexCaractere;
		char caractere;
		int indexLigne = 0;
		int indexColonne = 0;
		int occurrence;
		int nombreOccurrences = 0;

		boolean donneesLues = false;

		while (!donneesLues && (ligne = reader.readLine()) != null) {

			if (ligne.startsWith(PREFIXE_COORDONEES)) {

				String coordonnees = ligne.substring(PREFIXE_COORDONEES.length());
				scanner = new Scanner(coordonnees);

				x = scanner.nextInt();
				y = scanner.nextInt();

				coordonneesSpecifiques = true;

			} else if (ligne.startsWith("#")) {

			} else if (!coordonneesSpecifiques && ligne.startsWith("x")) {

				String[] parametres = ligne.split(",");

				String parametreX = parametres[0];
				String valeurX = parametreX.split("=")[1];
				scanner = new Scanner(valeurX);
				x = -scanner.nextInt() / 2;

				String parametreY = parametres[1];
				String valeurY = parametreY.split("=")[1];
				scanner = new Scanner(valeurY);
				y = -scanner.nextInt() / 2;

			} else {

				longueurLigne = ligne.length();

				for (indexCaractere = 0; indexCaractere < longueurLigne; indexCaractere++) {

					caractere = ligne.charAt(indexCaractere);

					if (caractere >= '0' && caractere <= '9') {

						nombreOccurrences = nombreOccurrences * 10 + caractere - '0';

					} else if (caractere == '$') {

						if (nombreOccurrences == 0) {
							nombreOccurrences = 1;
						}

						indexLigne += nombreOccurrences;
						nombreOccurrences = 0;
						indexColonne = 0;

					} else if (caractere == '!') {

						donneesLues = true;
						break;

					} else {

						if (nombreOccurrences == 0) {
							nombreOccurrences = 1;
						}

						if (caractere == 'o') {

							for (occurrence = 0; occurrence < nombreOccurrences; occurrence++) {
								structure.ajouterCellule(indexColonne + occurrence, indexLigne);
							}
						}

						indexColonne += nombreOccurrences;
						nombreOccurrences = 0;
					}
				}
			}
		}
	}

	@Override
	public void ajouter(Population population) throws IOException {
		structure.creer(population, x, y);
	}

}