package com.company.eventos;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/proyectos")
public class EventoWebController {
    private static final String REDIRECT_ProyectoS = "redirect:/proyectos";
    private final EventoRepository eventoRepository;
    private final CategoriaRepository categoriaRepository; 

    public EventoWebController(EventoRepository eventoRepository, CategoriaRepository categoriaRepository) {
        this.eventoRepository = eventoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public String listarProyectos(Model model) {
        model.addAttribute("proyectos", eventoRepository.findAll());
        return "proyectos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("proyecto", new Evento());
        model.addAttribute("categorias", categoriaRepository.findAll()); 
        return "proyectos/formulario";
    }

    @PostMapping("/nuevo")
    public String crearProyecto(@ModelAttribute Evento evento) {
        eventoRepository.save(evento);
        return REDIRECT_ProyectoS;
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de evento inválido: " + id));
        model.addAttribute("evento", evento);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "proyectos/formulario";
    }

    @PostMapping("/editar/{id}")
    public String actualizarProyecto(@PathVariable Long id, @ModelAttribute Evento evento) {
        evento.setId(id);
        eventoRepository.save(evento);
        return REDIRECT_ProyectoS;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProyecto(@PathVariable Long id) {
        eventoRepository.deleteById(id);
        return REDIRECT_ProyectoS;
    }
}