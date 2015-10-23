package conway.controle;

import conway.presentation.PresentationConway;
import conway.presentation.PresentationPopulation;

/**
 * @author Jonathan Guéhenneux
 */
public interface Population {

	/**
	 * @param editionManuellePossible
	 */
	public abstract void setEditionManuellePossible(boolean editionManuellePossible);

	/**
	 * calcule la generation suivante
	 */
	public abstract void generationSuivante();

	/**
	 * actualise la presentation (compteur de generations et taille de la population)
	 */
	public abstract void actualiserPresentation();

	/**
	 * 
	 * @return le nombre de cellules vivantes dans la population
	 */
	public abstract long getTaille();

	/**
	 * Cree une cellule vivante.
	 * 
	 * @param x
	 *            l'abscisse de la cellule a creer
	 * @param y
	 *            l'ordonnee de la cellule a creer
	 */
	public abstract void creerCelluleVivante(int x, int y);

	/**
	 * Change l'etat d'une cellule. Si il n'y a pas de cellule aux coordonnees fournies ou bien si elle est morte, cree
	 * une cellule vivante, sinon, tue la cellule.
	 * 
	 * @param x
	 *            l'abscisse de la cellule a inverser
	 * @param y
	 *            l'ordonnee de la cellule a inverser
	 */
	public abstract void inverserCellule(int x, int y);

	/**
	 * Vide la population.
	 */
	public abstract void effacer();

	/**
	 * @param x
	 * @param y
	 * @param largeur
	 * @param hauteur
	 * @return un echantillon de la population dans l'intervalle [x ; x + largeur[ x [y ; y + hauteur[, true signifie
	 *         cellule vivante, false signifie pas de cellule ou cellule morte
	 */
	public abstract boolean[][] getEchantillon(int x, int y, int largeur, int hauteur);

	/**
	 * @return le compteur de generations
	 */
	public abstract int getGeneration();

	/**
	 * @param generation
	 *            le nouveau compteur de generation
	 */
	public abstract void setGeneration(int generation);

	/**
	 * @return the presentation
	 */
	public abstract PresentationPopulation getPresentation();

	/**
	 * @param presentationConway
	 *            the presentationConway to set
	 */
	public abstract void setPresentationConway(PresentationConway presentationConway);
}