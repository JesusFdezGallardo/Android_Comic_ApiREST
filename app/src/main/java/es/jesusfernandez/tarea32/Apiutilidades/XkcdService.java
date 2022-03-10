package es.jesusfernandez.tarea32.Apiutilidades;

import es.jesusfernandez.tarea32.clasesPOJO.Comic;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface XkcdService {

    @GET("{idComic}/info.0.json")
    Call<Comic> getComic(@Path("idComic") int idComic);

    @GET("/info.0.json")
    Call<Comic> getUltimoComic();

    @GET("1/info.0.json")
    Call<Comic> getPrimerComic();
}
