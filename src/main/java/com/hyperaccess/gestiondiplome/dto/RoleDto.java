package com.hyperaccess.gestiondiplome.dto;


import com.hyperaccess.gestiondiplome.entities.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;



@Builder
public record RoleDto (
     Integer id,

     @NotNull(message = "Le role ne doit pas est null")
     @NotBlank(message = "Le role ne doit pas est vide")
     @NotEmpty(message = "Le role ne doit pas est vide")
     String role

){

    public static RoleDto toDto(Role role){
        return RoleDto.builder()
                        .id(role.getId())
                        .role(role.getRole())
                                .build();

    }

    public static Role toEntity(RoleDto roleDto){
        return Role.builder()
                .id(roleDto.id())
                .role(roleDto.role())
                .build();
    }

}
