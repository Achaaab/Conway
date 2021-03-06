package conway.presentation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.filechooser.FileView;

import utilitaire.fichier.FichierUtilitaire;
import conway.fichier.FichierConway;

/**
 * @author Jonathan Guéhenneux
 */
public class VueFichierConway extends FileView {

	public static final VueFichierConway INSTANCE = new VueFichierConway();

	public static final Map<String, String> DESCRIPTIONS_EXTENSIONS;

	static {

		DESCRIPTIONS_EXTENSIONS = new HashMap<String, String>();

		String description;

		description = "Image JPEG";
		DESCRIPTIONS_EXTENSIONS.put(FichierConway.JPG, description);
		DESCRIPTIONS_EXTENSIONS.put(FichierConway.JPEG, description);

		description = "Image GIF";
		DESCRIPTIONS_EXTENSIONS.put(FichierConway.GIF, description);

		description = "Image TIFF";
		DESCRIPTIONS_EXTENSIONS.put(FichierConway.TIFF, description);
		DESCRIPTIONS_EXTENSIONS.put(FichierConway.TIF, description);

		description = "Image BITMAP";
		DESCRIPTIONS_EXTENSIONS.put(FichierConway.BMP, description);

		description = "Image PNG";
		DESCRIPTIONS_EXTENSIONS.put(FichierConway.PNG, description);

		description = "Fichier RLE";
		DESCRIPTIONS_EXTENSIONS.put(FichierConway.RLE, description);

		description = "Fichier LIFE 1.05";
		DESCRIPTIONS_EXTENSIONS.put(FichierConway.LIF, description);
		DESCRIPTIONS_EXTENSIONS.put(FichierConway.LIFE, description);
	}

	/**
	 * constructeur prive pour appliquer forcer l'utilisation du singleton (inutile d'instancier plusieurs fois cette
	 * classe)
	 */
	private VueFichierConway() {

	}

	@Override
	public String getTypeDescription(File fichier) {

		String extension = FichierUtilitaire.getExtension(fichier);
		return DESCRIPTIONS_EXTENSIONS.get(extension);
	}
}