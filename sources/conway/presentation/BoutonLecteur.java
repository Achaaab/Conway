package conway.presentation;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JButton;

/**
 * @author Jonathan Guéhenneux
 */
public class BoutonLecteur extends JButton {

	/**
	 * @param iconeDesactive
	 * @param iconeNormal
	 * @param iconeSurvole
	 * @param iconeEnfonce
	 * @param largeur
	 * @param hauteur
	 */
	public BoutonLecteur(Icon iconeDesactive, Icon iconeNormal,
			Icon iconeSurvole, Icon iconeEnfonce, int largeur, int hauteur) {

		if (iconeDesactive != null) {
			setDisabledIcon(iconeDesactive);
		}

		if (iconeNormal != null) {
			setIcon(iconeNormal);
		}

		if (iconeSurvole != null) {
			setRolloverIcon(iconeSurvole);
		}

		if (iconeEnfonce != null) {
			setPressedIcon(iconeEnfonce);
		}

		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusPainted(false);

		Dimension taille = new Dimension(largeur, hauteur);
		setPreferredSize(taille);
	}
}