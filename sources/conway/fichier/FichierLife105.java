package conway.fichier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import conway.controle.Population;
import conway.controle.StructureListe;

/**
 * @author Jonathan Guéhenneux
 */
public class FichierLife105 extends FichierConway {

	private BufferedReader reader;

	/**
	 * @param fichier
	 * @throws FileNotFoundException
	 */
	public FichierLife105(File fichier) throws FileNotFoundException {
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(fichier)));
	}

	@Override
	public void ajouter(Population population) throws IOException {

		String ligne;
		int longueurLigne;

		Scanner scanner;

		StructureListe structure = null;
		int x = 0;
		int y = 0;

		int indexLigne = 0;
		int indexCaractere;
		char caractere;

		while ((ligne = reader.readLine()) != null) {

			longueurLigne = ligne.length();

			if (longueurLigne > 0) {

				if (ligne.charAt(0) == '#') {

					if (ligne.charAt(1) == 'P') {

						if (structure != null) {

							structure.creer(population, x, y);
							indexLigne = 0;
						}

						structure = new StructureListe();

						scanner = new Scanner(ligne.substring(2));

						x = scanner.nextInt();
						y = scanner.nextInt();
					}

				} else {

					for (indexCaractere = 0; indexCaractere < longueurLigne; indexCaractere++) {

						caractere = ligne.charAt(indexCaractere);

						if (caractere == '*') {
							structure.ajouterCellule(indexCaractere, indexLigne);
						}
					}

					indexLigne++;
				}
			}
		}

		if (structure != null) {
			structure.creer(population, x, y);
		}

		reader.close();
	}
}