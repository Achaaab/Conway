package utilitaire.apparence;

import javax.swing.JRadioButtonMenuItem;
import javax.swing.plaf.metal.MetalTheme;



/**
 * 
 * @author GUEHENNEUX
 * 
 */
public class BoutonLookAndFeel extends JRadioButtonMenuItem {

	/**
	 * UID genere le 29/04/2010
	 */
	private static final long serialVersionUID = -3574057587020244398L;

	/**
	 * 
	 * @param nomLookAndFeel
	 * @param classeLookAndFeel
	 * @param theme
	 *            optionnel (renseigner seulement pour le Look And Feel Metal)
	 */
	public BoutonLookAndFeel(String nomLookAndFeel, String classeLookAndFeel,
			MetalTheme theme) {

		super(getTexte(nomLookAndFeel, theme));

		EcouteurChangementLnf ecouteur = new EcouteurChangementLnf(
				classeLookAndFeel, theme);

		addActionListener(ecouteur);

	}

	/**
	 * 
	 * @param nomLookAndFeel
	 * @param theme
	 * @return
	 */
	private static String getTexte(String nomLookAndFeel, MetalTheme theme) {

		String texte;

		if (theme == null) {
			texte = nomLookAndFeel;
		} else {
			texte = theme.getName();
		}

		return texte;

	}

}