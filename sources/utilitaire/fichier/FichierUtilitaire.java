package utilitaire.fichier;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Jonathan Guéhenneux
 */
public class FichierUtilitaire {

	/**
	 * @param cheminFichier
	 *            nom d'un fichier
	 * @return l'extension du fichier en minuscules ou null si le fichier n'a pas d'extension. Si le seul '.' dans le
	 *         nom du fichier se trouve en premiere position, on considere que le fichier n'a pas d'extension, si le
	 *         dernier '.' dans le nom du fichier se trouve en derniere position, on considere que le fichier n'a pas
	 *         d'extension.
	 */
	public static final String getExtension(File fichier) {

		String extension;

		String nomFichier = fichier.getName();

		int indexPoint = nomFichier.lastIndexOf('.');

		if (indexPoint > 0 && indexPoint < nomFichier.length() - 1) {
			extension = nomFichier.substring(indexPoint + 1).toLowerCase();
		} else {
			extension = null;
		}

		return extension;
	}

	/**
	 * @param cheminFichier
	 * @param position
	 * @throws IOException
	 */
	public static final void ecrireOctet(String cheminFichier, long position, int octet) throws IOException {

		RandomAccessFile accesFichier = new RandomAccessFile(cheminFichier, "rw");

		accesFichier.seek(position);
		accesFichier.writeByte(octet);

		accesFichier.close();
	}

	/**
	 * @param accesFichier
	 * @param tampon
	 * @param position
	 * @param nombreOctetsMaximum
	 * @return
	 * @throws IOException
	 */
	public static final int lire(RandomAccessFile accesFichier, byte[] tampon, long position, int nombreOctetsMaximum)
			throws IOException {

		accesFichier.seek(position);
		return accesFichier.read(tampon, 0, nombreOctetsMaximum);
	}
}