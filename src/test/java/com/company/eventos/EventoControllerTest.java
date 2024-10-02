package com.company.eventos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventoControllerTest {

    @Mock
    private ProyectoRepository proyectoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ProyectoController proyectoController;

    private Proyecto proyecto;

    @BeforeEach
    public void setUp() {
        proyecto = new Proyecto();
        proyecto.setId(1L);
        proyecto.setNombre("Proyecto 1");
        proyecto.setPrecio(100.0);
    }

    @Test
    void testObtenerTodos() {
        System.out.println("Ejecutando prueba: testObtenerTodos");
        when(proyectoRepository.findAll()).thenReturn(Arrays.asList(proyecto));
        ResponseEntity<List<Proyecto>> responseEntity = proyectoController.obtenerTodos();
        List<Proyecto> proyectos = responseEntity.getBody();
        
        System.out.println("Datos enviados a la API: []"); // No hay datos enviados en este caso
        System.out.println("Datos devueltos de la API: " + proyectos);

        Assertions.assertNotNull(proyectos);
        Assertions.assertEquals(1, proyectos.size());
        Assertions.assertEquals("Proyecto 1", proyectos.get(0).getNombre());
    }

    @Test
    void testObtenerPorId() {
        System.out.println("Ejecutando prueba: testObtenerPorId");
        when(proyectoRepository.findById(anyLong())).thenReturn(Optional.of(proyecto));
        ResponseEntity<Proyecto> responseEntity = proyectoController.obtenerPorId(1L);
        
        System.out.println("Datos enviados a la API: ID = 1");
        System.out.println("Datos devueltos de la API: " + responseEntity.getBody());

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(proyecto, responseEntity.getBody());
    }

    @Test
    void testCrearProyecto() {
        System.out.println("Ejecutando prueba: testCrearProyecto");
        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(proyecto);
        
        System.out.println("Datos enviados a la API: " + proyecto);
        ResponseEntity<Proyecto> response = proyectoController.crearProyecto(proyecto);
        System.out.println("Datos devueltos de la API: " + response.getBody());

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(proyecto, response.getBody());
        verify(proyectoRepository, times(1)).save(any(Proyecto.class));
    }

    @Test
    void testActualizarProyecto() {
        System.out.println("Ejecutando prueba: testActualizarProyecto");
        when(proyectoRepository.existsById(anyLong())).thenReturn(true);
        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(proyecto);
        
        System.out.println("Datos enviados a la API: ID = 1, " + proyecto);
        ResponseEntity<Proyecto> response = proyectoController.actualizarProyecto(1L, proyecto);
        System.out.println("Datos devueltos de la API: " + response.getBody());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(proyecto, response.getBody());
        verify(proyectoRepository, times(1)).save(any(Proyecto.class));
    }

    @Test
    void testActualizarProyectoNoEncontrado() {
        System.out.println("Ejecutando prueba: testActualizarProyectoNoEncontrado");
        when(proyectoRepository.existsById(anyLong())).thenReturn(false);
        ResponseEntity<Proyecto> response = proyectoController.actualizarProyecto(1L, proyecto);
        
        System.out.println("Datos enviados a la API: ID = 1, " + proyecto);
        System.out.println("Datos devueltos de la API: " + response.getStatusCode());

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testEliminarProyecto() {
        System.out.println("Ejecutando prueba: testEliminarProyecto");
        when(proyectoRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(proyectoRepository).deleteById(anyLong());
        ResponseEntity<Void> response = proyectoController.eliminarProyecto(1L);
        
        System.out.println("Datos enviados a la API: ID = 1");
        System.out.println("Datos devueltos de la API: " + response.getStatusCode());

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(proyectoRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testEliminarProyectoNoEncontrado() {
        System.out.println("Ejecutando prueba: testEliminarProyectoNoEncontrado");
        when(proyectoRepository.existsById(anyLong())).thenReturn(false);
        ResponseEntity<Void> response = proyectoController.eliminarProyecto(1L);
        
        System.out.println("Datos enviados a la API: ID = 1");
        System.out.println("Datos devueltos de la API: " + response.getStatusCode());

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testObtenerPorCategoria() {
        System.out.println("Ejecutando prueba: testObtenerPorCategoria");
        when(categoriaRepository.existsById(anyLong())).thenReturn(true);
        when(proyectoRepository.findByCategoriaId(anyLong())).thenReturn(Arrays.asList(proyecto));
        ResponseEntity<List<Proyecto>> response = proyectoController.obtenerPorCategoria(1L);
        
        List<Proyecto> proyectos = response.getBody();
        System.out.println("Datos enviados a la API: Categoria ID = 1");
        System.out.println("Datos devueltos de la API: " + proyectos);

        Assertions.assertNotNull(proyectos);
        Assertions.assertEquals(1, proyectos.size());
        Assertions.assertEquals("Proyecto 1", proyectos.get(0).getNombre());
    }
}
