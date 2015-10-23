package utilitaire.mathematiques;

import java.math.BigInteger;
import java.util.Random;

import utilitaire.RegexUtilitaire;
import utilitaire.StringUtilitaire;

/**
 * @author guehenneux
 * 
 */
public class BigFraction extends Number implements Comparable<BigFraction> {

	/**
	 * UID genere le 04/06/2010
	 */
	private static final long serialVersionUID = -7515653377599378811L;

	public static final String SEPARATEUR = "/";

	public BigInteger numerateur;
	public BigInteger denominateur;

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
	public static final BigFraction aleatoire(BigInteger numerateurMaximum,
			BigInteger denominateurMaximum, float probabiliteFractionNegative) {

		int nombreBitsNumerateur = numerateurMaximum.bitLength();
		BigInteger numerateur = new BigInteger(nombreBitsNumerateur, RANDOM);
		numerateur = numerateur.mod(numerateurMaximum);

		int nombreBitsDenominateur = denominateurMaximum.bitLength();
		BigInteger denominateur = new BigInteger(nombreBitsDenominateur, RANDOM);
		denominateur = denominateur.mod(
				denominateurMaximum.subtract(BigInteger.ONE)).add(
				BigInteger.ONE);

		if (RANDOM.nextFloat() < probabiliteFractionNegative) {
			numerateur = numerateur.negate();
		}

		return new BigFraction(numerateur, denominateur);

	}

	/**
	 * simplifie la fraction
	 * 
	 * @param chaine
	 *            une chaine representant une fraction sous la forme
	 *            123456789/987654321 ou bien simplement 123456789
	 */
	public BigFraction(String chaine) {

		String[] elements = chaine.split(REGEX_SEPARATEUR);

		numerateur = new BigInteger(elements[0]);

		if (elements.length == 1) {
			denominateur = BigInteger.ONE;
		} else {
			denominateur = new BigInteger(elements[1]);
		}

		standardiser();

	}

	/**
	 * 
	 * @param numerateur
	 */
	public BigFraction(BigInteger numerateur) {

		this.numerateur = numerateur;
		denominateur = BigInteger.ONE;

	}

	/**
	 * simplifie la fraction
	 * 
	 * @param numerateur
	 * @param denominateur
	 */
	public BigFraction(BigInteger numerateur, BigInteger denominateur) {

		this.numerateur = numerateur;
		this.denominateur = denominateur;

		standardiser();

	}

	/**
	 * cree une fraction nulle numerateur a 0 et denominateur arbitraire
	 */
	public BigFraction() {
		this(BigInteger.ZERO, BigInteger.ONE);
	}

	/**
	 * 
	 * @return true si la fraction est nulle, false sinon
	 */
	public final boolean estNulle() {
		return numerateur.equals(BigInteger.ZERO);
	}

	/**
	 * @param terme
	 * @return this + terme
	 */
	public final BigFraction ajouter(BigInteger terme) {

		return new BigFraction(numerateur.add(terme.multiply(denominateur)),
				denominateur);

	}

	/**
	 * this = this + terme
	 * 
	 * @param terme
	 * @return this
	 */
	public final BigFraction ajouterThis(BigInteger terme) {

		numerateur = numerateur.add(terme.multiply(denominateur));

		return this;

	}

	/**
	 * @param fraction
	 * @return this + fraction
	 */
	public final BigFraction ajouter(BigFraction fraction) {

		return new BigFraction(numerateur.multiply(fraction.denominateur).add(
				fraction.numerateur.multiply(denominateur)), denominateur
				.multiply(fraction.denominateur));

	}

	/**
	 * this = this + fraction
	 * 
	 * @param fraction
	 * @return this
	 */
	public final BigFraction ajouterThis(BigFraction fraction) {

		numerateur = numerateur.multiply(fraction.denominateur).add(
				fraction.numerateur.multiply(denominateur));

		denominateur = denominateur.multiply(fraction.denominateur);

		standardiser();

		return this;

	}

	/**
	 * @param terme
	 * @return this * terme
	 */
	public final BigFraction multiplier(BigInteger terme) {
		return new BigFraction(numerateur.multiply(terme), denominateur);
	}

	/**
	 * this = this * terme
	 * 
	 * @param fraction
	 * @return this
	 */
	public final BigFraction multiplierThis(BigInteger terme) {

		numerateur = numerateur.multiply(terme);

		standardiser();

		return this;

	}

	/**
	 * @param fraction
	 * @return this * fraction
	 */
	public final BigFraction multiplier(BigFraction fraction) {

		return new BigFraction(numerateur.multiply(fraction.numerateur),
				denominateur.multiply(fraction.denominateur));

	}

	/**
	 * this = this * fraction
	 * 
	 * @param fraction
	 * @return this
	 */
	public final BigFraction multiplierThis(BigFraction fraction) {

		numerateur = numerateur.multiply(fraction.numerateur);
		denominateur = denominateur.multiply(fraction.denominateur);

		standardiser();

		return this;

	}

	/**
	 * 
	 * @param terme
	 * @return this - terme
	 */
	public final BigFraction soustraire(BigInteger terme) {

		return new BigFraction(numerateur
				.subtract(terme.multiply(denominateur)), denominateur);

	}

	/**
	 * this = this - terme
	 * 
	 * @param terme
	 * @return
	 */
	public final BigFraction soustraireThis(BigInteger terme) {

		numerateur = numerateur.subtract(terme.multiply(denominateur));

		standardiser();

		return this;

	}

	/**
	 * @param fraction
	 * @return this - fraction
	 */
	public final BigFraction soustraire(BigFraction fraction) {

		return new BigFraction(numerateur.multiply(fraction.denominateur)
				.subtract(fraction.numerateur.multiply(denominateur)),
				denominateur.multiply(fraction.denominateur));

	}

	/**
	 * this = this - fraction
	 * 
	 * @param fraction
	 * @return this
	 */
	public final BigFraction soustraireThis(BigFraction fraction) {

		numerateur = numerateur.multiply(fraction.denominateur).subtract(
				fraction.numerateur.multiply(denominateur));

		denominateur = denominateur.multiply(fraction.denominateur);

		standardiser();

		return this;

	}

	/**
	 * @param terme
	 * @return this / terme
	 */
	public final BigFraction diviser(BigInteger terme) {
		return new BigFraction(numerateur, denominateur.multiply(terme));
	}

	/**
	 * this = this / terme
	 * 
	 * @param terme
	 * @return this
	 */
	public final BigFraction diviserThis(BigInteger terme) {

		denominateur = denominateur.multiply(terme);

		standardiser();

		return this;

	}

	/**
	 * @param fraction
	 * @return this / fraction
	 */
	public final BigFraction diviser(BigFraction fraction) {

		return new BigFraction(numerateur.multiply(fraction.denominateur),
				denominateur.multiply(fraction.numerateur));

	}

	/**
	 * this = this / fraction
	 * 
	 * @param fraction
	 * @return this
	 */
	public final BigFraction diviserThis(BigFraction fraction) {

		numerateur = numerateur.multiply(fraction.denominateur);
		denominateur = denominateur.multiply(fraction.numerateur);

		standardiser();

		return this;

	}

	/**
	 * @return 1 / this
	 */
	public final BigFraction inverser() {
		return new BigFraction(denominateur, numerateur);
	}

	/**
	 * this = 1 / this
	 * 
	 * @return this
	 */
	public final BigFraction inverserThis() {

		BigInteger numerateur = this.numerateur;
		this.numerateur = denominateur;
		denominateur = numerateur;

		homogeneiser();

		return this;

	}

	/**
	 * @return -this
	 */
	public final BigFraction opposer() {
		return new BigFraction(numerateur.negate(), denominateur);
	}

	/**
	 * this = -this
	 * 
	 * @return this
	 */
	public final BigFraction opposerThis() {

		numerateur = numerateur.negate();

		return this;

	}

	/**
	 * utilise un algorithme d'exponentiation rapide
	 * 
	 * @param exposant
	 * @return this^exposant
	 */
	public final BigFraction puissance(int exposant) {

		BigFraction puissance = new BigFraction(BigInteger.ONE, BigInteger.ONE);
		BigFraction fraction = clone();

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

		if (denominateur.compareTo(BigInteger.ZERO) < 0) {

			numerateur = numerateur.negate();
			denominateur = denominateur.negate();

		}

	}

	/**
	 * reduit la fraction grace a l'algorithme d'Euclide
	 */
	private final void reduire() {

		BigInteger pgcd = numerateur.gcd(denominateur);

		if (pgcd.equals(BigInteger.ZERO)) {
			pgcd = BigInteger.ONE;
		}

		numerateur = numerateur.divide(pgcd);
		denominateur = denominateur.divide(pgcd);

	}

	@Override
	public final boolean equals(Object objet) {

		boolean egalite;

		if (objet == null) {

			egalite = false;

		} else if (objet == this) {

			egalite = true;

		} else if (objet instanceof BigFraction) {

			BigFraction fraction = (BigFraction) objet;

			egalite = estNulle() ? fraction.estNulle() : numerateur
					.equals(fraction.numerateur)
					&& denominateur.equals(fraction.denominateur);

		} else {

			egalite = false;

		}

		return egalite;

	}

	@Override
	public final BigFraction clone() {
		return new BigFraction(numerateur, denominateur);
	}

	@Override
	public final int hashCode() {

		int hashCodeNumerateur = numerateur.hashCode();
		int hashCodeDenominateur = denominateur.hashCode();

		return hashCodeNumerateur ^ hashCodeDenominateur;

	}

	@Override
	public final double doubleValue() {
		return numerateur.doubleValue() / denominateur.doubleValue();
	}

	@Override
	public final float floatValue() {
		return numerateur.floatValue() / denominateur.floatValue();
	}

	@Override
	public final int intValue() {
		return numerateur.divide(denominateur).intValue();
	}

	@Override
	public final long longValue() {
		return numerateur.divide(denominateur).longValue();
	}

	@Override
	public final int compareTo(BigFraction fraction) {

		BigInteger produit0 = numerateur.multiply(fraction.denominateur);
		BigInteger produit1 = denominateur.multiply(fraction.numerateur);

		return produit0.compareTo(produit1);

	}

	@Override
	public final String toString() {

		String chaine;

		if (estNulle()) {
			chaine = "0";
		} else if (denominateur.equals(BigInteger.ONE)) {
			chaine = numerateur.toString();
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