package utilitaire.multithreading;

/**
 * 
 * @author GUEHENNEUX
 * 
 */
public interface Tache<TypeTachePartielle extends TachePartielle> {

	/**
	 * 
	 * @return la partie suivante de la tache, null si la tache est terminee
	 */
	public abstract TypeTachePartielle getTachePartielle();

}