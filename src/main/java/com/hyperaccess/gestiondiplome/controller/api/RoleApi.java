package com.hyperaccess.gestiondiplome.controller.api;

import com.hyperaccess.gestiondiplome.dto.RoleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hyperaccess.gestiondiplome.utils.Constants.*;


@Tag(name = "Roles", description = "Roles API")
public interface RoleApi {


    @PostMapping(value = CREATE_ROLE_ENDPOINT,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet d'enregistrer ou de modifier un role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet role créer ou modifier"),
            @ApiResponse(responseCode = "400", description = "L'Objet role n'existe pas")
    })
    Integer save(@RequestBody RoleDto roleDto);


    @PutMapping(value = UPDATE_ROLE_ENDPOINT,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet de modifier un role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le role à été modifier"),
            @ApiResponse(responseCode = "400", description = "Le role n'existe pas dans la base de données")
    })
    Integer update(@PathVariable("id") Integer id,@RequestBody RoleDto roleDto);



    @GetMapping (value =  FIND_BY_ID_ROLE_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet de rechercher par son ID un role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet role à été trouvé dans la base de donnée"),
            @ApiResponse(responseCode = "404", description = "Aucun role à été trouvé dans la base de donnée avec l'ID fourni")
    })
    RoleDto findById(@PathVariable("id") Integer id);



    @GetMapping (value = FIND_BY_ROLENAME_ROLE_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description =  "Cette methode permet de rechercher par son CODE un role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet role à été trouvé dans la base de donnée"),
            @ApiResponse(responseCode = "404", description = "Aucun role à été trouvé dans la base de donnée avec l'CODE fourni")
    })
    RoleDto findRoleByRole(@PathVariable("role") String role);

    

    @GetMapping (value = FIND_ALL_ROLE_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet de sélectionner tous les roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet role à été trouvé dans la base de donnée"),
            @ApiResponse(responseCode = "404", description = "Aucun role à été trouvé dans la base de donnée avec l'ID fourni")
    })
    List<RoleDto> findAll();



    @DeleteMapping  (value =DELETE_BY_ID_ROLE_ENDPOINT)
    @Operation(description = "Cette methode permet de supprimer par son ID un role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet role à été supprimé dans la base de donnée"),
            @ApiResponse(responseCode = "404", description = "Aucun role à été trouvé dans la base de donnée avec l'ID fourni")
    })
    void delete(@PathVariable("id") Integer id);
}
