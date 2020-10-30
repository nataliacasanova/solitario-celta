package es.upm.miw.SolitarioCelta;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import es.upm.miw.SolitarioCelta.model.ResultDataBase;

public class ListResultsActivity extends AppCompatActivity {

    ListView lvListResults;
    ResultAdapter resultAdapter;
    ResultDataBase resultDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_result);

        lvListResults = findViewById(R.id.lvListResults);

        resultDataBase = Room.databaseBuilder(
                getApplicationContext(),
                ResultDataBase.class,
                ResultDataBase.DATA_BASE
        )
                .allowMainThreadQueries()
                .build();

        resultAdapter = new ResultAdapter(
                this,
                R.layout.result_item,
                resultDataBase.resultDAO().getAll()
        );

        lvListResults.setAdapter(resultAdapter);
        //incluir dialogo para borrar resultados


    }
}
