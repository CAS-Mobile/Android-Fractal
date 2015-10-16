package ch.hsr.mge.fractal.domain;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Die Klasse berechnet ein Fraktal und liefert die Grafik als Bitmap.
 *
 * @author Peter Buehler
 */
public class FractalGenerator implements IFractalGenerator {

	public Bitmap calculate(double x1, double y1, double x2, double y2, int width, int height, int itermax ) {
		
		// Daten berechnen
		int[] pixel = new int[width*height];
		double dx = (x2-x1) / width;
		double dy = (y1-y2) / height;
		
		for (int i=0; i<height; i++) {
			double imag = y2 + i*dy;
			for (int j=0; j<width; j++) {
				double real = x1 + j*dx;
				double rez=0; double imz=0; double rez_1=0; double imz_1=0;
				int iter = 0;
				while( iter < itermax) {
					rez = rez_1*rez_1 - imz_1*imz_1 + real;
					imz = 2*rez_1*imz_1 + imag;
					if (((rez*rez) + (imz*imz)) > 4) {
						break;
					}
					rez_1 = rez; imz_1 = imz;
					++iter;
				}
				pixel[i*width+j] = iterationToColor(iter, itermax);
			}
		}
		
		// Bitmap erzeugen
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixel, 0, width, 0, 0, width, height);
		return bitmap;
	}
	
	/**
	 * Weist einem Wert fuer die Anzahl Iterationen einen Farbwert zu.
	 * @param iter
	 * @param itermax
	 */
	private int iterationToColor(int iter, int itermax) {
		
		if (iter == itermax) {  // das "Loch" des Fraktals soll scwarz sein
			return Color.BLACK;
		}
		
		int a = iter%5;
		switch (a) {
			case 1: return Color.RED;
			case 2: return Color.BLUE;
			case 3: return Color.GREEN;
			case 4: return Color.MAGENTA;	
			default: return Color.YELLOW;	
		}
	}
	
}
