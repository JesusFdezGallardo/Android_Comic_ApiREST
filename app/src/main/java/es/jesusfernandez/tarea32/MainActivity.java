package es.jesusfernandez.tarea32;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.jesusfernandez.tarea32.Apiutilidades.ComicAdapter;
import es.jesusfernandez.tarea32.clasesPOJO.Comic;
import es.jesusfernandez.tarea32.Apiutilidades.XkcdService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText numComic;
    SharedPreferences sharedPreferences;
    XkcdService xkcdService;
    Call<Comic> request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primera_pantalla);
        Button btnComic  = (Button) findViewById(R.id.btnMostrarComic);
        numComic = findViewById(R.id.numComic);
        btnComic.setEnabled(false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://xkcd.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        xkcdService = retrofit.create(XkcdService.class);
        numComic.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            boolean numComicRelleno = numComic.getText().toString().length() >= 1;
                                            if (!numComicRelleno){
                                                numComic.setError("El número de comic está vacío");
                                            }
                                            btnComic.setEnabled(numComicRelleno);
                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {

                                            }
                                        });
                btnComic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String valor = numComic.getText().toString();
                        int valorfinal = Integer.parseInt(valor);
                        Intent registro = new Intent(MainActivity.this, MostrarComic.class);
                        sharedPreferences = getSharedPreferences("numComic", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("numComic", valorfinal);
                        editor.apply();
                        Log.d("mensaje", numComic.getText().toString());
                           startActivity(registro);
                    }
                });
        Button botonUltimo = (Button) findViewById(R.id.btnMostarUltimo);
        botonUltimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(MainActivity.this, MostrarComic.class);
                sharedPreferences = getSharedPreferences("numComic", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                request = xkcdService.getUltimoComic();
                request.enqueue(new Callback<Comic>() {
                    @Override
                    public void onResponse(Call<Comic> call, Response<Comic> response) {
                        Comic comic = response.body();
                        int numeroComicUltimo = comic.getIdComic();
                        editor.putInt("numComic", numeroComicUltimo);
                        Log.d("numeroComic", String.valueOf(numeroComicUltimo));
                        editor.apply();
                        startActivity(registro);
                    }
                    @Override
                    public void onFailure(Call<Comic> call, Throwable t) {

                    }
                });

            }
        });

        Button botonLista = (Button) findViewById(R.id.btnMostrarBD);
        botonLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(MainActivity.this, ListadoComics.class);
                startActivity(registro);
            }
        });
    }
}
