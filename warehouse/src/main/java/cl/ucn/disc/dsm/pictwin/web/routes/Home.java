package cl.ucn.disc.dsm.pictwin.web.routes;
import cl.ucn.disc.dsm.pictwin.web.Route;

//La ruta del Home.
public final class Home extends Route {

    //Constructor del hogar.
    public Home(){
        super(Method.GET,"/");
        this.handler = ctx -> {ctx.result("Welcome to PicTwin");};

    }
}
