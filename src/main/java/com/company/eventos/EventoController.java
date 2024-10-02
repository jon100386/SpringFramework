package com.company.eventos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proyectos")
public class EventoController {

    private final EventoRepository eventoRepository;
    private final CategoriaRepository categoriaRepository;

    public EventoController(EventoRepository eventoRepository, CategoriaRepository categoriaRepository) {
        this.eventoRepository = eventoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public ResponseEntity<List<Evento>> obtenerTodos() {
        List<Evento> eventos = (List<Evento>) eventoRepository.findAll();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtenerPorId(@PathVariable Long id) {
        return eventoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Evento> crearProyecto(@RequestBody Evento evento) {
        if (evento.getCategoria() != null && evento.getCategoria().getId() != null) {
            Optional<Categoria> categoria = categoriaRepository.findById(evento.getCategoria().getId());
            if (categoria.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            evento.setCategoria(categoria.get());
        }
        Evento nuevoEvento = eventoRepository.save(evento);
        return new ResponseEntity<>(nuevoEvento, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> actualizarProyecto(@PathVariable Long id, @RequestBody Evento evento) {
        if (!eventoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        evento.setId(id);
        if (evento.getCategoria() != null && evento.getCategoria().getId() != null) {
            Optional<Categoria> categoria = categoriaRepository.findById(evento.getCategoria().getId());
            if (categoria.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            evento.setCategoria(categoria.get());
        }
        Evento actualizado = eventoRepository.save(evento);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        if (!eventoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        eventoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Evento>> obtenerPorCategoria(@PathVariable Long categoriaId) {
        if (!categoriaRepository.existsById(categoriaId)) {
            return ResponseEntity.notFound().build();
        }
        List<Evento> eventos = eventoRepository.findByCategoriaId(categoriaId);
        return ResponseEntity.ok(eventos);
    }
}