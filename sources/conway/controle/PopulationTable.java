package conway.controle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import conway.presentation.PresentationConway;
import conway.presentation.PresentationPopulation;

/**
 * population représentée par une table de hachage, la taille maximale de l'univers est 2^32 * 2^32
 * 
 * @author Jonathan Guéhenneux
 */
public class PopulationTable implements Population {

	private Map<Integer, Map<Integer, Cellule>> cellules;

	private List<Cellule> cellulesMortes;
	private List<Cellule> cellulesNees;

	private long nombreCellulesVivantes;
	private int generation;

	private boolean editionManuellePossible;

	private PresentationPopulation presentation;
	private PresentationConway presentationConway;

	private int xEchantillon;
	private int yEchantillon;
	private int largeurEchantillon;
	private int hauteurEchantillon;

	private boolean[][] echantillon;

	/**
	 * 
	 */
	public PopulationTable() {

		cellules = new HashMap<>();

		nombreCellulesVivantes = 0;
		generation = 0;

		presentation = new PresentationPopulation(this);

		editionManuellePossible = true;

		cellulesMortes = new ArrayList<>();
		cellulesNees = new ArrayList<>();
	}

	/**
	 * 
	 */
	private void appliquerRegles() {

		cellulesMortes.clear();
		cellulesNees.clear();

		Collection<Map<Integer, Cellule>> colonnes = cellules.values();
		Collection<Cellule> cellulesColonne;

		for (Map<Integer, Cellule> colonne : colonnes) {

			cellulesColonne = colonne.values();

			for (Cellule cellule : cellulesColonne) {

				if (cellule.vivante) {

					if (cellule.nombreVoisins < 2 || cellule.nombreVoisins > 3) {
						cellulesMortes.add(cellule);
					}

				} else if (cellule.nombreVoisins == 3) {

					cellulesNees.add(cellule);
				}
			}
		}
	}

	/**
	 * 
	 */
	private void actualiserCellules() {

		for (Cellule cellule : cellulesMortes) {
			mort(cellule);
		}

		for (Cellule cellule : cellulesNees) {
			naissance(cellule);
		}
	}

	/**
	 * les cellules mortes n'ayant plus de voisins sont eliminees de la population pour soulager l'algorithme
	 */
	private void eliminerCellulesInutiles() {

		Collection<Map<Integer, Cellule>> colonnes = cellules.values();

		for (Map<Integer, Cellule> colonne : colonnes) {

			Collection<Cellule> collectionCellules = colonne.values();
			Iterator<Cellule> iterateurCellules = collectionCellules.iterator();

			Cellule cellule;

			while (iterateurCellules.hasNext()) {

				cellule = iterateurCellules.next();

				if (!cellule.vivante && cellule.nombreVoisins == 0) {
					iterateurCellules.remove();
				}
			}
		}
	}

	/**
	 * Tue une cellule.
	 * 
	 * @param cellule
	 */
	private void mort(Cellule cellule) {

		cellule.vivante = false;
		nombreCellulesVivantes--;

		int x = cellule.x;
		int y = cellule.y;

		getCellule(x, y - 1).nombreVoisins--;
		getCellule(x + 1, y - 1).nombreVoisins--;
		getCellule(x + 1, y).nombreVoisins--;
		getCellule(x + 1, y + 1).nombreVoisins--;
		getCellule(x, y + 1).nombreVoisins--;
		getCellule(x - 1, y + 1).nombreVoisins--;
		getCellule(x - 1, y).nombreVoisins--;
		getCellule(x - 1, y - 1).nombreVoisins--;

		if (x >= xEchantillon && x < xEchantillon + largeurEchantillon && y >= yEchantillon
				&& y < yEchantillon + hauteurEchantillon) {

			echantillon[x - xEchantillon][y - yEchantillon] = false;
		}
	}

	/**
	 * Donne naissance à une cellule.
	 * 
	 * @param cellule
	 */
	private void naissance(Cellule cellule) {

		cellule.vivante = true;
		nombreCellulesVivantes++;

		int x = cellule.x;
		int y = cellule.y;

		getCellule(x, y - 1).nombreVoisins++;
		getCellule(x + 1, y - 1).nombreVoisins++;
		getCellule(x + 1, y).nombreVoisins++;
		getCellule(x + 1, y + 1).nombreVoisins++;
		getCellule(x, y + 1).nombreVoisins++;
		getCellule(x - 1, y + 1).nombreVoisins++;
		getCellule(x - 1, y).nombreVoisins++;
		getCellule(x - 1, y - 1).nombreVoisins++;

		if (x >= xEchantillon && x < xEchantillon + largeurEchantillon && y >= yEchantillon
				&& y < yEchantillon + hauteurEchantillon) {

			echantillon[x - xEchantillon][y - yEchantillon] = true;
		}
	}

	@Override
	public synchronized void generationSuivante() {

		/*
		 * on applique les regles du jeu de la vie afin de determiner quelles cellules changent d'etat (naissance ou
		 * mort)
		 */

		appliquerRegles();

		/*
		 * on actualise le nombre de voisins des cellules en fonction des changements d'etats
		 */

		actualiserCellules();

		/*
		 * on elimine les cellules mortes n'ayant plus aucun voisins afin de soulager l'algorithme, on ne le fait qu'une
		 * fois toutes les 16 generations pour ne pas surcharger l'algorithme
		 */

		if ((generation & 1023) == 0) {
			eliminerCellulesInutiles();
		}

		generation++;
	}

	@Override
	public void actualiserPresentation() {

		if (presentationConway != null) {

			presentationConway.setGeneration(generation);
			presentationConway.setTaille(nombreCellulesVivantes);
		}
	}

	@Override
	public long getTaille() {
		return nombreCellulesVivantes;
	}

	@Override
	public void creerCelluleVivante(int x, int y) {

		Cellule cellule = getCellule(x, y);

		naissance(cellule);
	}

	@Override
	public void inverserCellule(int x, int y) {

		if (editionManuellePossible) {

			Cellule cellule = getCellule(x, y);

			if (cellule.vivante) {
				mort(cellule);
			} else {
				naissance(cellule);
			}
		}
	}

	/**
	 * 
	 * @param cle
	 *            la cle de la cellule a recuperer
	 * @return la cellule correspond a la cle, elle est creee si elle n'existe pas encore
	 */
	private Cellule getCellule(int x, int y) {

		Map<Integer, Cellule> colonne = cellules.get(x);

		if (colonne == null) {

			colonne = new HashMap<>();
			cellules.put(x, colonne);
		}

		Cellule cellule = colonne.get(y);

		if (cellule == null) {

			cellule = new Cellule(x, y);
			colonne.put(y, cellule);
		}

		return cellule;
	}

	@Override
	public void effacer() {

		cellules.clear();
		echantillon = new boolean[largeurEchantillon][hauteurEchantillon];
		nombreCellulesVivantes = 0;
	}

	@Override
	public boolean[][] getEchantillon(int x, int y, int largeur, int hauteur) {

		if (echantillon == null || x != xEchantillon || y != yEchantillon || largeur != largeurEchantillon
				|| hauteur != hauteurEchantillon) {

			synchronized (this) {

				echantillon = new boolean[largeur][hauteur];

				xEchantillon = x;
				yEchantillon = y;
				largeurEchantillon = largeur;
				hauteurEchantillon = hauteur;

				Map<Integer, Cellule> colonne;
				Cellule cellule;

				for (int xCellule = x; xCellule < x + largeur; xCellule++) {

					colonne = cellules.get(xCellule);

					if (colonne != null) {

						for (int yCellule = y; yCellule < y + hauteur; yCellule++) {

							cellule = colonne.get(yCellule);
							echantillon[xCellule - x][yCellule - y] = cellule != null && cellule.vivante;
						}
					}
				}
			}
		}

		return echantillon;
	}

	@Override
	public int getGeneration() {
		return generation;
	}

	@Override
	public void setGeneration(int generation) {
		this.generation = generation;
	}

	@Override
	public PresentationPopulation getPresentation() {
		return presentation;
	}

	@Override
	public void setPresentationConway(PresentationConway presentationConway) {
		this.presentationConway = presentationConway;
	}

	@Override
	public void setEditionManuellePossible(boolean editionManuellePossible) {
		this.editionManuellePossible = editionManuellePossible;
	}
}