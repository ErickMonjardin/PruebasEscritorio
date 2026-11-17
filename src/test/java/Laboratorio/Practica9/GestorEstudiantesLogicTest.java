package Laboratorio.Practica9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class GestorEstudiantesLogicTest {
    
    private GestorEstudiantesLogic gestor;

    @BeforeEach
    void setUp() {
        gestor = new GestorEstudiantesLogic();
        gestor.agregarEstudiante(new Estudiante("Erick Monjardin", 12345, "6to", 9.5));
        gestor.agregarEstudiante(new Estudiante("Adrian Cardona", 54321, "6to", 8.8));
    }

    @Test
    void testPU1_BusquedaInterpolada() {
        Estudiante exito = gestor.busquedaInterpolada(12345);
        assertNotNull(exito, "Debe encontrar al estudiante con matrícula 12345");
        assertEquals("Erick Monjardin", exito.getNombre());

        Estudiante fallo = gestor.busquedaInterpolada(99999);
        assertNull(fallo, "Debe retornar null al buscar una matrícula inexistente");
    }

    @Test
    void testPU2_BusquedaBinaria() {
        Estudiante exito = gestor.busquedaBinariaNombre("Adrian Cardona");
        assertNotNull(exito, "Debe encontrar a Adrian Cardona");
        assertEquals(54321, exito.getMatricula());

        Estudiante fallo = gestor.busquedaBinariaNombre("Nombre Inexistente");
        assertNull(fallo, "Debe retornar null al buscar un nombre que no existe");
    }

    @Test
    void testPU3_InsercionYDuplicados() {
        Estudiante nuevo = new Estudiante("Emmanuel Romo", 111, "1ro", 10.0);
        gestor.agregarEstudiante(nuevo);
        assertTrue(gestor.existeMatricula(111), "El estudiante nuevo debe ser agregado con éxito");

        Estudiante duplicado = new Estudiante("Emmanuel Clon", 111, "2do", 8.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gestor.agregarEstudiante(duplicado);
        });
        assertEquals("La matrícula ya existe", exception.getMessage());
    }

    @Test
    void testPU4_ValidacionPromedio() {
        Estudiante valido = new Estudiante("Juan Perez", 222, "3ro", 8.5);
        gestor.agregarEstudiante(valido);
        assertTrue(gestor.existeMatricula(222));

        Estudiante invalido = new Estudiante("Test Negativo", 333, "3ro", -5.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gestor.agregarEstudiante(invalido);
        });
        assertEquals("El promedio debe estar entre 0 y 10", exception.getMessage());
    }

    @Test
    void testPU5_FiltradoGrado() {
        List<Estudiante> exito = gestor.filtrarEstudiantes("6to", Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertEquals(2, exito.size(), "Debe encontrar 2 estudiantes en 6to grado");

        List<Estudiante> fallo = gestor.filtrarEstudiantes("Universidad", Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertEquals(0, fallo.size(), "Debe retornar una lista vacía para un grado inexistente");
    }
}