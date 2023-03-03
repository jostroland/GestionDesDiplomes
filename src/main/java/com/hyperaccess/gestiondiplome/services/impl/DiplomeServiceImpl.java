package com.hyperaccess.gestiondiplome.services.impl;

import com.hyperaccess.gestiondiplome.dto.DiplomeDto;
import com.hyperaccess.gestiondiplome.dto.LightDiplomeDto;
import com.hyperaccess.gestiondiplome.dto.NewLightDiplomeDto;
import com.hyperaccess.gestiondiplome.entities.Diplome;
import com.hyperaccess.gestiondiplome.exception.EntityNotFoundException;
import com.hyperaccess.gestiondiplome.repository.DiplomeRepository;
import com.hyperaccess.gestiondiplome.repository.MinistreRepository;
import com.hyperaccess.gestiondiplome.services.DiplomeService;
import com.hyperaccess.gestiondiplome.validator.ObjectsValidator;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static java.util.Objects.isNull;


@Service
@Slf4j
public class DiplomeServiceImpl implements DiplomeService{


    private final DiplomeRepository diplomeRepository;
    private final MinistreRepository ministreRepository;
    private final ObjectsValidator<DiplomeDto> validator;
    private final ObservationRegistry registry;
    private final DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
            .withLocale(Locale.UK )
            .withZone( ZoneId.systemDefault() );

    public DiplomeServiceImpl(DiplomeRepository diplomeRepository, MinistreRepository ministreRepository, ObjectsValidator<DiplomeDto> validator, ObservationRegistry registry) {
        this.diplomeRepository = diplomeRepository;
        this.ministreRepository = ministreRepository;
        this.validator = validator;
        this.registry = registry;
    }


    @Override
    public Integer save(DiplomeDto diplomeDto) {
        validator.validate(diplomeDto);

        var ministreId = diplomeDto.ministreId();

        var ministre =  ministreRepository.findById(ministreId).orElseThrow(()-> new EntityNotFoundException("Le Ministrere avec l'ID :"+ ministreId +" n'existe pas"));


        var diplome = DiplomeDto.toEntity(diplomeDto);

        var numeroEnreg = this.genererateNumeroEnreg();
        diplome.setNumeroEnreg(numeroEnreg);
        diplome.setMinistre(ministre);
        diplome.setNombreEdition(diplomeDto.nombreEdition());

        diplome.setDateObtention(diplome.getDateObtention());

        Diplome diplomeEnreg = diplomeRepository.save(diplome);

        return Observation.createNotStarted("saveDiplome",registry)
                .observe(diplomeEnreg::getId);
    }

    @Override
    public Integer update(Integer id, LightDiplomeDto lightDiplomeDto) {

        var diplomeTrouverDto = this.findById(id);

        var ministreId =  lightDiplomeDto.ministreId();
        var ministre =  ministreRepository.findById(ministreId).orElseThrow(()-> new EntityNotFoundException("Le Ministrere avec l'ID :"+ ministreId +" n'existe pas"));

        Diplome diplome = LightDiplomeDto.toEntity(lightDiplomeDto);
        Diplome diplomeTrouver = DiplomeDto.toEntity(diplomeTrouverDto);

        diplomeTrouver.setBeneficiaire(diplome.getBeneficiaire().toUpperCase());
        diplomeTrouver.setMinistre(ministre);
        diplomeTrouver.setNombreEdition(diplome.getNombreEdition());

        diplomeTrouver.setDateObtention(diplome.getDateObtention());

        return diplomeRepository.save(diplomeTrouver).getId();
    }

    @Override
    public DiplomeDto findById(Integer id) {
        if (isNull(id)){
            log.error("l'ID est null");
            return null;
        }

        return Observation.createNotStarted("findByIdDiplome",registry)
                .observe(()-> diplomeRepository.findById(id)
                        .map(DiplomeDto::toDto)
                        .orElseThrow(()->new EntityNotFoundException("Diplome n'est pas valide " + id)));


    }

    @Override
    public List<DiplomeDto> findAll() {
        return Observation.createNotStarted("findAllDiplome",registry)
                .observe(()-> diplomeRepository.findAll(Sort.by("dateObtention").ascending()).stream()
                        .map(DiplomeDto::toDto)
                        .toList());
    }

    @Override
    public void delete(Integer id) {
        if (isNull(id)) log.error("l'ID est null");

        diplomeRepository.deleteById(id);

    }

    @Override
    public DiplomeDto findDiplomeByNumeroEnreg(String numeroEnreg) {
        if (!StringUtils.hasLength(numeroEnreg)){
            log.error("Le numero d'enregistrement est null");
            return null;
        }

        return Observation.createNotStarted("findRoleByNom",registry)
                .observe(() -> diplomeRepository.findDiplomeByNumeroEnreg(numeroEnreg)
                        .map(DiplomeDto::toDto)
                        .orElseThrow(() -> new EntityNotFoundException("Diplome n'est pas valide " + numeroEnreg)));
    }

    @Override
    public String genererateNumeroEnreg() {
        var random = new Random();
        return "HC"+random.nextInt(1000)+"-"+2023;
    }

    @Override
    public void exportToExcel(List<DiplomeDto> diplomeDtos,HttpServletResponse response) throws IOException {
        DiplomeExcel excel = new DiplomeExcel(diplomeDtos, ministreRepository);
        excel.export(response);
    }

    @Override
    public ByteArrayInputStream exportToPdf(String numeroEnreg, String src) throws IOException {
        NewLightDiplomeDto lightDiplomeDto = this.diplomeByNumeroEnreg(numeroEnreg);
        DiplomePdf pdf = new DiplomePdf(lightDiplomeDto,src);
        ByteArrayInputStream data = pdf.getData();

        return data;
    }



    public NewLightDiplomeDto diplomeByNumeroEnreg(String numeroEnreg) {
        if (!StringUtils.hasLength(numeroEnreg)){
            log.error("Le numero d'enregistrement est null");
            return null;
        }

        return  diplomeRepository.findDiplomeByNumeroEnreg(numeroEnreg)
                        .map(NewLightDiplomeDto::toDto)
                        .orElseThrow(() -> new EntityNotFoundException("Diplome n'est pas valide " + numeroEnreg));
    }

}
