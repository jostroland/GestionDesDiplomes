package com.hyperaccess.gestiondiplome.services;

import com.hyperaccess.gestiondiplome.dto.DiplomeDto;
import com.hyperaccess.gestiondiplome.dto.RoleDto;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface RoleService extends AbstractService<RoleDto>  {
    RoleDto findRoleByRole(String role);

    Integer update(Integer id, RoleDto roleDto);
}
