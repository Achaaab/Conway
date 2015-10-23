package conway.presentation;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import utilitaire.fichier.FichierUtilitaire;

/**
 * 
 * @author guehenneux
 * 
 */
public class FiltreFichierConway extends FileFilter {

	private static FiltreFichierConway instance;

	/**
	 * 
	 * @return l'instance unique de la classe
	 */
	public static synchronized FiltreFichierConway getInstance() {

		if (instance == null) {
			instance = new FiltreFichierConway();
		}

		return instance;

	}

	/**
	 * constructeur prive pour appliquer forcer l'utilisation du singleton
	 * (inutile d'instancier plusieurs fois cette classe)
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
			fichierAccepte = VueFichierConway.DESCRIPTIONS_EXTENSIONS
					.containsKey(extension);

		}

		return fichierAccepte;

	}

	@Override
	public String getDescription() {
		return "Images, fichiers RLE et fichiers LIF";
	}

}