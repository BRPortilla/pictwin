package cl.ucn.disc.dsm.pictwin.web;
import io.javalin.http.Handler;

import lombok.Getter;
import lombok.NonNull;

//La ruta.
@Getter
public class Route {

    //El método.
    protected Method method;

    //El camino.
    protected String path;

    //El handler.
    protected Handler handler;

    //Constructor.
    protected Route(@NonNull final Method method, @NonNull final String path) {
        this.method = method;
        this.path = path;
    }

    //Método.
    public enum Method {
        GET,
        POST,
        PUT,
    }
}
