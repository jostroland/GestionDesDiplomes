package com.hyperaccess.gestiondiplome.repository;

import com.hyperaccess.gestiondiplome.entities.Diplome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiplomeRepository extends JpaRepository<Diplome, Integer> {
    Optional<Diplome> findDiplomeByBeneficiaire(String beneficiaire);
    Optional<Diplome> findDiplomeByNumeroEnreg(String numeroEnreg);
}