package cl.ucn.disc.dsm.pictwin.web.routes;


import cl.ucn.disc.dsm.pictwin.services.Controller;
import cl.ucn.disc.dsm.pictwin.web.Route;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class PersonaPic extends Route {
    public PersonaPic(@NonNull final Controller controller){
        super(Method.POST,"/api/personas/{ulid}/pic");
        this.handler = buildHandler(controller);
    }

    private static Handler buildHandler(Controller controller){
        return ctx -> {
            String ulid = ctx.pathParam("ulid");
            log.debug("Detected ulid={} for Persona",ulid);

            UploadedFile file = ctx.uploadedFile("file");
            if(file == null){
                return;
            }

            File tempFile = new File("temporal-" + file.filename());

            try (FileOutputStream fos = new FileOutputStream(tempFile)){
                fos.write(file.content().readAllBytes());
            }catch (IOException e){
                log.error("Error writing the temporal file.", e);
                return;
            }

            log.debug("Archivo recibido: {}",file.filename());
            ctx.json(controller.addPic(ulid,23.646388888889,70.398055555556,tempFile));
        };

    }


}
