package utilitaire;

import java.awt.Color;
import java.util.Random;

/**
 * @author Jonathan Guéhenneux
 */
public class CouleurUtilitaire {

	private static final Random RANDOM = new Random();

	/**
	 * @param rgb
	 * @return
	 */
	public static int getNiveauGris(int rgb) {

		int r = rgb >> 16 & 0xff;
		int g = rgb >> 8 & 0xff;
		int b = rgb & 0xff;

		int niveauGris = Math.round(r * 0.299f + g * 0.587f + b * 0.114f);

		return niveauGris;
	}

	/**
	 * @param minimum
	 * @param colorMinimum
	 * @param maximum
	 * @param colorMaximum
	 * @return
	 */
	public static Color getCouleurIntermediaire(double minimum, Color colorMinimum, double maximum, Color colorMaximum,
			double value) {

		Color colorValue;

		if (minimum > maximum) {

			colorValue = Color.BLACK;

		} else if (value < minimum) {

			colorValue = colorMinimum;

		} else if (value > maximum) {

			colorValue = colorMaximum;

		} else {

			double range = maximum - minimum;
			double relativeValue = value - minimum;
			double colorMaximumPart = relativeValue / range;
			double colorMinimumPart = 1.0 - colorMaximumPart;

			double r = colorMinimumPart * colorMinimum.getRed() + colorMaximumPart * colorMaximum.getRed();
			double g = colorMinimumPart * colorMinimum.getGreen() + colorMaximumPart * colorMaximum.getGreen();
			double b = colorMinimumPart * colorMinimum.getBlue() + colorMaximumPart * colorMaximum.getBlue();

			int red = (int) Math.round(r);
			int green = (int) Math.round(g);
			int blue = (int) Math.round(b);

			colorValue = new Color(red, green, blue);
		}

		return colorValue;
	}

	/**
	 * @param saturation
	 * @param valeur
	 * @return
	 */
	public static Color getTeinteAleatoire(float saturation, float valeur) {

		float teinte = RANDOM.nextFloat();

		return Color.getHSBColor(teinte, saturation, valeur);
	}

	/**
	 * @return
	 */
	public static Color getCouleurAleatoire() {

		int r = RANDOM.nextInt(256);
		int g = RANDOM.nextInt(256);
		int b = RANDOM.nextInt(256);

		return new Color(r, g, b);
	}
}