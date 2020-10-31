package es.upm.miw.SolitarioCelta;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.Collections;
import java.util.List;

import es.upm.miw.SolitarioCelta.model.Result;
import es.upm.miw.SolitarioCelta.model.ResultDataBase;

public class ListResultsActivity extends AppCompatActivity {


    private ListView lvListResults;
    private ResultAdapter resultAdapter;
    private ResultDataBase resultDataBase;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_result);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        lvListResults = findViewById(R.id.lvListResults);

        resultDataBase = Room.databaseBuilder(
                getApplicationContext(),
                ResultDataBase.class,
                ResultDataBase.DATA_BASE
        )
                .allowMainThreadQueries()
                .build();

        createAdapter(resultDataBase.resultDAO().getAll());
    }

    public void createAdapter(List<Result> resultList){

        resultAdapter = new ResultAdapter(
                this,
                R.layout.result_item,
                resultList
        );

        lvListResults.setAdapter(resultAdapter);
    }

    public void delete(View view) {
        new AlertDeleteResultsFragment().show(getSupportFragmentManager(), "ALERT DIALOG");

    }

    public void delete() {
        resultDataBase.resultDAO().deleteAll();

        createAdapter(Collections.<Result>emptyList());
    }

    public void showPlayerResults(View view){

        String jugador = sharedPref.getString(
                getString(R.string.key_NombreJugador),
                getString(R.string.default_NombreJugador));

        createAdapter(resultDataBase.resultDAO().getAllByNamePlayer(jugador));

    }

    public void showAllResults(View view){
        createAdapter(resultDataBase.resultDAO().getAll());
    }
}
