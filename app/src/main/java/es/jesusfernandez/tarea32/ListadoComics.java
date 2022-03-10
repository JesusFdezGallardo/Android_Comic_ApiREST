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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import es.jesusfernandez.tarea32.Apiutilidades.ListaComicAdapter;
import es.jesusfernandez.tarea32.BD.ComicDataBase;
import es.jesusfernandez.tarea32.clasesPOJO.Comic;

public class ListadoComics extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Comic> listaComics;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pagina3);
        recyclerView = findViewById(R.id.listaComicsVisualizados);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ComicDataBase dataBase = new ComicDataBase(ListadoComics.this);
        listaComics = new ArrayList<>();

        try {
            listaComics = dataBase.getListaComics();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListaComicAdapter adapter = new ListaComicAdapter(listaComics);
            Log.d("comicvisto", String.valueOf(listaComics.size()));

        recyclerView.setAdapter(adapter);
            //implementación del método OnClick generado en Adaptador
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(ListadoComics.this, MostrarComic.class);
                sharedPreferences = getSharedPreferences("numComic", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                int idComic = listaComics.get(recyclerView.getChildAdapterPosition(v)).getIdComic();
                editor.putInt("numComic", idComic);
                editor.apply();
                startActivity(registro);
            }
        });

    }

}