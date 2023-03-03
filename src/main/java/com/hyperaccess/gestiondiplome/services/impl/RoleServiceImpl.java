package com.hyperaccess.gestiondiplome.services.impl;

import com.hyperaccess.gestiondiplome.dto.RoleDto;
import com.hyperaccess.gestiondiplome.entities.Role;
import com.hyperaccess.gestiondiplome.exception.EntityNotFoundException;
import com.hyperaccess.gestiondiplome.repository.RoleRepository;
import com.hyperaccess.gestiondiplome.services.RoleService;
import com.hyperaccess.gestiondiplome.validator.ObjectsValidator;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static java.util.Objects.isNull;


@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final ObservationRegistry registry;
    private final RoleRepository roleRepository;
    private final ObjectsValidator<RoleDto> validator;

    public RoleServiceImpl(ObservationRegistry registry, RoleRepository roleRepository, ObjectsValidator<RoleDto> validator) {
        this.registry = registry;
        this.roleRepository = roleRepository;
        this.validator = validator;
    }

    @Override
    public Integer save(RoleDto roleDto) {
        validator.validate(roleDto);

        Role role =  RoleDto.toEntity(roleDto);

        String newRole = "ROLE_" + roleDto.role();
        role.setRole(newRole);

        Role roleEnreg = roleRepository.save(role);

        return Observation.createNotStarted("saveRole",registry)
                .observe(roleEnreg::getId);
    }

    @Override
    public RoleDto findById(Integer id) {
        if (isNull(id)){
            log.error("l'ID est null");
            return null;
        }

        return  Observation.createNotStarted("findByIdRole",registry)
                .observe(() -> roleRepository.findById(id)
                        .map(RoleDto::toDto)
                        .orElseThrow(() -> new EntityNotFoundException("Role n'est pas valide " + id)));
    }

    @Override
    public List<RoleDto> findAll() {
        return Observation.createNotStarted("findAllRole",registry)
                .observe(() -> roleRepository.findAll().stream()
                        .map(RoleDto::toDto)
                        .toList());
    }

    @Override
    public void delete(Integer id) {
        if (isNull(id)){
            log.error("l'ID est null");
        }

        roleRepository.deleteById(id);
    }


    @Override
    public RoleDto findRoleByRole(String role) {
        if (!StringUtils.hasLength(role)){
            log.error("Le role est null");
            return null;
        }

        String newRole = getNewRole(role);

        return Observation.createNotStarted("findRoleByRole",registry)

                .observe(() ->
                    roleRepository.findRoleByRole(newRole)
                        .map(RoleDto::toDto)
                        .orElseThrow(() -> new EntityNotFoundException("Role n'est pas valide " + role)));

    }

    private static String getNewRole(String role) {
        return "ROLE_" + role.toUpperCase();
    }

    @Override
    public Integer update(Integer id, RoleDto roleDto) {
        var roleExistantDto = findById(id);

        validator.validate(roleExistantDto);
        Role monRole =  RoleDto.toEntity(roleDto);
        Role roleExistant =  RoleDto.toEntity(roleExistantDto);

        var role = getNewRole(monRole.getRole());
        if (!isNull(role))
            roleExistant.setRole(role);


        var roleExistantEnreg = roleRepository.save(roleExistant);

        return Observation.createNotStarted("updateRole",registry)
                .observe(roleExistantEnreg::getId);
    }
}
