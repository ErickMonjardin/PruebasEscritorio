package Laboratorio.Practica9; // La ruta debe ser idéntica a la de main

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class GestorEstudiantesLogicTest {
    
    private GestorEstudiantesLogic gestor;

    @BeforeEach
    void setUp() {
        gestor = new GestorEstudiantesLogic();
    }

    @Test
    void testFiltrarPorGradoExacto() {
        gestor.agregarEstudiante(new Estudiante("Juan", 101, "1A", 8.0));
        gestor.agregarEstudiante(new Estudiante("Ana", 102, "2B", 9.0));
        gestor.agregarEstudiante(new Estudiante("Luis", 103, "1A", 7.5));
        
        List<Estudiante> filtrados = gestor.filtrarEstudiantes("1A", Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        assertEquals(2, filtrados.size());
        assertTrue(filtrados.stream().allMatch(e -> e.getGrado().equals("1A")));
    }

    @Test
    void testFiltrarPorRangoDeMatricula() {
        gestor.agregarEstudiante(new Estudiante("Juan", 100, "1A", 8.0));
        gestor.agregarEstudiante(new Estudiante("Ana", 150, "2B", 9.0));
        gestor.agregarEstudiante(new Estudiante("Luis", 200, "3C", 7.5));
        
        List<Estudiante> filtrados = gestor.filtrarEstudiantes("", 120, 250);
        
        assertEquals(2, filtrados.size());
        assertEquals("Ana", filtrados.get(0).getNombre());
        assertEquals("Luis", filtrados.get(1).getNombre());
    }

    @Test
    void testFiltrarSinRestriccionesDevuelveTodos() {
        gestor.agregarEstudiante(new Estudiante("Juan", 100, "1A", 8.0));
        gestor.agregarEstudiante(new Estudiante("Ana", 150, "2B", 9.0));
        
        List<Estudiante> filtrados = gestor.filtrarEstudiantes("", Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        assertEquals(2, filtrados.size());
    }
    
    @Test
    void testAgregarEstudianteExitosamente() {
        Estudiante e = new Estudiante("Juan Perez", 101, "3A", 9.5);
        gestor.agregarEstudiante(e);
        
        assertTrue(gestor.existeMatricula(101));
        assertEquals(1, gestor.getTodos().size());
    }

    @Test
    void testAgregarEstudianteFallaPorPromedioInvalido() {
        Estudiante e = new Estudiante("Ana Gomez", 102, "2B", 11.0);
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gestor.agregarEstudiante(e);
        });
        
        assertEquals("El promedio debe estar entre 0 y 10", exception.getMessage());
    }

    @Test
    void testBusquedaInterpoladaEncuentraEstudiante() {
        gestor.agregarEstudiante(new Estudiante("Juan", 100, "1A", 8.0));
        gestor.agregarEstudiante(new Estudiante("Ana", 200, "1A", 9.0));
        gestor.agregarEstudiante(new Estudiante("Luis", 300, "1A", 7.0));
        
        Estudiante resultado = gestor.busquedaInterpolada(200);
        
        assertNotNull(resultado);
        assertEquals("Ana", resultado.getNombre());
    }
}