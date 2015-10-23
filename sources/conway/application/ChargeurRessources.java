package conway.application;

import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import conway.exceptions.RessourceManquante;

import utilitaire.GestionnaireException;
import utilitaire.RessourceUtilitaire;

/**
 * 
 * @author guehenneux
 * 
 */
public class ChargeurRessources {

	private static Map<String, Image> images = new HashMap<String, Image>();

	private static final String SEPARATEUR_DOSSIERS = "/";

	private static final String DOSSIER_ICONES = "/icones";

	/**
	 * 
	 * @param cheminImage
	 * @return
	 * @throws RessourceManquante
	 */
	private static Image chargerImage(String cheminImage)
			throws RessourceManquante {

		Image image;

		try {

			URL urlIcone = RessourceUtilitaire.getUrlRessource(cheminImage);
			image = ImageIO.read(urlIcone);

		} catch (Throwable cause) {

			RessourceManquante exception = new RessourceManquante(cheminImage,
					cause);

			throw exception;

		}

		return image;

	}

	/**
	 * 
	 * @param nomFichier
	 * @return
	 */
	public static Icon getIcone(String nomFichier) {

		Image image;
		Icon icone;

		try {

			image = getImage(DOSSIER_ICONES, nomFichier);
			icone = new ImageIcon(image);

		} catch (RessourceManquante erreur) {

			GestionnaireException.traiter(erreur);
			icone = null;

		}

		return icone;

	}

	/**
	 * 
	 * @param nomFichier
	 * @return
	 * @throws RessourceManquante
	 */
	public static Image getImage(String nomDossier, String nomFichier)
			throws RessourceManquante {

		String cheminImage = nomDossier + SEPARATEUR_DOSSIERS + nomFichier;

		Image image = images.get(cheminImage);

		if (image == null) {

			image = chargerImage(cheminImage);
			images.put(cheminImage, image);

		}

		return image;

	}

}