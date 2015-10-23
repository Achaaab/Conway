package conway.exceptions;

import java.text.MessageFormat;

/**
 * 
 * @author guehenneux
 * 
 */
public class FichierConwayIllisible extends Exception {

	/**
	 * UID genere le 09/06/2010
	 */
	private static final long serialVersionUID = 5453568446011786095L;

	private static final MessageFormat FORMAT_MESSAGE = new MessageFormat(
			"La type de fichier {0} est illisible.");

	/**
	 * 
	 * @param extension
	 * @return
	 */
	private static String fabriquerMessage(String extension) {

		String[] parametresMessage = { extension };
		String message = FORMAT_MESSAGE.format(parametresMessage);
		return message;

	}

	/**
	 * 
	 * @param extension
	 */
	public FichierConwayIllisible(String extension) {
		super(fabriquerMessage(extension));
	}

}