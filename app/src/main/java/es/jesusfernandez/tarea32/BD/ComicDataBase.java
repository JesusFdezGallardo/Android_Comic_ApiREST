package es.jesusfernandez.tarea32.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.jesusfernandez.tarea32.clasesPOJO.Comic;

public class ComicDataBase {
private ComicDatabaseSQLiteOpenHelper helper;

    public ComicDataBase(Context context){
        helper = new ComicDatabaseSQLiteOpenHelper(context, "VistaComics", null, 1);
    }
    public void createComic (int idComic, String titutlo, String fecha, String urlImagen){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idComic", idComic);
        values.put("titulo", titutlo);
        values.put("fecha", fecha);
        values.put("URLimagen", urlImagen);
         database.insert("VistaComics ", null, values);
        database.close();
    }

    public Comic mostrarComic(int idCOmic) throws Exception {
        SQLiteDatabase database = helper.getReadableDatabase();
        String[] args = {String.valueOf(idCOmic)};
        Cursor cursor = database.rawQuery("SELECT * FROM VistaComics WHERE idComic = ?", args);
        Comic comicGuardado  = null;
        if (cursor.moveToFirst()){
            comicGuardado = new Comic(cursor.getInt(0), cursor.getString(1), cursor.getString(2)
                    , cursor.getString(3));
        }
        database.close();
        return comicGuardado;
    }

    public boolean comprobarComic(int idComic) throws Exception {
        List<Comic> listaComics = getListaComics();
        for (int i = 0; i < listaComics.size(); i++){
            if (listaComics.get(i).getIdComic() == idComic){
                return true;
            }
        }
    return false;
    }

    public ArrayList<Comic> getListaComics() throws Exception {
        ArrayList<Comic> listaComics = new ArrayList<>();
        SQLiteDatabase database = helper.getReadableDatabase();
        //Cursor cursor = database.rawQuery("SELECT idComic,titulo,fecha, URLimagen  FROM VistaComics", null);
        Cursor cursor = database.rawQuery("SELECT *  FROM VistaComics", null);
        Comic comic = null;
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                comic = new Comic(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3));
                listaComics.add(comic);
                Log.d("comicvisto", comic.getTitle());
            }
        }
        database.close();
        cursor.close();
        return listaComics;
        }

        public void deleteComic (int id){
        SQLiteDatabase database = helper.getWritableDatabase();
        String[] args = {String.valueOf(id)};
        database.delete("VistaComics", "idComic = ?", args);
        database.close();
        }

    public String comprobarURL(String URL) throws Exception {
        List<Comic> listaComics = getListaComics();
        String respuesta = "";
        for (int i = 0; i < listaComics.size(); i++){
            if (listaComics.get(i).getImg() == URL){
                respuesta = listaComics.get(i).getImg();
                return respuesta;
            }
        }
        return respuesta;
    }
}
