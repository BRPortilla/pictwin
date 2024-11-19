package cl.ucn.disc.dsm.pictwin.utils;

import cl.ucn.disc.dsm.pictwin.main;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

//Utilidades de archivos.
@UtilityClass
public class FileUtils {

    //Leer todos los bytes de un archivo.
    public byte[] readAllBytes(File file){
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e){
            throw new RuntimeException("Can't read the file", e);
        }
    }

    //Obtener los archivos de los recursos.
    public static File getResourceFile(@NonNull String name){
        return new File(Objects.requireNonNull(main.class.getClassLoader().getResource(name)).getFile());
    }
}
