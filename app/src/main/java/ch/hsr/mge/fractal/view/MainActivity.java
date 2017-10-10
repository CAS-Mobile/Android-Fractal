package ch.hsr.mge.fractal.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

import ch.hsr.mge.fractal.R;
import ch.hsr.mge.fractal.domain.BitmapHelper;
import ch.hsr.mge.fractal.services.FractalService;

/**
 * Die App stellt ein Fraktal dar.
 * Arbeitet im UI-Thread und blockiert das UI bei der Berechnung
 *
 * @author Peter Buehler
 */
public class MainActivity extends AppCompatActivity {

    public final static String DEBUG_TAG = "FractalApp";

    private Button button;
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(DEBUG_TAG, "onReceive() " + context);
            handleCalculationFinished();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Listener für Button registrieren
        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleButtonPressed();
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();

        // ist Datei vorhanden?
        File dir = getCacheDir();
        File file = new File(dir, FractalService.FILE_NAME);
        if (file.exists()) {
            Log.d(DEBUG_TAG, "file exists");
            handleCalculationFinished();
        } else {
            Log.d(DEBUG_TAG, "file does not exist");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
        IntentFilter filter = new IntentFilter(FractalService.ACTION);
        lbm.registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.unregisterReceiver(receiver);
    }

    private void handleButtonPressed() {

        // Koordinaten und max Iteration auslesen
        double x1 = Double.parseDouble(((EditText) findViewById(R.id.editText1)).getText().toString());
        double y1 = Double.parseDouble(((EditText) findViewById(R.id.editText2)).getText().toString());
        double x2 = Double.parseDouble(((EditText) findViewById(R.id.editText3)).getText().toString());
        double y2 = Double.parseDouble(((EditText) findViewById(R.id.editText4)).getText().toString());
        int itermax = Integer.parseInt(((EditText) findViewById(R.id.editText5)).getText().toString());

        // Grösse des Bildes bestimmen
        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        int width = imageView.getWidth();
        int height = imageView.getHeight();

        Intent intent = new Intent(this, FractalService.class);
        intent.putExtra("x1", x1);
        intent.putExtra("y1", y1);
        intent.putExtra("x2", x2);
        intent.putExtra("y2", y2);
        intent.putExtra("width", width);
        intent.putExtra("height", height);
        intent.putExtra("itermax", itermax);

        // Schoener waere Progressbar oder Spinner
        button.setText("Running...");

        startService(intent);
    }

    private void handleCalculationFinished() {

        // Datei laden und view aktualisieren
        File dir = getCacheDir();
        File file = new File(dir, FractalService.FILE_NAME);
        Bitmap bitmap = BitmapHelper.loadBitmap(file);
        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        imageView.setImageBitmap(bitmap);

        button.setText("Calculate");

        // Datei loeschen
        file.delete();
    }
}
