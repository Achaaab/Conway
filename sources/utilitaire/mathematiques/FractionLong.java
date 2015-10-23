package utilitaire.mathematiques;

import java.util.Random;

import utilitaire.RegexUtilitaire;
import utilitaire.StringUtilitaire;

/**
 * @author guehenneux
 * 
 */
public class FractionLong extends Number implements Comparable<FractionLong> {

	/**
	 * UID genere le 03/06/2010
	 */
	private static final long serialVersionUID = 224373819279468374L;

	public static final String SEPARATEUR = "/";

	public long numerateur;
	public long denominateur;

	private static final String REGEX_SEPARATEUR = RegexUtilitaire
			.echapperMetacaracteres(SEPARATEUR);

	private static final Random RANDOM = new Random();

	/**
	 * 
	 * @param numerateurMaximum
	 *            valeur maximum (exclue) pour le numerateur (doit etre
	 *            superieur ou egal a 1)
	 * @param denominateurMaximum
	 *            valeur maximum (exclue) pour le denominateur (doit etre
	 *            superieur ou egal a 2)
	 * @param probabiliteFractionNegative
	 * @return
	 */
	public static final FractionLong aleatoire(long numerateurMaximum,
			long denominateurMaximum, float probabiliteFractionNegative) {

		long numerateur = Math.abs(RANDOM.nextLong() % numerateurMaximum);

		long denominateur = 1 + Math.abs(RANDOM.nextLong()
				% (denominateurMaximum - 1));

		if (RANDOM.nextFloat() < probabiliteFractionNegative) {
			numerateur = -numerateur;
		}

		return new FractionLong(numerateur, denominateur);

	}

	/**
	 * simplifie la fraction
	 * 
	 * @param chaine
	 *            une chaine representant une fraction sous la forme
	 *            123456789/987654321 ou bien simplement 123456789
	 */
	public FractionLong(String chaine) {

		String[] elements = chaine.split(REGEX_SEPARATEUR);

		numerateur = Long.parseLong(elements[0]);

		if (elements.length == 1) {
			denominateur = 1;
		} else {
			denominateur = Long.parseLong(elements[1]);
		}

		standardiser();

	}

	/**
	 * 
	 * @param numerateur
	 */
	public FractionLong(long numerateur) {

		this.numerateur = numerateur;
		denominateur = 1;

	}

	/**
	 * simplifie la fraction
	 * 
	 * @param numerateur
	 * @param denominateur
	 */
	public FractionLong(long numerateur, long denominateur) {

		this.numerateur = numerateur;
		this.denominateur = denominateur;

		standardiser();

	}

	/**
	 * cree une fraction nulle numerateur a 0 et denominateur arbitraire
	 */
	public FractionLong() {
		this(0, 1);
	}

	/**
	 * 
	 * @return true si la fraction est nulle, false sinon
	 */
	public final boolean estNulle() {
		return numerateur == 0;
	}

	/**
	 * @param terme
	 * @return this + terme
	 */
	public final FractionLong ajouter(long terme) {
		return new FractionLong(numerateur + terme * denominateur, denominateur);
	}

	/**
	 * this = this + terme
	 * 
	 * @param terme
	 * @return this
	 */
	public final FractionLong ajouterThis(long terme) {

		numerateur += terme * denominateur;

		return this;

	}

	/**
	 * @param fraction
	 * @return this + fraction
	 */
	public final FractionLong ajouter(FractionLong fraction) {

		return new FractionLong(numerateur * fraction.denominateur
				+ fraction.numerateur * denominateur, denominateur
				* fraction.denominateur);

	}

	/**
	 * this = this + fraction
	 * 
	 * @param fraction
	 * @return this
	 */
	public final FractionLong ajouterThis(FractionLong fraction) {

		numerateur = numerateur * fraction.denominateur + fraction.numerateur
				* denominateur;

		denominateur *= fraction.denominateur;

		standardiser();

		return this;

	}

	/**
	 * @param terme
	 * @return this * terme
	 */
	public final FractionLong multiplier(long terme) {
		return new FractionLong(numerateur * terme, denominateur);
	}

	/**
	 * this = this * terme
	 * 
	 * @param fraction
	 * @return this
	 */
	public final FractionLong multiplierThis(long terme) {

		numerateur *= terme;

		standardiser();

		return this;

	}

	/**
	 * @param fraction
	 * @return this * fraction
	 */
	public final FractionLong multiplier(FractionLong fraction) {

		return new FractionLong(numerateur * fraction.numerateur, denominateur
				* fraction.denominateur);

	}

	/**
	 * this = this * fraction
	 * 
	 * @param fraction
	 * @return this
	 */
	public final FractionLong multiplierThis(FractionLong fraction) {

		numerateur *= fraction.numerateur;
		denominateur *= fraction.denominateur;

		standardiser();

		return this;

	}

	/**
	 * 
	 * @param terme
	 * @return this - terme
	 */
	public final FractionLong soustraire(long terme) {
		return new FractionLong(numerateur - terme * denominateur, denominateur);
	}

	/**
	 * this = this - terme
	 * 
	 * @param terme
	 * @return
	 */
	public final FractionLong soustraireThis(long terme) {

		numerateur -= terme * denominateur;

		standardiser();

		return this;

	}

	/**
	 * @param fraction
	 * @return this - fraction
	 */
	public final FractionLong soustraire(FractionLong fraction) {

		return new FractionLong(numerateur * fraction.denominateur
				- fraction.numerateur * denominateur, denominateur
				* fraction.denominateur);

	}

	/**
	 * this = this - fraction
	 * 
	 * @param fraction
	 * @return this
	 */
	public final FractionLong soustraireThis(FractionLong fraction) {

		numerateur = numerateur * fraction.denominateur - fraction.numerateur
				* denominateur;

		denominateur *= fraction.denominateur;

		standardiser();

		return this;

	}

	/**
	 * @param terme
	 * @return this / terme
	 */
	public final FractionLong diviser(long terme) {
		return new FractionLong(numerateur, denominateur * terme);
	}

	/**
	 * this = this / terme
	 * 
	 * @param terme
	 * @return this
	 */
	public final FractionLong diviserThis(long terme) {

		denominateur *= terme;

		standardiser();

		return this;

	}

	/**
	 * @param fraction
	 * @return this / fraction
	 */
	public final FractionLong diviser(FractionLong fraction) {

		return new FractionLong(numerateur * fraction.denominateur,
				denominateur * fraction.numerateur);

	}

	/**
	 * this = this / fraction
	 * 
	 * @param fraction
	 * @return this
	 */
	public final FractionLong diviserThis(FractionLong fraction) {

		numerateur *= fraction.denominateur;
		denominateur *= fraction.numerateur;

		standardiser();

		return this;

	}

	/**
	 * @return 1 / this
	 */
	public final FractionLong inverser() {
		return new FractionLong(denominateur, numerateur);
	}

	/**
	 * this = 1 / this
	 * 
	 * @return this
	 */
	public final FractionLong inverserThis() {

		long numerateur = this.numerateur;
		this.numerateur = denominateur;
		denominateur = numerateur;

		homogeneiser();

		return this;

	}

	/**
	 * @return -this
	 */
	public final FractionLong opposer() {
		return new FractionLong(-numerateur, denominateur);
	}

	/**
	 * this = -this
	 * 
	 * @return this
	 */
	public final FractionLong opposerThis() {

		numerateur = -numerateur;

		return this;

	}

	/**
	 * utilise un algorithme d'exponentiation rapide
	 * 
	 * @param exposant
	 * @return this^exposant
	 */
	public final FractionLong puissance(int exposant) {

		FractionLong puissance = new FractionLong(1, 1);
		FractionLong fraction = clone();

		boolean inversion = exposant < 0;

		if (inversion) {
			exposant = -exposant;
		}

		while (exposant > 0) {

			if (exposant % 2 == 1) {
				puissance.multiplierThis(fraction);
			}

			fraction.multiplierThis(fraction);
			exposant >>= 1;

		}

		if (inversion) {
			puissance.inverserThis();
		}

		return puissance;

	}

	/**
	 * reduit la fraction, puis s'assure que l'eventuel signe reste au
	 * numerateur
	 */
	private final void standardiser() {

		reduire();
		homogeneiser();

	}

	/**
	 * suite a la reduction ou l'inversion d'une fraction, le signe negatif peut
	 * passer au denominateur, on le place ainsi au numerateur
	 */
	private final void homogeneiser() {

		if (denominateur < 0) {

			numerateur = -numerateur;
			denominateur = -denominateur;

		}

	}

	/**
	 * reduit la fraction grace a l'algorithme d'Euclide
	 */
	private final void reduire() {

		long pgcd = MathematiquesUtilitaire.pgcd(numerateur, denominateur);

		numerateur /= pgcd;
		denominateur /= pgcd;

	}

	@Override
	public final boolean equals(Object objet) {

		boolean egalite;

		if (objet == null) {

			egalite = false;

		} else if (objet == this) {

			egalite = true;

		} else if (objet instanceof FractionLong) {

			FractionLong fraction = (FractionLong) objet;

			egalite = (numerateur == 0) ? fraction.numerateur == 0
					: numerateur == fraction.numerateur
							&& denominateur == fraction.denominateur;

		} else {

			egalite = false;

		}

		return egalite;

	}

	@Override
	public final FractionLong clone() {
		return new FractionLong(numerateur, denominateur);
	}

	@Override
	public final int hashCode() {

		int numerateur0 = (int) numerateur;
		int numerateur1 = (int) (numerateur >>> 32);

		int denominateur0 = (int) denominateur;
		int denominateur1 = (int) (denominateur >>> 32);

		return numerateur0 ^ numerateur1 ^ denominateur0 ^ denominateur1;

	}

	@Override
	public final double doubleValue() {
		return (double) numerateur / denominateur;
	}

	@Override
	public final float floatValue() {
		return (float) numerateur / denominateur;
	}

	@Override
	public final int intValue() {
		return (int) (numerateur / denominateur);
	}

	@Override
	public final long longValue() {
		return numerateur / denominateur;
	}

	@Override
	public final int compareTo(FractionLong fraction) {

		int comparaison;

		long produit0 = numerateur * fraction.denominateur;
		long produit1 = denominateur * fraction.numerateur;

		if (produit0 > produit1) {
			comparaison = 1;
		} else if (produit0 < produit1) {
			comparaison = -1;
		} else {
			comparaison = 0;
		}

		return comparaison;

	}

	@Override
	public final String toString() {

		String chaine;

		if (numerateur == 0) {
			chaine = "0";
		} else if (denominateur == 1) {
			chaine = Long.toString(numerateur);
		} else {
			chaine = numerateur + SEPARATEUR + denominateur;
		}

		return chaine;
	}

	/**
	 * 
	 * @param format
	 * @return
	 */
	public final String toString(int longueur) {

		String fractionString = toString();

		return StringUtilitaire.formater(fractionString, longueur, true, true,
				' ');

	}

}