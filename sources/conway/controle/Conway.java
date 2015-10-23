package conway.controle;

import utilitaire.Chronometre;
import conway.presentation.PresentationConway;
import conway.presentation.PresentationPopulation;

/**
 * Objet principal de l'application. Encapsule une population et les actions
 * permettant de la manipuler.
 * 
 * @author guehenneux
 * 
 */
public class Conway {

	public static final int TAUX_GENERATION_MINIMUM = 0;
	public static final int TAUX_GENERATION_MAXIMUM = 1000;

	public static final int TAUX_RAFRAICHISSEMENT_MINIMUM = 0;
	public static final int TAUX_RAFRAICHISSEMENT_MAXIMUM = 200;

	private Population population;
	private PresentationPopulation presentationPopulation;
	private ProcessusGeneration generation;

	private int tauxGeneration;
	private int tauxRafraichissement;
	private boolean debride;

	private PresentationConway presentation;

	/**
	 * Cree une nouvelle population vide.
	 */
	public Conway() {

		population = new PopulationTable();
		presentationPopulation = population.getPresentation();

		tauxGeneration = 50;
		debride = false;

		setTauxRafraichissement(60);

		presentation = new PresentationConway(this);
		population.setPresentationConway(presentation);

	}

	/**
	 * Arrete le processus de generation. Reinitialise le compteur de
	 * generations.
	 * 
	 * @throws InterruptedException
	 */
	public void arreter() throws InterruptedException {

		if (generation != null) {

			generation.interrompre();
			generation.join();
			population.setGeneration(0);
			population.setEditionManuellePossible(true);

		}

	}

	/**
	 * Demarre le processus de generation.
	 */
	public void demarrer() {

		population.setEditionManuellePossible(false);

		generation = new ProcessusGeneration(population);

		if (debride) {

			generation.setDureeBoucle(0);

		} else {

			generation.setDureeBoucle(Chronometre.MILLISECONDES_PAR_SECONDE
					/ tauxGeneration);

		}

		generation.start();

	}

	/**
	 * 
	 * Met en pause le processus de generation.
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public void pause() throws InterruptedException {

		generation.interrompre();
		population.setEditionManuellePossible(true);

	}

	/**
	 * Calcule la generation suivante.
	 */
	public void generationSuivante() {
		population.generationSuivante();
	}

	/**
	 * Vide la population.
	 */
	public void effacer() {
		population.effacer();
	}

	/**
	 * @return the tauxRafraichissement
	 */
	public int getTauxRafraichissement() {
		return tauxRafraichissement;
	}

	/**
	 * Modifie le taux de rafraichissement.
	 * 
	 * @param tauxRafraichissement
	 *            le nouveau taux de rafraichissement en images par seconde.
	 */
	public void setTauxRafraichissement(int tauxRafraichissement) {

		this.tauxRafraichissement = tauxRafraichissement;
		presentationPopulation.rafraichirAutomatiquement(tauxRafraichissement);

	}

	/**
	 * Modifie le taux de generation.
	 * 
	 * @param tauxGeneration
	 *            le nouveau taux de generation (en generations par seconde).
	 */
	public void setTauxGeneration(int tauxGeneration) {

		this.tauxGeneration = tauxGeneration;

		if (generation != null && generation.isAlive()) {

			generation.setDureeBoucle(Chronometre.MILLISECONDES_PAR_SECONDE
					/ tauxGeneration);

		}

	}

	/**
	 * @return the tauxGeneration
	 */
	public int getTauxGeneration() {
		return tauxGeneration;
	}

	/**
	 * Permet de debrider ou non le processus de generation.
	 * 
	 * @param debride
	 */
	public void setDebride(boolean debride) {

		this.debride = debride;

		if (generation != null && generation.isAlive()) {

			if (debride) {

				generation.setDureeBoucle(0);

			} else {

				generation.setDureeBoucle(Chronometre.MILLISECONDES_PAR_SECONDE
						/ tauxGeneration);

			}

		}

	}

	/**
	 * 
	 * @return true si le processus de generation est debride (vitesse maximum)
	 *         ou controle (vitesse specifiee).
	 */
	public boolean isDebride() {
		return debride;
	}

	/**
	 * @return the population
	 */
	public Population getPopulation() {
		return population;
	}

	/**
	 * @return the presentationPopulation
	 */
	public PresentationPopulation getPresentationPopulation() {
		return presentationPopulation;
	}

	/**
	 * @return the presentation
	 */
	public PresentationConway getPresentation() {
		return presentation;
	}

}