package es.upm.miw.SolitarioCelta;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.upm.miw.SolitarioCelta.model.Result;
import es.upm.miw.SolitarioCelta.model.ResultDataBase;
import es.upm.miw.SolitarioCelta.model.SCeltaViewModel;
import es.upm.miw.SolitarioCelta.model.SCeltaViewModelFactory;

public class MainActivity extends AppCompatActivity {

    protected final String LOG_TAG = "MiW";
    protected final Integer ID = 2021;
    protected SCeltaViewModel miJuegoVM;

    protected final Integer NUM_FICHAS_INIT = 32;

    ResultDataBase resultDataBase;
    SharedPreferences sharedPref;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        resultDataBase = Room.databaseBuilder(
                getApplicationContext(),
                ResultDataBase.class,
                ResultDataBase.DATA_BASE
        )
                .allowMainThreadQueries()
                .build();

        miJuegoVM = new ViewModelProvider(
                this,
                new SCeltaViewModelFactory(getApplication(), ID)
        )
                .get(SCeltaViewModel.class);
        mostrarTablero();
    }

    /**
     * Se ejecuta al pulsar una ficha
     * Las coordenadas (i, j) se obtienen a partir del nombre del recurso, ya que el botón
     * tiene un identificador en formato pXY, donde X es la fila e Y la columna
     *
     * @param v Vista de la ficha pulsada
     */
    public void fichaPulsada(@NotNull View v) {
        String resourceName = getResources().getResourceEntryName(v.getId()); //id pxy sobre el que se ha pulsado
        int i = resourceName.charAt(1) - '0';   // fila
        int j = resourceName.charAt(2) - '0';   // columna
        int fichas = miJuegoVM.numeroFichas();

        Log.i(LOG_TAG, "fichaPulsada(" + i + ", " + j + ") - " + resourceName);
        miJuegoVM.jugar(i, j);
        Log.i(LOG_TAG, "#fichas=" + miJuegoVM.numeroFichas());

        mostrarTablero();
        if (miJuegoVM.juegoTerminado()) {
            String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

            resultDataBase.resultDAO().insert(
                    new Result(
                            fichas,
                            getGamePlayerName(),
                            fecha
                            )
            );

            Toast.makeText(this, getString(R.string.txtGuardarBaseDatos), Toast.LENGTH_SHORT).show();
            new AlertDialogFragment().show(getSupportFragmentManager(), "ALERT_DIALOG");
        }
    }

    /**
     * Visualiza el tablero
     */
    public void mostrarTablero() {
        RadioButton button;
        String strRId;
        TextView tvBarraFichas = findViewById(R.id.tvBarraFichas);
        String prefijoIdentificador = getPackageName() + ":id/p"; // formato: package:type/entry
        int idBoton;


        tvBarraFichas.setText("Fichas restantes " +String.valueOf(miJuegoVM.numeroFichas()));

        for (int i = 0; i < SCeltaViewModel.TAMANIO; i++)
            for (int j = 0; j < SCeltaViewModel.TAMANIO; j++) {
                strRId = prefijoIdentificador + i + j;
                idBoton = getResources().getIdentifier(strRId, null, null);
                if (idBoton != 0) { // existe el recurso identificador del botón
                    button = findViewById(idBoton);
                    button.setChecked(miJuegoVM.obtenerFicha(i, j) == SCeltaViewModel.FICHA);
                }
            }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.opcAjustes:
                startActivity(new Intent(this, SCeltaPrefs.class));
                return true;

            case R.id.opcAcercaDe:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.aboutTitle)
                        .setMessage(R.string.aboutMessage)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                return true;

            case R.id.opcReiniciarPartida:
                new AlertRestartFragment().show(getSupportFragmentManager(), "ALERT DIALOG");
                return true;

            case R.id.opcGuardarPartida:
                saveGame();
                return true;

            case R.id.opcRecuperarPartida:
                recoverGame();
                return true;

            case R.id.opcMejoresResultados:
                startActivity(new Intent(this, ListResultsActivity.class));
                return true;


            default:
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.txtSinImplementar),
                        Snackbar.LENGTH_LONG
                )
                        .show();
        }
        return true;
    }

    private String getFileName() {
        return getString(R.string.default_NombreFich);
    }

    private String getGamePlayerName() {

        String jugador = sharedPref.getString(
                getString(R.string.key_NombreJugador),
                getString(R.string.default_NombreJugador));

        Log.i(LOG_TAG, "Nombre del jugador: " + jugador);

        return jugador;
    }

    public void saveGame() {

        String tablero = this.miJuegoVM.serializaTablero();

        try {
            FileOutputStream fos = openFileOutput(getFileName(), Context.MODE_PRIVATE);
            fos.write(tablero.getBytes());
            fos.close();
            Toast.makeText(this, getString(R.string.txtPartidaGuardada), Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.ficheroNotFound), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.txtPartidaNoGuardada), Toast.LENGTH_SHORT).show();

        }
    }

    public void recoverGame() {

        if (miJuegoVM.numeroFichas() == NUM_FICHAS_INIT) {
            loadGame();
        } else
            new AlertLoadGameFragment().show(getSupportFragmentManager(), "ALERT DIALOG");
    }

    public void loadGame() {

        try {
            BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput(getFileName())));
            String partida = fin.readLine();
            miJuegoVM.deserializaTablero(partida);
            mostrarTablero();
            Toast.makeText(this, getString(R.string.txtPartidaCargada), Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.ficheroNotFound), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.txtPartidaNoCargada), Toast.LENGTH_SHORT).show();
        }

    }
}
