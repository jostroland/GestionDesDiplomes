package com.hyperaccess.gestiondiplome.dto;


import com.hyperaccess.gestiondiplome.entities.Role;
import com.hyperaccess.gestiondiplome.entities.Utilisateur;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Builder;



@Builder
public record UtilisateurDto(
        Integer id,
        @NotNull(message = "Le nom ne doit pas est null")
        @NotBlank(message = "Le nom ne doit pas est vide")
        @NotEmpty(message = "Le nom ne doit pas est vide")
        @Column(unique = true)
        String nom,

        @NotNull(message = "Le prénom ne doit pas est null")
        @NotBlank(message = "Le prénom ne doit pas est vide")
        @NotEmpty(message = "Le prénom ne doit pas est vide")
        String prenoms,

        @NotNull(message = "L'email ne doit pas etre vide")
        @NotEmpty(message = "L'email ne doit pas etre vide")
        @NotBlank(message = "L'email ne doit pas etre vide")
        @Email(message = "L'email n'est conforme")
        @Column(unique = true)
        String email,
        boolean active,

        @NotNull(message = "Le mot de passe ne doit pas est null")
        @NotBlank(message = "Le mot de passe ne doit pas est vide")
        @NotEmpty(message = "Le mot de passe ne doit pas est vide")
        @Size(min = 6,max = 16, message = "Le mot de passe doit avoir au minimun six(6) caractères et seize(16) au maximun")
        String motDePasse,
        String photo,
        Integer  roleId

) {


    public static UtilisateurDto toDto(Utilisateur utilisateur) {

        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenoms(utilisateur.getPrenoms())
                .email(utilisateur.getEmail())
                .active(utilisateur.getActive())
                .motDePasse(utilisateur.getMotDePasse())
                .photo(utilisateur.getPhoto())
                .roleId(utilisateur.getRole().getId())
                .build();


    }

    public static Utilisateur toEntity(UtilisateurDto utilisateurDto) {

          return  Utilisateur.builder()
                  .id(utilisateurDto.id())
                  .nom(utilisateurDto.nom())
                  .prenoms(utilisateurDto.prenoms())
                  .email(utilisateurDto.email())
                  .active(utilisateurDto.active())
                  .motDePasse(utilisateurDto.motDePasse())
                  .photo(utilisateurDto.photo())
                  .role(
                          Role.builder()
                                  .id(utilisateurDto.roleId())
                                  .build()
                  )
                  .build();

    }
}
