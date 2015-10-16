package ch.hsr.mge.fractal.domain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Hilfsfunktionen, um Bitmap als Datei zu speichern
 * resp. von Datei zu lesen
 *
 * @author Peter Buehler
 */
public class BitmapHelper {

	/**
	 * Speichert Bitmap in Datei.
	 * @param bitmap
	 * @param file
	 */
	public static void saveBitmap(Bitmap bitmap, File file) {
		try {
			FileOutputStream fOut = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
			fOut.flush();
			fOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Laedt Bitmap aus Datei.
	 * @param file
	 */
	public static Bitmap loadBitmap(File file) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), options);
		return bitmap;
		
	}
	
}
