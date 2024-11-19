package cl.ucn.disc.dsm.pictwin.web.routes;

import cl.ucn.disc.dsm.pictwin.services.Controller;
import cl.ucn.disc.dsm.pictwin.web.Route;
import io.javalin.http.Handler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonaLogin extends Route{

    public PersonaLogin(@NonNull final Controller controller){
        super(Method.POST, "/api/personas");
        this.handler = buildHandler(controller);
    }

    private static Handler buildHandler(Controller controller){
        return ctx -> {
            String password = ctx.formParam("password");
            String email = ctx.formParam("email");
            log.debug("Password obtenido, email de usuario={}",email);

            if(password != null && email != null){
                ctx.json(controller.login(email,password));
            }
        };

    }

}
