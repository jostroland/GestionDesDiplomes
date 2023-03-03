package com.hyperaccess.gestiondiplome.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ministres")
public class Ministre extends AbstractEntity {

    private String nom;

    private String prenoms;

    private Instant datePriseFonction;

    /*@JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "ministre")
    private Set<Diplome> diplomes = new HashSet<>();*/

}
