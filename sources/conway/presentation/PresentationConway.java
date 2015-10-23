package conway.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utilitaire.GestionnaireException;
import conway.application.ChargeurRessources;
import conway.controle.Conway;
import conway.fichier.FichierConway;

/**
 * 
 * @author guehenneux
 * 
 */
public class PresentationConway extends JPanel {

	/**
	 * UID genere le 25/05/2010
	 */
	private static final long serialVersionUID = 5317987271771499202L;

	private static final int TAILLE_BOUTONS = 48;

	private static final Icon ICONE_LECTURE_DESACTIVE;
	private static final Icon ICONE_LECTURE_NORMAL;
	private static final Icon ICONE_LECTURE_SURVOLE;
	private static final Icon ICONE_LECTURE_ENFONCE;

	private static final Icon ICONE_SUIVANT_DESACTIVE;
	private static final Icon ICONE_SUIVANT_NORMAL;
	private static final Icon ICONE_SUIVANT_SURVOLE;
	private static final Icon ICONE_SUIVANT_ENFONCE;

	private static final Icon ICONE_PAUSE_DESACTIVE;
	private static final Icon ICONE_PAUSEE_NORMAL;
	private static final Icon ICONE_PAUSE_SURVOLE;
	private static final Icon ICONE_PAUSE_ENFONCE;

	private static final Icon ICONE_STOP_DESACTIVE;
	private static final Icon ICONE_STOP_NORMAL;
	private static final Icon ICONE_STOP_SURVOLE;
	private static final Icon ICONE_STOP_ENFONCE;

	static {

		ICONE_LECTURE_DESACTIVE = ChargeurRessources.getIcone("lecture_desactive_48.png");
		ICONE_LECTURE_NORMAL = ChargeurRessources.getIcone("lecture_normal_48.png");
		ICONE_LECTURE_SURVOLE = ChargeurRessources.getIcone("lecture_survole_48.png");
		ICONE_LECTURE_ENFONCE = ChargeurRessources.getIcone("lecture_enfonce_48.png");

		ICONE_SUIVANT_DESACTIVE = ChargeurRessources.getIcone("suivant_desactive_48.png");
		ICONE_SUIVANT_NORMAL = ChargeurRessources.getIcone("suivant_normal_48.png");
		ICONE_SUIVANT_SURVOLE = ChargeurRessources.getIcone("suivant_survole_48.png");
		ICONE_SUIVANT_ENFONCE = ChargeurRessources.getIcone("suivant_enfonce_48.png");

		ICONE_PAUSE_DESACTIVE = ChargeurRessources.getIcone("pause_desactive_48.png");
		ICONE_PAUSEE_NORMAL = ChargeurRessources.getIcone("pause_normal_48.png");
		ICONE_PAUSE_SURVOLE = ChargeurRessources.getIcone("pause_survole_48.png");
		ICONE_PAUSE_ENFONCE = ChargeurRessources.getIcone("pause_enfonce_48.png");

		ICONE_STOP_DESACTIVE = ChargeurRessources.getIcone("stop_desactive_48.png");
		ICONE_STOP_NORMAL = ChargeurRessources.getIcone("stop_normal_48.png");
		ICONE_STOP_SURVOLE = ChargeurRessources.getIcone("stop_survole_48.png");
		ICONE_STOP_ENFONCE = ChargeurRessources.getIcone("stop_enfonce_48.png");

	}

	private JFileChooser choixFichier;

	private Conway conway;

	private JSplitPane panneauPrincipal;

	private PresentationPopulation population;
	private JPanel commandes;
	private JPanel panneauEst;

	private JPanel parametres;

	private JButton lecture;
	private JButton pause;
	private JButton suivant;
	private JButton stop;

	private JPanel labelsParametres;
	private JPanel valeursParametres;

	private JLabel labelRafraichissement;
	private JSlider rafraichissement;

	private JLabel labelGeneration;
	private JSlider generation;

	private JLabel labelDebridage;
	private JCheckBox debridage;

	private JLabel labelNombreGenerations;
	private JLabel nombreGenerations;

	private JLabel labelTaille;
	private JLabel taille;

	private JPanel edition;

	private JButton effacer;
	private JButton ouvrir;

	/**
	 * @param conway
	 */
	public PresentationConway(Conway conway) {

		this.conway = conway;

		setLayout(new BorderLayout());

		creerComposants();
		ajouterComposants();
		ajouterEcouteurs();

	}

	/**
	 * 
	 */
	private final void creerComposants() {

		choixFichier = new JFileChooser();
		choixFichier.setFileView(VueFichierConway.getInstance());
		choixFichier.setFileFilter(FiltreFichierConway.getInstance());

		panneauPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		panneauPrincipal.setOneTouchExpandable(true);
		panneauPrincipal.setContinuousLayout(true);

		/*
		 * presentation de la population
		 */

		population = conway.getPresentationPopulation();

		/*
		 * presentation des commandes de base
		 */

		commandes = new JPanel();

		lecture = new BoutonLecteur(ICONE_LECTURE_DESACTIVE, ICONE_LECTURE_NORMAL, ICONE_LECTURE_SURVOLE,
				ICONE_LECTURE_ENFONCE, TAILLE_BOUTONS, TAILLE_BOUTONS);

		pause = new BoutonLecteur(ICONE_PAUSE_DESACTIVE, ICONE_PAUSEE_NORMAL, ICONE_PAUSE_SURVOLE, ICONE_PAUSE_ENFONCE,
				TAILLE_BOUTONS, TAILLE_BOUTONS);

		suivant = new BoutonLecteur(ICONE_SUIVANT_DESACTIVE, ICONE_SUIVANT_NORMAL, ICONE_SUIVANT_SURVOLE,
				ICONE_SUIVANT_ENFONCE, TAILLE_BOUTONS, TAILLE_BOUTONS);

		stop = new BoutonLecteur(ICONE_STOP_DESACTIVE, ICONE_STOP_NORMAL, ICONE_STOP_SURVOLE, ICONE_STOP_ENFONCE,
				TAILLE_BOUTONS, TAILLE_BOUTONS);

		lecture.setEnabled(true);
		suivant.setEnabled(true);
		pause.setEnabled(false);
		stop.setEnabled(false);

		/*
		 * presentation des parametres
		 */

		parametres = new JPanel();

		BoxLayout dispositionParametres = new BoxLayout(parametres, BoxLayout.LINE_AXIS);
		parametres.setLayout(dispositionParametres);

		Border bordureParametres = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		parametres.setBorder(bordureParametres);

		labelsParametres = new JPanel();
		labelsParametres.setLayout(new GridLayout(5, 1, 5, 5));

		valeursParametres = new JPanel();
		valeursParametres.setLayout(new GridLayout(5, 1, 5, 5));

		labelGeneration = new JLabel("Vitesse de génération : ");

		generation = new JSlider(JSlider.HORIZONTAL, Conway.TAUX_GENERATION_MINIMUM, Conway.TAUX_GENERATION_MAXIMUM,
				conway.getTauxGeneration());

		generation.setMajorTickSpacing(500);
		generation.setMinorTickSpacing(100);

		generation.setPaintLabels(true);
		generation.setPaintTicks(true);
		generation.setPaintTrack(true);

		labelDebridage = new JLabel("Débridage : ");

		debridage = new JCheckBox();
		debridage.setSelected(conway.isDebride());

		labelRafraichissement = new JLabel("Taux de rafraîchissement : ");

		rafraichissement = new JSlider(JSlider.HORIZONTAL, Conway.TAUX_RAFRAICHISSEMENT_MINIMUM,
				Conway.TAUX_RAFRAICHISSEMENT_MAXIMUM, conway.getTauxRafraichissement());

		rafraichissement.setMajorTickSpacing(50);
		rafraichissement.setMinorTickSpacing(10);

		rafraichissement.setPaintLabels(true);
		rafraichissement.setPaintTicks(true);
		rafraichissement.setPaintTrack(true);

		labelNombreGenerations = new JLabel("Génération : ");

		nombreGenerations = new JLabel(Integer.toString(conway.getPopulation().getGeneration()));

		nombreGenerations.setForeground(Color.BLUE);

		labelTaille = new JLabel("Taille de la population : ");

		taille = new JLabel(Long.toString(conway.getPopulation().getTaille()));
		taille.setForeground(Color.BLUE);

		/*
		 * presentation des outils d'edition
		 */

		edition = new JPanel();

		effacer = new JButton("Effacer");
		ouvrir = new JButton("Ouvrir");

		panneauEst = new JPanel();
		panneauEst.setLayout(new BorderLayout());

	}

	/**
	 * 
	 */
	private final void ajouterComposants() {

		commandes.add(lecture);
		commandes.add(suivant);
		commandes.add(pause);
		commandes.add(stop);

		labelsParametres.add(labelGeneration);
		labelsParametres.add(labelDebridage);
		labelsParametres.add(labelRafraichissement);
		labelsParametres.add(labelNombreGenerations);
		labelsParametres.add(labelTaille);

		valeursParametres.add(generation);
		valeursParametres.add(debridage);
		valeursParametres.add(rafraichissement);
		valeursParametres.add(nombreGenerations);
		valeursParametres.add(taille);

		parametres.add(labelsParametres);
		parametres.add(valeursParametres);
		parametres.add(Box.createHorizontalGlue());

		edition.add(effacer);
		edition.add(ouvrir);

		panneauEst.add(parametres, BorderLayout.NORTH);
		panneauEst.add(edition, BorderLayout.SOUTH);

		panneauPrincipal.setLeftComponent(population);
		panneauPrincipal.setRightComponent(panneauEst);

		add(panneauPrincipal, BorderLayout.CENTER);
		add(commandes, BorderLayout.SOUTH);

	}

	/**
	 * 
	 */
	private final void ajouterEcouteurs() {

		lecture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evenement) {
				lecture();
			}

		});

		pause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evenement) {
				pause();
			}

		});

		suivant.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evenement) {
				suivant();
			}

		});

		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evenement) {
				stop();
			}

		});

		debridage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evenement) {
				lireDebridage();
			}

		});

		generation.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent evenement) {
				lireTauxGeneration();
			}

		});

		rafraichissement.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent evenement) {
				lireTauxRafraichissement();
			}

		});

		effacer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evenement) {
				effacer();
			}

		});

		ouvrir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evenement) {
				ouvrir();
			}

		});

	}

	/**
	 * 
	 */
	private void ouvrir() {

		stop();

		int option = choixFichier.showOpenDialog(this);

		if (option == JFileChooser.APPROVE_OPTION) {

			File fichier = choixFichier.getSelectedFile();

			try {
				FichierConway.ajouter(fichier, conway.getPopulation());
			} catch (Exception erreur) {
				GestionnaireException.traiter(erreur);
			}

		}

	}

	/**
	 * 
	 */
	private void effacer() {

		stop();
		conway.effacer();
	}

	/**
	 * 
	 */
	private void lireDebridage() {

		boolean debride = debridage.isSelected();
		generation.setEnabled(!debride);
		conway.setDebride(debride);
	}

	/**
	 * 
	 */
	private void lireTauxGeneration() {

		int tauxGeneration = generation.getValue();

		if (tauxGeneration < 1) {
			tauxGeneration = 1;
		}

		conway.setTauxGeneration(tauxGeneration);
	}

	/**
	 * 
	 */
	private void lireTauxRafraichissement() {

		int tauxRafraichissement = rafraichissement.getValue();

		if (tauxRafraichissement < 1) {
			tauxRafraichissement = 1;
		}

		conway.setTauxRafraichissement(tauxRafraichissement);
	}

	/**
	 * 
	 */
	private void lecture() {

		conway.demarrer();

		lecture.setEnabled(false);
		suivant.setEnabled(false);
		pause.setEnabled(true);
		stop.setEnabled(true);
	}

	/**
	 * 
	 */
	private void stop() {

		try {

			conway.arreter();

			lecture.setEnabled(true);
			suivant.setEnabled(true);
			pause.setEnabled(false);
			stop.setEnabled(false);

		} catch (InterruptedException erreur) {

			GestionnaireException.traiter(erreur);
		}
	}

	/**
	 * 
	 */
	private void suivant() {
		conway.generationSuivante();
	}

	/**
	 * 
	 */
	private void pause() {

		try {

			conway.pause();

			lecture.setEnabled(true);
			suivant.setEnabled(true);
			pause.setEnabled(false);
			stop.setEnabled(true);

		} catch (InterruptedException erreur) {

			GestionnaireException.traiter(erreur);
		}
	}

	/**
	 * @param generation
	 */
	public void setGeneration(int generation) {
		nombreGenerations.setText(Integer.toString(generation));
	}

	/**
	 * @param taille
	 */
	public void setTaille(long taille) {
		this.taille.setText(Long.toString(taille));
	}
}