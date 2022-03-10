package es.jesusfernandez.tarea32.clasesPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comic {

    /*
    Clase Comic para la BBDD
     */

    @SerializedName("img") //Nombre en el JSON
    @Expose
    private String img;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("day")
    @Expose
    private String day;

    @SerializedName("year")
    @Expose
    private String year;

    @SerializedName("month")
    @Expose
    private String month;

    @SerializedName("num")
    @Expose
    private int idComic;

    public String getYear() {
        return year;
    }

    public int getIdComic() {
        return idComic;
    }

    public void setIdComic(int idComic) {
        this.idComic = idComic;
    }

    public void Comic(){

    }
    public Comic(int idComic, String title, String year, String img) {
        this.idComic = idComic;
        this.title = title;
        this.year = year;
        this.img = img;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
