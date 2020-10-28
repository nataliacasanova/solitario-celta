package es.upm.miw.SolitarioCelta.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ResultDAO {

    @Query("SELECT * FROM " + Result.TABLA+ " ORDER BY " + Result.FECHA + " ASC ")
    List<Result> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Result result);
}
