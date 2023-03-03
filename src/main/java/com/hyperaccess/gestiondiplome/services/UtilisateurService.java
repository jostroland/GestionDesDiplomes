package com.hyperaccess.gestiondiplome.services;

import com.hyperaccess.gestiondiplome.dto.AuthenticationRequest;
import com.hyperaccess.gestiondiplome.dto.AuthenticationResponse;
import com.hyperaccess.gestiondiplome.dto.LightUtilisateurDto;
import com.hyperaccess.gestiondiplome.dto.UtilisateurDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface UtilisateurService extends AbstractService<UtilisateurDto> {
    UtilisateurDto findUtilisateurByNom(String nom);

    AuthenticationResponse create(MultipartFile file, UtilisateurDto utilisateurDto);
    AuthenticationResponse register(UtilisateurDto utilisateurDto);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    Integer active(Integer id);
    Integer desactive(Integer id);

    Integer update(Integer id,LightUtilisateurDto lightUtilisateurDto);

    byte[] getPhoto(Integer id) throws IOException;

}
