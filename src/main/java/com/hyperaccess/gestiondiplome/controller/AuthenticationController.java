package com.hyperaccess.gestiondiplome.controller;


import com.hyperaccess.gestiondiplome.dto.AuthenticationRequest;
import com.hyperaccess.gestiondiplome.dto.AuthenticationResponse;
import com.hyperaccess.gestiondiplome.dto.UtilisateurDto;
import com.hyperaccess.gestiondiplome.services.UtilisateurService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
@Tag(name = "authentication")
public class AuthenticationController {

    private final UtilisateurService utilisateurService;


    @PostMapping("/register")
    private ResponseEntity<AuthenticationResponse> register(@RequestBody UtilisateurDto utilisateurDto) {
        return ResponseEntity.ok(utilisateurService.register(utilisateurDto));
    }


    @PostMapping("/authenticate")
    private ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest ){

        return ResponseEntity.ok(
               utilisateurService.authenticate(authenticationRequest)
        );

    }
}
