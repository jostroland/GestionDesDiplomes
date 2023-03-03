package com.hyperaccess.gestiondiplome.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "diplomes")
public class Diplome extends AbstractEntity {

    private String numeroEnreg;
    private  String beneficiaire;
    private  Integer nombreEdition;
    private Instant dateObtention;

    @OneToOne
    private Ministre ministre;


}
