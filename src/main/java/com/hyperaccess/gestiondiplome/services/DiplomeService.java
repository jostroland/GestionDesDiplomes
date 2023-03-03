package com.hyperaccess.gestiondiplome.services;

import com.hyperaccess.gestiondiplome.dto.DiplomeDto;
import com.hyperaccess.gestiondiplome.dto.LightDiplomeDto;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface DiplomeService extends AbstractService<DiplomeDto> {

    DiplomeDto findDiplomeByNumeroEnreg(String numeroEnreg);

    String genererateNumeroEnreg();

    void exportToExcel(List<DiplomeDto> diplomeDtos,HttpServletResponse response) throws IOException;

    ByteArrayInputStream exportToPdf(String numeroEnreg, String src) throws IOException;

    Integer update(Integer id, LightDiplomeDto lightDiplomeDto);
}
