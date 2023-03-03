package com.hyperaccess.gestiondiplome.dto;

import com.hyperaccess.gestiondiplome.entities.Diplome;
import com.hyperaccess.gestiondiplome.entities.Ministre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.Instant;

import static java.util.Objects.requireNonNull;

@Builder
public record DiplomeDto(
        Integer id,

        @NotNull(message = "Le numero d'enregistrement ne doit pas est null")
        @NotBlank(message = "Le numero d'enregistrement ne doit pas est vide")
        @NotEmpty(message = "Le numero d'enregistrement ne doit pas est vide")
        String numeroEnreg,


        @NotNull(message = "Le date d'obtention ne doit pas est null")
        Instant dateObtention,
        Integer nombreEdition,

        @NotNull(message = "Le nom du bénéficiaire ne doit pas est null")
        @NotBlank(message = "Le nom du bénéficiaire ne doit pas est vide")
        @NotEmpty(message = "Le nom du bénéficiaire ne doit pas est vide")
        String beneficiaire,


        Integer ministreId
         ) {


    public static DiplomeDto toDto(Diplome diplome){
         requireNonNull(diplome);
         return DiplomeDto.builder()
                 .id(diplome.getId())
                 .numeroEnreg(diplome.getNumeroEnreg())
                 .dateObtention(diplome.getDateObtention())
                 .nombreEdition(diplome.getNombreEdition())
                 .beneficiaire(diplome.getBeneficiaire())
                 .ministreId(
                         diplome.getMinistre().getId()
                         )
                 .build();
    }


    public static Diplome toEntity(DiplomeDto diplomeDto){
        requireNonNull(diplomeDto);

        return Diplome.builder()
                .id(diplomeDto.id())
                .numeroEnreg(diplomeDto.numeroEnreg())
                .dateObtention(diplomeDto.dateObtention())
                .nombreEdition(diplomeDto.nombreEdition())
                .beneficiaire(diplomeDto.beneficiaire())
                .ministre(
                        Ministre.builder()
                                .id(diplomeDto.ministreId())
                                .build()
                )
                .build();




    }

}
