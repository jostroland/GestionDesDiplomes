package com.hyperaccess.gestiondiplome.controller;


import com.hyperaccess.gestiondiplome.controller.api.DiplomeApi;
import com.hyperaccess.gestiondiplome.dto.DiplomeDto;
import com.hyperaccess.gestiondiplome.dto.LightDiplomeDto;
import com.hyperaccess.gestiondiplome.services.DiplomeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

@RestController
public class DiplomeController implements DiplomeApi {
    private final DiplomeService diplomeService;
    private final String SOURCE = "D:\\test.pdf";

    public DiplomeController(DiplomeService diplomeService) {
        this.diplomeService = diplomeService;
    }

    @Override
    public Integer save(DiplomeDto diplomeDto) {
        return diplomeService.save(diplomeDto);
    }

    @Override
    public Integer update(Integer id, LightDiplomeDto lightDiplomeDto) {
           return diplomeService.update(id, lightDiplomeDto);
    }

    @Override
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss");
        String currentDateTime = format.format(ZonedDateTime.now());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=diplomes_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<DiplomeDto> diplomeDtoList = diplomeService.findAll();

        diplomeService.exportToExcel(diplomeDtoList,response);
    }


    @Override
    public InputStreamResource exportToPdf(String numeroEnreg) throws IOException {


        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                .withLocale(Locale.UK )
                .withZone( ZoneId.systemDefault() );

        String currentDateTime = formatter.format(Instant.now());

        HttpHeaders httpHeaders = new HttpHeaders();
        //httpHeaders.add("Content-Disposition", "inline; filename=employees.pdf")
        String headerValue = "attachment; filename=diplomes_" + numeroEnreg + "_du_"+currentDateTime+".pdf";
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION,headerValue);
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);

        var byteArrayInputStream = diplomeService.exportToPdf(numeroEnreg, SOURCE);

        return  new InputStreamResource(byteArrayInputStream);
    }


    @Override
    public DiplomeDto findById(Integer id) {
        return diplomeService.findById(id);
    }

    @Override
    public DiplomeDto findDiplomeByNumeroEnreg(String numeroEnreg) {
        return diplomeService.findDiplomeByNumeroEnreg(numeroEnreg);
    }

    @Override
    public List<DiplomeDto> findAll() {
        return diplomeService.findAll();
    }

    @Override
    public void delete(Integer id) {
        diplomeService.delete(id);
    }
}
