package es.jesusfernandez.tarea32.hilos;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {

    public ExecutorService diskIOExecutor = Executors.newSingleThreadExecutor();

}
