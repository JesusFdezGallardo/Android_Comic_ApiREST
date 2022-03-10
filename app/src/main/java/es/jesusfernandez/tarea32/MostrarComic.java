package es.jesusfernandez.tarea32;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import es.jesusfernandez.tarea32.Apiutilidades.ComicAdapter;
import es.jesusfernandez.tarea32.Apiutilidades.XkcdService;
import es.jesusfernandez.tarea32.BD.ComicDataBase;
import es.jesusfernandez.tarea32.clasesPOJO.Comic;
import es.jesusfernandez.tarea32.hilos.MyApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MostrarComic extends AppCompatActivity {

    Executor executor;
    XkcdService xkcdService;
    Call<Comic> request;
    Button btnAnterior, btnSiguiente;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Integer numComic;
    ComicDataBase db;
    TextView titulo;
    ImageView foto;
    TextView fecha;
    Comic comic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_comic_pagina2);
        executor = ((MyApplication) getApplication()).diskIOExecutor;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://xkcd.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        db = new ComicDataBase(getApplicationContext());
        xkcdService = retrofit.create(XkcdService.class);
        titulo = findViewById(R.id.txtTitulo);
        foto = findViewById(R.id.fotoComic);
        fecha = findViewById(R.id.txtFecha);
        btnAnterior = (Button) findViewById(R.id.btnAnterior);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        //Traigo datos de nº Comic. Si está vacío muestro el último creado
        sharedPreferences = getSharedPreferences("numComic", Context.MODE_PRIVATE);
        numComic = sharedPreferences.getInt("numComic", 0);
        Log.d("numeroComic", String.valueOf(numComic));
        titulo.setText("Cargando imagen");
        fecha.setText("");

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean existeComic = db.comprobarComic(numComic);
                    Log.d("numeroComic", String.valueOf(existeComic));
                    if (existeComic) {
                        comic = db.mostrarComic(numComic);
                        Log.d("hilo1", "Hilo BD");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mostrarComic(comic);
                            }
                        });
                    } else {
                        request = xkcdService.getComic(numComic);
                        request.enqueue(new Callback<Comic>() {
                            @Override
                            public void onResponse(Call<Comic> call, Response<Comic> response) {
                                Log.d("hilo1", "hilo API");
                                comic = response.body();
                                db.createComic(comic.getIdComic(),
                                        comic.getTitle(),
                                        comic.getDay() + "/" + comic.getMonth() + "/" + comic.getYear(),
                                        comic.getImg());
                                mostrarComic(comic);
                                Log.d("hilo1", "hola2");
                                Log.d("numeroComic", String.valueOf(comic.getIdComic()));
                            }
                            @Override
                            public void onFailure(Call<Comic> call, Throwable t) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(MostrarComic.this, e.getMessage(), Toast.LENGTH_SHORT);
                }
            }
        });
        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MostrarComic.this, MostrarComic.class);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (numComic == 1){
                    request = xkcdService.getUltimoComic();
                    request.enqueue(new Callback<Comic>() {
                        @Override
                        public void onResponse(Call<Comic> call, Response<Comic> response) {
                            Comic ultimoComic = response.body();
                            int comicUltimo = ultimoComic.getIdComic();
                            Log.d("numeroComic", String.valueOf(comicUltimo));
                            Log.d("numeroComic", String.valueOf(numComic));
                            numComic = comicUltimo;
                            editor.putInt("numComic", (numComic));
                            editor.apply();
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Comic> call, Throwable t) {

                        }
                    });
                } else {
                    editor.putInt("numComic", numComic - 1);
                    editor.apply();
                    startActivity(intent);
                }
            }
        });
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MostrarComic.this, MostrarComic.class);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //Compruebo num maximo para evitar superar el último numero de comic
                request = xkcdService.getUltimoComic();
                request.enqueue(new Callback<Comic>() {
                    @Override
                    public void onResponse(Call<Comic> call, Response<Comic> response) {
                        Comic nuevoComic = response.body();
                        int comicUltimo = nuevoComic.getIdComic();
                        Log.d("numeroComic", String.valueOf(comicUltimo));
                        Log.d("numeroComic", String.valueOf(numComic));
                        if (comicUltimo <= numComic){
                            numComic = 1;
                            editor.putInt("numComic", (numComic));
                            editor.apply();
                            startActivity(intent);
                        } else {
                            editor.putInt("numComic", (numComic + 1));
                            editor.apply();
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Comic> call, Throwable t) {

                    }
                });

            }
        });
    }
    public void mostrarComic (Comic comic){
        fecha.setText("Fecha de publicacion--> " + comic.getYear());
        titulo.setText("Titulo--> " + comic.getTitle());
        Picasso.with(getApplicationContext()).load(comic.getImg())
                .placeholder(R.drawable.cargandoimagen)
                .error(R.drawable.descarga)
                .resize(2000, 2000)
                .centerInside()
                .into(foto);
    }

}