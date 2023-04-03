package com.bbva.cambiomoneda.repository;

import com.bbva.cambiomoneda.repository.entity.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Integer> {
}
