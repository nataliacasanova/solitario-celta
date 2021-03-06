package es.upm.miw.SolitarioCelta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import es.upm.miw.SolitarioCelta.model.Result;

public class ResultAdapter extends ArrayAdapter {

    private Context context;
    private int idLayout;
    private List<Result> results;

    public ResultAdapter(@NonNull Context context, int idLayout, @NonNull List<Result> results) {
        super(context, idLayout, results);
        this.context = context;
        this.idLayout = idLayout;
        this.results = results;
        this.setNotifyOnChange(true);
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflador.inflate(this.idLayout, null);
        }

        Result result = results.get(position);

        if (result != null) {

            // Asignar contenido a los elementos de la vista
            TextView tvNamePlayer = convertView.findViewById(R.id.tvListResultNamePlayer);
            TextView tvDate = convertView.findViewById(R.id.tvListResultDate);
            TextView tvFichas = convertView.findViewById(R.id.tvListResultFichas);

            tvNamePlayer.setText(result.getJugador());
            tvDate.setText(result.getFecha());
            tvFichas.setText(Integer.toString(result.getFichas()));
        }

        return convertView;
    }


}
