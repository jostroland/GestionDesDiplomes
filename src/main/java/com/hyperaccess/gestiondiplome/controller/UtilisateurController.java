package com.hyperaccess.gestiondiplome.controller;


import com.hyperaccess.gestiondiplome.controller.api.UtilisateurApi;
import com.hyperaccess.gestiondiplome.dto.AuthenticationResponse;
import com.hyperaccess.gestiondiplome.dto.LightUtilisateurDto;
import com.hyperaccess.gestiondiplome.dto.UtilisateurDto;
import com.hyperaccess.gestiondiplome.services.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class UtilisateurController implements UtilisateurApi {
    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }



    @Override
    public Integer save(UtilisateurDto utilisateurDto) {
        return utilisateurService.save(utilisateurDto);
    }

    @Override
    public Integer update(Integer id,LightUtilisateurDto lightUtilisateurDto) {
        return utilisateurService.update(id,lightUtilisateurDto);
    }

    @Override
    public Integer active(Integer id) {
        return utilisateurService.active(id);
    }

    @Override
    public AuthenticationResponse create(MultipartFile file, UtilisateurDto utilisateurDto) {
        return utilisateurService.create(file,utilisateurDto);
    }


    @Override
    public Integer desactive(Integer id) {

        return utilisateurService.desactive(id);
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        return utilisateurService.findById(id);
    }

    @Override
    public UtilisateurDto findUtilisateurByNom(String nom) {
        return utilisateurService.findUtilisateurByNom(nom);
    }

    @Override
    public List<UtilisateurDto> findAll() {

        return utilisateurService.findAll();
    }

    @Override
    public void delete(Integer id) {
        utilisateurService.delete(id);
    }

   @Override
    public byte[] getPhoto(Integer id){
       try {
           return utilisateurService.getPhoto(id);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }
}
