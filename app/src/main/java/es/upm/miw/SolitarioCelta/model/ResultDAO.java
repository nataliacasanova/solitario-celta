package es.upm.miw.SolitarioCelta.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ResultDAO {

    @Query("SELECT * FROM " + Result.TABLA+ " ORDER BY " + Result.FICHAS + " ASC ")
    List<Result> getAll();

    @Query("SELECT * FROM " + Result.TABLA+ " WHERE  nombre = :name")
    List<Result> getAllByNamePlayer(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Result result);

    @Query(" DELETE FROM " + Result.TABLA )
    void deleteAll();
}
