package es.upm.miw.SolitarioCelta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class AlertLoadGameFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final MainActivity main = (MainActivity) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(main);

        builder
                .setTitle(R.string.txtCargarPartidaTitulo)
                .setMessage(R.string.txtCargarPartidaPregunta)
                .setPositiveButton(
                        getString(R.string.txtCargarPartidaAfirmativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                main.loadGame();
                            }
                        }
                )
                .setNegativeButton(
                        getString(R.string.txtCargarPartidaNegativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }
                );
        return builder.create();

    }
}
