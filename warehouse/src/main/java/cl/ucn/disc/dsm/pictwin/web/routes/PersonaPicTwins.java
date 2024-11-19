package cl.ucn.disc.dsm.pictwin.web.routes;

import cl.ucn.disc.dsm.pictwin.services.Controller;
import cl.ucn.disc.dsm.pictwin.web.Route;

import io.javalin.http.Handler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

//Ruta de los PicTwins de la Persona.
@Slf4j
public final class PersonaPicTwins extends Route{

    //Constructor.
    //Utiliza el constructor de la clase anterior (Route), enviando tipo de método y ruta.
    public PersonaPicTwins(@NonNull final Controller controller){
        super(Method.GET, "/api/personas/{ulid}/pictwins");
        this.handler = buildHandler(controller);
    }

    //Construir el handler (?)
    private static  Handler buildHandler(Controller controller){
        return ctx -> {String ulid = ctx.pathParam("ulid");
                        log.debug("Detected ulid={} for Persona",ulid);

                        ctx.json(controller.getPicTwins(ulid));
        };
    }


}
