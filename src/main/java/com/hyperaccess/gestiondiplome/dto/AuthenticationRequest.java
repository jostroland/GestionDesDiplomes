package com.hyperaccess.gestiondiplome.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record AuthenticationRequest(

        @Email(message = "Veuillez entrer un mail valide")
        @Column(unique = true)
        String email,

        @NotNull(message = "Le mot de passe ne doit pas est null")
        @NotBlank(message = "Le mot de passe ne doit pas est vide")
        @NotEmpty(message = "Le mot de passe ne doit pas est vide")
        @Size(min = 6,max = 16, message = "Le mot de passe doit avoir au minimun six(6) caractères et seize(16) au maximun")
        String password
) {
}
