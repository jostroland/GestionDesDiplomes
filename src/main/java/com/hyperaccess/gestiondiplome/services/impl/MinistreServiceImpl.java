package com.hyperaccess.gestiondiplome.services.impl;

import com.hyperaccess.gestiondiplome.dto.MinistreDto;
import com.hyperaccess.gestiondiplome.entities.Ministre;
import com.hyperaccess.gestiondiplome.exception.EntityNotFoundException;
import com.hyperaccess.gestiondiplome.repository.MinistreRepository;
import com.hyperaccess.gestiondiplome.services.MinistreService;
import com.hyperaccess.gestiondiplome.validator.ObjectsValidator;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


@Service
@Slf4j
public class MinistreServiceImpl implements MinistreService {

    private final MinistreRepository ministreRepository;
    private final ObservationRegistry registry;
    private final ObjectsValidator<MinistreDto> validator;

    public MinistreServiceImpl(MinistreRepository ministreRepository, ObservationRegistry registry, ObjectsValidator<MinistreDto> validator) {
        this.ministreRepository = ministreRepository;
        this.registry = registry;
        this.validator = validator;
    }

    @Override
    public Integer save(MinistreDto ministreDto) {
        validator.validate(ministreDto);

        Ministre ministreEnreg = ministreRepository.save(
                MinistreDto.toEntity(ministreDto)
        );

        return Observation.createNotStarted("saveMinistre",registry)
                .observe(ministreEnreg::getId);
    }

    @Override
    public MinistreDto findById(Integer id) {
        if (isNull(id)){
            log.error("l'ID est null");
            return null;
        }

        return  Observation.createNotStarted("findByIdMinistre",registry)
                .observe(()-> ministreRepository.findById(id)
                        .map(MinistreDto::toDto)
                        .orElseThrow(() -> new EntityNotFoundException("Ministre n'est pas valide " + id)));

    }

    @Override
    public List<MinistreDto> findAll() {
        return Observation.createNotStarted("findAllMinistre",registry)
                .observe(()-> ministreRepository.findAll().stream()
                        .map(MinistreDto::toDto)
                        .toList());
    }

    @Override
    public void delete(Integer id) {
        if (isNull(id)) log.error("l'ID est null");

        ministreRepository.deleteById(id);

    }

    @Override
    public Integer update(Integer id, MinistreDto ministreDto) {
        validator.validate(ministreDto);

        var ministreExistantDto = findById(id);
        Ministre ministre         =  MinistreDto.toEntity(ministreDto);
        Ministre ministreExistant =  MinistreDto.toEntity(ministreExistantDto);

        if (nonNull(ministre.getNom()))
            ministreExistant.setNom(ministre.getNom());

        if (nonNull(ministre.getPrenoms()))
            ministreExistant.setPrenoms(ministre.getPrenoms());

        if (nonNull(ministre.getDatePriseFonction()))
            ministreExistant.setDatePriseFonction(ministre.getDatePriseFonction());

       var ministreExistantEnreg = ministreRepository.save(ministreExistant);

       return Observation.createNotStarted("updateMinistre",registry)
                .observe(ministreExistantEnreg::getId);
    }
}
