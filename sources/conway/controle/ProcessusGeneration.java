package conway.controle;

import utilitaire.ProcessusInterruptible;

/**
 * Processus charg� de calculer les nouvelles g�n�rations d'une population.
 * 
 * @author Jonathan Gu�henneux
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