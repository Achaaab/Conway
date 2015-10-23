package conway.presentation;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JButton;

/**
 * 
 * @author guehenneux
 * 
 */
public class BoutonLecteur extends JButton {

	/**
	 * UID genere le 25/05/2010
	 */
	private static final long serialVersionUID = -8890215816301927881L;

	/**
	 * 
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