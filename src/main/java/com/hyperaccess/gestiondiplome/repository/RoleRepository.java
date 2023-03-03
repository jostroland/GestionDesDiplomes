package com.hyperaccess.gestiondiplome.repository;

import com.hyperaccess.gestiondiplome.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findRoleByRole(String role);
}