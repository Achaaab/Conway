package conway.controle;

import utilitaire.ProcessusInterruptible;

/**
 * Processus chargé de calculer les nouvelles générations d'une population.
 * 
 * @author Jonathan Guéhenneux
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