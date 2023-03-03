package com.hyperaccess.gestiondiplome.controller.api;

import com.hyperaccess.gestiondiplome.dto.AuthenticationResponse;
import com.hyperaccess.gestiondiplome.dto.LightUtilisateurDto;
import com.hyperaccess.gestiondiplome.dto.UtilisateurDto;
import com.hyperaccess.gestiondiplome.entities.Utilisateur;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.hyperaccess.gestiondiplome.utils.Constants.*;


@Tag(name = "Utilisateurs", description = "Utilisateurs API")
public interface UtilisateurApi {


    @PostMapping(value = CREATE_UTILISATEUR_ENDPOINT,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet d'enregistrer ou de modifier un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet utilisateur créer ou modifier", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Utilisateur.class))}),
            @ApiResponse(responseCode = "400", description = "L'Objet utilisateur n'existe pas")
    })
    Integer save(@RequestBody UtilisateurDto utilisateurDto);


    @PutMapping(value = UPDATE_UTILISATEUR_ENDPOINT,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet d'enregistrer ou de modifier un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet utilisateur créer ou modifier", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Utilisateur.class))}),
            @ApiResponse(responseCode = "400", description = "L'Objet utilisateur n'existe pas")
    })
    Integer update(@PathVariable("id") Integer id,@RequestBody LightUtilisateurDto lightUtilisateurDto);


    @GetMapping(value = ACTIVE_UTILISATEUR_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet d'activer utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'utilisateur activer", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Utilisateur.class))}),
            @ApiResponse(responseCode = "400", description = "L'utilisateur n'existe pas")
    })
    Integer active(@PathVariable("id") Integer id);





    @PostMapping(value = CREATE_UTILISATEUR_PHOTO_ENDPOINT,consumes = {MediaType.MULTIPART_MIXED_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(description = "Cette methode permet d'enregistrer ou de modifier un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet utilisateur créer ou modifier", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Utilisateur.class))}),
            @ApiResponse(responseCode = "400", description = "L'Objet utilisateur n'existe pas")
    })
    AuthenticationResponse create(@RequestPart("file") MultipartFile file, @RequestBody UtilisateurDto utilisateurDto);


    @GetMapping(value = DESACTIVE_UTILISATEUR_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet d'activer utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'utilisateur activer", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Utilisateur.class))}),
            @ApiResponse(responseCode = "400", description = "L'utilisateur n'existe pas")
    })
    Integer desactive(@PathVariable("id") Integer id);



    @GetMapping (value =  FIND_BY_ID_UTILISATEUR_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet de rechercher par son ID un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet utilisateur à été trouvé dans la base de donnée"),
            @ApiResponse(responseCode = "404", description = "Aucun utilisateur à été trouvé dans la base de donnée avec l'ID fourni")
    })
    UtilisateurDto findById(@PathVariable("id") Integer id);



    @GetMapping (value = FIND_BY_NOM_UTILISATEUR_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description =  "Cette methode permet de rechercher par son CODE un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet utilisateur à été trouvé dans la base de donnée"),
            @ApiResponse(responseCode = "404", description = "Aucun utilisateur à été trouvé dans la base de donnée avec l'CODE fourni")
    })
    UtilisateurDto findUtilisateurByNom(@PathVariable("nom") String nom);

    

    @GetMapping (value = FIND_ALL_UTILISATEUR_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet de sélectionner tous les utilisateurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet utilisateur à été trouvé dans la base de donnée"),
            @ApiResponse(responseCode = "404", description = "Aucun utilisateur à été trouvé dans la base de donnée avec l'ID fourni")
    })
    List<UtilisateurDto> findAll();




    @DeleteMapping (value =DELETE_BY_ID_UTILISATEUR_ENDPOINT)
    @Operation(description = "Cette methode permet de supprimer par son ID un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet utilisateur à été supprimé dans la base de donnée"),
            @ApiResponse(responseCode = "404", description = "Aucun utilisateur à été trouvé dans la base de donnée avec l'ID fourni")
    })
    void delete(@PathVariable("id") Integer id);


    @GetMapping(value=OBTENIR_PHOTO_BY_ID_UTILISATEUR_ENDPOINT)
    @Operation(description = "Cette methode permet d'obtenir la photo de l'utilisateur conntecté à partir de son ID un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Photo de l'utilisateur obtenu avec success"),
            @ApiResponse(responseCode = "404", description = "Aucun utilisateur à été trouvé dans la base de donnée avec l'ID fourni")
    })
    byte[] getPhoto(@PathVariable("id") Integer id);


}
