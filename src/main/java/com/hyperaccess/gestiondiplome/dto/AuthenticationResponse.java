package com.hyperaccess.gestiondiplome.dto;


import lombok.Builder;


@Builder
public record AuthenticationResponse(String token) {
}
