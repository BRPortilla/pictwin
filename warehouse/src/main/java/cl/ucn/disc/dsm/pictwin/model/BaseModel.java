/*
Copyright (c) 2024. Departamento de Ingeniería de Sistemas y Computación.
 */

package cl.ucn.disc.dsm.pictwin.model;
import com.github.f4b6a3.ulid.UlidCreator;

import io.ebean.annotation.Index;
import io.ebean.annotation.SoftDelete;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

/** La clase del BaseModel. **/

/** Necesitamos las clases del modelo de domino para poder persistir en una base de datos.
 *  Podemos construir una clase abstracta que encapsule todos los atributos parte de una clase persistente.
 **/


//Atributos para cada uno de los elementos en la base de datos.
@ToString
@MappedSuperclass
public abstract class BaseModel {


    //ID: identificador único en la base de datos, generalmente correlativo al anterior.
    @Getter @Setter @Id private Long id;


    //ULID: Identificador único universal ordenado lexicográficamente.
    @Getter
    @Index(unique = true)
    @Column(length = 26)
    private final String ulid = UlidCreator.getUlid().toLowerCase();

    //Versión:
    @Getter @Setter @Version private Long version;

    //Fecha de creación.
    @Getter @Setter @WhenCreated private Instant createdAt;

    //Fecha de modificación.
    @Getter @Setter @WhenModified private Long modifiedAt;

    //Borrado suave.
    @SoftDelete private boolean deleted = Boolean.FALSE;
}
