package com.hyperaccess.gestiondiplome.repository;

import com.hyperaccess.gestiondiplome.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findUtilisateursByNom(String nom);
    Optional<Utilisateur> findUtilisateursByEmail(String email);
}