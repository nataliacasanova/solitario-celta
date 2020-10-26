package es.upm.miw.SolitarioCelta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class AlertRestartFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final MainActivity main = (MainActivity) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(main);

        builder
                .setTitle(R.string.txtReiniciarTitulo)
                .setMessage(R.string.txtReiniciarPregunta)
                .setPositiveButton(
                        getString(R.string.txtReiniciarAfirmativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                main.miJuegoVM.reiniciar();
                                main.mostrarTablero();
                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.txtReiniciarNegativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                main.finish();
                            }
                        }
                );
        return builder.create();
    }


}
