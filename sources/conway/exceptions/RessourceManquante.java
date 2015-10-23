package conway.exceptions;

import java.text.MessageFormat;

/**
 * 
 * @author GUEHENNEUX
 * 
 */
public class RessourceManquante extends Exception {

	/**
	 * UID genere le 30/04/2010
	 */
	private static final long serialVersionUID = 7903307454194832392L;

	private static final MessageFormat FORMAT_MESSAGE = new MessageFormat(
			"La ressource {0} est manquante.");

	/**
	 * 
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
	 * 
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