package es.jesusfernandez.tarea32.Apiutilidades;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import es.jesusfernandez.tarea32.BD.ComicDataBase;
import es.jesusfernandez.tarea32.R;
import es.jesusfernandez.tarea32.clasesPOJO.Comic;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolders>
        implements View.OnClickListener {

    private List<Comic> listaComics;
    private Context context;
    private View.OnClickListener listener;
    private ComicDataBase dataBase;


    public ComicAdapter(List<Comic> listaComics, Context context) {
        this.listaComics = listaComics;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.lista_comic_pagina2, parent, false);
        //escuchador OnClick
        vista.setOnClickListener(this);

        vista.setOnClickListener(this);
        return new ViewHolders(vista);

    }
        //Leer informacion
    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
        Comic comic = listaComics.get(position);
        String respuesta = "error";
        try {
            respuesta = dataBase.comprobarURL(listaComics.get(position).getImg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.titulo.setText( "Titulo: " + comic.getTitle());
        holder.fecha.setText("Fecha publicación: " + comic.getDay() + "/"+ comic.getMonth() +"/" + comic.getYear());
        Picasso.with(context).load(respuesta)
                .resize(2000, 2000)
                .centerInside()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.foto, new Callback() {
                    @Override public void onSuccess() {

                } @Override public void onError() {
                        Picasso.with(context) .load(comic.getImg())
                                .resize(2000, 2000)
                                .centerInside()
                                .placeholder(R.drawable.cargandoimagen)
                                .error(R.drawable.descarga)
                                .into(holder.foto); } });
                        Log.d("comic", String.valueOf(listaComics.get(position).getIdComic()));
    }

    @Override
    public int getItemCount() {
        return listaComics.size();
    }

    public void setOnClickListener (View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }
    }

    //Rellenamos Campos
    public static class ViewHolders extends RecyclerView.ViewHolder{

        private TextView titulo;
        private ImageView foto;
        private TextView fecha;

        public ViewHolders(@NonNull View itemView) {
            super(itemView);
                titulo= itemView.findViewById(R.id.txtTitulo);
                foto =itemView.findViewById(R.id.fotoComic);
                fecha = itemView.findViewById(R.id.txtFecha);
        }
    }

}
