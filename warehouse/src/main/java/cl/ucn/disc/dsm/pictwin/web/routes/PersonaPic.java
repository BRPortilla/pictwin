package cl.ucn.disc.dsm.pictwin.web.routes;


import cl.ucn.disc.dsm.pictwin.model.PicTwin;
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
                ctx.status(400);
                return;
            }

            File tempFile = new File("temporal-" + file.filename());
            log.debug(tempFile.getName());


            try (FileOutputStream fos = new FileOutputStream(tempFile)){
                fos.write(file.content().readAllBytes());
            }catch (IOException e){
                log.debug("Error writing the temporal file.", e);
            }

            log.debug("Archivo recibido: {}",file.filename());
            PicTwin picTwin = controller.addPic(ulid,23.646388888889,70.398055555556,tempFile);

            ctx.json(picTwin.getPersona().getEmail());
            ctx.status(200).result("File uploaded.");

            if(!tempFile.delete()){
                log.debug("No se puede borrar el archivo temporal.");
            }
        };
    }
}
