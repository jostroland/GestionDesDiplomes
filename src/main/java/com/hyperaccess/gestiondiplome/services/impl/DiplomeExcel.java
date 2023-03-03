package com.hyperaccess.gestiondiplome.services.impl;

import com.hyperaccess.gestiondiplome.dto.DiplomeDto;
import com.hyperaccess.gestiondiplome.entities.Diplome;
import com.hyperaccess.gestiondiplome.exception.EntityNotFoundException;
import com.hyperaccess.gestiondiplome.repository.MinistreRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

import static java.util.Objects.nonNull;

public class DiplomeExcel {

    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final List<Diplome> diplomes;

    private final MinistreRepository ministreRepository;


    public DiplomeExcel(List<DiplomeDto> diplomes, MinistreRepository ministreRepository) {
        this.ministreRepository = ministreRepository;
        workbook = new XSSFWorkbook();
        this.diplomes = diplomes.stream()
                .map(DiplomeDto::toEntity).toList();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Diplomes");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Numéro d'enregistrement", style);
        createCell(row, 1, "Bénéficiaire", style);
        createCell(row, 2, "Ministre", style);
        createCell(row, 3, "Nombre d'édition", style);
        createCell(row, 4, "Date d'obtention", style);


    }


    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Diplome diplome  : diplomes) {
            Row row = sheet.createRow(rowCount++);
            createCell(row, 0, diplome.getNumeroEnreg(), style);
            createCell(row, 1, diplome.getBeneficiaire(), style);

            var ministreId =  diplome.getMinistre().getId();
           var ministre =  ministreRepository.findById(ministreId).orElseThrow(()-> new EntityNotFoundException("Le Ministrere avec l'ID :"+ ministreId +" n'existe pas"));

            createCell(row, 2, ministre.getNom() +" "+ministre.getPrenoms(), style);
            createCell(row, 3, diplome.getNombreEdition(), style);

            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                    .withLocale(Locale.UK )
                    .withZone( ZoneId.systemDefault() );
            var format = formatter.format(diplome.getDateObtention());
            createCell(row,4, format, style);

        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }


    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
