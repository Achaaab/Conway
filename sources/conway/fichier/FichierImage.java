package conway.fichier;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utilitaire.CouleurUtilitaire;
import conway.controle.Population;
import conway.controle.StructureListe;

/**
 * @author Jonathan Guéhenneux
 */
public class FichierImage extends FichierConway {

	private BufferedImage image;

	private int largeur;
	private int hauteur;

	/**
	 * @param fichier
	 * @throws IOException
	 */
	public FichierImage(File fichier) throws IOException {

		image = ImageIO.read(fichier);

		largeur = image.getWidth();
		hauteur = image.getHeight();
	}

	@Override
	public void ajouter(Population population) throws IOException {

		int x;
		int y;

		int rgb;
		int niveauGris;

		StructureListe structure = new StructureListe();

		for (x = 0; x < largeur; x++) {

			for (y = 0; y < hauteur; y++) {

				rgb = image.getRGB(x, y);

				niveauGris = CouleurUtilitaire.getNiveauGris(rgb);

				if (niveauGris < 64) {
					structure.ajouterCellule(x, y);
				}
			}
		}

		structure.creer(population, -largeur / 2, -hauteur / 2);
	}
}