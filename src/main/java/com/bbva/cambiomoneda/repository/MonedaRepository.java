package com.bbva.cambiomoneda.repository;

import com.bbva.cambiomoneda.repository.entity.Moneda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MonedaRepository extends JpaRepository<Moneda, Integer> {

    @Query("SELECT m from Moneda m WHERE m.nombre  =:monedalocal")
    Moneda buscarPorNombre(String monedalocal);
}
