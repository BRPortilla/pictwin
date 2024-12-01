package cl.ucn.disc.dsm.pictwin.model;

//import com.fasterxml.jackson.annotation.JsonBackReference;
import io.ebean.annotation.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

//EL Pic.
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Pic extends BaseModel {

    //La latitud.
    @NotNull private Double latitude;

    //La longitud.
    @NotNull private Double longitude;

    //Número de reportes.
    @Builder.Default @NotNull private Integer reports = 0;

    //Fecha.
    @NotNull private Instant date;

    //La foto.
    @NotNull @Lob private byte[] photo;

    //Bloqueado.
    @Builder.Default @NotNull private Boolean blocked = Boolean.FALSE;

    //Número de visitas.
    @Builder.Default @NotNull private Integer views = 0;

    //Relación con Persona.
    @ManyToOne(optional = false)
    //@JsonBackReference
    private Persona persona;
}
