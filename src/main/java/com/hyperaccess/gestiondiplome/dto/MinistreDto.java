package com.hyperaccess.gestiondiplome.dto;

import com.hyperaccess.gestiondiplome.entities.Ministre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.Instant;

@Builder
public record MinistreDto(
        Integer id,

        @NotNull(message = "Le nom ne doit pas est null")
        @NotBlank(message = "Le nom ne doit pas est vide")
        @NotEmpty(message = "Le nom ne doit pas est vide")
        String nom,
        @NotNull(message = "Le prénom ne doit pas est null")
        @NotBlank(message = "Le prénom ne doit pas est vide")
        @NotEmpty(message = "Le prénom ne doit pas est vide")
        String prenoms,


        @NotNull(message =  "La date de prise de fonction ne doit pas est null")
        Instant datePriseFonction) {


    public static MinistreDto toDto(Ministre ministre){
        return MinistreDto.builder()
                .id(ministre.getId())
                .nom(ministre.getNom())
                .prenoms(ministre.getPrenoms())
                .datePriseFonction(ministre.getDatePriseFonction())
                .build();
    }

    public static Ministre toEntity(MinistreDto ministreDto) {
        return Ministre.builder()
                .id(ministreDto.id())
                .nom(ministreDto.nom())
                .prenoms(ministreDto.prenoms())
                .datePriseFonction(ministreDto.datePriseFonction())
                .build();

    }
}
