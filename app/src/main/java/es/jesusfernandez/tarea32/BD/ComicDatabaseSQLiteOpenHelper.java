package es.jesusfernandez.tarea32.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ComicDatabaseSQLiteOpenHelper extends SQLiteOpenHelper {
    public ComicDatabaseSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS comics");
        db.execSQL("DROP TABLE IF EXISTS listaComics");
        db.execSQL("DROP TABLE IF EXISTS comicsVistos");

        String sql = "CREATE TABLE VistaComics(" +
                "idComic INTEGER primary key," +
                "titulo TEXT,"+
                "fecha TEXT," +
                "URLimagen TEXT)";
        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
