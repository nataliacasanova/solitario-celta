package es.upm.miw.SolitarioCelta.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities= {Result.class}, version = 1)
public abstract  class ResultDataBase  extends RoomDatabase {
  public static final String DATA_BASE = Result.TABLA + ".db";

  public abstract ResultDAO resultDAO();

}
