package utilitaire.test;

import java.text.MessageFormat;

import utilitaire.Chronometre;
import utilitaire.introspection.ExceptionIntrospection;
import utilitaire.introspection.IntrospectionUtilitaire;

/**
 * 
 * @author guehenneux
 * 
 */
public abstract class CasTest {

	private static final MessageFormat PROPOSITION_VRAIE = new MessageFormat(
			"La proposition est vraie.");

	private static final MessageFormat PROPOSITION_FAUSSE = new MessageFormat(
			"La proposition est fausse.");

	private static final MessageFormat ENTIERS_COURTS_INEGAUX = new MessageFormat(
			"Les entiers courts {0} et {1} sont inégaux.");

	private static final MessageFormat ENTIERS_LONGS_INEGAUX = new MessageFormat(
			"Les entiers longs {0} et {1} sont inégaux.");

	private static final MessageFormat FLOTTANTS_COURTS_INEGAUX = new MessageFormat(
			"Les flottants courts {0} et {1} sont inégaux.");

	private static final MessageFormat OBJETS_INEGAUX = new MessageFormat(
			"Les objets {0} et {1} sont inégaux.");

	private static final String CLE_CHRONOMETRAGE_METHODE = "appel_methode";

	/**
	 * 
	 * @param proposition
	 */
	public static final void affirmerVraie(boolean proposition) {

		if (!proposition) {
			leverException(PROPOSITION_FAUSSE);
		}

	}

	/**
	 * 
	 * @param proposition
	 */
	public static final void affirmerFausse(boolean proposition) {

		if (proposition) {
			leverException(PROPOSITION_VRAIE);
		}

	}

	/**
	 * 
	 * @param entier1
	 * @param entier2
	 */
	public static final void affirmerEgaux(int entier1, int entier2) {

		if (entier1 != entier2) {
			leverException(ENTIERS_COURTS_INEGAUX, entier1, entier2);
		}

	}

	/**
	 * 
	 * @param entier1
	 * @param entier2
	 */
	public static final void affirmerEgaux(long entier1, long entier2) {

		if (entier1 != entier2) {
			leverException(ENTIERS_LONGS_INEGAUX, entier1, entier2);
		}

	}

	/**
	 * 
	 * @param flottant1
	 * @param flottant2
	 */
	public static final void affirmerEgaux(float flottant1, float flottant2) {

		if (flottant1 != flottant2) {
			leverException(FLOTTANTS_COURTS_INEGAUX, flottant1, flottant2);
		}

	}

	/**
	 * 
	 * @param objet1
	 * @param objet2
	 */
	public static final void affirmerEgaux(Object objet1, Object objet2) {

		if (!objet1.equals(objet2)) {
			leverException(OBJETS_INEGAUX, objet1, objet2);
		}

	}

	/**
	 * 
	 * @param formatMessage
	 * @param parametresMessage
	 */
	public static final void leverException(MessageFormat formatMessage,
			Object... parametresMessage) {

		String message = formatMessage.format(parametresMessage);

		Exception exception = new AffirmationNonVerifiee(message);
		exception.printStackTrace();

	}

	/**
	 * 
	 * @param objet
	 * @param nomMethode
	 * @param typesParametres
	 * @param parametres
	 * @return
	 * @throws ExceptionIntrospection
	 */
	public static final Object chronometrer(Object objet, String nomMethode,
			Class<?>[] typesParametres, Object[] parametres)
			throws ExceptionIntrospection {

		Chronometre chronometre = new Chronometre();
		chronometre.start(CLE_CHRONOMETRAGE_METHODE);

		Object retour = IntrospectionUtilitaire.appelerMethode(objet,
				nomMethode, typesParametres, parametres);

		float tempsExecutionMethode = chronometre
				.stop(CLE_CHRONOMETRAGE_METHODE);

		System.out.println(nomMethode + " : " + tempsExecutionMethode + "s");

		return retour;

	}

}