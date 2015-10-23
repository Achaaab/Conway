package utilitaire;

import java.io.InputStream;
import java.net.URL;

/**
 * @author Jonathan Gu�henneux
 */
public class RessourceUtilitaire {

	/**
	 * @param cheminRessource
	 * @return
	 */
	public static URL getUrlRessource(String cheminRessource) {
		return RessourceUtilitaire.class.getResource(cheminRessource);
	}

	/**
	 * @param cheminRessource
	 * @return
	 */
	public static InputStream getFluxLecture(String cheminRessource) {
		return RessourceUtilitaire.class.getResourceAsStream(cheminRessource);
	}
}