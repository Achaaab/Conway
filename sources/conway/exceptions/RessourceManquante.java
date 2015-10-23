package conway.exceptions;

import java.text.MessageFormat;

/**
 * @author Jonathan Guéhenneux
 */
public class RessourceManquante extends Exception {

	private static final MessageFormat FORMAT_MESSAGE = new MessageFormat("La ressource {0} est manquante.");

	/**
	 * @param cheminRessource
	 * @return
	 */
	private static String fabriquerMessage(String cheminRessource) {

		String[] parametresMessage = { cheminRessource };
		String message = FORMAT_MESSAGE.format(parametresMessage);
		return message;
	}

	private String cheminRessource;

	/**
	 * @param cheminRessource
	 */
	public RessourceManquante(String cheminRessource) {

		super(fabriquerMessage(cheminRessource));

		this.cheminRessource = cheminRessource;
	}

	/**
	 * @param cheminRessource
	 * @param exceptionMere
	 */
	public RessourceManquante(String cheminRessource, Throwable exceptionMere) {

		super(fabriquerMessage(cheminRessource), exceptionMere);

		this.cheminRessource = cheminRessource;
	}

	/**
	 * @return the cheminRessource
	 */
	public String getCheminRessource() {
		return cheminRessource;
	}
}