package cl.ucn.disc.dsm.pictwin.model;

import io.ebean.annotation.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

//El PicTwin.
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class PicTwin extends BaseModel {

    //La expiración.
    @NotNull private Instant expiration;

    //Expirado.
    @Builder.Default @NotNull private Boolean expired = Boolean.FALSE;

    //Reportado.
    @Builder.Default @NotNull private Boolean reported=  Boolean.FALSE;

    //Relación con Persona.
    @ManyToOne(optional = false)
    private Persona persona;

    //Relación con Pic.
    @ToString.Exclude
    @ManyToOne(optional = false)
    private Pic pic;

    //Relación con Twin.
    @ToString.Exclude
    @ManyToOne(optional = false)
    private Pic twin;
}
