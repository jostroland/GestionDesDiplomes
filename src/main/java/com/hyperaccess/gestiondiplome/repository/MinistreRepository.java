package com.hyperaccess.gestiondiplome.repository;

import com.hyperaccess.gestiondiplome.entities.Ministre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MinistreRepository extends JpaRepository<Ministre, Integer> {
}