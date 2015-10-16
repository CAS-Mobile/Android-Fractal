package ch.hsr.mge.fractal.domain;

import android.graphics.Bitmap;

public interface IFractalGenerator {

	/**
	 * Berechnet das Fraktal des gewaehlten Ausschnitts und liefert eine Bitmap mit dem Resultat
	 * @param x1 X-Koordinate Punkt links unten
	 * @param y1 Y-Koordinate Punkt links unten
	 * @param x2 X-Koordinate Punkt rechts oben
	 * @param y2 Y-Koordinate Punkt rechts oben
	 * @param width Bildgbreite
	 * @param height Bildhoehe
	 * @param itermax maximale Rechentiefe
	 */
	public Bitmap calculate(double x1, double y1, double x2, double y2, int width, int height, int itermax );
	
}
