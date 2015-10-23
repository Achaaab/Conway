package utilitaire;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * @author GUEHENNEUX
 * 
 */
public class GestionnaireException {

	/**
	 * 
	 * @param erreur
	 */
	public static void traiter(Throwable erreur) {

		JFrame fenetreErreur = new JFrame();

		String typeErreur = erreur.getClass().getSimpleName();
		String messageErreur = erreur.getLocalizedMessage();

		StringBuilder message = new StringBuilder();

		message.append(typeErreur);
		message.append('(' + messageErreur + ')');

		Throwable erreurInitiale = ErreurUtilitaire.getErreurInitiale(erreur);

		if (erreurInitiale != erreur) {

			String typeErreurInitiale = erreurInitiale.getClass()
					.getSimpleName();

			String messageErreurInitiale = erreurInitiale.getLocalizedMessage();

			message.append("\nCause : ");
			message.append(typeErreurInitiale);
			message.append('(' + messageErreurInitiale + ')');

		}

		erreur.printStackTrace();

		JOptionPane.showMessageDialog(fenetreErreur, message, "Erreur",
				JOptionPane.ERROR_MESSAGE);

	}

}