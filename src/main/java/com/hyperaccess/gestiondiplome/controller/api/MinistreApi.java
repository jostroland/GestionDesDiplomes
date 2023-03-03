package com.hyperaccess.gestiondiplome.controller.api;

import com.hyperaccess.gestiondiplome.dto.MinistreDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hyperaccess.gestiondiplome.utils.Constants.*;


@Tag(name = "Ministres", description = "Ministres API")
public interface MinistreApi {


    @PostMapping(value = CREATE_MINISTRE_ENDPOINT,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet d'enregistrer ou de modifier un ministre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet ministre créer ou modifier"),
            @ApiResponse(responseCode = "400", description = "L'Objet ministre n'existe pas")
    })
    Integer save(@RequestBody MinistreDto ministreDto);


    @PutMapping(value = UPDATE_MINISTRE_ENDPOINT,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet de modifier un ministre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le Ministre à été modifié"),
            @ApiResponse(responseCode = "400", description = "Le Ministre n'existe pas dans la base de donnée")
    })
    Integer update(@PathVariable("id") Integer id,@RequestBody MinistreDto ministreDto);



    @GetMapping (value =  FIND_BY_ID_MINISTRE_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet de rechercher par son ID un ministre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet ministre à été trouvé dans la base de donnée"),
            @ApiResponse(responseCode = "404", description = "Aucun ministre à été trouvé dans la base de donnée avec l'ID fourni")
    })
    MinistreDto findById(@PathVariable("id") Integer id);



    @GetMapping (value = FIND_ALL_MINISTRE_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet de sélectionner tous les Ministres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet ministre à été trouvé dans la base de donnée"),
            @ApiResponse(responseCode = "404", description = "Aucun ministre à été trouvé dans la base de donnée avec l'ID fourni")
    })
    List<MinistreDto> findAll();




    @DeleteMapping  (value =DELETE_BY_ID_MINISTRE_ENDPOINT)
    @Operation(description = "Cette methode permet de supprimer par son ID un ministre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet ministre à été supprimé dans la base de donnée"),
            @ApiResponse(responseCode = "404", description = "Aucun ministre à été trouvé dans la base de donnée avec l'ID fourni")
    })
    void delete(@PathVariable("id") Integer id);
}
