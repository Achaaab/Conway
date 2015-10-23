package utilitaire.apparence;

import java.awt.Window;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * 
 * @author guehenneux
 * 
 */
public class LookAndFeelUtilitaire {

	/**
	 * Tente d'appliquer un look and feel en le recherchant par son nom dans la
	 * liste des look and feel installes. Actualise toutes les fenetres de
	 * l'application.
	 * 
	 * Ne fait rien si le aucun des look and feel installes ne porte le nom
	 * fourni.
	 * 
	 * 
	 * @param nom
	 *            nom du look and feel a appliquer
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void setLookAndFeelParNom(String nom)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		LookAndFeelInfo[] informationsLnf = UIManager
				.getInstalledLookAndFeels();

		String nomLnf;

		for (LookAndFeelInfo informationLnf : informationsLnf) {

			nomLnf = informationLnf.getName();

			if (nomLnf.equals(nom)) {

				String classeLnf = informationLnf.getClassName();
				setLookAndFeelParClasse(classeLnf);
				break;

			}

		}

	}

	/**
	 * Applique un look and feel et actualise les fenetres.
	 * 
	 * @param classe
	 *            classe du look and feel a appliquer
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void setLookAndFeelParClasse(String classe)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		UIManager.setLookAndFeel(classe);

		Window[] fenetres = Window.getWindows();

		for (Window fenetre : fenetres) {

			SwingUtilities.updateComponentTreeUI(fenetre);
			fenetre.pack();

		}

	}

}