package utilitaire.apparence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;


/**
 * 
 * @author GUEHENNEUX
 * 
 */
public class MenuLookAndFeel extends JMenu {

	/**
	 * UID genere le 29/04/2010
	 */
	private static final long serialVersionUID = 6489764030337693826L;

	private static final Map<String, String> CLASSES_LNF;

	/**
	 * nom du look and feel : Metal
	 */
	private static final String NOM_LNF_METAL = "Metal";

	/**
	 * classe du look and feel : Metal
	 */
	private static final String CLASSE_LNF_METAL = MetalLookAndFeel.class
			.getName();

	/**
	 * liste des themes existants pour le look and feel metal
	 */
	private static final List<MetalTheme> THEMES_METAL;

	static {

		/*
		 * on recupere les look and feel installes
		 */

		CLASSES_LNF = new HashMap<String, String>();

		LookAndFeelInfo[] informationsLnf = UIManager
				.getInstalledLookAndFeels();

		String nomLnf;
		String classeLnf;

		for (LookAndFeelInfo informationLnf : informationsLnf) {

			nomLnf = informationLnf.getName();
			classeLnf = informationLnf.getClassName();
			CLASSES_LNF.put(nomLnf, classeLnf);

		}

		/*
		 * on liste les themes disponibles pour le look and feel metal
		 */

		THEMES_METAL = new ArrayList<MetalTheme>();

		THEMES_METAL.add(new DefaultMetalTheme());
		THEMES_METAL.add(new OceanTheme());

	}

	private ButtonGroup groupeLnf;

	/**
	 * 
	 */
	public MenuLookAndFeel() {

		super("Apparence");

		creerComposants();

	}

	/**
	 * 
	 */
	private void creerComposants() {

		LookAndFeel lnfCourant = UIManager.getLookAndFeel();
		String nomLnfCourant = lnfCourant.getName();
		String nomThemeMetalCourant = MetalLookAndFeel.getCurrentTheme()
				.getName();

		boolean lnfCourantMetal = nomLnfCourant.equals(NOM_LNF_METAL);

		groupeLnf = new ButtonGroup();

		Set<String> nomsLnf = CLASSES_LNF.keySet();

		JRadioButtonMenuItem lnf;
		String classeLnf;

		for (String nomLnf : nomsLnf) {

			classeLnf = CLASSES_LNF.get(nomLnf);

			/*
			 * on ajoute les themes "Metal" dans un sous-menu
			 */

			if (nomLnf.equals(NOM_LNF_METAL)) {

				JMenu menuMetal = new JMenu(NOM_LNF_METAL);

				String nomThemeMetal;

				for (MetalTheme themeMetal : THEMES_METAL) {

					nomThemeMetal = themeMetal.getName();

					lnf = new BoutonLookAndFeel(NOM_LNF_METAL,
							CLASSE_LNF_METAL, themeMetal);

					lnf.setSelected(lnfCourantMetal
							&& nomThemeMetal.equals(nomThemeMetalCourant));

					groupeLnf.add(lnf);
					menuMetal.add(lnf);

				}

				add(menuMetal);

			} else {

				lnf = new BoutonLookAndFeel(nomLnf, classeLnf, null);
				lnf.setSelected(nomLnf.equals(nomLnfCourant));

				groupeLnf.add(lnf);
				add(lnf);

			}

		}

	}

}