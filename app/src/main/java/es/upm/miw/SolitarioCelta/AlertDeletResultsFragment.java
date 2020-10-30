package es.upm.miw.SolitarioCelta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

public class AlertDeletResultsFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final ListResultsActivity listResultsActivity = (ListResultsActivity) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(listResultsActivity);

        builder
                .setTitle(R.string.txtBorrarResultados)
                .setMessage(R.string.txtBorrarPregunta)
                .setPositiveButton(
                        getString(R.string.txtBorrarResultadosAfirmativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listResultsActivity.resultDataBase.resultDAO().deleteAll();

                                listResultsActivity.resultAdapter = new ResultAdapter(
                                        listResultsActivity,
                                        R.layout.result_item,
                                        listResultsActivity.resultDataBase.resultDAO().getAll()
                                );

                                listResultsActivity.lvListResults.setAdapter(listResultsActivity.resultAdapter);

                            }


                        }
                )
                .setNegativeButton(
                        getString(R.string.txtBorrarResultadosNegativo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }
                );
        return builder.create();

    }

}

