package ch.hsr.mge.fractal.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import ch.hsr.mge.fractal.domain.FractalGenerator;
import ch.hsr.mge.fractal.domain.IFractalGenerator;
import ch.hsr.mge.fractalapp.R;

/**
 * Die App stellt ein Fraktal dar.
 * Arbeitet im UI-Thread und blockiert das UI bei der Berechnung
 *
 * @author Peter Buehler
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Listener fuer Button registrieren
        ((Button) findViewById(R.id.button1)).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleButtonPressed();
                    }
                });

    }

    private void handleButtonPressed() {

        // Koordinaten und max Iteration auslesen
        double x1 = Double.parseDouble(((EditText) findViewById(R.id.editText1)).getText().toString());
        double y1 = Double.parseDouble(((EditText) findViewById(R.id.editText2)).getText().toString());
        double x2 = Double.parseDouble(((EditText) findViewById(R.id.editText3)).getText().toString());
        double y2 = Double.parseDouble(((EditText) findViewById(R.id.editText4)).getText().toString());
        int itermax = Integer.parseInt(((EditText) findViewById(R.id.editText5)).getText().toString());

        // Groesse des Bildes bestimmen
        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        int width = imageView.getWidth();
        int height = imageView.getHeight();

        // Fraktal berechnen (blockiert UI)
        IFractalGenerator fg = new FractalGenerator();
        Bitmap bitmap = fg.calculate(x1, y1, x2, y2, width, height, itermax);

        // Bitmap setzen
        imageView.setImageBitmap(bitmap);

    }


}
