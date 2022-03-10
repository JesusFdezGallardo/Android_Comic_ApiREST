package es.jesusfernandez.tarea32.Apiutilidades;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.jesusfernandez.tarea32.R;
import es.jesusfernandez.tarea32.clasesPOJO.Comic;

public class ListaComicAdapter extends RecyclerView.Adapter<ListaComicAdapter.ViewHolder>
implements View.OnClickListener {

    private ArrayList<Comic> listaComics;
    private View.OnClickListener listener;

    public ListaComicAdapter(ArrayList<Comic> listaComics) {
        this.listaComics = listaComics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_comic_vistos_pagina3,
                parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaComicAdapter.ViewHolder holder, int position) {
        Comic comic = listaComics.get(position);
        holder.idComic.setText("ID Comic--> " + String.valueOf(comic.getIdComic()));
        holder.titulo.setText("Titulo--> " + comic.getTitle());
        holder.fecha.setText("Fecha --> " + comic.getYear());
    }

    @Override
    public int getItemCount() {
        return listaComics.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idComic;
        TextView titulo;
        TextView fecha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idComic = itemView.findViewById(R.id.idComicVisto);
            titulo = itemView.findViewById(R.id.tituloComicVisto);
            fecha = itemView.findViewById(R.id.fechaComicVisto);
        }
    }
    public  void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
