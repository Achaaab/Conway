package conway.controle;

import utilitaire.ProcessusInterruptible;

/**
 * Processus charge de calculer les nouvelles generations d'une population.
 * 
 * @author guehenneux
 * 
 */
public class ProcessusGeneration extends ProcessusInterruptible {

	private Population population;

	/**
	 * @param population
	 */
	public ProcessusGeneration(Population population) {
		this.population = population;
	}

	@Override
	public void boucle() {
		population.generationSuivante();
	}

}