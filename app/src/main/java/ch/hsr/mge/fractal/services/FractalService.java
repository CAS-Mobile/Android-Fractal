package ch.hsr.mge.fractal.services;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;

import ch.hsr.mge.fractal.domain.BitmapHelper;
import ch.hsr.mge.fractal.domain.FractalGenerator;
import ch.hsr.mge.fractal.view.MainActivity;

public class FractalService extends IntentService {
	
	public final static String FILE_NAME = "fractal.png";
	public final static String ACTION = "hsr.example.fractalapp2.ACTION";

	// leerer Konstruktor muss vorhanden sein
	public FractalService() {
		super("FractalService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Log.d(MainActivity.DEBUG_TAG, "onHandleIntent");
		
		// Daten aus Intent auslesen
		double x1  = intent.getDoubleExtra("x1", -2.25);
		double y1  = intent.getDoubleExtra("y1", -1.5);
		double x2  = intent.getDoubleExtra("x2", 0.75);
		double y2  = intent.getDoubleExtra("y2", 1.5);
		int width = intent.getIntExtra("width", 100);
		int height = intent.getIntExtra("height", 100);
		int itermax = intent.getIntExtra("itermax", 50);
		
		// Fraktal berechnen
		FractalGenerator fg = new FractalGenerator();
		Bitmap bitmap = fg.calculate(x1, y1, x2, y2, width, height, itermax);
		
		// Bitmap speichern
		File dir = getCacheDir();
		File file = new File(dir, FILE_NAME);
		BitmapHelper.saveBitmap(bitmap, file);
		
		// Meldung absetzen
		LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
		Intent intent2 = new Intent(ACTION);
		lbm.sendBroadcast(intent2);
		
		Log.d(MainActivity.DEBUG_TAG, "fractal calculated and saved");
	}

}
