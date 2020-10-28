package es.upm.miw.SolitarioCelta.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Result.TABLA)
public class Result {

    static public final String TABLA = "resultados";
    static public final String FECHA = "fecha";
    static public final String FICHAS = "fichas";

    @PrimaryKey(autoGenerate = true)
    protected Integer uid;

    @ColumnInfo(name = FICHAS)
    protected Integer fichas;

    @ColumnInfo(name = "nombre")
    protected String jugador;

    @ColumnInfo(name = FECHA)
    protected String fecha;

    public Result(Integer fichas, String jugador, String fecha) {
        this.fichas = fichas;
        this.jugador = jugador;
        this.fecha = fecha;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getFichas() {
        return fichas;
    }

    public void setFichas(Integer fichas) {
        this.fichas = fichas;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
