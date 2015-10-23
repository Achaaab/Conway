package conway.presentation;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import utilitaire.fichier.FichierUtilitaire;

/**
 * @author Jonathan Guéhenneux
 */
public class FiltreFichierConway extends FileFilter {

	public static final FiltreFichierConway INSTANCE = new FiltreFichierConway();

	/**
	 * constructeur prive pour appliquer forcer l'utilisation du singleton (inutile d'instancier plusieurs fois cette
	 * classe)
	 */
	private FiltreFichierConway() {

	}

	@Override
	public boolean accept(File fichier) {

		boolean fichierAccepte;

		if (fichier.isDirectory()) {

			fichierAccepte = true;

		} else {

			String extension = FichierUtilitaire.getExtension(fichier);
			fichierAccepte = VueFichierConway.DESCRIPTIONS_EXTENSIONS.containsKey(extension);
		}

		return fichierAccepte;
	}

	@Override
	public String getDescription() {
		return "Images, fichiers RLE et fichiers LIF";
	}
}