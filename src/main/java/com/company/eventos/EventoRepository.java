package com.company.eventos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends CrudRepository<Evento, Long> {
    List<Evento> findByNombre(String nombre);

    List<Evento> findByCategoriaId(Long categoriaId);

    List<Evento> findByPrecioGreaterThan(Double precio);

    List<Evento> findByPrecioLessThan(Double precio);

}
