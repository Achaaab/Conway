package utilitaire.introspection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * classe utilitaire pour l'introspection
 * 
 * @author GUEHENNEUX
 */
public class IntrospectionUtilitaire {

	/**
	 * signature d'une methode sans parametre
	 */
	public static final Class<?>[] SIGNATURE_VIDE = new Class[0];

	/**
	 * parametres d'une methode sans parametre
	 */
	public static final Object[] PARAMETRES_VIDES = new Object[0];

	/**
	 * format du message d'erreur lorsque l'appel a une methode a declenche une
	 * exception
	 */
	private static final String MESSAGE_EXCEPTION = "L''appel de la méthode {0} "
			+ "sur le type {1} a déclenché une exception.";

	/**
	 * format du message d'erreur lors d'une violation de securite sur
	 * l'obtention d'un getter
	 */
	private static final String MESSAGE_VIOLATION_SECURITE = "L''obtention de la méthode {0} "
			+ "sur un objet de type {1} est interdit, "
			+ "vérifier que la méthode en question est publique.";

	/**
	 * format du message d'erreur lors de l'appel illegal a une methode
	 */
	private static final String MESSAGE_APPEL_INTERDIT = "L''apel de la méthode {0} "
			+ "sur un objet de type {1} est interdit, "
			+ "vérifier que la méthode en question est publique.";

	/**
	 * format du message d'erreur lors de la tentative d'obtention d'une methode
	 * inexistante pour un type donne
	 */
	private static final String MESSAGE_METHODE_INEXISTANTE = "La méthode {0} "
			+ "n''existe pas pour le type {1}.";

	/**
	 * format du message d'erreur si on appelle une methode avec de mauvais
	 * arguments
	 */
	private static final String MESSAGE_MAUVAIS_ARGUMENTS = "Le type des arguments founis ({0}) "
			+ "ne correspond pas avec la signature de la méthode appelée ({1}) du type {2}.";

	/**
	 * format du message d'erreur si une erreur survient durant l'instanciation
	 * d'une classe
	 */
	private static final String MESSAGE_ERREUR_INSTANCIATION = "Une erreur est survenue "
			+ "durant l''instanciation de la classe {0} (constructeur appelé : {1}, paramètres : {2}).";

	/**
	 * prefixe des getters (pour l'instant on ne se soucie pas des getters de
	 * booleens)
	 */
	private static final String PREFIXE_GETTER = "get";

	/**
	 * prefixe des setters
	 */
	private static final String PREFIXE_SETTER = "set";

	/**
	 * table associant son wrapper a chaque type primitif
	 */
	private static Map<Class<?>, Class<?>> tableWrappers;

	static {

		tableWrappers = new HashMap<Class<?>, Class<?>>();

		tableWrappers.put(Double.class, Double.TYPE);
		tableWrappers.put(Long.class, Long.TYPE);
		tableWrappers.put(Integer.class, Integer.TYPE);
		tableWrappers.put(Short.class, Short.TYPE);
		tableWrappers.put(Float.class, Float.TYPE);
		tableWrappers.put(Byte.class, Byte.TYPE);
		tableWrappers.put(Boolean.class, Boolean.TYPE);
		tableWrappers.put(Character.class, Character.TYPE);

	}

	/**
	 * constructeur prive pour empecher l'instanciation de cette classe
	 * utilitaire
	 */
	private IntrospectionUtilitaire() {

	}

	/**
	 * @param classe
	 *            classe dont on souhaite creer une instance
	 * @param typesParametres
	 *            types des parametres du constructeur a appeler
	 * @param valeursParametres
	 *            valeurs des parametres du constructeur a appeler
	 * @return une nouvelle instance de la classe donnee creee via le
	 *         constructeur sans parametre
	 * @throws P2PIntrospectionException
	 *             exception durant la tentative d'instanciation
	 */
	public static Object creerInstance(Class<?> classe,
			Class<?>[] typesParametres, Object[] valeursParametres)
			throws ExceptionIntrospection {

		Object instance;

		String nomConstructeur = getNomClasseCourt(classe);
		String nomClasse = classe.getName();
		String detailConstructeur = getNomMethode(nomConstructeur,
				typesParametres);
		String detailParametres = getParametresEffectifs(valeursParametres);

		Object[] parametresMessage = { nomClasse, detailConstructeur,
				detailParametres };

		String message = MessageFormat.format(MESSAGE_ERREUR_INSTANCIATION,
				parametresMessage);

		try {

			Constructor<?> constructeur = classe
					.getConstructor(typesParametres);
			instance = constructeur.newInstance(valeursParametres);

		} catch (SecurityException cause) {

			ExceptionIntrospection exception = new ExceptionIntrospection(
					message, cause);
			throw exception;

		} catch (IllegalArgumentException cause) {

			ExceptionIntrospection exception = new ExceptionIntrospection(
					message, cause);
			throw exception;

		} catch (NoSuchMethodException cause) {

			ExceptionIntrospection exception = new ExceptionIntrospection(
					message, cause);
			throw exception;

		} catch (InstantiationException cause) {

			ExceptionIntrospection exception = new ExceptionIntrospection(
					message, cause);
			throw exception;

		} catch (IllegalAccessException cause) {

			ExceptionIntrospection exception = new ExceptionIntrospection(
					message, cause);
			throw exception;

		} catch (InvocationTargetException cause) {

			ExceptionIntrospection exception = new ExceptionIntrospection(
					message, cause);
			throw exception;

		}

		/*
		 * FIN DE METHODE
		 */

		return instance;

	}

	/**
	 * recupere la valeur d'une propriete dans un bean par introspection
	 * 
	 * @param bean
	 *            le bean dont on souhaite obtenir la valeur d'une propriete
	 * @param propriete
	 *            la propriete du bean dont on souhaite obtenir la valeur
	 *            (exemple : nom, prenom, personne.nom, personne.pere.age, ...)
	 * @return la valeur de la propriete donnee dans le bean donne
	 * @throws ExceptionIntrospection
	 *             exception lors de l'introspection
	 */
	public static Object getProprieteBean(Object bean, String propriete)
			throws ExceptionIntrospection {

		Object valeurPropriete;

		int indexPoint = propriete.indexOf('.');

		if (indexPoint != -1) {

			/*
			 * si la propriete est du type champA.champB.champC...
			 */

			String nomChamp;
			Object valeurChamp;

			nomChamp = propriete.substring(0, indexPoint);
			valeurChamp = getChampBean(bean, nomChamp);
			String restePropriete = propriete.substring(indexPoint + 1);
			valeurPropriete = getProprieteBean(valeurChamp, restePropriete);

		} else {

			/*
			 * si la propriete est du type champA
			 */

			valeurPropriete = getChampBean(bean, propriete);

		}

		return valeurPropriete;

	}

	/**
	 * assigne la valeur d'une propriete dans un bean par introspection
	 * 
	 * @param bean
	 *            le bean dans lequel on souhaite insérer la valeur d'une
	 *            propriete
	 * @param propriete
	 *            la propriete du bean que l'on souhairte valorisé (exemple :
	 *            nom, prenom, personne.nom, personne.pere.age, ...)
	 * @param valeur
	 *            valeur à insérer dans le champ du bean (pour les types
	 *            primitifs, utiliser le wrapper)
	 * @param primitif
	 *            booléen indiquant si l'objet passé en paramètre est de type
	 *            primitif
	 * @throws ExceptionIntrospection
	 *             exception lors de l'introspection
	 */
	public static void setProprieteBean(Object bean, String propriete,
			String valeur, boolean primitif) throws ExceptionIntrospection {

		int indexPoint = propriete.indexOf('.');

		if (indexPoint != -1) {

			/*
			 * si la propriete est du type champA.champB.champC...
			 */

			String nomChamp;
			Object valeurChamp;

			nomChamp = propriete.substring(0, indexPoint);
			valeurChamp = getChampBean(bean, nomChamp);
			String restePropriete = propriete.substring(indexPoint + 1);
			setProprieteBean(valeurChamp, restePropriete, valeur, primitif);

		} else {

			/*
			 * si la propriete est du type champA
			 */

			setChampBean(bean, propriete, valeur, primitif);

		}
	}

	/**
	 * recupere la valeur d'un champ dans un bean par introspection
	 * 
	 * @param bean
	 *            bean dont on souhaite obtenir la valeur d'un champ
	 * @param nomChamp
	 *            nom du champ du bean dont on souhaite obtenir la valeur
	 * @return la valeur du champ donne dans le bean donne
	 * @throws FacturecuSaExcp
	 *             exception lors de l'introspection
	 */
	private static Object getChampBean(Object bean, String nomChamp)
			throws ExceptionIntrospection {

		Object valeurChamp;

		String nomGetter = getNomGetter(nomChamp);

		/*
		 * pas de parametres pour les getters
		 */

		valeurChamp = appelerMethode(bean, nomGetter, SIGNATURE_VIDE,
				PARAMETRES_VIDES);

		return valeurChamp;

	}

	/**
	 * assigne la valeur à un champ dans un bean par introspection
	 * 
	 * @param bean
	 *            bean dans lequel on souhaite modifier la valeur d'un champ
	 * @param nomChamp
	 *            nom du champ du bean dont on souhaite modifier la valeur
	 * @param valeur
	 *            valeur à insérer dans le champ du bean (pour les types
	 *            primitifs, utiliser le wrapper)
	 * @param primitif
	 *            booléen indiquant si l'objet passé en paramètre est de type
	 *            primitif
	 * @throws FacturecuSaExcp
	 *             exception lors de l'introspection
	 */
	private static void setChampBean(Object bean, String nomChamp,
			Object valeur, boolean primitif) throws ExceptionIntrospection {

		String nomSetter = getNomSetter(nomChamp);

		/*
		 * si le parametre a passer est un primitif on doit utiliser son wrapper
		 * pour l'introspection
		 */

		Class<?> classe = valeur.getClass();

		if (primitif) {
			classe = tableWrappers.get(classe);
		}

		Class<?>[] typesParametresSetter = new Class[1];
		typesParametresSetter[0] = classe;

		Object[] parametresSetter = new Object[1];
		parametresSetter[0] = valeur;

		appelerMethode(bean, nomSetter, typesParametresSetter, parametresSetter);

	}

	/**
	 * @param nomChamp
	 *            nom du champ
	 * @return le nom du getter correspondant (selon la norme JavaBean)
	 */
	private static String getNomGetter(String nomChamp) {
		return PREFIXE_GETTER + premiereLettreMajuscule(nomChamp);
	}

	/**
	 * @param nomChamp
	 *            nom du champ
	 * @return le nom du setter correspondant (selon la norme JavaBean)
	 */
	private static String getNomSetter(String nomChamp) {
		return PREFIXE_SETTER + premiereLettreMajuscule(nomChamp);
	}

	/**
	 * @param chaine
	 *            une chaine de caractere contenant au moins un caractere
	 * @return une chaine de caractere egale, mais avec le premier caractere en
	 *         majuscule
	 */
	private static String premiereLettreMajuscule(String chaine) {

		String resultat;

		char premiereLettre = chaine.charAt(0);
		String resteChaine = chaine.substring(1);
		char premiereLettreMajuscule = Character.toUpperCase(premiereLettre);
		resultat = premiereLettreMajuscule + resteChaine;

		return resultat;

	}

	/**
	 * @param classe
	 * @param propriete
	 * @return
	 * @throws ExceptionIntrospection
	 */
	public static Method getGetter(Class<?> classe, String propriete)
			throws ExceptionIntrospection {

		String nomGetter = getNomGetter(propriete);
		return getMethode(classe, nomGetter, SIGNATURE_VIDE);

	}

	/**
	 * @param classe
	 * @param nomMethode
	 * @return
	 * @throws ExceptionIntrospection
	 */
	public static Method getMethode(Class<?> classe, String nomMethode,
			Class<?>[] typesParametres) throws ExceptionIntrospection {

		Method methode;

		String nomMethodeComplet;
		Object[] parametresMessage;
		String message;

		try {

			methode = classe.getMethod(nomMethode, typesParametres);

		} catch (SecurityException cause) {

			/*
			 * obtention methode interdite : methode privee en general
			 */

			nomMethodeComplet = getNomMethode(nomMethode, typesParametres);
			parametresMessage = new Object[] { nomMethodeComplet,
					classe.getName() };
			message = MessageFormat.format(MESSAGE_VIOLATION_SECURITE,
					parametresMessage);
			ExceptionIntrospection exception = new ExceptionIntrospection(
					message, cause);
			throw exception;

		} catch (NoSuchMethodException cause) {

			/*
			 * obtention methode impossible : methode inexistante
			 */

			nomMethodeComplet = getNomMethode(nomMethode, typesParametres);
			parametresMessage = new Object[] { nomMethodeComplet,
					classe.getName() };
			message = MessageFormat.format(MESSAGE_METHODE_INEXISTANTE,
					parametresMessage);
			ExceptionIntrospection exception = new ExceptionIntrospection(
					message, cause);
			throw exception;

		}

		return methode;

	}

	/**
	 * @param objet
	 *            objet sur lequel appeler la methode (null pour appel d'une
	 *            methode statique)
	 * @param nomMethode
	 *            nom de la methode
	 * @param typesParametres
	 *            tableau contenant le type des parametres, attention si le
	 *            parametre effectif est de type primitif il faut utiliser
	 *            l'attribut TYPE du wrapper correspondant
	 * @param parametres
	 *            tableau des parametres effectifs eventuellement encapsules
	 *            dans leur wrapper respectif (int : Integer, double : Double,
	 *            ...)
	 * @return le resultat de la methode (null pour une methode de type void)
	 * @throws FacturecuSaExcp
	 *             exception lors de l'introspection
	 */
	public static Object appelerMethode(Object objet, String nomMethode,
			Class<?>[] typesParametres, Object[] parametres)
			throws ExceptionIntrospection {

		Class<?> classeObjet = objet.getClass();
		Method methode;

		/*
		 * variables utilisees pour journaliser les erreurs d'introspection
		 */

		String message;
		String nomMethodeComplet;
		Object[] parametresMessage;

		/*
		 * on appelle la methode et on stocke le retour
		 */

		Object retour;

		methode = getMethode(classeObjet, nomMethode, typesParametres);

		try {

			retour = methode.invoke(objet, parametres);

		} catch (IllegalArgumentException cause) {

			/*
			 * les arguments passes pour l'appel du getter ne correspondent pas
			 * avec la signature du getter : ce cas ne devrait pas se produire
			 */

			String parametresEffectifs = getParametresEffectifs(parametres);
			nomMethodeComplet = getNomMethode(nomMethode, typesParametres);

			parametresMessage = new Object[] { parametresEffectifs,
					nomMethodeComplet, classeObjet.getName() };

			message = MessageFormat.format(MESSAGE_MAUVAIS_ARGUMENTS,
					parametresMessage);
			ExceptionIntrospection exception = new ExceptionIntrospection(
					message, cause);
			throw exception;

		} catch (IllegalAccessException cause) {

			/*
			 * appel methode interdit : methode privee en general
			 */

			nomMethodeComplet = getNomMethode(nomMethode, typesParametres);
			parametresMessage = new Object[] { nomMethodeComplet,
					classeObjet.getName() };
			message = MessageFormat.format(MESSAGE_APPEL_INTERDIT,
					parametresMessage);
			ExceptionIntrospection exception = new ExceptionIntrospection(
					message, cause);
			throw exception;

		} catch (InvocationTargetException cause) {

			/*
			 * exception levee par la methode appelee
			 */

			nomMethodeComplet = getNomMethode(nomMethode, typesParametres);
			parametresMessage = new Object[] { nomMethodeComplet,
					classeObjet.getName() };
			message = MessageFormat
					.format(MESSAGE_EXCEPTION, parametresMessage);
			ExceptionIntrospection exception = new ExceptionIntrospection(
					message, cause);
			throw exception;

		}

		return retour;

	}

	/**
	 * @param parametres
	 *            tableau d'objets
	 * @return une chaine de caracteres representant le type des parametres
	 */
	private static String getParametresEffectifs(Object[] parametres) {

		String parametresEffectifs;
		StringBuffer buffer = new StringBuffer();

		buffer.append("(");

		int nombreParametres = parametres.length;
		Object parametre;
		Class<?> typeParametre;
		String typeParametreCourt;

		for (int indexParametre = 0; indexParametre < nombreParametres; indexParametre++) {

			parametre = parametres[indexParametre];
			typeParametre = parametre.getClass();
			typeParametreCourt = getNomClasseCourt(typeParametre);

			if (indexParametre > 0) {
				buffer.append(", ");
			}

			buffer.append(typeParametreCourt);

		}

		buffer.append(")");

		parametresEffectifs = buffer.toString();
		return parametresEffectifs;

	}

	/**
	 * @param nomMethode
	 *            nom de la methode sans parentheses ni arguments
	 * @param typesArguments
	 *            types des arguments
	 * @return le nom de la methode complet, dans un souci de clarte, le type
	 *         des arguments n'inclus pas le package
	 */
	private static String getNomMethode(String nomMethode,
			Class<?>[] typesArguments) {

		String nomComplet;
		StringBuffer buffer = new StringBuffer();

		buffer.append(nomMethode);
		buffer.append("(");

		int nombreArguments = typesArguments.length;
		Class<?> typeArgument;
		String nomClasseSansPackage;

		/*
		 * on parcourt les types d'argument
		 */

		for (int indexArgument = 0; indexArgument < nombreArguments; indexArgument++) {

			typeArgument = typesArguments[indexArgument];
			nomClasseSansPackage = getNomClasseCourt(typeArgument);

			if (indexArgument > 0) {
				buffer.append(", ");
			}

			buffer.append(nomClasseSansPackage);

		}

		buffer.append(")");

		nomComplet = buffer.toString();
		return nomComplet;

	}

	/**
	 * permet de recuperer le nom court d'une classe sans le package la chaine
	 * obtenue n'identifie plus la classe de facon unique mais est plus lisible
	 * dans le cadre de la journalisation par exemple
	 * 
	 * @param classe
	 *            une classe quelconque
	 * @return le nom court de la classe (sans le package)
	 */
	private static String getNomClasseCourt(Class<?> classe) {

		String nomClasseSansPackage;

		String nomClasseAvecPackage = classe.getName();
		int indexNomClasseSansPackage = nomClasseAvecPackage.lastIndexOf('.') + 1;
		nomClasseSansPackage = nomClasseAvecPackage
				.substring(indexNomClasseSansPackage);

		return nomClasseSansPackage;

	}

}