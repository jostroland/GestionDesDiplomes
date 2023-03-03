package com.hyperaccess.gestiondiplome.dto;

import com.hyperaccess.gestiondiplome.entities.Diplome;
import com.hyperaccess.gestiondiplome.entities.Ministre;
import lombok.Builder;

import java.time.Instant;

import static java.util.Objects.requireNonNull;


@Builder
public record NewLightDiplomeDto(
        String numeroEnreg,
        String beneficiaire,

        String ministre

) {

        public static NewLightDiplomeDto toDto(Diplome diplome){
                requireNonNull(diplome);
                return NewLightDiplomeDto.builder()
                        .beneficiaire(diplome.getBeneficiaire())
                        .numeroEnreg(diplome.getNumeroEnreg())
                        .ministre(
                                diplome.getMinistre().getNom() + " " + diplome.getMinistre().getPrenoms()
                        )
                        .build();
        }


       /* public static Diplome toEntity(NewLightDiplomeDto lightDiplomeDto){
                requireNonNull(lightDiplomeDto);
                return Diplome.builder()
                        .beneficiaire(lightDiplomeDto.beneficiaire())
                        .numeroEnreg(lightDiplomeDto.numeroEnreg())
                        .ministre(
                                Ministre.builder()
                                        .id(lightDiplomeDto.ministreId())
                                        .build()
                        )
                        .build();
        }*/
}
