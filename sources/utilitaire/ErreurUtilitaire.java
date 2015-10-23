package utilitaire;

/**
 * @author Jonathan Gu�henneux
 */
public class ErreurUtilitaire {

	/**
	 * @param erreur
	 * @return
	 */
	public static Throwable getErreurInitiale(Throwable erreur) {

		Throwable cause = erreur.getCause();
		Throwable erreurInitiale;

		if (cause != null && cause != erreur) {
			erreurInitiale = getErreurInitiale(cause);
		} else {
			erreurInitiale = erreur;
		}

		return erreurInitiale;
	}
}