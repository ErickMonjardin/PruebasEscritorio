package Laboratorio.Practica9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GestorEstudiantesLogic {
    private ArrayList<Estudiante> estudiantes;

    public GestorEstudiantesLogic() {
        this.estudiantes = new ArrayList<>();
    }

    public void agregarEstudiante(Estudiante nuevo) {
        if (nuevo.getPromedio() < 0 || nuevo.getPromedio() > 10) {
            throw new IllegalArgumentException("El promedio debe estar entre 0 y 10");
        }
        if (existeMatricula(nuevo.getMatricula())) {
            throw new IllegalArgumentException("La matrícula ya existe");
        }
        
        estudiantes.add(nuevo);
        // Mantener ordenada por matrícula para la búsqueda interpolada
        Collections.sort(estudiantes, Comparator.comparingInt(Estudiante::getMatricula));
    }

    public boolean existeMatricula(int matricula) {
        return estudiantes.stream().anyMatch(e -> e.getMatricula() == matricula);
    }

    public Estudiante busquedaInterpolada(int matricula) {
        if (estudiantes.isEmpty()) return null;
        
        int low = 0;
        int high = estudiantes.size() - 1;
        
        while (low <= high && matricula >= estudiantes.get(low).getMatricula() && matricula <= estudiantes.get(high).getMatricula()) {
            if (low == high) {
                if (estudiantes.get(low).getMatricula() == matricula) return estudiantes.get(low);
                return null;
            }
            
            int pos = low + (((high - low) / 
                (estudiantes.get(high).getMatricula() - estudiantes.get(low).getMatricula())) * 
                (matricula - estudiantes.get(low).getMatricula()));
            
            if (estudiantes.get(pos).getMatricula() == matricula) return estudiantes.get(pos);
            if (estudiantes.get(pos).getMatricula() < matricula) low = pos + 1;
            else high = pos - 1;
        }
        return null;
    }

    public Estudiante busquedaBinariaNombre(String nombre) {
        List<Estudiante> copiaOrdenada = new ArrayList<>(estudiantes);
        Collections.sort(copiaOrdenada, Comparator.comparing(Estudiante::getNombre));
        
        int low = 0;
        int high = copiaOrdenada.size() - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            Estudiante midEst = copiaOrdenada.get(mid);
            int comparacion = midEst.getNombre().compareToIgnoreCase(nombre);
            
            if (comparacion == 0) return midEst;
            else if (comparacion < 0) low = mid + 1;
            else high = mid - 1;
        }
        return null;
    }

    public void modificarEstudiante(int matricula, String nuevoNombre, String nuevoGrado, Double nuevoPromedio) {
        Estudiante estudiante = busquedaInterpolada(matricula);
        if (estudiante == null) {
            throw new IllegalArgumentException("No se encontró estudiante con esa matrícula");
        }

        boolean sinCambiosTexto = (nuevoNombre == null || nuevoNombre.trim().isEmpty()) &&
                                  (nuevoGrado == null || nuevoGrado.trim().isEmpty());
        
        if (sinCambiosTexto && nuevoPromedio == null) {
            throw new IllegalArgumentException("Debe proporcionar al menos un dato para modificar");
        }

        if (nuevoPromedio != null && (nuevoPromedio < 0 || nuevoPromedio > 10)) {
            throw new IllegalArgumentException("El promedio debe estar entre 0 y 10");
        }

        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) estudiante.setNombre(nuevoNombre.trim());
        if (nuevoGrado != null && !nuevoGrado.trim().isEmpty()) estudiante.setGrado(nuevoGrado.trim());
        if (nuevoPromedio != null) estudiante.setPromedio(nuevoPromedio);
    }

    public List<Estudiante> filtrarEstudiantes(String grado, int matriculaInicio, int matriculaFin) {
        return estudiantes.stream()
            .filter(e -> grado == null || grado.trim().isEmpty() || e.getGrado().equalsIgnoreCase(grado.trim()))
            .filter(e -> e.getMatricula() >= matriculaInicio && e.getMatricula() <= matriculaFin)
            .collect(Collectors.toList());
    }

    public List<Estudiante> getTodos() {
        return new ArrayList<>(estudiantes);
    }
}