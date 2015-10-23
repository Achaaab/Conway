package conway.application;

import utilitaire.apparence.LookAndFeelUtilitaire;
import utilitaire.swing.FenetreApplication;
import conway.controle.Conway;

/**
 * 
 * @author guehenneux
 * 
 */
public class ConwayFenetre {

	/**
	 * 
	 * @param arguments
	 *            inutiles
	 * @throws Exception
	 */
	public static void main(String... arguments) throws Exception {

		LookAndFeelUtilitaire.setLookAndFeelParNom("Nimbus");

		Conway conway = new Conway();
		new FenetreApplication(conway.getPresentation(),
				"Le jeu de la vie (Conway)");

	}

}