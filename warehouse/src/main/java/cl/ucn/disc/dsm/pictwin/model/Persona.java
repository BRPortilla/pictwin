package cl.ucn.disc.dsm.pictwin.model;

import io.ebean.annotation.Index;
import io.ebean.annotation.NotNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;


/**
 * La clase Persona. Extiende la clase abstracta y añade los atributos del modelo de dominio,
 * incluyendo relaciones con las otras clases.
 */
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Persona extends BaseModel {

    //El correo.
    @NotNull
    @Index(unique = true)
    private String email;

    //La contraseña.
    @NotNull
    @Column(length = 72)
    private String password;

    //Número de strikes.
    @Builder.Default @NotNull private Integer strikes = 0;

    //Bloqueado.
    @Builder.Default @NotNull private  Boolean blocked = Boolean.FALSE;

    //Fecha de bloqueo.
    private Instant blockedAt;

    //Lista de pics.
    @ToString.Exclude
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    private List<Pic> pics;

    //Lista de PicTwins.
    @ToString.Exclude
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    private List<PicTwin> picTwins;
}