package utilitaire.apparence;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;

import utilitaire.GestionnaireException;

/**
 * 
 * @author GUEHENNEUX
 * 
 */
public class EcouteurChangementLnf implements ActionListener {

	private String classeLnf;

	private MetalTheme theme;

	/**
	 * 
	 * @param classeLnf
	 * @param theme
	 *            optionnel (renseigner seulement pour le Look And feel Metal)
	 */
	public EcouteurChangementLnf(String classeLnf, MetalTheme theme) {

		this.classeLnf = classeLnf;
		this.theme = theme;

	}

	@Override
	public void actionPerformed(ActionEvent evenement) {

		try {

			if (theme != null) {
				MetalLookAndFeel.setCurrentTheme(theme);
			}

			UIManager.setLookAndFeel(classeLnf);

			Window[] fenetres = Window.getWindows();

			for (Window fenetre : fenetres) {

				SwingUtilities.updateComponentTreeUI(fenetre);
				fenetre.pack();

			}

		} catch (Exception exception) {

			GestionnaireException.traiter(exception);

		}

	}

}