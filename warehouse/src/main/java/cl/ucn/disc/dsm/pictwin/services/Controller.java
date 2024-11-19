package cl.ucn.disc.dsm.pictwin.services;

import cl.ucn.disc.dsm.pictwin.model.Persona;
import cl.ucn.disc.dsm.pictwin.model.Pic;
import cl.ucn.disc.dsm.pictwin.model.PicTwin;
import cl.ucn.disc.dsm.pictwin.model.query.QPersona;
import cl.ucn.disc.dsm.pictwin.model.query.QPicTwin;
import cl.ucn.disc.dsm.pictwin.utils.FileUtils;

import com.password4j.Password;

import io.ebean.Database;
import io.ebean.annotation.Transactional;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.time.Instant;
import java.util.List;

import cl.ucn.disc.dsm.pictwin.utils.FileUtils;

//EL controlador.
@Slf4j
public class Controller {

    //La base de datos.
    private final Database database;

    //EL constructor del controlador.
    public Controller (@NonNull final Database database) {this.database = database;}

    //Registrar un nuevo usuario.
    @Transactional
    public Persona register(@NonNull final String email, @NonNull final String password){

        //Hashear la contraseña.
        String hashedPassword = Password.hash(password).withBcrypt().getResult();
        log.debug("Hashed password: {}", hashedPassword);

        //Construir una Persona.
        Persona persona = Persona.builder()
                .email(email)
                .password(hashedPassword)
                .strikes(0)
                .blocked(Boolean.FALSE)
                .build();

        //Guardar la Persona.
        this.database.save(persona);
        log.debug("Persona saved: {}", persona);
        return persona;
    }


    //El "seed" del controlador.
    public Boolean seed(){

        //Encontrar el tamaño de la tabla Persona.
        int personaSize = new QPersona().findCount();
        log.debug("Personas in database: {}",personaSize);

        //Si no está vacío, no usar el seed!
        if (personaSize != 0){
            return Boolean.FALSE;
        }

        log.debug("Can't find data, seeding the database...");

        //Hacerle seed a la tabla Persona.
        Persona persona = this.register("durrutia@ucn.cl","durrutia123");
        log.debug("Persona registered: {}",persona);
        log.debug("Database seeded.");
        return Boolean.TRUE;
    }


    //Loggear un usuario.
    public Persona login(@NonNull final String email, @NonNull final String password){

        //Encontrar la persona.
        Persona persona = new QPersona().email.equalTo(email).findOne();
        if(persona == null){
            throw new RuntimeException("User not found");
        }

        //Comprobar la contraseña.
        if(!Password.check(password, persona.getPassword()).withBcrypt()){
            throw new RuntimeException("Wrong password");
        }

        //Retornar la contraseña.
        return persona;
    }

    //Añadir una nueva Pic en un PicTwin.
    @Transactional
    public PicTwin addPic(
            @NonNull String ulidPersona,
            @NonNull Double latitude,
            @NonNull Double longitude,
            @NonNull File picture){

        //Leer el archivo.
        byte[] data = FileUtils.readAllBytes(picture);

        //Encontrar la persona.
        Persona persona = new QPersona().ulid.equalTo(ulidPersona).findOne();
        log.debug("Persona found: {}", persona);

        //Guardar el Pic.
        Pic pic = Pic.builder()
                .latitude(latitude)
                .longitude(longitude)
                .reports(0)
                .date(Instant.now())
                .photo(data).blocked(false)
                .views(0)
                .persona(persona)
                .build();
        log.debug("Pic to save: {}", pic);
        this.database.save(pic);

        //Guardar el PicTwin.
        PicTwin picTwin = PicTwin.builder().
                expiration(Instant.now().plusSeconds(7 * 24 * 60 * 60))
                .expired(false)
                .reported(false)
                .persona(persona)
                .pic(pic)
                .twin(pic) //FIXME: añadir una nueva pic de la base de datos.
                .build();

        log.debug("PicTwin to save: {}", picTwin);
        this.database.save(picTwin);
        return picTwin;
    }

    /*
    private byte[] readAllBytes(File file){
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e){
            throw new RuntimeException("Can't read the file", e);
        }
    }
    */

    //Obtener los PicTwins.
    public List<PicTwin> getPicTwins(@NonNull String ulidPersona){
        return new QPicTwin().persona.ulid.equalTo(ulidPersona).findList();
    }
}
