package cl.ucn.disc.dsm.pictwin;

import cl.ucn.disc.dsm.pictwin.model.Persona;
import cl.ucn.disc.dsm.pictwin.model.PicTwin;
import cl.ucn.disc.dsm.pictwin.services.Controller;

import cl.ucn.disc.dsm.pictwin.utils.FileUtils;
import io.ebean.DB;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

//El main.
@Slf4j
public class main {

    public static void main(String[] args){

        log.debug("Starting TheMain ...");
        Controller c = new Controller(DB.getDefault());

        if(c.seed()){
            log.debug("La base de datos ha sido seedeada.");
        }

        log.debug("Registering Persona...");
        Persona p1 = c.register("durrutia@ucn.cl","durrutia123");
        log.debug("Persona: {}", p1);

        File file = FileUtils.getResourceFile("antofagasta.jpg");
        log.debug("File: {}", file);

        PicTwin pt = c.addPic(p1.getUlid(), -23.6509, -70.3975, file);
        log.debug("PicTwin: {}", pt);

        Persona p2 = c.login("durrutia@ucn.cl","durrutia123");
        log.debug("Persona: {}", p2);

        List<PicTwin> pts = c.getPicTwins(p1.getUlid());
        for (PicTwin ptt: pts)
            log.debug("PicTwin: {}",pt);

        log.debug("Done.");
    }
}
