package com.hyperaccess.gestiondiplome.controller.api;

import com.hyperaccess.gestiondiplome.dto.DiplomeDto;
import com.hyperaccess.gestiondiplome.dto.LightDiplomeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.hyperaccess.gestiondiplome.utils.Constants.*;



@Tag(name = "Diplomes", description = "Diplomes API")
public interface DiplomeApi {


    @PostMapping(value = CREATE_DIPLOME_ENDPOINT,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet d'enregistrer ou de modifier un Diplome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet Diplome créer ou modifier"),
            @ApiResponse(responseCode = "400", description = "L'Objet Diplome n'existe pas")
    })
    Integer save(@RequestBody DiplomeDto DiplomeDto);


    @PutMapping(value = UPDATE_DIPLOME_ENDPOINT,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet de modifier un Diplome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diplome modifier"),
            @ApiResponse(responseCode = "400", description = "Le Diplome n'existe pas")
    })
    Integer update(@PathVariable("id") Integer id,@RequestBody LightDiplomeDto lightDiplomeDto);



    @GetMapping(value = EXPORT_EXCEL_DIPLOME_ENDPOINT,consumes = MediaType.ALL_VALUE)
    @Operation(description = "Cette methode permet d'exporter les infomration de plusieurs Diplomes au format excel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diplome exporter au format excel"),
            @ApiResponse(responseCode = "400", description = "Le Diplome n'existe pas")
    })
    void exportToExcel(HttpServletResponse response) throws IOException;


    @GetMapping(value = EXPORT_PDF_DIPLOME_ENDPOINT,consumes=MediaType.ALL_VALUE,produces=MediaType.APPLICATION_PDF_VALUE)
    @Operation(description = "Cette methode permet d'exporter un Diplome au format pdf en fonction de son numéro d'enregistreement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diplome exporter au format PDF"),
            @ApiResponse(responseCode = "400", description = "Le Diplome n'existe pas")
    })
    InputStreamResource exportToPdf(@PathVariable("numeroEnreg") String numeroEnreg) throws IOException;



    @GetMapping (value =  FIND_BY_ID_DIPLOME_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet de rechercher par son ID un Diplome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet Diplome à été trouvé dans la base de donnée"),
            @ApiResponse(responseCode = "404", description = "Aucune Diplome à été trouvé dans la base de donnée avec l'ID fourni")
    })
    DiplomeDto findById(@PathVariable("id") Integer id);


    @GetMapping (value =  FIND_BY_NUMERO_ENREG_DIPLOME_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet de rechercher par son ID un Diplome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet Diplome à été trouvé dans la base de donnée"),
            @ApiResponse(responseCode = "404", description = "Aucune Diplome à été trouvé dans la base de donnée avec l'ID fourni")
    })
    DiplomeDto findDiplomeByNumeroEnreg(@PathVariable("numeroEnreg")  String numeroEnreg);



    @GetMapping (value = FIND_ALL_DIPLOME_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cette methode permet de sélectionner tous les Diplomes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet Diplome à été trouvé dans la base de donnée"),
            @ApiResponse(responseCode = "404", description = "Aucun Diplome à été trouvé dans la base de donnée avec l'ID fourni")
    })
    List<DiplomeDto> findAll();




    @DeleteMapping  (value =DELETE_BY_ID_DIPLOME_ENDPOINT)
    @Operation(description = "Cette methode permet de supprimer par son ID un Diplome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'Objet Diplome à été supprimé dans la base de donnée"),
            @ApiResponse(responseCode = "404", description = "Aucun Diplome à été trouvé dans la base de donnée avec l'ID fourni")
    })
    void delete(@PathVariable("id") Integer id);
}
