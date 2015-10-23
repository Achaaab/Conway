package utilitaire;

/**
 * @author Jonathan Guéhenneux
 */
public abstract class ProcessusInterruptible extends Thread {

	protected boolean demandeInterruption;
	protected long dureeBoucle;
	private Chronometre chronometre;

	/**
	 * 
	 */
	public ProcessusInterruptible() {
		this(0);
	}

	/**
	 * @param dureeBoucle
	 */
	public ProcessusInterruptible(long dureeBoucle) {

		demandeInterruption = false;
		this.dureeBoucle = dureeBoucle;

		chronometre = new Chronometre();
	}

	/**
	 * demande l'interruption du processus
	 */
	public void interrompre() {
		demandeInterruption = true;
	}

	@Override
	public void run() {

		float dureeBoucleInterneSecondes;
		int dureeBoucleInterne;

		while (!demandeInterruption) {

			chronometre.start("boucle");
			boucle();
			dureeBoucleInterneSecondes = chronometre.stop("boucle");

			dureeBoucleInterne = Math.round(dureeBoucleInterneSecondes * Chronometre.MILLISECONDES_PAR_SECONDE);

			if (dureeBoucleInterne < dureeBoucle) {

				try {
					sleep(dureeBoucle - dureeBoucleInterne);
				} catch (InterruptedException erreur) {
				}
			}
		}
	}

	/**
	 * effectue une iteration, l'iteration est l'operation atomique effectuee par le processus et ne peut etre
	 * interrompue
	 */
	public abstract void boucle();

	/**
	 * @return the dureeBoucle
	 */
	public long getDureeBoucle() {
		return dureeBoucle;
	}

	/**
	 * @param dureeBoucle
	 *            the dureeBoucle to set
	 */
	public void setDureeBoucle(long dureeBoucle) {
		this.dureeBoucle = dureeBoucle;
	}
}