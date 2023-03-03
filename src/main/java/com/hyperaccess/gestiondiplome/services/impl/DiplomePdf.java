package com.hyperaccess.gestiondiplome.services.impl;

import com.hyperaccess.gestiondiplome.dto.DiplomeDto;
import com.hyperaccess.gestiondiplome.dto.LightDiplomeDto;
import com.hyperaccess.gestiondiplome.dto.NewLightDiplomeDto;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import static com.itextpdf.layout.properties.TextAlignment.CENTER;
import static com.itextpdf.layout.properties.TextAlignment.LEFT;
import static com.itextpdf.layout.properties.VerticalAlignment.BOTTOM;
import static com.itextpdf.layout.properties.VerticalAlignment.TOP;
import static java.lang.Math.PI;


public class DiplomePdf {




    private PdfDocument pdfDocument;

    private Document document;
    private String src;

    private String des;

    private NewLightDiplomeDto lightDiplomeDto;


    public DiplomePdf(NewLightDiplomeDto lightDiplomeDto, String src) throws IOException {
        this.src = Objects.requireNonNull(src,"Le chemin source ne doit pas etre null");
        this.lightDiplomeDto = lightDiplomeDto;
    }

    public ByteArrayInputStream getData() throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            pdfDocument = new PdfDocument(new PdfReader(this.src),
                    new PdfWriter(byteArrayOutputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        document = new Document(pdfDocument);
        Paragraph paragraphBeneficiaire = this.createParagraph(this.lightDiplomeDto.beneficiaire(),70);
        Paragraph paragraphNumeroEnreg = this.createParagraph(this.lightDiplomeDto.numeroEnreg(),8);
        Paragraph paragraphMinistre = this.createParagraph(this.lightDiplomeDto.ministre(),16);


        document.add(paragraphBeneficiaire);
        //addParagraphToGeneratedPDF(document,paragraphBeneficiaire,0,0,0);
        //addParagraphToGeneratedPDF(document,paragraphMinistre,0,0,0);
        //addParagraphToGeneratedPDF(document,paragraphNumeroEnreg,0,0,90);

        document.close();
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }



    public void addParagraphToGeneratedPDF(Document document,
                                           Paragraph paragraph,
                                           float xOffset,
                                           float verticalOffset,
                                           float rotation) {

        //PdfPage pdfPage = document.getPdfDocument().getPage(pageIndex);
        PdfPage pdfPage = document.getPdfDocument().getFirstPage();

        PageSize pageSize = (PageSize) pdfPage.getPageSizeWithRotation();
        float x = (pageSize.getLeft() + pageSize.getRight());
        float y = (pageSize.getTop() + pageSize.getBottom());
        float rotationInRadians = (float) (PI / 180 * rotation);
        document.showTextAligned(paragraph, x - xOffset, y + verticalOffset,
                0, LEFT, BOTTOM, rotationInRadians);
    }


    public Paragraph createParagraph(String paragraph,Integer fontSize) throws IOException {
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        Text text = new Text(paragraph);
        text.setFont(font);
        text.setFontSize(fontSize);
        text.setOpacity(1f);
        return new Paragraph(text);
    }



}
