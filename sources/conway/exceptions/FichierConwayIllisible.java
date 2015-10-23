package conway.exceptions;

import java.text.MessageFormat;

/**
 * @author Jonathan Guéhenneux
 */
public class FichierConwayIllisible extends Exception {

	private static final MessageFormat FORMAT_MESSAGE = new MessageFormat("La type de fichier {0} est illisible.");

	/**
	 * @param extension
	 * @return
	 */
	private static String fabriquerMessage(String extension) {

		String[] parametresMessage = { extension };
		String message = FORMAT_MESSAGE.format(parametresMessage);
		return message;
	}

	/**
	 * @param extension
	 */
	public FichierConwayIllisible(String extension) {
		super(fabriquerMessage(extension));
	}
}