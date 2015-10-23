package conway.fichier;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import utilitaire.fichier.FichierUtilitaire;
import conway.controle.Population;
import conway.exceptions.FichierConwayIllisible;

/**
 * @author Jonathan Guéhenneux
 */
public abstract class FichierConway {

	public static final String JPG = "jpg";
	public static final String JPEG = "jpeg";
	public static final String GIF = "gif";
	public static final String TIFF = "tiff";
	public static final String TIF = "tif";
	public static final String BMP = "bmp";
	public static final String PNG = "png";
	public static final String RLE = "rle";
	public static final String LIF = "lif";
	public static final String LIFE = "life";

	public static final Set<String> EXTENSIONS_IMAGE;

	static {

		EXTENSIONS_IMAGE = new HashSet<String>();

		EXTENSIONS_IMAGE.add(JPG);
		EXTENSIONS_IMAGE.add(JPEG);
		EXTENSIONS_IMAGE.add(GIF);
		EXTENSIONS_IMAGE.add(TIFF);
		EXTENSIONS_IMAGE.add(TIF);
		EXTENSIONS_IMAGE.add(BMP);
		EXTENSIONS_IMAGE.add(PNG);
	}

	/**
	 * 
	 * @param population
	 * @throws IOException
	 * @throws FichierConwayIllisible
	 */
	public static final void ajouter(File fichier, Population population) throws IOException, FichierConwayIllisible {

		String extension = FichierUtilitaire.getExtension(fichier);

		FichierConway fichierConway;

		if (EXTENSIONS_IMAGE.contains(extension)) {
			fichierConway = new FichierImage(fichier);
		} else if (RLE.equals(extension)) {
			fichierConway = new FichierRle(fichier);
		} else if (LIF.equals(extension) || LIFE.equals(extension)) {
			fichierConway = new FichierLife105(fichier);
		} else {
			throw new FichierConwayIllisible(extension);
		}

		fichierConway.ajouter(population);
	}

	/**
	 * Ajoute a la population la structure de cellules definie dans le fichier.
	 * 
	 * @param population
	 * @throws IOException
	 */
	public abstract void ajouter(Population population) throws IOException;
}