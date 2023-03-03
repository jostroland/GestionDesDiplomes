package com.hyperaccess.gestiondiplome.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity{

    private String role;


    @OneToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
}
