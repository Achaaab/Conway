package utilitaire;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jonathan Gu�henneux
 */
public class Chronometre {

	public static final Chronometre INSTANCE = new Chronometre();

	public static final int MILLISECONDES_PAR_SECONDE = 1000;
	public static final int MICROSECONDES_PAR_SECONDE = MILLISECONDES_PAR_SECONDE * 1000;
	public static final int NANOSECONDES_PAR_SECONDE = MICROSECONDES_PAR_SECONDE * 1000;

	private Map<String, Long> temps;

	/**
	 * 
	 */
	public Chronometre() {
		temps = new HashMap<String, Long>();
	}

	/**
	 * @param cle
	 */
	public void start(String cle) {

		long t = System.nanoTime();
		temps.put(cle, t);
	}

	/**
	 * 
	 * @param cle
	 * @return
	 */
	public float tick(String cle) {

		long t1 = temps.get(cle);
		long t2 = System.nanoTime();
		float t = Float.valueOf(t2 - t1);
		float secondes = t / NANOSECONDES_PAR_SECONDE;
		return secondes;
	}

	/**
	 * 
	 * @param cle
	 * @return
	 */
	public float stop(String cle) {

		float secondes = tick(cle);
		temps.remove(cle);
		return secondes;
	}
}