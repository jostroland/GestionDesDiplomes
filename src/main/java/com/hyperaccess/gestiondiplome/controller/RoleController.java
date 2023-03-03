package com.hyperaccess.gestiondiplome.controller;


import com.hyperaccess.gestiondiplome.controller.api.RoleApi;
import com.hyperaccess.gestiondiplome.dto.RoleDto;
import com.hyperaccess.gestiondiplome.services.RoleService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;


import static java.util.Objects.nonNull;

@RestController
public class RoleController implements RoleApi {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Integer save(RoleDto roleDto) {
        return roleService.save(roleDto);
    }

    @Override
    public Integer update(Integer id, RoleDto roleDto) {
            return roleService.update(id,roleDto);
    }


    @Override
    public RoleDto findById(Integer id) {
        return roleService.findById(id);
    }

    @Override
    public RoleDto findRoleByRole(String role) {
        return roleService.findRoleByRole(role);
    }

    @Override
    public List<RoleDto> findAll() {
        return roleService.findAll();
    }

    @Override
    public void delete(Integer id) {
        roleService.delete(id);
    }
}
