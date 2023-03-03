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
public record LightDiplomeDto(
        String beneficiaire,
        Instant dateObtention,
        Integer nombreEdition,
        Integer ministreId

) {

        public static LightDiplomeDto toDto(Diplome diplome){
                requireNonNull(diplome);
                return LightDiplomeDto.builder()
                        .beneficiaire(diplome.getBeneficiaire())
                        .dateObtention(diplome.getDateObtention())
                        .nombreEdition(diplome.getNombreEdition())
                        .ministreId(
                                diplome.getMinistre().getId()
                        )
                        .build();
        }


        public static Diplome toEntity(LightDiplomeDto lightDiplomeDto){
                requireNonNull(lightDiplomeDto);
                return Diplome.builder()
                        .beneficiaire(lightDiplomeDto.beneficiaire())
                        .dateObtention(lightDiplomeDto.dateObtention())
                        .nombreEdition(lightDiplomeDto.nombreEdition())
                        .ministre(
                                Ministre.builder()
                                        .id(lightDiplomeDto.ministreId())
                                        .build()
                        )
                        .build();
        }
}
