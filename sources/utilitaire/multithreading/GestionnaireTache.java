package utilitaire.multithreading;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author GUEHENNEUX
 * 
 */
public class GestionnaireTache {
	
	private Tache<?> tache;
	private List<ExecuteurTache> executeurs;

	/**
	 * @param tache
	 *            la tache a effectuer
	 * @param nombreExecuteurs
	 */
	public GestionnaireTache(Tache<?> tache, int nombreExecuteurs) {

		this.tache = tache;

		executeurs = new ArrayList<ExecuteurTache>(nombreExecuteurs);

		for (int indexExecuteur = 0; indexExecuteur < nombreExecuteurs; indexExecuteur++) {
			executeurs.add(new ExecuteurTache(tache));
		}

	}

	/**
	 * lance l'execution de la tache
	 * 
	 * @throws InterruptedException
	 */
	public void executer() throws InterruptedException {

		for (ExecuteurTache executeur : executeurs) {
			executeur.start();
		}

		for (ExecuteurTache executeur : executeurs) {
			executeur.join();
		}

	}

	/**
	 * 
	 * @return la tache a effectuer
	 */
	public Tache<?> getTache() {
		return tache;
	}

}