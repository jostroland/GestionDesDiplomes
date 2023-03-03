package com.hyperaccess.gestiondiplome.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.hyperaccess.gestiondiplome.entities.Role;
import com.hyperaccess.gestiondiplome.entities.Utilisateur;
import jakarta.persistence.Column;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record LightUtilisateurDto(
                                  Integer id,
                                  @Column(unique = true)
                                  String nom,

                                  String prenoms,
                                  String email,
                                  //boolean active,
                                  String motDePasse,

                                  String photo,
                                  Integer  roleId

) {


    public static LightUtilisateurDto toDto(Utilisateur utilisateur) {

        return LightUtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenoms(utilisateur.getPrenoms())
                .email(utilisateur.getEmail())
                //.active(utilisateur.getActive())
                .motDePasse(utilisateur.getMotDePasse())
                .photo(utilisateur.getPhoto())
                .roleId(utilisateur.getRole().getId())
                .build();


    }

    public static Utilisateur toEntity(LightUtilisateurDto lightUtilisateurDto) {

        return  Utilisateur.builder()
                .id(lightUtilisateurDto.id())
                .nom(lightUtilisateurDto.nom())
                .prenoms(lightUtilisateurDto.prenoms())
                .email(lightUtilisateurDto.email())
                //.active(lightUtilisateurDto.active())
                .motDePasse(lightUtilisateurDto.motDePasse())
                .photo(lightUtilisateurDto.photo())
                .role(
                        Role.builder()
                                .id(lightUtilisateurDto.roleId())
                                .build()
                )
                .build();

    }
}
